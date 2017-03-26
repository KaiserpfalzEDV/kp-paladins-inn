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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.IdentifiableAbstractImpl;
import de.kaiserpfalzedv.paladinsinn.commons.api.person.Email;
import de.kaiserpfalzedv.paladinsinn.security.api.services.PasswordFailureException;
import de.kaiserpfalzedv.paladinsinn.security.api.services.UserIsLockedException;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
class UserImpl extends IdentifiableAbstractImpl implements User {
    private static final long serialVersionUID = 7375303558202040469L;
    private final HashSet<Role> roles = new HashSet<>();
    private Persona person;
    private Email emailAddress;
    private String password;
    private boolean locked = false;


    UserImpl(
            final UUID uniqueId,
            final String name,
            final Persona person,
            final Email emailAddress,
            final String password,
            final boolean locked,
            final Collection<Role> roles
    ) {
        super(uniqueId, name);
        
        this.person = person;
        this.emailAddress = emailAddress;
        this.password = password;
        this.locked = locked;

        if (roles != null) {
            this.roles.addAll(roles);
        }
    }
    

    public Persona getPerson() {
        return person;
    }

    @Override
    public Locale getLocale() {
        if (person == null) {
            return Locale.getDefault();
        }

        return person.getLocale();
    }


    @Override
    public Email getEmailAddress() {
        return emailAddress;
    }


    @Override
    public void login(final String passwordToCheck) throws PasswordFailureException, UserIsLockedException {
        if (password == null || ! password.equals(passwordToCheck)) {
            throw new PasswordFailureException(getName());
        }

        if (locked) {
            throw new UserIsLockedException(getName());
        }
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public Set<Role> getRoles() {
        HashSet<Role> result = new HashSet<>();


        roles.forEach(r -> addRole(result, r));

        return Collections.unmodifiableSet(result);
    }

    @Override
    public boolean isInRole(Role role) {
        return getRoles().contains(role);
    }

    @Override
    public Set<Entitlement> getEntitlements() {
        HashSet<Entitlement> result = new HashSet<>();

        getRoles().forEach(r -> result.addAll(r.getEntitlements()));

        return result;
    }

    @Override
    public boolean isEntitled(Entitlement entitlement) {
        return getEntitlements().contains(entitlement);
    }

    /**
     * This is an internal method needed for the copying builder.
     *
     * @return the password of this user.
     */
    String getPassword() {
        return password;
    }

    private void addRole(HashSet<Role> roles, Role role) {
        if (roles.contains(role))
            return;

        roles.add(role);

        role.getIncludedRoles().forEach(r -> addRole(roles, role));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder()
                .append(getClass().getSimpleName()).append('@').append(System.identityHashCode(this)).append('{')
                .append(getUniqueId()).append(", ").append(getName());

        if (locked) {
            result.append(", locked");
        }

        if (roles.size() >= 1) {
            result.append(", ").append(roles.size()).append(" roles");
        }

        return result.append('}').toString();
    }


}
