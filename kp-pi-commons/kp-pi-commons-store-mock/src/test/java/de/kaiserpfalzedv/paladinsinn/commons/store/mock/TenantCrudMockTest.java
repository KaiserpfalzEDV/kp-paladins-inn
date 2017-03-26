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

package de.kaiserpfalzedv.paladinsinn.commons.store.mock;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.api.BuilderValidationException;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.Page;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.PageRequestImpl;
import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.PersistenceException;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.Tenant;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.TenantBuilder;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.store.TenantCrudService;
import de.kaiserpfalzedv.paladinsinn.commons.tenant.store.mock.TenantCrudMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-18
 */
public class TenantCrudMockTest {
    private static final Logger LOG = LoggerFactory.getLogger(TenantCrudMockTest.class);

    private static final UUID TENANT_UNIQUE_ID_1 = UUID.randomUUID();
    private static final String TENANT_KEY_1 = "TEN1";
    private static final String TENANT_NAME_1 = "tenant 1";
    private static final Tenant TENANT_1;

    private static final UUID TENANT_UNIQUE_ID_2 = UUID.randomUUID();
    private static final String TENANT_KEY_2 = "TEN2";
    private static final String TENANT_NAME_2 = "tenant 2";
    private static final Tenant TENANT_2;

    private static final UUID TENANT_UNIQUE_ID_3 = UUID.randomUUID();
    private static final String TENANT_KEY_3 = "TEN3";
    private static final String TENANT_NAME_3 = "tenant 3";
    private static final Tenant TENANT_3;

    private static final UUID TENANT_UNIQUE_ID_4 = UUID.randomUUID();
    private static final String TENANT_KEY_4 = "TEN4";
    private static final String TENANT_NAME_4 = "tenant 4";
    private static final Tenant TENANT_4;

    private static final UUID TENANT_UNIQUE_ID_5 = UUID.randomUUID();
    private static final String TENANT_KEY_5 = "TEN5";
    private static final String TENANT_NAME_5 = "tenant 5";
    private static final Tenant TENANT_5;

    private static final UUID TENANT_UNIQUE_ID_6 = UUID.randomUUID();
    private static final String TENANT_KEY_6 = "TEN6";
    private static final String TENANT_NAME_6 = "tenant 6";
    private static final Tenant TENANT_6;

