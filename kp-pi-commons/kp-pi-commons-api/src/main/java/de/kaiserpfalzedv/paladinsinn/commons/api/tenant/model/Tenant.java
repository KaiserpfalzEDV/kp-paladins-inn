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

import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.Identifiable;

/**
 * The tenant of a data node in the database.
 *
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-03-20
 */
public interface Tenant extends Identifiable {
    /**
     * The default tenant id for non-multitenant systems.
     */
    UUID DEFAULT_TENANT = UUID.fromString("4dfb9268-7f29-4442-a458-f00e7e620f18");

    String getKey();
}
