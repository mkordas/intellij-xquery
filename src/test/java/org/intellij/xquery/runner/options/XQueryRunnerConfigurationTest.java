/*
 * Copyright 2013 Grzegorz Ligas <ligasgr@gmail.com> and other contributors (see the CONTRIBUTORS file).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.intellij.xquery.runner.options;

import org.junit.Before;
import org.junit.Test;

import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.intellij.xquery.runner.options.XQueryRunnerType.MARKLOGIC;
import static org.intellij.xquery.runner.options.XQueryRunnerType.SAXON;
import static org.intellij.xquery.util.XmlUtils.*;
import static org.junit.Assert.assertThat;

/**
 * User: ligasgr
 * Date: 06/10/13
 * Time: 19:32
 */
public class XQueryRunnerConfigurationTest {

    private static final String CONFIG_PATH = "/my/path/to/config/file.xml";
    private static final String LIBRARY_PATH = "/path/to/user/library.jar";
    private static final String XML_TEMPLATE = "<runner-configuration %s='%s'/>";
    private static final String NAME_FIELD = "name";
    private static final String PASSWORD = "password";
    private static final String USER = "user";
    private static final String PORT = "1234";
    private static final String NAME = "name";
    private static final String HOST = "localhost";
    private static final String TYPE_FIELD = "type";
    private static final String HOST_FIELD = "host";
    private static final String CONFIG_ENABLED_FIELD = "configEnabled";
    private static final String CONFIG_FILE_FIELD = "configFile";
    private static final String PORT_FIELD = "port";
    private static final String USERNAME_FIELD = "username";
    private static final String PASSWORD_FIELD = "password";
    private static final String USER_DEFINED_LIBRARY_ENABLED = "userDefinedLibraryEnabled";
    private static final String USER_DEFINED_LIBRARY_PATH = "userDefinedLibraryPath";
    private static final String CONFIG_ATTRIBUTE_XPATH = "/runner-configuration/@";
    private static final String VALUE = "1";
    private static final String DIFFERENT_VALUE = "2";
    private XQueryRunnerConfiguration runnerConfiguration;
    private XQueryRunnerConfiguration runnerConfiguration2;

    @Before
    public void setUp() throws Exception {
        runnerConfiguration = new XQueryRunnerConfiguration();
        runnerConfiguration2 = new XQueryRunnerConfiguration();
    }

    @Test
    public void shouldPersistRunnerName() throws Exception {
        runnerConfiguration.NAME = NAME;

        String xml = serializeToXml(runnerConfiguration);

        assertThat(the(xml), hasXPath(CONFIG_ATTRIBUTE_XPATH + NAME_FIELD, equalTo(NAME)));
    }

    @Test
    public void shouldPersistRunnerType() throws Exception {
        runnerConfiguration.TYPE = SAXON;

        String xml = serializeToXml(runnerConfiguration);

        assertThat(the(xml), hasXPath(CONFIG_ATTRIBUTE_XPATH + TYPE_FIELD, equalTo(SAXON.toString())));
    }

    @Test
    public void shouldPersistConfigurationFileActivity() throws Exception {
        runnerConfiguration.CONFIG_ENABLED = true;

        String xml = serializeToXml(runnerConfiguration);

        assertThat(the(xml), hasXPath(CONFIG_ATTRIBUTE_XPATH + CONFIG_ENABLED_FIELD, equalTo(TRUE.toString())));
    }

    @Test
    public void shouldPersistConfigurationFilePath() throws Exception {
        runnerConfiguration.CONFIG_FILE = CONFIG_PATH;

        String xml = serializeToXml(runnerConfiguration);

        assertThat(the(xml), hasXPath(CONFIG_ATTRIBUTE_XPATH + CONFIG_FILE_FIELD, equalTo(CONFIG_PATH)));
    }

    @Test
    public void shouldPersistHost() throws Exception {
        runnerConfiguration.HOST = HOST;

        String xml = serializeToXml(runnerConfiguration);

        assertThat(the(xml), hasXPath(CONFIG_ATTRIBUTE_XPATH + HOST_FIELD, equalTo(HOST)));
    }

