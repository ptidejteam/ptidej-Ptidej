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
/// @file Ogr4JAnimation.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::Animation class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     08.05.2005
///
/// $Revision: 1.2 $
/// $Date: 2005/08/18 23:49:35 $
/// $Author: earl3982 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_Animation.h>



#define JNIEXPORT
#define JNICALL
typedef struct JNIEnv {} JNIEnv;



/// static void setDefaultRotationInterpolationMode(int im)
JNIEXPORT void JNICALL Java_org_ogre4j_Animation_setDefaultRotationInterpolationModeImpl
  (JNIEnv *, jclass, jint jim)
{
	Ogre::Animation::RotationInterpolationMode im = static_cast<Ogre::Animation::RotationInterpolationMode>(jim);
	Ogre::Animation::setDefaultRotationInterpolationMode(im);
}

/// AnimationTrack createTrack(short handle)
JNIEXPORT jint JNICALL Java_org_ogre4j_Animation_createTrack__IS
  (JNIEnv *env, jclass that, jint pInstance, jshort jsHandle)
{
    Ogre::Animation * pAnim = GET_PTR(Ogre::Animation, pInstance);
    Ogre::AnimationTrack *pAnimTrack = pAnim->createTrack( jsHandle );
    RETURN_PTR(pAnimTrack);
}

/// AnimationTrack createTrack(short handle, Node node)
JNIEXPORT jint JNICALL Java_org_ogre4j_Animation_createTrack__ISI
  (JNIEnv *env, jclass that, jint pInstance, jshort jsHandle, jint pNode)
{
    Ogre::Animation * pAnim = GET_PTR(Ogre::Animation, pInstance);
    Ogre::AnimationTrack *pAnimTrack = pAnim->createTrack( jsHandle, GET_PTR(Ogre::Node,pNode) );
    RETURN_PTR(pAnimTrack);
}

/// void destroyAllTracks()
JNIEXPORT void JNICALL Java_org_ogre4j_Animation_destroyAllTracks
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Animation * pAnim = GET_PTR(Ogre::Animation, pInstance);
    pAnim->destroyAllTracks();
}

/// void destroyTrack(short handle)
JNIEXPORT void JNICALL Java_org_ogre4j_Animation_destroyTrack
  (JNIEnv *env, jclass that, jint pInstance, jshort jsHandle)
{
    Ogre::Animation * pAnim = GET_PTR(Ogre::Animation, pInstance);
    pAnim->destroyTrack( jsHandle );
}

/// int getInterpolationMode()
JNIEXPORT jint JNICALL Java_org_ogre4j_Animation_getInterpolationMode
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Animation * pAnim = GET_PTR(Ogre::Animation, pInstance);
    return pAnim->getInterpolationMode();
}

/// float getLength()
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Animation_getLength
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Animation * pAnim = GET_PTR(Ogre::Animation, pInstance);
    return pAnim->getLength();
}

/// String getName()
JNIEXPORT jstring JNICALL Java_org_ogre4j_Animation_getName
  (JNIEnv *env, jclass that, jint pInstance )
{
    Ogre::Animation * pAnim = GET_PTR(Ogre::Animation, pInstance);
    return env->NewStringUTF( pAnim->getName().c_str() );
}

/// int getNumTracks()
JNIEXPORT jint JNICALL Java_org_ogre4j_Animation_getNumTracks
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Animation * pAnim = GET_PTR(Ogre::Animation, pInstance);
    return pAnim->getNumTracks();
}

/// AnimationTrack getTrack(short handle)
JNIEXPORT jint JNICALL Java_org_ogre4j_Animation_getTrack
  (JNIEnv *env, jclass that, jint pInstance, jshort jsHandle)
{
    Ogre::Animation * pAnim = GET_PTR(Ogre::Animation, pInstance);
    Ogre::AnimationTrack *pAnimTrack = pAnim->getTrack( jsHandle );
    RETURN_PTR(pAnimTrack);
}

/// void optimise()

/// void setInterpolationMode(int interpolationMode)
JNIEXPORT void JNICALL Java_org_ogre4j_Animation_setInterpolationMode
  (JNIEnv *env, jclass that, jint pInstance, jint jiMode)
{
    Ogre::Animation * pAnim = GET_PTR(Ogre::Animation, pInstance);
    pAnim->setInterpolationMode( static_cast<Ogre::Animation::InterpolationMode>(jiMode) );
}

/// static void setDefaultRotationInterpolationMode(int im)
JNIEXPORT void JNICALL Java_org_ogre4j_Animation_setDefaultRotationInterpolationModeImpl
  (JNIEnv *, jclass, jint jim)
{
	Ogre::Animation::RotationInterpolationMode im = static_cast<Ogre::Animation::RotationInterpolationMode>(jim);
	Ogre::Animation::setDefaultRotationInterpolationMode(im);
}

/// static void setDefaultInterpolationMode(int im)
JNIEXPORT void JNICALL Java_org_ogre4j_Animation_setDefaultInterpolationModeImpl
  (JNIEnv *, jclass, jint jim)
{
	Ogre::Animation::InterpolationMode im = static_cast<Ogre::Animation::InterpolationMode>(jim);
	Ogre::Animation::setDefaultInterpolationMode(im);
}


