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

package de.kaiserpfalzedv.paladinsinn.security.model.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.impl.IdentifiableAbstractBuilder;
import de.kaiserpfalzedv.paladinsinn.security.model.Entitlement;
import de.kaiserpfalzedv.paladinsinn.security.model.Role;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class RoleBuilder extends IdentifiableAbstractBuilder<Role> {
    private final HashSet<Role> roles = new HashSet<>();
    private final HashSet<Entitlement> entitlements = new HashSet<>();


    @Override
    public RoleImpl build() {
        setDefaultsIfNeeded();
        validateDuringBuild();

        return new RoleImpl(uniqueId, name, roles, entitlements, getEffectiveEntitlements());
    }

    @Override
    public void validateDuringBuild() {
    }

    @Override
    public boolean validate() {
        return true;
    }

    public RoleBuilder withUniqueId(final UUID uniqueId) {
        return (RoleBuilder) super.withUniqueId(uniqueId);
    }

    public RoleBuilder withName(final String name) {
        return (RoleBuilder) super.withName(name);
    }

    private HashSet<Entitlement> getEffectiveEntitlements() {
        HashSet<Entitlement> roleEntitlements = new HashSet<>(entitlements);
        roles.forEach(r -> roleEntitlements.addAll(r.getEntitlements()));
        return roleEntitlements;
    }

    public RoleBuilder withRole(final Role role) {
        withUniqueId(role.getUniqueId());
        withName(role.getName());
        withRoles(role.getIncludedRoles());
        withEntitlements(role.getEntitlements());

        return this;
    }

    public RoleBuilder withRoles(final Set<? extends Role> roles) {
        this.roles.addAll(roles);

        return this;
    }

    public RoleBuilder withEntitlements(final Set<? extends Entitlement> entitlements) {
        this.entitlements.addAll(entitlements);
        return this;
    }

    public RoleBuilder clearRoles() {
        this.roles.clear();
        return this;
    }

    public <R extends Role> RoleBuilder addRole(R role) {
        this.roles.add(role);
        return this;
    }

    public <R extends Role> RoleBuilder removeRole(R role) {
        this.roles.remove(role);
        return this;
    }

    /**
     * Removes all directly assigned {@link Entitlement}s. The entitlements resulting from {@link Role} memberships will
     * be preserved.
     *
     * @return the builder itself.
     */
    public RoleBuilder clearEntitlements() {
        this.entitlements.clear();
        return this;
    }

    /**
     * Adds a single entitlement to the direct entitlements.
     * @param entitlement the entitlement that should be added.
     * @param <E> every interface deriving from Entitlement should work
     * @return the builder itself
     */
    public <E extends Entitlement> RoleBuilder addEntitlement(E entitlement) {
        this.entitlements.add(entitlement);
        return this;
    }

    /**
     * Removes a single entitlement from the direct entitlements.
     * @param entitlement the entitlement that should be removed.
     * @param <E> every interface deriving from Entitlement should work
     * @return the builder itself
     */
    public <E extends Entitlement> RoleBuilder removeEntitlement(E entitlement) {
        this.entitlements.remove(entitlement);
        return this;
    }
}
