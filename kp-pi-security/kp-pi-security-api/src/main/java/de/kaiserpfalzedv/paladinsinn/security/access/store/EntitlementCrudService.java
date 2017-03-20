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

package de.kaiserpfalzedv.paladinsinn.security.access.store;

import de.kaiserpfalzedv.paladinsinn.commons.paging.Page;
import de.kaiserpfalzedv.paladinsinn.commons.paging.PageRequest;
import de.kaiserpfalzedv.paladinsinn.security.access.model.Entitlement;

import java.util.Set;
import java.util.UUID;

/**
 * The low level CRUD services used by the business services to handle entitlements in a single-tenant environment. If
 * you have a multi-tenant environment, please use {@link TenantEntitlementCrudService} instead.
 * 
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-17
 */
public interface EntitlementCrudService {
    /**
     * Persists the entitlement.
     *
     * @param entitlement the data to be persisted.
     *
     * @return the persisted data.
     */
    Entitlement create(Entitlement entitlement);

    Set<Entitlement> retrieve();

    Page<Entitlement> retrieve(PageRequest pageRequest);

    Entitlement retrieve(String entitlementName);

    Entitlement update(Entitlement entitlement);

    void delete(Entitlement entitlement);

    void delete(UUID uniqueId);

    void delete(String entitlementName);
}
