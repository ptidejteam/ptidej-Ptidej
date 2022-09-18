/* This source file is part of ogre4j
 *     (The JNI bindings for OGRE)
 * For the latest info, see http://www.ogre4j.org/
 * 
 * Copyright (c) 2005 netAllied GmbH, Tettnang
 * Also see acknowledgements in Readme.html
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA, or go to
 * http://www.gnu.org/copyleft/lesser.txt.
 * 
 * 
 * InstancePointer.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.1 $
 * $Date: 2005/07/29 09:58:55 $
 * $Author: yavin02 $
 */
package org.ogre4j;

/**
 * @author Kai Klesatschke <yavin@ogre4j.org>
 * TODO InstancePointer type/class description.
 */
public class InstancePointer
{
    private int value;
    public InstancePointer(int value)
    {
        this.value = value; 
    }
    public void setValue( int value )
    {
        this.value = value;
    }
    public int getValue()
    {
        return this.value;
    }
    
    public int hashCode() {
        return value;
    }
    
    public boolean equals(Object obj) {
        if (obj instanceof InstancePointer) {
            return value == ((InstancePointer)obj).getValue();
        }
        return false;
        }
}
