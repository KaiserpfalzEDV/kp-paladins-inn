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

package de.kaiserpfalzedv.paladinsinn.security.api.model;

import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.IdentifiableAbstractBuilder;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class EntitlementBuilder extends IdentifiableAbstractBuilder<Entitlement> {

    @Override
    public EntitlementImpl build() {
        setDefaultsIfNeeded();
        validate();

        return new EntitlementImpl(uniqueId, name);
    }


    @Override
    public void validateDuringBuild() {
    }

    @Override
    public boolean validate() {
        return true;
    }

    public EntitlementBuilder withUniqueId(final UUID uniqueId) {
        return (EntitlementBuilder) super.withUniqueId(uniqueId);
    }

    public EntitlementBuilder withName(final String name) {
        return (EntitlementBuilder) super.withName(name);
    }

    public EntitlementBuilder withEntitlement(final Entitlement data) {
        withUniqueId(data.getUniqueId());
        withName(data.getName());

        return this;
    }
}
