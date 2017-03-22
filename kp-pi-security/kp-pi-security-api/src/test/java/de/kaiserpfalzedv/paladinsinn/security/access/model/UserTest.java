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
import java.util.Locale;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.person.Email;
import de.kaiserpfalzedv.paladinsinn.commons.person.Gender;
import de.kaiserpfalzedv.paladinsinn.commons.person.impl.NameBuilder;
import de.kaiserpfalzedv.paladinsinn.security.model.Persona;
import de.kaiserpfalzedv.paladinsinn.security.model.User;
import de.kaiserpfalzedv.paladinsinn.security.model.impl.PersonaBuilder;
import de.kaiserpfalzedv.paladinsinn.security.model.impl.UserBuilder;
import de.kaiserpfalzedv.paladinsinn.security.services.TestUserIdGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class UserTest {
    private static final Logger LOG = LoggerFactory.getLogger(UserTest.class);

    private static final String PRINCIPAL_NAME = "principal";

    private UserBuilder service;


    @Test
    public void shouldThrowExcpetionWhenNoNameIsGiven() {
        try {
            service.build();
        } catch (IllegalStateException e) {
            assertTrue("Wrong message: " + e.getMessage(),
                       e.getMessage().contains("The name (user id) is not set."));
        }
    }


    @Test
    public void shouldCreateMinimalPrincipalWhenNameIsGiven() {
        User result = service.withName(PRINCIPAL_NAME).build();

        assertEquals("The name does not match", PRINCIPAL_NAME, result.getName());
    }


    @Test
    public void shouldCreateFullPrincipalWhenPersonIsGiven() {
        UUID uniqueId = UUID.randomUUID();
        Persona person = new PersonaBuilder()
                .withName(
                        new NameBuilder()
                                .withSn("sur name")
                                .withGivenName("given name")
                                .build()
                )
                .withGender(Gender.gender_questioning)
                .withCountry(Locale.GERMANY)
                .withLocale(Locale.GERMAN)
                .withDateOfBirth(LocalDate.parse("2000-01-01"))
                .build();
        Email emailAddress = new Email("rlichti@kaiserpfalz-edv.de");

        User result = service
                .withPerson(person)
                .withUniqueId(uniqueId)
                .withEmailAddress(emailAddress)
                .build();

        assertEquals("The unique id does not match", uniqueId, result.getUniqueId());
        assertEquals("The name should be: gsur_nam", "gsur_nam", result.getName());
        assertEquals("The person does not match", person, result.getPerson());
        assertEquals("The locale should be: " + Locale.GERMAN, Locale.GERMAN, result.getLocale());
        assertEquals("The email address does not match", emailAddress, result.getEmailAddress());
    }


    @Test
    public void shouldCreateIdenticalPrincipalWhenPrincipalIsGiven() {
        UUID uniqueId = UUID.randomUUID();
        Persona person = new PersonaBuilder()
                .withUniqueId(uniqueId)
                .withName(
                        new NameBuilder()
                                .withSn("sur name")
                                .withGivenName("given name")
                                .build()
                )
                .withGender(Gender.gender_questioning)
                .withCountry(Locale.GERMANY)
                .withLocale(Locale.GERMAN)
                .withDateOfBirth(LocalDate.parse("2000-01-01"))
                .build();
        Email emailAddress = new Email("rlichti@kaiserpfalz-edv.de");

        User input = service
                .withPerson(person)
                .withUniqueId(uniqueId)
                .withEmailAddress(emailAddress)
                .build();

        User result = service
                .withUser(input)
                .build();

        assertTrue("The principals don't match", input.equals(result));
    }


    @Before
    public void setUpService() {
        service = new UserBuilder().withUserIdGenerator(new TestUserIdGenerator());
    }

    @After
    public void tearDownService() {
        service = null;
    }
}
