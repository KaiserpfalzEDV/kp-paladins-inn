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
import java.util.List;

/**
 * A data page.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-17
 */
public interface Page<T extends Serializable> extends Serializable {
    /**
     * @return The data content of the page retrieved.
     */
    List<T> getData();

    /**
     * @return The default page size requested. The {@link #getCurrentPageSize()} can vary from this value, since the
     * last page can be smaller than the other ones.
     */
    long getPageSize();

    /**
     * @return The current page number of this data set.
     */
    long getCurrentPageNumber();

    /**
     * @return The current page size of this data set. The page size can be smaller than the requested page size on the
     * last page.
     */
    long getCurrentPageSize();

    /**
     * @return The total number of pages of the requested data set.
     */
    long getTotalPages();

    /**
     * @return The total number of elements on all pages of the requested data set.
     */
    long getTotalElements();

    /**
     * @return The current request definition.
     */
    PageRequest getCurrentRequest();

    /**
     * @return The {@link PageRequest} to retrieve the first page of the data set.
     */
    PageRequest getFirstPageRequest();

    /**
     * Retrieves a {@link PageRequest} for the previous page. If the current page is the first page, then a request
     * information set for the same page again is returned.
     *
     * @return The request information to retrieve the previous page of the data set.
     */
    PageRequest getPreviousPageRequest();

    /**
     * Retrieves a {@link PageRequest} for the next page. If the current page is the last page, then a request
     * information set for the same page again is returned.
     *
     * @return The request information to retrieve the next page of the data set.
     */
    PageRequest getNextPageRequest();

    /**
     * @return The {@link PageRequest} to retrieve the last page of the data set.
     */
    PageRequest getLastPageRequest();
}
