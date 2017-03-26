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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.api.person.Gender;
import de.kaiserpfalzedv.paladinsinn.commons.api.person.Name;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class PersonaBuilder {
    /**
     * A list of errors during validation.
     */
    private final ArrayList<String> errors = new ArrayList<>(2);
    private UUID uniqueId;
    private Name name;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Locale country;
    private Locale locale;

    public Persona build() {
        setDefaultValuesIfNeeded();
        validateBeforeBuild();

        return new PersonaImpl(uniqueId, name, gender, dateOfBirth, calculateAge(dateOfBirth), country, locale);
    }

    private void setDefaultValuesIfNeeded() {
        if (uniqueId == null) {
            uniqueId = UUID.randomUUID();
        }
    }

    private void validateBeforeBuild() {
        if (! validate()) {
            StringBuilder error = new StringBuilder("The personal name can't be build:\n");

            errors.forEach(e -> error.append("- ").append(e).append('\n'));

            throw new IllegalStateException(error.toString());
        }
    }

    private int calculateAge(final LocalDate dateOfBirth) {
        if (dateOfBirth == null)
            return -1;
        
        return LocalDate.ofEpochDay(LocalDate.now().toEpochDay() - dateOfBirth.toEpochDay()).getYear() - 1970;
    }


    public boolean validate() {
        boolean result = true;
        errors.clear();

        if (name == null) {
            result = false;
            errors.add("No name is given.");
        }

        return result;
    }

    public List<String> getValidationResults() {
        return Collections.unmodifiableList(errors);
    }


    public PersonaBuilder withPerson(final Persona person) {
        uniqueId = person.getUniqueId();
        name = person.getFullName();
        gender = person.getGender();
        dateOfBirth = person.getDateOfBirth();
        country = person.getCountry();
        locale = person.getLocale();

        return this;
    }


    public PersonaBuilder withUniqueId(final UUID uniqueId) {
        this.uniqueId = uniqueId;
        return this;
    }

    public PersonaBuilder withName(final Name name) {
        this.name = name;
        return this;
    }

    public PersonaBuilder withGender(final Gender gender) {
        this.gender = gender;
        return this;
    }

    public PersonaBuilder withDateOfBirth(final LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public PersonaBuilder withCountry(final Locale country) {
        this.country = country;
        return this;
    }

    public PersonaBuilder withLocale(final Locale locale) {
        this.locale = locale;
        return this;
    }
}