    @Test
    public void shouldPersistPort() throws Exception {
        runnerConfiguration.PORT = PORT;

        String xml = serializeToXml(runnerConfiguration);

        assertThat(the(xml), hasXPath(CONFIG_ATTRIBUTE_XPATH + PORT_FIELD, equalTo(PORT)));
    }

    @Test
    public void shouldPersistUsername() throws Exception {
        runnerConfiguration.USERNAME = USER;

        String xml = serializeToXml(runnerConfiguration);

        assertThat(the(xml), hasXPath(CONFIG_ATTRIBUTE_XPATH + USERNAME_FIELD, equalTo(USER)));
    }

    @Test
    public void shouldPersistPassword() throws Exception {
        runnerConfiguration.PASSWORD = PASSWORD;

        String xml = serializeToXml(runnerConfiguration);

        assertThat(the(xml), hasXPath(CONFIG_ATTRIBUTE_XPATH + PASSWORD_FIELD, equalTo(PASSWORD)));
    }

    @Test
    public void shouldPersistUserDefinedLibraryActivity() throws Exception {
        runnerConfiguration.USER_DEFINED_LIBRARY_ENABLED = true;

        String xml = serializeToXml(runnerConfiguration);

        assertThat(the(xml), hasXPath(CONFIG_ATTRIBUTE_XPATH + USER_DEFINED_LIBRARY_ENABLED, equalTo(TRUE.toString())));
    }

    @Test
    public void shouldPersistUserDefinedLibraryPath() throws Exception {
        runnerConfiguration.USER_DEFINED_LIBRARY_PATH = LIBRARY_PATH;

        String xml = serializeToXml(runnerConfiguration);

        assertThat(the(xml), hasXPath(CONFIG_ATTRIBUTE_XPATH + USER_DEFINED_LIBRARY_PATH, equalTo(LIBRARY_PATH)));
    }

    @Test
    public void shouldReadRunnerName() throws Exception {
        String xml = format(XML_TEMPLATE, NAME_FIELD, NAME);

        runnerConfiguration = deserializeFromXml(rootElement(xml), runnerConfiguration);

        assertThat(runnerConfiguration.NAME, is(equalTo(NAME)));
    }

    @Test
    public void shouldReadRunnerType() throws Exception {
        String xml = format(XML_TEMPLATE, TYPE_FIELD, MARKLOGIC.toString());

        runnerConfiguration = deserializeFromXml(rootElement(xml), runnerConfiguration);

        assertThat(runnerConfiguration.TYPE, is(equalTo(MARKLOGIC)));
    }

    @Test
    public void shouldReadConfigurationFileActivity() throws Exception {
        String xml = format(XML_TEMPLATE, CONFIG_ENABLED_FIELD, TRUE.toString());

        runnerConfiguration = deserializeFromXml(rootElement(xml), runnerConfiguration);

        assertThat(runnerConfiguration.CONFIG_ENABLED, is(equalTo(TRUE)));
    }

    @Test
    public void shouldReadConfigurationFilePath() throws Exception {
        String xml = format(XML_TEMPLATE, CONFIG_FILE_FIELD, CONFIG_PATH);

        runnerConfiguration = deserializeFromXml(rootElement(xml), runnerConfiguration);

        assertThat(runnerConfiguration.CONFIG_FILE, is(equalTo(CONFIG_PATH)));
    }

    @Test
    public void shouldReadHost() throws Exception {
        String xml = format(XML_TEMPLATE, HOST_FIELD, HOST);

        runnerConfiguration = deserializeFromXml(rootElement(xml), runnerConfiguration);

        assertThat(runnerConfiguration.HOST, is(equalTo(HOST)));
    }

    @Test
    public void shouldReadPort() throws Exception {
        String xml = format(XML_TEMPLATE, PORT_FIELD, PORT);

        runnerConfiguration = deserializeFromXml(rootElement(xml), runnerConfiguration);

        assertThat(runnerConfiguration.PORT, is(equalTo(PORT)));
    }

    @Test
    public void shouldReadUsername() throws Exception {
        String xml = format(XML_TEMPLATE, USERNAME_FIELD, USER);

        runnerConfiguration = deserializeFromXml(rootElement(xml), runnerConfiguration);

        assertThat(runnerConfiguration.USERNAME, is(equalTo(USER)));
    }

