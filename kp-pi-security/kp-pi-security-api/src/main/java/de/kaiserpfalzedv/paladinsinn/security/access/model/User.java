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

import java.util.Locale;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.Identifiable;
import de.kaiserpfalzedv.paladinsinn.commons.person.Email;
import de.kaiserpfalzedv.paladinsinn.security.access.PasswordFailureException;
import de.kaiserpfalzedv.paladinsinn.security.access.UserIsLockedException;

/**
 * Paladins Inn uses an extended principal containing some additional data of the user.
 *
 * At first registration every user gets a {@link UUID} assigned. This ID may be used for everything else later.
 * 
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public interface User extends Identifiable {
    /**
     * @return the person of the user.
     */
    Persona getPerson();

    /**
     * @return the preferred language of the user. If not set returns default {@link Locale} of the JVM runtime.
     */
    Locale getLocale();

    /**
     * @return an email address of the user.
     */
    Email getEmailAddress();

    /**
     * @param passwordToCheck The password to check against the saved password.
     * @return if both passwords checked.
     * @throws PasswordFailureException The password did not match the password of the user.
     * @throws UserIsLockedException The user is locked and can not log in.
     */
    void login(final String passwordToCheck) throws PasswordFailureException, UserIsLockedException;

    /**
     * @return TRUE if the user currentl is locked.
     */
    boolean isLocked();
}
