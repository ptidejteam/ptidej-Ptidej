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
/// @file Ogre4JBillboardSet.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::BillboardSet class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     09.05.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_BillboardSet.h>
/*
static DWORD tlsIndex = 0;
static jobject javaClass;
*/
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_BillboardSet_setMaterialName
  (JNIEnv *env, jclass that, jint pInstance, jstring jsName)
{
    try
    {
        Ogre::BillboardSet * pBBSet = GET_PTR(Ogre::BillboardSet, pInstance);
        Ogre::String sName;
        Ogre4JHelper::CopyJString( env, jsName, sName );
        pBBSet->setMaterialName( sName );
    }
	catch(Ogre::Exception & e)
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew(OgreException, e.getFullDescription().c_str() );
    }
    catch(...)
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew(OgreException, "Unkown exception!" );
    }

}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_BillboardSet_createBillboard
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfX, jfloat jfY, jfloat jfZ)
{
    try
    {
        Ogre::BillboardSet * pBBSet = GET_PTR(Ogre::BillboardSet, pInstance);
        Ogre::Billboard * pBillboard = pBBSet->createBillboard( jfX, jfY, jfZ );
        RETURN_PTR(pBillboard);
    }
	catch(Ogre::Exception & e)
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew(OgreException, e.getFullDescription().c_str() );
    }
    catch(...)
    {
        jclass OgreException = env->FindClass("org/ogre4j/OgreException");
        env->ThrowNew(OgreException, "Unkown exception!" );
    }

}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_BillboardSet_setDefaultWidth
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfWidth)
{
    Ogre::BillboardSet * pBBSet = GET_PTR(Ogre::BillboardSet, pInstance);
    pBBSet->setDefaultWidth( jfWidth );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jstring JNICALL Java_org_ogre4j_BillboardSet_getName
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::BillboardSet * pBBSet = GET_PTR(Ogre::BillboardSet, pInstance);
    return env->NewStringUTF( pBBSet->getName().c_str() );
}