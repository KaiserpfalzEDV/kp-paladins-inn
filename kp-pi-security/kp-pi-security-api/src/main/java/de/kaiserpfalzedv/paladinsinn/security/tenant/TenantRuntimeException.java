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

package de.kaiserpfalzedv.paladinsinn.security.tenant;

import de.kaiserpfalzedv.paladinsinn.commons.PaladinsInnBaseRuntimeException;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-18
 */
public class TenantRuntimeException extends PaladinsInnBaseRuntimeException {
    public TenantRuntimeException(String message) {
        super(message);
    }

    public TenantRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TenantRuntimeException(Throwable cause) {
        super(cause);
    }

    public TenantRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}