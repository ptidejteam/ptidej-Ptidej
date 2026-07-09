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
 * ResourceGroupManager.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.5 $
 * $Date: 2005/08/20 03:01:56 $
 * $Author: earl3982 $
 */
package org.ogre4j;

/**
 * This singleton class manages the list of resource groups, and notifying the
 * various resource managers of their obligations to load / unload resources in
 * a group.
 * 
 * <p>
 * It also provides facilities to monitor resource loading per group (to do
 * progress bars etc), provided the resources that are required are
 * pre-registered.
 * </p>
 * 
 * <p>
 * Defining new resource groups, and declaring the resources you intend to use
 * in advance is optional, however it is a very useful feature. In addition, if
 * a ResourceManager supports the definition of resources through scripts, then
 * this is the class which drives the locating of the scripts and telling the
 * ResourceManager to parse them.
 * </p>
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 */
public class ResourceGroupManager extends NativeObject {
    public static final String DEFAULT_RESOURCE_GROUP_NAME = "General";

    public static final String BOOTSTRAP_RESOURCE_GROUP_NAME = "Bootstrap";

    private static native void addResourceLocation(int pInstance, String name,
            String locType, String resGroup, boolean recursive)
            throws OgreException;

    private static native void clearResourceGroup(int pInstance, String name)
            throws OgreException;

    private static native void createResourceGroup(int pInstance, String name)
            throws OgreException;;

    private static native void destroyResourceGroup(int pInstance, String name)
            throws OgreException;

    protected static native void dispose(int pInstance);

    /**
     * Returns the singleton of the SkeletonManager.
     * 
     * @return Returns a SkeletonManager. This can be null if the object wasn't
     *         initialized before.
     * @throws OgreException
     *             if any error occurs while creating the singleton.
     */
    public static ResourceGroupManager getSingleton() throws OgreException {
        int address = getSingletonImpl();
        InstancePointer ptr = new InstancePointer(address);
        return new ResourceGroupManager(ptr);
    }

    private static native int getSingletonImpl();

    private static native void initialiseAllResourceGroups(int pInstance)
            throws OgreException;

    private static native void initialiseResourceGroup(int pInstance,
            String name) throws OgreException;

    private static native void loadResourceGroup(int pInstance, String name,
            boolean loadMainResources, boolean loadWorldGeom)
            throws OgreException;

    private static native void removeResourceLocation(int pInstance,
            String name, String resGroup) throws OgreException;

    private static native void shutdownAll(int pInstance) throws OgreException;

    private static native void unloadResourceGroup(int pInstance, String name)
            throws OgreException;

    // public void declareResource(String name, String resourceType,
    // String groupName,
    // NameValuePairList loadParameters)
    // {
    // }
    // public void undeclareResource(String name, String groupName)
    // {
    // }
    //
    // public DataStreamListPtr openResources(String pattern,
    // String groupName = DEFAULT_RESOURCE_GROUP_NAME);
    //
    // public StringVectorPtr listResourceNames(String groupName);
    //
    // public FileInfoListPtr listResourceFileInfo(String groupName);
    //
    // public StringVectorPtr findResourceNames(String groupName, String
    // pattern);
    //
    // public boolean resourceExists(String group, String filename)
    // {
    // return false;
    // }
    //
    // public FileInfoListPtr findResourceFileInfo(String group, String pattern)
    // {
    // return null;
    // }
    //
    //
    // public void addResourceGroupListener(ResourceGroupListener l)
    // {
    // }
    // public void removeResourceGroupListener(ResourceGroupListener l)
    // {
    // }
    //
    // public void setWorldResourceGroupName(String groupName) {}
    //
    // public String getWorldResourceGroupName() {}
    //
    // public void linkWorldGeometryToResourceGroup(String group,
    // String worldGeometry, SceneManager sceneManager)
    // {
    // }
    //
    // public void unlinkWorldGeometryFromResourceGroup(String group)
    // {
    // }

    /**
     * Creates a new SkeletonManager singleton. If created the singleton by this
     * constructor call dispose if you don't need the singleton anymore.
     * 
     * @throws OgreException
     *             if the singleton exists.
     */
    public ResourceGroupManager() throws OgreException {
        // if (singleton != null)
        // throw new OgreException("ResourceGroupManager already initialized");
        //
        // int ptrResGrpMgr = _getSingleton();
        // if (ptrResGrpMgr == 0)
        // ptrResGrpMgr = createInstance();
        // if (ptrResGrpMgr != 0)
        // pInstance = new InstancePointer(ptrResGrpMgr);
        //
        // singleton = this;
        // isManualCreated = true;
    }

