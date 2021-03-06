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
public class CommandExecutionRuntimeException extends PaladinsInnBaseRuntimeException {
    private static final long serialVersionUID = 7430103822829715176L;

    private Command<?> command;
    private CommandExecutionContext<?> context;

    public CommandExecutionRuntimeException(final CommandExecutionContext<?> context, final Command<?> command, final String message) {
        super(message);

        this.context = context;
        this.command = command;
    }

    public CommandExecutionRuntimeException(final CommandExecutionContext<?> context, final Command<?> command, final String message, final Throwable cause) {
        super(message, cause);

        this.context = context;
        this.command = command;
    }

    public CommandExecutionRuntimeException(final CommandExecutionContext<?> context, final Command<?> command, final Throwable cause) {
        super(cause);

        this.context = context;
        this.command = command;
    }

    public CommandExecutionRuntimeException(final CommandExecutionContext<?> context, final Command<?> command, final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

        this.context = context;
        this.command = command;
    }


    public CommandExecutionContext<?> getContext() {
        return context;
    }

    public Command<?> getCommand() {
        return command;
    }

}
