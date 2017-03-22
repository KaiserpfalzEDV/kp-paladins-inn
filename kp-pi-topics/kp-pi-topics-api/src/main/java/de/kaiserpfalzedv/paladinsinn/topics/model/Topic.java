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

package de.kaiserpfalzedv.paladinsinn.topics.model;

import java.io.Serializable;
import java.util.List;

import de.kaiserpfalzedv.paladinsinn.commons.paging.Page;
import de.kaiserpfalzedv.paladinsinn.commons.paging.PageRequest;
import de.kaiserpfalzedv.paladinsinn.commons.persistence.Identifiable;
import de.kaiserpfalzedv.paladinsinn.commons.tenant.model.Maintainable;
import de.kaiserpfalzedv.paladinsinn.commons.tenant.model.TenantHolding;

/**
 * A single topic.
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-03-18
 */
public interface Topic extends Identifiable, TenantHolding, Maintainable, Serializable {
    /**
     * @return The parent topic.
     */
    Topic getParent();

    /**
     * @return list of children topics of the current topic.
     */
    List<Topic> getChildren();

    /**
     * @param pageRequest The page definition of the page to retrieve.
     *
     * @return The part of the children that are on the requested page.
     */
    Page<Topic> getChildren(PageRequest pageRequest);
}
