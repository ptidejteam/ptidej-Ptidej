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
 * SceneManager.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.7 $
 * $Date: 2005/08/20 03:01:56 $
 * $Author: earl3982 $
 */
package org.ogre4j;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author Kai Klesatschke <yavin@ogre4j.org> TODO SceneManager type/class
 *         description. status: no has/get/setoption & getoptionvalues/keys
 *         about iterator
 */
public class SceneManager extends NativeObject {
    /** Render all except the queues in the special case list. */
    public static final int SCRQM_EXCLUDE = 1;

    /** Render only the queues in the special case list. */
    public static final int SCRQM_INCLUDE = 0;

    private static native void _applySceneAnimations(int ptrSelf);

    private static native void _findVisibleObjects(int ptrSelf, int ptrCam,
            boolean onlyShadowCasters);

    private static native void _notifyAutotrackingSceneNode(int ptrSelf,
            int ptrNode, boolean autoTrack);

    private static native void _queueSkiesForRendering(int ptrSelf, int ptrCam);

    private static native void _renderVisibleObjects(int ptrSelf);

    private static native void addRenderQueueListener(int ptrSelf,
            int ptrNewListener);

    private static native void addSpecialCaseRenderQueue(int ptrSelf, int qid);

    private static native void clearScene(int pInstance) throws OgreException;

    private static native void clearSpecialCaseRenderQueues(int ptrSelf);

    private static native int createAnimation(int pInstance, String name,
            float length) throws OgreException;

    private static native int createAnimationState(int pInstance,
            String animName) throws OgreException;

    private static native int createBillboardSet(int pInstance, String name)
            throws OgreException;

    private static native int createCamera(int pInstance, String name)
            throws OgreException;

    private static native int createEntity(int ptrSelf, String entityName,
            int pType);

    private static native int createEntity(int pInstance, String entityName,
            String meshName) throws OgreException;

    private static native int createInstance();

    private static native int createLight(int pInstace, String name)
            throws OgreException;

    private static native int createRayQuery(int pInstance, int pRay, long mask)
            throws OgreException;

    private static native int createSceneNode(int ptrSelf);

    private static native int createSceneNode(int ptrSelf, String name);

    private static native int createStaticGeometry(int pInstance, String name)
            throws OgreException;

    private static native void destroyAllAnimations(int ptrSelf);

    private static native void destroyAllAnimationStates(int ptrSelf);

    private static native void destroyAnimation(int ptrSelf, String name);

    private static native void destroyAnimationState(int ptrSelf, String name);

    private static native void destroyQuery(int ptrSelf, int ptrQuery);

    private static native void destroySceneNode(int ptrSelf, String name);

    private static native int estimateWorldGeometry(int ptrSelf, String filename);

    private static native int getAnimation(int ptrSelf, String name);

    private static native Animation[] getAnimationIterator(int ptrSelf);

    private static native AnimationState[] getAnimationStateIterator(int ptrSelf);

    private static native BillboardSet[] getBillboardSetIterator(int ptrSelf);

    private static native int getCamera(int ptrSelf, String name);

    private static native Camera[] getCameraIterator(int ptrSelf);

    private static native int getEntity(int ptrSelf, String name);

    private static native Entity[] getEntityIterator(int ptrSelf);

    private static native float getFogEnd(int ptrSelf);

    private static native int getFogMode(int ptrSelf);

    private static native int getFogStart(int ptrSelf);

    private static native Light[] getLightIterator(int ptrSelf);

    private static native int getRootSceneNode(int pInstance);

    private static native int getShadowIndexBufferSize(int ptrSelf);

    private static native short getShadowTextureCount(int ptrSelf);

    private static native boolean getShowBoundingBoxes(int pInstance);

    private static native boolean getShowDebugShadows(int ptrSelf);

    private static native int getSuggestedViewpoint(int ptrSelf, boolean random);

    private static native int getWorldGeometryRenderQueue(int ptrSelf);

    private static native boolean isRenderQueueToBeProcessed(int ptrSelf);

    private static native void removeAllBillboardSets(int ptrSelf);

    private static native void removeAllCameras(int ptrSelf);

    private static native void removeAllEntities(int ptrSelf);

    private static native void removeAllLights(int ptrSelf);

    private static native void removeAllStaticGeometry(int ptrSelf);

    private static native void removeBillboardSet(int ptrSelf, int ptrSet);

    private static native void removeBillboardSet(int ptrSelf, String name);

    private static native void removeCamera(int ptrSelf, int ptrCam);

    private static native void removeCamera(int ptrSelf, String name);

    private static native void removeEntity(int ptrSelf, int ptrEnt);

    private static native void removeEntity(int ptrSelf, String name);

    private static native void removeLight(int ptrSelf, int ptrLight);

    private static native void removeLight(int ptrSelf, String name);

    private static native void removeSpecialCaseRenderQueue(int ptrSelf, int qid);