    @Test
    public void shouldReadPassword() throws Exception {
        String xml = format(XML_TEMPLATE, PASSWORD_FIELD, PASSWORD);

        runnerConfiguration = deserializeFromXml(rootElement(xml), runnerConfiguration);

        assertThat(runnerConfiguration.PASSWORD, is(equalTo(PASSWORD)));
    }

    @Test
    public void shouldReadUserDefinedLibraryActivity() throws Exception {
        String xml = format(XML_TEMPLATE, USER_DEFINED_LIBRARY_ENABLED, TRUE.toString());

        runnerConfiguration = deserializeFromXml(rootElement(xml), runnerConfiguration);

        assertThat(runnerConfiguration.USER_DEFINED_LIBRARY_ENABLED, is(equalTo(TRUE)));
    }

    @Test
    public void shouldReadUserDefinedLibraryPath() throws Exception {
        String xml = format(XML_TEMPLATE, USER_DEFINED_LIBRARY_PATH, LIBRARY_PATH);

        runnerConfiguration = deserializeFromXml(rootElement(xml), runnerConfiguration);

        assertThat(runnerConfiguration.USER_DEFINED_LIBRARY_PATH, is(equalTo(LIBRARY_PATH)));
    }

    @Test
    public void shouldBeEqualWhenItIsTheSameObject() throws Exception {
        assertThat(runnerConfiguration, is(equalTo(runnerConfiguration)));
    }

    @Test
    public void shouldNotBeEqualWhenObjectNull() throws Exception {
        assertThat(runnerConfiguration, is(not(equalTo(null))));
    }

    @Test
    public void shouldNotBeEqualWhenObjectOfDifferentClass() throws Exception {
        assertThat(runnerConfiguration, is(not(equalTo(new Object()))));
    }

    @Test
    public void shouldNotBeEqualWhenNameIsDifferent() throws Exception {
        runnerConfiguration.NAME = VALUE;
        runnerConfiguration2.NAME = DIFFERENT_VALUE;

        assertThat(runnerConfiguration, is(not(equalTo(runnerConfiguration2))));
    }

    @Test
    public void shouldBeEqualWhenNameIsTheSame() throws Exception {
        runnerConfiguration.NAME = VALUE;
        runnerConfiguration2.NAME = VALUE;

        assertThat(runnerConfiguration, is(equalTo(runnerConfiguration2)));
    }

    @Test
    public void shouldNotBeEqualWhenTypeIsDifferent() throws Exception {
        runnerConfiguration.TYPE = SAXON;
        runnerConfiguration2.TYPE = MARKLOGIC;

        assertThat(runnerConfiguration, is(not(equalTo(runnerConfiguration2))));
    }

    @Test
    public void shouldBeEqualWhenTypeIsTheSame() throws Exception {
        runnerConfiguration.TYPE = SAXON;
        runnerConfiguration2.TYPE = SAXON;

        assertThat(runnerConfiguration, is(equalTo(runnerConfiguration2)));
    }

    @Test
    public void shouldNotBeEqualWhenConfigFileActivityIsDifferent() throws Exception {
        runnerConfiguration.CONFIG_ENABLED = true;
        runnerConfiguration2.CONFIG_ENABLED = false;

        assertThat(runnerConfiguration, is(not(equalTo(runnerConfiguration2))));
    }

    @Test
    public void shouldBeEqualWhenConfigFileActivityIsTheSame() throws Exception {
        runnerConfiguration.CONFIG_ENABLED = true;
        runnerConfiguration2.CONFIG_ENABLED = true;

        assertThat(runnerConfiguration, is(equalTo(runnerConfiguration2)));
    }

    @Test
    public void shouldNotBeEqualWhenConfigFileIsDifferent() throws Exception {
        runnerConfiguration.CONFIG_FILE = VALUE;
        runnerConfiguration2.CONFIG_FILE = DIFFERENT_VALUE;

        assertThat(runnerConfiguration, is(not(equalTo(runnerConfiguration2))));
    }

    @Test
    public void shouldBeEqualWhenConfigFileIsTheSame() throws Exception {
        runnerConfiguration.CONFIG_FILE = VALUE;
        runnerConfiguration2.CONFIG_FILE = VALUE;

        assertThat(runnerConfiguration, is(equalTo(runnerConfiguration2)));
    }

