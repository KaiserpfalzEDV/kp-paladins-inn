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

package de.kaiserpfalzedv.paladinsinn.security.access;

/**
 * This exception is thrown if the password did not match during a login attempt. It is for software internal
 * measurements and it is considered good practice not to give this information to the user.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-14
 */
public class PasswordFailureException extends SecurityException {
    private static final long serialVersionUID = -6223137068380318434L;

    /**
     * @param userId the user name of the user who tried to log in.
     */
    public PasswordFailureException(final String userId) {
        super(String.format("Password for '%s' did not match!", userId));
    }
}
