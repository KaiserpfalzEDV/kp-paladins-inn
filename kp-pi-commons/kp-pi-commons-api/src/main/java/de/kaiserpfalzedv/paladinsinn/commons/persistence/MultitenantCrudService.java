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

package de.kaiserpfalzedv.paladinsinn.commons.persistence;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.paging.Page;
import de.kaiserpfalzedv.paladinsinn.commons.paging.PageRequest;
import de.kaiserpfalzedv.paladinsinn.commons.tenant.model.Tenant;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-20
 */
public interface MultitenantCrudService<T extends Identifiable> {
    /**
     * @param data the data to be saved.
     * @return the saved user data.
     * @throws DuplicateUniqueIdException if the ID of the data set is already persisted.
     * @throws DuplicateUniqueNameException if the unique name of the data set is already persisted.
     */
    T create(Tenant tenant, T data) throws DuplicateUniqueIdException, DuplicateUniqueNameException;

    /**
     * Loads a single data set by the unique id.
     *
     * @param uniqueId the unique id of the data set to be loaded.
     *
     * @return The selected data set.
     */
    Optional<T> retrieve(Tenant tenant, UUID uniqueId);

    /**
     * Loads a single data set by the unique name.
     *
     * @param uniqueName the unique name of the data to be loaded.
     *
     * @return The selected data.
     */
    Optional<T> retrieve(Tenant tenant, String uniqueName);

    /**
     * @param tenant The tenant to retrieve all data for (DANGEROUS)
     * @return all data of the system for the given tenant.
     */
    Set<T> retrieve(Tenant tenant);

    /**
     * @param pageRequest the definition which part of the result to retrieve.
     *
     * @return the sublist of data given by the page request informatio.
     */
    Page<T> retrieve(Tenant tenant, PageRequest pageRequest);

    /**
     * changes the data in the store.
     *
     * @param data new data to be saved.
     * @return the saved data set.
     * @throws DuplicateUniqueIdException if the ID of the data set is already persisted.
     * @throws DuplicateUniqueNameException if the unique name of the data set is already persisted.
     */
    T update(Tenant tenant, T data) throws DuplicateUniqueNameException, DuplicateUniqueIdException;

    /**
     * @param data the data set to be deleted.
     */
    void delete(Tenant tenant, T data);

    /**
     * @param uniqueId the unique id of the data set to be deleted.
     */
    void delete(Tenant tenant, UUID uniqueId);

    /**
     * @param uniqueName the unique name of the data set to be dleted.
     */
    void delete(Tenant tenant, String uniqueName);
}
