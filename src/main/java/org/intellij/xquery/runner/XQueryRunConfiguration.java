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

package org.intellij.xquery.runner;

import com.intellij.diagnostic.logging.LogConfigurationPanel;
import com.intellij.execution.*;
import com.intellij.execution.configuration.EnvironmentVariablesComponent;
import com.intellij.execution.configurations.*;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.util.JavaParametersUtil;
import com.intellij.execution.util.ProgramParametersUtil;
import com.intellij.openapi.components.PathMacroManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.options.SettingsEditorGroup;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.util.xmlb.XmlSerializer;
import org.apache.commons.lang.StringUtils;
import org.intellij.xquery.runner.options.XQueryRunnerConfiguration;
import org.intellij.xquery.runner.ui.XQueryRunConfigurationJavaTab;
import org.intellij.xquery.runner.ui.XQueryRunConfigurationMainTab;
import org.jdom.CDATA;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * User: ligasgr
 * Date: 04/08/13
 * Time: 14:56
 */
public class XQueryRunConfiguration extends ModuleBasedConfiguration<XQueryModuleBasedConfiguration> implements
        CommonJavaRunConfigurationParameters, RunConfigurationWithSuppressedDefaultDebugAction {
    public static final String CONFIGURATION_TAG = "xQueryConfiguration";
    private String mainFileName;
    private String vmParameters;
    private String programParameters;
    private String workingDirectory;
    private boolean alternativeJrePathEnabled;
    private String alternativeJrePath;
    private Map<String, String> myEnvs = new LinkedHashMap<String, String>();
    private boolean passParentEnvs = true;
    private XQueryRunVariables variables;
    private boolean contextItemEnabled;
    private boolean contextItemFromEditorEnabled = true;
    private String contextItemText;
    private String contextItemFile;
    private String runnerConfigurationName;

    public XQueryRunConfiguration(String name, XQueryModuleBasedConfiguration configurationModule,
                                  ConfigurationFactory factory) {
        super(name, configurationModule, factory);
        setWorkingDirectory(getProject().getBasePath());
    }

    @Override
    public Collection<Module> getValidModules() {
        Module[] modules = ModuleManager.getInstance(getProject()).getModules();
        return Arrays.asList(modules);
    }

    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        SettingsEditorGroup<XQueryRunConfiguration> group = new SettingsEditorGroup<XQueryRunConfiguration>();
        group.addEditor("Configuration", new XQueryRunConfigurationMainTab(getProject()));
        group.addEditor("Java Configuration", new XQueryRunConfigurationJavaTab(getProject()));
        group.addEditor("Logs", new LogConfigurationPanel<XQueryRunConfiguration>());
        return group;
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment env) throws
            ExecutionException {
        XQueryRunProfileState state = new XQueryRunProfileState(env, (XQueryRunConfiguration) env
                .getRunnerAndConfigurationSettings().getConfiguration());
        XQueryModuleBasedConfiguration module = getConfigurationModule();
        state.setConsoleBuilder(TextConsoleBuilderFactory.getInstance().createBuilder(getProject(),
                module.getSearchScope()));
        return state;
    }

    @Override
    public void setVMParameters(String vmParameters) {
        this.vmParameters = vmParameters;
    }

    @Override
    public String getVMParameters() {
        return vmParameters;
    }

    @Override
    public boolean isAlternativeJrePathEnabled() {
        return alternativeJrePathEnabled;
    }

    @Override
    public void setAlternativeJrePathEnabled(boolean enabled) {
        alternativeJrePathEnabled = enabled;
    }

    @Override
    public String getAlternativeJrePath() {
        return alternativeJrePath;
    }

    @Override
    public void setAlternativeJrePath(String path) {
        alternativeJrePath = path;
    }

    @Nullable
    @Override
    public String getRunClass() {
        return "org.intellij.xquery.runner.rt.XQueryRunnerApp";
    }

    @Nullable
    @Override
    public String getPackage() {
        return null;
    }

    @Override
    public void setProgramParameters(@Nullable String value) {
        programParameters = value;
    }

    @Nullable
    @Override
    public String getProgramParameters() {
        return programParameters;
    }

    @Override
    public void setWorkingDirectory(@Nullable String value) {
        workingDirectory = ExternalizablePath.urlValue(value);
    }

    @Nullable
    @Override
    public String getWorkingDirectory() {
        return ExternalizablePath.localPathValue(workingDirectory);
    }

    public void setPassParentEnvs(boolean passParentEnvs) {
        this.passParentEnvs = passParentEnvs;
    }

    @NotNull
    public Map<String, String> getEnvs() {
        return myEnvs;
    }

    public void setEnvs(@NotNull final Map<String, String> envs) {
        myEnvs.clear();
        myEnvs.putAll(envs);
    }

    public boolean isPassParentEnvs() {
        return passParentEnvs;
    }

    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {
        JavaParametersUtil.checkAlternativeJRE(this);
        final RunConfigurationModule configurationModule = getConfigurationModule();
        configurationModule.checkForWarning();
        ProgramParametersUtil.checkWorkingDirectoryExist(this, getProject(), configurationModule.getModule());
        checkRunnerConfiguration();
        checkVariables();
    }

    private void checkVariables() throws RuntimeConfigurationError {
        for (XQueryRunVariable variable : variables.getVariables()) {
            if (isEmpty(variable.getName()) || isEmpty(variable.getType()))
                throw new RuntimeConfigurationError("Variables must have name and type");
        }
    }

    private void checkRunnerConfiguration() throws RuntimeConfigurationError {
        if (runnerConfigurationName == null) {
            throw new RuntimeConfigurationError("Runner not specified");
        }
    }

    public void readExternal(final Element element) throws InvalidDataException {
        PathMacroManager.getInstance(getProject()).expandPaths(element);
        super.readExternal(element);
        readConfiguration(element);
        readModule(element);
        EnvironmentVariablesComponent.readExternal(element, getEnvs());
        readVariables(element);
    }

    private void readConfiguration(Element element) {
        Element configuration = element.getChild(CONFIGURATION_TAG);
        if (configuration == null) return;

        mainFileName = configuration.getAttributeValue("mainFileName");
        vmParameters = configuration.getAttributeValue("vmParameters");
        programParameters = configuration.getAttributeValue("programParameters");
        workingDirectory = configuration.getAttributeValue("workingDirectory");
        String alternativeJrePathEnabledString = configuration.getAttributeValue("alternativeJrePathEnabled");
        if (alternativeJrePathEnabledString != null)
            alternativeJrePathEnabled = new Boolean(alternativeJrePathEnabledString);
        alternativeJrePath = configuration.getAttributeValue("alternativeJrePath");
        String contextItemEnabledString = configuration.getAttributeValue("contextItemEnabled");
        if (contextItemEnabledString != null)
            contextItemEnabled = new Boolean(contextItemEnabledString);
        String contextItemFromEditorEnabledString = configuration.getAttributeValue("contextItemFromEditorEnabled");
        if (contextItemFromEditorEnabledString != null)
            contextItemFromEditorEnabled = new Boolean(contextItemFromEditorEnabledString);
        Element contextItemTextElement = configuration.getChild("contextItemText");
        if (contextItemTextElement != null) {
            contextItemText = contextItemTextElement.getText();
        }
        contextItemFile = configuration.getAttributeValue("contextItemFile");
        runnerConfigurationName = configuration.getAttributeValue("runnerName");
    }

    public void writeExternal(final Element element) throws WriteExternalException {
        super.writeExternal(element);
        writeConfiguration(element);
        writeModule(element);
        EnvironmentVariablesComponent.writeExternal(element, getEnvs());
        PathMacroManager.getInstance(getProject()).collapsePathsRecursively(element);
        writeVariables(element);
    }

    private void writeConfiguration(Element element) {
        Element configuration = new Element(CONFIGURATION_TAG);
        element.addContent(configuration);
        if (mainFileName != null)
            configuration.setAttribute("mainFileName", mainFileName);
        if (vmParameters != null)
            configuration.setAttribute("vmParameters", vmParameters);
        if (programParameters != null)
            configuration.setAttribute("programParameters", programParameters);
        if (workingDirectory != null)
            configuration.setAttribute("workingDirectory", workingDirectory);
        configuration.setAttribute("alternativeJrePathEnabled", Boolean.toString(alternativeJrePathEnabled));
        if (alternativeJrePath != null)
            configuration.setAttribute("alternativeJrePath", alternativeJrePath);
        configuration.setAttribute("contextItemEnabled", Boolean.toString(contextItemEnabled));
        configuration.setAttribute("contextItemFromEditorEnabled", Boolean.toString(contextItemFromEditorEnabled));
        if (contextItemText != null) {
            Element contextItemTextElement = new Element("contextItemText");
            contextItemTextElement.addContent(new CDATA(contextItemText));
            configuration.addContent(contextItemTextElement);
        }
        if (contextItemFile != null) {
            configuration.setAttribute("contextItemFile", contextItemFile);
        }
        if (runnerConfigurationName != null) {
            configuration.setAttribute("runnerName", runnerConfigurationName);
        }
    }

    private void readVariables(Element element) throws InvalidDataException {
        setVariables(new XQueryRunVariables());
        Element variablesElement = element.getChild("variables");
        if (variablesElement != null) {
            XQueryRunVariables localVariables = new XQueryRunVariables();
            XmlSerializer.deserializeInto(localVariables, variablesElement);
            variables.loadState(localVariables);
        }
    }

    private void writeVariables(Element element) throws WriteExternalException {
        if (variables != null) {
            Element variablesElement = XmlSerializer.serialize(variables.getState());
            element.addContent(variablesElement);
        }
    }

    @Override
    protected ModuleBasedConfiguration createInstance() {
        return new XQueryRunConfiguration(getName(), new XQueryModuleBasedConfiguration(getProject()),
                XQueryRunConfigurationType.getInstance().getConfigurationFactories()[0]);
    }

    public void setVariables(XQueryRunVariables variables) {
        this.variables = variables;
    }

    public XQueryRunVariables getVariables() {
        return variables;
    }

    public String getMainFileName() {
        return mainFileName;
    }

    public void setMainFileName(String mainFileName) {
        this.mainFileName = mainFileName;
    }

    public boolean isContextItemEnabled() {
        return contextItemEnabled;
    }

    public void setContextItemEnabled(boolean contextItemEnabled) {
        this.contextItemEnabled = contextItemEnabled;
    }

    public boolean isContextItemFromEditorEnabled() {
        return contextItemFromEditorEnabled;
    }

    public void setContextItemFromEditorEnabled(boolean contextItemFromEditorEnabled) {
        this.contextItemFromEditorEnabled = contextItemFromEditorEnabled;
    }

    public String getContextItemText() {
        return contextItemText;
    }

    public void setContextItemText(String contextItemText) {
        this.contextItemText = contextItemText;
    }

    public String getContextItemFile() {
        return contextItemFile;
    }

    public void setContextItemFile(String contextItemFile) {
        this.contextItemFile = contextItemFile;
    }

    public void setRunnerConfigurationName(String runnerConfigurationName) {
        this.runnerConfigurationName = runnerConfigurationName;
    }

    public String getRunnerConfigurationName() {
        return runnerConfigurationName;
    }
}
