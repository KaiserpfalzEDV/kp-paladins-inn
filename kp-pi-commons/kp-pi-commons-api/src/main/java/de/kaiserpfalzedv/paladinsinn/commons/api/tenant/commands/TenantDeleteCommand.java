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
import java.util.UUID;

import javax.xml.bind.annotation.XmlType;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-25
 */
@XmlType(name = "tenantdelete")
public class TenantDeleteCommand extends AbstractTenantCommand {
    static final String COMMAND_NAME = TenantDeleteCommand.class.getSimpleName();
    private static final long serialVersionUID = -5213162981893372537L;

    @SuppressWarnings("deprecation")
    @Deprecated
    public TenantDeleteCommand() {}

    @SuppressWarnings("deprecation")
    public TenantDeleteCommand(
            final UUID commandUniqueId,
            final OffsetDateTime commandTimeStamp,
            final UUID tenant
    ) {
        super(commandUniqueId, COMMAND_NAME, commandTimeStamp);

        setTenantUniqueId(tenant);
    }
}
