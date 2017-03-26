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

package de.kaiserpfalzedv.paladinsinn.security.api.services;

/**
 * The userId is not entitled to use this method.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-17
 */
public class UserIsNotEntitledException extends SecurityException {
    private static final long serialVersionUID = -6578423501660505447L;

    private final String userId;
    private final String entitlement;

    /**
     * @param message         the failure message.
     * @param userId          the userId that is not entitled.
     * @param entitlementName the entitlement checked.
     */
    public UserIsNotEntitledException(final String message, final String userId, final String entitlementName) {
        super(String.format(message, userId, entitlementName));

        this.userId = userId;
        this.entitlement = entitlementName;
    }

    /**
     * @param userId          the userId that is not entitled.
     * @param entitlementName the entitlement checked.
     */
    public UserIsNotEntitledException(final String userId, final String entitlementName) {
        super(String.format("User %s is not entitled for %s", userId, entitlementName));

        this.userId = userId;
        this.entitlement = entitlementName;
    }


    public String getUserId() {
        return userId;
    }

    public String getEntitlement() {
        return entitlement;
    }
}
