///////////////////////////////////////////////////////////////////////////////
/// This source file is part of ogre4j
///     (The JNI bindings for OGRE)
/// For the latest info, see http://www.ogre4j.org/
/// 
/// Copyright (c) 2005 netAllied GmbH, Tettnang
/// Also see acknowledgements in Readme.html
/// 
/// This program is free software; you can redistribute it and/or modify it under
/// the terms of the GNU Lesser General Public License as published by the Free Software
/// Foundation; either version 2 of the License, or (at your option) any later
/// version.
/// 
/// This program is distributed in the hope that it will be useful, but WITHOUT
/// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
/// FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
/// 
/// You should have received a copy of the GNU Lesser General Public License along with
/// this program; if not, write to the Free Software Foundation, Inc., 59 Temple
/// Place - Suite 330, Boston, MA 02111-1307, USA, or go to
/// http://www.gnu.org/copyleft/lesser.txt.
///
///
/// @file Ogr4JRoot.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::D3D9RenderWindow class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org
/// @date     07.05.2005
///
/// $Revision: 1.6 $
/// $Date: 2005/08/26 06:52:30 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_SceneManager.h>


using namespace Ogre4JHelper;
/*
static DWORD tlsIndex = 0;
static jobject javaClass;

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_SceneManager_createInstance
(JNIEnv *env, jclass that)
{
Ogre4JHelper::normalLogMsg("-- Create SceneManager Instance --");
if (tlsIndex == 0) 
{
tlsIndex = TlsAlloc();
if (tlsIndex == -1) return (jint) 0;
javaClass = env->NewGlobalRef( static_cast<jobject>(that) );
}
TlsSetValue(tlsIndex, (LPVOID) env);

Ogre::SceneManager * sceneMgr = new Ogre::SceneManager();
RETURN_PTR(sceneMgr);
}
*/

/// void SceneManager._applySceneAnimations()
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager__1applySceneAnimations
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	self->_applySceneAnimations();
}


/// void SceneManager._findVisibleObjects(Camera cam, boolean onlyShadowCasters)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager__1findVisibleObjects
(JNIEnv *, jclass, jint ptrSelf, jint ptrCam, jboolean jOnlyShadowCasters)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::Camera * cam = GET_PTR(Ogre::Camera, ptrCam);

	bool onlyShadowCasters = (jOnlyShadowCasters == JNI_TRUE) ? true : false;
	self->_findVisibleObjects(cam, onlyShadowCasters);
}

/// void _notifyAutotrackingSceneNode(SceneNode node, boolean autoTrack)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager__1notifyAutotrackingSceneNode
  (JNIEnv *, jclass, jint ptrSelf, jint ptrNode, jboolean jautoTrack)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::SceneNode * node = GET_PTR(Ogre::SceneNode, ptrNode);
	bool autoTrack = (jautoTrack == JNI_TRUE) ? true : false;
	self->_notifyAutotrackingSceneNode(node, autoTrack);
}

/// void _queueSkiesForRendering(Camera cam)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager__1queueSkiesForRendering
  (JNIEnv *, jclass, jint ptrSelf, jint ptrCam)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::Camera * cam = GET_PTR(Ogre::Camera, ptrCam);
	self->_queueSkiesForRendering(cam);
}

/// void _renderVisibleObjects()
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager__1renderVisibleObjects
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	self->_renderVisibleObjects();
}

/// void addRenderQueueListener(RenderQueueListener newListener)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_addRenderQueueListener
  (JNIEnv *, jclass, jint ptrSelf, jint ptrNewListener)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::RenderQueueListener * newListener = GET_PTR(Ogre::RenderQueueListener, ptrNewListener);
	self->addRenderQueueListener(newListener);
}

/// void addSpecialCaseRenderQueue(int qid)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_addSpecialCaseRenderQueue
  (JNIEnv *, jclass, jint ptrSelf, jint jqid)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::RenderQueueGroupID qid = static_cast<Ogre::RenderQueueGroupID>(jqid);
	self->addSpecialCaseRenderQueue(qid);
}

