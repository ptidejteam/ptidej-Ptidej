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
/// $Revision: 1.2 $
/// $Date: 2005/08/19 09:07:47 $
/// $Author: earl3982 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_MeshManager.h>

using namespace Ogre4JHelper;

/// 
JNIEXPORT jint JNICALL Java_org_ogre4j_MeshManager_getSingletonImpl
(JNIEnv *, jclass)
{
	Ogre::MeshManager * ret = Ogre::MeshManager::getSingletonPtr();
	RETURN_PTR(ret);
}

//private native int createPlane(int ptrSelf, String name, String groupName,
//        float planeX, float planeY, float planeZ, float planeD,
//        float width, float height, int xsegments, int ysegments,
//        boolean normals, int numTexCoordSets, float uTile, float vTile,
//        float upX, float upY, float upZ, int vertexBufferUsage,
//        int indexBufferUsage, boolean vertexShadowBuffer,
//        boolean indexShadowBuffer);
JNIEXPORT jint JNICALL Java_org_ogre4j_MeshManager_createPlane__ILjava_lang_String_2Ljava_lang_String_2FFFFFFIIZIFFFFFIIZZ
(JNIEnv * env, jobject, jint ptrSelf, jstring jname, jstring jgroupName, jfloat jplaneX, 
 jfloat jplaneY, jfloat jplaneZ, jfloat jplaneD, jfloat jwidth, jfloat jheight, jint jxsegments,
 jint jysegments, jboolean jnormals, jint jnumTexCoordSets, jfloat juTile, jfloat jvTile,
 jfloat jupX, jfloat jupY, jfloat jupZ, jint jvertexBufferUsage, jint jindexBufferUsage, 
 jboolean jvertexShadowBuffer, jboolean jindexShadowBuffer)
{
	Ogre::MeshManager * self = GET_PTR(Ogre::MeshManager, ptrSelf);
	Ogre::String name, groupName;
	CopyJString(env, jname, name);
	CopyJString(env, jgroupName, groupName);
	Ogre::Vector3 normal(jplaneX, jplaneY, jplaneZ);
	Ogre::Plane plane(normal, jplaneD);
	bool normals = (jnormals == JNI_TRUE) ? true : false;
	Ogre::Vector3 upVector(jupX, jupY, jupZ);
	Ogre::HardwareBuffer::Usage vertexBufferUsage = static_cast<Ogre::HardwareBuffer::Usage>(jvertexBufferUsage);
	Ogre::HardwareBuffer::Usage indexBufferUsage = static_cast<Ogre::HardwareBuffer::Usage>(jindexBufferUsage);
	bool vertexShadowBuffer = (jvertexShadowBuffer == JNI_TRUE) ? true : false;
	bool indexShadowBuffer = (jindexShadowBuffer == JNI_TRUE) ? true : false;

	Ogre::MeshPtr mesh = self->createPlane(name, groupName, plane, jwidth, jheight, jxsegments, jysegments, 
		normals, jnumTexCoordSets, juTile, jvTile, upVector, vertexBufferUsage, 
		indexBufferUsage, vertexShadowBuffer, indexShadowBuffer);
	unsigned int *count = mesh.useCountPointer();
	(*count)++;
	RETURN_PTR(mesh.getPointer());
}



