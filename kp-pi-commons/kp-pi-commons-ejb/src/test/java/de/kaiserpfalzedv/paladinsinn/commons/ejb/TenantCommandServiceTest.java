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

import de.kaiserpfalzedv.paladinsinn.commons.api.BuilderValidationException;
import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.DuplicateEntityException;
import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.DuplicateUniqueIdException;
import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.DuplicateUniqueKeyException;
import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.EntityNotFoundException;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.Tenant;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.TenantBuilder;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.store.TenantCrudService;
import de.kaiserpfalzedv.paladinsinn.commons.store.jpa.model.TenantJPA;
import de.kaiserpfalzedv.paladinsinn.commons.store.jpa.model.TenantJPABuilder;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-26
 */
public class TenantCommandServiceTest {
    private TenantCommandService service;

    private TenantCrudService crudService;


    @Test
    public void shouldCreateTenantWhenUniqueDataIsGiven() throws BuilderValidationException, DuplicateEntityException {
        Tenant tenant = createDefaultTenant();

        service.create(tenant);

        verify(crudService).create(tenant);
    }

    private Tenant createDefaultTenant() throws BuilderValidationException {
        return new TenantBuilder()
                .withKey("TENANT_KEY")
                .withName("TENANT_NAME")
                .build();
    }


    @Test(expected = DuplicateUniqueIdException.class)
    public void shouldThrowDuplicateUniqueIdExcptionWhenTheUniqueIdIsNotUnique() throws BuilderValidationException, DuplicateEntityException {
        Tenant tenant = createDefaultTenant();

        when(crudService.create(tenant)).thenThrow(new DuplicateUniqueIdException(Tenant.class, tenant));

        service.create(tenant);

        // no verification since the exception defined in the test is the verification
    }

    @Test(expected = DuplicateUniqueKeyException.class)
    public void shouldThrowDuplicateUniqueKeyExceptionWhenTheKeyIsNotUnique() throws BuilderValidationException, DuplicateEntityException {
        Tenant tenant = createDefaultTenant();

        when(crudService.create(tenant)).thenThrow(new DuplicateUniqueKeyException(Tenant.class, "key", tenant));

        service.create(tenant);

        // no verification since the exception defined in the test is the verification
    }

    @Test
    public void shouldChangeTheTenantWhenNewKeyIsGiven() throws BuilderValidationException, EntityNotFoundException, DuplicateEntityException {
        UUID tenantId = UUID.randomUUID();

        final Optional<Tenant> wrappedTenant = retrieveTenantJPA(tenantId);

        when(crudService.retrieve(tenantId)).thenAnswer(invocationOnMock -> wrappedTenant);

        service.changeKey(tenantId, "NEW_KEY");

        verify(crudService).update(any());
    }

    private Optional<Tenant> retrieveTenantJPA(UUID tenantId) throws BuilderValidationException {
        TenantJPA tenant = new TenantJPABuilder()
                .withUniqueId(tenantId)
                .withKey("TENANT_KEY")
                .withName("TENANT_NAME")
                .build();

        return Optional.of(tenant);
    }

    @Test
    public void shouldChangeTheTenantWhenNewNameIsGiven() throws BuilderValidationException, EntityNotFoundException, DuplicateEntityException {
        UUID tenantId = UUID.randomUUID();

        final Optional<Tenant> wrappedTenant = retrieveTenantJPA(tenantId);

        when(crudService.retrieve(tenantId)).thenAnswer(invocationOnMock -> wrappedTenant);

        service.changeName(tenantId, "NEW Tenant Name");

        verify(crudService).update(any());
    }

    @Test
    public void shouldDeleteTenantWhenTheCorrectUniqueIdIsGiven() {
        UUID tenantId = UUID.randomUUID();

        service.delete(tenantId);

        verify(crudService).delete(tenantId);
    }

    @Test
    public void shouldDeleteTenantWhenNoValidUniqueIdIsGiven() {
        UUID tenantId = UUID.randomUUID();

        service.delete(tenantId);

        verify(crudService).delete(tenantId);
    }


    @Before
    public void setUpService() {
        crudService = mock(TenantCrudService.class);
        service = new TenantCommandService(crudService);
    }
}
