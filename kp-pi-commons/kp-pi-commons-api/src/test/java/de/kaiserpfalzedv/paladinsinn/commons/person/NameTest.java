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

import java.util.List;

import de.kaiserpfalzedv.paladinsinn.commons.api.person.Name;
import de.kaiserpfalzedv.paladinsinn.commons.api.person.NameBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class NameTest {
    private static final Logger LOG = LoggerFactory.getLogger(NameTest.class);

    private static final String GIVEN_NAME_PREFIX = "given_name_prefix";
    private static final String GIVEN_NAME = "given_name";
    private static final String GIVEN_NAME_POSTFIX = "given_name_postfix";

    private static final String SN_PREFIX = "sn_prefix";
    private static final String SN = "sn";
    private static final String SN_POSTFIX = "sn_postfix";

    private static final int FULL_PERSON_HASHCODE = 1831550003;
    private static final String FORMAL_FULL_NAME_STRING = new StringBuilder()
            .append(GIVEN_NAME_PREFIX).append(' ').append(GIVEN_NAME).append(' ').append(GIVEN_NAME_POSTFIX)
            .append(' ')
            .append(SN_PREFIX).append(' ').append(SN).append(' ').append(SN_POSTFIX)
            .toString();
    private static final String FORMAL_SNSTRING = new StringBuilder()
            .append(GIVEN_NAME_PREFIX)
            .append(' ')
            .append(SN_PREFIX).append(' ').append(SN).append(' ').append(SN_POSTFIX)
            .toString();
    private static final String INFORMAL_FULL_NAME_STRING = new StringBuilder()
            .append(GIVEN_NAME).append(' ').append(GIVEN_NAME_POSTFIX)
            .append(' ')
            .append(SN)
            .toString();
    private static final String FULL_PERSON_STRING = new StringBuilder("{")
            .append(FORMAL_FULL_NAME_STRING).toString();

    private static final int MINIMUM_PERSON_HASHCODE = -1652585517;
    private static final String MINIMUM_PERSON_STRING = new StringBuilder("{")
            .append(GIVEN_NAME).append(' ').append(SN)
            .append('}').toString();




    private NameBuilder service;


    @Test
    public void shouldBuildPersonalNameWithSnAndGivenNameOnly() {
        Name result = generateMinimumPersonalName();

        assertNull("There should be no sn prefix!", result.getSnPrefix());
        assertEquals("Family name does not match!", SN, result.getSn());
        assertNull("There should be no sn postfix!", result.getSnPostfix());

        assertNull("There shuld be no given name prefix!", result.getGivenNamePrefix());
        assertEquals("Given name does not match!", GIVEN_NAME, result.getGivenName());
        assertNull("There should be no given name postfix!", result.getGivenNamePostfix());
    }

    private Name generateMinimumPersonalName() {
        return service
                    .withGivenName(GIVEN_NAME)
                    .withSn(SN)
                    .build();
    }

    @Test
    public void shouldFailWithoutSn() {
        service
                .withGivenName(GIVEN_NAME)
                .withSnPostfix(SN_POSTFIX);

        try {
            service.build();

            fail("An IllegalStateException should have been thrown!");
        } catch (IllegalStateException e) {
            assertTrue(
                    "Exception match does not contain correct message!",
                    e.getMessage().contains("The family name is not set")
            );
        }
    }


    @Test
    public void shouldHaveSpecialHashForFullPersonName() {
        Name person = generateFullPersonName();

        assertEquals("Hash for full person name differs!", FULL_PERSON_HASHCODE, person.hashCode());
    }

    private Name generateFullPersonName() {
        return service
                .withSnPrefix(SN_PREFIX)
                .withSn(SN)
                .withSnPostfix(SN_POSTFIX)

                .withGivenNamePrefix(GIVEN_NAME_PREFIX)
                .withGivenName(GIVEN_NAME)
                .withGivenNamePostfix(GIVEN_NAME_POSTFIX)

                .build();
    }


    @Test
    public void shouldIncludeEverythingInToStringWhenGivenAFullPerson() {
        Name person = generateFullPersonName();

        assertTrue(
                "Must contain all person name data: " + person.toString(),
                person.toString().contains(FULL_PERSON_STRING)
        );
    }


    @Test
    public void shouldHaveSpecialHashForMinimumPersonName() {
        Name person = generateMinimumPersonalName();

        assertEquals("Hash for full person name differs!", MINIMUM_PERSON_HASHCODE, person.hashCode());
    }


    @Test
    public void shouldIncludeSnAndGivenNameInToStringWhenGivenAMinimumPerson() {
        Name person = generateMinimumPersonalName();

        assertTrue(
                "Must contain the giveName and the surname: " + person.toString(),
                person.toString().contains(MINIMUM_PERSON_STRING)
        );
    }


    @Test
    public void shouldReturnTrueWhenGivenItselfAsComparison() {
        Name result = generateMinimumPersonalName();

        assertTrue("Comparing with itself should be true!", result.equals(result));
    }

    @Test
    public void shouldReturnFalseWhenGivenAnotherObject() {
        Name result = generateMinimumPersonalName();

        assertFalse("Comparing with another classes object should fail!", result.equals(this));
    }

    @Test
    public void shouldReturnTrueWhenGivenTheSameFullObject() {
        Name personA = generateFullPersonName();

        Name personB = new NameBuilder().withPersonalName(personA).build();

        assertTrue("Two identical persons should match (personA=personB)!", personA.equals(personB));
        assertTrue("Two identical persons should match (personB=personA)!", personB.equals(personA));
    }


    @Test
    public void shouldGiveBackNonEmptyErrorListWithoutGivenName() {
        service
                .withSn(SN)
                .withSnPostfix(SN_POSTFIX);

        service.validate();
        List<String> result = service.getValidationResults();

        assertTrue("There should be at least one error (given name missing)", result.size() >= 1);
    }


    @Test
    public void shouldFailWithoutGivenName() {
        service
                .withSn(SN)
                .withSnPostfix(SN_POSTFIX);

        try {
            service.build();

            fail("An IllegalStateException should have been thrown!");
        } catch (IllegalStateException e) {
            assertTrue(
                    "Exception match does not contain correct message!",
                    e.getMessage().contains("The given name is not set")
            );
        }
    }


    @Test
    public void shouldGiveGivenNamePrefixSnPrefixSnSnPostfixWhenAskedForFormalSn() {
        Name person = generateFullPersonName();

        assertEquals("The result string does not match!", FORMAL_SNSTRING, person.getFormalSn());
    }

    @Test
    public void shouldGiveAllDataWhenAskedForFormalFullName() {
        Name person = generateFullPersonName();

        assertEquals("The result string does not match!", FORMAL_FULL_NAME_STRING, person.getFormalFullName());
    }

    @Test
    public void shouldGiveGivenNameGivenNamePostfixSnWhenAskedForInformalFullName() {
        Name person = generateFullPersonName();

        assertEquals("The result string does not match!", INFORMAL_FULL_NAME_STRING, person.getInformalFullName());
    }


    @Before
    public void setUpService() {
        service = new NameBuilder();
    }

    @After
    public void tearDownService() {
        service = null;
    }
}
