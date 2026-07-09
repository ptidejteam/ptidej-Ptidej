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
/// @file Ogre4JNode.cpp
/// Implementation of the JNI bindings for the usage of the Ogre::Node class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     07.05.2005
///
/// $Revision: 1.3 $
/// $Date: 2005/08/18 23:49:35 $
/// $Author: earl3982 $
///////////////////////////////////////////////////////////////////////////////

#include <Ogre4J.h>
#include <org_ogre4j_Node.h>

using namespace Ogre4JHelper;

JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node__1getDerivedOrientation
(JNIEnv *, jclass, jint)
{
	return 0;
}

/// void addChild(Node child)
JNIEXPORT void JNICALL Java_org_ogre4j_Node_addChild
(JNIEnv *, jclass, jint ptrSelf, jint ptrChild)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	Ogre::Node * node = GET_PTR(Ogre::Node, ptrChild);
	self->addChild(node);
}

/// void cancelUpdate(Node child)
JNIEXPORT void JNICALL Java_org_ogre4j_Node_cancelUpdate
(JNIEnv *, jclass, jint ptrSelf, jint ptrChild)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	Ogre::Node * child = GET_PTR(Ogre::Node, ptrChild);
	self->cancelUpdate(child);
}

/// Node createChild(Vector3 translate, Quaternion rotate)
JNIEXPORT jint JNICALL Java_org_ogre4j_Node_createChild__IFFFFFFF
(JNIEnv *, jclass, jint ptrSelf, jfloat jtx, jfloat jty, jfloat jtz, jfloat jrw, jfloat jrx, jfloat jry, jfloat jrz)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	Ogre::Vector3 translate = Ogre::Vector3(jtx, jty, jtz);
	Ogre::Quaternion rotate =  Ogre::Quaternion(jrw, jrx, jry, jrz);
	Ogre::Node * ret = self->createChild(translate, rotate);
	RETURN_PTR(ret);
}


/// Node createChild(String name, Vector3 translate, Quaternion rotate)
JNIEXPORT jint JNICALL Java_org_ogre4j_Node_createChild__ILjava_lang_String_2FFFFFFF
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

/// boolean getCastsShadows()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_Node_getCastsShadows
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	bool ret = self->getCastsShadows();
	return (ret) ? JNI_TRUE : JNI_FALSE;
}

/// boolean getInheritScale()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_Node_getInheritScale
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	bool ret = self->getInheritScale();
	return (ret) ? JNI_TRUE : JNI_FALSE;
}

/// String getName()
JNIEXPORT jstring JNICALL Java_org_ogre4j_Node_getName
(JNIEnv * env, jclass, jint ptrSelf)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	return env->NewStringUTF(self->getName().c_str());
}


/// boolean getNormaliseNormals()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_Node_getNormaliseNormals
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	bool ret = self->getNormaliseNormals();
	return (ret) ? JNI_TRUE : JNI_FALSE;
}

/// short getNumWorldTransforms()
JNIEXPORT jshort JNICALL Java_org_ogre4j_Node_getNumWorldTransforms
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	return self->getNumWorldTransforms();
}

/// Node getParent()
JNIEXPORT jint JNICALL Java_org_ogre4j_Node_getParent
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	Ogre::Node * ret = self->getParent();
	RETURN_PTR(ret);
}

/// float getSquaredViewDepth(Camera cam)
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Node_getSquaredViewDepth
(JNIEnv *env, jclass, jint ptrSelf, jint ptrCam)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	Ogre::Camera * cam = GET_PTR(Ogre::Camera, ptrCam);
	return self->getSquaredViewDepth(cam);
}








JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node_getOrientation
(JNIEnv *env, jclass that, jint pInstance)
{
	Ogre::Node * pNode = GET_PTR(Ogre::Node, pInstance);
	Ogre::Quaternion quat = pNode->getOrientation();
	Ogre4JHelper::normalLogMsg(Ogre::StringConverter::toString(quat));
	RETURN_QUAT4F(quat);
}



