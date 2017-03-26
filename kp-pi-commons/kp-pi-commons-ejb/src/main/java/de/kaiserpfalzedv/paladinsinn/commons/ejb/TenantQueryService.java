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

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateless;
import javax.inject.Inject;

import de.kaiserpfalzedv.paladinsinn.commons.api.BuilderValidationException;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.Page;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.PageBuilder;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.PageRequest;
import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.DataConversionException;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.Tenant;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.store.TenantCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the EJB that encapsulates the {@link TenantCrudService} for accessing tenant data. Together with the
 * {@link TenantCommandService} it implements the Query-and-Command-Segregation pattern for managing {@link Tenant}
 * data.
 *
 * This is the read-only interface part of the managing services.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-25
 */
@Stateless
public class TenantQueryService
        implements de.kaiserpfalzedv.paladinsinn.commons.api.tenant.service.TenantQueryService {
    private static final Logger LOG = LoggerFactory.getLogger(TenantQueryService.class);

    @Inject
    private TenantCrudService service;


    public TenantQueryService() {}

    public TenantQueryService(TenantCrudService service) {
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
    public Optional<Tenant> retrieve(UUID uniqueId) {
        Optional<? extends Tenant> result = service.retrieve(uniqueId);

        return (result.isPresent()) ? Optional.of(result.get()) : Optional.empty();
    }

    @Override
    public Optional<Tenant> retrieve(String key) {
        Optional<? extends Tenant> result = service.retrieve(key);

        // repackaging as Optional<Tenant> instad of Optional<? extends Tenant>
        return result.isPresent() ? Optional.of(result.get()) : Optional.empty();
    }

    @Override
    public Page<Tenant> retrieve(PageRequest pageRequest) {
        Page<? extends Tenant> result = service.retrieve(pageRequest);

        ArrayList<Tenant> data = new ArrayList<>(result.getData());

        try {
            return new PageBuilder<Tenant>()
                    .withData(data)
                    .withRequest(result.getCurrentRequest())
                    .withTotalElements(result.getTotalElements())
                    .withTotalPages(result.getTotalPages())
                    .build();
        } catch (BuilderValidationException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new DataConversionException(Tenant.class, e);
        }
    }
}
