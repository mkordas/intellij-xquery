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

import org.intellij.xquery.icons.XQueryIcons;

import javax.swing.*;

/**
 * User: ligasgr
 * Date: 02/10/13
 * Time: 14:19
 */
public enum XQueryRunnerType {
    SAXON("Saxon", true, false), MARKLOGIC("MarkLogic", false, true), EXIST("eXist", false, true), BASEX("BaseX", false, true), SEDNA("Sedna", false, true);

    private String presentableName;
    private boolean configFileSupported;
    private boolean database;

    private XQueryRunnerType(String presentableName, boolean configFileSupported, boolean database) {
        this.presentableName = presentableName;
        this.configFileSupported = configFileSupported;
        this.database = database;
    }

    public String getPresentableName() {
        return presentableName;
    }

    public boolean configFileIsSupported() {
        return configFileSupported;
    }

    public boolean connectionPropertiesAreSupported() {
        return database;
    }

    public Icon getIcon() {
        return null;
    }
}
