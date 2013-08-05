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

import com.intellij.openapi.ui.DialogWrapper;
import org.intellij.xquery.runner.options.XQueryRunnerSettings;
import org.intellij.xquery.runner.options.XQueryRunnerSettingsForm;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * User: ligasgr
 * Date: 06/10/13
 * Time: 21:59
 */
public class XQueryConfigureRunnerConfigurationDialog extends DialogWrapper {

    private XQueryRunnerSettingsForm settingsForm;

    protected XQueryConfigureRunnerConfigurationDialog(JComponent parent) {
        super(parent, false);
        setTitle("XQuery Runners");
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        settingsForm = new XQueryRunnerSettingsForm(XQueryRunnerSettings.getInstance().getRunnerConfigurations());
        return settingsForm.getFormComponent();
    }

    @Override
    protected void createDefaultActions() {
        myOKAction = new MyOkAction();
        myCancelAction = new MyCancelAction();
    }

    private class MyOkAction extends OkAction {
        @Override
        protected void doAction(ActionEvent e) {
            XQueryRunnerSettings.getInstance().setRunnerConfigurations(settingsForm.getCurrentConfigurations());
            super.doAction(e);
        }
    }

    private class MyCancelAction extends DialogWrapperAction {

        protected MyCancelAction() {
            super("Cancel");
        }

        @Override
        protected void doAction(ActionEvent e) {
            doCancelAction();
        }
    }
}