    private static native void removeStaticGeometry(int ptrSelf, int ptrGeom);

    private static native void removeStaticGeometry(int ptrSelf, String name);

    private static native void setAmbientLight(int pInstance, float r, float g,
            float b, float a);

    private static native void setDisplaySceneNodes(int ptrSelf, boolean display);

    private static native void setFog(int ptrSelf, int mode, float colourR,
            float colourG, float colourB, float colourA, float expDensity,
            float linearDensity, float linearEnd);

    private static native void setShadowColour(int ptrSelf, float r, float g,
            float b, float a);

    private static native void setShadowDirectionalLightExtrusionDistance(
            int ptrSelf, float dist);

    private static native void setShadowDirLightTextureOffset(int ptrSelf,
            float offsetT);

    private static native void setShadowFarDistance(int ptrSelf, float distance);

    private static native void setShadowIndexBufferSize(int ptrSelf, int size);

    private static native void setShadowTextureCasterMaterial(int ptrSelf,
            String name);

    private static native void setShadowTextureCount(int ptrSelf, short count);

    private static native void setShadowTextureFadeEnd(int ptrSelf,
            float fadeEnd);

    private static native void setShadowTextureFadeStart(int ptrSelf,
            float fadeStart);

    private static native void setShadowTextureReceiverMaterial(int ptrSelf,
            String name);

    private static native void setShadowTextureSelfShadow(int ptrSelf,
            boolean selfShadow);

    private static native void setShadowTextureSize(int ptrSelf, short size);

    private static native void setShadowUseInfiniteFarPlane(int ptrSelf,
            boolean enable);

    private static native void setShowDebugShadows(int ptrSelf, boolean debug);

    private static native void setSkyBox(int ptrSelf, boolean enable,
            String materialName, float distance, boolean drawFirst,
            float orientW, float orientX, float orientY, float orientZ,
            String groupName);

    private static native void setSkyPlane(int ptrSelf, boolean enable,
            Plane plane, String materialName, float scale, float tiling,
            boolean drawFirst, float bow, int xsegments, int ysegments,
            String groupName);

    private static native void setSpecialCaseRenderQueueMode(int ptrSelf,
            int mode);

    private static native void setWorldGeometry(int ptrSelf, String filename);

    private static native void setWorldGeometryRenderQueue(int ptrSelf, int qid);

    private static native void showBoundingBoxes(int pInstance, boolean bShow);

    /**
     * @param ptrSceneMgr
     */
    public SceneManager() {
        super(new InstancePointer(createInstance()));
    }

    public SceneManager(InstancePointer pInstance) {
        super(pInstance);
    }

    /**
     * Internal method for applying animations to scene nodes.
     * 
     */
    public void _applySceneAnimations() {
        _applySceneAnimations(pInstance.getValue());
    }

    /**
     * Internal method which parses the scene to find visible objects to render.
     * 
     * @param cam
     * @param onlyShadowCasters
     */
    public void _findVisibleObjects(Camera cam, boolean onlyShadowCasters) {
        _findVisibleObjects(pInstance.getValue(), cam.pInstance.getValue(),
                onlyShadowCasters);
    }

    /**
     * Internal method for notifying the manager that a SceneNode is
     * autotracking.
     * 
     * @param node
     * @param autoTrack
     */
    public void _notifyAutotrackingSceneNode(SceneNode node, boolean autoTrack) {
        _notifyAutotrackingSceneNode(pInstance.getValue(), node.pInstance
                .getValue(), autoTrack);
    }

    /**
     * Populate a light list with an ordered set of the lights which are closest
     * to the position specified.
     * 
     * @param position
     * @param radius
     * @param destList
     */
    public void _populateLightList(Vector3 position, float radius,
            Vector<Light> destList) {
        // TODO
    }

    /**
     * Internal method for queueing the sky objects with the params as
     * previously set through setSkyBox, setSkyPlane and setSkyDome.
     * 
     * @param cam
     */
    public void _queueSkiesForRendering(Camera cam) {
        _queueSkiesForRendering(pInstance.getValue(), cam.pInstance.getValue());
    }

    /**
     * Prompts the class to send its contents to the renderer.
     * 
     * @param camera
     * @param vp
     * @param includeOverlays
     */
    public void _renderScene(Camera camera, Viewport vp, boolean includeOverlays) {
        // TODO
    }

    /**
     * Sends visible objects found in _findVisibleObjects to the rendering
     * engine.
     * 
     */
    public void _renderVisibleObjects() {
        _renderVisibleObjects(pInstance.getValue());
    }

    /**
     * Notifies the scene manager of its destination render system.
     * 
     * @param sys
     */
    public void _setDestinationRenderSystem(RenderSystem sys) {
        // TODO
    }

    /**
     * Internal method for updating the scene graph ie the tree of SceneNode
     * instances managed by this class.
     * 
     * @param cam
     */
    public void _updateSceneGraph(Camera cam) {
        // TODO
    }

