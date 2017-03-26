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

package de.kaiserpfalzedv.paladinsinn.security.store;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.AbstractCSVDataReader;
import de.kaiserpfalzedv.paladinsinn.commons.api.service.CSV;
import de.kaiserpfalzedv.paladinsinn.commons.api.service.SingleTenant;
import de.kaiserpfalzedv.paladinsinn.security.api.model.Entitlement;
import de.kaiserpfalzedv.paladinsinn.security.api.model.Role;
import de.kaiserpfalzedv.paladinsinn.security.api.model.RoleBuilder;
import de.kaiserpfalzedv.paladinsinn.security.api.store.EntitlementCrudService;
import de.kaiserpfalzedv.paladinsinn.security.api.store.RoleCrudService;
import de.kaiserpfalzedv.paladinsinn.security.api.store.RoleDataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-14
 */
@CSV
@SingleTenant
public class RoleCSVReader extends AbstractCSVDataReader<Role> implements RoleDataReader {
    private static final Logger LOG = LoggerFactory.getLogger(RoleCSVReader.class);

    private EntitlementCrudService entitlementCrudService;

    @Inject
    public RoleCSVReader(
            final RoleCrudService crudService,
            final EntitlementCrudService entitlementCrudService
    ) {
        super(crudService);

        this.entitlementCrudService = entitlementCrudService;
    }


    @Override
    public Role readSingleLine(final String line) {
        LOG.trace("Reading line: {}", line);

        Scanner scanner = new Scanner(line);
        scanner.useDelimiter(";");

        UUID uniqueId = scanner.hasNext() ? UUID.fromString(scanner.next()) : UUID.randomUUID();

        if (!scanner.hasNext()) {
            throw new IllegalArgumentException("Need to give an user id for user: " + uniqueId);
        }
        String uniqueName = scanner.next();

        Set<Role> roles = null;
        if (scanner.hasNext()) {
            roles = readRoles(scanner.next());
        }

        Set<Entitlement> entitlements = null;
        if (scanner.hasNext()) {
            entitlements = readEntitlements(scanner.next());
        }

        scanner.close();


        Role result = new RoleBuilder()
                .withUniqueId(uniqueId)
                .withName(uniqueName)
                .withRoles(roles)
                .withEntitlements(entitlements)
                .build();

        LOG.debug("Role read: {} -> {}", line, result);
        return result;
    }

    private Set<Role> readRoles(final String data) {
        HashSet<Role> result = new HashSet<>();

        Scanner scanner = new Scanner(data);
        scanner.useDelimiter(",");

        while (scanner.hasNext()) {
            UUID roleId = UUID.fromString(scanner.next());
            crudService.retrieve(roleId).ifPresent(result::add);
        }

        scanner.close();
        return result;
    }

    private Set<Entitlement> readEntitlements(final String data) {
        HashSet<Entitlement> result = new HashSet<>();

        Scanner scanner = new Scanner(data);
        scanner.useDelimiter(",");

        while (scanner.hasNext()) {
            UUID entitlementId = UUID.fromString(scanner.next());
            entitlementCrudService.retrieve(entitlementId).ifPresent(result::add);
        }

        scanner.close();
        return result;
    }
}
