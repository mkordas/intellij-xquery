/*
 * Copyright 2013 Grzegorz Ligas
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

package org.intellij.xquery.reference;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;
import org.intellij.xquery.psi.XQueryModuleDecl;
import org.intellij.xquery.psi.XQueryModuleImport;
import org.intellij.xquery.psi.XQueryNamespaceDecl;
import org.intellij.xquery.psi.XQueryVarNamespace;

import java.util.List;

import static org.intellij.xquery.reference.ReferenceUtil.assertChildOf;
import static org.intellij.xquery.reference.ReferenceUtil.getTargetOfReferenceAtCaret;

/**
 * User: ligasgr
 * Date: 03/07/13
 * Time: 13:49
 */
public class XQueryVariableNamespaceNameReferenceTest extends LightPlatformCodeInsightFixtureTestCase {
    @Override
    protected String getTestDataPath() {
        return "src/test/testData/org/intellij/xquery/reference/namespace";
    }

    public void testVariableNamespaceCompletion() {
        myFixture.configureByFiles("VariableNamespaceNameCompletion.xq");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertEquals(0, strings.size());
    }

    public void testVariableNamespaceReferenceForModuleDeclaration() {
        myFixture.configureByFiles("VariableNamespaceNameReference_Module.xq");

        PsiElement resolvedReference = getTargetOfReferenceAtCaret(myFixture, XQueryVarNamespace.class);

        assertChildOf(resolvedReference, XQueryModuleDecl.class);
    }

    public void testVariableNamespaceReferenceForModuleImport() {
        myFixture.configureByFiles("VariableNamespaceNameReference_Import.xq");

        PsiElement resolvedReference = getTargetOfReferenceAtCaret(myFixture, XQueryVarNamespace.class);

        assertChildOf(resolvedReference, XQueryModuleImport.class);
    }

    public void testVariableNamespaceReferenceForNamespaceDeclaration() {
        myFixture.configureByFiles("VariableNamespaceNameReference_Declaration.xq");

        PsiElement resolvedReference = getTargetOfReferenceAtCaret(myFixture, XQueryVarNamespace.class);

        assertChildOf(resolvedReference, XQueryNamespaceDecl.class);
    }

    public void testVariableNamespaceRename() {
        myFixture.configureByFiles("VariableNamespaceNameRename.xq");
        myFixture.renameElementAtCaret("aaa");
        myFixture.checkResultByFile("VariableNamespaceNameRename.xq", "VariableNamespaceNameRenameAfter.xq", false);
    }
}