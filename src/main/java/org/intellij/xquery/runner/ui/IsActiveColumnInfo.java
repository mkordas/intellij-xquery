package org.intellij.xquery.runner.ui;

import com.intellij.util.ui.ColumnInfo;
import org.intellij.xquery.runner.XQueryRunVariable;
import org.jetbrains.annotations.Nullable;

/**
 * User: ligasgr
 * Date: 23/09/13
 * Time: 15:40
 */
public class IsActiveColumnInfo extends ColumnInfo<XQueryRunVariable, Boolean> {
    public IsActiveColumnInfo() {
        super("Pass");
    }

    @Nullable
    @Override
    public Boolean valueOf(XQueryRunVariable xQueryRunVariable) {
        return xQueryRunVariable.isActive();
    }

    public Class getColumnClass() {
        return Boolean.class;
    }

    public boolean isCellEditable(XQueryRunVariable xQueryRunVariable) {
        return true;
    }

    @Override
    public void setValue(XQueryRunVariable xQueryRunVariable, Boolean value) {
        xQueryRunVariable.setActive(value);
    }
}
