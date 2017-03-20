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

/**
 * This exception is thrown if there is already an entity with the same unique id.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-19
 */
public class DuplicateEntityException extends PersistenceException {
    private static final long serialVersionUID = 782078487712154277L;

    private Identifiable identifiable;


    /**
     * @param clasz        The class of the entity.
     * @param identifiable The identity that should be created.
     */
    public DuplicateEntityException(final Class<?> clasz, final Identifiable identifiable) {
        super(clasz, String.format("Entity already exists with unique id of %s", identifiable.getUniqueId()));
    }


    /**
     * @return The identity that should have been created.
     */
    public Identifiable getIdentifiable() {
        return identifiable;
    }
}
