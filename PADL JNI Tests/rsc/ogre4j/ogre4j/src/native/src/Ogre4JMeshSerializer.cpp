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
/// @file Ogre4JMeshSerializer.cpp
/// Implementation of the JNI bindings for the usage of the Ogre::MeshSerializer class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     09.05.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_MeshSerializer.h>
#include <windows.h>

static DWORD tlsIndex = 0;
static jobject javaClass;

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_MeshSerializer_createInstance
  (JNIEnv * env, jclass that)
{
    Ogre4JHelper::normalLogMsg( "-- Create MeshSerializer Instance --" );
    if (tlsIndex == 0) 
    {
        tlsIndex = TlsAlloc();
        if (tlsIndex == -1) return (jint) 0;
        javaClass = env->NewGlobalRef( static_cast<jobject>(that) );
    }
    TlsSetValue(tlsIndex, (LPVOID) env);

    Ogre::MeshSerializer * pMeshSer = new Ogre::MeshSerializer();
    RETURN_PTR(pMeshSer);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_MeshSerializer_dispose
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre4JHelper::normalLogMsg( "-- Delete MeshSerializer Instance --" );
    Ogre::MeshSerializer * pMeshSer = GET_PTR( Ogre::MeshSerializer, pInstance);
    delete pMeshSer;

}
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_MeshSerializer_exportMesh
  (JNIEnv *env, jclass that, jint pInstance, jint jiMesh, jstring jsFileName)
{
    Ogre::Mesh * pMesh = GET_PTR(Ogre::Mesh, jiMesh);
    Ogre::String sFileName;
    Ogre4JHelper::CopyJString(env, jsFileName, sFileName );
    Ogre::MeshSerializer * pMeshSer = GET_PTR(Ogre::MeshSerializer,pInstance);
    pMeshSer->exportMesh( pMesh, sFileName );
}
