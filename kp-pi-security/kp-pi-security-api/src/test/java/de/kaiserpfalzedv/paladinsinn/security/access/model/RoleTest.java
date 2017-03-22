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

package de.kaiserpfalzedv.paladinsinn.security.access.model;

import java.util.HashSet;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.security.model.Entitlement;
import de.kaiserpfalzedv.paladinsinn.security.model.Role;
import de.kaiserpfalzedv.paladinsinn.security.model.impl.EntitlementBuilder;
import de.kaiserpfalzedv.paladinsinn.security.model.impl.RoleBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class RoleTest {
    private static final Logger LOG = LoggerFactory.getLogger(RoleTest.class);

    private static final UUID ROLE_ID = UUID.randomUUID();
    private static final String ROLE_NAME = "role";

    private static final HashSet<Entitlement> DEFAULT_ENTITLEMENTS = new HashSet<>(5);
    private static final HashSet<Role> DEFAULT_ROLES = new HashSet<>(5);
    private static final HashSet<Entitlement> DEFAULT_EFFECTIVE_ENTITLEMENTS = new HashSet<>(30);

    // Want to do it only once, so I do it in a static block ...
    static {
        for(int i=1; i <= 5; i++) {
            DEFAULT_ENTITLEMENTS.add(
                    new EntitlementBuilder().withName("entitlement0-" + i).build()
            );
        }
        DEFAULT_EFFECTIVE_ENTITLEMENTS.addAll(DEFAULT_ENTITLEMENTS);


        for(int i=1; i <= 5; i++) {
            HashSet<Entitlement> entitlements = new HashSet<>(5);
            for (int j=1; j <= 5; j++) {
                entitlements.add(
                        new EntitlementBuilder().withName("entitlement" + i + "-" + j).build()
                );
            }

            DEFAULT_ROLES.add(
                    new RoleBuilder().withName("role" + i).withEntitlements(entitlements).build()
            );

            DEFAULT_EFFECTIVE_ENTITLEMENTS.addAll(entitlements);
        }
    }


    private RoleBuilder service;



    @Test
    public void shouldCreateMinimalRoleWhenNothingIsGiven() {
        Role result = service.build();

        assertEquals("The name should match the unique id",
                            result.getUniqueId().toString(), result.getName());
    }

    @Test
    public void shouldCreateRoleWithNameWhenNameIsGiven() {
        Role result = service.withName(ROLE_NAME).build();

        assertEquals("The name should match the prepared name", ROLE_NAME, result.getName());
    }

    @Test
    public void shouldCreateRoleWithGivenNameAdUniqueIdWhenEverythingIsGiven() {
        Role result = service.withName(ROLE_NAME).withUniqueId(ROLE_ID).build();

        assertEquals("The unique id should be the prepared id", ROLE_ID, result.getUniqueId());
        assertEquals("The name should match the prepared name", ROLE_NAME, result.getName());
    }


    @Test
    public void shouldHaveAllEntitlementsWhenFullRoleIsCreated() {
        Role result = service
                .withName(ROLE_NAME)
                .withUniqueId(ROLE_ID)
                .withEntitlements(DEFAULT_ENTITLEMENTS)
                .withRoles(DEFAULT_ROLES)
                .build();

        for (Entitlement e : DEFAULT_EFFECTIVE_ENTITLEMENTS) {
            assertTrue("Entitlement is missing: " + e, result.isEntitled(e));
        }
    }

    @Test
    public void shouldNotBeEntitledWhenTheRoleDoesNotHaveTheEntitlement() {
        Role result = service
                .withName(ROLE_NAME)
                .withUniqueId(ROLE_ID)
                .withEntitlements(DEFAULT_ENTITLEMENTS)
                .withRoles(DEFAULT_ROLES)
                .build();

        assertFalse("Role is entitled", result.isEntitled(new EntitlementBuilder().build()));
    }

    @Test
    public void shouldHaveAllRolesWhenFullRoleIsCreated() {
        Role result = service
                .withName(ROLE_NAME)
                .withUniqueId(ROLE_ID)
                .withEntitlements(DEFAULT_ENTITLEMENTS)
                .withRoles(DEFAULT_ROLES)
                .build();

        assertEquals("Roles do not match", DEFAULT_ROLES, result.getIncludedRoles());
    }


    @Test
    public void shouldNotBeInRoleWhenRoleIsRemoved() {
        Role toRemove = DEFAULT_ROLES.iterator().next();

        Role result = service
                .withName(ROLE_NAME)
                .withUniqueId(ROLE_ID)
                .withRoles(DEFAULT_ROLES)
                .removeRole(toRemove)
                .build();

        assertFalse("Role should not be a sub role: " + toRemove, result.getIncludedRoles().contains(toRemove));
    }


    @Test
    public void shouldBeInRoleWhenRoleIsAdded() {
        Role toAdd = DEFAULT_ROLES.iterator().next();

        Role result = service
                .withName(ROLE_NAME)
                .withUniqueId(ROLE_ID)
                .withRoles(DEFAULT_ROLES)
                .clearRoles()
                .addRole(toAdd)
                .build();

        assertTrue("Role should be a sub role: " + toAdd, result.getIncludedRoles().contains(toAdd));
    }


    @Test
    public void shouldNotBeEntitledWhenEntitlementIsRemoved() {
        Entitlement toRemove = DEFAULT_ENTITLEMENTS.iterator().next();

        Role result = service
                .withName(ROLE_NAME)
                .withUniqueId(ROLE_ID)
                .withEntitlements(DEFAULT_ENTITLEMENTS)
                .removeEntitlement(toRemove)
                .build();

        assertFalse("Role should not be entitled: " + toRemove, result.isEntitled(toRemove));
    }


    @Test
    public void shouldBeEntitledWhenEntitlementIsAdded() {
        Entitlement toAdd = DEFAULT_ENTITLEMENTS.iterator().next();

        Role result = service
                .withName(ROLE_NAME)
                .withUniqueId(ROLE_ID)
                .withEntitlements(DEFAULT_ENTITLEMENTS)
                .clearEntitlements()
                .addEntitlement(toAdd)
                .build();

        assertTrue("Role should be entitled: " + toAdd, result.isEntitled(toAdd));
    }


    @Before
    public void setUpService() {
        service = new RoleBuilder();
    }

    @After
    public void tearDownService() {
        service = null;
    }
}
