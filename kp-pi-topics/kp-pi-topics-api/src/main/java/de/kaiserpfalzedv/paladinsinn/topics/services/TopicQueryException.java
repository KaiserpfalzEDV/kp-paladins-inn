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

package de.kaiserpfalzedv.paladinsinn.topics.services;

import de.kaiserpfalzedv.paladinsinn.commons.PaladinsInnBaseException;

/**
 * A failure occured while working on the {@link TopicQuery}. The failed query can be retrieved from the exception by
 * {@link #getFailedQuery}.
 *
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-03-20
 */
public class TopicQueryException extends PaladinsInnBaseException {
    private TopicQuery failedQuery;

    public TopicQueryException(final TopicQuery query, final String message) {
        super(message);

        failedQuery = query;
    }

    public TopicQueryException(final TopicQuery query, final String message, final Throwable cause) {
        super(message, cause);

        failedQuery = query;
    }

    public TopicQueryException(final TopicQuery query, final Throwable cause) {
        super(cause);

        failedQuery = query;
    }


    public TopicQuery getFailedQuery() {
        return failedQuery;
    }
}