/// void SceneManager.clearScene()
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_clearScene
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	self->clearScene();
}

/// void SceneManager.clearSpecialCaseRenderQueues()
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_clearSpecialCaseRenderQueues
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	self->clearSpecialCaseRenderQueues();
}

/// AnimationState createAnimationState(String animState)
JNIEXPORT jint JNICALL Java_org_ogre4j_SceneManager_createAnimationState
(JNIEnv * env, jclass, jint pInstance, jstring jsAnimName)
{
	try
	{
		Ogre::String sAnimName;
		Ogre4JHelper::CopyJString( env, jsAnimName, sAnimName );
		Ogre::SceneManager * sceneMgr = GET_PTR(Ogre::SceneManager, pInstance);
		Ogre::AnimationState * pAnimState = sceneMgr->createAnimationState(sAnimName);
		RETURN_PTR(pAnimState);
	}
	catch(Ogre::Exception & e)
	{
		jclass OgreException = env->FindClass("org/ogre4j/OgreException");
		env->ThrowNew(OgreException, e.getFullDescription().c_str() );
	}
	catch(...)
	{
		jclass OgreException = env->FindClass("org/ogre4j/OgreException");
		env->ThrowNew(OgreException, "Unkown exception!" );
	}
	return 0;
}

/// Entity SceneManager.createEntity(String entityName, int ptype)
JNIEXPORT jint JNICALL Java_org_ogre4j_SceneManager_createEntity__ILjava_lang_String_2I
(JNIEnv * env, jclass, jint ptrSelf, jstring jentityName, jint jptype)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::String entityName;
	Ogre::SceneManager::PrefabType ptype = static_cast<Ogre::SceneManager::PrefabType>(jptype);
	CopyJString(env, jentityName, entityName);
	Ogre::Entity * ret = self->createEntity(entityName, ptype);
	RETURN_PTR(ret);
}

/// Entity createEntity(String entityName, String meshName)
JNIEXPORT jint JNICALL Java_org_ogre4j_SceneManager_createEntity__ILjava_lang_String_2Ljava_lang_String_2
  (JNIEnv * env, jclass, jint ptrSelf, jstring jentityName, jstring jmeshName)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::String entityName, meshName;
	CopyJString(env, jentityName, entityName);
	CopyJString(env, jmeshName, meshName);
	try {
		Ogre::Entity * ret = self->createEntity(entityName, meshName);
		RETURN_PTR(ret);
	} catch(Ogre::Exception & e) {
		jclass OgreException = env->FindClass("org/ogre4j/OgreException");
		env->ThrowNew(OgreException, e.getFullDescription().c_str());
	} catch(...) {
		jclass OgreException = env->FindClass("org/ogre4j/OgreException");
		env->ThrowNew(OgreException, "UNKNOWN");
	}
	return 0;
}

/// Animation createAnimation(String name, float length)
JNIEXPORT jint JNICALL Java_org_ogre4j_SceneManager_createAnimation
  (JNIEnv * env, jclass, jint ptrSelf, jstring jname, jfloat jlength)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::String name;
	CopyJString(env, jname, name);
	Ogre::Animation * ret = self->createAnimation(name, jlength);
	RETURN_PTR(ret);
}

/// AnimationState createAnimationState(String animState)
JNIEXPORT jint JNICALL Java_org_ogre4j_SceneManager_createStaticGeometry
(JNIEnv * env, jclass, jint pInstance, jstring jsName)
{
	try
	{
		Ogre::String sName;
		Ogre4JHelper::CopyJString( env, jsName, sName );
		Ogre::SceneManager * sceneMgr = GET_PTR(Ogre::SceneManager, pInstance);
		Ogre::StaticGeometry * pStatic = sceneMgr->createStaticGeometry(sName);
		RETURN_PTR(pStatic);
	}
	catch(Ogre::Exception & e)
	{
		jclass OgreException = env->FindClass("org/ogre4j/OgreException");
		env->ThrowNew(OgreException, e.getFullDescription().c_str() );
	}
	catch(...)
	{
		jclass OgreException = env->FindClass("org/ogre4j/OgreException");
		env->ThrowNew(OgreException, "Unkown exception!" );
	}
	return 0;
}