    /**
     * Registers a new RenderQueueListener which will be notified when render
     * queues are processed.
     * 
     * @param newListener
     */
    public void addRenderQueueListener(RenderQueueListener newListener) {
        addRenderQueueListener(pInstance.getValue(), newListener.pInstance
                .getValue());

    }

    /**
     * Adds an item to the 'special case' render queue list.
     * 
     * @param qid
     */
    public void addSpecialCaseRenderQueue(int qid) {
        addSpecialCaseRenderQueue(pInstance.getValue(), qid);
    }

    /**
     * Empties the entire scene, inluding all SceneNodes, Entities, Lights,
     * BillboardSets etc.
     * 
     * @throws OgreException
     */
    public void clearScene() throws OgreException {
        clearScene(pInstance.getValue());
    }

    /**
     * Clears the 'special case' render queue list.
     * 
     */
    public void clearSpecialCaseRenderQueues() {
        clearSpecialCaseRenderQueues(pInstance.getValue());
    }

    /**
     * Creates an AxisAlignedBoxSceneQuery for this scene manager.
     * 
     * @param box
     * @param mask
     * @return
     */
    public AxisAlignedBoxSceneQuery createAABBQuery(AxisAlignedBox box,
            long mask) {
        // TODO
        return null;
    }

    /**
     * Creates an animation which can be used to animate scene nodes.
     * <p>
     * <b>Remarks:</b><br/> An animation is a collection of 'tracks' which
     * over time change the position / orientation of Node objects. In this
     * case, the animation will likely have tracks to modify the position /
     * orientation of SceneNode objects, e.g. to make objects move along a path.
     * You don't need to use an Animation object to move objects around - you
     * can do it yourself using the methods of the Node in your FrameListener
     * class. However, when you need relatively complex scripted animation, this
     * is the class to use since it will interpolate between keyframes for you
     * and generally make the whole process easier to manage. A single animation
     * can affect multiple Node objects (each AnimationTrack affects a single
     * Node). In addition, through animation blending a single Node can be
     * affected by multiple animations, athough this is more useful when
     * performing skeletal animation (see Skeleton::createAnimation). Note that
     * whilst it uses the same classes, the animations created here are kept
     * separate from the skeletal animations of meshes (each Skeleton owns those
     * animations).
     * </p>
     * 
     * @param name
     *            The name of the animation, must be unique within this
     *            SceneManager.
     * @param length
     *            The total length of the animation.
     * @return
     * @throws OgreException
     */
    public Animation createAnimation(String name, float length)
            throws OgreException {
        int ptr = createAnimation(pInstance.getValue(), name, length);
        if (ptr == 0)
            return null;

        return new Animation(new InstancePointer(ptr));

    }

    /**
     * Create an AnimationState object for managing application of animations.
     * 
     * @param animState
     * @return
     */
    public AnimationState createAnimationState(String animState)
            throws OgreException {
        int address = createAnimationState(pInstance.getValue(), animState);
        InstancePointer ptr = new InstancePointer(address);
        return new AnimationState(ptr);
    }

    /**
     * @param string
     * @return
     * @throws OgreException
     */
    public BillboardSet createBillboardSet(String name) throws OgreException {
        int ptr = createBillboardSet(pInstance.getValue(), name);
        InstancePointer ptrBillboardSet = new InstancePointer(ptr);
        return new BillboardSet(ptrBillboardSet);
    }

    public BillboardSet createBillboardSet(String string, int poolSize) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Creates a camera to be managed by this scene manager.
     * 
     * @param name
     * @return
     * @throws OgreException
     */
    public Camera createCamera(String name) throws OgreException {
        int address = createCamera(pInstance.getValue(), name);
        InstancePointer ptr = new InstancePointer(address);
        return new Camera(ptr);
    }

    /**
     * Create an Entity (instance of a discrete mesh) from a range of prefab
     * shapes.
     * 
     * @param entityName
     * @param ptype
     * @return
     */
    public Entity createEntity(String entityName, int ptype) {
        int ptr = createEntity(pInstance.getValue(), entityName, ptype);
        return new Entity(new InstancePointer(ptr));
    }

    /**
     * Create an Entity (instance of a discrete mesh).
     * 
     * @param entityName
     *            The name to be given to the entity (must be unique).
     * @param meshName
     *            The name of the Mesh it is to be based on (e.g. 'knot.oof').
     *            The mesh will be loaded if it is not already.
     * @throws OgreException
     */

    public Entity createEntity(String entityName, String meshName)
            throws OgreException {
        int address = createEntity(pInstance.getValue(), entityName, meshName);
        InstancePointer ptr = new InstancePointer(address);
        return new Entity(ptr);
    }

    /**
     * Creates an IntersectionSceneQuery for this scene manager
     * 
     * @param mask
     * @return
     */
    public IntersectionSceneQuery createIntersectionQuery(long mask) {
        return null;
    }

