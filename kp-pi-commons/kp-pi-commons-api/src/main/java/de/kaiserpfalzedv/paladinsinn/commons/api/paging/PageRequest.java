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

package de.kaiserpfalzedv.paladinsinn.commons.api.paging;

import java.io.Serializable;

/**
 * This page request describes the paging of the data. A page definition is given by the page number and page size.
 * First element is (page number * page size).
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-17
 */
public interface PageRequest extends Serializable {
    public static final long serialVersionUID = -8117789167434431687L;

    /**
     * @return the page to retrieve.
     */
    long getPageNumber();

    /**
     * Builds a new PageRequest with the same page size than the current request and the new page number given as
     * parameter.
     *
     * @param pageNumber The new page number to create the page request information for.
     *
     * @return the new page request informaton for the new page number.
     */
    default PageRequest build(final long pageNumber) {
        return new PageRequestImpl(pageNumber, getPageSize());
    }

    /**
     * @return the size of pages to retrieve.
     */
    long getPageSize();
}
