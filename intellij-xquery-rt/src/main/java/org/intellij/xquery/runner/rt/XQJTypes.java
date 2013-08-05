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

package org.intellij.xquery.runner.rt;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ligasgr
 * Date: 07/10/13
 * Time: 14:47
 */
public class XQJTypes {
    private static String[] singleTypes = new String[]{
            "atomic()",
            "attribute()",
            "comment()",
            "document()",
            "document-element()",
            "document-schema-element()",
            "element()",
            "item()",
            "node()",
            "processing-instruction()",
            "text()",
            "schema-element()",
            "schema-attribute()",
            "xs:untyped",
            "xs:anyType",
            "xs:anySimpleType",
            "xs:anyAtomicType",
            "xs:untypedAtomic",
            "xs:dayTimeDuration",
            "xs:yearMonthDuration",
            "xs:anyURI",
            "xs:base64Binary",
            "xs:boolean",
            "xs:date",
            "xs:int",
            "xs:integer",
            "xs:short",
            "xs:long",
            "xs:dateTime",
            "xs:decimal",
            "xs:double",
            "xs:duration",
            "xs:float",
            "xs:gDay",
            "xs:gMonth",
            "xs:gMonthDay",
            "xs:gYear",
            "xs:gYearMonth",
            "xs:hexBinary",
            "xs:NOTATION",
            "xs:QName",
            "xs:string",
            "xs:time",
            "xs:byte",
            "xs:nonPositiveInteger",
            "xs:nonNegativeInteger",
            "xs:negativeInteger",
            "xs:positiveInteger",
            "xs:unsignedLong",
            "xs:unsignedInt",
            "xs:unsignedShort",
            "xs:unsignedByte",
            "xs:normalizedString",
            "xs:token",
            "xs:language",
            "xs:Name",
            "xs:NCName",
            "xs:NMToken",
            "xs:ID",
            "xs:IDREF",
            "xs:ENTITY",
            "xs:IDREFS",
            "xs:ENTITIES",
            "xs:NMTOKENS"
    };

    public static List<String> getAll() {
        List<String> all = new ArrayList<String>();
        for (String singleType : singleTypes) {
            all.add(singleType);
            all.add(singleType + "?");
            all.add(singleType + "*");
            all.add(singleType + "+");
        }
        return all;
    }
}
