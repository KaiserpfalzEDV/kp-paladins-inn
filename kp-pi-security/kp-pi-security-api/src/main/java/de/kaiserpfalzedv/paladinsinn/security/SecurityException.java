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

package de.kaiserpfalzedv.paladinsinn.security;

import de.kaiserpfalzedv.paladinsinn.commons.PaladinsInnBaseException;

/**
 * The abstract base class of all checked security exceptions.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-14
 */
public abstract class SecurityException extends PaladinsInnBaseException {
    private static final long serialVersionUID = 5383647166865816794L;

    /**
     * @param message The failure message.
     */
    public SecurityException(String message) {
        super(message);
    }

    /**
     * @param message The failure message.
     * @param cause   The failure cause.
     */
    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message The failure message.
     * @param cause The failure cause.
     * @param enableSuppression
     * @param writableStackTrace If the stack trace should be written.
     */
    public SecurityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