JNIEXPORT void JNICALL Java_org_ogre4j_Node_setOrientation
(JNIEnv *env, jclass that, jint pInstance, jfloat jfW, jfloat jfX, jfloat jfY, jfloat jfZ)
{
	Ogre::Node * pNode = GET_PTR(Ogre::Node, pInstance);
	pNode->setOrientation( jfW, jfX, jfY, jfZ );
}






JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node_getPosition
(JNIEnv *env, jclass that, jint pInstance)
{
	Ogre::Node * pNode = GET_PTR(Ogre::Node, pInstance);
	Ogre::Vector3 vector = pNode->getPosition();
	RETURN_VECTOR3F(vector);
}





JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node_getScale
(JNIEnv *env, jclass that, jint pInstance)
{
	Ogre::Node * pNode = GET_PTR(Ogre::Node, pInstance);
	Ogre::Vector3 vector = pNode->getScale();
	RETURN_VECTOR3F(vector);
}



JNIEXPORT void JNICALL Java_org_ogre4j_Node_rotateByAxisAngle
(JNIEnv *env, jclass that, jint pInstance, jfloat jfX, jfloat jfY, jfloat jfZ, jfloat jfRadian)
{
	Ogre::Node * pNode = GET_PTR(Ogre::Node, pInstance);
	pNode->rotate( Ogre::Vector3( jfX, jfY, jfZ ), static_cast<Ogre::Radian>(jfRadian) );
}



JNIEXPORT void JNICALL Java_org_ogre4j_Node_rotateByQuaternion
(JNIEnv *env, jclass that, jint pInstance, jfloat jfW, jfloat jfX, jfloat jfY, jfloat jfZ)
{
	Ogre::Node * pNode = GET_PTR(Ogre::Node, pInstance);
	pNode->rotate( Ogre::Quaternion( jfW, jfX, jfY, jfZ ) );
}








JNIEXPORT jint JNICALL Java_org_ogre4j_Node_getChild__II
(JNIEnv *env, jclass that, jint pInstance, jint jiIndex)
{
	try
	{
		Ogre::Node * pNode = GET_PTR(Ogre::Node, pInstance);
		Ogre::Node * pChildNode = pNode->getChild( jiIndex );
		RETURN_PTR(pChildNode);
	}
	catch( Ogre::Exception & e )
	{
		jclass OgreException = env->FindClass( "org/ogre4j/OgreException" );
		env->ThrowNew(OgreException, e.getFullDescription().c_str());
	}
	catch(...)
	{
		jclass OgreException = env->FindClass( "org/ogre4j/OgreException" );
		env->ThrowNew(OgreException, "Unkown Exception" );
	}
	return 0;
}



JNIEXPORT jint JNICALL Java_org_ogre4j_Node_getChild__ILjava_lang_String_2
(JNIEnv *env, jclass that, jint pInstance, jstring jsName)
{
	try
	{
		Ogre::Node * pNode = GET_PTR(Ogre::Node, pInstance);
		Ogre::String sName;
		Ogre4JHelper::CopyJString( env, jsName, sName);
		Ogre::Node * pChildNode = pNode->getChild( sName );
		RETURN_PTR(pChildNode);
	}
	catch( Ogre::Exception & e )
	{
		jclass OgreException = env->FindClass( "org/ogre4j/OgreException" );
		env->ThrowNew(OgreException, e.getFullDescription().c_str());
	}
	catch(...)
	{
		jclass OgreException = env->FindClass( "org/ogre4j/OgreException" );
		env->ThrowNew(OgreException, "Unkown Exception" );
	}
	return 0;
}










JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node__getDerivedOrientation
(JNIEnv *env, jclass that, jint pInstance)
{
	Ogre::Node * pNode = GET_PTR(Ogre::Node, pInstance);
	Ogre::Quaternion quat = pNode->_getDerivedOrientation();
	RETURN_QUAT4F(quat);
}



JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node__getDerivedPosition
(JNIEnv *env, jclass that, jint pInstance)
{
	Ogre::Node * pNode = GET_PTR(Ogre::Node, pInstance);
	Ogre::Quaternion quat = pNode->_getDerivedOrientation();
	RETURN_QUAT4F(quat);
}



JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node__getDerivedScale
(JNIEnv *env, jclass that, jint pInstance)
{
	Ogre::Node * pNode = GET_PTR(Ogre::Node, pInstance);
	Ogre::Vector3 vector = pNode->_getDerivedScale();
	RETURN_VECTOR3F(vector);
}



JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node_getInitialPosition
(JNIEnv *env, jclass that, jint pInstance)
{
	Ogre::Node * pNode = GET_PTR(Ogre::Node, pInstance);
	Ogre::Vector3 vector = pNode->getInitialPosition();
	RETURN_VECTOR3F(vector);
}



JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node_getInitialOrientation
(JNIEnv *env, jclass that, jint pInstance)
{
	Ogre::Node * pNode = GET_PTR(Ogre::Node, pInstance);
	Ogre::Quaternion quat = pNode->getInitialOrientation();
	RETURN_QUAT4F(quat);
}



JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node_getWorldPosition
(JNIEnv *env, jclass that, jint pInstance)
{
	Ogre::Node * pNode = GET_PTR(Ogre::Node, pInstance);
	Ogre::Vector3 vector = pNode->getWorldPosition();
	RETURN_VECTOR3F(vector);
}



JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node_getWorldOrientation
(JNIEnv *env, jclass that, jint pInstance)
{
	Ogre::Node * pNode = GET_PTR(Ogre::Node, pInstance);
	Ogre::Quaternion quat = pNode->getWorldOrientation();
	RETURN_QUAT4F(quat);
}



JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node_getWorldTransforms
(JNIEnv *env, jclass, jint pInstance)
{
	Ogre::Node * pNode = GET_PTR(Ogre::Node, pInstance);
	Ogre::Matrix4 mat;
	pNode->getWorldTransforms(&mat);
	return Ogre4JHelper::createJavaArray(env, mat);
}

/// void needUpdate() 
JNIEXPORT void JNICALL Java_org_ogre4j_Node_needUpdate
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	self->needUpdate();
}


/// int numChildren()
JNIEXPORT jint JNICALL Java_org_ogre4j_Node_numChildren
(JNIEnv *env, jclass that, jint ptrSelf)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	return self->numChildren();
}

/// void pitch(float radians, int relativeTo)
JNIEXPORT void JNICALL Java_org_ogre4j_Node_pitch
(JNIEnv *, jclass, jint ptrSelf, jfloat jradians, jint jrelativeTo)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	Ogre::Radian degrees = static_cast<Ogre::Radian>(jradians);
	Ogre::Node::TransformSpace relativeTo = static_cast<Ogre::Node::TransformSpace>(jrelativeTo);
	self->pitch(degrees, relativeTo);
}

/// void removeAllChildren()
JNIEXPORT void JNICALL Java_org_ogre4j_Node_removeAllChildren
(JNIEnv *env, jclass, jint ptrSelf)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	self->removeAllChildren();
}

/// void removeChild(Node child)
JNIEXPORT void JNICALL Java_org_ogre4j_Node_removeChild__II
(JNIEnv *, jclass, jint ptrSelf, jint ptrChild)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	Ogre::Node * child = GET_PTR(Ogre::Node, ptrChild);
	self->removeChild(child);
}

/// Node removeChild(short index)
JNIEXPORT jint JNICALL Java_org_ogre4j_Node_removeChild__IS
(JNIEnv *, jclass, jint ptrSelf, jshort jindex)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	Ogre::Node * ret = self->removeChild(jindex);
	RETURN_PTR(ret);
}

/// Node removeChild(String name)
JNIEXPORT jint JNICALL Java_org_ogre4j_Node_removeChild__ILjava_lang_String_2
(JNIEnv * env, jclass, jint ptrSelf, jstring jname)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	Ogre::String name;
	CopyJString(env, jname, name);
	Ogre::Node * ret = self->removeChild(name);
	RETURN_PTR(ret);
}

/// void requestUpdate(Node child)
JNIEXPORT void JNICALL Java_org_ogre4j_Node_requestUpdate
(JNIEnv *, jclass, jint ptrSelf, jint ptrChild)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	Ogre::Node * child = GET_PTR(Ogre::Node, ptrChild);
	self->requestUpdate(child);
}

