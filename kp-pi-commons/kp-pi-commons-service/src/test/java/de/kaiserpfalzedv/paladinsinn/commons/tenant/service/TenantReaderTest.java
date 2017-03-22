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

package de.kaiserpfalzedv.paladinsinn.commons.tenant.service;

import java.io.FileNotFoundException;
import java.util.Set;

import de.kaiserpfalzedv.paladinsinn.commons.tenant.model.Tenant;
import de.kaiserpfalzedv.paladinsinn.commons.tenant.store.TenantCrudService;
import de.kaiserpfalzedv.paladinsinn.commons.tenant.store.mock.TenantCrudMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-15
 */
public class TenantReaderTest {
    private static final Logger LOG = LoggerFactory.getLogger(TenantReaderTest.class);

    private TenantCrudService crudService;
    private TenantCSVReader service;


    @Test
    public void shouldReadFiveTenantsFromFile() throws FileNotFoundException {
        service.read("./target/test-classes/tenants.csv");

        Set<Tenant> result = crudService.retrieve();
        LOG.debug("Read tenants: {}", result);

        assertEquals("Wrong number of tenants read", 5, result.size());
    }


    @Before
    public void setUpService() {
        crudService = new TenantCrudMock();
        service = new TenantCSVReader(crudService);
    }

    @After
    public void tearDownService() {
        service = null;
    }
}
