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
/// @file Ogre4JAnimationTrack.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::AnimationTrack class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     09.05.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_AnimationTrack.h>

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_AnimationTrack_createKeyFrame
  (JNIEnv *env, jclass that, jint pInstance, jfloat jfTimePos)
{
    Ogre::AnimationTrack * pAnimTrack = GET_PTR(Ogre::AnimationTrack,pInstance);
    Ogre::KeyFrame * pKeyFrame = pAnimTrack->createKeyFrame( jfTimePos );
    RETURN_PTR(pKeyFrame);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_AnimationTrack_getAssociatedNode
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::AnimationTrack * pAnimTrack = GET_PTR(Ogre::AnimationTrack,pInstance);
    Ogre::Node * pNode = pAnimTrack->getAssociatedNode();
    RETURN_PTR(pNode);
}