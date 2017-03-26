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

import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.api.person.Gender;
import de.kaiserpfalzedv.paladinsinn.commons.api.person.Name;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class PersonaImpl implements Persona {
    private static final long serialVersionUID = -1049362696230737811L;

    
    private UUID uniqueId;

    private Name name;
    private Gender gender;
    private LocalDate dateOfBirth;
    private int age;

    private Locale country;
    private Locale locale;

    PersonaImpl(
            final UUID uniqueId,
            final Name name,
            final Gender gender,
            final LocalDate dateOfBirth,
            final int age,
            final Locale country,
            final Locale locale
    ) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.age = age;
        this.country = country;
        this.locale = locale;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUniqueId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonaImpl)) return false;
        Persona person = (Persona) o;
        return Objects.equals(getUniqueId(), person.getUniqueId());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder()
                .append(getClass().getSimpleName()).append('@').append(System.identityHashCode(this)).append('{')
                .append(getUniqueId()).append(", ").append(getFullName().getInformalFullName());

        if (getDateOfBirth() != null) {
            result.append(" (").append(getDateOfBirth()).append(')');
        }

        if (getCountry() != null) {
            result.append(", country: ").append(getCountry());
        }

        if (getLocale() != null) {
            result.append(", locale: ").append(getLocale());
        }

        return result.append('}').toString();
    }

    public Name getFullName() {
        return name;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public Locale getCountry() {
        return country;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name.getFormalFullName();
    }
}