    @Test
    public void shouldNotBeEqualWhenHostIsDifferent() throws Exception {
        runnerConfiguration.HOST = VALUE;
        runnerConfiguration2.HOST = DIFFERENT_VALUE;

        assertThat(runnerConfiguration, is(not(equalTo(runnerConfiguration2))));
    }

    @Test
    public void shouldBeEqualWhenHostIsTheSame() throws Exception {
        runnerConfiguration.HOST = VALUE;
        runnerConfiguration2.HOST = VALUE;

        assertThat(runnerConfiguration, is(equalTo(runnerConfiguration2)));
    }

    @Test
    public void shouldNotBeEqualWhenPortIsDifferent() throws Exception {
        runnerConfiguration.PORT = VALUE;
        runnerConfiguration2.PORT = DIFFERENT_VALUE;

        assertThat(runnerConfiguration, is(not(equalTo(runnerConfiguration2))));
    }

    @Test
    public void shouldBeEqualWhenPortIsTheSame() throws Exception {
        runnerConfiguration.PORT = VALUE;
        runnerConfiguration2.PORT = VALUE;

        assertThat(runnerConfiguration, is(equalTo(runnerConfiguration2)));
    }

    @Test
    public void shouldNotBeEqualWhenUsernameIsDifferent() throws Exception {
        runnerConfiguration.USERNAME = VALUE;
        runnerConfiguration2.USERNAME = DIFFERENT_VALUE;

        assertThat(runnerConfiguration, is(not(equalTo(runnerConfiguration2))));
    }

    @Test
    public void shouldBeEqualWhenUsernameIsTheSame() throws Exception {
        runnerConfiguration.USERNAME = VALUE;
        runnerConfiguration2.USERNAME = VALUE;

        assertThat(runnerConfiguration, is(equalTo(runnerConfiguration2)));
    }

    @Test
    public void shouldNotBeEqualWhenPasswordIsDifferent() throws Exception {
        runnerConfiguration.PASSWORD = VALUE;
        runnerConfiguration2.PASSWORD = DIFFERENT_VALUE;

        assertThat(runnerConfiguration, is(not(equalTo(runnerConfiguration2))));
    }

    @Test
    public void shouldBeEqualWhenPasswordIsTheSame() throws Exception {
        runnerConfiguration.PASSWORD = VALUE;
        runnerConfiguration2.PASSWORD = VALUE;

        assertThat(runnerConfiguration, is(equalTo(runnerConfiguration2)));
    }

    @Test
    public void shouldNotBeEqualWhenUserDefinedLibraryActivityIsDifferent() throws Exception {
        runnerConfiguration.USER_DEFINED_LIBRARY_ENABLED = true;
        runnerConfiguration2.USER_DEFINED_LIBRARY_ENABLED = false;

        assertThat(runnerConfiguration, is(not(equalTo(runnerConfiguration2))));
    }

    @Test
    public void shouldBeEqualWhenUserDefinedLibraryActivityIsTheSame() throws Exception {
        runnerConfiguration.USER_DEFINED_LIBRARY_ENABLED = true;
        runnerConfiguration2.USER_DEFINED_LIBRARY_ENABLED = true;

        assertThat(runnerConfiguration, is(equalTo(runnerConfiguration2)));
    }

    @Test
    public void shouldNotBeEqualWhenUserDefinedLibraryPathIsDifferent() throws Exception {
        runnerConfiguration.USER_DEFINED_LIBRARY_PATH = VALUE;
        runnerConfiguration2.USER_DEFINED_LIBRARY_PATH = DIFFERENT_VALUE;

        assertThat(runnerConfiguration, is(not(equalTo(runnerConfiguration2))));
    }

    @Test
    public void shouldBeEqualWhenUserDefinedLibraryPathIsTheSame() throws Exception {
        runnerConfiguration.USER_DEFINED_LIBRARY_PATH = VALUE;
        runnerConfiguration2.USER_DEFINED_LIBRARY_PATH = VALUE;

        assertThat(runnerConfiguration, is(equalTo(runnerConfiguration2)));
    }

    @Test
    public void shouldCreateCloneWhichIsEqual() throws Exception {
        assertThat(runnerConfiguration, is(equalTo(runnerConfiguration.clone())));
    }
}
