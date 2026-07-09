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
/// @file Ogre4JSceneNode.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::SceneNode class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     07.05.2005
///
/// $Revision: 1.3 $
/// $Date: 2005/08/25 14:40:14 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_SceneNode.h>

using namespace Ogre4JHelper;

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_SceneNode_dispose
(JNIEnv *, jclass, jint pInstance)
{
	Ogre::SceneNode * pSceneNode = GET_PTR(Ogre::SceneNode, pInstance);
	delete pSceneNode;
}

/// void attachObject(MovableObject obj)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneNode_attachObject
  (JNIEnv *, jclass, jint ptrSelf, jint ptrObj)
{
	Ogre::SceneNode * self = GET_PTR(Ogre::SceneNode, ptrSelf);
	Ogre::MovableObject * obj = GET_PTR(Ogre::MovableObject, ptrObj);
	self->attachObject(obj);
}

/// SceneNode createChildSceneNode(Vector3 translate, Quaternion rotate)
JNIEXPORT jint JNICALL Java_org_ogre4j_SceneNode_createChildSceneNode__IFFFFFFF
(JNIEnv *, jclass, jint ptrSelf, jfloat jtx, jfloat jty, jfloat jtz, jfloat jrw, jfloat jrx, jfloat jry, jfloat jrz)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	Ogre::Vector3 translate = Ogre::Vector3(jtx, jty, jtz);
	Ogre::Quaternion rotate =  Ogre::Quaternion(jrw, jrx, jry, jrz);
	Ogre::Node * ret = self->createChild(translate, rotate);
	RETURN_PTR(ret);
}

/// SceneNode createChildSceneNode(String name, Vector3 translate, Quaternion rotate)
JNIEXPORT jint JNICALL Java_org_ogre4j_SceneNode_createChildSceneNode__ILjava_lang_String_2FFFFFFF
(JNIEnv * env, jclass, jint ptrSelf, jstring jname, jfloat jtx, jfloat jty, jfloat jtz, jfloat jrw, jfloat jrx, jfloat jry, jfloat jrz)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	Ogre::String name;
	CopyJString(env, jname, name);
	Ogre::Vector3 translate = Ogre::Vector3(jtx, jty, jtz);
	Ogre::Quaternion rotate =  Ogre::Quaternion(jrw, jrx, jry, jrz);
	Ogre::Node * ret = self->createChild(name, translate, rotate);
	RETURN_PTR(ret);
}

/// void detachAllObjects()
JNIEXPORT void JNICALL Java_org_ogre4j_SceneNode_detachAllObjects
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::SceneNode * self = GET_PTR(Ogre::SceneNode, ptrSelf);
	self->detachAllObjects();
}

/// void SceneNode.detachObject(MovableObject obj)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneNode_detachObject__II
(JNIEnv *, jclass, jint ptrSelf, jint ptrObj)
{
	Ogre::SceneNode * self = GET_PTR(Ogre::SceneNode, ptrSelf);
	Ogre::MovableObject * obj = GET_PTR(Ogre::MovableObject, ptrObj);
	self->detachObject(obj);
}

/// MovableObject getAttachedObject(short index)
JNIEXPORT jint JNICALL Java_org_ogre4j_SceneNode_detachObject__IS
(JNIEnv *, jclass, jint ptrSelf, jshort jindex)
{
	Ogre::SceneNode * self = GET_PTR(Ogre::SceneNode, ptrSelf);
	Ogre::MovableObject * ret = self->detachObject(jindex);
	RETURN_PTR(ret);
}

/// SceneNode detachObject(String name)
JNIEXPORT jint JNICALL Java_org_ogre4j_SceneNode_detachObject__ILjava_lang_String_2
(JNIEnv * env, jclass, jint ptrSelf, jstring jname)
{
	Ogre::SceneNode * self = GET_PTR(Ogre::SceneNode, ptrSelf);
	Ogre::String name;
	CopyJString(env, jname, name);
	Ogre::MovableObject *ret = self->detachObject(name);
	RETURN_PTR(ret);
}


