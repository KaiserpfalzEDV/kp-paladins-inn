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
import java.util.Objects;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Abstract basis class to all commands.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-25
 */
@XmlType(propOrder = {"name", "uniqueId", "commandTimeStamp"})
public abstract class AbstractCommand<T> implements Command<T> {
    private static final long serialVersionUID = 5500761242719443433L;

    /**
     * The command name.
     */
    private String name;

    /**
     * The UUID of the command.
     */
    private UUID uniqueId;

    /**
     * The timestamp of this command.
     */
    private OffsetDateTime timeStamp;


    @Deprecated
    public AbstractCommand() {}

    public AbstractCommand(final UUID uniqueId, final String name, final OffsetDateTime timestamp) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.timeStamp = timestamp;
    }

    @Override
    @XmlElement(required = true)
    public OffsetDateTime getCommandTimeStamp() {
        if (timeStamp == null) {
            timeStamp = OffsetDateTime.now();
        }

        return timeStamp;
    }

    @Deprecated
    public void setCommandTimeStamp(OffsetDateTime timestamp) {
        this.timeStamp = timestamp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUniqueId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractCommand)) return false;
        AbstractCommand<?> that = (AbstractCommand<?>) o;
        return Objects.equals(getUniqueId(), that.getUniqueId());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AbstractCommand{");
        sb.append(uniqueId);
        sb.append(", name='").append(name).append('\'');
        sb.append(timeStamp);
        sb.append('}');
        return sb.toString();
    }

    @Override
    @XmlElement(required = true)
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Deprecated
    public void setUniqueId(final UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    @XmlElement(required = true)
    public String getName() {
        return name;
    }

    @Deprecated
    public void setName(final String name) {
        this.name = name;
    }
}
