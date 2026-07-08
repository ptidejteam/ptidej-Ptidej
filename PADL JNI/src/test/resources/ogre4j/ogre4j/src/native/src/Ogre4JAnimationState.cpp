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
/// @file Ogre4JAnimationState.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::AnimationState class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     07.05.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_AnimationState.h>
/*
static DWORD tlsIndex = 0;
static jobject javaClass;

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_AnimationState_createInstance
  (JNIEnv *env, jclass that, jstring jsAnimName, jfloat jfTimePos, jfloat jfLength, jfloat jfWeigth, jboolean jbEnabled)
{
    std::cout << "-- Create Root Instance --" << std::endl;
    if (tlsIndex == 0) 
    {
        tlsIndex = TlsAlloc();
        if (tlsIndex == -1) return (jint) 0;
        javaClass = env->NewGlobalRef( static_cast<jobject>(that) );
    }
    TlsSetValue(tlsIndex, (LPVOID) env);
    
    Ogre::String sAnimName;
    Ogre4JHelper::CopyJString( env, jsAnimName, sAnimName );
    Ogre::AnimationState * pAnimState = new Ogre::AnimationState( sAnimName, jfTimePos, jfLength, jfWeigth, jbEnabled?true:false);
    RETURN_PTR(pAnimState);
}
*/
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_AnimationState_dispose
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::AnimationState * pAnimState = GET_PTR(Ogre::AnimationState, pInstance);
    delete pAnimState;
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jstring JNICALL Java_org_ogre4j_AnimationState_getAnimationName
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::AnimationState * pAnimState = GET_PTR(Ogre::AnimationState, pInstance);
    return env->NewStringUTF( pAnimState->getAnimationName().c_str() );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_AnimationState_setAnimationName
  (JNIEnv *env, jclass that, jint pInstance, jstring jsName)
{
    Ogre::AnimationState * pAnimState = GET_PTR(Ogre::AnimationState, pInstance);
    Ogre::String sName;
    Ogre4JHelper::CopyJString(env, jsName, sName );
    pAnimState->setAnimationName( sName );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloat JNICALL Java_org_ogre4j_AnimationState_getTimePosition
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::AnimationState * pAnimState = GET_PTR(Ogre::AnimationState, pInstance);
    return pAnimState->getTimePosition();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_AnimationState_setTimePosition
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfTimePos)
{
    Ogre::AnimationState * pAnimState = GET_PTR(Ogre::AnimationState, pInstance);
    pAnimState->setTimePosition( jfTimePos );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloat JNICALL Java_org_ogre4j_AnimationState_getLength
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::AnimationState * pAnimState = GET_PTR(Ogre::AnimationState, pInstance);
    return pAnimState->getLength();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_AnimationState_setLength
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfLength)
{
    Ogre::AnimationState * pAnimState = GET_PTR(Ogre::AnimationState, pInstance);
    pAnimState->setLength( jfLength );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jfloat JNICALL Java_org_ogre4j_AnimationState_getWeight
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::AnimationState * pAnimState = GET_PTR(Ogre::AnimationState, pInstance);
    return pAnimState->getWeight();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_AnimationState_setWeight
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfWeight)
{
    Ogre::AnimationState * pAnimState = GET_PTR(Ogre::AnimationState, pInstance);
    pAnimState->setWeight( jfWeight );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_AnimationState_addTime
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfOffset)
{
    Ogre::AnimationState * pAnimState = GET_PTR(Ogre::AnimationState, pInstance);
    pAnimState->addTime( jfOffset );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jboolean JNICALL Java_org_ogre4j_AnimationState_getEnabled
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::AnimationState * pAnimState = GET_PTR(Ogre::AnimationState, pInstance);
    return pAnimState->getEnabled();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_AnimationState_setEnabled
  (JNIEnv *env, jclass that, jint pInstance, jboolean jbEnabled)
{
    Ogre::AnimationState * pAnimState = GET_PTR(Ogre::AnimationState, pInstance);
    pAnimState->setEnabled( jbEnabled?true:false );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_AnimationState_setLoop
  (JNIEnv *env, jclass, jint pInstance, jboolean jbLoop)
{
    Ogre::AnimationState * pAnimState = GET_PTR(Ogre::AnimationState, pInstance);
    pAnimState->setLoop(jbLoop?true:false);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jboolean JNICALL Java_org_ogre4j_AnimationState_getLoop
  (JNIEnv *env, jclass, jint pInstance)
{
    Ogre::AnimationState * pAnimState = GET_PTR(Ogre::AnimationState, pInstance);
    return pAnimState->getLoop();
}