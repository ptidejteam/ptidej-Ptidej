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
/// @file Ogre4JPlane.cpp
/// Implementation of the JNI bindings for the usage of the Ogre::Plane class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     27.06.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_Plane.h>

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Plane_createInstance__
  (JNIEnv *, jclass)
{
    Ogre::Plane * pPlane = new Ogre::Plane();
    RETURN_PTR(pPlane);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Plane_createInstance__FFFF
  (JNIEnv *, jclass, jfloat rkNormalX, jfloat rkNormalY, jfloat rkNormalZ, jfloat fConstant)
{
    Ogre::Plane * pPlane = new Ogre::Plane(Ogre::Vector3(rkNormalX, rkNormalY, rkNormalZ), fConstant);
    RETURN_PTR(pPlane);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Plane_createInstance__FFFFFF
  (JNIEnv *env, jclass that, 
  jfloat rkNormalX, jfloat rkNormalY, jfloat rkNormalZ, 
  jfloat rkPointX,  jfloat rkPointY,  jfloat rkPointZ)
{
    Ogre::Plane * pPlane = new Ogre::Plane( Ogre::Vector3(rkNormalX, rkNormalY, rkNormalZ), 
                                            Ogre::Vector3(rkPointX,  rkPointY,  rkPointZ) );
    RETURN_PTR(pPlane);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Plane_createInstance__FFFFFFFFF
  (JNIEnv *, jclass, 
  jfloat rkPoint0X, jfloat rkPoint0Y, jfloat rkPoint0Z,
  jfloat rkPoint1X, jfloat rkPoint1Y, jfloat rkPoint1Z,
  jfloat rkPoint2X, jfloat rkPoint2Y, jfloat rkPoint2Z)
{
    Ogre::Plane * pPlane = new Ogre::Plane( Ogre::Vector3(rkPoint0X,  rkPoint0Y,  rkPoint0Z),
                                            Ogre::Vector3(rkPoint1X,  rkPoint1Y,  rkPoint1Z),
                                            Ogre::Vector3(rkPoint2X,  rkPoint2Y,  rkPoint2Z));
    RETURN_PTR(pPlane);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Plane_dispose
  (JNIEnv *, jclass, jint pInstance)
{
    Ogre::Plane * pPlane = GET_PTR(Ogre::Plane, pInstance);
    delete pPlane;
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Plane_getSide
  (JNIEnv *, jclass, jint pInstance, jfloat rkPointX,  jfloat rkPointY,  jfloat rkPointZ)
{
    Ogre::Plane * pPlane = GET_PTR(Ogre::Plane, pInstance);
    return pPlane->getSide(Ogre::Vector3(rkPointX,  rkPointY,  rkPointZ));
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Plane_getDistance
  (JNIEnv *env, jclass that, jint pInstance, jfloat rkPointX,  jfloat rkPointY,  jfloat rkPointZ)
{
    Ogre::Plane * pPlane = GET_PTR(Ogre::Plane, pInstance);
    return pPlane->getDistance(Ogre::Vector3(rkPointX,  rkPointY,  rkPointZ));
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Plane_redefine
  (JNIEnv *env, jclass that, jint pInstance, 
  jfloat rkPoint0X, jfloat rkPoint0Y, jfloat rkPoint0Z,
  jfloat rkPoint1X, jfloat rkPoint1Y, jfloat rkPoint1Z,
  jfloat rkPoint2X, jfloat rkPoint2Y, jfloat rkPoint2Z)
{
    Ogre::Plane * pPlane = GET_PTR(Ogre::Plane, pInstance);
    pPlane->redefine(Ogre::Vector3(rkPoint0X,  rkPoint0Y,  rkPoint0Z),
                     Ogre::Vector3(rkPoint1X,  rkPoint1Y,  rkPoint1Z),
                     Ogre::Vector3(rkPoint2X,  rkPoint2Y,  rkPoint2Z));
}