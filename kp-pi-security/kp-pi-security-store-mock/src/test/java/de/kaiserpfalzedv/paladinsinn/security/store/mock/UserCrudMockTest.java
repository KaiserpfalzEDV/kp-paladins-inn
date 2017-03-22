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

package de.kaiserpfalzedv.paladinsinn.security.store.mock;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.paging.Page;
import de.kaiserpfalzedv.paladinsinn.commons.paging.impl.PageRequestImpl;
import de.kaiserpfalzedv.paladinsinn.commons.persistence.DuplicateEntityException;
import de.kaiserpfalzedv.paladinsinn.commons.persistence.DuplicateUniqueIdException;
import de.kaiserpfalzedv.paladinsinn.commons.persistence.DuplicateUniqueKeyException;
import de.kaiserpfalzedv.paladinsinn.commons.persistence.DuplicateUniqueNameException;
import de.kaiserpfalzedv.paladinsinn.commons.persistence.PersistenceException;
import de.kaiserpfalzedv.paladinsinn.commons.person.Email;
import de.kaiserpfalzedv.paladinsinn.commons.person.impl.NameBuilder;
import de.kaiserpfalzedv.paladinsinn.commons.tenant.model.impl.DefaultTenant;
import de.kaiserpfalzedv.paladinsinn.security.model.User;
import de.kaiserpfalzedv.paladinsinn.security.model.impl.PersonaBuilder;
import de.kaiserpfalzedv.paladinsinn.security.model.impl.UserBuilder;
import de.kaiserpfalzedv.paladinsinn.security.store.UserCrudService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.System.identityHashCode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-19
 */
public class UserCrudMockTest {
    private static final Logger LOG = LoggerFactory.getLogger(UserCrudMockTest.class);

    private UserCrudService service;


    @Test
    public void shouldCreateUserWhenGivenCorrectData() throws DuplicateEntityException {
        User user = createDefaultUser();
        User result = service.create(user);

        assertEquals("Should be equal", user, result);
        assertNotEquals("Should be a deep copy", identityHashCode(user), identityHashCode(result));
    }

    private User createDefaultUser() {
        return createAnUser(0);
    }

    private User createAnUser(int number) {
        return new UserBuilder()
                .withUniqueId(UUID.randomUUID())
                .withName("USER_BASIC_CREATE " + number)
                .withPassword("PASSWORD")
                .withPerson(
                        new PersonaBuilder()
                                .withUniqueId(UUID.randomUUID())
                                .withName(
                                        new NameBuilder()
                                                .withSn("sn" + number)
                                                .withGivenName("givenName")
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    @Test(expected = DuplicateUniqueIdException.class)
    public void shouldThrowAnExceptionWhenUniqueIdIsAlreadyInTheSystem() throws DuplicateEntityException {
        User user = createDefaultUser();
        service.create(user);

        service.create(user);
    }

    @Test(expected = DuplicateUniqueNameException.class)
    public void shouldThrowAnExceptionWhenUserIdIsAlreadyInTheSystem() throws DuplicateEntityException {
        User user = createDefaultUser();
        service.create(user);

        User secondUser = new UserBuilder().withUser(user).withUniqueId(UUID.randomUUID()).build();
        service.create(secondUser);
    }

    @Test
    public void shouldRetrieveDataWhenKnownUniqueIdIsGiven() throws DuplicateEntityException {
        User user = createDefaultUser();
        service.create(user);

        Optional<User> result = service.retrieve(user.getUniqueId());

        assertTrue("The result should not be empty", result.isPresent());
    }

    @Test
    public void shouldRetrieveNoDataWhenKnowUniqueIdIsGiven() throws DuplicateEntityException {
        User user = createDefaultUser();
        service.create(user);

        Optional<User> result = service.retrieve(UUID.randomUUID());

        assertFalse("The result should be empty", result.isPresent());
    }

    @Test
    public void shouldRetrieveDataWhenKnownUserIdIsGiven() throws DuplicateEntityException {
        User user = createDefaultUser();
        service.create(user);

        Optional<User> result = service.retrieve(user.getName());

        assertTrue("The result should not be empty", result.isPresent());
    }

    @Test
    public void shouldRetrieveNoDataWhenKnowUserIdIsGiven() throws DuplicateUniqueKeyException {
        User user = createDefaultUser();
        service.create(user);

        Optional<User> result = service.retrieve("some idiotic string that should never be an user name");

        assertFalse("The result should be empty", result.isPresent());
    }

    @Test
    public void shouldRetrievePage2WhenRequestingIt() throws DuplicateUniqueKeyException {
        createUsers(10);

        Page<User> result = service.retrieve(new PageRequestImpl(2, 4));

        assertEquals("The page number is not correct!", 2, result.getCurrentPageNumber());
        assertEquals("The page size is not correct!", 4, result.getCurrentPageSize());
        assertEquals("The data element size differ!", 4, result.getData().size());
    }

    private void createUsers(@SuppressWarnings("SameParameterValue") int number) throws DuplicateUniqueKeyException {
        for (int i = 1; i <= number; i++) {
            service.create(createAnUser(i));
        }
    }

    @Test
    public void shouldUpdateTheDataWhenAnUserExist() throws PersistenceException {
        User user = createAnUser(0);
        UUID id = user.getUniqueId();
        user = service.create(user);


        Email emailAddress = new Email("info@kaiserpfalz-edv.de");
        user = new UserBuilder().withUser(user).withEmailAddress(emailAddress).build();
        service.update(user);

        Optional<User> wrappedResult = service.retrieve(id);

        if (wrappedResult.isPresent()) {
            User result = wrappedResult.get();

            assertNotNull(result.getEmailAddress());
            assertEquals("The email address is not correct", emailAddress, result.getEmailAddress());
            assertEquals(user, result);
            assertNotEquals(identityHashCode(user), identityHashCode(result));
        } else {
            fail("There is no user: " + user);
        }
    }

    @Test
    public void shouldCreateTheUserWhenNoUserExists() throws PersistenceException {
        User user = createAnUser(0);
        UUID id = user.getUniqueId();

        service.update(user);

        Optional<User> result = service.retrieve(id);

        assertTrue(result.isPresent());
    }

    @Test
    public void shouldDeleteUserWhenUserIsMatching() throws DuplicateUniqueKeyException {
        User user = createAnUser(0);
        service.create(user);

        service.delete(user);
        Optional<User> result = service.retrieve(user.getUniqueId());

        assertFalse(result.isPresent());
    }

    @Test
    public void shouldDeleteUserWhenAUniqueIdIsMatching() throws DuplicateUniqueKeyException {
        User user = createAnUser(0);
        service.create(user);

        service.delete(user.getUniqueId());
        Optional<User> result = service.retrieve(user.getUniqueId());

        assertFalse(result.isPresent());
    }

    @Test
    public void shouldDeleteUserWhenAUserIdIsMatching() throws DuplicateUniqueKeyException {
        User user = createAnUser(0);
        service.create(user);

        service.delete(user.getName());
        Optional<User> result = service.retrieve(user.getName());

        assertFalse(result.isPresent());
    }

    @Test
    public void shouldDoNothingWhenTheUserToBeDeletedDoesNotExist() throws DuplicateUniqueIdException {
        service.delete(UUID.randomUUID());
    }

    @Before
    public void setUpService() {
        service = new UserCrudMock(
                DefaultTenant.INSTANCE,
                new UserMultitenantCrudMock()
        );
    }

    @After
    public void tearDownService() {
        Set<User> persistedUsers = service.retrieve();

        LOG.debug("Test persisted {} users: {}", persistedUsers.size(), persistedUsers);
    }
}
