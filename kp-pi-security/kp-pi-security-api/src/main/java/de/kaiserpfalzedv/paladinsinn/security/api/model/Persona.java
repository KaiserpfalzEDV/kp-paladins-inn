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

package de.kaiserpfalzedv.paladinsinn.security.api.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Locale;

import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.Identifiable;
import de.kaiserpfalzedv.paladinsinn.commons.api.person.Gender;
import de.kaiserpfalzedv.paladinsinn.commons.api.person.Name;

/**
 * The basic identity data of a person.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public interface Persona extends Identifiable, Serializable {
    /**
     * @return the full name data of this persona.
     */
    Name getFullName();

    /**
     * @return the gender of the person.
     */
    Gender getGender();

    /**
     * @return the date of birth of the person.
     */
    LocalDate getDateOfBirth();

    /**
     * @return the current age of the person.
     */
    int getAge();

    /**
     * @return the registered country of this person.
     */
    Locale getCountry();

    /**
     * @return the registered language of this person.
     */
    Locale getLocale();
}