/// void resetOrientation()
JNIEXPORT void JNICALL Java_org_ogre4j_Node_resetOrientation
(JNIEnv *env, jclass, jint ptrSelf)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	self->resetOrientation();
}

/// void resetToInitialState()
JNIEXPORT void JNICALL Java_org_ogre4j_Node_resetToInitialState
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	self->resetToInitialState();
}

/// void roll(float radians, int relativeTo)
JNIEXPORT void JNICALL Java_org_ogre4j_Node_roll
(JNIEnv *, jclass, jint ptrSelf, jfloat jradians, jint jrelativeTo)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	Ogre::Radian degrees = static_cast<Ogre::Radian>(jradians);
	Ogre::Node::TransformSpace relativeTo = static_cast<Ogre::Node::TransformSpace>(jrelativeTo);
	self->roll(degrees, relativeTo);
}

/// void scale(float x, float y, float z) 
JNIEXPORT void JNICALL Java_org_ogre4j_Node_scale
(JNIEnv *, jclass, jint ptrSelf, jfloat jx, jfloat jy, jfloat jz)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	self->scale(jx, jy, jz);
}

/// void setInheritScale(boolean inherit)
JNIEXPORT void JNICALL Java_org_ogre4j_Node_setInheritScale
(JNIEnv *, jclass, jint ptrSelf, jboolean jinherit)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	bool inherit = (jinherit == JNI_TRUE) ? true : false;
	self->setInheritScale(inherit);
}

/// void setInitialState()
JNIEXPORT void JNICALL Java_org_ogre4j_Node_setInitialState
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	self->setInitialState();
}

/// void setPosition(float x, float y, float z)
JNIEXPORT void JNICALL Java_org_ogre4j_Node_setPosition
(JNIEnv *, jclass, jint ptrSelf, jfloat jx, jfloat jy, jfloat jz)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	self->setPosition(jx, jy, jz);
}

/// void setRenderDetailOverrideable(boolean override)
JNIEXPORT void JNICALL Java_org_ogre4j_Node_setRenderDetailOverrideable
(JNIEnv *, jclass, jint ptrSelf, jboolean joverride)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	bool override = (joverride == JNI_TRUE) ? true : false;
	self->setRenderDetailOverrideable(override);
}

/// void setScale(float x, float y, float z)
JNIEXPORT void JNICALL Java_org_ogre4j_Node_setScale
(JNIEnv *, jclass, jint ptrSelf, jfloat jx, jfloat jy, jfloat jz)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	self->scale(jx, jy, jz);
}

/// void translate(float x, float y, float z, int relativeTo)
JNIEXPORT void JNICALL Java_org_ogre4j_Node_translate
(JNIEnv *, jclass, jint ptrSelf, jfloat jx, jfloat jy, jfloat jz, jint jrelativeTo)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	Ogre::Node::TransformSpace relativeTo = static_cast<Ogre::Node::TransformSpace>(jrelativeTo);
	self->translate(jx, jy, jz, relativeTo);
}

/// boolean useIdentityProjection()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_Node_useIdentityProjection
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	bool ret = self->useIdentityProjection();
	return (ret) ? JNI_TRUE : JNI_FALSE;
}

/// boolean useIdentityView()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_Node_useIdentityView
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	bool ret = self->useIdentityView();
	return (ret) ? JNI_TRUE : JNI_FALSE;
}

/// void yaw(float radians, int relativeTo)
JNIEXPORT void JNICALL Java_org_ogre4j_Node_yaw
(JNIEnv *, jclass, jint ptrSelf, jfloat jradians, jint jrelativeTo)
{
	Ogre::Node * self = GET_PTR(Ogre::Node, ptrSelf);
	Ogre::Radian degrees = static_cast<Ogre::Radian>(jradians);
	Ogre::Node::TransformSpace relativeTo = static_cast<Ogre::Node::TransformSpace>(jrelativeTo);
	self->yaw(degrees, relativeTo);
}

