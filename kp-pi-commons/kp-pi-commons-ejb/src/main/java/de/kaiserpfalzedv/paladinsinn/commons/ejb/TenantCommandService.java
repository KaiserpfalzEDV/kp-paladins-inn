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

package de.kaiserpfalzedv.paladinsinn.commons.ejb;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;

import de.kaiserpfalzedv.paladinsinn.commons.api.BuilderValidationException;
import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.DuplicateEntityException;
import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.EntityNotFoundException;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.Tenant;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.TenantBuilder;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.TenantImpl;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.store.TenantCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static javax.ejb.TransactionAttributeType.SUPPORTS;

/**
 * This is the EJB that encapsulates the {@link TenantCrudService} for changing tenant data. Together with the
 * {@link TenantQueryService} it implements the Query-and-Command-Segregation pattern for managing {@link Tenant} data.
 *
 * This is the writing interface part of the managing services.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-26
 */
@Stateless(description = "Service for modifying the tenant data.")
@TransactionAttribute(SUPPORTS)
public class TenantCommandService
        implements de.kaiserpfalzedv.paladinsinn.commons.api.tenant.service.TenantCommandService {
    private static final Logger LOG = LoggerFactory.getLogger(TenantCommandService.class);

    @Inject
    private TenantCrudService service;

    public TenantCommandService() {}

    public TenantCommandService(TenantCrudService service) {
        this.service = service;
    }

    @PostConstruct
    public void init() {
        LOG.info("{} created with service: {}", getClass().getSimpleName(), service);
    }

    @PreDestroy
    public void close() {
        LOG.info("{} destroyed", getClass().getSimpleName());
    }

    @PostActivate
    public void activate() {
        LOG.info("{} activated", getClass().getSimpleName());
    }

    @PrePassivate
    public void passivate() {
        LOG.info("{} passivated", getClass().getSimpleName());
    }

    @Override
    public Tenant create(Tenant tenant) throws DuplicateEntityException {
        return service.create(tenant);
    }

    @Override
    public void changeKey(UUID uniqueId, String key) throws EntityNotFoundException {
        TenantImpl data = loadTenantFromCrudService(uniqueId);

        //noinspection deprecation
        data.setKey(key);

        saveTenantWithCrudService(data);
    }

    private TenantImpl loadTenantFromCrudService(UUID uniqueId) throws EntityNotFoundException {
        Optional<? extends Tenant> wrapped = service.retrieve(uniqueId);

        if (!wrapped.isPresent()) {
            throw new EntityNotFoundException(Tenant.class, uniqueId);
        }

        TenantImpl data;
        try {
            data = (TenantImpl) wrapped.get();
        } catch (ClassCastException e) {
            try {
                data = (TenantImpl) new TenantBuilder().withTenant(wrapped.get()).build();
            } catch (ClassCastException | BuilderValidationException e1) {
                LOG.error(e1.getClass().getSimpleName() + " caught: " + e1.getMessage(), e1);

                throw new EntityNotFoundException(Tenant.class, uniqueId);
            }
        }
        return data;
    }

    private void saveTenantWithCrudService(TenantImpl data) {
        try {
            service.update(data);
        } catch (DuplicateEntityException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new IllegalStateException("There should be no duplicate entity exception here!");
        }
    }

    @Override
    public void changeName(UUID uniqueId, String name) throws EntityNotFoundException {
        TenantImpl data = loadTenantFromCrudService(uniqueId);

        //noinspection deprecation
        data.setName(name);

        saveTenantWithCrudService(data);
    }

    @Override
    public void delete(UUID uniqueId) {
        service.delete(uniqueId);
    }
}
