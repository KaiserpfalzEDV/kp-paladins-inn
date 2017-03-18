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

package de.kaiserpfalzedv.paladinsinn.security.tenant.mock;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.BuilderValidationException;
import de.kaiserpfalzedv.paladinsinn.commons.paging.Page;
import de.kaiserpfalzedv.paladinsinn.commons.paging.PageRequest;
import de.kaiserpfalzedv.paladinsinn.commons.paging.impl.PageBuilder;
import de.kaiserpfalzedv.paladinsinn.security.tenant.TenantPersistenceException;
import de.kaiserpfalzedv.paladinsinn.security.tenant.TenantPersistenceRuntimeException;
import de.kaiserpfalzedv.paladinsinn.security.tenant.model.Tenant;
import de.kaiserpfalzedv.paladinsinn.security.tenant.model.impl.TenantBuilder;
import de.kaiserpfalzedv.paladinsinn.security.tenant.services.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-18
 */
public class TenantMock implements TenantService {
    private static final Logger LOG = LoggerFactory.getLogger(TenantMock.class);

    private final HashMap<UUID, Tenant> tenantsByUniqueId = new HashMap<>();
    private final HashMap<String, Tenant> tenantsByKey = new HashMap<>();
    private final HashMap<String, Tenant> tenantsByName = new HashMap<>();


    @Override
    public Tenant create(final Tenant tenant) throws TenantPersistenceException {
        Tenant data;

        if (tenantsByUniqueId.containsKey(tenant.getUniqueId())) {
            LOG.warn("Tenant with unique id {} already exists: {}",
                     tenant.getUniqueId(), tenantsByUniqueId.get(tenant.getUniqueId())
            );
            throw new TenantPersistenceException("Tenant with unique id " + tenant.getUniqueId() + " already exists!");
        }
        if (tenantsByKey.containsKey(tenant.getKey())) {
            LOG.warn("Tenant with key '{}' already exists: {}",
                     tenant.getName(), tenantsByName.get(tenant.getName())
            );
            throw new TenantPersistenceException("Tenant with name '" + tenant.getName() + "' already exists!");
        }
        if (tenantsByName.containsKey(tenant.getName())) {
            LOG.warn("Tenant with name '{}' already exists: {}",
                     tenant.getName(), tenantsByName.get(tenant.getName())
            );
            throw new TenantPersistenceException("Tenant with name '" + tenant.getName() + "' already exists!");
        }


        try {
            data = new TenantBuilder().withTenant(tenant).build();
        } catch (BuilderValidationException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new TenantPersistenceException("Can't save the tenant to the persistence sore: ", e);
        }

        tenantsByUniqueId.put(tenant.getUniqueId(), data);
        tenantsByKey.put(tenant.getKey(), data);
        tenantsByName.put(tenant.getName(), data);

        LOG.info("Saved tenant to persistence: {} -> {}", tenant, data);
        return data;
    }


    @Override
    public Optional<Tenant> retrieve(final UUID uniqueId) {
        return Optional.ofNullable(tenantsByUniqueId.get(uniqueId));
    }

    @Override
    public Optional<Tenant> retrieve(final String tenantKey) {
        return Optional.ofNullable(tenantsByKey.get(tenantKey));
    }

    @Override
    public Set<Tenant> retrieve() {
        return Collections.unmodifiableSet(new HashSet<>(tenantsByUniqueId.values()));
    }

    @Override
    public Page<Tenant> retrieve(final PageRequest pageRequest) throws TenantPersistenceRuntimeException {
        try {
            return new PageBuilder<Tenant>()
                    .withPage(tenantsByUniqueId.values(), pageRequest.getPageNumber(), pageRequest.getPageSize())
                    .build();
        } catch (BuilderValidationException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new TenantPersistenceRuntimeException("Can't generate the page requested by the caller", e);
        }
    }


    @Override
    public Tenant update(final Tenant tenant) throws TenantPersistenceException {
        delete(tenant);

        return create(tenant);
    }


    @Override
    public void delete(final Tenant tenant) {
        if (tenantsByUniqueId.containsKey(tenant.getUniqueId())) {
            Tenant deletedTenant = tenantsByUniqueId.get(tenant.getUniqueId());

            tenantsByUniqueId.remove(tenant.getUniqueId());
            tenantsByKey.remove(tenant.getKey());
            tenantsByName.remove(tenant.getName());

            LOG.info("Deleted tenant: {}", deletedTenant);
        } else {
            LOG.warn("Can't delete tenant since it did not exist in persistence: {}", tenant);
        }
    }

    @Override
    public void delete(final UUID uniqueId) {
        if (tenantsByUniqueId.containsKey(uniqueId)) {
            delete(tenantsByUniqueId.get(uniqueId));
        } else {
            LOG.warn("Can't delete tenant since it did not exist in persiscence: uniqueID={}", uniqueId);
        }
    }

    @Override
    public void delete(final String tenantName) {
        if (tenantsByName.containsKey(tenantName)) {
            delete(tenantsByName.get(tenantName));
        } else {
            LOG.warn("Can't delete tenant since it did not exist in persistence: name={}", tenantName);
        }
    }
}
