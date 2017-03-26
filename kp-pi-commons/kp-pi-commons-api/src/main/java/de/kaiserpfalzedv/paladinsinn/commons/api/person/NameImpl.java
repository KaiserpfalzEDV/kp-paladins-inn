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

package de.kaiserpfalzedv.paladinsinn.commons.api.person;

import java.util.Objects;

/**
 * This is a full name.
 * 
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class NameImpl implements Name {
    private static final long serialVersionUID = -2244644692239645246L;
    
    private String givenNamePrefix;
    private String givenName;
    private String givenNamePostfix;

    private String snPrefix;
    private String sn;
    private String snPostfix;

    NameImpl(
            final String givenNamePrefix,
            final String givenName,
            final String givenNamePostfix,
            final String snPrefix,
            final String sn,
            final String snPostfix)
    {
        this.givenNamePrefix = givenNamePrefix;
        this.givenName = givenName;
        this.givenNamePostfix = givenNamePostfix;
        this.snPrefix = snPrefix;
        this.sn = sn;
        this.snPostfix = snPostfix;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getGivenNamePrefix(),
                getGivenName(),
                getGivenNamePostfix(),
                getSnPrefix(),
                getSn(),
                getSnPostfix()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NameImpl)) return false;
        Name that = (Name) o;
        return Objects.equals(getGivenNamePrefix(), that.getGivenNamePrefix()) &&
                Objects.equals(getGivenName(), that.getGivenName()) &&
                Objects.equals(getGivenNamePostfix(), that.getGivenNamePostfix()) &&
                Objects.equals(getSnPrefix(), that.getSnPrefix()) &&
                Objects.equals(getSn(), that.getSn()) &&
                Objects.equals(getSnPostfix(), that.getSnPostfix());
    }

    @Override
    public String getGivenNamePrefix() {
        return givenNamePrefix;
    }

    @Override
    public String getGivenName() {
        return givenName;
    }

    @Override
    public String getGivenNamePostfix() {
        return givenNamePostfix;
    }

    @Override
    public String getSnPrefix() {
        return snPrefix;
    }

    @Override
    public String getSn() {
        return sn;
    }

    @Override
    public String getSnPostfix() {
        return snPostfix;
    }

    @Override
    public String getFormalSn() {
        StringBuilder result = new StringBuilder();

        if (isNotEmpty(givenNamePrefix))
            result.append(givenNamePrefix).append(' ');

        if (isNotEmpty(snPrefix))
            result.append(snPrefix).append(' ');

        result.append(sn);

        if (isNotEmpty(snPostfix))
            result.append(' ').append(snPostfix);

        return result.toString();
    }

    @Override
    public String getInformalFullName() {
        StringBuilder result = new StringBuilder();

        result.append(givenName);

        if (isNotEmpty(givenNamePostfix))
            result.append(' ').append(givenNamePostfix);

        result.append(' ').append(sn);

        return result.toString();
    }

    @Override
    public String getFormalFullName() {
        StringBuilder result = new StringBuilder();

        if (isNotEmpty(givenNamePrefix))
            result.append(givenNamePrefix).append(' ');

        result.append(givenName);

        if (isNotEmpty(givenNamePostfix))
            result.append(' ').append(givenNamePostfix);

        if (isNotEmpty(snPrefix))
            result.append(' ').append(snPrefix);

        result.append(' ').append(sn);

        if (isNotEmpty(snPostfix))
            result.append(' ').append(snPostfix);

        return result.toString();
    }

    private boolean isNotEmpty(final String value) {
        return value != null && !value.isEmpty();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(getClass().getSimpleName())
                .append("@")
                .append(System.identityHashCode(this)).append("{")

                .append(getFormalFullName())

                .append('}').toString();
    }
}