    protected ResourceGroupManager(InstancePointer pInstance) {
        super(pInstance);
    }

    /**
     * Method to add a resource location to for a given resource group.
     * 
     * @remarks Resource locations are places which are searched to load
     *          resource files. When you choose to load a file, or to search for
     *          valid files to load, the resource locations are used.
     * @param name
     *            The name of the resource location; probably a directory, zip
     *            file, URL etc.
     * @param locType
     *            The codename for the resource type, which must correspond to
     *            the Archive factory which is providing the implementation.
     * @param resGroup
     *            The name of the resource group for which this location is to
     *            apply. ResourceGroupManager::DEFAULT_RESOURCE_GROUP_NAME is
     *            the default group which always exists, and can be used for
     *            resources which are unlikely to be unloaded until application
     *            shutdown. Otherwise it must be the name of a group; if it has
     *            not already been created with createResourceGroup then it is
     *            created automatically.
     * @param recursive
     *            Whether subdirectories will be searched for files when using a
     *            pattern match (such as *.material), and whether subdirectories
     *            will be indexed. This can slow down initial loading of the
     *            archive and searches. When opening a resource you still need
     *            to use the fully qualified name, this allows duplicate names
     *            in alternate paths.
     */
    public void addResourceLocation(String name, String locType,
            String resGroup, boolean recursive) throws OgreException {
        addResourceLocation(pInstance.getValue(), name, locType, resGroup,
                recursive);
    }

    public void addResourceLocation(String name, String locType, String resGroup)
            throws OgreException {
        addResourceLocation(pInstance.getValue(), name, locType, resGroup,
                false);
    }

    /**
     * Clears a resource group.
     * 
     * <p>
     * <b>Remarks:</b><br/>This method unloads all resources in the group, but
     * in addition it removes all those resources from their ResourceManagers,
     * and then clears all the members from the list. That means after calling
     * this method, there are no resources declared as part of the named group
     * any more. Resource locations still persist though.
     * </p>
     * 
     * @param name
     *            The name to of the resource group to clear.
     * @throws OgreException
     */
    public void clearResourceGroup(String name) throws OgreException {
        clearResourceGroup(pInstance.getValue(), name);
    }

    /**
     * Create a resource group.
     * 
     * <p>
     * <b>Remarks:</b><br/>A resource group allows you to define a set of
     * resources that can be loaded / unloaded as a unit. For example, it might
     * be all the resources used for the level of a game. There is always one
     * predefined resource group called
     * ResourceGroupManager::DEFAULT_RESOURCE_GROUP_NAME, which is typically
     * used to hold all resources which do not need to be unloaded until
     * shutdown. You can create additional ones so that you can control the life
     * of your resources in whichever way you wish.
     * </p>
     * <p>
     * Once you have defined a resource group, resources which will be loaded as
     * part of it are defined in one of 3 ways:
     * <ul>
     * <li>Manually through declareResource(); this is useful for scripted
     * declarations since it is entirely generalised, and does not create
     * Resource instances right away</li>
     * <li>Through the use of scripts; some ResourceManager subtypes have
     * script formats (e.g. .material, .overlay) which can be used to declare
     * resources</li>
     * <li>By calling ResourceManager::create to create a resource manually.
     * This resource will go on the list for it's group and will be loaded and
     * unloaded with that group</li>
     * </ul>
     * You must remember to call initialiseResourceGroup if you intend to use
     * the first 2 types.
     * </p>
     * 
     * @param name
     *            The name to give the resource group.
     * @throws OgreException
     */
    public void createResourceGroup(String name) throws OgreException {
        createResourceGroup(pInstance.getValue(), name);
    }

    /**
     * Destroys a resource group, clearing it first, destroying the resources
     * which are part of it, and then removing it from the list of resource
     * groups.
     * 
     * @param name
     *            The name of the resource group to destroy.
     * @throws OgreException
     */
    public void destroyResourceGroup(String name) throws OgreException {
        destroyResourceGroup(pInstance.getValue(), name);
    }

