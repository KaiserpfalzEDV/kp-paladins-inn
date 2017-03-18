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

package de.kaiserpfalzedv.paladinsinn.security.access.services;

import java.util.Set;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.paging.Page;
import de.kaiserpfalzedv.paladinsinn.commons.paging.PageRequest;
import de.kaiserpfalzedv.paladinsinn.security.access.model.Role;
import de.kaiserpfalzedv.paladinsinn.security.tenant.model.Tenant;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-18
 */
public interface TenantRoleService {
    Role create(Tenant tenant, Role role);

    Set<Role> retrieve(Tenant tenant);

    Page<Role> retrieve(Tenant tenant, PageRequest pageRequest);

    Role retrieve(Tenant tenant, UUID uniqueId);

    Role retrieve(Tenant tenant, String roleName);

    Role update(Tenant tenant, Role role);

    void delete(Tenant tenant, Role role);

    void delete(Tenant tenant, UUID uniqueId);

    void delete(Tenant tenant, String roleName);
}
