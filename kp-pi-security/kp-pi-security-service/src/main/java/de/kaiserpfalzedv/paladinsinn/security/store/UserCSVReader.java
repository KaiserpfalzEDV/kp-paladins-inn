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

package de.kaiserpfalzedv.paladinsinn.security.store;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import de.kaiserpfalzedv.paladinsinn.commons.persistence.impl.AbstractCSVDataReader;
import de.kaiserpfalzedv.paladinsinn.commons.person.Gender;
import de.kaiserpfalzedv.paladinsinn.commons.person.impl.NameBuilder;
import de.kaiserpfalzedv.paladinsinn.commons.service.CSV;
import de.kaiserpfalzedv.paladinsinn.commons.service.SingleTenant;
import de.kaiserpfalzedv.paladinsinn.security.model.Role;
import de.kaiserpfalzedv.paladinsinn.security.model.User;
import de.kaiserpfalzedv.paladinsinn.security.model.impl.PersonaBuilder;
import de.kaiserpfalzedv.paladinsinn.security.model.impl.UserBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-14
 */
@CSV
@SingleTenant
public class UserCSVReader extends AbstractCSVDataReader<User> implements UserDataReader {
    private static final Logger LOG = LoggerFactory.getLogger(UserCSVReader.class);


    private static final HashMap<String, Locale> languages = new HashMap<>(Locale.getAvailableLocales().length);
    private static final HashMap<String, Locale> countries = new HashMap<>(Locale.getAvailableLocales().length);

    static {
        for (Locale l : Locale.getAvailableLocales()) {
            try {
                languages.put(l.getISO3Language(), l);
                countries.put(l.getISO3Country(), l);

                LOG.trace("Available Locale and Country: {} -> (language: {}, country: {})", l, l.getISO3Language(), l.getISO3Country());
            } catch (MissingResourceException e) {
                LOG.warn("No 3-letter-code found for locale: {}", l);
            }
        }
    }

    private RoleCrudService roleCrudService;


    @Inject
    public UserCSVReader(
            final UserCrudService crudService,
            final RoleCrudService roleCrudService
    ) {
        super(crudService);

        this.roleCrudService = roleCrudService;
    }

    @Override
    public User readSingleLine(final String line) {
        LOG.trace("Reading line: {}", line);

        Scanner scanner = new Scanner(line);
        scanner.useDelimiter(";");

        String uniqueId = scanner.hasNext() ? scanner.next() : UUID.randomUUID().toString();

        if (!scanner.hasNext()) {
            throw new IllegalArgumentException("Need to give an user id for user: " + uniqueId);
        }
        String userId = scanner.next();

        if (!scanner.hasNext()) {
            throw new IllegalArgumentException("Need to give password for user: " + uniqueId);
        }
        String password = scanner.next();

        if (!scanner.hasNext()) {
            throw new IllegalArgumentException("Need to give a name for the user: " + uniqueId);
        }
        String sn = scanner.next();

        if (!scanner.hasNext()) {
            throw new IllegalArgumentException("Need to give a given name for the user: " + uniqueId);
        }
        String givenName = scanner.next();

        String snPrefix = scanner.hasNext() ? scanner.next() : null;
        String snPostfix = scanner.hasNext() ? scanner.next() : null;
        String givenNamePrefix = scanner.hasNext() ? scanner.next() : null;
        String givenNamePostfix = scanner.hasNext() ? scanner.next() : null;

        String gender = scanner.hasNext() ? scanner.next() : null;
        String dateOfBirth = scanner.hasNext() ? scanner.next() : null;

        String country = scanner.hasNext() ? scanner.next() : null;
        String locale = scanner.hasNext() ? scanner.next() : null;

        String locked = scanner.hasNext() ? scanner.next() : "N";

        Set<Role> roles = null;
        if (scanner.hasNext()) {
            roles = readRoles(scanner.next());
        }

        User result = buildUser(uniqueId, userId, password,
                                sn, givenName,
                                snPrefix, snPostfix,
                                givenNamePrefix, givenNamePostfix,
                                gender, dateOfBirth,
                                country, locale, locked,
                                roles
        );

        LOG.debug("User read: {} -> {}", line, result);
        return result;
    }

    private Set<Role> readRoles(final String data) {
        HashSet<Role> result = new HashSet<>();

        Scanner scanner = new Scanner(data);
        scanner.useDelimiter(",");

        while (scanner.hasNext()) {
            UUID roleId = UUID.fromString(scanner.next());
            roleCrudService.retrieve(roleId).ifPresent(result::add);
        }

        scanner.close();
        return result;
    }

    private User buildUser(String uniqueId, String userId, String password, String sn, String givenName, String snPrefix, String snPostfix, String givenNamePrefix, String givenNamePostfix, String gender, String dateOfBirth, String country, String locale, String locked, Set<Role> roles) {
        UserBuilder result = new UserBuilder()
                .withUniqueId(UUID.fromString(uniqueId))
                .withName(userId)
                .withPassword(password)

                .withPerson(
                        new PersonaBuilder()
                                .withUniqueId(UUID.fromString(uniqueId))
                                .withName(
                                        new NameBuilder()
                                                .withGivenNamePrefix(givenNamePrefix)
                                                .withGivenName(givenName)
                                                .withGivenNamePostfix(givenNamePostfix)
                                                .withSnPrefix(snPrefix)
                                                .withSn(sn)
                                                .withSnPostfix(snPostfix)
                                                .build()
                                )
                                .withGender(Gender.valueOf(gender))
                                .withDateOfBirth(LocalDate.parse(dateOfBirth))
                                .withCountry(countries.get(country))
                                .withLocale(languages.get(locale))
                                .build()
                )
                .withRoles(roles);

        if ("Y".equalsIgnoreCase(locked) || "1".equals(locked) || "T".equals(locked)) {
            result.locked();
        }

        return result.build();
    }

}