    /**
     * Creates a light for use in the scene.
     * 
     * @param lightName
     * @return
     * @throws OgreException
     */
    public Light createLight(String lightName) throws OgreException {
        int address = createLight(pInstance.getValue(), lightName);
        InstancePointer ptrLight = new InstancePointer(address);
        return new Light(ptrLight);
    }

    /**
     * Creates a PlaneBoundedVolumeListSceneQuery for this scene manager.
     */
    public PlaneBoundedVolumeListSceneQuery createPlaneBoundedVolumeQuery() {
        // TODO
        return null;
    }

    /**
     * Creates a RaySceneQuery for this scene manager.
     * 
     * @param ray
     * @param mask
     * @return
     */
    public RaySceneQuery createRayQuery(Ray ray, long mask)
            throws OgreException {
        // TODO C++
        InstancePointer ptrRaySceneQuery = new InstancePointer(createRayQuery(
                pInstance.getValue(), ray.getInstancePtr().getValue(), mask));
        return new RaySceneQuery(ptrRaySceneQuery);
    }

    /**
     * Creates an instance of a SceneNode.
     * 
     * @return
     */
    public SceneNode createSceneNode() {
        InstancePointer ptr = new InstancePointer(createSceneNode(pInstance
                .getValue()));
        return new SceneNode(ptr);
    }

    /**
     * Creates an instance of a SceneNode with a given name.
     * 
     * @param name
     * @return
     */
    public SceneNode createSceneNode(String name) {
        int address = createSceneNode(pInstance.getValue(), name);
        InstancePointer ptr = new InstancePointer(address);
        return new SceneNode(ptr);
    }

    /**
     * Creates a SphereSceneQuery for this scene manager.
     * 
     * @param sphere
     * @param mask
     * @return
     */
    public SphereSceneQuery createSphereQuery(Sphere sphere, long mask) {
        return null;
    }

    /**
     * @param name
     * @return
     * @throws OgreException
     */
    public StaticGeometry createStaticGeometry(String name)
            throws OgreException {
        int ptr = createStaticGeometry(pInstance.getValue(), name);
        InstancePointer ptrStaticGeometry = new InstancePointer(ptr);
        return new StaticGeometry(ptrStaticGeometry);
    }

    /**
     * Removes all animations created using this SceneManager.
     * 
     */
    public void destroyAllAnimations() {
        destroyAllAnimations(pInstance.getValue());
    }

    /**
     * Removes all animation states created using this SceneManager.
     * 
     */
    public void destroyAllAnimationStates() {
        destroyAllAnimationStates(pInstance.getValue());
    }

    /**
     * Destroys an Animation.
     * 
     * @param name
     */
    public void destroyAnimation(String name) {
        destroyAnimation(pInstance.getValue(), name);
    }

    /**
     * Destroys an AnimationState.
     * 
     * @param name
     */
    public void destroyAnimationState(String name) {
        destroyAnimationState(pInstance.getValue(), name);
    }

    /**
     * Destroys a scene query of any type.
     * 
     * @param query
     */
    public void destroyQuery(SceneQuery query) {
        destroyQuery(pInstance.getValue(), query.pInstance.getValue());
    }

    /**
     * Destroys a SceneNode with a given name.
     * 
     * @param name
     */
    public void destroySceneNode(String name) {
        destroySceneNode(pInstance.getValue(), name);
    }

    /**
     * Estimate the number of loading stages required to load the named world
     * geometry.
     * 
     * @param filename
     * @return
     */
    public int estimateWorldGeometry(String filename) {
        return estimateWorldGeometry(pInstance.getValue(), filename);
    }

    /**
     * Returns the ambient light level to be used for the scene.
     * 
     * @return
     */
    public ColourValue getAmbientColor() {
        return null;
    }

    /**
     * Looks up an Animation object previously created with createAnimation.
     * 
     * @param name
     * @return
     */
    public Animation getAnimation(String name) {
        int ptr = getAnimation(pInstance.getValue(), name);
        return new Animation(new InstancePointer(ptr));
    }

    /**
     * Returns a specialised MapIterator over all animations in the scene.
     * 
     * @return
     */
    public Iterator<Animation> getAnimationIterator() {
        List<Animation> l = Arrays.asList(getAnimationIterator(pInstance
                .getValue()));
        return l.iterator();
    }

    /**
     * Retrieves animation state as previously created using
     * createAnimationState.
     * 
     * @param animState
     * @return
     */
    public AnimationState getAnimationState(String animState) {
        return null;
    }

    /**
     * Returns a specialised MapIterator over all animation states in the scene.
     * 
     * @return
     */
    public Iterator<AnimationState> getAnimationStateIterator() {
        List<AnimationState> l = Arrays
                .asList(getAnimationStateIterator(pInstance.getValue()));
        return l.iterator();
    }

