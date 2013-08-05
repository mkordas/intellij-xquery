package org.intellij.xquery.runner.options;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * User: ligasgr
 * Date: 02/10/13
 * Time: 14:47
 */
public class XQueryRunnerConfigurable implements Configurable {
    private XQueryRunnerSettingsForm settingsForm;

    @Nls
    @Override
    public String getDisplayName() {
        return "XQuery Runners";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (settingsForm == null) {
            settingsForm = new XQueryRunnerSettingsForm(XQueryRunnerSettings.getInstance().getRunnerConfigurations());
        }
        return settingsForm.getFormComponent();
    }

    @Override
    public boolean isModified() {
        if (settingsForm != null) {
            return settingsForm.isModified(XQueryRunnerSettings.getInstance().getRunnerConfigurations());
        }
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
        if (settingsForm != null) {
            XQueryRunnerSettings.getInstance().setRunnerConfigurations(settingsForm.getCurrentConfigurations());
        }
    }

    @Override
    public void reset() {
        if (settingsForm != null) {
            settingsForm.refreshCurrentConfiguration(XQueryRunnerSettings.getInstance().getRunnerConfigurations());
        }
    }

    @Override
    public void disposeUIResources() {
        settingsForm = null;
    }
}
