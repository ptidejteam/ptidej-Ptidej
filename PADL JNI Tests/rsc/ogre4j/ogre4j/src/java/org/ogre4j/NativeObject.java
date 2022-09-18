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
 * NativeObject.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.5 $
 * $Date: 2005/08/25 07:26:27 $
 * $Author: yavin02 $
 */
package org.ogre4j;

import java.security.InvalidParameterException;
import java.util.Hashtable;

import javax.crypto.spec.IvParameterSpec;

/**
 * Native object is the representation of a C++ instance. It holds a pointer to
 * the native object. Every native function should use this pointer to access
 * the corresponding native object.
 * 
 * TODO While creating the native object the instance pointer is saved in a
 * hashtable. If the instance pointer (the java object) already exists the
 * counter will be incremented. At the moment the corresponding dispose isn't
 * implemented!
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 */
public abstract class NativeObject
{
    // hashtable of the C++ pointers
    protected static Hashtable<InstancePointer, Integer> table = new Hashtable<InstancePointer, Integer>();

    protected InstancePointer                            pInstance;

    public NativeObject()
    {
        // FIXME should that be protected to allow access to classes in package
        // only?
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a new native object. A hashtable over the instancepointers
     * implements a smart pointer solution for the C++ instances.
     * 
     * @param pInstance
     *            InstancePointer to the C++ instance.
     */
    public NativeObject(InstancePointer pInstance)
    {
        if (pInstance == null || pInstance.getValue() == 0)
            throw new InvalidParameterException("Instance pointer have not to be null or its value zero!");

        this.pInstance = pInstance;

        Integer counter = (Integer) table.get(pInstance);

        if (null != counter)
            counter++;
        else
            counter = Integer.valueOf(1);
        table.put(pInstance, counter);
    }

    public void setInstancePtr(InstancePointer pInstance)
    {
        this.pInstance = pInstance;
        if (pInstance == null)
            return;
        Integer counter = (Integer) table.get(pInstance);

        if (null != counter)
            counter++;
        else
            counter = Integer.valueOf(1);
        table.put(pInstance, counter);
    }

    public InstancePointer getInstancePtr()
    {
        return pInstance;
    }

    public void getT()
    {
        getT(pInstance.getValue());
    }

    private static native void getT(int ptr);

    // /**
    // * Disposes this object. Must be called if a constructor was used to
    // create
    // * this object!
    // */
    // public void dispose()
    // {
    // Integer counter = (Integer) table.get(pInstance);
    //
    // if (null != counter)
    // {
    // counter--;
    // if (counter.intValue() == 0)
    // {
    // table.remove(pInstance);
    // pInstance = null;
    // }
    // else
    // {
    // table.put(pInstance, counter);
    // }
    // }
    // }

    public boolean equals(Object obj)
    {
        if (obj instanceof NativeObject)
        {
            return pInstance.equals(((NativeObject) obj).getInstancePtr());
        }
        return false;
    }
}
