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

package de.kaiserpfalzedv.paladinsinn.security.impl;

import java.util.Objects;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.security.Identifiable;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public abstract class IdentifiableAbstractImpl implements Identifiable {
    private static final long serialVersionUID = -1960897761625553726L;

    
    private UUID uniqueId;
    private String name;


    public IdentifiableAbstractImpl(final UUID uniqueId, final String name) {
        this.uniqueId = uniqueId;
        this.name = name;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Identifiable)) return false;
        Identifiable that = (Identifiable) o;
        return Objects.equals(getUniqueId(), that.getUniqueId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUniqueId());
    }


    @Override
    public String toString() {
        return new StringBuilder()
                .append(getClass().getSimpleName()).append('@').append(System.identityHashCode(this)).append('{')
                .append(getUniqueId()).append(", ").append(getName())
                .append('}').toString();
    }
}
