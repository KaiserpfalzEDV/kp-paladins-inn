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

package de.kaiserpfalzedv.paladinsinn.commons.api.tenant.service;

import java.util.Optional;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.api.paging.Page;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.PageRequest;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.Tenant;

/**
 * The read only service definition of the command and query seperation pattern for tenants. The writing interface is
 * {@link TenantCommandService}.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-25
 */
public interface TenantQueryService {
    /**
     * @param uniqueId The unique id of the tenant to be retrieved.
     *
     * @return The tenant or {@link Optional#empty()}.
     */
    Optional<? extends Tenant> retrieve(UUID uniqueId);


    /**
     * @param key The unique key of the tenant to be retrieved.
     * @return The tenant or {@link Optional#empty()}.
     */
    Optional<? extends Tenant> retrieve(String key);


    /**
     * @param pageRequest The page definition of the data page of tenants to be retrieved.
     * @return The data page requested.
     */
    Page<? extends Tenant> retrieve(PageRequest pageRequest);
}
