package org.intellij.xquery.runner.ui;

import com.intellij.util.ui.ColumnInfo;
import org.intellij.xquery.runner.XQueryRunVariable;
import org.jetbrains.annotations.Nullable;

/**
 * User: ligasgr
 * Date: 23/09/13
 * Time: 15:39
 */
public class NameColumnInfo extends ColumnInfo<XQueryRunVariable, String> {
    public NameColumnInfo() {
        super("Name");
    }

    @Nullable
    @Override
    public String valueOf(XQueryRunVariable xQueryRunVariable) {
        return xQueryRunVariable.getName();
    }
}
