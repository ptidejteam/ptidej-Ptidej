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
/// @file Ogre4JResourceGroupManager.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::ResourceGroupManager class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     07.05.2005
///
/// $Revision: 1.2 $
/// $Date: 2005/08/20 03:02:11 $
/// $Author: earl3982 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_ResourceGroupManager.h>

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_ResourceGroupManager_createInstance
  (JNIEnv *env, jclass)
{
    try
    {
        Ogre::ResourceGroupManager * pResGrpMgr = new Ogre::ResourceGroupManager();
        RETURN_PTR(pResGrpMgr);
    }
    catch( Ogre::Exception & e )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, e.getFullDescription().c_str() );
    }
    catch( ... )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, "Unknown Exception" );
    }
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_ResourceGroupManager_getSingletonImpl
  (JNIEnv * env, jclass)
{
    try
    {
        Ogre::ResourceGroupManager * pResGrpMgr = Ogre::ResourceGroupManager::getSingletonPtr();
        RETURN_PTR(pResGrpMgr);
    }
    catch( Ogre::Exception & e )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, e.getFullDescription().c_str() );
    }
    catch( ... )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, "Unknown Exception" );
    }
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_ResourceGroupManager_dispose
  (JNIEnv *, jclass, jint pInstance)
{
    Ogre::ResourceGroupManager * pResGrpMgr = GET_PTR(Ogre::ResourceGroupManager, pInstance);
    delete pResGrpMgr;
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_ResourceGroupManager_createResourceGroup
  (JNIEnv *env, jclass that, jint pInstance, jstring name)
 {
    try
    {
        Ogre::String sName;
        Ogre4JHelper::CopyJString(env, name, sName);
	    Ogre::ResourceGroupManager::getSingleton().createResourceGroup(sName);
    }
    catch( Ogre::Exception & e )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, e.getFullDescription().c_str() );
    }
    catch( ... )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, "Unknown Exception" );
    }
 }

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_ResourceGroupManager_initialiseResourceGroup
  (JNIEnv *env, jclass that, jint pInstance, jstring name) 
{
    try
    {
        Ogre::String sName;
        Ogre4JHelper::CopyJString(env, name, sName);
	    Ogre::ResourceGroupManager::getSingleton().initialiseResourceGroup(sName);
    }
    catch( Ogre::Exception & e )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, e.getFullDescription().c_str() );
    }
    catch( ... )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, "Unknown Exception" );
    }
}


///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_ResourceGroupManager_initialiseAllResourceGroups
  (JNIEnv *env, jclass that, jint pInstance)
{
    try
    {
	    Ogre::ResourceGroupManager::getSingleton().initialiseAllResourceGroups();
    }
    catch( Ogre::Exception & e )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, e.getFullDescription().c_str() );
    }
    catch( ... )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, "Unknown Exception" );
    }
}


///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_ResourceGroupManager_loadResourceGroup
  (JNIEnv *env, jclass that, jint pInstance, jstring name, jboolean loadMainResources, jboolean loadWorldGeom)
{
    try
    {
        Ogre::String sName;
        Ogre4JHelper::CopyJString(env, name, sName);
        Ogre::ResourceGroupManager::getSingleton().loadResourceGroup(sName, loadMainResources?true:false, loadWorldGeom?true:false);
    }
    catch( Ogre::Exception & e )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, e.getFullDescription().c_str() );
    }
    catch( ... )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, "Unknown Exception" );
    }
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_ResourceGroupManager_unloadResourceGroup
  (JNIEnv *env, jclass that, jint pInstance, jstring name)
{
    try
    {
        Ogre::String sName;
        Ogre4JHelper::CopyJString(env, name, sName);
        Ogre::ResourceGroupManager::getSingleton().unloadResourceGroup(sName);
    }
    catch( Ogre::Exception & e )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, e.getFullDescription().c_str() );
    }
    catch( ... )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, "Unknown Exception" );
    }
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_ResourceGroupManager_clearResourceGroup
  (JNIEnv *env, jclass that, jint pInstance, jstring name)
{
    try
    {
        Ogre::String sName;
        Ogre4JHelper::CopyJString(env, name, sName);
        Ogre::ResourceGroupManager::getSingleton().clearResourceGroup(sName);
    }
    catch( Ogre::Exception & e )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, e.getFullDescription().c_str() );
    }
    catch( ... )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, "Unknown Exception" );
    }
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_ResourceGroupManager_destroyResourceGroup
  (JNIEnv *env, jclass that, jint pInstance, jstring name)
{
  try
  {
        Ogre::String sName;
        Ogre4JHelper::CopyJString( env, name, sName );
        Ogre::ResourceGroupManager::getSingleton().destroyResourceGroup(sName);
  }
    catch( Ogre::Exception & e )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, e.getFullDescription().c_str() );
    }
    catch( ... )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, "Unknown Exception" );
    }
}


///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_ResourceGroupManager_addResourceLocation
(JNIEnv *env, jclass that, jint pInstance,
 jstring jsName, jstring jsLocType, jstring jsResGroup, jboolean jbRecursive) {

    Ogre::String sName, sLocType, sResGroup;
    Ogre4JHelper::CopyJString( env, jsName, sName );
    Ogre4JHelper::CopyJString( env, jsLocType, sLocType );
    Ogre4JHelper::CopyJString( env, jsResGroup, sResGroup );
    try
    {
        Ogre::ResourceGroupManager::getSingleton().addResourceLocation(
            sName, sLocType, sResGroup,jbRecursive?true:false);
    }
    catch( Ogre::Exception & e )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, e.getFullDescription().c_str() );
    }
    catch( ... )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, "Unknown Exception" );
    }
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_ResourceGroupManager_removeResourceLocation
  (JNIEnv *env, jclass that, jint pInstance, jstring name, jstring resGroup)
{
    try
    {
        Ogre::String sName, sResGroup;
        Ogre4JHelper::CopyJString(env, name, sName);
        Ogre4JHelper::CopyJString(env, resGroup, sResGroup);
        Ogre::ResourceGroupManager::getSingleton().removeResourceLocation(sName, sResGroup);
    }
    catch( Ogre::Exception & e )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, e.getFullDescription().c_str() );
    }
    catch( ... )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, "Unknown Exception" );
    }
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_ResourceGroupManager_shutdownAll
  (JNIEnv *env, jclass that, jint pInstance)
{
    try
    {
        Ogre::ResourceGroupManager::getSingleton().shutdownAll();
    }
    catch( Ogre::Exception & e )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, e.getFullDescription().c_str() );
    }
    catch( ... )
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew( OgreException, "Unknown Exception" );
    }
}
