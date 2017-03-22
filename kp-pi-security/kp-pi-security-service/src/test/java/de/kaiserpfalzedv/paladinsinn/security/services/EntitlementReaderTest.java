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

package de.kaiserpfalzedv.paladinsinn.security.services;

import java.io.FileNotFoundException;
import java.util.Set;

import de.kaiserpfalzedv.paladinsinn.commons.tenant.model.impl.DefaultTenant;
import de.kaiserpfalzedv.paladinsinn.security.model.Entitlement;
import de.kaiserpfalzedv.paladinsinn.security.store.EntitlementCSVReader;
import de.kaiserpfalzedv.paladinsinn.security.store.EntitlementCrudService;
import de.kaiserpfalzedv.paladinsinn.security.store.mock.EntitlementCrudMock;
import de.kaiserpfalzedv.paladinsinn.security.store.mock.EntitlementMultitenantCrudMock;
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
public class EntitlementReaderTest {
    private static final Logger LOG = LoggerFactory.getLogger(EntitlementReaderTest.class);

    private EntitlementCrudService crudService;
    private EntitlementCSVReader readerService;

    @Test
    public void shouldReadTwoUsersFromFile() throws FileNotFoundException {
        readerService.read("./target/test-classes/entitlements.csv");

        Set<Entitlement> result = crudService.retrieve();
        LOG.debug("Read entitlements: {}", result);

        assertEquals("Wrong number of users read", 20, result.size());
    }


    @Before
    public void setUpService() {
        crudService = new EntitlementCrudMock(DefaultTenant.INSTANCE, new EntitlementMultitenantCrudMock());
        readerService = new EntitlementCSVReader(crudService);
    }
}
