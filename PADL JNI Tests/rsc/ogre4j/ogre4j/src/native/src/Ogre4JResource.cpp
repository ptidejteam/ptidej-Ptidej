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
/// @date     08.06.2005
///
/// $Revision: 1.2 $
/// $Date: 2005/08/15 07:34:53 $
/// $Author: earl3982 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_Resource.h>

using namespace Ogre4JHelper;

/// void Resource._notifyOrigin()
JNIEXPORT void JNICALL Java_org_ogre4j_Resource__1notifyOrigin
  (JNIEnv * env, jclass, jint ptrSelf, jstring jsOrigin)
{
	Ogre::Resource * self= GET_PTR(Ogre::Resource, ptrSelf);
	Ogre::String origin;
	CopyJString(env, jsOrigin, origin);
	self->_notifyOrigin(origin);
}

/// int Resource.getCreator()
JNIEXPORT jint JNICALL Java_org_ogre4j_Resource_getCreator
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Resource * self= GET_PTR(Ogre::Resource, ptrSelf);
	Ogre::ResourceManager * manager = self->getCreator();
	RETURN_PTR(manager);
}


/// String Resource.getGroup()
JNIEXPORT jstring JNICALL Java_org_ogre4j_Resource_getGroup
(JNIEnv * env, jclass, jint ptrSelf)
{
	Ogre::Resource * self= GET_PTR(Ogre::Resource, ptrSelf);
	Ogre::String ret = self->getGroup();
	return env->NewStringUTF(ret.c_str());
}


/// int Resource.getHandle()
JNIEXPORT jint JNICALL Java_org_ogre4j_Resource_getHandle
(JNIEnv * env, jclass, jint ptrSelf)
{
	Ogre::Resource * self= GET_PTR(Ogre::Resource, ptrSelf);
	return self->getHandle();
}

/// String Resource.getName()
JNIEXPORT jstring JNICALL Java_org_ogre4j_Resource_getName
(JNIEnv * env, jclass, jint ptrSelf)
{
	Ogre::Resource * self= GET_PTR(Ogre::Resource, ptrSelf);
	Ogre::String ret = self->getName();
	return env->NewStringUTF(ret.c_str());
}

/// String Resource.getOrigin()
JNIEXPORT jstring JNICALL Java_org_ogre4j_Resource_getOrigin
(JNIEnv * env, jclass, jint ptrSelf)
{
	Ogre::Resource * self= GET_PTR(Ogre::Resource, ptrSelf);
	Ogre::String ret = self->getOrigin();
	return env->NewStringUTF(ret.c_str());
}

/// int Resource.getSize()
JNIEXPORT jint JNICALL Java_org_ogre4j_Resource_getSize
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Resource * self= GET_PTR(Ogre::Resource, ptrSelf);
	return self->getSize();
}

/// boolean Resource.isLoaded()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_Resource_isLoaded
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Resource * self= GET_PTR(Ogre::Resource, ptrSelf);
	bool ret = self->isLoaded();
	return (ret) ? JNI_TRUE : JNI_FALSE;
}

/// boolean Resource.isManuallyLoaded()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_Resource_isManuallyLoaded
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Resource * self= GET_PTR(Ogre::Resource, ptrSelf);
	bool ret = self->isManuallyLoaded();
	return (ret) ? JNI_TRUE : JNI_FALSE;
}

/// void Resource.load()
JNIEXPORT void JNICALL Java_org_ogre4j_Resource_load
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Resource * self= GET_PTR(Ogre::Resource, ptrSelf);
	self->load();
}

/// void Resource.reload()
JNIEXPORT void JNICALL Java_org_ogre4j_Resource_reload
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Resource * self= GET_PTR(Ogre::Resource, ptrSelf);
	self->reload();
}

/// void Resource.touch()
JNIEXPORT void JNICALL Java_org_ogre4j_Resource_touch
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Resource * self= GET_PTR(Ogre::Resource, ptrSelf);
	self->touch();
}

/// void Resource.unload()
JNIEXPORT void JNICALL Java_org_ogre4j_Resource_unload
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Resource * self= GET_PTR(Ogre::Resource, ptrSelf);
	self->unload();
}