///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_MeshManager_createInstance
(JNIEnv *env, jclass that)
{
	try
	{
		Ogre::MeshManager * pMshMgr = new Ogre::MeshManager();
		RETURN_PTR(pMshMgr);
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
JNIEXPORT jint JNICALL Java_org_ogre4j_MeshManager__1getSingleton
(JNIEnv *env, jclass that)
{
	try
	{
		Ogre::MeshManager * pMshMgr = Ogre::MeshManager::getSingletonPtr();
		RETURN_PTR(pMshMgr);
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
JNIEXPORT void JNICALL Java_org_ogre4j_MeshManager_dispose
(JNIEnv *env, jclass that,
 jint pInstance)
{
	Ogre::MeshManager * pMshMgr = GET_PTR(Ogre::MeshManager, pInstance);
	delete pMshMgr;
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_MeshManager__1load
(JNIEnv *env, jclass that, 
 jstring jsFileName,
 jstring jsGroupName,
 jint jiVertexBufferUsage,
 jint jiIndexBufferUsage,
 jboolean jbVertexBufferShadowed,
 jboolean jbIndexBufferShadowed)
{
	try
	{
		Ogre::String sFileName, sGroupName;
		Ogre4JHelper::CopyJString( env, jsFileName, sFileName );
		Ogre4JHelper::CopyJString( env, jsGroupName, sGroupName );
		Ogre::MeshPtr pMesh = Ogre::MeshManager::getSingleton().load( 
			sFileName, 
			sGroupName, 
			static_cast<Ogre::HardwareBuffer::Usage>(jiVertexBufferUsage), 
			static_cast<Ogre::HardwareBuffer::Usage>(jiIndexBufferUsage),
			jbVertexBufferShadowed?true:false,
			jbIndexBufferShadowed?true:false);
		unsigned int * pUseCount = pMesh.useCountPointer();
		(*pUseCount)++;
		RETURN_PTR(pMesh.getPointer());
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
JNIEXPORT jint JNICALL Java_org_ogre4j_MeshManager__1createManual
(JNIEnv *env, jclass that, jstring jsName, jstring jsGroupName, jint jiLoader)
{
	try
	{
		Ogre::String sName, sGroupName;
		Ogre4JHelper::CopyJString( env, jsName, sName );
		Ogre4JHelper::CopyJString( env, jsGroupName, sGroupName );
		Ogre::MeshPtr pMesh = Ogre::MeshManager::getSingleton().createManual(
			sName, sGroupName, reinterpret_cast<Ogre::ManualResourceLoader*>(jiLoader) );
		unsigned int * pUseCount = pMesh.useCountPointer();
		(*pUseCount)++;
		RETURN_PTR(pMesh.getPointer());
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
JNIEXPORT jint JNICALL Java_org_ogre4j_MeshManager__1getByName
(JNIEnv *env, jclass that, jstring jsName)
{
	try
	{
		Ogre::String sName;
		Ogre4JHelper::CopyJString(env, jsName, sName);
		Ogre::MeshPtr pMesh = Ogre::MeshManager::getSingleton().getByName( sName );
		unsigned int * pUseCount = pMesh.useCountPointer();
		(*pUseCount)++;
		RETURN_PTR(pMesh.getPointer());
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
JNIEXPORT void JNICALL Java_org_ogre4j_MeshManager__1removeAll
(JNIEnv *env, jclass)
{
	try
	{
		Ogre::MeshManager::getSingleton().removeAll();
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
JNIEXPORT void JNICALL Java_org_ogre4j_MeshManager_remove
(JNIEnv *env, jclass, jint pInstance, jstring jsName)
{
	try
	{
		Ogre::String sName;
		Ogre4JHelper::CopyJString(env, jsName, sName);
		Ogre::MeshManager::getSingleton().remove(sName);
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
/// param[in] name                The name to give the resulting mesh  
/// param[in] groupName           The name of the resource group to assign the mesh to  
/// param[in] pPlane              The orientation of the plane and distance from the origin  
/// param[in] width               The width of the plane in world coordinates  
/// param[in] height              The height of the plane in world coordinates  
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_MeshManager_createPlane__Ljava_lang_String_2Ljava_lang_String_2IFF
(JNIEnv *env, jclass that, jstring name, jstring groupName, jint pPlane, jfloat width, jfloat height)
{
	try
	{
		Ogre::String sName, sGroupName;
		Ogre4JHelper::CopyJString(env, name, sName);
		Ogre4JHelper::CopyJString(env, groupName, sGroupName);
		Ogre::Plane * plane = GET_PTR(Ogre::Plane, pPlane);
		Ogre::MeshPtr meshPtr = Ogre::MeshManager::getSingleton().createPlane(sName, sGroupName, *plane, width, height); 
		unsigned int * pUseCount = meshPtr.useCountPointer();
		(*pUseCount)++;
		RETURN_PTR(meshPtr.getPointer());
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
/// param[in] name                The name to give the resulting mesh  
/// param[in] groupName           The name of the resource group to assign the mesh to  
/// param[in] pPlane              The orientation of the plane and distance from the origin  
/// param[in] width               The width of the plane in world coordinates  
/// param[in] height              The height of the plane in world coordinates  
/// param[in] xsegments           The number of segements to the plane in the x direction  
/// param[in] ysegments           The number of segements to the plane in the y direction  
/// param[in] normals             If true, normals are created perpendicular to the plane  
/// param[in] numTexCoordSets     The number of 2D texture coordinate sets created - by default the corners are created to be the corner of the texture.  
/// param[in] uTile               The number of times the texture should be repeated in the u direction  
/// param[in] vTile               The number of times the texture should be repeated in the v direction  
/// param[in] upVectorX           The 'Up' direction of the plane.  
/// param[in] upVectorY           The 'Up' direction of the plane.  
/// param[in] upVectorZ           The 'Up' direction of the plane.  
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_MeshManager_createPlane__Ljava_lang_String_2Ljava_lang_String_2IFFIIZIFFFFF
(JNIEnv *env, jclass that,
 jstring name,
 jstring groupName,
 jint pPlane,
 jfloat width,
 jfloat height,
 jint xsegments,
 jint ysegments,
 jboolean normals,
 jint numTexCoordSets,
 jfloat uTile,
 jfloat vTile,
 jfloat upVectorX,
 jfloat upVectorY,
 jfloat upVectorZ)
{
	try
	{
		Ogre::String sName, sGroupName;
		Ogre4JHelper::CopyJString(env, name, sName);
		Ogre4JHelper::CopyJString(env, groupName, sGroupName);
		Ogre::Plane * plane = GET_PTR(Ogre::Plane, pPlane);
		Ogre::MeshPtr meshPtr = Ogre::MeshManager::getSingleton().createPlane( sName,
			sGroupName, 
			*plane, 
			width,
			height,
			xsegments,
			ysegments,
			normals?true:false,
			numTexCoordSets,
			uTile,
			vTile,
			Ogre::Vector3(upVectorX, upVectorY, upVectorZ));
		unsigned int * pUseCount = meshPtr.useCountPointer();
		(*pUseCount)++;
		RETURN_PTR(meshPtr.getPointer());
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
/// param[in] name                The name to give the resulting mesh  
/// param[in] groupName           The name of the resource group to assign the mesh to  
/// param[in] pPlane              The orientation of the plane and distance from the origin  
/// param[in] width               The width of the plane in world coordinates  
/// param[in] height              The height of the plane in world coordinates  
/// param[in] xsegments           The number of segements to the plane in the x direction  
/// param[in] ysegments           The number of segements to the plane in the y direction  
/// param[in] normals             If true, normals are created perpendicular to the plane  
/// param[in] numTexCoordSets     The number of 2D texture coordinate sets created - by default the corners are created to be the corner of the texture.  
/// param[in] uTile               The number of times the texture should be repeated in the u direction  
/// param[in] vTile               The number of times the texture should be repeated in the v direction  
/// param[in] upVectorX           The 'Up' direction of the plane.  
/// param[in] upVectorY           The 'Up' direction of the plane.  
/// param[in] upVectorZ           The 'Up' direction of the plane.  
/// param[in] vertexBufferUsage   The usage flag with which the vertex buffer for this plane will be created  
/// param[in] indexBufferUsage    The usage flag with which the index buffer for this plane will be created  
/// param[in] vertexShadowBuffer  If this flag is set to true, the vertex buffer will be created with a system memory shadow buffer, allowing you to read it back more efficiently than if it is in hardware  
/// param[in] indexShadowBuffer   If this flag is set to true, the index buffer will be created with a system memory shadow buffer, allowing you to read it back more efficiently than if it is in hardware  
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_MeshManager_createPlane__Ljava_lang_String_2Ljava_lang_String_2IFFIIZIFFFFFIIZZ
(JNIEnv *env, jclass that,
 jstring name,
 jstring groupName,
 jint pPlane,
 jfloat width,
 jfloat height,
 jint xsegments,
 jint ysegments,
 jboolean normals,
 jint numTexCoordSets,
 jfloat uTile,
 jfloat vTile,
 jfloat upVectorX,
 jfloat upVectorY,
 jfloat upVectorZ,
 jint vertexBufferUsage,
 jint indexBufferUsage,
 jboolean vertexShadowBuffer,
 jboolean indexShadowBuffer)
{
	try
	{
		Ogre::String sName, sGroupName;
		Ogre4JHelper::CopyJString(env, name, sName);
		Ogre4JHelper::CopyJString(env, groupName, sGroupName);
		Ogre::Plane * plane = GET_PTR(Ogre::Plane, pPlane);
		Ogre::MeshPtr meshPtr = Ogre::MeshManager::getSingleton().createPlane( sName,
			sGroupName, 
			*plane, 
			width,
			height,
			xsegments,
			ysegments,
			normals?true:false,
			numTexCoordSets,
			uTile,
			vTile,
			Ogre::Vector3(upVectorX, upVectorY, upVectorZ),
			static_cast<Ogre::HardwareBuffer::Usage>(vertexBufferUsage),
			static_cast<Ogre::HardwareBuffer::Usage>(indexBufferUsage),
			vertexShadowBuffer?true:false,
			indexShadowBuffer?true:false); 
		unsigned int * pUseCount = meshPtr.useCountPointer();
		(*pUseCount)++;
		RETURN_PTR(meshPtr.getPointer());
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


