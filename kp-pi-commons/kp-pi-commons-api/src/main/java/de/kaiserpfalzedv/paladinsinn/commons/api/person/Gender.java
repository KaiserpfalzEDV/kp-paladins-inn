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

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public enum Gender {
    agender("Agender"),
    androgyne("Androgyne"),
    androgynous("Androgynous"),
    bigender("Bigender"),
    cis("Cis"),
    cisgender("Cisgender"),
    cis_femaile("Cis Female"),
    cis_male("Cis Male"),
    cis_man("Cis Man"),
    cis_woman("Cis Woman"),
    cisgender_female("Cisgender Female"),
    cisgender_male("Cisgender Male"),
    cisgender_man("Cisgender Man"),
    cisgender_woman("Cisgender Woman"),
    female_to_male("Female to Male"),
    ftm("FTM"),
    gender_fluid("Gender Fluid"),
    gender_nonconforming("Gender Nonconforming"),
    gender_questioning("Gender Questioning"),
    gender_variant("Gender Variant"),
    genderqueer("Genderqueer"),
    intersex("Intersex"),
    male_to_female("Male to Female"),
    mtf("MTF"),
    neither("Neither"),
    neutrois("Neutrois"),
    non_binary("Non-binary"),
    other("Other"),
    pangender("Pangender"),
    trans("Trans"),
    trans_askerisk("Trans*"),
    trans_female("Trans Female"),
    trans_askerisk_female("Trans* Female"),
    trans_male("Trans Male"),
    trans_askerisk_male("Trans* Male"),
    trans_man("Trans Man"),
    trans_askerisk_man("Trans* Man"),
    trans_person("Trans Person"),
    trans_askerisk_person("Trans* Person"),
    trans_woman("Trans Woman"),
    trans_askerisk_woman("Trans* Woman"),
    trans_feminine("Transfeminine"),
    transgender("Transgender"),
    transgender_female("Transgender Female"),
    transgender_male("Transgender Male"),
    transgender_man("Transgender Man"),
    transgender_person("Transgender Person"),
    transgender_woman("Transgender Woman"),
    transmasculine("Transmasculine"),
    transsexual("Transsexual"),
    transsexual_female("Transsexual Female"),
    transsexual_male("Transsexual Male"),
    transsexual_man("Transsexual Man"),
    transsexual_person("Transsexual Person"),
    transsexual_woman("Transsexual Woman"),
    two_spirit("Two-Spirit");

    private String facebookString;
    private Gender(final String facebookString) {
        this.facebookString = facebookString;
    }


}