    /**
     * Initialise all resource groups which are yet to be initialised.
     * 
     * @throws OgreException
     */
    public void initialiseAllResourceGroups() throws OgreException {
        initialiseAllResourceGroups(pInstance.getValue());
    }

    /**
     * Initialises a resource group.
     * 
     * <p>
     * <b>Remarks:</b><br/>After creating a resource group, adding some
     * resource locations, and perhaps pre-declaring some resources using
     * declareResource(), but before you need to use the resources in the group,
     * you should call this method to initialise the group. By calling this, you
     * are triggering the following processes:
     * <ul>
     * <li>Scripts for all resource types which support scripting are parsed
     * from the resource locations, and resources within them are created (but
     * not loaded yet).</li>
     * <li> Creates all the resources which have just pre-declared using
     * declareResource (again, these are not loaded yet)</li>
     * </ul>
     * So what this essentially does is create a bunch of unloaded Resource
     * entries in the respective ResourceManagers based on scripts, and
     * resources you've pre-declared. That means that code looking for these
     * resources will find them, but they won't be taking up much memory yet,
     * until they are either used, or they are loaded in bulk using
     * loadResourceGroup. Loading the resource group in bulk is entirely
     * optional, but has the advantage of coming with progress reporting as
     * resources are loaded. Failure to call this method means that
     * loadResourceGroup will do nothing, and any resources you define in
     * scripts will not be found. Similarly, once you have called this method
     * you won't be able to pick up any new scripts or pre-declared resources,
     * unless you call clearResourceGroup, set up declared resources, and call
     * this method again.
     * </p>
     * <p>
     * <b>Note:</b><br/>When you call Root::initialise, all resource groups
     * that have already been created are automatically initialised too.
     * Therefore you do not need to call this method for groups you define and
     * set up before you call Root::initialise. However, since one of the most
     * useful features of resource groups is to set them up after the main
     * system initialisation has occurred (e.g. a group per game level), you
     * must remember to call this method for the groups you create after this.
     * </p>
     * 
     * @param name
     *            The name of the resource group to initialise.
     * @throws OgreException
     */
    public void initialiseResourceGroup(String name) throws OgreException {
        initialiseResourceGroup(pInstance.getValue(), name);
    }

    /**
     * Loads a resource group.
     * 
     * <p>
     * <b>Remarks:</b><br/> Loads any created resources which are part of the
     * named group. Note that resources must have already been created by
     * calling ResourceManager::create, or declared using declareResource() or
     * in a script (such as .material and .overlay). The latter requires that
     * initialiseResourceGroup has been called. When this method is called, this
     * class will callback any ResourceGroupListeners which have been registered
     * to update them on progress.
     * 
     * @param name
     *            The name to of the resource group to load.
     * @param loadMainResources
     *            If true, loads normal resources associated with the group (you
     *            might want to set this to false if you wanted to just load
     *            world geometry in bulk)
     * @param loadWorldGeom
     *            If true, loads any linked world geometry
     * @throws OgreException
     */
    public void loadResourceGroup(String name, boolean loadMainResources,
            boolean loadWorldGeom) throws OgreException {
        loadResourceGroup(pInstance.getValue(), name, loadMainResources,
                loadWorldGeom);
    }

    /**
     * Removes a resource location from the search path.
     * 
     * @param name
     * @param resGroup
     * @throws OgreException
     */
    public void removeResourceLocation(String name, String resGroup)
            throws OgreException {
        removeResourceLocation(pInstance.getValue(), name, resGroup);
    }

    /**
     * Shutdown all ResourceManagers, performed as part of clean-up.
     * 
     * @throws OgreException
     */
    public void shutdownAll() throws OgreException {
        shutdownAll(pInstance.getValue());
    }

    /**
     * Unloads a resource group.
     * 
     * <p>
     * <b>Remarks:</b><br/>This method unloads all the resources that have
     * been declared as being part of the named resource group. Note that these
     * resources will still exist in their respective ResourceManager classes,
     * but will be in an unloaded state. If you want to remove them entirely,
     * you should use clearResourceGroup or destroyResourceGroup.
     * </p>
     * 
     * @param name
     *            The name to of the resource group to unload.
     * @throws OgreException
     */
    public void unloadResourceGroup(String name) throws OgreException {
        unloadResourceGroup(pInstance.getValue(), name);
    }
}