/// SceneNode getRootSceneNode()
JNIEXPORT jint JNICALL Java_org_ogre4j_SceneManager_getRootSceneNode
(JNIEnv *, jclass, jint ptrSelf) 
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::SceneNode * ret = self->getRootSceneNode();
	RETURN_PTR(ret);
}


///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_SceneManager_createCamera
(JNIEnv *env, jclass, jint pInstance, jstring jsName)
{
	try
	{
		Ogre::String sName;
		Ogre4JHelper::CopyJString( env, jsName, sName );
		Ogre::SceneManager * sceneMgr = GET_PTR(Ogre::SceneManager,pInstance);
		Ogre::Camera * pCam = sceneMgr->createCamera( sName );
		RETURN_PTR(pCam);
	}
	catch(Ogre::Exception & e)
	{
		jclass OgreException = env->FindClass("org/ogre4j/OgreException");
		env->ThrowNew(OgreException, e.getFullDescription().c_str() );
	}
	catch(...)
	{
		jclass OgreException = env->FindClass("org/ogre4j/OgreException");
		env->ThrowNew(OgreException, "Unkown exception!" );
	}
	return 0;
}
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_SceneManager_createEntity
(JNIEnv *env, jclass, jint pInstance, jstring jsEntityName, jstring jsMeshName)
{
	try
	{   
		Ogre::String sEntityName, sMeshName;
		Ogre4JHelper::CopyJString( env, jsEntityName, sEntityName );
		Ogre4JHelper::CopyJString( env, jsMeshName, sMeshName );
		Ogre::SceneManager * sceneMgr = GET_PTR(Ogre::SceneManager, pInstance);
		Ogre::Entity * pEntity = sceneMgr->createEntity( sEntityName, sMeshName );
		RETURN_PTR(pEntity);   	
	}
	catch(Ogre::Exception & e)
	{
		jclass OgreException = env->FindClass("org/ogre4j/OgreException");
		env->ThrowNew(OgreException, e.getFullDescription().c_str() );
	}
	catch(...)
	{
		jclass OgreException = env->FindClass("org/ogre4j/OgreException");
		env->ThrowNew(OgreException, "Unkown exception!" );
	}
	return 0;
}



///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_SceneManager_createRayQuery
(JNIEnv *env, jclass, jint pInstance, jint pRay, jlong mask)  
{
	try
	{
		Ogre::SceneManager * sceneMgr = GET_PTR(Ogre::SceneManager, pInstance);
		Ogre::Ray * ray = GET_PTR(Ogre::Ray, pRay);
		Ogre::RaySceneQuery * pRaySceneQuery = sceneMgr->createRayQuery( *ray, mask);
		RETURN_PTR(pRaySceneQuery);
	}
	catch(Ogre::Exception & e)
	{
		jclass OgreException = env->FindClass("org/ogre4j/OgreException");
		env->ThrowNew(OgreException, e.getFullDescription().c_str() );
	}
	catch(...)
	{
		jclass OgreException = env->FindClass("org/ogre4j/OgreException");
		env->ThrowNew(OgreException, "Unkown exception!" );
	}
	return 0;
}


