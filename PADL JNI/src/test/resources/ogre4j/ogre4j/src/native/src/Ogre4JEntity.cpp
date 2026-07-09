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
/// @file Ogre4JEntity.cpp
/// Implementation of the JNI bindings for the usage of the Ogre::Entity class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     09.05.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_Entity.h>


///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Entity_getMesh
  (JNIEnv *env, jclass that, jint pInstance)
{

	// TODODODODODODO SHAREDPOINTER!
   Ogre::Entity * pEntity = GET_PTR(Ogre::Entity,pInstance);
   Ogre::MeshPtr pMesh = pEntity->getMesh();
   RETURN_PTR(pMesh.getPointer());
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Entity_getSubEntity__II
  (JNIEnv *env, jclass that, jint pInstance, jint jiIndex)
{    
    Ogre::Entity * pEntity = GET_PTR(Ogre::Entity,pInstance);
    Ogre::SubEntity * pSubEntity = pEntity->getSubEntity( jiIndex );
    RETURN_PTR(pSubEntity);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Entity_getSubEntity__ILjava_lang_String_2
  (JNIEnv *env, jclass that, jint pInstance, jstring jsName)
{
    Ogre::Entity * pEntity = GET_PTR(Ogre::Entity,pInstance);
    Ogre::String sName;
    Ogre4JHelper::CopyJString( env, jsName, sName );
    Ogre::SubEntity * pSubEntity = pEntity->getSubEntity( sName );
    RETURN_PTR(pSubEntity);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Entity_getNumSubEntities
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Entity * pEntity = GET_PTR(Ogre::Entity,pInstance);
    return pEntity->getNumSubEntities();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Entity_clone
  (JNIEnv *env, jclass that, jint pInstance, jstring jsNewName)
{
    Ogre::Entity * pEntity = GET_PTR(Ogre::Entity,pInstance);
    Ogre::String sNewName;
    Ogre4JHelper::CopyJString( env, jsNewName, sNewName );
    Ogre::Entity * pNewEntity = pEntity->clone( sNewName );
    RETURN_PTR(pNewEntity);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Entity_setMaterialName
  (JNIEnv *env, jclass that, jint pInstance, jstring jsMaterialName)
{    
    Ogre::Entity * pEntity = GET_PTR(Ogre::Entity,pInstance);
    Ogre::String sMaterialName;
    Ogre4JHelper::CopyJString( env, jsMaterialName, sMaterialName );
    pEntity->setMaterialName( sMaterialName );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Entity_getAnimationState
  (JNIEnv *env, jclass that, jint pInstance, jstring jsName)
{    
    Ogre::Entity * pEntity = GET_PTR(Ogre::Entity,pInstance);
    Ogre::String sName;
    Ogre4JHelper::CopyJString( env, jsName, sName );
    try{
        Ogre::AnimationState * pAnimState = pEntity->getAnimationState( sName );
        RETURN_PTR(pAnimState);
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
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Entity_setDisplaySkeleton
  (JNIEnv *env, jclass that, jint pInstance, jboolean jbDisplay)
{ 
    Ogre::Entity * pEntity = GET_PTR(Ogre::Entity,pInstance);
    pEntity->setDisplaySkeleton( jbDisplay?true:false );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jboolean JNICALL Java_org_ogre4j_Entity_hasSkeleton
  (JNIEnv *env, jclass that, jint pInstance)
{    
    Ogre::Entity * pEntity = GET_PTR(Ogre::Entity,pInstance);
    return pEntity->hasSkeleton();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Entity_getSkeleton
  (JNIEnv *env, jclass that, jint pInstance)
{   
    Ogre::Entity * pEntity = GET_PTR(Ogre::Entity,pInstance);
    Ogre::SkeletonInstance * pSkeleton = pEntity->getSkeleton();
    RETURN_PTR(pSkeleton);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Entity_attachObjectToBone__ILjava_lang_String_2I
  (JNIEnv *env, jclass that, jint pInstance, jstring jsBoneName, jint pMoveable)
{
    try
    {
        Ogre::Entity * pEntity = GET_PTR(Ogre::Entity,pInstance);
        Ogre::String sBoneName;
        Ogre4JHelper::CopyJString( env, jsBoneName, sBoneName );
        pEntity->attachObjectToBone( sBoneName, GET_PTR(Ogre::MovableObject,pMoveable) );
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
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Entity_attachObjectToBone__ILjava_lang_String_2IFFFFFFF
  (JNIEnv *env, jclass that, jint pInstance, jstring jsBoneName, jint pMoveable,
  jfloat jfQuatW, jfloat jfQuatX, jfloat jfQuatY, jfloat jfQuatZ,
  jfloat jfVecX, jfloat jfVecY, jfloat jfVecZ)
{
    try
    {
	    Ogre::Entity * pEntity = GET_PTR(Ogre::Entity,pInstance);
        Ogre::String sBoneName;
        Ogre4JHelper::CopyJString( env, jsBoneName, sBoneName );
        pEntity->attachObjectToBone( sBoneName, GET_PTR(Ogre::MovableObject,pMoveable),
		    Ogre::Quaternion(jfQuatW, jfQuatX, jfQuatY, jfQuatZ),
		    Ogre::Vector3( jfVecX, jfVecY, jfVecZ) );
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
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Entity_detachObjectFromBone
  (JNIEnv *env, jclass that, jint pInstance, jstring jsMoveableName)
{
    try
    {
        Ogre::Entity * pEntity = GET_PTR(Ogre::Entity,pInstance);
        Ogre::String sMoveableName;
        Ogre4JHelper::CopyJString( env, jsMoveableName, sMoveableName );
        Ogre::MovableObject * pMoveable = pEntity->detachObjectFromBone( sMoveableName );
        RETURN_PTR(pMoveable);
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
}