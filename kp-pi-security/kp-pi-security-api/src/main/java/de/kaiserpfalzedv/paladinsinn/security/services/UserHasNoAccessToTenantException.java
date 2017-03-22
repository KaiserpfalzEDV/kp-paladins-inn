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

package de.kaiserpfalzedv.paladinsinn.security.services;

import de.kaiserpfalzedv.paladinsinn.commons.tenant.model.Tenant;

/**
 * This exception is thrown if the user has no access to the tenant given.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-14
 */
public class UserHasNoAccessToTenantException extends UserIsNotEntitledException {
    private static final long serialVersionUID = -5693671131425424047L;

    /**
     * @param userId The user id of the user without access.
     * @param tenant The tenant the user has no access to.
     */
    public UserHasNoAccessToTenantException(final String userId, final Tenant tenant) {
        super("%s is not entitled for tenant %s.", userId, tenant.getName());
    }
}
