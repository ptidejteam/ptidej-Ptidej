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
/// @file Ogre4JMesh.cpp
/// Implementation of the JNI bindings for the usage of the Ogre::Mesh class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     09.05.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_Mesh.h>


///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Mesh_dispose
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Mesh * pMesh = GET_PTR(Ogre::Mesh, pInstance);
    Ogre::MeshPtr spMesh = Ogre::MeshManager::getSingleton().getByName(pMesh->getName());
    unsigned int * pUseCount = spMesh.useCountPointer();
    (*pUseCount)--;
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Mesh_setSkeletonName
  (JNIEnv *env, jclass that, jint pInstance, jstring jsSkelName)
{
    Ogre::Mesh * pMesh = GET_PTR(Ogre::Mesh, pInstance);
    Ogre::String sSkelName;
    Ogre4JHelper::CopyJString( env, jsSkelName, sSkelName );
    pMesh->setSkeletonName( sSkelName );    
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jboolean JNICALL Java_org_ogre4j_Mesh_hasSkeleton
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Mesh * pMesh = GET_PTR(Ogre::Mesh, pInstance);
    return pMesh->hasSkeleton();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Mesh_getSkeleton
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Mesh * pMesh = GET_PTR(Ogre::Mesh, pInstance);
    Ogre::SkeletonPtr pSkeleton = pMesh->getSkeleton();
    RETURN_PTR(pSkeleton.getPointer());
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jstring JNICALL Java_org_ogre4j_Mesh_getSkeletonName
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Mesh * pMesh = GET_PTR(Ogre::Mesh, pInstance);
    return env->NewStringUTF( pMesh->getSkeletonName().c_str() );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Mesh_createSubMesh
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Mesh * pMesh = GET_PTR(Ogre::Mesh, pInstance);
    Ogre::SubMesh * pSubMesh = pMesh->createSubMesh();
    RETURN_PTR(pSubMesh);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Mesh_setSharedVertexData
  (JNIEnv *env, jclass that, jint pInstance, jint jiVertexData)
{
    Ogre::Mesh * pMesh = GET_PTR(Ogre::Mesh, pInstance);
    Ogre::VertexData * pVertexData = GET_PTR(Ogre::VertexData,jiVertexData);
    pMesh->sharedVertexData = pVertexData;
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Mesh_getSharedVertexData
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Mesh * pMesh = GET_PTR(Ogre::Mesh, pInstance);
    Ogre::VertexData * pVertexData = pMesh->sharedVertexData;
    RETURN_PTR(pVertexData);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Mesh_load
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Mesh * pMesh = GET_PTR(Ogre::Mesh, pInstance);
    pMesh->load();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Mesh_unload
  (JNIEnv *, jclass, jint pInstance)
{
    Ogre::Mesh * pMesh = GET_PTR(Ogre::Mesh, pInstance);
    pMesh->unload();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Mesh__1setBounds__IIZ
  (JNIEnv *env, jclass that, jint pInstance, jint pAAB, jboolean pad)
{
    Ogre::Mesh * pMesh = GET_PTR(Ogre::Mesh, pInstance);
    Ogre::AxisAlignedBox * aab = GET_PTR(Ogre::AxisAlignedBox, pAAB);
    pMesh->_setBounds(*aab, pad?true:false);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Mesh__1setBounds__IFFFFFFZ
  (JNIEnv *env, jclass that, jint pInstance, jfloat mx, jfloat my, jfloat mz, jfloat Mx, jfloat My, jfloat Mz, jboolean pad)
{
    Ogre::Mesh * pMesh = GET_PTR(Ogre::Mesh, pInstance);
    pMesh->_setBounds(Ogre::AxisAlignedBox(mx, my, mz, Mx, My, Mz), pad?true:false);
}

///////////////////////////////////////////////////////////////////////////////
/// @deprecated
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Mesh_setBounds
  (JNIEnv *env, jclass that, jint pInstance, jfloat mx, jfloat my, jfloat mz, jfloat Mx, jfloat My, jfloat Mz)
{
    Ogre::Mesh * pMesh = GET_PTR(Ogre::Mesh, pInstance);
    pMesh->_setBounds(Ogre::AxisAlignedBox(mx, my, mz, Mx, My, Mz));
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Mesh__1setBoundingSphereRadius
  (JNIEnv *env, jclass that, jint pInstance, jfloat radius)
{
    Ogre::Mesh * pMesh = GET_PTR(Ogre::Mesh, pInstance);
    pMesh->_setBoundingSphereRadius( radius );
}

///////////////////////////////////////////////////////////////////////////////
/// @deprecated
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Mesh_setBoundingSphereRadius
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfRadius)
{
    Ogre::Mesh * pMesh = GET_PTR(Ogre::Mesh, pInstance);
    pMesh->_setBoundingSphereRadius( jfRadius );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jstring JNICALL Java_org_ogre4j_Mesh_getName
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Mesh * pMesh = GET_PTR(Ogre::Mesh, pInstance);
    return env->NewStringUTF( pMesh->getName().c_str() );
}