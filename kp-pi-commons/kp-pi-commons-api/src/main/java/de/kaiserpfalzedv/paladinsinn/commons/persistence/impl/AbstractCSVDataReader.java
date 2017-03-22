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

package de.kaiserpfalzedv.paladinsinn.commons.persistence.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Scanner;

import de.kaiserpfalzedv.paladinsinn.commons.BuilderValidationException;
import de.kaiserpfalzedv.paladinsinn.commons.persistence.CrudService;
import de.kaiserpfalzedv.paladinsinn.commons.persistence.DataReader;
import de.kaiserpfalzedv.paladinsinn.commons.persistence.DuplicateEntityException;
import de.kaiserpfalzedv.paladinsinn.commons.persistence.DuplicateUniqueIdException;
import de.kaiserpfalzedv.paladinsinn.commons.persistence.Identifiable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-20
 */
public abstract class AbstractCSVDataReader<T extends Identifiable> implements DataReader<T> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractCSVDataReader.class);

    protected CrudService<T> crudService;

    public AbstractCSVDataReader(final CrudService<T> crudService) {
        this.crudService = crudService;
    }


    @Override
    public void read(URI uri) throws IOException {
        read(uri.toURL().openStream());
    }

    @Override
    public void read(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        scanner.useDelimiter(";");

        readAllLines(scanner);
    }

    @Override
    public void read(String fileName) throws FileNotFoundException {
        read(new File(fileName));
    }

    @Override
    public void read(File file) throws FileNotFoundException {
        read(new FileInputStream(file));
    }

    private void readAllLines(Scanner scanner) {
        while (scanner.hasNextLine()) {
            try {
                crudService.create(readSingleLine(scanner.nextLine()));
            } catch (BuilderValidationException | DuplicateEntityException e) {
                LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
            }
        }
    }

    public abstract T readSingleLine(final String line) throws BuilderValidationException, DuplicateUniqueIdException;
}
