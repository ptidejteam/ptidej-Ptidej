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
/// @file Ogre4JSkeletonManager.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::SkeletonManager class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     07.05.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_SkeletonManager.h>

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_SkeletonManager_createInstance
(JNIEnv *env, jclass) 
{
    try
    {
        Ogre::SkeletonManager *skelManager = new Ogre::SkeletonManager();
        RETURN_PTR(skelManager);
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
JNIEXPORT jint JNICALL Java_org_ogre4j_SkeletonManager__1getSingleton
  (JNIEnv *env, jclass)
{
    try
    {
        Ogre::SkeletonManager *skelManager = Ogre::SkeletonManager::getSingletonPtr();
        RETURN_PTR(skelManager);
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
JNIEXPORT void JNICALL Java_org_ogre4j_SkeletonManager_dispose
  (JNIEnv *env, jclass, jint pInstance)
{
    Ogre::SkeletonManager *skelManager = GET_PTR(Ogre::SkeletonManager, pInstance);
    delete skelManager;
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_SkeletonManager__1create
(JNIEnv *env, jclass, jstring jsFileName, jstring jsGroupName, jboolean isManual, jint loader, jint createParams)
{
    try
    {
	    Ogre::String sFileName, sGroupName;
        Ogre4JHelper::CopyJString( env, jsFileName, sFileName );
	    Ogre4JHelper::CopyJString( env, jsGroupName, sGroupName );

	    Ogre::SkeletonPtr sptr = 	Ogre::SkeletonManager::getSingleton().create(sFileName, 
															    sGroupName, 
                                                                isManual?true:false, 
															    (Ogre::ManualResourceLoader*)loader, 
															    (Ogre::NameValuePairList*)createParams);
        unsigned int * pUseCount = sptr.useCountPointer();
        (*pUseCount)++;
	    RETURN_PTR(sptr.getPointer());
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
JNIEXPORT void JNICALL Java_org_ogre4j_SkeletonManager__1removeAll
  (JNIEnv *, jclass)
{
	Ogre::SkeletonManager::getSingleton().removeAll();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_SkeletonManager__1getByName
  (JNIEnv *env, jclass that, jstring jsName)
{
    try
    {
        Ogre::String sName;
        Ogre4JHelper::CopyJString( env, jsName, sName );
        Ogre::SkeletonPtr pSkeleton = Ogre::SkeletonManager::getSingleton().getByName( sName );
        unsigned int * pUseCount = pSkeleton.useCountPointer();
        (*pUseCount)++;
        RETURN_PTR(pSkeleton.getPointer());
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