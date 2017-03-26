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

package de.kaiserpfalzedv.paladinsinn.commons.paging;

import java.util.ArrayList;

import de.kaiserpfalzedv.paladinsinn.commons.api.BuilderValidationException;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.Page;
import de.kaiserpfalzedv.paladinsinn.commons.api.paging.PageBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-17
 */
public class PageTest {
    private static final Logger LOG = LoggerFactory.getLogger(PageTest.class);

    private static final long TOTAL_ELEMENTS = 9;
    private static final ArrayList<String> DATA = new ArrayList<>((int) TOTAL_ELEMENTS);
    private static final long PAGE_SIZE = 2;
    private static final long TOTAL_PAGES = TOTAL_ELEMENTS / PAGE_SIZE + ((TOTAL_ELEMENTS % PAGE_SIZE != 0) ? 1 : 1);

    static {
        DATA.add("ABC");
        DATA.add("DEF");
        DATA.add("GHI");
        DATA.add("JKL");
        DATA.add("MNO");
        DATA.add("PQR");
        DATA.add("STU");
        DATA.add("VWX");
        DATA.add("YZ");
    }

    @Test
    public void shouldReturnThirdPageWhenAskedForIt() throws BuilderValidationException {
        Page<String> service = generatePage(3);

        String[] data = new String[]{service.getData().get(0), service.getData().get(1)};
        String[] expected = new String[]{DATA.get(4), DATA.get(5)};

        assertArrayEquals("Wrong elements in result set", expected, data);

        assertEquals("Wrong current page", 3, service.getCurrentPageNumber());
        assertEquals("Wrong current page size", PAGE_SIZE, service.getCurrentPageSize());
        assertEquals("Wrong default page size", PAGE_SIZE, service.getPageSize());
        assertEquals("Wrong number of total elements", TOTAL_ELEMENTS, service.getTotalElements());
        assertEquals("Wrong number of total pages", TOTAL_PAGES, service.getTotalPages());
    }

    private Page<String> generatePage(long page) throws BuilderValidationException {
        return new PageBuilder<String>()
                .withPage(DATA, page, PAGE_SIZE)
                .build();
    }

    @Test
    public void shouldReturnFirstPageRequestWhenAskedWithSecondPageForIt() throws BuilderValidationException {
        Page<String> service = generatePage(2);

        assertEquals("Wrong page request found!", service.getFirstPageRequest(), service.getPreviousPageRequest());
    }

    @Test
    public void shouldReturnFirstPageRequestWhenAskedWithFirstPageRequestForIt() throws BuilderValidationException {
        Page<String> service = generatePage(1);

        assertEquals("Wrong page request found!", service.getCurrentRequest(), service.getFirstPageRequest());
    }

    @Test
    public void shouldReturnLastPageRequestWhenAskedWithForthPageForIt() throws BuilderValidationException {
        Page<String> service = generatePage(4);

        assertEquals("Wrong page request found!", service.getLastPageRequest(), service.getNextPageRequest());
    }

    @Test
    public void shouldReturnLastPageRequestWhenAskedWithLastPageRequestForIt() throws BuilderValidationException {
        Page<String> service = generatePage(5);

        assertEquals("Wrong page request found!", service.getCurrentRequest(), service.getLastPageRequest());
    }
}
