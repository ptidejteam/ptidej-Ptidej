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
/// @file Ogre4JMovableObject.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::MovableObject class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     08.05.2005
///
/// $Revision: 1.3 $
/// $Date: 2005/08/18 23:49:35 $
/// $Author: earl3982 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_MovableObject.h>



//JNIEXPORT jstring JNICALL Java_org_ogre4j_MovableObject_getType
//(JNIEnv * env, jclass, jint ptrSelf)
//{
//	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);
//	try {
//		Ogre::Entity * ent = dynamic_cast<Ogre::Entity*>(self);
//		if(ent == NULL) {
//			std::cout << "YES" << std::endl;
//		} else {
//			std::cout << "NO " << std::endl;
//		} 
//	} catch(std::bad_cast) {
//		std::cout << "badcast" << std::endl;
//	} catch(std::__non_rtti_object ) {
//		std::cout << "non" << std::endl;
//	} catch(std::bad_typeid) {
//			std::cout << "badtypeid" << std::endl;
//	
//	} catch(std::exception &e) {
//		std::cout << "eeee" << std::endl;
//	}catch(...) {
//		std::cout << "oh noes " << std::endl;
//	}
//
//	std::cout << typeid(self).name() << std::endl;
//	return env->NewStringUTF(typeid(self).name());
//}


/// void MovableObject._notifyAttached(boolean isTagPoint)
JNIEXPORT void JNICALL Java_org_ogre4j_MovableObject__1notifyAttached
(JNIEnv *, jclass, jint ptrSelf, jint ptrParent, jboolean jIsTagPoint)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);
	Ogre::Node * parent = GET_PTR(Ogre::Node, ptrParent);
	bool isTagPoint = (jIsTagPoint == JNI_TRUE) ? JNI_TRUE : JNI_FALSE;
	self->_notifyAttached(parent, isTagPoint);
}

/// void MovableObject.addQueryFlags(long flags)
JNIEXPORT void JNICALL Java_org_ogre4j_MovableObject_addQueryFlags
(JNIEnv *, jclass, jint ptrSelf, jlong jFlags)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);
	self->addQueryFlags(jFlags);
}

/// boolean MovableObject.getCastShadows()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_MovableObject_getCastShadows
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);
	bool ret = self->getCastShadows();
	return (ret) ? JNI_TRUE : JNI_FALSE;
}

/// AxisAlignedBox MovableObject.getDarkCapBounds(Light light, float dirLightExtrusionDist)
JNIEXPORT jint JNICALL Java_org_ogre4j_MovableObject_getDarkCapBounds
(JNIEnv *, jclass, jint ptrSelf, jint ptrLight, jfloat jDirLightExtrusionDist)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);
	const Ogre::Light &light = GET_REF(const Ogre::Light, ptrLight);

	const Ogre::AxisAlignedBox &ret = self->getDarkCapBounds(light, jDirLightExtrusionDist);
	RETURN_PTR(&ret);
}


/// AxisAlignedBox MovableObject.getLightCapBounds()
JNIEXPORT jint JNICALL Java_org_ogre4j_MovableObject_getLightCapBounds
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);

	const Ogre::AxisAlignedBox &ret = self->getLightCapBounds();
	RETURN_PTR(&ret);
}

/// Node MovableObject.getParentNode()
JNIEXPORT jint JNICALL Java_org_ogre4j_MovableObject_getParentNode
(JNIEnv *env, jclass, jint ptrSelf)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);

	Ogre::Node * ret = self->getParentNode();
	RETURN_PTR(ret);
}


/// SceneNode MovableObject.getParentSceneNode()
JNIEXPORT jint JNICALL Java_org_ogre4j_MovableObject_getParentSceneNode
(JNIEnv *env, jclass, jint ptrSelf)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);

	Ogre::SceneNode *sceneNode = self->getParentSceneNode();
	RETURN_PTR(sceneNode);
}


