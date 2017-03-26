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

package de.kaiserpfalzedv.paladinsinn.commons.person;

import de.kaiserpfalzedv.paladinsinn.commons.api.person.Email;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class EmailTest {
    private static final Logger LOG = LoggerFactory.getLogger(EmailTest.class);

    private static final String EMAIL_ADDRESS = "rlichti@kaiserpfalz-edv.de";


    @Test
    public void shouldGiveTheCorrectEmailAdressWhenCalled() {
        Email service = new Email(EMAIL_ADDRESS);

        assertEquals("The email address does not match!", EMAIL_ADDRESS, service.getAddress());
    }
}
