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
/// @file Ogre4JD3D9RenderWindow.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::KeyFrame class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     09.05.2005
///
/// $Revision: 1.2 $
/// $Date: 2005/08/18 23:49:35 $
/// $Author: earl3982 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_KeyFrame.h>


/// Orientation getOrientation()
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_KeyFrame_getRotation
  (JNIEnv * env, jclass, jint ptrSelf)
{
	Ogre::KeyFrame * self = GET_PTR(Ogre::KeyFrame, ptrSelf);
	Ogre::Quaternion ret = self->getRotation();
	RETURN_VECTOR3F(ret);
}

/// float getTime()
JNIEXPORT jfloat JNICALL Java_org_ogre4j_KeyFrame_getTime
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::KeyFrame * self = GET_PTR(Ogre::KeyFrame, ptrSelf);
	return self->getTime();
} 

/// Vector3 getScale()
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_KeyFrame_getScale
  (JNIEnv * env, jclass, jint ptrSelf)
{
	Ogre::KeyFrame * self = GET_PTR(Ogre::KeyFrame, ptrSelf);
	Ogre::Vector3 ret = self->getScale();
	RETURN_VECTOR3F(ret);
}

/// Vector3 getTranslate()
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_KeyFrame_getTranslate
(JNIEnv * env, jclass, jint ptrSelf)
{
	Ogre::KeyFrame * self = GET_PTR(Ogre::KeyFrame, ptrSelf);
	Ogre::Vector3 ret = self->getTranslate();
	RETURN_VECTOR3F(ret);
}

/// void setRotation(Quaternion rot)
JNIEXPORT void JNICALL Java_org_ogre4j_KeyFrame_setRotation
(JNIEnv *, jclass, jint ptrSelf, jfloat jw, jfloat jx, jfloat jy, jfloat jz)
{
	Ogre::KeyFrame * self = GET_PTR(Ogre::KeyFrame, ptrSelf);
	self->setRotation(Ogre::Quaternion(jw, jx, jy, jz));
}

/// void setScale(Vector3 scale)
JNIEXPORT void JNICALL Java_org_ogre4j_KeyFrame_setScale
(JNIEnv *, jclass, jint ptrSelf, jfloat jx, jfloat jy, jfloat jz)
{
	Ogre::KeyFrame * self = GET_PTR(Ogre::KeyFrame, ptrSelf);
	self->setScale(Ogre::Vector3(jx, jy, jz));
}

/// void setTranslate(Vector3 translate)
JNIEXPORT void JNICALL Java_org_ogre4j_KeyFrame_setTranslate
(JNIEnv *, jclass, jint ptrSelf, jfloat jx, jfloat jy, jfloat jz)
{
	Ogre::KeyFrame * self = GET_PTR(Ogre::KeyFrame, ptrSelf);
	self->setTranslate(Ogre::Vector3(jx, jy, jz));
}

