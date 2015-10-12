/*
// Licensed to Julian Hyde under one or more contributor license
// agreements. See the NOTICE file distributed with this work for
// additional information regarding copyright ownership.
//
// Julian Hyde licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except in
// compliance with the License. You may obtain a copy of the License at:
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
*/
package org.olap4j.driver.xmla;

import org.olap4j.Axis;
import org.olap4j.CellSetAxisMetaData;
import org.olap4j.impl.Olap4jUtil;
import org.olap4j.metadata.Hierarchy;
import org.olap4j.metadata.Property;

import java.util.List;

/**
 * Implementation of {@link org.olap4j.CellSetMetaData}
 * for XML/A providers.
 *
 * @author jhyde
 * @since Nov 17, 2007
 */
class XmlaOlap4jCellSetAxisMetaData implements CellSetAxisMetaData {
    private final Axis axis;
    private final List<Hierarchy> hierarchyList;
    private final List<XmlaOlap4jCellSetMemberProperty> propertyList;

    XmlaOlap4jCellSetAxisMetaData(
        XmlaOlap4jConnection olap4jConnection,
        Axis axis,
        List<Hierarchy> hierarchyList,
        List<XmlaOlap4jCellSetMemberProperty> propertyList)
    {
        this.axis = axis;
        this.hierarchyList = hierarchyList;
        this.propertyList = propertyList;
    }

    public Axis getAxisOrdinal() {
        return axis;
    }

    public List<Hierarchy> getHierarchies() {
        return hierarchyList;
    }

    public List<Property> getProperties() {
        return Olap4jUtil.cast(propertyList);
    }

    XmlaOlap4jCellSetMemberProperty lookupProperty(
        String hierarchyName,
        String tag)
    {
        //System.out.println("    looking for " + hierarchyName + " & " + tag);
        for (XmlaOlap4jCellSetMemberProperty property : propertyList) {
            //System.out.println("        checking " + property.hierarchy.getUniqueName() + " | " + property.hierarchy.getName() + " & " + property.tag);
            //System.out.println("            T1 = " + property.hierarchy.getUniqueName().equals(hierarchyName));
            //System.out.println("            T2 = " + property.hierarchy.getName().equals(hierarchyName));
            //System.out.println("            T3 = " + property.tag.equals(tag));
            //if (property.hierarchy.getName().equals(hierarchyName) && property.tag.equals(tag))   // <- original
            if ((property.hierarchy.getUniqueName().equals(hierarchyName) || property.hierarchy.getName().equals(hierarchyName)) // <- fixed for infor
                && property.tag.equals(tag))
            {
                //System.out.println("    found");
                return property;
            }
        }
        //System.out.println("    nope");
        return null;
    }
}

// End XmlaOlap4jCellSetAxisMetaData.java
