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

package de.kaiserpfalzedv.paladinsinn.commons.api.persistence;

import de.kaiserpfalzedv.paladinsinn.commons.api.PaladinsInnBaseRuntimeException;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-20
 */
public class PersistenceRuntimeException extends PaladinsInnBaseRuntimeException {
    private static final long serialVersionUID = 1941371818976579607L;

    private Class<?> clasz;

    public PersistenceRuntimeException(final Class<?> clasz, final String message) {
        super(String.format("%s (class: %s)", message, clasz.getCanonicalName()));

        this.clasz = clasz;
    }

    public PersistenceRuntimeException(final Class<?> clasz, final String message, final Throwable cause) {
        super(String.format("%s (class: %s)", message, clasz.getCanonicalName()), cause);

        this.clasz = clasz;
    }

    public PersistenceRuntimeException(
            final Class<?> clasz, final String message, final Throwable cause,
            final boolean enableSuppression, final boolean writableStackTrace
    ) {
        super(String.format("%s (class: %s)", message, clasz.getCanonicalName()), cause,
              enableSuppression, writableStackTrace
        );

        this.clasz = clasz;
    }


    public Class<?> getEntityClass() {
        return clasz;
    }
}
