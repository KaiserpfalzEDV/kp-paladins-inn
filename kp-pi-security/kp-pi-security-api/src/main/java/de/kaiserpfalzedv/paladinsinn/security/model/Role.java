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

package de.kaiserpfalzedv.paladinsinn.security.model;

import java.security.Principal;
import java.util.Set;

import de.kaiserpfalzedv.paladinsinn.commons.persistence.Identifiable;

/**
 * The basic role within this system. It can have entitlements and other roles attached.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public interface Role extends Identifiable {
    /**
     * @param role the role to be tested.
     *
     * @return TRUE if the role includes the requested role.
     */
    boolean isInRole(Role role);

    /**
     * @param entitlement the entitlement to be tested.
     * @return TRUE if the entitlement is included in this role.
     */
    boolean isEntitled(Principal entitlement);


    /**
     * @return a set of all directly included roles. These roles may contain other additional roles.
     */
    Set<? extends Role> getIncludedRoles();

    /**
     * @return a set of all directly attached entitlements. The entitlemens of included roles are not given back.
     */
    Set<? extends Entitlement> getEntitlements();
}
