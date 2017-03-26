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

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.api.BuilderValidationException;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.Page;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.PageBuilder;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.PageRequest;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.PageRequestImpl;
import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.DuplicateEntityException;
import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.EntityNotFoundException;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.Tenant;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.TenantBuilder;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.store.TenantCrudService;
import de.kaiserpfalzedv.paladinsinn.commons.store.jpa.model.TenantJPA;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-26
 */
public class TenantQueryServiceTest {
    private TenantQueryService service;

    private TenantCrudService crudService;


    @Test
    public void shouldRetrieveTenantWhenUniqueDataIsGiven() throws BuilderValidationException, DuplicateEntityException {
        Tenant tenant = createDefaultTenant();
        when(service.retrieve(tenant.getUniqueId())).thenReturn(Optional.of(tenant));

        Optional<Tenant> result = service.retrieve(tenant.getUniqueId());

        assertTrue(result.isPresent());
    }

    private Tenant createDefaultTenant() throws BuilderValidationException {
        return new TenantBuilder()
                .withKey("TENANT_KEY")
                .withName("TENANT_NAME")
                .build();
    }


    @Test
    public void shouldReturnEmptyOptionalWhenTheUniqueIdIsNotFound() throws BuilderValidationException, DuplicateEntityException {
        UUID tenantId = UUID.randomUUID();

        when(crudService.retrieve(tenantId)).thenReturn(Optional.empty());

        Optional<Tenant> result = service.retrieve(tenantId);

        assertFalse(result.isPresent());
    }

    @Test
    public void shouldRetrieveTenantWhenKeyIsGiven() throws BuilderValidationException, DuplicateEntityException {
        Tenant tenant = createDefaultTenant();
        when(service.retrieve(tenant.getKey())).thenReturn(Optional.of(tenant));

        Optional<Tenant> result = service.retrieve(tenant.getKey());

        assertTrue(result.isPresent());
    }

    @Test
    public void shouldReturnEmptyOptionalWhenTheKeyIsNotFound() throws BuilderValidationException, DuplicateEntityException {
        when(crudService.retrieve("KEY")).thenReturn(Optional.empty());

        Optional<Tenant> result = service.retrieve("KEY");

        assertFalse(result.isPresent());
    }


    @Test
    public void shouldReturnValidPageWhenRequested() throws BuilderValidationException, EntityNotFoundException, DuplicateEntityException {
        PageRequest pageRequest = new PageRequestImpl(1, 10);


        Page<? extends Tenant> resultPage = new PageBuilder<TenantJPA>()
                .withPage(new HashSet<>(), 1, 10)
                .build();

        when(crudService.retrieve(pageRequest)).thenAnswer(invocationOnMock -> resultPage);

        Page<? extends Tenant> result = service.retrieve(pageRequest);

        verify(crudService).retrieve(pageRequest);
        Assert.assertEquals(0, result.getTotalElements());
    }


    @Before
    public void setUpService() {
        crudService = mock(TenantCrudService.class);
        service = new TenantQueryService(crudService);
    }
}
