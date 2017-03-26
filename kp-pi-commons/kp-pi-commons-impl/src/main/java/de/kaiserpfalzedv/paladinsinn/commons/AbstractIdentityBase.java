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

package de.kaiserpfalzedv.paladinsinn.commons;

import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.Identifiable;

/**
 * A base implementation for all identifable classes (including writable interface).
 *
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-03-20
 */
public abstract class AbstractIdentityBase implements Identifiable {
    private static final long serialVersionUID = -8962217148724162694L;
    
    private UUID id;
    private String name;

    @SuppressWarnings("WeakerAccess")
    public AbstractIdentityBase(final String name) {
        this(name, UUID.randomUUID());

    }

    @SuppressWarnings("WeakerAccess")
    public AbstractIdentityBase(final String name, final UUID id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public UUID getUniqueId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
