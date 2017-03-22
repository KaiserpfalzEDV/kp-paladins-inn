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

package de.kaiserpfalzedv.paladinsinn.security.model.impl;

import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.impl.IdentifiableAbstractImpl;
import de.kaiserpfalzedv.paladinsinn.security.model.Entitlement;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class EntitlementImpl extends IdentifiableAbstractImpl implements Entitlement {
    private static final long serialVersionUID = -5783778709432204025L;

    public EntitlementImpl(final UUID uniqueId, final String name) {
        super(uniqueId, name);
    }
}
