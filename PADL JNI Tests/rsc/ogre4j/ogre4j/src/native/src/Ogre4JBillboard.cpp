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
/// @file Ogre4JBillboard.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::Billboard class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     15.07.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_Billboard.h>

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Billboard_createInstance__
(JNIEnv *env, jclass that)
{
    try
    {
        Ogre::Billboard * pBillboard = new Ogre::Billboard();
        RETURN_PTR(pBillboard);
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
JNIEXPORT jint JNICALL Java_org_ogre4j_Billboard_createInstance__FFFIFFFF
(JNIEnv *env, jclass that, 
 jfloat x, jfloat y, jfloat z, 
 jint pOwner, 
 jfloat r, jfloat g, jfloat b, jfloat a)
{
    try
    {
        Ogre::BillboardSet * pBillboardSet = GET_PTR(Ogre::BillboardSet, pOwner);
        Ogre::Billboard * pBillboard = new Ogre::Billboard(Ogre::Vector3(x,y,z), 
            pBillboardSet, Ogre::ColourValue(r,g,b,a));
        RETURN_PTR(pBillboard);
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
JNIEXPORT void JNICALL Java_org_ogre4j_Billboard_dispose
  (JNIEnv *env, jclass that, jint pInstance)
{
    try
    {
        Ogre::Billboard * pBillboard = GET_PTR(Ogre::Billboard, pInstance);
        delete pBillboard;
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
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Billboard_getRotation
(JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Billboard * pBillboard = GET_PTR(Ogre::Billboard, pInstance);
    return pBillboard->getRotation().valueRadians();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Billboard_setRotation
(JNIEnv *env, jclass that, jint pInstance, jfloat rotation)
{
    Ogre::Billboard * pBillboard = GET_PTR(Ogre::Billboard, pInstance);
    pBillboard->setRotation(static_cast<Ogre::Radian>(rotation));
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Billboard_setPosition
(JNIEnv *env, jclass that, jint pInstance, jfloat x, jfloat y, jfloat z)
{
    Ogre::Billboard * pBillboard = GET_PTR(Ogre::Billboard, pInstance);
    pBillboard->setPosition(x,y,z);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Billboard_getPosition
(JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Billboard * pBillboard = GET_PTR(Ogre::Billboard, pInstance);
    RETURN_VECTOR3F(pBillboard->mPosition);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Billboard_getDirection
(JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Billboard * pBillboard = GET_PTR(Ogre::Billboard, pInstance);
    RETURN_VECTOR3F(pBillboard->mDirection);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Billboard_setDirection
(JNIEnv *env, jclass that, jint pInstance, jfloat x, jfloat y, jfloat z)
{
    Ogre::Billboard * pBillboard = GET_PTR(Ogre::Billboard, pInstance);
    pBillboard->mDirection = Ogre::Vector3(x,y,z);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Billboard_getParentSet
(JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Billboard * pBillboard = GET_PTR(Ogre::Billboard, pInstance);
    RETURN_PTR(pBillboard->mParentSet);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Billboard_setDimensions
(JNIEnv *env, jclass that, jint pInstance, jfloat width, jfloat height)
{
    Ogre::Billboard * pBillboard = GET_PTR(Ogre::Billboard, pInstance);
    pBillboard->setDimensions(width, height);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Billboard_resetDimensions
(JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Billboard * pBillboard = GET_PTR(Ogre::Billboard, pInstance);
    pBillboard->resetDimensions();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Billboard_getColour
(JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Billboard * pBillboard = GET_PTR(Ogre::Billboard, pInstance);
    RETURN_COLOUR_VALUE(pBillboard->mColour);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Billboard_setColour
(JNIEnv *env, jclass that, jint pInstance, jfloat r, jfloat g, jfloat b, jfloat a)
{
    Ogre::Billboard * pBillboard = GET_PTR(Ogre::Billboard, pInstance);
    pBillboard->mColour = Ogre::ColourValue(r,g,b,a);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jboolean JNICALL Java_org_ogre4j_Billboard_hasOwnDimensions
(JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Billboard * pBillboard = GET_PTR(Ogre::Billboard, pInstance);
    return pBillboard->hasOwnDimensions();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Billboard_getOwnWidth
(JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Billboard * pBillboard = GET_PTR(Ogre::Billboard, pInstance);
    return pBillboard->getOwnWidth();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Billboard_getOwnHeight
(JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Billboard * pBillboard = GET_PTR(Ogre::Billboard, pInstance);
    return pBillboard->getOwnHeight();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Billboard__1notifyOwner
(JNIEnv *env, jclass that, jint pInstance, jint pOwner)
{
    Ogre::Billboard * pBillboard = GET_PTR(Ogre::Billboard, pInstance);
    Ogre::BillboardSet * pBillboardSet = GET_PTR(Ogre::BillboardSet, pOwner);
    pBillboard->_notifyOwner(pBillboardSet);
}