///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_SceneNode_getAttachedObject
(JNIEnv *env, jclass, jint pInstance, jstring jsName)
{
	Ogre::SceneNode * pSceneNode = GET_PTR(Ogre::SceneNode, pInstance);
	Ogre::String sName;
	Ogre4JHelper::CopyJString( env, jsName, sName );
	try
	{
		Ogre::MovableObject * pMovable = pSceneNode->getAttachedObject( sName );
		RETURN_PTR(pMovable);
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
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_SceneNode__1getWorldAABB
(JNIEnv *env, jclass, jint pInstance)
{
	Ogre::SceneNode * pSceneNode = GET_PTR(Ogre::SceneNode, pInstance);
	const Ogre::AxisAlignedBox &box = pSceneNode->_getWorldAABB();
	RETURN_PTR(&box);
}

/// boolean getShowBoundingBox()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_SceneNode_getShowBoundingBox
(JNIEnv *, jclass, jint ptrSelf) 
{
	Ogre::SceneNode * self = GET_PTR(Ogre::SceneNode, ptrSelf);
	bool ret = self->getShowBoundingBox();
	return (ret == JNI_TRUE) ? true : false;
}

/// boolean isInSceneGraph()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_SceneNode_isInSceneGraph
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::SceneNode * self = GET_PTR(Ogre::SceneNode, ptrSelf);
	jboolean ret = self->isInSceneGraph();
	return (ret == JNI_TRUE) ? true : false;
}


JNIEXPORT void JNICALL Java_org_ogre4j_SceneNode_lookAt
(JNIEnv *, jclass, jint, jfloat, jfloat, jfloat, jint, jfloat, jfloat, jfloat);


/// short numAttachedObjects()
JNIEXPORT jshort JNICALL Java_org_ogre4j_SceneNode_numAttachedObjects
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::SceneNode * self = GET_PTR(Ogre::SceneNode, ptrSelf);
	return self->numAttachedObjects();
}

/// void removeAndDestroyAllChildren()
JNIEXPORT void JNICALL Java_org_ogre4j_SceneNode_removeAndDestroyAllChildren
  (JNIEnv *env, jclass, jint pInstance)
{
    try
    {
        Ogre::SceneNode * pSceneNode = GET_PTR(Ogre::SceneNode, pInstance);
        pSceneNode->removeAndDestroyAllChildren();
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
}

/// void removeAndDestroyChild(short index)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneNode_removeAndDestroyChild__IS
  (JNIEnv *env, jclass, jint pInstance, jshort index)
{
    try
    {
        Ogre::SceneNode * pSceneNode = GET_PTR(Ogre::SceneNode, pInstance);
        pSceneNode->removeAndDestroyChild(index);
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
}

/// void removeAndDestroyChild(String name)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneNode_removeAndDestroyChild__ILjava_lang_String_2
  (JNIEnv *env, jclass, jint pInstance, jstring jName)
{
    try
    {
        Ogre::SceneNode * pSceneNode = GET_PTR(Ogre::SceneNode, pInstance);
        Ogre::String sName;
        Ogre4JHelper::CopyJString(env, jName, sName);
        pSceneNode->removeAndDestroyChild(sName);
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
}

/// void setDirection(float x, float y, float z)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneNode_setDirection
  (JNIEnv *env, jclass, jint pInstance, jfloat jfX, jfloat jfY, jfloat jfZ)
{
    Ogre::SceneNode * pSceneNode = GET_PTR(Ogre::SceneNode, pInstance);
    pSceneNode->setDirection(jfX, jfY, jfZ);
}

/// void setFixedYawAxis(boolean useFixed)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneNode_setFixedYawAxis__IZ
  (JNIEnv *env, jclass that, jint pInstance, jboolean useFixed)
{
    Ogre::SceneNode * pSceneNode = GET_PTR(Ogre::SceneNode, pInstance);
    pSceneNode->setFixedYawAxis(useFixed?true:false);
}

/// void setFixedYawAxis(boolean useFixed, float x, float y, float z)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneNode_setFixedYawAxis__IZFFF
  (JNIEnv *env, jclass that, jint pInstance, jboolean useFixed, jfloat x, jfloat y, jfloat z)
{
    Ogre::SceneNode * pSceneNode = GET_PTR(Ogre::SceneNode, pInstance);
    pSceneNode->setFixedYawAxis(useFixed?true:false, Ogre::Vector3(x,y,z));
}

/// void setVisible(boolean visible, boolean cascade)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneNode_setVisible
(JNIEnv *, jclass, jint ptrSelf, jboolean jvisible, jboolean jcascade)
{
	Ogre::SceneNode * self = GET_PTR(Ogre::SceneNode, ptrSelf);
	bool visible = (jvisible == JNI_TRUE) ? true : false;
	bool cascade = (jcascade == JNI_TRUE) ? true : false;
	self->setVisible(visible, cascade);
}

/// void showBoundingBox(boolean bShow)
JNIEXPORT void JNICALL Java_org_ogre4j_SceneNode_showBoundingBox
(JNIEnv *, jclass, jint ptrSelf, jboolean jbShow)
{
	Ogre::SceneNode * self = GET_PTR(Ogre::SceneNode, ptrSelf);
	self->showBoundingBox(jbShow?true:false);
}
