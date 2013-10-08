package org.intellij.xquery.runner.ui;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.GuiUtils;
import com.intellij.ui.PanelWithAnchor;
import com.intellij.ui.components.JBCheckBox;
import net.miginfocom.swing.MigLayout;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * User: ligasgr
 * Date: 24/09/13
 * Time: 15:23
 */
public class XQueryContextItemPanel extends JPanel implements PanelWithAnchor {

    private final JBCheckBox contextItemEnabled;
    private final TextFieldWithBrowseButton contextItemPathField;
    private final EditorTextField contextItemEditorField;
    private final ComponentWithBrowseButton<EditorTextField> contextItemEditorContent;
    private final JRadioButton editorRadioButton;
    private final JRadioButton fileRadioButton;
    private final ButtonGroup buttonGroup;
    private final JPanel contextItemOptionsPanel;
    private JComponent anchor;

    public XQueryContextItemPanel(Project project) {
        contextItemEnabled = new JBCheckBox("Pass context item");
        contextItemEnabled.setMnemonic(KeyEvent.VK_C);
        contextItemEnabled.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contextItemEnabledChanged();
            }
        });
        contextItemOptionsPanel = new JPanel(new MigLayout("ins 0, gap 5, fill, flowx"));
        contextItemEditorField = new EditorTextField("", project, StdFileTypes.PLAIN_TEXT);
        contextItemEditorContent = new MyEditorTextFieldWithBrowseButton(project, contextItemEditorField);
        contextItemEditorContent.setButtonIcon(AllIcons.Actions.ShowViewer);
        contextItemPathField = new TextFieldWithBrowseButton();
        contextItemPathField.addBrowseFolderListener("Choose file", null, null, FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor());
        editorRadioButton = new JRadioButton("Custom content");
        editorRadioButton.setMnemonic(KeyEvent.VK_E);
        editorRadioButton.setSelected(true);
        editorRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contextItemSourceChanged();
            }
        });
        fileRadioButton = new JRadioButton("Content from file");
        fileRadioButton.setMnemonic(KeyEvent.VK_L);
        fileRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contextItemSourceChanged();
            }
        });
        buttonGroup = new ButtonGroup();
        buttonGroup.add(editorRadioButton);
        buttonGroup.add(fileRadioButton);
        setLayout(new MigLayout("ins 0, gap 5, fill, flowx"));
        add(contextItemEnabled, "shrinkx, top");
        add(contextItemOptionsPanel, "growx, pushx");
        contextItemOptionsPanel.add(editorRadioButton);
        contextItemOptionsPanel.add(contextItemEditorContent, "growx, pushx, wrap");
        contextItemOptionsPanel.add(fileRadioButton);
        contextItemOptionsPanel.add(contextItemPathField, "growx, pushx");
        contextItemEnabledChanged();
        contextItemSourceChanged();
    }

    @Override
    public JComponent getAnchor() {
        return anchor;
    }

    @Override
    public void setAnchor(@Nullable JComponent anchor) {
        this.anchor = anchor;
    }

    public void init(boolean isEnabled, String content, String filePath, boolean contextItemFromEditorEnabled) {
        contextItemEditorContent.getChildComponent().setText(content);
        contextItemPathField.setText(filePath);
        setContextItemEnabled(isEnabled);
        setContextItemFromEditorEnabled(contextItemFromEditorEnabled);
    }

    private void setContextItemFromEditorEnabled(boolean contextItemFromEditorEnabled) {
        if (contextItemFromEditorEnabled) {
            editorRadioButton.setSelected(true);
        } else {
            fileRadioButton.setSelected(true);
        }
        contextItemSourceChanged();
    }

    private void setContextItemEnabled(boolean enabled) {
        contextItemEnabled.setSelected(enabled);
        contextItemEnabledChanged();
    }

    private void contextItemEnabledChanged() {
        final boolean pathEnabled = isContextItemEnabled();
        GuiUtils.enableChildren(contextItemOptionsPanel, pathEnabled);
        contextItemSourceChanged();
    }

    private void contextItemSourceChanged() {
        final boolean editorAsSource = isContextItemFromEditorEnabled();
        if (isContextItemEnabled()) {
            GuiUtils.enableChildren(contextItemEditorContent, editorAsSource);
            GuiUtils.enableChildren(contextItemPathField, !editorAsSource);
        }
        contextItemEditorContent.invalidate();
        contextItemPathField.invalidate();
    }

    public boolean isContextItemFromEditorEnabled() {
        return editorRadioButton.isSelected();
    }

    public boolean isContextItemEnabled() {
        return contextItemEnabled.isSelected();
    }

    public String getContextItemPath() {
        return FileUtil.toSystemIndependentName(contextItemPathField.getChildComponent().getText().trim());
    }

    public String getContextItemEditorContent() {
        return contextItemEditorContent.getChildComponent().getText();
    }

    private class MyEditorTextFieldWithBrowseButton extends ComponentWithBrowseButton<EditorTextField> {
        public MyEditorTextFieldWithBrowseButton(final Project project, final EditorTextField editorTextField) {
            super(editorTextField, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editorTextField.setText(
                            Messages.showMultilineInputDialog(project, "Edit custom content", "Edit", editorTextField.getText(), null, null));
                }
            });
        }
    }
}
