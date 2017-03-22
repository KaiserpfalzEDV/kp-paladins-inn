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

package de.kaiserpfalzedv.paladinsinn.security.access.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.person.Gender;
import de.kaiserpfalzedv.paladinsinn.commons.person.Name;
import de.kaiserpfalzedv.paladinsinn.commons.person.impl.NameBuilder;
import de.kaiserpfalzedv.paladinsinn.security.model.Persona;
import de.kaiserpfalzedv.paladinsinn.security.model.impl.PersonaBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class PersonaTest {
    private static final Logger LOG = LoggerFactory.getLogger(PersonaTest.class);

    private static final String PERSON_UNIQUE_ID_STRING = "0737074c-51a2-4d98-89a3-886c61d1ec1d";
    private static final UUID PERSON_UNIQUE_ID = UUID.fromString(PERSON_UNIQUE_ID_STRING);
    private static final Gender PERSON_GENDER = Gender.gender_questioning;
    private static final LocalDate PERSON_DOB = LocalDate.parse("2000-12-31");
    private static final int PERSON_AGE = LocalDate.ofEpochDay(LocalDate.now().toEpochDay() - PERSON_DOB.toEpochDay())
                                                   .getYear() - 1970;
    private static final Locale PERSON_COUNTRY = Locale.GERMANY;
    private static final Locale PERSON_LOCALE = Locale.GERMAN;

    private static final String GIVEN_NAME_PREFIX = "given_name_prefix";
    private static final String GIVEN_NAME = "given_name";
    private static final String GIVEN_NAME_POSTFIX = "given_name_postfix";

    private static final String SN_PREFIX = "sn_prefix";
    private static final String SN = "sn";
    private static final String SN_POSTFIX = "sn_postfix";

    private static final Name PERSON_NAME = new NameBuilder()
            .withGivenNamePrefix(GIVEN_NAME_PREFIX).withGivenName(GIVEN_NAME).withGivenNamePostfix(GIVEN_NAME_POSTFIX)
            .withSnPrefix(SN_PREFIX).withSn(SN).withSnPostfix(SN_POSTFIX)
            .build();

    private static final String PERSON_STRING = new StringBuilder().append('{')
            .append(PERSON_UNIQUE_ID_STRING)
            .append(", ").append(PERSON_NAME.getInformalFullName())
            .append(" (").append(PERSON_DOB)
            .append("), country: ").append(PERSON_COUNTRY)
            .append(", locale: ").append(PERSON_LOCALE)
            .append('}').toString();


    private PersonaBuilder service;

    @Test
    public void shouldIncludeEverythingInToStringWhenGivenAFullPerson() {
        Persona person = generateFullPersona();

        assertTrue(
                "Must contain the data: " + person.toString(),
                person.toString().contains(PERSON_STRING)
        );
    }

    private Persona generateFullPersona() {
        return service
                    .withUniqueId(PERSON_UNIQUE_ID)
                    .withName(PERSON_NAME)
                    .withGender(PERSON_GENDER)
                    .withDateOfBirth(PERSON_DOB)
                    .withCountry(PERSON_COUNTRY)
                    .withLocale(PERSON_LOCALE)
                    .build();
    }

    @Test
    public void shouldReturnTheCorrectDOBWhenADateOfBirthIsSpecified() {
        Persona result = generateFullPersona();

        assertEquals("The date of birth does not match!", PERSON_DOB, result.getDateOfBirth());
    }

    @Test
    public void shouldReturnTheCorrectAgeWhenADateOfBirthIsSpecified() {
        Persona result = generateFullPersona();

        assertEquals("Age does not match!", PERSON_AGE, result.getAge());
    }

    @Test
    public void shouldReturnTheCorrectGenderWhenADateOfBirthIsSpecified() {
        Persona result = generateFullPersona();

        assertEquals("Gender does not match!", PERSON_GENDER, result.getGender());
    }


    @SuppressWarnings("EqualsWithItself")
    @Test
    public void shouldReturnTrueWhenGivenItselfAsComparison() {
        Persona result = generateFullPersona();

        assertTrue("Comparing with itself should be true!", result.equals(result));
    }

    @Test
    public void shouldReturnFalseWhenGivenAnotherObject() {
        Persona result = generateFullPersona();

        assertFalse("Comparing with another classes object should fail!", result.equals(this));
    }

    @Test
    public void shouldReturnTrueWhenGivenTheSameFullObject() {
        Persona personA = generateFullPersona();

        Persona personB = new PersonaBuilder().withPerson(personA).build();

        assertTrue("Two identical persons should match (personA=personB)!", personA.equals(personB));
        assertTrue("Two identical persons should match (personB=personA)!", personB.equals(personA));
    }


    @Test
    public void shouldGiveBackNonEmptyErrorListWithoutName() {
        service
                .withUniqueId(PERSON_UNIQUE_ID)
                .withGender(PERSON_GENDER)
                .withDateOfBirth(PERSON_DOB)
                .withCountry(PERSON_COUNTRY)
                .withLocale(PERSON_LOCALE);

        service.validate();
        List<String> result = service.getValidationResults();

        assertTrue("There should be at least one error (given name missing)", result.size() >= 1);
    }


    
    @Before
    public void setUpService() {
        service = new PersonaBuilder();
    }

    @After
    public void tearDownService() {
        service = null;
    }
}
