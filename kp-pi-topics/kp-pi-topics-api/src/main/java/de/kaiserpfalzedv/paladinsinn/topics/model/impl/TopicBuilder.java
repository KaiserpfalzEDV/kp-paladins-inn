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

package de.kaiserpfalzedv.paladinsinn.topics.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.Builder;
import de.kaiserpfalzedv.paladinsinn.commons.BuilderValidationException;
import de.kaiserpfalzedv.paladinsinn.commons.tenant.model.Tenant;
import de.kaiserpfalzedv.paladinsinn.security.model.User;
import de.kaiserpfalzedv.paladinsinn.topics.model.Topic;

/**
 * Builds a new topic.
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-03-20
 */
public class TopicBuilder implements Builder<Topic> {
    private final ArrayList<Topic> children = new ArrayList<>();
    private UUID identifier;
    private UUID tenant;
    private UUID maintainer;
    private String name;
    private Topic parent;

    public Topic build() throws BuilderValidationException {
        validate();

        return new TopicImpl(
                identifier, tenant, maintainer,
                name, parent, children
        );
    }

    public void validate() throws BuilderValidationException {
        HashSet<String> failures = new HashSet<>(6);

        if (identifier == null) {
            identifier = UUID.randomUUID();
        }

        if (name == null) {
            failures.add("A topic needs a name. No name given!");
        }

        if (maintainer == null) {
            failures.add("You need a maintainer for this topic!");
        }

        if (parent == null) {
            failures.add("You need a parent topic!");
        }

        if (tenant == null && parent != null) {
            tenant = parent.getTenant();
        }

        if (tenant == null) {
            failures.add("You need a tenant for this topic!");
        }

        if (failures.size() != 0) {
            throw new BuilderValidationException(TopicImpl.class, failures);
        }
    }

    public TopicBuilder withTopic(final Topic topic) {
        setIdentifier(topic.getUniqueId());
        setTenant(topic.getTenant());
        setMaintainer(topic.getMaintainer());
        setName(topic.getName());
        setParent(topic.getParent());
        setChildren(topic.getChildren());

        return this;
    }


    public TopicBuilder setIdentifier(final UUID identifier) {
        this.identifier = identifier;
        return this;
    }

    public TopicBuilder setTenant(final UUID tenant) {
        this.tenant = tenant;
        return this;
    }

    public TopicBuilder setMaintainer(final UUID maintainer) {
        this.maintainer = maintainer;
        return this;
    }

    public TopicBuilder setName(final String name) {
        this.name = name;
        return this;
    }

    public TopicBuilder setParent(final Topic parent) {
        this.parent = parent;
        return this;
    }

    public TopicBuilder setChildren(final Collection<Topic> children) {
        if (children != null) {
            this.children.addAll(children);
        }
        return this;
    }

    public TopicBuilder setTenant(final Tenant tenant) {
        this.tenant = tenant.getUniqueId();
        return this;
    }

    public TopicBuilder setMaintainer(final User maintainer) {
        this.maintainer = maintainer.getUniqueId();
        return this;
    }

    public TopicBuilder unsetParent() {
        this.parent = null;
        return this;
    }
}
