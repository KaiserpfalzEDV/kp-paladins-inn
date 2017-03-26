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

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-26
 */
public class DataConversionException extends PersistenceRuntimeException {
    private static final long serialVersionUID = 6696187586304452710L;

    public DataConversionException(Class<?> clasz) {
        super(clasz, "The data could not be converted to the target type.");
    }

    public DataConversionException(Class<?> clasz, Throwable cause) {
        super(clasz, "The data could not be converted to the target type.", cause);
    }

    public DataConversionException(Class<?> clasz, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(clasz, "The data could not be converted to the target type.", cause, enableSuppression, writableStackTrace);
    }
}
