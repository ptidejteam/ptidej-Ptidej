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
/// @file Ogre4JAxisAlignedBox.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::AxisAlignedBox class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     23.06.2005
///
/// $Revision: 1.2 $
/// $Date: 2005/08/24 11:10:34 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////

#include <Ogre4J.h>
#include <org_ogre4j_AxisAlignedBox.h>


// int createInstance()
JNIEXPORT jint JNICALL Java_org_ogre4j_AxisAlignedBox_createInstance__
  (JNIEnv *, jclass)
{
    Ogre::AxisAlignedBox * pAAB = new Ogre::AxisAlignedBox();
    RETURN_PTR(pAAB);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_AxisAlignedBox_createInstance__FFFFFF
  (JNIEnv *env, jclass that, jfloat mx, jfloat my, jfloat mz, jfloat Mx, jfloat My, jfloat Mz)
{
    Ogre::AxisAlignedBox * pAAB = new Ogre::AxisAlignedBox(mx, my, mz, Mx, My, Mz);
    RETURN_PTR(pAAB);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_AxisAlignedBox_dispose
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::AxisAlignedBox *pAAB = (Ogre::AxisAlignedBox*)(pInstance);
    delete pAAB;
}

/// float[] getCenter()
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_AxisAlignedBox_getCenter
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::AxisAlignedBox *pAAB = (Ogre::AxisAlignedBox*)(pInstance);
    Ogre::Vector3 center = pAAB->getCenter();
    RETURN_VECTOR3F(center);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_AxisAlignedBox_getMinimum
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::AxisAlignedBox *pAAB = (Ogre::AxisAlignedBox*)(pInstance);
    Ogre::Vector3 vector = pAAB->getMinimum();
    RETURN_VECTOR3F(vector);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_AxisAlignedBox_getMaximum
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::AxisAlignedBox *pAAB = (Ogre::AxisAlignedBox*)(pInstance);
    Ogre::Vector3 vector = pAAB->getMaximum();
    RETURN_VECTOR3F(vector);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_AxisAlignedBox_intersection
  (JNIEnv *env, jclass that, jint pInstance, jint pB2)
{
    Ogre::AxisAlignedBox *pAAB = (Ogre::AxisAlignedBox*)(pInstance);
    Ogre::AxisAlignedBox *pAAB2 = (Ogre::AxisAlignedBox*)(pB2);
    Ogre::AxisAlignedBox *intersection = new Ogre::AxisAlignedBox(pAAB->intersection(*pAAB2));
    RETURN_PTR(intersection);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jboolean JNICALL Java_org_ogre4j_AxisAlignedBox_intersects
  (JNIEnv *env, jclass that, jint pInstance, jint pB2)
{
    Ogre::AxisAlignedBox *pAAB = (Ogre::AxisAlignedBox*)(pInstance);
    Ogre::AxisAlignedBox *pAAB2 = (Ogre::AxisAlignedBox*)(pB2);
    return pAAB->intersects(*pAAB2);
}

/// boolean isNull()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_AxisAlignedBox_isNull
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::AxisAlignedBox *pAAB = (Ogre::AxisAlignedBox*)(pInstance);
    return pAAB->isNull();
}

/// void merge(AxisAlignedBox rhs)
JNIEXPORT void JNICALL Java_org_ogre4j_AxisAlignedBox_merge
  (JNIEnv *env, jclass that, jint pInstance, jint pRhs)
{
    Ogre::AxisAlignedBox *pAAB = (Ogre::AxisAlignedBox*)(pInstance);
    Ogre::AxisAlignedBox *rhs = (Ogre::AxisAlignedBox*)(pRhs);
    pAAB->merge(*rhs);
}

/// void scale(float x, float y, float z)
JNIEXPORT void JNICALL Java_org_ogre4j_AxisAlignedBox_scale
  (JNIEnv *env, jclass that, jint pInstance, jfloat x, jfloat y, jfloat z)
{
    Ogre::AxisAlignedBox *pAAB = (Ogre::AxisAlignedBox*)(pInstance);
    pAAB->scale(Ogre::Vector3(x,y,z));
}

/// void setMinimum(float x, float y, float z)
JNIEXPORT void JNICALL Java_org_ogre4j_AxisAlignedBox_setMinimum
  (JNIEnv *env, jclass that, jint pInstance, jfloat x, jfloat y, jfloat z)
{
    Ogre::AxisAlignedBox *pAAB = (Ogre::AxisAlignedBox*)(pInstance);
    pAAB->setMinimum(x,y,z);
}

/// void setMaximum(float x, float y, float z)
JNIEXPORT void JNICALL Java_org_ogre4j_AxisAlignedBox_setMaximum
  (JNIEnv *env, jclass that, jint pInstance, jfloat x, jfloat y, jfloat z)
{
    Ogre::AxisAlignedBox *pAAB = (Ogre::AxisAlignedBox*)(pInstance);
    pAAB->setMaximum(x,y,z);
}

/// void setNull()
JNIEXPORT void JNICALL Java_org_ogre4j_AxisAlignedBox_setNull
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::AxisAlignedBox *pAAB = (Ogre::AxisAlignedBox*)(pInstance);
    pAAB->setNull();
}

/// void transform(Matrix m)
JNIEXPORT void JNICALL Java_org_ogre4j_AxisAlignedBox_transform
  (JNIEnv *env, jclass that, jint pInstance, 
  jfloat m00, jfloat m01, jfloat m02, jfloat m03, 
  jfloat m10, jfloat m11, jfloat m12, jfloat m13, 
  jfloat m20, jfloat m21, jfloat m22, jfloat m23, 
  jfloat m30, jfloat m31, jfloat m32, jfloat m33)
{
    Ogre::AxisAlignedBox *pAAB = (Ogre::AxisAlignedBox*)(pInstance);
    Ogre::Matrix4 m(m00, m01, m02, m03,
                    m10, m11, m12, m13,
                    m20, m21, m22, m23,
                    m30, m31, m32, m33);
    pAAB->transform(m);
}