    /**
     * Retrieves a pointer to the named BillboardSet.
     * 
     * @param name
     * @return
     */
    public BillboardSet getBillboardSet(String name) {
        return null;
    }

    /**
     * Returns a specialised MapIterator over all BillboardSets in the scene.
     * 
     * @return
     */
    public Iterator<BillboardSet> getBillboardSetIterator() {
        List<BillboardSet> l = Arrays.asList(getBillboardSetIterator(pInstance
                .getValue()));
        return l.iterator();
    }

    /**
     * Retrieves a pointer to the named camera.
     * 
     * @param name
     * @return
     */
    public Camera getCamera(String name) {
        int ptr = getCamera(pInstance.getValue(), name);
        return new Camera(new InstancePointer(ptr));
    }

    /**
     * Returns a specialised MapIterator over all cameras in the scene.
     * 
     * @return
     */
    public Iterator<Camera> getCameraIterator() {
        List<Camera> l = Arrays.asList(getCameraIterator(pInstance.getValue()));
        return l.iterator();
    }

    /**
     * Retrieves a pointer to the named Entity.
     * 
     * @param name
     * @return
     */
    public Entity getEntity(String name) {
        int ptr = getEntity(pInstance.getValue(), name);
        return new Entity(new InstancePointer(ptr));
    }

    /**
     * Returns a specialised MapIterator over all entities in the scene.
     * 
     * @return
     */
    public Iterator<Entity> getEntityIterator() {
        List<Entity> l = Arrays.asList(getEntityIterator(pInstance.getValue()));
        return l.iterator();
    }

    /**
     * Returns the fog colour for the scene.
     * 
     * @return
     */
    public ColourValue getFogColour() {
        return null;
    }

    /**
     * Returns the fog density for the scene.
     * 
     * @return
     */
    public float getFogDensity() {
        return 0.0f;
    }

    /**
     * Returns the fog end distance for the scene.
     * 
     * @return
     */
    public float getFogEnd() {
        return getFogEnd(pInstance.getValue());
    }

    /**
     * Returns the fog mode for the scene.
     * 
     * @return
     */
    public int getFogMode() {
        return getFogMode(pInstance.getValue());
    }

    /**
     * Returns the fog start distance for the scene.
     * 
     * @return
     */
    public float getFogStart() {
        return getFogStart(pInstance.getValue());
    }

    /**
     * Returns a pointer to the named Light which has previously been added to
     * the scene.
     * 
     * @param name
     * @return
     */
    public Light getLight(String name) {
        return null;
    }

    /**
     * Returns a specialised MapIterator over all lights in the scene.
     * 
     * @return
     */
    public Iterator<Light> getLightIterator() {
        List<Light> l = Arrays.asList(getLightIterator(pInstance.getValue()));
        return l.iterator();
    }

    /**
     * Gets the SceneNode at the root of the scene hierarchy.
     * 
     * @return
     */
    public SceneNode getRootSceneNode() {
        int ptr = getRootSceneNode(pInstance.getValue());
        return new SceneNode(new InstancePointer(ptr));
    }

    /**
     * Retrieves a named SceneNode from the scene graph.
     * 
     * @param name
     * @return
     */
    public SceneNode getSceneNode(String name) {
        return null;
    }

    /**
     * Get the colour used to modulate areas in shadow.
     * 
     * @return
     */
    public ColourValue getShadowColour() {
        return null;
    }

    /**
     * Gets the distance a shadow volume is extruded for a directional light.
     * 
     * @return
     */
    public float getShadowDirectionalLightExtrusionDistance() {
        return 0.0f;
    }

    /**
     * Gets the maximum distance away from the camera that shadows will be
     * visible.
     * 
     * @return
     */
    public float getShadowFarDistance() {
        return 0.0f;
    }

    /**
     * Get the size of the shadow index buffer.
     * 
     * @return
     */
    public int getShadowIndexBufferSize() {
        return getShadowIndexBufferSize(pInstance.getValue());
    }

    /**
     * Gets the current shadow technique.
     * 
     * @return
     */
    public ShadowTechnique getShadowTechnique() {
        return null;
    }

    /**
     * Get the number of the textures allocated for texture based shadows.
     * 
     * @return
     */
    public short getShadowTextureCount() {
        return getShadowTextureCount(pInstance.getValue());
    }

    /**
     * Get the format of the textures used for texture based shadows.
     * 
     * @return
     */
    public PixelFormat getShadowTexturePixelFormat() {
        return null;
    }

    /**
     * Gets whether or not texture shadows attempt to self-shadow.
     * 
     * @return
     */
    public boolean getShadowTextureSelfShadow() {
        return false;
    }

    /**
     * Get the size of the texture used for texture based shadows.
     * 
     * @return
     */
    public short getShadowTextureSize() {
        return 0;
    }

    /**
     * Returns if all bounding boxes of scene nodes are to be displayed.
     * 
     * @return
     */
    public boolean getShowBoundingBoxes() {
        return getShowBoundingBoxes(pInstance.getValue());
    }

