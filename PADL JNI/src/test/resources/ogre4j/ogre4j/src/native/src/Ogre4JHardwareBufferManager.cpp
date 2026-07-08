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
/// @file Ogr4JHardwareBufferManager.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::HardwareBufferManager class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     06.06.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_HardwareBufferManager.h>

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_HardwareBufferManager__1getSingleton
  (JNIEnv *env, jclass)
{
    try
    {
        Ogre::HardwareBufferManager * hwBufMgr = Ogre::HardwareBufferManager::getSingletonPtr();
        RETURN_PTR(hwBufMgr);
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

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_HardwareBufferManager_dispose
  (JNIEnv *, jclass, jint pInstance)
{
    Ogre::HardwareBufferManager *pHwBufMgr = GET_PTR(Ogre::HardwareBufferManager, pInstance);
    delete pHwBufMgr;
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_HardwareBufferManager_createVertexBuffer
  (JNIEnv *env, jclass that, jint pInstance,
   jint jiVertexSize, jint jiNumVerts, jint jiUsage, jboolean jbUseShadowBuffer )
{
    Ogre::HardwareBufferManager * pHwBufMgr = Ogre::HardwareBufferManager::getSingletonPtr();
    Ogre::HardwareVertexBufferSharedPtr hwVertBuf = pHwBufMgr->createVertexBuffer(
        jiVertexSize, jiNumVerts, static_cast<Ogre::HardwareBuffer::Usage>(jiUsage),
        jbUseShadowBuffer?true:false);

    //we need a second shared pointer on the heap because the HardwareBufferManager gives
    //us no chance to retrieve the shared pointer again!
    Ogre::HardwareVertexBufferSharedPtr * pHwVertBuf = new Ogre::HardwareVertexBufferSharedPtr(hwVertBuf);
    RETURN_PTR(pHwVertBuf);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_HardwareBufferManager_createIndexBuffer
  (JNIEnv *env, jclass that, jint pInstance,
   jint jiIndexType, jint jiNumIndexes, jint jiUsage, jboolean jbUseShadowBuffer)
{
    Ogre::HardwareBufferManager * pHwBufMgr = Ogre::HardwareBufferManager::getSingletonPtr();
    Ogre::HardwareIndexBufferSharedPtr hwIndexBuf = pHwBufMgr->createIndexBuffer(
        static_cast<Ogre::HardwareIndexBuffer::IndexType>(jiIndexType),
        jiNumIndexes, static_cast<Ogre::HardwareBuffer::Usage>(jiUsage),
        jbUseShadowBuffer?true:false);

    //we need a second shared pointer on the heap because the HardwareBufferManager gives
    //us no chance to retrieve the shared pointer again!
    Ogre::HardwareIndexBufferSharedPtr * pHwIndexBuf = new Ogre::HardwareIndexBufferSharedPtr(hwIndexBuf);
    RETURN_PTR(pHwIndexBuf);
}
