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
import javax.swing.event.DocumentListener;

/**
 * User: ligasgr
 * Date: 02/10/13
 * Time: 22:11
 */
public class XQueryRunnerConfigurationPanel {

    private JPanel mainPanel;
    private LabeledComponent<JTextField> name;
    private XQueryConfigurationFilePanel configurationFilePanel;
    private XQueryConnectionParametersPanel connectionParametersPanel;
    private XQueryUserDefinedLibraryPanel userDefinedLibraryPanel;
    private XQueryRunnerType runnerType;

    public XQueryRunnerConfigurationPanel(final XQueryRunnerConfiguration runnerConfiguration, DocumentListener
            nameChangedListener) {
        runnerType = runnerConfiguration.TYPE;
        name.getComponent().setText(runnerConfiguration.NAME);
        name.getComponent().getDocument().addDocumentListener(nameChangedListener);
        initConfigurationFilePanel(runnerConfiguration);
        initConnectionParametersPanel(runnerConfiguration);
        initUserDefinedLibraryPanel(runnerConfiguration);
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public XQueryRunnerConfiguration getCurrentConfigurationState() {
        XQueryRunnerConfiguration currentConfiguration = new XQueryRunnerConfiguration();
        currentConfiguration.NAME = name.getComponent().getText();
        currentConfiguration.TYPE = runnerType;
        currentConfiguration.CONFIG_ENABLED = configurationFilePanel.isConfigurationEnabled();
        currentConfiguration.CONFIG_FILE = configurationFilePanel.getConfigFile();
        currentConfiguration.HOST = connectionParametersPanel.getHost();
        currentConfiguration.PORT = connectionParametersPanel.getPort();
        currentConfiguration.USERNAME = connectionParametersPanel.getUsername();
        currentConfiguration.PASSWORD = connectionParametersPanel.getPassword();
        currentConfiguration.USER_DEFINED_LIBRARY_ENABLED = userDefinedLibraryPanel.isUserDefinedLibraryEnabled();
        currentConfiguration.USER_DEFINED_LIBRARY_PATH = userDefinedLibraryPanel.getUserDefinedLibraryPath();
        return currentConfiguration;
    }

    private void initConfigurationFilePanel(XQueryRunnerConfiguration cfg) {
        configurationFilePanel.init(cfg.TYPE, cfg.CONFIG_ENABLED, cfg.CONFIG_FILE);
    }

    private void initConnectionParametersPanel(XQueryRunnerConfiguration cfg) {
        connectionParametersPanel.init(cfg.TYPE, cfg.HOST, cfg.PORT, cfg.USERNAME, cfg.PASSWORD);
    }

    private void initUserDefinedLibraryPanel(XQueryRunnerConfiguration cfg) {
        userDefinedLibraryPanel.init(cfg.USER_DEFINED_LIBRARY_ENABLED, cfg.USER_DEFINED_LIBRARY_PATH);
    }
}
