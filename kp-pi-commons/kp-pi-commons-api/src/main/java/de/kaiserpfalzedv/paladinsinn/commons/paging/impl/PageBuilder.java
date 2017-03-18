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

package de.kaiserpfalzedv.paladinsinn.commons.paging.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import de.kaiserpfalzedv.paladinsinn.commons.Builder;
import de.kaiserpfalzedv.paladinsinn.commons.BuilderValidationException;
import de.kaiserpfalzedv.paladinsinn.commons.paging.Page;
import de.kaiserpfalzedv.paladinsinn.commons.paging.PageRequest;

/**
 * Builds a data page.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-17
 */
public class PageBuilder<T extends Serializable> implements Builder<Page<T>> {
    private final ArrayList<T> data = new ArrayList<>();

    private PageRequest request;

    private long totalPages;
    private long totalElements;


    @Override
    public Page<T> build() throws BuilderValidationException {
        calculateDefaults();
        validate();

        return new PageImpl<>(data, request, totalPages, totalElements);
    }

    private void calculateDefaults() {
        if (request == null) {
            if (totalPages != 0 && totalElements != 0) {
                long pageSize = totalElements / totalPages + ((totalElements % totalPages != 0) ? 1 : 0);
                request = new PageRequestImpl(1, pageSize);
            } else {
                request = new PageRequestImpl(1, data.size());
            }
        }

        if (totalElements == 0) {
            totalElements = data.size();
            totalPages = 1;

            request = new PageRequestImpl(1, totalElements);
        }

        if (totalPages == 0) {
            totalPages = totalElements / request.getPageSize() + ((totalElements % request.getPageSize() != 0) ? 1 : 0);
        }
    }

    @Override
    public void validate() throws BuilderValidationException {
        HashSet<String> failures = new HashSet<>(2);

        if (request == null) {
            failures.add("No request information given. Can't build a page!");
        }

        if (totalElements < 0) {
            failures.add("Can't have less than zero elments in a list!");
        }

        if (failures.size() >= 1) {
            throw new BuilderValidationException(PageImpl.class, failures);
        }
    }


    public PageBuilder<T> withData(final Collection<T> data) {
        if (data != null) {
            this.data.addAll(data);
        }
        return this;
    }

    public PageBuilder<T> withRequest(final PageRequest request) {
        this.request = request;
        return this;
    }

    /**
     * An all in one setter. It takes the full data set, the page number to retrieve and the size of the pages.
     *
     * @param data       The full data set. The page will be selected from this data set.
     * @param pageNumber The page number of the data to retrieve.
     * @param pageSize   The size of the page to rerieve.
     *
     * @return the page builder itself.
     */
    public PageBuilder<T> withPage(final Collection<T> data, final long pageNumber, final long pageSize) {
        this.totalElements = data.size();
        this.totalPages = data.size() / pageSize + ((data.size() % pageSize != 0) ? 1 : 0);

        long currentPage = pageNumber <= totalPages ? pageNumber : totalPages;

        retrieveDataForPage(data, pageSize, currentPage);

        this.request = new PageRequestImpl(currentPage, pageSize);
        return this;
    }

    /**
     * Retrieves the page data content from the data set and prepares the building of the page.
     *
     * @param data       The full data set to read the single page from.
     * @param pageSize   The size of the data page to read.
     * @param pageNumber the page number of the data page to read.
     */
    private void retrieveDataForPage(Collection<T> data, long pageSize, long pageNumber) {
        long firstElement = (pageNumber - 1) * pageSize;
        long lastElement = (pageNumber - 1) * pageSize + pageSize - 1;
        long current = 0;
        Iterator<T> it = data.iterator();

        while (it.hasNext() && current < firstElement) {
            it.next();
            current++;
        }

        while (it.hasNext() && current <= lastElement && current < totalElements) {
            this.data.add(it.next());
            current++;
        }
    }

    public PageBuilder<T> withTotalPages(final long totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public PageBuilder<T> withTotalElements(final long totalElements) {
        this.totalElements = totalElements;
        return this;
    }
}
