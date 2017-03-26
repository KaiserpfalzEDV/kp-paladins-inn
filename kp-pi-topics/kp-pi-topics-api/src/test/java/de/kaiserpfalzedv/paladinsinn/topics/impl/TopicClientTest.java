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

package de.kaiserpfalzedv.paladinsinn.topics.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.kaiserpfalzedv.paladinsinn.commons.api.BuilderValidationException;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.Page;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.PageBuilder;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.PageRequest;
import de.kaiserpfalzedv.paladinsinn.security.api.model.User;
import de.kaiserpfalzedv.paladinsinn.security.api.model.UserBuilder;
import de.kaiserpfalzedv.paladinsinn.topics.model.Topic;
import de.kaiserpfalzedv.paladinsinn.topics.model.impl.TopicBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-03-24
 */
public class TopicClientTest {
    private static final UUID USER_UNIQUE_ID = UUID.randomUUID();
    private static final String USER_ID = "user_id";
    private static final String PASSWORD = "password";
    private static final User MAINTAINER = new UserBuilder()
            .withUniqueId(USER_UNIQUE_ID)
            .withName(USER_ID)
            .withPassword(PASSWORD)
            .build();

    private static final Topic PARENT_TOPIC = new Topic() {
        private static final long serialVersionUID = 0L;

        private UUID tenant = UUID.randomUUID();
        private UUID uniqueId = UUID.randomUUID();

        @Override
        public Topic getParent() {
            return this;
        }

        @Override
        public List<Topic> getChildren() {
            return new ArrayList<>();
        }

        @Override
        public Page<Topic> getChildren(PageRequest pageRequest) {
            try {
                return new PageBuilder<Topic>()
                        .withPage(new ArrayList<>(), pageRequest.getPageNumber(), pageRequest.getPageSize())
                        .build();
            } catch (BuilderValidationException e) {
                throw new IllegalStateException("Can't create the page for the paged children of this topic request");
            }
        }

        @Override
        public UUID getTenant() {
            return tenant;
        }

        @Override
        public UUID getUniqueId() {
            return uniqueId;
        }

        @Override
        public String getName() {
            return "NULL";
        }

        @Override
        public UUID getMaintainer() {
            return MAINTAINER.getUniqueId();
        }
    };

    @Test
    public void createMinimalTopic() throws BuilderValidationException {
        Topic result = new TopicBuilder()
                .setName("TestTopic")
                .setMaintainer(MAINTAINER)
                .setParent(PARENT_TOPIC)
                .build();

        assertEquals("TestTopic", result.getName());
    }
}