/// float MovableObject.getPointExtrusionDistance(Light l)
JNIEXPORT jfloat JNICALL Java_org_ogre4j_MovableObject_getPointExtrusionDistance
(JNIEnv *, jclass, jint ptrSelf, jint ptrLight)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);
	Ogre::Light * light = GET_PTR(Ogre::Light, ptrLight);

	return self->getPointExtrusionDistance(light);
}

/// long MovableObject.getQueryFlags()
JNIEXPORT jlong JNICALL Java_org_ogre4j_MovableObject_getQueryFlags
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);
	return self->getQueryFlags();
}

/// int MovableObject.getRenderQueueGroup()
JNIEXPORT jint JNICALL Java_org_ogre4j_MovableObject_getRenderQueueGroup
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);

	Ogre::RenderQueueGroupID id = self->getRenderQueueGroup();
	jint ret = static_cast<jint>(id);
	return ret;
}

/// AxisAlignedBox MovableObject.getWorldBoundingBox(boolean derive)
JNIEXPORT jint JNICALL Java_org_ogre4j_MovableObject_getWorldBoundingBox
(JNIEnv *, jclass, jint ptrSelf, jboolean jDerive)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);

	bool derive = (jDerive == JNI_TRUE) ? true : false;
	const Ogre::AxisAlignedBox &ret = self->getWorldBoundingBox(derive);
	RETURN_PTR(&ret);
}

/// Sphere MovableObject.getWorldBoundingSphere(boolean derive)
JNIEXPORT jint JNICALL Java_org_ogre4j_MovableObject_getWorldBoundingSphere
(JNIEnv *, jclass, jint ptrSelf, jboolean jDerive)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);

	bool derive = (jDerive == JNI_TRUE) ? JNI_TRUE : JNI_FALSE;
	const Ogre::Sphere &ret = self->getWorldBoundingSphere(derive);
	RETURN_PTR(&ret);
}

/// boolean MovableObject.isAttached()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_MovableObject_isAttached
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);

	bool ret = self->isAttached();
	return (ret == true) ? JNI_TRUE : JNI_FALSE;
}

/// boolean MovableObject.isInScene()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_MovableObject_isInScene
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);

	bool ret = self->isInScene();
	return (ret == true) ? JNI_TRUE : JNI_FALSE;
}

/// boolean MovableObject.isVisible()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_MovableObject_isVisible
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);

	bool ret = self->isVisible();
	return (ret == true) ? JNI_TRUE : JNI_FALSE;
}

/// void MovableObject.removeQueryFlags(long flags)
JNIEXPORT void JNICALL Java_org_ogre4j_MovableObject_removeQueryFlags
(JNIEnv *, jclass, jint ptrSelf, jlong jFlags)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);
	self->removeQueryFlags(jFlags);
}

/// void MovableObject.setCastShadows(boolean enabled)
JNIEXPORT void JNICALL Java_org_ogre4j_MovableObject_setCastShadows
(JNIEnv *, jclass, jint ptrSelf, jboolean jEnabled)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);

	bool enabled = (jEnabled == JNI_TRUE) ? true : false;
	self->setCastShadows(enabled);
}

/// void MovableObject.setQueryFlags(long flags)
JNIEXPORT void JNICALL Java_org_ogre4j_MovableObject_setQueryFlags
(JNIEnv *, jclass, jint ptrSelf, jlong jFlags)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);
	self->setQueryFlags(jFlags);
}

/// void MovableObject.setRenderQueueGroup(int queueID)
JNIEXPORT void JNICALL Java_org_ogre4j_MovableObject_setRenderQueueGroup
(JNIEnv *, jclass, jint ptrSelf, jint jQueueID)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);

	Ogre::RenderQueueGroupID id = static_cast<Ogre::RenderQueueGroupID>(jQueueID);
	self->setRenderQueueGroup(id);
}

/// void MovableObject.setVisible(boolean visible)
JNIEXPORT void JNICALL Java_org_ogre4j_MovableObject_setVisible
(JNIEnv *, jclass, jint ptrSelf, jboolean jVisible)
{
	Ogre::MovableObject * self = GET_PTR(Ogre::MovableObject, ptrSelf);
	bool visible = (jVisible == JNI_TRUE) ? true : false;
	self->setVisible(visible);
}


