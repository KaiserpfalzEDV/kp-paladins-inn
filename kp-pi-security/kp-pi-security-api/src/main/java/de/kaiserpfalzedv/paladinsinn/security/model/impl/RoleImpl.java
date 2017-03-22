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

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.impl.IdentifiableAbstractImpl;
import de.kaiserpfalzedv.paladinsinn.security.model.Entitlement;
import de.kaiserpfalzedv.paladinsinn.security.model.Role;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class RoleImpl extends IdentifiableAbstractImpl implements Role {
    private static final long serialVersionUID = -6332463063835726746L;
    private final HashSet<Role> roles = new HashSet<>();
    private final HashSet<Entitlement> directEntitlements = new HashSet<>();
    private final HashSet<Entitlement> entitlements = new HashSet<>();


    RoleImpl(
            final UUID uniqueId,
            final String name,
            final Set<? extends Role> roles,
            final Set<? extends Entitlement> directEntitlements,
            final Set<? extends Entitlement> entitlements
    ) {
        super(uniqueId, name);

        this.roles.addAll(roles);
        this.directEntitlements.addAll(directEntitlements);
        this.entitlements.addAll(entitlements);
    }


    public boolean isInRole(Role role) {
        boolean result = equals(role);

        Iterator<Role> roleIterator = roles.iterator();
        while (!result && roleIterator.hasNext()) {
            result = roleIterator.next().isInRole(role);
        }

        return result;
    }


    @Override
    public boolean isEntitled(Principal entitlement) {
        for (Principal e : entitlements) {
            if (e.equals(entitlement))
                return true;
        }

        return false;
    }

    @Override
    public Set<? extends Role> getIncludedRoles() {
        return Collections.unmodifiableSet(roles);
    }

    @Override
    public Set<? extends Entitlement> getEntitlements() {
        return Collections.unmodifiableSet(directEntitlements);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder()
                .append(getClass().getSimpleName()).append('@').append(System.identityHashCode(this)).append('{')
                .append(getUniqueId()).append(", ").append(getName());

        if (roles.size() >= 1) {
            result.append(", roles=").append(roles.size());
        }

        if (directEntitlements.size() >= 1) {
            result
                    .append(", entitlements=")
                    .append(directEntitlements.size())
                    .append("/")
                    .append(entitlements.size());
        }

        return result.append('}').toString();
    }
}
