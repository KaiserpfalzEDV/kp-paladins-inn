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

package de.kaiserpfalzedv.paladinsinn.commons.api.tenant.store;

import java.util.Optional;

import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.CrudService;
import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.Identifiable;
import de.kaiserpfalzedv.paladinsinn.commons.api.tenant.model.Tenant;

/**
 * The interface for the CRUD services for Tenants within the system. For tenants exist a short string typed key.
 *
 * <em>For the CRUD services this {@link Tenant#getKey()}  will be used instead of the normally used
 * {@link Identifiable#getName()} whithin this interface.</em>
 * 
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-18
 */
public interface TenantCrudService extends CrudService<Tenant> {
    /**
     * With tenants the {@link CrudService#delete(String)} method does not take the {@link Identifiable#getName()} but
     * the {@link Tenant#getKey()} as parameter. Please keep that in mind!
     *
     * @param uniqueKey the {@link Tenant#getKey()} of the data set to be deleted.
     *
     * @return the tenant with the unique key given as parameter.
     *
     * @see Tenant#getKey()
     * @see Tenant#getName()
     */
    Optional<? extends Tenant> retrieve(String uniqueKey);

    /**
     * With tenants the {@link CrudService#delete(String)} method does not take the {@link Identifiable#getName()} but
     * the {@link Tenant#getKey()} as parameter. Please keep that in mind!
     *
     * @param uniqueKey the {@link Tenant#getKey()} of the data set to be deleted.
     *
     * @see Tenant#getKey()
     * @see Tenant#getName()
     */
    void delete(String uniqueKey);
}
