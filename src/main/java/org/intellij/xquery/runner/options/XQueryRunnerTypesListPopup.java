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

import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * User: ligasgr
 * Date: 06/10/13
 * Time: 17:07
 */
public class XQueryRunnerTypesListPopup extends BaseListPopupStep<XQueryRunnerType> {
    private ActionExecutor actionExecutor;

    public XQueryRunnerTypesListPopup(ActionExecutor actionExecutor) {
        super("Add new runner", XQueryRunnerType.values());
        this.actionExecutor = actionExecutor;
    }

    @NotNull
    public String getTextFor(final XQueryRunnerType type) {
        return type.getPresentableName();
    }

    @Override
    public boolean isSpeedSearchEnabled() {
        return true;
    }

    @Override
    public boolean canBeHidden(XQueryRunnerType value) {
        return true;
    }

    public Icon getIconFor(final XQueryRunnerType type) {
        return type.getIcon();
    }

    public PopupStep onChosen(final XQueryRunnerType type, final boolean finalChoice) {
        actionExecutor.execute(type);
        return FINAL_CHOICE;
    }

    public int getDefaultOptionIndex() {
        return super.getDefaultOptionIndex();
    }
}
