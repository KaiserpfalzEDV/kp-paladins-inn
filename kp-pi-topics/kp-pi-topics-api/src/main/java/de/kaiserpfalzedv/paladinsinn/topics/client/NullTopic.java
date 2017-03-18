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

package de.kaiserpfalzedv.paladinsinn.topics.client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.security.access.model.impl.NullUser;
import de.kaiserpfalzedv.paladinsinn.security.tenant.model.impl.NullTenant;
import de.kaiserpfalzedv.paladinsinn.topics.Topic;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-03-24
 */
public class NullTopic implements Topic {
    private static final UUID identifier = UUID.fromString("5870cba5-c4c6-4cfb-b384-c18ec5d5091e");

    @Override
    public Topic getParent() {
        return new NullTopic();
    }

    @Override
    public List<Topic> getChildren() {
        return new ArrayList<>();
    }

    @Override
    public UUID getUniqueId() {
        return identifier;
    }

    @Override
    public UUID getMaintainer() {
        return new NullUser().getUniqueId();
    }

    @Override
    public String getName() {
        return "no topic";
    }

    @Override
    public UUID getTenant() {
        return new NullTenant().getUniqueId();
    }
}
