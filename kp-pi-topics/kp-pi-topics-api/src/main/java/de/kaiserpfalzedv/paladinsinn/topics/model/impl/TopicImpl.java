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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.api.BuilderValidationException;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.Page;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.PageBuilder;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.PageRequest;
import de.kaiserpfalzedv.paladinsinn.topics.model.Topic;

/**
 * The impl side read-only implementation of a Topic.
 *
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-03-20
 */
public class TopicImpl implements Topic {
    private static final long serialVersionUID = -6934794520130256051L;
    
    private final ArrayList<Topic> children = new ArrayList<>();
    private UUID tenant;
    private UUID maintainer;
    private UUID identity;
    private String name;
    private Topic parent;


    TopicImpl(
            final UUID identity,
            final UUID tenant,
            final UUID maintainer,
            final String name,
            final Topic parent,
            final List<Topic> children
    ) {
        this.identity = identity;
        this.tenant = tenant;
        this.maintainer = maintainer;

        this.name = name;

        this.parent = parent;
        this.children.addAll(children);
    }


    @Override
    public UUID getUniqueId() {
        return identity;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getTenant() {
        return tenant;
    }

    @Override
    public UUID getMaintainer() {
        return maintainer;
    }

    @Override
    public Topic getParent() {
        return parent;
    }

    @Override
    public List<Topic> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public Page<Topic> getChildren(PageRequest pageRequest) {
        try {
            return new PageBuilder<Topic>()
                    .withPage(getChildren(), pageRequest.getPageNumber(), pageRequest.getPageSize())
                    .build();
        } catch (BuilderValidationException e) {
            throw new IllegalStateException("Can't build the page containing the children of this topic!", e);
        }
    }
}
