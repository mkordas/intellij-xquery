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

package org.intellij.xquery.runner.ui;

import com.intellij.ui.ComboboxSpeedSearch;
import com.intellij.ui.ListCellRendererWrapper;
import com.intellij.ui.SortedComboBoxModel;
import org.intellij.xquery.runner.XQueryRunConfiguration;
import org.intellij.xquery.runner.options.XQueryRunnerConfiguration;
import org.intellij.xquery.runner.options.XQueryRunnerSettings;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;

/**
 * User: ligasgr
 * Date: 06/10/13
 * Time: 21:52
 */
public class XQueryRunnerConfigurationSelector {
    private final JComboBox runnersList;
    private final SortedComboBoxModel<Object> runnersModel = new SortedComboBoxModel<Object>(new Comparator<Object>() {
        public int compare(final Object runner, final Object runner1) {
            if (runner instanceof XQueryRunnerConfiguration && runner1 instanceof XQueryRunnerConfiguration) {
                return ((XQueryRunnerConfiguration) runner).NAME.compareToIgnoreCase(((XQueryRunnerConfiguration)
                        runner1).NAME);
            }
            return -1;
        }
    });

    public XQueryRunnerConfigurationSelector(JComboBox runnersList) {
        this.runnersList = runnersList;
        new ComboboxSpeedSearch(this.runnersList) {
            protected String getElementText(Object element) {
                if (element instanceof XQueryRunnerConfiguration) {
                    return ((XQueryRunnerConfiguration) element).NAME;
                } else if (element == null) {
                    return "<no runner>";
                }
                return super.getElementText(element);
            }
        };
        this.runnersList.setModel(runnersModel);
        this.runnersList.setRenderer(new ListCellRendererWrapper() {
            @Override
            public void customize(final JList list, final Object value, final int index, final boolean selected,
                                  final boolean hasFocus) {
                if (value instanceof XQueryRunnerConfiguration) {
                    final XQueryRunnerConfiguration runnerConfiguration = (XQueryRunnerConfiguration) value;
//                    setIcon(runnerConfiguration.TYPE.getIcon());
                    setText(runnerConfiguration.NAME);
                } else if (value == null) {
                    setText("<no runner>");
                }
            }
        });
    }


    public void applyTo(final XQueryRunConfiguration configuration) {
        Object selectedItem = this.runnersList.getSelectedItem();
        String name = null;
        if (selectedItem != null) {
            name = ((XQueryRunnerConfiguration) selectedItem).NAME;
        }
        configuration.setRunnerConfigurationName(name);

    }

    public void reset(final XQueryRunConfiguration configuration) {
        List<XQueryRunnerConfiguration> runnerConfigurations = XQueryRunnerSettings.getInstance()
                .getRunnerConfigurations();
        setRunners(runnerConfigurations);
        for (XQueryRunnerConfiguration cfg : runnerConfigurations) {
            if (cfg.NAME.equals(configuration.getRunnerConfigurationName())) {
                runnersModel.setSelectedItem(cfg);
                break;
            }
        }
    }

    private void setRunners(List<XQueryRunnerConfiguration> runnerConfigurations) {
        runnersModel.clear();
        runnersModel.add(null);
        for (XQueryRunnerConfiguration runnerConfiguration : runnerConfigurations) {
            runnersModel.add(runnerConfiguration);
        }
    }
}
