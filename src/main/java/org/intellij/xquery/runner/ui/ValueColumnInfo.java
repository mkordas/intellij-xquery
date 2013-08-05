package org.intellij.xquery.runner.ui;

import com.intellij.util.ui.ColumnInfo;
import org.intellij.xquery.runner.XQueryRunVariable;
import org.jetbrains.annotations.Nullable;

/**
 * User: ligasgr
 * Date: 23/09/13
 * Time: 15:40
 */
public class ValueColumnInfo extends ColumnInfo<XQueryRunVariable, String> {

    public ValueColumnInfo() {
        super("Value");
    }

    @Nullable
    @Override
    public String valueOf(XQueryRunVariable xQueryRunVariable) {
        return xQueryRunVariable.getValue();
    }
}
