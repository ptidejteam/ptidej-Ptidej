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
/// @file Ogre4JHelper.cpp
/// Interface of the Ogre4JHelper namespace. 
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     07.05.2005
///
/// $Revision: 1.2 $
/// $Date: 2005/08/16 07:51:10 $
/// $Author: earl3982 $
///////////////////////////////////////////////////////////////////////////////
#ifndef __OGRE4J_HELPER_H__
#define __OGRE4J_HELPER_H__

#include <Ogre4J.h>

//-----------------------------------------------------------------------------
// MACROS
//-----------------------------------------------------------------------------
#define RETURN_PTR(p) return reinterpret_cast<jint>(p)
#define RETURN_BOOL(b) return static_cast<jboolean>(b)
#define GET_PTR(cl, p) reinterpret_cast<cl*>(p)
#define GET_REF(cl, p) reinterpret_cast<cl&>(p)

#define RETURN_VECTOR3F(vec) \
    jfloat values[3] = { vec.x, vec.y, vec.z}; \
    jfloatArray vector3 = env->NewFloatArray( 3 ); \
    env->SetFloatArrayRegion( vector3, 0, 3, values); \
    return vector3;
#define RETURN_QUAT4F(quat) \
    jfloat values[4] = { quat.x, quat.y, quat.z, quat.w}; \
    jfloatArray vector4 = env->NewFloatArray( 4 ); \
    env->SetFloatArrayRegion( vector4, 0, 4, values); \
    return vector4;
#define RETURN_COLOUR_VALUE(c)\
    jfloat values[4] = { c.r, c.g, c.b, c.a}; \
    jfloatArray array = env->NewFloatArray( 4 ); \
    env->SetFloatArrayRegion( array, 0, 4, values); \
    return array;

namespace Ogre4JHelper {
//-----------------------------------------------------------------------------
// FUNCTIONS
//-----------------------------------------------------------------------------
    void trivialLogMsg( Ogre::String const & mesg );
    void normalLogMsg( Ogre::String const & mesg );
    void criticalLogMsg( Ogre::String const & mesg );
    int CopyJString( JNIEnv * env, jstring jString, Ogre::String & sString );
    jfloatArray createJavaArray( JNIEnv *env, Ogre::Vector3 const & vec);
    jfloatArray createJavaArray( JNIEnv *env, Ogre::Quaternion const & quat);
    jfloatArray createJavaArray( JNIEnv *env, Ogre::ColourValue const & c );
    jfloatArray createJavaArray( JNIEnv *env, Ogre::Matrix4 const & mat);
}

#endif // __OGRE4J_HELPER_H__