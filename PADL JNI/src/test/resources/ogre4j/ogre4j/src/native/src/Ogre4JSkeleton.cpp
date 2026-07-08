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
/// @file Ogre4JSkeleton.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::Skeleton class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     07.05.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_Skeleton.h>

// TODO SkeletonAnimationBlendMode enumerator

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Skeleton_dispose
  (JNIEnv *env, jclass that, jint pInstance)
{
    try
    {
        Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
        Ogre::SkeletonPtr sptr = Ogre::SkeletonManager::getSingleton().getByName(pSkeleton->getName());
        unsigned int * pUseCount = sptr.useCountPointer();
        (*pUseCount)--;
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
JNIEXPORT jstring JNICALL Java_org_ogre4j_Skeleton_getName
(JNIEnv *env, jclass, jint pInstance){
    Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
    return env->NewStringUTF( pSkeleton->getName().c_str() );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Skeleton__1createBone__I
  (JNIEnv *env, jclass, jint pInstance)
{
    Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
	RETURN_PTR(pSkeleton->createBone( ));
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Skeleton__1createBone__IS
  (JNIEnv *env, jclass, jint pInstance, jshort handle)
{
    Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
	RETURN_PTR(pSkeleton->createBone( handle ));
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Skeleton__1createBone__ILjava_lang_String_2
  (JNIEnv *env, jclass, jint pInstance, jstring jsName)
{
	Ogre::String sName;
    Ogre4JHelper::CopyJString( env, jsName, sName );
    Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
	RETURN_PTR(pSkeleton->createBone( sName ));
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Skeleton__1createBone__ILjava_lang_String_2S
  (JNIEnv *env, jclass, jint pInstance, jstring jsName, jshort handle)
{
    Ogre::String sName;
    Ogre4JHelper::CopyJString( env, jsName, sName );
    Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
	RETURN_PTR(pSkeleton->createBone( sName, handle ));
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jshort JNICALL Java_org_ogre4j_Skeleton_getNumBones
  (JNIEnv *env, jclass, jint pInstance)
{
    Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
	return pSkeleton->getNumBones();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Skeleton_getRootBone
  (JNIEnv *env, jclass, jint pInstance)
{
    Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
	RETURN_PTR(pSkeleton->getRootBone( ));
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Skeleton_getBone__IS
  (JNIEnv *env, jclass, jint pInstance, jshort handle)
{
    Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
	RETURN_PTR(pSkeleton->getBone( handle ));
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Skeleton_getBone__ILjava_lang_String_2
  (JNIEnv *env, jclass, jint pInstance, jstring jsName)
{
    Ogre::String sName;
    Ogre4JHelper::CopyJString( env, jsName, sName );
    try
    {
        Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
	    RETURN_PTR(pSkeleton->getBone( sName ));
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
JNIEXPORT void JNICALL Java_org_ogre4j_Skeleton_setBindingPose
  (JNIEnv *env, jclass, jint pInstance)
{
    Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
	return pSkeleton->setBindingPose( );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Skeleton_reset
  (JNIEnv *env, jclass, jint pInstance)
{
    Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
	return pSkeleton->reset( );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Skeleton_createAnimation
  (JNIEnv *env, jclass, jint pInstance, jstring jsName, jfloat length)
{
    Ogre::String sName;
    Ogre4JHelper::CopyJString( env, jsName, sName );
    Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
	RETURN_PTR(pSkeleton->createAnimation( sName, length ));
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Skeleton_getAnimation__ILjava_lang_String_2
  (JNIEnv *env, jclass, jint pInstance, jstring jsName)
{
    Ogre::String sName;
    Ogre4JHelper::CopyJString( env, jsName, sName );
    Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
	RETURN_PTR(pSkeleton->getAnimation( sName ));
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Skeleton_removeAnimation
  (JNIEnv *env, jclass, jint pInstance, jstring jsName)
{
    Ogre::String sName;
    Ogre4JHelper::CopyJString( env, jsName, sName );
    Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
	pSkeleton->removeAnimation( sName );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jshort JNICALL Java_org_ogre4j_Skeleton_getNumAnimations
  (JNIEnv *env, jclass, jint pInstance)
{
    Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
	return pSkeleton->getNumAnimations();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Skeleton_getAnimation__IS
  (JNIEnv *env, jclass, jint pInstance, jshort index )
{
    Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
	RETURN_PTR(pSkeleton->getAnimation( index ));

}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Skeleton_getBlendMode
  (JNIEnv *env, jclass, jint pInstance)
{
    Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
	return pSkeleton->getBlendMode();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Skeleton_setBlendMode
  (JNIEnv *env, jclass, jint pInstance, jint blendMode)
{
    Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
	pSkeleton->setBlendMode((Ogre::SkeletonAnimationBlendMode)blendMode);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Skeleton__1updateTransforms
  (JNIEnv *env, jclass, jint pInstance)
{
    Ogre::Skeleton * pSkeleton = GET_PTR(Ogre::Skeleton, pInstance);
    pSkeleton->_updateTransforms();
}