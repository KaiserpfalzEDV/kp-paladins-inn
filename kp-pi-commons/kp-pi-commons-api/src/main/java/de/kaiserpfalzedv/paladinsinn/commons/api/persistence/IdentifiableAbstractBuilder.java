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

package de.kaiserpfalzedv.paladinsinn.commons.api.persistence;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public abstract class IdentifiableAbstractBuilder<T extends Identifiable> {
    protected final ArrayList<String> validationErrors = new ArrayList<>(5);
    protected UUID uniqueId;
    protected String name;

    public abstract T build();

    public abstract void validateDuringBuild();

    protected void setDefaultsIfNeeded() {
        if (uniqueId == null) {
            uniqueId = UUID.randomUUID();
        }

        if (name == null || name.isEmpty()) {
            name = uniqueId.toString();
        }
    }

    public abstract boolean validate();

    public IdentifiableAbstractBuilder<T> withUniqueId(final UUID uniqueId) {
        this.uniqueId = uniqueId;
        return this;
    }

    public IdentifiableAbstractBuilder<T> withName(final String name) {
        this.name = name;
        return this;
    }
}
