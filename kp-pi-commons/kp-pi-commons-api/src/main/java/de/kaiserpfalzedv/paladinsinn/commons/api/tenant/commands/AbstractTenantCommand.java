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
import java.util.Optional;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import de.kaiserpfalzedv.paladinsinn.commons.api.service.commands.AbstractCommand;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.Tenant;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.TenantCommand;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.TenantImpl;

/**
 * The base command for all tenant commands.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-25
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractTenantCommand extends AbstractCommand<Tenant> implements TenantCommand {
    private static final long serialVersionUID = -8987934519321441084L;

    private TenantImpl tenant;
    private UUID tenantUniqueId;
    private String tenantKey;
    private String tenantName;

    @SuppressWarnings({"deprecation", "DeprecatedIsStillUsed"})
    @Deprecated
    public AbstractTenantCommand() {}

    AbstractTenantCommand(
            final UUID commandUniqueId,
            final String commandName,
            final OffsetDateTime commandTimeStamp
    ) {
        super(commandUniqueId, commandName, commandTimeStamp);
    }

    @Override
    public Optional<Tenant> getTenant() {
        return Optional.ofNullable(tenant);
    }

    @SuppressWarnings({"deprecation", "DeprecatedIsStillUsed"})
    @Deprecated
    public void setTenant(final TenantImpl tenant) {
        this.tenant = tenant;
        setTenantUniqueId(tenant.getUniqueId());
        setTenantKey(tenant.getKey());
        setTenantName(tenant.getName());
    }

    @Override
    public Optional<UUID> getTenantUniqueId() {
        return Optional.ofNullable(tenantUniqueId);
    }

    @SuppressWarnings({"DeprecatedIsStillUsed"})
    @Deprecated
    public void setTenantUniqueId(final UUID tenantUniqueId) {
        this.tenantUniqueId = tenantUniqueId;
    }

    @Override
    public Optional<String> getTenantKey() {
        return Optional.ofNullable(tenantKey);
    }

    @SuppressWarnings({"DeprecatedIsStillUsed"})
    @Deprecated
    public void setTenantKey(final String tenantKey) {
        this.tenantKey = tenantKey;
    }

    @Override
    public Optional<String> getTenantName() {
        return Optional.ofNullable(tenantName);
    }

    @SuppressWarnings({"DeprecatedIsStillUsed"})
    @Deprecated
    public void setTenantName(final String tenantName) {
        this.tenantName = tenantName;
    }
}
