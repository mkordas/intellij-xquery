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

import com.intellij.openapi.actionSystem.ActionToolbarPosition;
import com.intellij.openapi.ui.Splitter;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.ui.*;
import com.intellij.ui.components.JBList;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.intellij.xquery.runner.options.UniqueNameGenerator.generateUniqueNameUsingBaseName;

/**
 * User: ligasgr
 * Date: 02/10/13
 * Time: 14:49
 */
public class XQueryRunnerSettingsForm {

    private JPanel formComponent;
    private JPanel runnerConfigurationPanel;
    private ToolbarDecorator runnersToolbarDecorator;
    private DefaultListModel runnerListModel;
    private JBList runnerList;
    private XQueryRunnerConfigurationPanel currentRunnerConfigurationPanel;
    private int previouslySelectedIndex = -1;

    public XQueryRunnerSettingsForm(List<XQueryRunnerConfiguration> runnerConfigurations) {
        runnerListModel = new DefaultListModel();
        runnerList = prepareRunnersList(runnerListModel);
        runnersToolbarDecorator = prepareRunnersTableToolbarDecorator(runnerList);
        runnerConfigurationPanel = new JPanel(new BorderLayout());
        formComponent = new JPanel(new BorderLayout());
        formComponent.add(prepareSplitter(runnersToolbarDecorator, runnerConfigurationPanel), BorderLayout.CENTER);
        refreshCurrentConfiguration(runnerConfigurations);
    }

    public void refreshCurrentConfiguration(List<XQueryRunnerConfiguration> runnerConfigurations) {
        cleanupCurrentConfigurationPanel();
        clearRunnerListModel();
        populateModelWithClonesOfConfigurations(runnerConfigurations);
        setFirstPositionAsSelectedIfExists();
    }

    public boolean isModified(List<XQueryRunnerConfiguration> runnerConfigurations) {
        return !getCurrentConfigurations().equals(runnerConfigurations);
    }

    public List<XQueryRunnerConfiguration> getCurrentConfigurations() {
        updateModelWithChangesFromConfigurationPanel(previouslySelectedIndex);
        return getConfigurationsFromModel();
    }

    private void clearRunnerListModel() {
        runnerListModel.removeAllElements();
    }

    private void cleanupCurrentConfigurationPanel() {
        currentRunnerConfigurationPanel = null;
    }

    private Splitter prepareSplitter(ToolbarDecorator runnersToolbarDecorator, JPanel runnerConfigurationPanel) {
        Splitter splitter = new Splitter(false, 0.3f);
        JPanel runnersPanel = new JPanel(new BorderLayout());
        runnersPanel.add(runnersToolbarDecorator.createPanel(), BorderLayout.CENTER);
        runnersPanel.setMinimumSize(new Dimension(150, 300));
        splitter.setFirstComponent(runnersPanel);
        splitter.setSecondComponent(runnerConfigurationPanel);
        runnerConfigurationPanel.setMinimumSize(new Dimension(300, 300));
        return splitter;
    }

    private void populateModelWithClonesOfConfigurations(List<XQueryRunnerConfiguration> runnerConfigurations) {
        for (XQueryRunnerConfiguration configuration : runnerConfigurations) {
            addConfigurationCloneToModel(configuration);
        }
    }

    private void addConfigurationCloneToModel(XQueryRunnerConfiguration configuration) {
        try {
            runnerListModel.addElement(configuration.clone());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<XQueryRunnerConfiguration> getConfigurationsFromModel() {
        List<XQueryRunnerConfiguration> currentConfigurations = new ArrayList<XQueryRunnerConfiguration>();
        for (int i = 0; i < runnerListModel.getSize(); i++) {
            currentConfigurations.add(((XQueryRunnerConfiguration) runnerListModel.getElementAt(i)));
        }
        return currentConfigurations;
    }

    public JComponent getFormComponent() {
        return formComponent;
    }

    private JBList prepareRunnersList(DefaultListModel runnerListModel) {
        final JBList runnerList = new JBList(runnerListModel);
        runnerList.getEmptyText().setText("No runners defined");
        runnerList.setDragEnabled(false);
        runnerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        runnerList.setCellRenderer(new XQueryRunnerConfigurationCellRenderer());
        runnerList.addListSelectionListener(getSelectionChangedListener(runnerList));
        return runnerList;
    }

    private ListSelectionListener getSelectionChangedListener(final JBList runnerList) {
        return new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                runnerConfigurationPanel.removeAll();
                XQueryRunnerConfiguration selection = (XQueryRunnerConfiguration) runnerList.getSelectedValue();
                if (selection != null) {
                    saveConfigurationChangesForPreviouslySelected();
                    updateRunnerConfigurationPanel(selection);
                    runnerList.repaint();
                }
                runnerConfigurationPanel.revalidate();
                runnerConfigurationPanel.repaint();
            }
        };
    }

