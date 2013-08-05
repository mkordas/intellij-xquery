package org.intellij.xquery.runner.options;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.AbstractCollection;
import com.intellij.util.xmlb.annotations.Tag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ligasgr
 * Date: 02/10/13
 * Time: 14:09
 */
@State(name = "XQueryRunnerSettings", storages = {@Storage(id = "xqueryRunners", file = StoragePathMacros.APP_CONFIG
        + "/xquery.xml")})
public class XQueryRunnerSettings implements PersistentStateComponent<XQueryRunnerSettings>,
        ExportableApplicationComponent {

    private List<XQueryRunnerConfiguration> runnerConfigurations = new ArrayList<XQueryRunnerConfiguration>();

    public static XQueryRunnerSettings getInstance() {
        return ApplicationManager.getApplication().getComponent(XQueryRunnerSettings.class);
    }

    @Tag("list")
    @AbstractCollection(surroundWithTag = false)
    public List<XQueryRunnerConfiguration> getRunnerConfigurations() {
        return runnerConfigurations;
    }

    public void setRunnerConfigurations(List<XQueryRunnerConfiguration> runnerConfigurations) {
        this.runnerConfigurations = runnerConfigurations;
    }

    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() {
    }

    @NotNull
    @Override
    public File[] getExportFiles() {
        return new File[]{PathManager.getOptionsFile("xquery.xml")};
    }

    @NotNull
    @Override
    public String getPresentableName() {
        return "XQuery runners configuration";
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "XQueryRunnerSettings";
    }

    @Nullable
    @Override
    public XQueryRunnerSettings getState() {
        return this;
    }

    @Override
    public void loadState(XQueryRunnerSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XQueryRunnerSettings that = (XQueryRunnerSettings) o;
        if (runnerConfigurations != null)
            return runnerConfigurations.equals(that.runnerConfigurations);
        else
            return that.runnerConfigurations == null;
    }

    @Override
    public int hashCode() {
        return runnerConfigurations != null ? runnerConfigurations.hashCode() : 0;
    }
}
