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

package de.kaiserpfalzedv.paladinsinn.commons.store.jpa.model;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-23
 */
@Embeddable
public class PaladinsInnJPAMetaData implements Serializable {
    private static final long serialVersionUID = 634812979344690814L;

    private static final ZoneId UTC = ZoneId.of("UTC");

    @Column(name = "CREATED", nullable = false)
    private ZonedDateTime created = ZonedDateTime.now(UTC);
    @Column(name = "MODIFIED", nullable = false)
    private ZonedDateTime changed = created;

    @PrePersist
    public void updateChanged() {
        changed = ZonedDateTime.now(UTC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCreated(), getChanged());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaladinsInnJPAMetaData)) return false;
        PaladinsInnJPAMetaData that = (PaladinsInnJPAMetaData) o;
        return Objects.equals(getCreated(), that.getCreated()) &&
                Objects.equals(getChanged(), that.getChanged());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("created=").append(created);
        sb.append(", changed=").append(changed);
        sb.append('}');
        return sb.toString();
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getChanged() {
        return changed;
    }

    public void setChanged(ZonedDateTime changed) {
        this.changed = changed;
    }
}
