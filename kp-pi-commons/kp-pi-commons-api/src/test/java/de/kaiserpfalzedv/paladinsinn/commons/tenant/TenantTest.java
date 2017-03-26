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

package de.kaiserpfalzedv.paladinsinn.commons.tenant;

import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.api.BuilderValidationException;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.Tenant;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.TenantBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-03-20
 */
public class TenantTest {
    private static final UUID TENANT_UNIQUE_ID = UUID.randomUUID();
    private static final String TENANT_NAME = "tenant";
    private static final String TENANT_KEY = "TENANT";

    private TenantBuilder service;


    @Test
    public void shouldCreateANewTenantWhenANameIsGiven() throws BuilderValidationException {
        Tenant result = service.withName(TENANT_NAME).build();

        assertEquals("Name should match!", TENANT_NAME, result.getName());
    }

    @Test(expected = BuilderValidationException.class)
    public void shouldNotCreateANewTenantWhenNoNameIsGiven() throws BuilderValidationException {
        service.build();
    }

    @Test
    public void shouldCreateANewTenantWhenANameAndUniqueIdIsGiven() throws BuilderValidationException {
        Tenant result = service.withUniqueId(TENANT_UNIQUE_ID).withName(TENANT_NAME).build();

        assertEquals("Unique ID should match!", TENANT_UNIQUE_ID, result.getUniqueId());
        assertEquals("Name should match!", TENANT_NAME, result.getName());
    }


    @Test
    public void shouldCreateANewTenantWhenANameAndKeyIsGiven() throws BuilderValidationException {
        Tenant result = service.withUniqueId(TENANT_UNIQUE_ID).withName(TENANT_NAME).withKey(TENANT_KEY).build();

        assertEquals("Unique ID should match!", TENANT_UNIQUE_ID, result.getUniqueId());
        assertEquals("Name should match!", TENANT_NAME, result.getName());
        assertEquals("Key should match!", TENANT_KEY, result.getKey());
    }


    @Before
    public void setUp() throws Exception {
        service = new TenantBuilder();
    }
}
