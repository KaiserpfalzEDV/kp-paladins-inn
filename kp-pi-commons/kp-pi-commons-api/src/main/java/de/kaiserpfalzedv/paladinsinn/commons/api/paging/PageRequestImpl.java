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

import java.util.Objects;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-17
 */
public class PageRequestImpl implements PageRequest {
    private static final long serialVersionUID = -4390847181944395810L;
    
    private long pageNumber;
    private long pageSize;

    public PageRequestImpl(final long pageNumber, final long pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPageNumber(), getPageSize());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageRequestImpl)) return false;
        PageRequestImpl that = (PageRequestImpl) o;
        return getPageNumber() == that.getPageNumber() &&
                getPageSize() == that.getPageSize();
    }

    @Override
    public String toString() {
        return new StringBuilder("PageRequestImpl@").append(System.identityHashCode(this))
                                                    .append("{pageNumber")
                                                    .append(pageNumber)
                                                    .append(", pageSize=")
                                                    .append(pageSize)
                                                    .append('}')
                                                    .toString();
    }

    @Override
    public long getPageNumber() {
        return pageNumber;
    }

    @Override
    public long getPageSize() {
        return pageSize;
    }
}