    /**
     * Are debug shadows shown?
     * 
     * @return
     */
    public boolean getShowDebugShadows() {
        return getShowDebugShadows(pInstance.getValue());
    }

    /**
     * Retrieve a previously created StaticGeometry instance.
     * 
     * @param name
     * @return
     */
    public StaticGeometry getStaticGeometry(String name) {
        return null;
    }

    /**
     * Asks the SceneManager to provide a suggested viewpoint from which the
     * scene should be viewed.
     * 
     * @return
     */
    public Viewport getSuggestedViewpoint() {
        return getSuggestedViewpoint(false);
    }

    /**
     * Asks the SceneManager to provide a suggested viewpoint from which the
     * scene should be viewed.
     * 
     * @return
     */
    public Viewport getSuggestedViewpoint(boolean random) {
        int address = getSuggestedViewpoint(pInstance.getValue(), random);
        InstancePointer ptr = new InstancePointer(address);
        return new Viewport(ptr);
    }

    /**
     * Gets the render queue that the world geometry (if any) this SceneManager
     * renders will be associated with.
     * 
     * @return
     */
    public int getWorldGeometryRenderQueue() {
        return getWorldGeometryRenderQueue(pInstance.getValue());
    }

    /**
     * Returns whether or not the named queue will be rendered based on the
     * current 'special case' render queue list and mode.
     * 
     * @return
     */
    // TODO public boolean isRenderQueueToBeProcessed() {
    /**
     * Manual rendering method, for advanced users only.
     * 
     * @param rend
     * @param pass
     * @param vp
     * @param worldMatrix
     * @param viewMatrix
     * @param projMatrix
     * @param doBeginEndFrame
     */
    public void manualRender(RenderOperation rend, Pass pass, Viewport vp,
            Matrix4 worldMatrix, Matrix4 viewMatrix, Matrix4 projMatrix,
            boolean doBeginEndFrame) {
        // TODO
    }

    /**
     * Removes & destroys all BillboardSets.
     * 
     */
    public void removeAllBillboardSets() {
        removeAllBillboardSets(pInstance.getValue());
    }

    /**
     * Removes (and destroys) all cameras from the scene.
     * 
     */
    public void removeAllCameras() {
        removeAllCameras(pInstance.getValue());
    }

    /**
     * Removes & destroys all Entities.
     * 
     */
    public void removeAllEntities() {
        removeAllEntities(pInstance.getValue());
    }

    /**
     * Removes and destroys all lights in the scene.
     * 
     */
    public void removeAllLights() {
        removeAllLights(pInstance.getValue());
    }

    /**
     * Remove & destroy all StaticGeometry instances.
     * 
     */
    public void removeAllStaticGeometry() {
        removeAllStaticGeometry(pInstance.getValue());
    }

    /**
     * Removes & destroys an BillboardSet from the SceneManager.
     * 
     * @param set
     */
    public void removeBillboardSet(BillboardSet set) {
        removeBillboardSet(pInstance.getValue(), set.pInstance.getValue());
    }

    /**
     * Removes & destroys an BillboardSet from the SceneManager by name.
     * 
     * @param name
     */
    public void removeBillboardSet(String name) {
        removeBillboardSet(pInstance.getValue(), name);
    }

    /**
     * Removes a camera from the scene.
     * 
     * @param cam
     */
    public void removeCamera(Camera cam) {
        removeCamera(pInstance.getValue(), cam.pInstance.getValue());
    }

    /**
     * Removes a camera from the scene.
     * 
     * @param name
     */
    public void removeCamera(String name) {
        removeCamera(pInstance.getValue(), name);
    }

    /**
     * Removes & destroys an Entity from the SceneManager.
     * 
     * @param ent
     */
    public void removeEntity(Entity ent) {
        removeEntity(pInstance.getValue(), ent.pInstance.getValue());
    }

    /**
     * Removes & destroys an Entity from the SceneManager by name.
     * 
     * @param name
     */
    public void removeEntity(String name) {
        removeEntity(pInstance.getValue(), name);
    }

    /**
     * Removes the light from the scene and destroys it based on a pointer.
     * 
     * @param light
     */
    public void removeLight(Light light) {
        removeLight(pInstance.getValue(), light.pInstance.getValue());
    }

    /**
     * Removes the named light from the scene and destroys it.
     * 
     * @param name
     */
    public void removeLight(String name) {
        removeLight(pInstance.getValue(), name);
    }

    /**
     * Removes a listener previously added with addRenderQueueListener.
     * 
     * @param delListener
     */
    public void removeRenderQueueListener(RenderQueueListener delListener) {

    }

    /**
     * Removes an item to the 'special case' render queue list.
     * 
     * @param qid
     */
    public void removeSpecialCaseRenderQueue(int qid) {
        removeSpecialCaseRenderQueue(pInstance.getValue(), qid);
    }

