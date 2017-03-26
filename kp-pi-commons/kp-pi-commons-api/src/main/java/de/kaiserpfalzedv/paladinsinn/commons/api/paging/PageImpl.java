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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-17
 */
public class PageImpl<T extends Serializable> implements Page<T> {
    private static final long serialVersionUID = -1245206955340210764L;


    private final ArrayList<T> data = new ArrayList<>();

    private PageRequest request;

    private long totalPages;
    private long totalElements;


    PageImpl(
            final Collection<T> data,
            final PageRequest request,
            final long totalPages,
            final long totalElements
    ) {
        this.data.addAll(data);
        this.request = request;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }


    @Override
    public List<T> getData() {
        return Collections.unmodifiableList(data);
    }

    @Override
    public long getPageSize() {
        return request.getPageSize();
    }

    @Override
    public long getCurrentPageNumber() {
        return request.getPageNumber();
    }

    @Override
    public long getCurrentPageSize() {
        return data.size();
    }

    @Override
    public long getTotalPages() {
        return totalPages;
    }

    @Override
    public long getTotalElements() {
        return totalElements;
    }

    @Override
    public PageRequest getCurrentRequest() {
        return request;
    }

    @Override
    public PageRequest getFirstPageRequest() {
        return request.build(1);
    }

    @Override
    public PageRequest getPreviousPageRequest() {
        long previousPage = (request.getPageNumber() > 1) ? request.getPageNumber() - 1 : 1;

        return request.build(previousPage);
    }

    @Override
    public PageRequest getNextPageRequest() {
        long nextPage = (request.getPageNumber() < totalPages) ? request.getPageNumber() + 1 : totalPages;

        return request.build(nextPage);
    }

    @Override
    public PageRequest getLastPageRequest() {
        return request.build(totalPages);
    }
}
