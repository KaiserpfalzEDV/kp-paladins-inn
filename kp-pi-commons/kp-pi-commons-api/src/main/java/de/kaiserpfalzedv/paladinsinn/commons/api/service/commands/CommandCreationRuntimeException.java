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

package de.kaiserpfalzedv.paladinsinn.commons.api.service.commands;

import de.kaiserpfalzedv.paladinsinn.commons.api.PaladinsInnBaseRuntimeException;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-25
 */
public class CommandCreationRuntimeException extends PaladinsInnBaseRuntimeException {
    private static final long serialVersionUID = -3464544091141583623L;

    public CommandCreationRuntimeException(final String message) {
        super(message);
    }

    public CommandCreationRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CommandCreationRuntimeException(final Throwable cause) {
        super(cause);
    }

    public CommandCreationRuntimeException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
