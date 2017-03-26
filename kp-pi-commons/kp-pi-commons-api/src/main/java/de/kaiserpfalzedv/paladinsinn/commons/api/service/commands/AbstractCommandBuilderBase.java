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

import java.time.OffsetDateTime;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.api.Builder;
import de.kaiserpfalzedv.paladinsinn.commons.api.BuilderValidationException;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-25
 */
public abstract class AbstractCommandBuilderBase<T extends Command<?>> implements Builder<T> {
    private UUID uniqueId;
    private OffsetDateTime timeStamp;

    @Override
    public T build() throws BuilderValidationException {
        return generateImplementation(uniqueId, timeStamp);
    }

    /**
     * Generates a base object to set all data to.
     *
     * @param uniqueId  The unique id of the command to build.
     * @param timeStamp the time stamp of the command to build.
     *
     * @return
     */
    public abstract T generateImplementation(final UUID uniqueId, final OffsetDateTime timeStamp);

    @Override
    public void validate() throws BuilderValidationException {
        if (uniqueId == null) {
            uniqueId = UUID.randomUUID();
        }

        if (timeStamp == null) {
            timeStamp = OffsetDateTime.now();
        }
    }

    public Builder<T> setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
        return this;
    }

    public Builder<T> setTimeStamp(OffsetDateTime timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }
}
