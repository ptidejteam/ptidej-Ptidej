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
 * SkeletonManager.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.3 $
 * $Date: 2005/08/19 09:07:17 $
 * $Author: earl3982 $
 */
package org.ogre4j;

public class SkeletonManager extends NativeObject {

    private static native int _create(String filename, String groupName,
            boolean isManual, int loader, int createParams)
            throws OgreException;

    private static native int _getByName(String name) throws OgreException;

    private static native void _removeAll();

    private static native int createCppInstance() throws OgreException;

    protected static native void dispose(int pInstance);

    /**
     * Returns the singleton of the SkeletonManager.
     * 
     * @return Returns a SkeletonManager. This can be null if the object wasn't
     *         initialized before.
     * @throws OgreException
     *             if any error occurs while creating the singleton.
     */
    public static SkeletonManager getSingelton() throws OgreException {
        int address = getSingleton();
        InstancePointer ptr = new InstancePointer(address);
        return new SkeletonManager(ptr);
    }

    private static native int getSingleton();

    public SkeletonManager() throws OgreException {
        super(new InstancePointer(createCppInstance()));
    }

    protected SkeletonManager(InstancePointer ptr) {
        super(ptr);
    }

    public void create(Skeleton skeleton, String fileName, String groupName,
            boolean isManual) throws OgreException {

        Skeleton skel = new Skeleton(new InstancePointer(_create(fileName,
                groupName, isManual, 0, 0)));
        if (skeleton == null)
            skel.dispose();
        else
            skeleton.pInstance = skel.pInstance;
    }

    // public Skeleton create(String fileName, String groupName, boolean
    // isManual) throws OgreException
    public void create(String fileName, String groupName, boolean isManual)
            throws OgreException {
        create(null, fileName, groupName, isManual);
    }

    public void getByName(Skeleton skeleton, String name) throws OgreException {
        int ptr = _getByName(name);
        if (ptr != 0) {
            Skeleton skel = new Skeleton(new InstancePointer(ptr));
            if (skeleton == null)
                skel.dispose();
            else
                skeleton.pInstance = skel.pInstance;
        }
    }

    // public Skeleton getByName(String name)
    public void getByName(String name) throws OgreException {
        getByName(null, name);
    }

    public void removeAll() {
        _removeAll();
    }

}
