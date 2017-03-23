/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.kaiserpfalzedv.paladinsinn.commons.store.jpa;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import de.kaiserpfalzedv.paladinsinn.commons.BuilderValidationException;
import de.kaiserpfalzedv.paladinsinn.commons.paging.Page;
import de.kaiserpfalzedv.paladinsinn.commons.paging.PageRequest;
import de.kaiserpfalzedv.paladinsinn.commons.paging.impl.PageBuilder;
import de.kaiserpfalzedv.paladinsinn.commons.persistence.DuplicateUniqueKeyException;
import de.kaiserpfalzedv.paladinsinn.commons.persistence.PersistenceRuntimeException;
import de.kaiserpfalzedv.paladinsinn.commons.service.SingleTenant;
import de.kaiserpfalzedv.paladinsinn.commons.service.WorkerService;
import de.kaiserpfalzedv.paladinsinn.commons.store.jpa.model.TenantJPA;
import de.kaiserpfalzedv.paladinsinn.commons.store.jpa.model.TenantJPABuilder;
import de.kaiserpfalzedv.paladinsinn.commons.tenant.model.Tenant;
import de.kaiserpfalzedv.paladinsinn.commons.tenant.model.impl.TenantBuilder;
import de.kaiserpfalzedv.paladinsinn.commons.tenant.store.TenantCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-18
 */