///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_SceneManager_createLight
(JNIEnv * env, jclass, jint pInstance, jstring jsLightName)
{
	try
	{
		Ogre::String sLightName;
		Ogre4JHelper::CopyJString( env, jsLightName, sLightName );
		Ogre::SceneManager * sceneMgr = GET_PTR(Ogre::SceneManager, pInstance);
		Ogre::Light * pLight = sceneMgr->createLight(sLightName);
		RETURN_PTR(pLight);
	}
	catch(Ogre::Exception & e)
	{
		jclass OgreException = env->FindClass("org/ogre4j/OgreException");
		env->ThrowNew(OgreException, e.getFullDescription().c_str() );
	}
	catch(...)
	{
		jclass OgreException = env->FindClass("org/ogre4j/OgreException");
		env->ThrowNew(OgreException, "Unkown exception!" );
	}

	return 0;
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_SceneManager_createBillboardSet
(JNIEnv *env, jclass, jint pInstance, jstring jsName)
{
	try
	{
		Ogre::String sName;
		Ogre4JHelper::CopyJString( env, jsName, sName );
		Ogre::SceneManager * sceneMgr = GET_PTR(Ogre::SceneManager, pInstance);
		Ogre::BillboardSet * pBBoardSet = sceneMgr->createBillboardSet(sName);
		RETURN_PTR(pBBoardSet);
	}
	catch(Ogre::Exception & e)
	{
		jclass OgreException = env->FindClass("org/ogre4j/OgreException");
		env->ThrowNew(OgreException, e.getFullDescription().c_str() );
	}
	catch(...)
	{
		jclass OgreException = env->FindClass("org/ogre4j/OgreException");
		env->ThrowNew(OgreException, "Unkown exception!" );
	}
	return 0;
}

/// boolean getShowBoundingBoxes()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_SceneManager_getShowBoundingBoxes
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	jboolean ret = self->getShowBoundingBoxes();
	return (ret == JNI_TRUE) ? true : false;
}

/// int getWorldGeometryRenderQueue()
JNIEXPORT jint JNICALL Java_org_ogre4j_SceneManager_getWorldGeometryRenderQueue
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	int qid = self->getWorldGeometryRenderQueue();
	return static_cast<Ogre::RenderQueueGroupID>(qid);
}


/// void removeAllBillboardSets()
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_removeAllBillboardSets
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	self->removeAllBillboardSets();
}

/// void removeAllCameras()
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_removeAllCameras
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	self->removeAllCameras();
}

/// void removeAllEntities()
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_removeAllEntities
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	self->removeAllEntities();
}

/// void removeAllLights()
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_removeAllLights
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	self->removeAllLights();
}

/// void removeAllStaticGeometry
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_removeAllStaticGeometry
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	self->removeAllStaticGeometry();
}

/// void removeBillboardSet(BillboardSet set)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_removeBillboardSet__II
(JNIEnv *, jclass, jint ptrSelf, jint ptrSet)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::BillboardSet * set = GET_PTR(Ogre::BillboardSet, ptrSet);
	self->removeBillboardSet(set);
}

/// void removeBillboardSet(String name)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_removeBillboardSet__ILjava_lang_String_2
(JNIEnv * env, jclass, jint ptrSelf, jstring jname)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::String name;
	CopyJString(env, jname, name);
	self->removeBillboardSet(name);
}

/// void removeCamera(Camera cam)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_removeCamera__II
(JNIEnv *, jclass, jint ptrSelf, jint ptrCam)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::Camera * cam = GET_PTR(Ogre::Camera, ptrCam);
	self->removeCamera(cam);
}

/// void removeCamera(String name)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_removeCamera__ILjava_lang_String_2
(JNIEnv * env, jclass, jint ptrSelf, jstring jname)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::String name;
	CopyJString(env, jname, name);
	self->removeCamera(name);
}

/// void removeEntity(Entity ent)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_removeEntity__II
(JNIEnv *, jclass, jint ptrSelf, jint ptrEnt)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::Entity * ent = GET_PTR(Ogre::Entity, ptrEnt);
	self->removeEntity(ent);
}

