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

package de.kaiserpfalzedv.paladinsinn.commons.api.tenant.commands;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.api.BuilderValidationException;
import de.kaiserpfalzedv.paladinsinn.commons.api.service.commands.AbstractCommandBuilderBase;
import de.kaiserpfalzedv.paladinsinn.commons.api.service.commands.CommandCreationRuntimeException;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.Tenant;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.TenantBuilder;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.TenantCommand;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.TenantImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-25
 */
public class TenantCommandBuilder extends AbstractCommandBuilderBase<TenantCommand> {
    private static final Logger LOG = LoggerFactory.getLogger(TenantCommandBuilder.class);

    private Tenant tenant;
    private UUID tenantUniqueId;
    private String key;
    private String name;
    private CommandType type = CommandType.unspecified;

    public TenantCommandBuilder asCreateCommand() {
        type = CommandType.create;
        return this;
    }

    public TenantCommandBuilder asChangeKeyCommand() {
        type = CommandType.changeKey;
        return this;
    }

    public TenantCommandBuilder asChangeNameCommand() {
        type = CommandType.changeName;
        return this;
    }

    public TenantCommandBuilder asDeleteCommand() {
        type = CommandType.delete;
        return this;
    }

    public TenantCommandBuilder withTenant(final Tenant tenant) {
        this.tenant = tenant;
        this.tenantUniqueId = tenant.getUniqueId();
        this.key = tenant.getKey();
        this.name = tenant.getName();
        return this;
    }

    public TenantCommandBuilder withTenantUniqueId(UUID tenantUniqueId) {
        this.tenantUniqueId = tenantUniqueId;
        return this;
    }

    public TenantCommandBuilder withKey(String key) {
        this.key = key;
        return this;
    }

    public TenantCommandBuilder withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public TenantCommand generateImplementation(UUID uniqueId, OffsetDateTime timeStamp) {
        switch (type) {
            case create:
                return createTenantCreateCommand(uniqueId, timeStamp);
            case delete:
                return createTenantDeleteCommand(uniqueId, timeStamp);
            case changeKey:
                return createTenantChangeKeyCommand(uniqueId, timeStamp);
            case changeName:
                return createTenantChangeNameCommand(uniqueId, timeStamp);
            default:
                throw new IllegalStateException("No valid tenant command type given!");
        }
    }

    public void validate() throws BuilderValidationException {
        super.validate();

        HashSet<String> failures = new HashSet<>();

        switch (type) {
            case create:
                if (tenant == null && (tenantUniqueId == null || key == null || name == null)) {
                    failures.add("Can't create a tenant without tenant information!");
                }
                break;
            case delete:
                if (tenant == null && tenantUniqueId == null) {
                    failures.add("Can't delete a tenant without knowing its unique id!");
                }
                break;
            case changeKey:
                if (tenant == null && tenantUniqueId == null) {
                    failures.add("Can't change the key of a tenant without knowing its unique id!");
                }
                if (key == null || key.isEmpty()) {
                    failures.add("Can't change the key of a tenant without a new key!");
                }
                break;
            case changeName:
                if (tenant == null && tenantUniqueId == null) {
                    failures.add("Can't change the name of a tenant without knowing its unique id!");
                }
                if (name == null || name.isEmpty()) {
                    failures.add("Can't change the name of a tenant without a new key!");
                }
                break;
            default:
                failures.add("Can't create a command when not knowing what to do!");
        }

        if (failures.size() >= 1) {
            throw new BuilderValidationException(TenantCommand.class, failures);
        }
    }

    private TenantCreateCommand createTenantCreateCommand(UUID uniqueId, OffsetDateTime timeStamp) {
        try {
            return new TenantCreateCommand(
                    uniqueId,
                    timeStamp,
                    (TenantImpl) new TenantBuilder()
                            .withUniqueId(tenantUniqueId)
                            .withKey(key)
                            .withName(name)
                            .build()
            );
        } catch (BuilderValidationException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new CommandCreationRuntimeException("Can't build new TenantImpl Object", e);
        }
    }

    private TenantDeleteCommand createTenantDeleteCommand(final UUID uniqueId, final OffsetDateTime timeStamp) {
        return new TenantDeleteCommand(uniqueId, timeStamp, tenantUniqueId);
    }

    private TenantChangeKeyCommand createTenantChangeKeyCommand(final UUID uniqueId, final OffsetDateTime timeStamp) {
        return new TenantChangeKeyCommand(uniqueId, timeStamp, tenantUniqueId, key);
    }

    private TenantChangeNameCommand createTenantChangeNameCommand(final UUID uniqueId, final OffsetDateTime timeStamp) {
        return new TenantChangeNameCommand(uniqueId, timeStamp, tenantUniqueId, key);
    }

    private enum CommandType {
        unspecified,
        create,
        changeKey,
        changeName,
        delete;
    }
}
