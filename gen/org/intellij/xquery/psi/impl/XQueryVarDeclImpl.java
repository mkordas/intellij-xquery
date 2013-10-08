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

// This is a generated file. Not intended for manual editing.
package org.intellij.xquery.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.intellij.xquery.psi.XQueryTypes.*;
import org.intellij.xquery.psi.*;
import com.intellij.navigation.ItemPresentation;

public class XQueryVarDeclImpl extends XQueryElementImpl implements XQueryVarDecl {

  public XQueryVarDeclImpl(ASTNode node) {
    super(node);
  }

  @Override
  @NotNull
  public List<XQueryAnnotation> getAnnotationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, XQueryAnnotation.class);
  }

  @Override
  @Nullable
  public XQueryExternalVarPart getExternalVarPart() {
    return findChildByClass(XQueryExternalVarPart.class);
  }

  @Override
  @Nullable
  public XQueryTypeDeclaration getTypeDeclaration() {
    return findChildByClass(XQueryTypeDeclaration.class);
  }

  @Override
  @Nullable
  public XQueryVarName getVarName() {
    return findChildByClass(XQueryVarName.class);
  }

  @Override
  @Nullable
  public XQueryVarValue getVarValue() {
    return findChildByClass(XQueryVarValue.class);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XQueryVisitor) ((XQueryVisitor)visitor).visitVarDecl(this);
    else super.accept(visitor);
  }

  public ItemPresentation getPresentation() {
    return XQueryPsiImplUtil.getPresentation(this);
  }

  public boolean isExternal() {
    return XQueryPsiImplUtil.isExternal(this);
  }

}
