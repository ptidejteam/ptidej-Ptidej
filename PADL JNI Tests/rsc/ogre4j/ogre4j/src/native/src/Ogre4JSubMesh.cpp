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
/// @file Ogre4JSubMesh.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::SubMesh class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     10.05.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_SubMesh.h>

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_SubMesh_addBoneAssignment
  (JNIEnv *env, jclass, jint pInstance, jint vertexIndex, jshort boneIndex, jfloat weight)
{
  Ogre::SubMesh * pSubMesh = GET_PTR(Ogre::SubMesh, pInstance);
	  Ogre::VertexBoneAssignment vba;
        vba.boneIndex   = boneIndex;
        vba.vertexIndex = vertexIndex;
        vba.weight      = weight;
        pSubMesh->addBoneAssignment( vba );

}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_SubMesh_getIndexData
  (JNIEnv *env, jclass, jint pInstance)
{
Ogre::SubMesh * pSubMesh = GET_PTR(Ogre::SubMesh, pInstance);
RETURN_PTR(pSubMesh->indexData);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_SubMesh_setMaterialName
  (JNIEnv *env, jclass, jint pInstance, jstring jsName)
{
	Ogre::String sName;
    Ogre4JHelper::CopyJString( env, jsName, sName );
    Ogre::SubMesh * pSubMesh = GET_PTR(Ogre::SubMesh, pInstance);
    pSubMesh->setMaterialName(sName);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_SubMesh_setUseSharedVertices
  (JNIEnv *env, jclass, jint pInstance, jboolean sharedVert)
{
Ogre::SubMesh * pSubMesh = GET_PTR(Ogre::SubMesh, pInstance);
pSubMesh->useSharedVertices =  sharedVert?true:false;
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jboolean JNICALL Java_org_ogre4j_SubMesh_getUseSharedVertices
  (JNIEnv *env, jclass, jint pInstance)
{
Ogre::SubMesh * pSubMesh = GET_PTR(Ogre::SubMesh, pInstance);
return pSubMesh->useSharedVertices;
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_SubMesh_setVertexData
  (JNIEnv *env, jclass, jint pInstance, jint jVDInstance)
{
  Ogre::SubMesh * pSubMesh = GET_PTR(Ogre::SubMesh, pInstance);
  Ogre::VertexData * pVertexData = GET_PTR(Ogre::VertexData, jVDInstance);
   pSubMesh->vertexData = pVertexData;

}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_SubMesh_getVertexData
  (JNIEnv *env, jclass, jint pInstance)
{
  Ogre::SubMesh * pSubMesh = GET_PTR(Ogre::SubMesh, pInstance);
  RETURN_PTR(pSubMesh->vertexData);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_SubMesh_clearBoneAssignment
  (JNIEnv *env, jclass, jint pInstance)
{
  Ogre::SubMesh * pSubMesh = GET_PTR(Ogre::SubMesh, pInstance);
  pSubMesh->clearBoneAssignments();
}
