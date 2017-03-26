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

package de.kaiserpfalzedv.paladinsinn.security.services;

import de.kaiserpfalzedv.paladinsinn.commons.api.person.Email;
import de.kaiserpfalzedv.paladinsinn.commons.api.person.Name;
import de.kaiserpfalzedv.paladinsinn.security.api.model.Persona;
import de.kaiserpfalzedv.paladinsinn.security.api.services.UserIdGenerator;

/**
 * Creates a default user id by:
 *
 * <ol>
 *     <li>If last name is at least 7 letters and first name is at least 1 letter long:<br>
 *          1. letter of given name and 7 letters of last name.</li>
 *     <li>If there is no first name but last name is at least 8 letters long:<br>
 *          the first 8 letters of the lastn ame.</li>
 *     <li>If the last name is smaller than 7 letters but the first name is long enough to fill up to 8 letters:
 *          the first n letters (n = 8 - length of last name) of the given name and the full last name.</li>
 *     <li>If the given name is not long enough to fill the user id up to 8 letters in length:
 *          append zeroes to generate an 8 digit user id.</li>
 *     <li>If no name is set but an email address: the full email address.</li>
 *     <li>If no name and no email address is set: a string consisting of 8 zeroes.</li>
 * </ol>
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class DefaultUserIdGenerator implements UserIdGenerator {

    @Override
    public String generateUserId(final Persona person, final Email emailAddress) {
        String result = null;

        if (person != null) {
            Name name = person.getFullName();

            String sn = name.getSn();
            int snLength = sn.length();

            String givenName = name.getGivenName();
            int givenNameLength = givenName.length();

            if (snLength >= 7 && givenNameLength >= 1) {
                result = givenName.substring(0, 1) + sn.substring(0, 7);
            } else if (givenNameLength == 0) {
                result = sn.substring(0, 8);
            } else {
                result = givenName.substring(0, 8 - snLength) + sn;
            }
        }

        if (result == null) {
            if (emailAddress != null) {
                result = emailAddress.getAddress();
            } else {
                result = "";
            }
        }
        
        result += fillZeros(result);

        result = result.replace(' ', '_');

        return result;
    }


    /**
     * Return a string consiting of 0s to fill up to 8 letters.
     *
     * @param userId The user id generated.
     * @return string of 0s to fill the original userId to 8 letters.
     */
    private String fillZeros(final String userId) {
        int length = userId.length();

        if (length >= 8)
            return "";
        
        return String.format("%" + (7 - length) + "d", 0);
    }
}