    private ToolbarDecorator prepareRunnersTableToolbarDecorator(final JBList runnerList) {
        return ToolbarDecorator.createDecorator(runnerList)
                .setAddAction(new AnActionButtonRunnable() {
                    @Override
                    public void run(AnActionButton button) {
                        showAddRunnerPopupWithActionExecutor(getAddRunnerActionExecutor());
                    }
                }).setRemoveAction(new AnActionButtonRunnable() {
                    @Override
                    public void run(AnActionButton button) {
                        cleanupCurrentConfigurationPanel();
                        ListUtil.removeSelectedItems(runnerList);
                        runnerList.repaint();
                    }
                }).disableUpDownActions().setToolbarPosition(ActionToolbarPosition.TOP);
    }

    private void setFirstPositionAsSelectedIfExists() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (runnerListModel.size() > 0) {
                    runnerList.setSelectedIndex(0);
                }
            }
        });
    }

    private void saveConfigurationChangesForPreviouslySelected() {
        if (currentRunnerConfigurationPanel != null
                && previouslySelectedIndex > -1
                && previouslySelectedIndex < runnerListModel.getSize()) {
            updateModelWithChangesFromConfigurationPanel(previouslySelectedIndex);
        }
    }

    private void updateModelWithChangesFromConfigurationPanel(int index) {
        if (currentRunnerConfigurationPanel == null) return;
        XQueryRunnerConfiguration currentConfigurationState
                = currentRunnerConfigurationPanel.getCurrentConfigurationState();
        runnerListModel.setElementAt(currentConfigurationState, index);
    }

    private void updateRunnerConfigurationPanel(final XQueryRunnerConfiguration runnerConfiguration) {
        final int index = runnerList.getSelectedIndex();
        currentRunnerConfigurationPanel = new XQueryRunnerConfigurationPanel(runnerConfiguration,
                getNameChangedListener(index));
        runnerConfigurationPanel.add(currentRunnerConfigurationPanel.getPanel(), BorderLayout.NORTH);
        setupEnclosingDialogBounds();
        previouslySelectedIndex = index;
    }

    private void setupEnclosingDialogBounds() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UIUtil.setupEnclosingDialogBounds(formComponent);
            }
        });
    }

    private DocumentAdapter getNameChangedListener(final int index) {
        return new DocumentAdapter() {
            @Override
            protected void textChanged(DocumentEvent e) {
                updateModelWithChangesFromConfigurationPanel(index);
            }
        };
    }

    private void showAddRunnerPopupWithActionExecutor(ActionExecutor addRunnerActionExecutor) {
        XQueryRunnerTypesListPopup runnersTypesPopup = new XQueryRunnerTypesListPopup(addRunnerActionExecutor);
        final ListPopup popup = JBPopupFactory.getInstance().createListPopup(runnersTypesPopup);
        popup.showUnderneathOf(runnersToolbarDecorator.getActionsPanel());
    }

    private ActionExecutor getAddRunnerActionExecutor() {
        return new ActionExecutor() {
            @Override
            public void execute(XQueryRunnerType type) {
                addRunnerConfigurationBasedOnType(type);
            }
        };
    }

    private void addRunnerConfigurationBasedOnType(XQueryRunnerType type) {
        XQueryRunnerConfiguration newItem = createNewRunnerConfiguration(type);
        addNewRunnerConfigurationToModelAndSelectIt(newItem);
    }

    private void addNewRunnerConfigurationToModelAndSelectIt(XQueryRunnerConfiguration newItem) {
        runnerListModel.addElement(newItem);
        runnerList.clearSelection();
        ListScrollingUtil.selectItem(runnerList, newItem);
    }

    private XQueryRunnerConfiguration createNewRunnerConfiguration(XQueryRunnerType type) {
        String name = generateUniqueNameUsingBaseName(type.getPresentableName(), getNames(getCurrentConfigurations()));
        return new XQueryRunnerConfiguration(name, type);
    }

    private List<String> getNames(List<XQueryRunnerConfiguration> currentConfigurations) {
        List<String> names = new ArrayList<String>();
        for (XQueryRunnerConfiguration cfg : currentConfigurations) {
            names.add(cfg.NAME);
        }
        return names;
    }
}
