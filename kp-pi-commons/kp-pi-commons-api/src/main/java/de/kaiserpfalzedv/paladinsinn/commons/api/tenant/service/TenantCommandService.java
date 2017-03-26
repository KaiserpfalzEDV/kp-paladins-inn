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

import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.DuplicateEntityException;
import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.EntityNotFoundException;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.Tenant;

/**
 * The writing part of the tenant command-and-query-separated interfaces. The reading interface is
 * {@link TenantQueryService}.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-26
 */
public interface TenantCommandService {
    /**
     * @param tenant The tenant to be created.
     *
     * @return The created tenant
     *
     * @throws DuplicateEntityException If the entity already existed.
     */
    Tenant create(Tenant tenant) throws DuplicateEntityException;

    /**
     * @param uniqueId The unique id of the tenant to be changed.
     * @param key The new key for the tenant.
     * @throws EntityNotFoundException If there is no such tenant.
     */
    void changeKey(UUID uniqueId, String key) throws EntityNotFoundException;

    /**
     * @param uniqueId The unique id of the tenant to be changed.
     * @param name The new name for the tenant.
     * @throws EntityNotFoundException If there is no such tenant.
     */
    void changeName(UUID uniqueId, String name) throws EntityNotFoundException;

    /**
     * @param uniqueId The unique id of the tenant to be deleted.
     */
    void delete(UUID uniqueId);
}
