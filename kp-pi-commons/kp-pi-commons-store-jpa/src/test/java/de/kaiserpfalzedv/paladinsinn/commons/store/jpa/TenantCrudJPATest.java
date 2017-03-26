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

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import de.kaiserpfalzedv.paladinsinn.commons.api.BuilderValidationException;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.Page;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.PageRequestImpl;
import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.DuplicateEntityException;
import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.PersistenceException;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.Tenant;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.TenantBuilder;
import de.kaiserpfalzedv.paladinsinn.commons.store.jpa.model.TenantJPA;
import de.kaiserpfalzedv.paladinsinn.commons.store.jpa.model.TenantJPABuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-18
 */
@RunWith(MockitoJUnitRunner.class)
public class TenantCrudJPATest {
    private static final Logger LOG = LoggerFactory.getLogger(TenantCrudJPATest.class);

    private static final UUID TENANT_UNIQUE_ID_1 = UUID.randomUUID();
    private static final String TENANT_KEY_1 = "TEN1";
    private static final String TENANT_NAME_1 = "tenant 1";
    private static final ZonedDateTime TENANT_CREATED_1 = ZonedDateTime.parse("2017-01-01T00:00:00+01:00[Europe/Berlin]");
    private static final ZonedDateTime TENANT_MODIFIED_1 = ZonedDateTime.parse("2017-01-01T00:00:00+01:00[Europe/Berlin]");
    private static final Tenant TENANT_1;
    private static final TenantJPA TENANT_1_JPA;

    private static final UUID TENANT_UNIQUE_ID_2 = UUID.randomUUID();
    private static final String TENANT_KEY_2 = "TEN2";
    private static final String TENANT_NAME_2 = "tenant 2";
    private static final ZonedDateTime TENANT_CREATED_2 = ZonedDateTime.parse("2017-01-02T00:00:00+01:00[Europe/Berlin]");
    private static final ZonedDateTime TENANT_MODIFIED_2 = ZonedDateTime.parse("2017-01-03T14:00:00+01:00[Europe/Berlin]");
    private static final TenantJPA TENANT_2_JPA;

    private static final UUID TENANT_UNIQUE_ID_3 = UUID.randomUUID();
    private static final String TENANT_KEY_3 = "TEN3";
    private static final String TENANT_NAME_3 = "tenant 3";
    private static final ZonedDateTime TENANT_CREATED_3 = ZonedDateTime.parse("2017-01-03T14:00:00+01:00[Europe/Berlin]");
    private static final ZonedDateTime TENANT_MODIFIED_3 = ZonedDateTime.parse("2017-01-03T14:00:00+01:00[Europe/Berlin]");
    private static final TenantJPA TENANT_3_JPA;

