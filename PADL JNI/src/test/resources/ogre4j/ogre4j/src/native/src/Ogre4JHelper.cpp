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
/// Implementation of Ogre4JHelper namespace.
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     07.05.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////

#include <Ogre4J.h>
#include <org_ogre4j_Helper.h>
#include <windows.h>
#include <commctrl.h>
#include <getdxversion.h>

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
void Ogre4JHelper::trivialLogMsg( Ogre::String const & msg )
{
#ifdef _DEBUG
    Ogre::LogManager::getSingleton().logMessage( "Ogre4J(C++): "+msg, Ogre::LML_TRIVIAL );
#endif
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
void Ogre4JHelper::normalLogMsg( Ogre::String const & msg )
{
#ifdef _DEBUG
    Ogre::LogManager::getSingleton().logMessage( "Ogre4J(C++): "+msg, Ogre::LML_NORMAL );
#endif
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
void Ogre4JHelper::criticalLogMsg( Ogre::String const & msg )
{
    Ogre::LogManager::getSingleton().logMessage( "Ogre4J(C++): "+msg, Ogre::LML_CRITICAL );
}
 
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
int Ogre4JHelper::CopyJString( JNIEnv * env, jstring jString, Ogre::String & sString )
{
    const char * sTemp = env->GetStringUTFChars( jString, 0 );
    sString = sTemp;
    env->ReleaseStringUTFChars(jString, sTemp );
    return (int)sString.size();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
jfloatArray Ogre4JHelper::createJavaArray( JNIEnv *env, Ogre::Vector3 const & vec)
{
    jfloat values[3] = { vec.x, vec.y, vec.z};
    jfloatArray vector3 = env->NewFloatArray( 3 );
    env->SetFloatArrayRegion( vector3, 0, 3, values);
    return vector3;
}
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
jfloatArray Ogre4JHelper::createJavaArray( JNIEnv *env, Ogre::Quaternion const & quat)
{
    jfloat values[4] = { quat.x, quat.y, quat.z, quat.w};
    jfloatArray vector4 = env->NewFloatArray( 4 );
    env->SetFloatArrayRegion( vector4, 0, 4, values);
    return vector4;
}
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
jfloatArray Ogre4JHelper::createJavaArray( JNIEnv *env, Ogre::ColourValue const & c )
{
    jfloat values[4] = { c.r, c.g, c.b, c.a};
    jfloatArray array = env->NewFloatArray( 4 );
    env->SetFloatArrayRegion( array, 0, 4, values);
    return array;
}
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
jfloatArray Ogre4JHelper::createJavaArray( JNIEnv *env, Ogre::Matrix4 const & mat)
{
    jfloat values[16] = { mat[0][0],  mat[0][1],  mat[0][2],  mat[0][3],
                          mat[1][0],  mat[1][1],  mat[1][2],  mat[1][3],
                          mat[2][0],  mat[2][1],  mat[2][2],  mat[2][3],
                          mat[3][0],  mat[3][1],  mat[3][2],  mat[3][3] };
    jfloatArray array = env->NewFloatArray( 16 );
    env->SetFloatArrayRegion( array, 0, 16, values);
    return array;
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jlong JNICALL Java_org_ogre4j_Helper__1getDirectXVersion
  (JNIEnv *env, jclass that)
{
    std::cout << "--- Ogre4JHelper::getDirectXVersion ---" << std::endl;
    DWORD dwDirectXVersion = 0; 
    TCHAR strDirectXVersion[10];
    HRESULT hr = GetDXVersion( &dwDirectXVersion, strDirectXVersion, 10 );
    Ogre4JHelper::trivialLogMsg("--- Helper: DirectX Version " + Ogre::StringConverter::toString(dwDirectXVersion) );
    return static_cast<jlong>(dwDirectXVersion);
}
