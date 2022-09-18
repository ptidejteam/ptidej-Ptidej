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
/// @file Ogre4JStaticGeometry.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::StaticGeometry class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     10.05.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_StaticGeometry.h>

/*
static DWORD tlsIndex = 0;
static jobject javaClass;

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_StaticGeometry_createInstance
  (JNIEnv *env, jclass that, jint jiOwner, jstring jsName)
{
    Ogre4JHelper::normalLogMsg( "-- Create Ray Instance --" );
    if (tlsIndex == 0) 
    {
        tlsIndex = TlsAlloc();
        if (tlsIndex == -1) return (jint) 0;
        javaClass = env->NewGlobalRef( static_cast<jobject>(that) );
    }
    TlsSetValue(tlsIndex, (LPVOID) env);

    Ogre::String sName;
    Ogre4JHelper::CopyJString(env, jsName, sName);
    Ogre::SceneManager * pOwner = GET_PTR(Ogre::SceneManager,jiOwner);
    Ogre::StaticGeometry * pSGeom  = new Ogre::StaticGeometry(pOwner, sName);
    RETURN_PTR(pSGeom);
}
*/
///////////////////////////////////////////////////////////////////////////////  
/// @param[in] jiEntity     Pointer to Ogre::Entity.
/// @param[in] jfLocX       X of vector for Location.
/// @param[in] jfLocY       Y of vector for Location.
/// @param[in] jfLocZ       Z of vector for Location.
/// @param[in] jfRotW       W of quaternion for rotation.
/// @param[in] jfRotX       X of quaternion for rotation.
/// @param[in] jfRotY       Y of quaternion for rotation.
/// @param[in] jfRotZ       Z of quaternion for rotation.
/// @param[in] jfScaleX     X of vector for scale.
/// @param[in] jfScaleY     Y of vector for scale.
/// @param[in] jfScaleZ     Z of vector for scale.
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_StaticGeometry_addEntity
  (JNIEnv *env, jclass that, jint pInstance,
  jint jiEntity,
  jfloat jfLocX, jfloat jfLocY, jfloat jfLocZ,
  jfloat jfRotW, jfloat jfRotX, jfloat jfRotY, jfloat jfRotZ,
  jfloat jfScaleX, jfloat jfScaleY, jfloat jfScaleZ)
{
    try
    {
        Ogre::StaticGeometry * pSGeom = GET_PTR(Ogre::StaticGeometry, pInstance);
        Ogre::Entity * pEntity = GET_PTR(Ogre::Entity,jiEntity);
        pSGeom->addEntity( pEntity,
        Ogre::Vector3( jfLocX, jfLocY, jfLocZ ),
        Ogre::Quaternion( jfRotW, jfRotX, jfRotY, jfRotZ ),
        Ogre::Vector3( jfScaleX, jfScaleY, jfScaleZ ) );
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
JNIEXPORT void JNICALL Java_org_ogre4j_StaticGeometry_setRegionDimensions
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfSizeX, jfloat jfSizeY, jfloat jfSizeZ)
{
    Ogre::StaticGeometry * pSGeom = GET_PTR(Ogre::StaticGeometry, pInstance);
    pSGeom->setRegionDimensions( Ogre::Vector3( jfSizeX, jfSizeY, jfSizeZ ) );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_StaticGeometry_setOrigin
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfOriginX, jfloat jfOriginY, jfloat jfOriginZ)
{
    Ogre::StaticGeometry * pSGeom = GET_PTR(Ogre::StaticGeometry, pInstance);
    pSGeom->setOrigin( Ogre::Vector3( jfOriginX, jfOriginY, jfOriginZ ) );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_StaticGeometry_build
  (JNIEnv *env, jclass that, jint pInstance)
{
    try
    {
        Ogre::StaticGeometry * pSGeom = GET_PTR(Ogre::StaticGeometry, pInstance);
        pSGeom->build();
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