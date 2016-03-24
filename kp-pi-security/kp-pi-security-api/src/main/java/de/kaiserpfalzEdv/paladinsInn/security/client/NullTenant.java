/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.paladinsinn.security.client;

import de.kaiserpfalzedv.paladinsinn.security.Tenant;
import de.kaiserpfalzedv.paladinsinn.security.User;

import java.util.UUID;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-03-24
 */
public class NullTenant implements Tenant {
    private static final UUID identifier = UUID.fromString("4dfb9268-7f29-4442-a458-f00e7e620f18");

    @Override
    public User getMaintainer() {
        return new NullUser();
    }

    @Override
    public UUID getIdentifier() {
        return identifier;
    }

    @Override
    public String getName() {
        return "no tenant";
    }
}
