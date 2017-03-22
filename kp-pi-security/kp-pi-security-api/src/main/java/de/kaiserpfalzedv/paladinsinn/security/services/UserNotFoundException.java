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

/**
 * The user who tried to log in does not exist. This is a software internal state that should not be communicated to
 * the user since that would faciliate brute force attacks to check which user exist on the system.
 * 
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-14
 */
public class UserNotFoundException extends SecurityException {
    private static final long serialVersionUID = 7103820497194994734L;

    /**
     * @param userId The user id of the user not found in the system.
     */
    public UserNotFoundException(String userId) {
        super(String.format("No user with id '%s' found.", userId));
    }
}
