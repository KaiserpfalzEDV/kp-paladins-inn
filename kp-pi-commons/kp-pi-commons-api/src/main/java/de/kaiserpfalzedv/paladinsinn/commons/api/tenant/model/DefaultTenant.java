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

package de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model;

import java.util.UUID;

/**
 * Default tenant if the system is not multi-tenant.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-19
 */
public class DefaultTenant implements Tenant {
    // singleton pattern. YEAH!
    public static final Tenant INSTANCE = new DefaultTenant();
    private static final long serialVersionUID = 2387082762521069393L;
    private static final UUID uniqueId = UUID.fromString("736eb2d8-dacf-48bd-879a-100000000000");

    private DefaultTenant() {}

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public String getName() {
        return "Default Tenant";
    }

    @Override
    public String getKey() {
        return "DEFAULT";
    }
}