    /**
     * Remove & destroy a StaticGeometry instance.
     * 
     * @param geom
     */
    public void removeStaticGeometry(StaticGeometry geom) {
        removeStaticGeometry(pInstance.getValue(), geom.pInstance.getValue());
    }

    /**
     * Remove & destroy a StaticGeometry instance.
     * 
     * @param name
     */
    public void removeStaticGeometry(String name) {
        removeStaticGeometry(pInstance.getValue(), name);
    }

    /**
     * Sets the ambient light level to be used for the scene.
     * 
     * @param colour
     */
    public void setAmbientLight(ColourValue colour) {
        setAmbientLight(pInstance.getValue(), colour.r, colour.g, colour.b,
                colour.a);
    }

    /**
     * Tells the SceneManager whether it should render the SceneNodes which make
     * up the scene as well as the objects in the scene.
     * 
     * @param display
     */
    public void setDisplaySceneNodes(boolean display) {
        setDisplaySceneNodes(pInstance.getValue(), display);
    }

    public void setFog(int fog_exp, ColourValue white, float f) {
        // TODO
    }

    /**
     * Sets the fogging mode applied to the scene.
     * 
     * @param mode
     * @param colour
     * @param expDensity
     * @param linearDensity
     * @param linearEnd
     */
    public void setFog(int mode, ColourValue colour, float expDensity,
            float linearDensity, float linearEnd) {
        setFog(pInstance.getValue(), mode, colour.r, colour.g, colour.b,
                colour.a, expDensity, linearDensity, linearEnd);
    }

    /**
     * Set the colour used to modulate areas in shadow.
     * 
     * @param colour
     */
    public void setShadowColour(ColourValue colour) {
        setShadowColour(pInstance.getValue(), colour.r, colour.g, colour.b,
                colour.a);
    }

    /**
     * Sets the distance a shadow volume is extruded for a directional light.
     * 
     * @param dist
     */
    public void setShadowDirectionalLightExtrusionDistance(float dist) {
        setShadowDirectionalLightExtrusionDistance(pInstance.getValue(), dist);
    }

    /**
     * Sets the proportional distance which a texture shadow which is generated
     * from a directional light will be offset into the camera view to make best
     * use of texture space.
     * 
     * @param offset
     */
    public void setShadowDirLightTextureOffset(float offset) {
        setShadowDirLightTextureOffset(pInstance.getValue(), offset);
    }

    /**
     * Sets the maximum distance away from the camera that shadows will be
     * visible.
     * 
     * @param distance
     */
    public void setShadowFarDistance(float distance) {
        setShadowFarDistance(pInstance.getValue(), distance);
    }

    /**
     * Sets the maximum size of the index buffer used to render shadow
     * primitives.
     * 
     * @param size
     */
    public void setShadowIndexBufferSize(int size) {
        setShadowIndexBufferSize(pInstance.getValue(), size);
    }

    /**
     * Sets the general shadow technique to be used in this scene.
     * 
     * @param technique
     */
    public void setShadowTechnique(ShadowTechnique technique) {
        // TODO
    }

    /**
     * Sets the default material to use for rendering shadow casters.
     * 
     * @param name
     */
    public void setShadowTextureCasterMaterial(String name) {
        setShadowTextureCasterMaterial(pInstance.getValue(), name);
    }

    /**
     * Set the number of textures allocated for texture-based shadows.
     * 
     * @param count
     */
    public void setShadowTextureCount(short count) {
        setShadowTextureCount(pInstance.getValue(), count);
    }

    /**
     * Sets the proportional distance at which texture shadows finish to fading
     * out.
     * 
     * @param fadeEnd
     */
    public void setShadowTextureFadeEnd(float fadeEnd) {
        setShadowTextureFadeEnd(pInstance.getValue(), fadeEnd);
    }

    /**
     * Sets the proportional distance at which texture shadows begin to fade
     * out.
     * 
     * @param fadeStart
     */
    public void setShadowTextureFadeStart(float fadeStart) {
        setShadowTextureFadeStart(pInstance.getValue(), fadeStart);
    }

    /**
     * Set the pixel format of the textures used for texture-based shadows.
     * 
     * @param fmt
     */
    public void setShadowTexturePixelFormat(PixelFormat fmt) {

    }

    /**
     * Sets the default material to use for rendering shadow receivers.
     * 
     * @param name
     */
    public void setShadowTextureReceiverMaterial(String name) {
        setShadowTextureReceiverMaterial(pInstance.getValue(), name);
    }

    /**
     * Sets whether or not texture shadows should attempt to self-shadow.
     * 
     * @param selfShadow
     */
    public void setShadowTextureSelfShadow(boolean selfShadow) {
        setShadowTextureSelfShadow(pInstance.getValue(), selfShadow);
    }

