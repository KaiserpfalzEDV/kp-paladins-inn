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

import de.kaiserpfalzedv.paladinsinn.commons.PaladinsInnBaseRuntimeException;

/**
 * The abstract base of all runtime exceptions of the security APIs.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-14
 */
public abstract class SecurityRuntimeException extends PaladinsInnBaseRuntimeException {
    private static final long serialVersionUID = 1223640146136848624L;

    /**
     * @param message the failure message.
     */
    public SecurityRuntimeException(String message) {
        super(message);
    }

    /**
     * @param message the failure message.
     * @param cause   the failure cause.
     */
    public SecurityRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message            the failure message.
     * @param cause              the failure cause.
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SecurityRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
