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
/// @file Ogre4JLight.cpp
/// Implementation of the JNI bindings for the usage of the Ogre::Light class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     09.05.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_Light.h>


///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jstring JNICALL Java_org_ogre4j_Light_getName
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    return env->NewStringUTF( pLight->getName().c_str() );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jstring JNICALL Java_org_ogre4j_Light_getlightType
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Light * light = GET_PTR(Ogre::Light, pInstance);
    Ogre::String sType = light->getMovableType();
    return env->NewStringUTF(sType.c_str());
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Light__1notifyCurrentCamera
(JNIEnv *env, jclass that, jint pInstance, jint pCam)
{
    Ogre::Light * light = GET_PTR(Ogre::Light, pInstance);
    Ogre::Camera * camera = GET_PTR(Ogre::Camera, pCam);
    light->_notifyCurrentCamera(camera);
}
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Light_getBoundingBox
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Light * light = GET_PTR(Ogre::Light, pInstance);
    Ogre::AxisAlignedBox box = light->getBoundingBox();
    RETURN_PTR(&box);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Light_getBoundingRadius
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Light * light = GET_PTR(Ogre::Light, pInstance);
    return light->getBoundingRadius();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Light_setType
  (JNIEnv *env, jclass that, jint pInstance, jint jiType)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    pLight->setType( static_cast<Ogre::Light::LightTypes>(jiType) );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Light_getType
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    return pLight->getType();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Light_setDiffuseColour
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfR, jfloat jfG, jfloat jfB)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    pLight->setDiffuseColour( jfR, jfG, jfB );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Light_getDiffuseColour
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    Ogre::ColourValue colour = pLight->getDiffuseColour();
    RETURN_COLOUR_VALUE(colour);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Light_setSpecularColour
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfR, jfloat jfG, jfloat jfB)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    pLight->setSpecularColour( jfR, jfG, jfB );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Light_getSpecularColour
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    Ogre::ColourValue colour = pLight->getSpecularColour();
    RETURN_COLOUR_VALUE(colour);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Light_setAttenuation
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfRange, jfloat jfConstant, jfloat jfLinear, jfloat jfQuadric)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    pLight->setAttenuation( jfRange, jfConstant, jfLinear, jfQuadric );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Light_getAttenuationRange
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    return pLight->getAttenuationRange();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Light_getAttenuationConstant
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    return pLight->getAttenuationConstant();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Light_getAttenuationLinear
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    return pLight->getAttenuationLinear();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Light_getAttenuationQuadric
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    return pLight->getAttenuationQuadric();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Light_setPosition
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfX, jfloat jfY, jfloat jfZ)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    pLight->setPosition( jfX, jfY, jfZ );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Light_getPosition
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    Ogre::Vector3 vector = pLight->getPosition();
    RETURN_VECTOR3F(vector);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Light_setDirection
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfX, jfloat jfY, jfloat jfZ)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    pLight->setDirection( jfX, jfY, jfZ );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Light_getDirection
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    Ogre::Vector3 vector = pLight->getDirection();
    RETURN_VECTOR3F(vector);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Light_setSpotlightRange
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfX, jfloat jfY, jfloat jfZ)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    pLight->setSpotlightRange( Ogre::Radian(jfX),
                               Ogre::Radian(jfY),
                               jfZ );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Light_getSpotlightInnerAngle
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    return pLight->getSpotlightInnerAngle().valueRadians();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Light_getSpotlightOuterAngle
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    return pLight->getSpotlightOuterAngle().valueRadians();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Light_getSpotlightFalloff
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    return pLight->getSpotlightFalloff();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Light_getDerivedPosition
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    Ogre::Vector3 vecotr = pLight->getDerivedPosition();
    RETURN_VECTOR3F(vecotr);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Light_getDerivedDirection
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Light * pLight = GET_PTR(Ogre::Light, pInstance);
    Ogre::Vector3 vecotr = pLight->getDerivedDirection();
    RETURN_VECTOR3F(vecotr);
}