    /**
     * Sets the size and count of textures used in texture-based shadows.
     * 
     * @param size
     * @param count
     * @param fmt
     */
    public void setShadowTextureSettings(short size, short count,
            PixelFormat fmt) {

    }

    /**
     * Set the size of the texture used for texture-based shadows.
     * 
     * @param size
     */
    public void setShadowTextureSize(short size) {
        setShadowTextureSize(pInstance.getValue(), size);
    }

    /**
     * Sets whether we should use an inifinite camera far plane when rendering
     * stencil shadows.
     * 
     * @param enable
     */
    public void setShadowUseInfiniteFarPlane(boolean enable) {
        setShadowUseInfiniteFarPlane(pInstance.getValue(), enable);
    }

    /**
     * Enables / disables the rendering of debug information for shadows.
     * 
     * @param debug
     */
    public void setShowDebugShadows(boolean debug) {
        setShowDebugShadows(pInstance.getValue(), debug);
    }

    /**
     * Enables / disables a 'sky box' i.e.
     * 
     * @param enable
     * @param materialName
     * @param distance
     */
    public void setSkyBox(boolean enable, String materialName, float distance) {
        setSkyBox(enable, materialName, distance, true);
    }

    /**
     * Enables / disables a 'sky box' i.e.
     * 
     * @param enable
     * @param materialName
     * @param distance
     * @param drawFirst
     */
    public void setSkyBox(boolean enable, String materialName, float distance,
            boolean drawFirst) {
        setSkyBox(enable, materialName, distance, drawFirst,
                Quaternion.IDENTITY);
    }

    /**
     * Enables / disables a 'sky box' i.e.
     * 
     * @param enable
     * @param materialName
     * @param distance
     * @param drawFirst
     * @param orientation
     */
    public void setSkyBox(boolean enable, String materialName, float distance,
            boolean drawFirst, Quaternion orientation) {
        setSkyBox(enable, materialName, distance, drawFirst, orientation,
                ResourceGroupManager.DEFAULT_RESOURCE_GROUP_NAME);
    }

    /**
     * Enables / disables a 'sky box' i.e.
     * 
     * @param enable
     * @param materialName
     * @param distance
     * @param drawFirst
     * @param orientation
     * @param groupName
     */
    public void setSkyBox(boolean enable, String materialName, float distance,
            boolean drawFirst, Quaternion orientation, String groupName) {
        setSkyBox(pInstance.getValue(), enable, materialName, distance,
                drawFirst, orientation.w, orientation.x, orientation.y,
                orientation.z, groupName);
    }

    /**
     * Enables / disables a 'sky dome' i.e.
     * 
     * @param enable
     * @param materialName
     * @param curvature
     * @param tiling
     */
    public void setSkyDome(boolean enable, String materialName,
            float curvature, float tiling) {
    }

    /**
     * Enables / disables a 'sky dome' i.e.
     * 
     * @param enable
     * @param materialName
     * @param curvature
     * @param tiling
     * @param distance
     * @param drawFirst
     * @param orientation
     * @param xsegments
     * @param ysegments
     * @param ysegments_keep
     * @param groupName
     */
    public void setSkyDome(boolean enable, String materialName,
            float curvature, float tiling, float distance, boolean drawFirst,
            Quaternion orientation, int xsegments, int ysegments,
            int ysegments_keep, String groupName) {
    }

    /**
     * Enables / disables a 'sky plane' i.e.
     * 
     * @param enable
     * @param plane
     * @param materialName
     * @param scale
     * @param tiling
     * @param drawFirst
     * @param bow
     * @param xsegments
     * @param ysegments
     * @param groupName
     */
    public void setSkyPlane(boolean enable, Plane plane, String materialName,
            float scale, float tiling, boolean drawFirst, float bow,
            int xsegments, int ysegments, String groupName) {
        setSkyPlane(pInstance.getValue(), enable, plane, materialName, scale,
                tiling, drawFirst, bow, xsegments, ysegments, groupName);
    }

    /**
     * Sets the way the special case render queue list is processed.
     * 
     * @param mode
     */
    public void setSpecialCaseRenderQueueMode(int mode) {
        setSpecialCaseRenderQueueMode(pInstance.getValue(), mode);
    }

    /**
     * Sets the source of the 'world' geometry, i.e.
     * 
     * @param filename
     */
    public void setWorldGeometry(String filename) {
        setWorldGeometry(pInstance.getValue(), filename);
    }

    /**
     * Sets the render queue that the world geometry (if any) this SceneManager
     * renders will be associated with.
     * 
     * @param qid
     */
    public void setWorldGeometryRenderQueue(int qid) {
        setWorldGeometryRenderQueue(pInstance.getValue(), qid);
    }

    /**
     * Allows all bounding boxes of scene nodes to be displayed.
     * 
     * @param bShow
     */
    public void showBoundingBoxes(boolean bShow) {
        showBoundingBoxes(pInstance.getValue(), bShow);
    }
}