    static {
        try {
            TENANT_1 = new TenantBuilder().withUniqueId(TENANT_UNIQUE_ID_1)
                                          .withKey(TENANT_KEY_1)
                                          .withName(TENANT_NAME_1)
                                          .build();
            TENANT_2 = new TenantBuilder().withUniqueId(TENANT_UNIQUE_ID_2)
                                          .withKey(TENANT_KEY_2)
                                          .withName(TENANT_NAME_2)
                                          .build();
            TENANT_3 = new TenantBuilder().withUniqueId(TENANT_UNIQUE_ID_3)
                                          .withKey(TENANT_KEY_3)
                                          .withName(TENANT_NAME_3)
                                          .build();
            TENANT_4 = new TenantBuilder().withUniqueId(TENANT_UNIQUE_ID_4)
                                          .withKey(TENANT_KEY_4)
                                          .withName(TENANT_NAME_4)
                                          .build();
            TENANT_5 = new TenantBuilder().withUniqueId(TENANT_UNIQUE_ID_5)
                                          .withKey(TENANT_KEY_5)
                                          .withName(TENANT_NAME_5)
                                          .build();
            TENANT_6 = new TenantBuilder().withUniqueId(TENANT_UNIQUE_ID_6)
                                          .withKey(TENANT_KEY_6)
                                          .withName(TENANT_NAME_6)
                                          .build();
        } catch (BuilderValidationException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    private TenantCrudService service;


    @Test
    public void shouldSaveTenantWhenCalledWithValidTenant() throws PersistenceException {
        Tenant result = service.create(TENANT_1);

        assertEquals("The objects should be equal!", TENANT_1, result);
        assertNotEquals("The objects should not be the same!",
                        System.identityHashCode(TENANT_1), System.identityHashCode(result)
        );
    }

    @Test
    public void shouldSaveAnotherTenantWhenCalledWithANotEqualTenant() throws PersistenceException {
        service.create(TENANT_1);
        Tenant result = service.create(TENANT_2);

        assertEquals("Objects should be equal!", TENANT_2, result);
    }

    @Test(expected = PersistenceException.class)
    public void shouldFailWhenSavingATenantWithExistingUniqueId()
            throws PersistenceException, BuilderValidationException {
        service.create(TENANT_1);
        service.create(new TenantBuilder().withUniqueId(TENANT_UNIQUE_ID_1)
                                          .withKey(TENANT_KEY_2)
                                          .withName(TENANT_NAME_2)
                                          .build());
    }

    @Test(expected = PersistenceException.class)
    public void shouldFailWhenSavingATenantWithExistingKey()
            throws PersistenceException, BuilderValidationException {
        service.create(TENANT_1);
        service.create(new TenantBuilder().withUniqueId(TENANT_UNIQUE_ID_2)
                                          .withKey(TENANT_KEY_1)
                                          .withName(TENANT_NAME_2)
                                          .build());
    }

    @Test(expected = PersistenceException.class)
    public void shouldFailWhenSavingATenantWithExistingName()
            throws PersistenceException, BuilderValidationException {
        service.create(TENANT_1);
        service.create(new TenantBuilder().withUniqueId(TENANT_UNIQUE_ID_2)
                                          .withKey(TENANT_KEY_2)
                                          .withName(TENANT_NAME_1)
                                          .build());
    }


    @Test
    public void shouldRetrieveTenantWhenGivenAValidUniqueId() throws PersistenceException {
        service.create(TENANT_3);

        Optional<? extends Tenant> result = service.retrieve(TENANT_UNIQUE_ID_3);

        assertTrue(result.isPresent());
    }

    @Test
    public void shouldRetrieveNoTenantWhenGivenAnInvalidUniqueId() {
        Optional<? extends Tenant> result = service.retrieve(UUID.randomUUID());

        assertFalse(result.isPresent());
    }


    @Test
    public void shouldRetrieveTenantWhenGivenAValidKey() throws PersistenceException {
        service.create(TENANT_3);

        Optional<? extends Tenant> result = service.retrieve(TENANT_KEY_3);
        if (!result.isPresent()) {
            Assert.fail("The result is empty. Should contain a tenant!");
        }

        Tenant data = result.get();

        assertTrue(result.isPresent());
        assertEquals(TENANT_UNIQUE_ID_3, data.getUniqueId());
        assertEquals(TENANT_NAME_3, data.getName());
    }

    @Test
    public void shouldRetrieveNoTenantWhenGivenAnName() {
        Optional<? extends Tenant> result = service.retrieve("non existing tenant name");

        assertFalse(result.isPresent());
    }


    @Test
    public void shouldGetSetOf6TenantsWhen6TenantsWereSavedBefore() {
        generate6Tenants();

        Set<? extends Tenant> results = service.retrieve();

        assertEquals("There should be 6 tenants!", 6, results.size());
    }

    private void generate6Tenants() {
        try {
            service.create(TENANT_1);
            service.create(TENANT_2);
            service.create(TENANT_3);
            service.create(TENANT_4);
            service.create(TENANT_5);
            service.create(TENANT_6);
        } catch (PersistenceException e) {
            LOG.error("Unexpected exception while setting up tests.", e);
        }
    }

    @Test
    public void shouldGetTheSecondPageWhenTryingToLoadIt() {
        generate6Tenants();

        Page<? extends Tenant> result = service.retrieve(new PageRequestImpl(2, 3));

        assertEquals("Should contain 3 elements", 3, result.getCurrentPageSize());
    }

    @Test
    public void shouldUpdateDataWhenGivenWithChangedName() throws BuilderValidationException, PersistenceException {
        service.create(TENANT_1);

        Optional<? extends Tenant> data = service.retrieve(TENANT_UNIQUE_ID_1);
        if (!data.isPresent()) {
            Assert.fail("The tenant data optional is empty! Should contain the tenant data!");
        }

        Tenant updateTenant = new TenantBuilder().withTenant(data.get()).withName(TENANT_NAME_1 + " updated").build();

        Tenant result = service.update(updateTenant);

        assertEquals("Tenants should be equal", updateTenant, result);
        assertNotEquals("Tenants should not be identical",
                        System.identityHashCode(updateTenant), System.identityHashCode(result)
        );
    }

    @Test
    public void shouldReturnNoTenantWhenTenantIsDeletedBefore() throws PersistenceException {
        service.create(TENANT_1);

        service.delete(TENANT_1);

        Optional<? extends Tenant> result = service.retrieve(TENANT_UNIQUE_ID_1);
        assertFalse(result.isPresent());
    }

    @Test
    public void shouldReturnNoTenantWhenTenantIsDeletedByUniqueIdBefore() throws PersistenceException {
        service.create(TENANT_1);

        service.delete(TENANT_UNIQUE_ID_1);

        Optional<? extends Tenant> result = service.retrieve(TENANT_UNIQUE_ID_1);
        assertFalse(result.isPresent());
    }

    @Test
    public void shouldReturnNoTenantWhenTenantIsDeletedByNameBefore() throws PersistenceException {
        service.create(TENANT_1);

        service.delete(TENANT_NAME_1);

        Optional<? extends Tenant> result = service.retrieve(TENANT_UNIQUE_ID_1);
        assertFalse(result.isPresent());
    }

    @Before
    public void setUpService() throws PersistenceException {
        service = new TenantCrudMock();
    }
}
