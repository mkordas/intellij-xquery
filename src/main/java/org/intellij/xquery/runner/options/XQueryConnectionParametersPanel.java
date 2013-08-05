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

import com.intellij.openapi.ui.LabeledComponent;

import javax.swing.*;

/**
 * User: ligasgr
 * Date: 06/10/13
 * Time: 17:51
 */
public class XQueryConnectionParametersPanel {
    private JPanel mainPanel;
    private LabeledComponent<JTextField> host;
    private LabeledComponent<JTextField> port;
    private LabeledComponent<JTextField> username;
    private LabeledComponent<JTextField> password;

    public JPanel getPanel() {
        return mainPanel;
    }

    public String getHost() {
        return host.getComponent().getText();
    }

    public String getPort() {
        return port.getComponent().getText();
    }

    public String getUsername() {
        return username.getComponent().getText();
    }

    public String getPassword() {
        return password.getComponent().getText();
    }

    public void init(XQueryRunnerType type, String host, String port, String username, String password) {
        mainPanel.setVisible(type.connectionPropertiesAreSupported());
        this.host.getComponent().setText(host);
        this.port.getComponent().setText(port);
        this.username.getComponent().setText(username);
        this.password.getComponent().setText(password);
    }
}
