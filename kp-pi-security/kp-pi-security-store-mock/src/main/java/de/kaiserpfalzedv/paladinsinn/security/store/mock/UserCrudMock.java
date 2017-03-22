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

package de.kaiserpfalzedv.paladinsinn.security.store.mock;

import javax.inject.Inject;

import de.kaiserpfalzedv.paladinsinn.commons.persistence.impl.AbstractCrudMock;
import de.kaiserpfalzedv.paladinsinn.commons.service.MockService;
import de.kaiserpfalzedv.paladinsinn.commons.service.SingleTenant;
import de.kaiserpfalzedv.paladinsinn.commons.tenant.model.Tenant;
import de.kaiserpfalzedv.paladinsinn.commons.tenant.model.impl.DefaultTenant;
import de.kaiserpfalzedv.paladinsinn.security.model.User;
import de.kaiserpfalzedv.paladinsinn.security.store.UserCrudService;
import de.kaiserpfalzedv.paladinsinn.security.store.UserMultitenantCrudService;

/**
 * The tenant-less version of the mock service uses the multi-tenant {@link UserMultitenantCrudMock} with the
 * {@link DefaultTenant} as tenant.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-19
 */
@MockService
@SingleTenant
public class UserCrudMock extends AbstractCrudMock<User> implements UserCrudService {

    /**
     * @param defaultTenant         The tenant to use when calling the multi tenant service.
     * @param userTenantCrudService The multi tenant user CRUD service used as backend to this single tenant
     *                              implementation.
     */
    @Inject
    public UserCrudMock(
            final Tenant defaultTenant,
            final UserMultitenantCrudService userTenantCrudService
    ) {
        super(defaultTenant, userTenantCrudService);
    }
}