/// void removeEntity(String name)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_removeEntity__ILjava_lang_String_2
(JNIEnv * env, jclass, jint ptrSelf, jstring jname)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::String name;
	CopyJString(env, jname, name);
	self->removeEntity(name);
}

/// void removeLight(Light light)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_removeLight__II
(JNIEnv *, jclass, jint ptrSelf, jint ptrLight)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::Light * light = GET_PTR(Ogre::Light, ptrLight);
	self->removeLight(light);
}

/// void removeLight(String name)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_removeLight__ILjava_lang_String_2
(JNIEnv * env, jclass, jint ptrSelf, jstring jname)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::String name;
	CopyJString(env, jname, name);
	self->removeLight(name);
}

/// void removeSpecialCaseRenderQueue(int qid)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_removeSpecialCaseRenderQueue
(JNIEnv *, jclass, jint ptrSelf, jint jqid)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::RenderQueueGroupID qid = static_cast<Ogre::RenderQueueGroupID>(jqid);
	self->removeSpecialCaseRenderQueue(qid);
}


/// void removeStaticGeometry(StaticGeometry geom)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_removeStaticGeometry__II
(JNIEnv *, jclass, jint ptrSelf, jint ptrGeom)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::StaticGeometry * geom = GET_PTR(Ogre::StaticGeometry, ptrGeom);
	self->removeStaticGeometry(geom);
}

/// void removeStaticGeometry(String name)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_removeStaticGeometry__ILjava_lang_String_2
(JNIEnv * env, jclass, jint ptrSelf, jstring jname)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::String name;
	CopyJString(env, jname, name);
	self->removeStaticGeometry(name);
}

/// void setAmbientLight(ColourValue colour)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setAmbientLight
(JNIEnv *, jclass, jint ptrSelf, jfloat jR, jfloat jG, jfloat jB, jfloat jA)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::ColourValue colour(jR, jG, jB, jA);
	self->setAmbientLight(colour);
}

/// void setDisplaySceneNodes(boolean display)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setDisplaySceneNodes
(JNIEnv *, jclass, jint ptrSelf, jboolean jdisplay)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	bool display = (jdisplay == JNI_TRUE) ? true : false;
	self->setDisplaySceneNodes(display);
}

/// void setShadowColour(ColourValue colour)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setShadowColour
(JNIEnv *, jclass, jint ptrSelf, jfloat jR, jfloat jG, jfloat jB, jfloat jA)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::ColourValue colour(jR, jG, jB, jA);
	self->setShadowColour(colour);
}

/// void setShadowDirectionalLightExtrusionDistance(float dist)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setShadowDirectionalLightExtrusionDistance
(JNIEnv *, jclass, jint ptrSelf, jfloat jdist)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	self->setShadowDirectionalLightExtrusionDistance(jdist);
}

/// void setShadowDirLightTextureOffset(float offset)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setShadowDirLightTextureOffset
(JNIEnv *, jclass, jint ptrSelf, jfloat joffset)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	self->setShadowDirLightTextureOffset(joffset);
}

/// void setShadowFarDistance(float distance)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setShadowFarDistance
(JNIEnv *, jclass, jint ptrSelf, jfloat jdistance)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	self->setShadowFarDistance(jdistance);
}

/// void setShadowIndexBufferSize(int size)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setShadowIndexBufferSize
(JNIEnv *, jclass, jint ptrSelf, jint jsize)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	self->setShadowIndexBufferSize(jsize);
}

/// void setShadowTextureCasterMaterial(String name)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setShadowTextureCasterMaterial
(JNIEnv * env, jclass, jint ptrSelf, jstring jname)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);

	Ogre::String name;
	CopyJString(env, jname, name);

	self->setShadowTextureCasterMaterial(name);
}

/// void setShadowTextureCount(short count)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setShadowTextureCount
(JNIEnv *, jclass, jint ptrSelf, jshort jcount)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	self->setShadowTextureCount(jcount);
}


