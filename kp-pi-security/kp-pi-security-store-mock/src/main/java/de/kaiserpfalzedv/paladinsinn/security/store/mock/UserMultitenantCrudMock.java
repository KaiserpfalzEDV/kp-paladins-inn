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

package de.kaiserpfalzedv.paladinsinn.security.store.mock;

import de.kaiserpfalzedv.paladinsinn.commons.persistence.impl.AbstractMultitenantCrudMock;
import de.kaiserpfalzedv.paladinsinn.commons.service.MockService;
import de.kaiserpfalzedv.paladinsinn.commons.service.MultiTenant;
import de.kaiserpfalzedv.paladinsinn.security.model.User;
import de.kaiserpfalzedv.paladinsinn.security.model.impl.UserBuilder;
import de.kaiserpfalzedv.paladinsinn.security.store.UserMultitenantCrudService;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-19
 */
@MockService
@MultiTenant
public class UserMultitenantCrudMock extends AbstractMultitenantCrudMock<User> implements UserMultitenantCrudService {
    public UserMultitenantCrudMock() {
        super(User.class);
    }


    @Override
    public User copy(User data) {
        return new UserBuilder().withUser(data).build();
    }
}
