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

package de.kaiserpfalzedv.paladinsinn.commons.store.jpa.model;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.api.Builder;
import de.kaiserpfalzedv.paladinsinn.commons.api.BuilderValidationException;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.Tenant;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.TenantImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-18
 */
public class TenantJPABuilder implements Builder<TenantJPA> {
    private static final Logger LOG = LoggerFactory.getLogger(TenantJPABuilder.class);

    private UUID uniqueId;
    private String key;
    private String name;
    private ZonedDateTime created;
    private ZonedDateTime modified;


    @Override
    public TenantJPA build() throws BuilderValidationException {
        calculateDefaults();
        validate();

        TenantJPA result = new TenantJPA();
        result.setUniqueId(uniqueId);
        result.setName(name);
        result.setKey(key);
        result.setCreated(created);
        result.setChanged(modified);

        return result;
    }

    private void calculateDefaults() {
        if (uniqueId == null) {
            uniqueId = UUID.randomUUID();
        }

        if (key == null && name != null) {
            key = name.substring(0, 5).toUpperCase();
        }
    }

    @Override
    public void validate() throws BuilderValidationException {
        HashSet<String> failures = new HashSet<>();

        if (name == null || name.isEmpty() || "".equals(name)) {
            failures.add("A tenant needs a name!");
        }

        if (failures.size() > 0) {
            throw new BuilderValidationException(TenantImpl.class, failures);
        }
    }


    public TenantJPABuilder withTenant(final Tenant tenant) {
        withUniqueId(tenant.getUniqueId());
        withKey(tenant.getKey());
        withName(tenant.getName());

        if (tenant instanceof TenantJPA) {
            withCreated(((TenantJPA) tenant).getCreated());
            withModified(((TenantJPA) tenant).getChanged());
        }
        return this;
    }


    public TenantJPABuilder withUniqueId(final UUID uniqueId) {
        this.uniqueId = uniqueId;
        return this;
    }

    public TenantJPABuilder withKey(final String key) {
        this.key = key;
        return this;
    }

    public TenantJPABuilder withName(final String name) {
        this.name = name;
        return this;
    }

    public TenantJPABuilder withCreated(final ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public TenantJPABuilder withModified(final ZonedDateTime modified) {
        this.modified = modified;
        return this;
    }
}