/// void setShadowTextureFadeEnd(float fadeEnd)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setShadowTextureFadeEnd
(JNIEnv *, jclass, jint ptrSelf, jfloat jfadeEnd)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	self->setShadowTextureFadeStart(jfadeEnd);
}

/// void setShadowTextureFadeStart(float fadeStart)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setShadowTextureFadeStart
(JNIEnv *, jclass, jint ptrSelf, jfloat jfadeStart)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	self->setShadowTextureFadeStart(jfadeStart);
}

/// void setShadowTextureReceiverMaterial(String name)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setShadowTextureReceiverMaterial
(JNIEnv * env, jclass, jint ptrSelf, jstring jname)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);

	Ogre::String name;
	CopyJString(env, jname, name);
	self->setShadowTextureReceiverMaterial(name);
}

/// void setShadowTextureSelfShadow(boolean selfShadow)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setShadowTextureSelfShadow
(JNIEnv *, jclass, jint ptrSelf, jboolean jselfShadow)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	bool selfShadow = (jselfShadow == JNI_TRUE) ? true : false;
	self->setShadowTextureSelfShadow(selfShadow);
}

/// void SceneManager.setShadowTextureSize(short size)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setShadowTextureSize
(JNIEnv *, jclass, jint ptrSelf, jshort jsize)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	self->setShadowTextureSize(jsize);
}

/// void SceneManager.setShadowUseInfiniteFarPlane(boolean enable)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setShadowUseInfiniteFarPlane
(JNIEnv *, jclass, jint ptrSelf, jboolean jenable)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);

	bool enable = (jenable == JNI_TRUE) ? true : false;
	self->setShadowUseInfiniteFarPlane(enable);
}

/// void SceneManager.setShowDebugShadows(boolean debug)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setShowDebugShadows
(JNIEnv *, jclass, jint ptrSelf, jboolean jdebug)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);

	bool debug = (jdebug == JNI_TRUE) ? true : false;
	self->setShowDebugShadows(debug);
}

/// void setSkyPlane(boolean enable, Plane plane, String materialName,
///            float scale, float tiling, boolean drawFirst, float bow,
///            int xsegments, int ysegments, String groupName)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setSkyPlane
(JNIEnv * env, jclass, jint ptrSelf, jboolean jenable, jobject, jstring, jfloat, jfloat, jboolean, jfloat, jint, jint, jstring)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	//self->setSkyPlane(enable, plane, materialName, jscale, jtiling, drawFirst, jbow, jxsegments, jysegments, groupName);
}


/// void setSpecialCaseRenderQueueMode(int mode)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setSpecialCaseRenderQueueMode
(JNIEnv *, jclass, jint ptrSelf, jint jmode)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::SceneManager::SpecialCaseRenderQueueMode mode = static_cast<Ogre::SceneManager::SpecialCaseRenderQueueMode>(jmode);
	self->setSpecialCaseRenderQueueMode(mode);
}

/// void setWorldGeometry(String filename)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setWorldGeometry
(JNIEnv * env, jclass, jint ptrSelf, jstring jfilename)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::String filename;
	CopyJString(env, jfilename, filename);
	self->setWorldGeometry(filename);
}

/// void setWorldGeometryRenderQueue(int qid)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_setWorldGeometryRenderQueue
(JNIEnv *, jclass, jint ptrSelf, jint jQid)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	Ogre::RenderQueueGroupID qid = static_cast<Ogre::RenderQueueGroupID>(jQid);
	self->setWorldGeometryRenderQueue(qid);
}

/// void showBoundingBoxes(boolean show)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneManager_showBoundingBoxes
(JNIEnv *, jclass, jint ptrSelf, jboolean bShow)
{
	Ogre::SceneManager * self = GET_PTR(Ogre::SceneManager, ptrSelf);
	bool show = (bShow == JNI_TRUE) ? true : false;
	self->showBoundingBoxes(show);
}
