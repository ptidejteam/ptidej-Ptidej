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
/// @file Ogre4JCamera.cpp
/// Implementation of the JNI bindings for the usage of the Ogre::Camera class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     07.05.2005
///
/// $Revision: 1.3 $
/// $Date: 2005/08/24 11:10:34 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_Camera.h>

/// float[] getDerivedOrientation()
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Camera_getDerivedOrientation
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    Ogre::Quaternion orient = pCam->getDerivedOrientation();
    RETURN_QUAT4F(orient);
}

/// float[] getDerivedPosition()
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Camera_getDerivedPosition
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    Ogre::Vector3 pos = pCam->getDerivedPosition();
    RETURN_VECTOR3F(pos);
}

/// void setPosition(float x, float y, float z)
JNIEXPORT void JNICALL Java_org_ogre4j_Camera_setPosition
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfX, jfloat jfY, jfloat jfZ)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    pCam->setPosition( Ogre::Vector3( jfX, jfY, jfZ ) );
}

/// int getDetailLevel()
JNIEXPORT jint JNICALL Java_org_ogre4j_Camera_getDetailLevel
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    return pCam->getDetailLevel();
}

/// void lookAt(float x, float y, float z)
JNIEXPORT void JNICALL Java_org_ogre4j_Camera_lookAt
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfX, jfloat jfY, jfloat jfZ)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    pCam->lookAt( jfX, jfY, jfZ );
}

///  void moveRelative(Vector3 d)
JNIEXPORT void JNICALL Java_org_ogre4j_Camera_moveRelative
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfX, jfloat jfY, jfloat jfZ)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    pCam->moveRelative( Ogre::Vector3(jfX, jfY, jfZ) );
}

/// void pitch(float radians)
JNIEXPORT void JNICALL Java_org_ogre4j_Camera_pitch
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfRadian)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    pCam->pitch( static_cast<Ogre::Radian>(jfRadian) );
}

/// void roll(float radians)
JNIEXPORT void JNICALL Java_org_ogre4j_Camera_roll
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfRadian)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    pCam->roll( static_cast<Ogre::Radian>(jfRadian) );
}

///  void setAutoTracking(boolean enabled, SceneNode target, Vector3 offset)
JNIEXPORT void JNICALL Java_org_ogre4j_Camera_setAutoTracking
  (JNIEnv *, jclass, jint ptrSelf, jboolean jenabled, jint ptrTarget, jfloat joffsetX, 
  jfloat joffsetY, jfloat joffsetZ)
{
	Ogre::Camera * self = GET_PTR(Ogre::Camera, ptrSelf);
	bool enabled = (jenabled == JNI_TRUE) ? true : false;
	Ogre::SceneNode * target = GET_PTR(Ogre::SceneNode, ptrTarget);
	Ogre::Vector3 offset(joffsetX, joffsetY, joffsetZ);
	self->setAutoTracking(enabled, target, offset);
}

/// float[] getPosition()
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Camera_getPosition
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    Ogre::Vector3 pos = pCam->getPosition();
    RETURN_VECTOR3F(pos);
}


///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Camera_setDetailLevel
  (JNIEnv *env, jclass that, jint pInstance, jint jiDetailLevel)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    pCam->setDetailLevel( static_cast<Ogre::SceneDetailLevel>(jiDetailLevel) );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Camera_getDirection
  (JNIEnv *env, jclass, jint pInstance)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    Ogre::Vector3 vec = pCam->getDirection();
    RETURN_VECTOR3F(vec);
}

/// float[] getOrientation()
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Camera_getOrientation
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    Ogre::Quaternion q = pCam->getOrientation();
    RETURN_QUAT4F(q);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Camera_setDirection
  (JNIEnv *env, jclass, jint pInstance, jfloat jfX, jfloat jfY, jfloat jfZ)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    pCam->setDirection(jfX, jfY, jfZ);
}

/// void setFixedYawAxis(boolean useFixed)
JNIEXPORT void JNICALL Java_org_ogre4j_Camera_setFixedYawAxis__IZ
  (JNIEnv *env, jclass that, jint pInstance, jboolean useFixed)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    pCam->setFixedYawAxis(useFixed?true:false);
}

/// void setFixedYawAxis(boolean useFixed, float x, float y, float z)
JNIEXPORT void JNICALL Java_org_ogre4j_Camera_setFixedYawAxis__IZFFF
  (JNIEnv *env, jclass that, jint pInstance, jboolean useFixed, jfloat x, jfloat y, jfloat z)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    pCam->setFixedYawAxis(useFixed?true:false, Ogre::Vector3(x,y,z));
}

/// void setOrientation(float x, float y, float z, float w)
JNIEXPORT void JNICALL Java_org_ogre4j_Camera_setOrientation
  (JNIEnv *env, jclass that, jint pInstance, jfloat x, jfloat y, jfloat z, jfloat w)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    pCam->setOrientation(Ogre::Quaternion(w,x,y,z));
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Camera_setFarClipDistance
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfFarDist)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    pCam->setFarClipDistance( jfFarDist );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Camera_getFarClipDistance
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    return pCam->getFarClipDistance();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Camera_setNearClipDistance
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfNearDist)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    pCam->setNearClipDistance( jfNearDist );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Camera_getNearClipDistance
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    return  pCam->getNearClipDistance();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Camera_setFOVy
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfFovy)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    pCam->setFOVy( static_cast<Ogre::Radian>(jfFovy) );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Camera_yaw
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfRadian)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    pCam->yaw( static_cast<Ogre::Radian>(jfRadian) );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jstring JNICALL Java_org_ogre4j_Camera_getName
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
    return env->NewStringUTF( pCam->getName().c_str() );
}

/// int getCameraToViewportRay(flaot screenx, float screeny)
JNIEXPORT jint JNICALL Java_org_ogre4j_Camera_getCameraToViewportRay
(JNIEnv *env, jclass, jint pInstance, jfloat screenx, jfloat screeny)
{
	Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
	Ogre::Ray * pRay = new Ogre::Ray (pCam->getCameraToViewportRay( screenx, screeny ));
	RETURN_PTR(pRay);
}

/// void setAspectRatio(float ratio)
JNIEXPORT void JNICALL Java_org_ogre4j_Camera_setAspectRatio
  (JNIEnv *, jclass, jint pInstance, jfloat ratio) 
{
	Ogre::Camera * pCam = GET_PTR(Ogre::Camera, pInstance);
	pCam->setAspectRatio(ratio);
}