@SingleTenant
@WorkerService
public class TenantCrudJPA implements TenantCrudService {
    private static final Logger LOG = LoggerFactory.getLogger(TenantCrudJPA.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<TenantJPA> retrieve(final String tenantKey) {
        TypedQuery<TenantJPA> query = createTenantByKeyQuery(tenantKey);

        return retrieveSingleTenant(query);
    }

    @Override
    public Tenant create(final Tenant tenant) throws DuplicateUniqueKeyException {
        TenantJPA data = createTenantJPA(tenant);

        em.persist(data);
        LOG.info("Saved tenant to persistence: {} -> {}", tenant, data);

        return retrieve(tenant.getUniqueId()).orElseThrow(() -> {
            LOG.error("Could not load the newly persisted tenant: {}", data);

            return new PersistenceRuntimeException(TenantJPA.class, "Could not create new Tenant: " + data);
        });
    }

    @Override
    public void delete(final String uniqueKey) {
        TypedQuery<TenantJPA> query = createTenantByKeyQuery(uniqueKey);
        Optional<TenantJPA> loaded = retrieveSingleTenant(query);

        loaded.ifPresent(
                data -> deleteTenant(data, "Tried to delete tenant. But its key is not in the database: {}",
                                     uniqueKey
                )
        );
    }

    private TenantJPA createTenantJPA(Tenant tenant) {
        TenantJPA data;

        try {
            data = new TenantJPABuilder().withTenant(tenant).build();
        } catch (BuilderValidationException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new PersistenceRuntimeException(TenantJPA.class, "Can't build the tenant to save it to the persistence store: " + tenant);
        }

        return data;
    }

    private TypedQuery<TenantJPA> createTenantByKeyQuery(String tenantKey) {
        return createNamedQuery("tenant-get-key").setParameter("key", tenantKey);
    }

    @Override
    public Optional<TenantJPA> retrieve(final UUID uniqueId) {
        return Optional.ofNullable(em.find(TenantJPA.class, uniqueId));
    }

    private Optional<TenantJPA> retrieveSingleTenant(final TypedQuery<TenantJPA> query) {
        TenantJPA result = null;

        try {
            result = query.getSingleResult();

        } catch (NoResultException | NonUniqueResultException | IllegalStateException e) {
            LOG.warn(e.getClass().getSimpleName() + " during retrieval: " + e.getMessage(), e);

            // Do nothing. Will return null.
        } catch (PersistenceException e) {
            LOG.error(e.getClass().getSimpleName() + " during retrieval: " + e.getMessage(), e);

            throw new PersistenceRuntimeException(TenantJPA.class, "Caught a " + e.getClass().getSimpleName() + ": " + e
                    .getMessage());
        }
        return Optional.ofNullable(result);
    }

    private TypedQuery<TenantJPA> createNamedQuery(String query) {
        return em.createNamedQuery(query, TenantJPA.class);
    }

    @Override
    public Set<TenantJPA> retrieve() {
        final HashSet<TenantJPA> result = new HashSet<>();

        try {
            createNamedQuery("tenant-all").getResultList().forEach(result::add);
        } catch (IllegalStateException e) {
            LOG.warn(e.getClass().getSimpleName() + " during retrieval: " + e.getMessage(), e);

            // do nothing more. Will return an empty result.
        } catch (PersistenceException e) {
            LOG.error(e.getClass().getSimpleName() + " during retrieval: " + e.getMessage(), e);

            throw new PersistenceRuntimeException(TenantJPA.class, "Caught a " + e.getClass().getSimpleName() + ": " + e
                    .getMessage());
        }

        return result;
    }

    @Override
    public Page<TenantJPA> retrieve(final PageRequest pageRequest) throws PersistenceRuntimeException {
        List<TenantJPA> tenants = createNamedQuery("tenant-all")
                .setFirstResult(calculateFirstElementToRetrieveFromPageRequest(pageRequest))
                .setMaxResults((int) pageRequest.getPageSize())
                .setLockMode(LockModeType.NONE)
                .getResultList();

        try {
            return new PageBuilder<TenantJPA>()
                    .withData(tenants)
                    .withRequest(pageRequest)
                    .withTotalElements(tenants.size())
                    .withTotalPages(calculateTotalPages(pageRequest, tenants))
                    .build();
        } catch (BuilderValidationException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new PersistenceRuntimeException(Tenant.class, "Can't generate the page requested by the caller", e);
        }
    }

    private int calculateFirstElementToRetrieveFromPageRequest(PageRequest pageRequest) {
        return (int) ((pageRequest.getPageNumber() - 1) * pageRequest.getPageSize());
    }

    private int calculateTotalPages(PageRequest pageRequest, List<TenantJPA> tenants) {
        return (int) (tenants.size() / pageRequest.getPageSize() // all full pages
                + (tenants.size() % pageRequest.getPageSize() != 0 ? 1 : 0)); // if there is only a part of the last page
    }


    @Override
    public Tenant update(final Tenant tenant) throws DuplicateUniqueKeyException {
        Optional<TenantJPA> loaded = retrieve(tenant.getUniqueId());
        if (!loaded.isPresent()) return create(tenant);

        TenantJPA data = loaded.get();
        data.setName(tenant.getName());
        data.setKey(tenant.getKey());

        try {
            TenantJPA merged = em.merge(data);

            LOG.info("Updated tenant data: {}", data);

            return new TenantBuilder().withTenant(merged).build();
        } catch (IllegalArgumentException e) {
            return create(tenant);
        } catch (TransactionRequiredException | BuilderValidationException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new PersistenceRuntimeException(TenantJPA.class, e.getClass()
                                                                    .getSimpleName() + " caught: " + e.getMessage());
        }
    }


    @Override
    public void delete(final Tenant tenant) {
        UUID uniqueId = tenant.getUniqueId();

        delete(uniqueId);

    }

    public void delete(final UUID uniqueId) {
        retrieve(uniqueId).ifPresent(
                data -> deleteTenant(data, "Tried to delete tenant. But its UUID not in the database: {}", uniqueId)
        );
    }

    private void deleteTenant(TenantJPA data, final String errorMessage, final Object errorMessageData) {
        if (data != null) {
            try {
                em.remove(data);

                LOG.info("Deleted tenant: {}", data);
            } catch (IllegalArgumentException e) {
                LOG.warn("Tried to delete tenant. But it did not exist: {}", data);
            } catch (TransactionRequiredException e) {
                LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

                throw new PersistenceRuntimeException(
                        TenantJPA.class,
                        e.getClass().getSimpleName() + " caught: " + e.getMessage()
                );
            }
        } else {
            LOG.warn(errorMessage, errorMessageData);
        }
    }







}
