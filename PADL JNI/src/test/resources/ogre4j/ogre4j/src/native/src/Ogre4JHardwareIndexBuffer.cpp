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
/// @file Ogr4JHardwareIndexBuffer.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::HardwareIndexBuffer class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     06.06.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///
/// This library is free software; you can redistribute it and/or
/// modify it under the terms of the GNU Lesser General Public
/// License as published by the Free Software Foundation; either
/// version 2.1 of the License, or any later version.
/// 
/// This library is distributed in the hope that it will be useful,
/// but WITHOUT ANY WARRANTY; without even the implied warranty of
/// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
/// Lesser General Public License for more details.
/// 
/// You should have received a copy of the GNU Lesser General Public
/// License along with this library; if not, write to the Free Software
/// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_HardwareIndexBuffer.h>

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_HardwareIndexBuffer_dispose
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::HardwareIndexBufferSharedPtr * pHwIndexBuf = GET_PTR(Ogre::HardwareIndexBufferSharedPtr, pInstance);
    delete pHwIndexBuf;
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_HardwareIndexBuffer_writeData__IIJ_3IZ
  (JNIEnv *env, jclass that, jint pInstance, jint jiOffset, jlong jlLength, jintArray jiaSource, jboolean jbDiscardWholeBuffer)
{
    try
    {
        Ogre::HardwareIndexBufferSharedPtr * pHwIndexBuf = GET_PTR(Ogre::HardwareIndexBufferSharedPtr, pInstance);
        jsize len = env->GetArrayLength( jiaSource );
        jint *pSource = env->GetIntArrayElements( jiaSource, 0 );
        pHwIndexBuf->get()->writeData( jiOffset, jlLength, pSource, jbDiscardWholeBuffer?true:false);
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
JNIEXPORT void JNICALL Java_org_ogre4j_HardwareIndexBuffer_writeData__IIJ_3SZ
  (JNIEnv *env, jclass that, jint pInstance, jint jiOffset, jlong jlLength, jshortArray jsaSource, jboolean jbDiscardWholeBuffer)
{
    try
    {
        Ogre::HardwareIndexBufferSharedPtr * pHwIndexBuf = GET_PTR(Ogre::HardwareIndexBufferSharedPtr, pInstance);
        jsize len = env->GetArrayLength( jsaSource );
        jshort *pSource = env->GetShortArrayElements( jsaSource, 0 );
        pHwIndexBuf->get()->writeData( jiOffset, jlLength, pSource, jbDiscardWholeBuffer?true:false);
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
JNIEXPORT jlong JNICALL Java_org_ogre4j_HardwareIndexBuffer_getSizeInBytes
  (JNIEnv *, jclass, jint pInstance)
{
    Ogre::HardwareIndexBufferSharedPtr * pHwIndexBuf = GET_PTR(Ogre::HardwareIndexBufferSharedPtr, pInstance);
    return pHwIndexBuf->get()->getSizeInBytes();
}

