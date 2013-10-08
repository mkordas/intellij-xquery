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

import com.intellij.util.xmlb.annotations.Attribute;
import com.intellij.util.xmlb.annotations.Tag;

/**
 * User: ligasgr
 * Date: 02/10/13
 * Time: 14:16
 */
@Tag("runner-configuration")
public class XQueryRunnerConfiguration implements Cloneable {
    @Attribute("name") public String NAME;
    @Attribute("type") public XQueryRunnerType TYPE;
    @Attribute("configEnabled") public boolean CONFIG_ENABLED;
    @Attribute("configFile") public String CONFIG_FILE;
    @Attribute("host") public String HOST;
    @Attribute("port") public String PORT;
    @Attribute("username") public String USERNAME;
    @Attribute("password") public String PASSWORD;
    @Attribute("userDefinedLibraryEnabled") public boolean USER_DEFINED_LIBRARY_ENABLED;
    @Attribute("userDefinedLibraryPath") public String USER_DEFINED_LIBRARY_PATH;

    public XQueryRunnerConfiguration() {
    }

    public XQueryRunnerConfiguration(String name, XQueryRunnerType type) {
        NAME = name;
        TYPE = type;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XQueryRunnerConfiguration that = (XQueryRunnerConfiguration) o;

        if (CONFIG_ENABLED != that.CONFIG_ENABLED) return false;
        if (USER_DEFINED_LIBRARY_ENABLED != that.USER_DEFINED_LIBRARY_ENABLED) return false;
        if (CONFIG_FILE != null ? !CONFIG_FILE.equals(that.CONFIG_FILE) : that.CONFIG_FILE != null) return false;
        if (HOST != null ? !HOST.equals(that.HOST) : that.HOST != null) return false;
        if (NAME != null ? !NAME.equals(that.NAME) : that.NAME != null) return false;
        if (PASSWORD != null ? !PASSWORD.equals(that.PASSWORD) : that.PASSWORD != null) return false;
        if (PORT != null ? !PORT.equals(that.PORT) : that.PORT != null) return false;
        if (TYPE != that.TYPE) return false;
        if (USERNAME != null ? !USERNAME.equals(that.USERNAME) : that.USERNAME != null) return false;
        if (USER_DEFINED_LIBRARY_PATH != null ? !USER_DEFINED_LIBRARY_PATH.equals(that.USER_DEFINED_LIBRARY_PATH) :
                that.USER_DEFINED_LIBRARY_PATH != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = NAME != null ? NAME.hashCode() : 0;
        result = 31 * result + (TYPE != null ? TYPE.hashCode() : 0);
        result = 31 * result + (CONFIG_ENABLED ? 1 : 0);
        result = 31 * result + (CONFIG_FILE != null ? CONFIG_FILE.hashCode() : 0);
        result = 31 * result + (HOST != null ? HOST.hashCode() : 0);
        result = 31 * result + (PORT != null ? PORT.hashCode() : 0);
        result = 31 * result + (USERNAME != null ? USERNAME.hashCode() : 0);
        result = 31 * result + (PASSWORD != null ? PASSWORD.hashCode() : 0);
        result = 31 * result + (USER_DEFINED_LIBRARY_ENABLED ? 1 : 0);
        result = 31 * result + (USER_DEFINED_LIBRARY_PATH != null ? USER_DEFINED_LIBRARY_PATH.hashCode() : 0);
        return result;
    }
}