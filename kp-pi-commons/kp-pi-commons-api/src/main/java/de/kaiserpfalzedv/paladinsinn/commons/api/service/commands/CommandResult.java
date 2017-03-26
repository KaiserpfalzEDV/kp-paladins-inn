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

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.api.persistence.Identifiable;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-25
 */
public interface CommandResult<T> extends Identifiable {
    public static final long serialVersionUID = 0L;

    /**
     * If there is no result object, then the command is no success.
     *
     * @return !{@link #isSuccess()}
     */
    default boolean isFailure() {
        return !isSuccess();
    }

    /**
     * If there is a result object, then the command has been a success.
     *
     * @return TRUE, if {@link #getResult()}.isPresent() is true.
     */
    default boolean isSuccess() {
        return getResult().isPresent();
    }

    Optional<T> getResult();

    /**
     * @return the unique id of the command answered.
     */
    UUID getCommandUniqueId();

    /**
     * @return the date when the execution was finished.
     */
    OffsetDateTime getExecutionTimestamp();

    /**
     * @return the duration of the execution.
     */
    Duration getExecutionDuration();
}
