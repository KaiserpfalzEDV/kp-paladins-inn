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

import java.util.Scanner;
import java.util.UUID;

import javax.inject.Inject;

import de.kaiserpfalzedv.paladinsinn.commons.BuilderValidationException;
import de.kaiserpfalzedv.paladinsinn.commons.persistence.impl.AbstractCSVDataReader;
import de.kaiserpfalzedv.paladinsinn.commons.service.CSV;
import de.kaiserpfalzedv.paladinsinn.commons.service.SingleTenant;
import de.kaiserpfalzedv.paladinsinn.commons.tenant.model.Tenant;
import de.kaiserpfalzedv.paladinsinn.commons.tenant.model.impl.TenantBuilder;
import de.kaiserpfalzedv.paladinsinn.commons.tenant.store.TenantCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-18
 */
@SingleTenant
@CSV
public class TenantCSVReader extends AbstractCSVDataReader<Tenant> {
    private static final Logger LOG = LoggerFactory.getLogger(TenantCSVReader.class);


    @Inject
    public TenantCSVReader(final TenantCrudService crudService) {
        super(crudService);
    }

    public Tenant readSingleLine(String line) throws BuilderValidationException {
        LOG.trace("Reading line: {}", line);

        Scanner scanner = new Scanner(line);
        scanner.useDelimiter(";");

        String uniqueId = scanner.hasNext() ? scanner.next() : UUID.randomUUID().toString();

        if (!scanner.hasNext()) {
            throw new IllegalArgumentException("Need to give a key for tenant: " + uniqueId);
        }
        String tenantKey = scanner.next();

        if (!scanner.hasNext()) {
            throw new IllegalArgumentException("Need to give a nme for the tenant: " + uniqueId);
        }
        String tenantName = scanner.next();

        Tenant result = new TenantBuilder()
                .withUniqueId(UUID.fromString(uniqueId))
                .withKey(tenantKey)
                .withName(tenantName)
                .build();

        LOG.debug("Tenant read: {} -> {}", line, result);
        return result;
    }
}
