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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class NameBuilder {
    /**
     * A list of errors during validation.
     */
    private final ArrayList<String> errors = new ArrayList<>(2);
    private String givenNamePrefix;
    private String givenName;
    private String givenNamePostfix;
    private String snPrefix;
    private String sn;
    private String snPostfix;

    public Name build() {
        validateBeforeBuild();
        
        return new NameImpl(
                givenNamePrefix, givenName, givenNamePostfix,
                snPrefix, sn, snPostfix
        );
    }


    private void validateBeforeBuild() {
        if (! validate()) {
            StringBuilder error = new StringBuilder("The personal name can't be build:\n");

            errors.forEach(e -> error.append("- ").append(e).append('\n'));

            throw new IllegalStateException(error.toString());
        }
    }


    public boolean validate() {
        boolean result = true;
        errors.clear();

        if (sn == null || sn.isEmpty()) {
            result = false;
            errors.add("The family name is not set.");
        }

        if (givenName == null || givenName.isEmpty()) {
            result = false;
            errors.add("The given name is not set.");
        }

        return result;
    }
    
    public List<String> getValidationResults() {
        return Collections.unmodifiableList(errors);
    }



    public NameBuilder withPersonalName(final Name person) {
        this.givenNamePrefix = person.getGivenNamePrefix();
        this.givenName = person.getGivenName();
        this.givenNamePostfix = person.getGivenNamePostfix();

        this.snPrefix = person.getSnPrefix();
        this.sn = person.getSn();
        this.snPostfix = person.getSnPostfix();

        return this;
    }


    public NameBuilder withGivenNamePrefix(String givenNamePrefix) {
        this.givenNamePrefix = givenNamePrefix;
        return this;
    }

    public NameBuilder withGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public NameBuilder withGivenNamePostfix(String givenNamePostfix) {
        this.givenNamePostfix = givenNamePostfix;
        return this;
    }

    public NameBuilder withSnPrefix(String snPrefix) {
        this.snPrefix = snPrefix;
        return this;
    }

    public NameBuilder withSn(String sn) {
        this.sn = sn;
        return this;
    }

    public NameBuilder withSnPostfix(String snPostfix) {
        this.snPostfix = snPostfix;
        return this;
    }
}