    static {
        try {
            TENANT_1 = new TenantBuilder().withUniqueId(TENANT_UNIQUE_ID_1)
                                          .withKey(TENANT_KEY_1)
                                          .withName(TENANT_NAME_1)
                                          .build();

            TENANT_1_JPA = new TenantJPABuilder().withUniqueId(TENANT_UNIQUE_ID_1)
                                                 .withKey(TENANT_KEY_1)
                                                 .withName(TENANT_NAME_1)
                                                 .withCreated(TENANT_CREATED_1)
                                                 .withModified(TENANT_MODIFIED_1)
                                                 .build();
            TENANT_2_JPA = new TenantJPABuilder().withUniqueId(TENANT_UNIQUE_ID_2)
                                                 .withKey(TENANT_KEY_2)
                                                 .withName(TENANT_NAME_2)
                                                 .withCreated(TENANT_CREATED_2)
                                                 .withModified(TENANT_MODIFIED_2)
                                                 .build();
            TENANT_3_JPA = new TenantJPABuilder().withUniqueId(TENANT_UNIQUE_ID_3)
                                                 .withKey(TENANT_KEY_3)
                                                 .withName(TENANT_NAME_3)
                                                 .withCreated(TENANT_CREATED_3)
                                                 .withModified(TENANT_MODIFIED_3)
                                                 .build();
        } catch (BuilderValidationException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<TenantJPA> query;

    private TenantCrudJPA service;


    @Test
    public void shouldSaveTenantWhenCalledWithValidTenant() throws PersistenceException {
        when(em.find(TenantJPA.class, TENANT_UNIQUE_ID_1)).thenReturn(TENANT_1_JPA);

        Tenant result = service.create(TENANT_1);

        assertEquals("The objects should be equal!", TENANT_1, result);
        assertNotEquals("The objects should not be the same!",
                        System.identityHashCode(TENANT_1), System.identityHashCode(result)
        );
    }

    @Test(expected = DuplicateEntityException.class)
    public void shouldFailWhenSavingATenantWithExistingUniqueId()
            throws PersistenceException, BuilderValidationException {

        doThrow(new EntityExistsException("Entity already exists!")).when(em).persist(TENANT_1_JPA);

        service.create(TENANT_1);
    }

    @Test
    public void shouldRetrieveTenantWhenGivenAValidUniqueId() throws PersistenceException {
        when(em.find(TenantJPA.class, TENANT_UNIQUE_ID_3)).thenReturn(TENANT_3_JPA);

        Optional<? extends Tenant> result = service.retrieve(TENANT_UNIQUE_ID_3);

        assertTrue(result.isPresent());
    }

    @Test
    public void shouldRetrieveNoTenantWhenGivenAnInvalidUniqueId() {
        when(em.find(any(), any(UUID.class))).thenReturn(null);

        Optional<? extends Tenant> result = service.retrieve(UUID.randomUUID());

        assertFalse(result.isPresent());
    }


    @Test
    public void shouldRetrieveTenantWhenGivenAValidKey() throws PersistenceException {
        prepareMockitoRetrieveByKey(TENANT_KEY_3, TENANT_3_JPA);

        Optional<? extends Tenant> result = service.retrieve(TENANT_KEY_3);
        if (!result.isPresent()) {
            Assert.fail("The result is empty. Should contain a tenant!");
        }

        @SuppressWarnings("OptionalGetWithoutIsPresent") Tenant data = result.get();

        assertTrue(result.isPresent());
        assertEquals(TENANT_UNIQUE_ID_3, data.getUniqueId());
        assertEquals(TENANT_NAME_3, data.getName());
    }

    private void prepareMockitoRetrieveByKey(final String key, final TenantJPA result) {
        when(em.createNamedQuery("tenant-by-key", TenantJPA.class)).thenReturn(query);
        when(query.setParameter("key", key)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(result);

    }

    @Test
    public void shouldRetrieveNoTenantWhenGivenAKey() {
        when(em.createNamedQuery("tenant-by-key", TenantJPA.class)).thenReturn(query);
        when(query.setParameter("key", "non existing tenant name")).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new NoResultException("There is no result for query."));

        Optional<? extends Tenant> result = service.retrieve("non existing tenant name");

        assertFalse(result.isPresent());
    }

    @Test
    public void shouldGetTheSecondPageWhenTryingToLoadIt() {
        ArrayList<TenantJPA> tenantList = new ArrayList<>(3);
        tenantList.add(TENANT_1_JPA);
        tenantList.add(TENANT_2_JPA);
        tenantList.add(TENANT_3_JPA);

        when(em.createNamedQuery("tenant-all", TenantJPA.class)).thenReturn(query);
        when(query.setFirstResult(3)).thenReturn(query);
        when(query.setMaxResults(3)).thenReturn(query);
        when(query.setLockMode(LockModeType.NONE)).thenReturn(query);
        when(query.getResultList()).thenReturn(tenantList);

        Page<? extends Tenant> result = service.retrieve(new PageRequestImpl(2, 3));

        assertEquals("Should contain 3 elements", 3, result.getCurrentPageSize());
    }

    @Test
    public void shouldUpdateDataWhenUsingAJPAObject() throws BuilderValidationException, PersistenceException {
        when(em.merge(TENANT_1_JPA)).thenReturn(new TenantJPABuilder().withTenant(TENANT_1_JPA).build());

        TenantJPA result = service.update(TENANT_1_JPA);

        assertEquals("Should be equal objects", TENANT_1_JPA, result);
        assertNotEquals("Shoud not be identical",
                        System.identityHashCode(TENANT_1_JPA), System.identityHashCode(result)
        );
    }

    @Test
    public void shouldCreateDataWhenTheDataExists() throws BuilderValidationException, PersistenceException {
        when(em.find(TenantJPA.class, TENANT_UNIQUE_ID_1)).thenReturn(TENANT_1_JPA);


        when(em.merge(any())).thenReturn(
                new TenantJPABuilder().withTenant(TENANT_1_JPA)
                                      .withModified(ZonedDateTime.now())
                                      .build()
        );

        TenantJPA result = service.update(TENANT_1);

        assertEquals("Should be equal objects", TENANT_1_JPA, result);
        assertNotEquals("Shoud not be identical",
                        System.identityHashCode(TENANT_1_JPA), System.identityHashCode(result)
        );
        assertEquals("Creation time should not differ", TENANT_1_JPA.getCreated(), result.getCreated());
        assertNotEquals("Modification time should differ", TENANT_1_JPA.getChanged(), result.getChanged());
    }

    @Test
    public void shouldCreateDataWhenTheDataNotExists() throws BuilderValidationException, PersistenceException {
        when(em.find(TenantJPA.class, TENANT_UNIQUE_ID_1)).thenReturn(null);


        when(em.merge(any())).thenReturn(
                new TenantJPABuilder().withTenant(TENANT_1_JPA)
                                      .withCreated(ZonedDateTime.now())
                                      .withModified(ZonedDateTime.now())
                                      .build()
        );

        TenantJPA result = service.update(TENANT_1);

        assertEquals("Should be equal objects", TENANT_1_JPA, result);
        assertNotEquals("Shoud not be identical",
                        System.identityHashCode(TENANT_1_JPA), System.identityHashCode(result)
        );
        assertNotEquals("Creation time should differ", TENANT_1_JPA.getCreated(), result.getCreated());
        assertNotEquals("Modification time should differ", TENANT_1_JPA.getChanged(), result.getChanged());
    }

    @Test
    public void shouldDeleteWhenGiventheUniqueId() throws PersistenceException {
        when(em.find(TenantJPA.class, TENANT_UNIQUE_ID_1)).thenReturn(TENANT_1_JPA);

        service.delete(TENANT_UNIQUE_ID_1);
    }

    @Test
    public void shouldDeleteWhenGiventheUniqueKey() throws PersistenceException {
        prepareMockitoRetrieveByKey(TENANT_KEY_1, TENANT_1_JPA);

        service.delete(TENANT_UNIQUE_ID_1);
    }

    @Before
    public void setUpService() throws PersistenceException {
        service = new TenantCrudJPA(em);
    }
}
