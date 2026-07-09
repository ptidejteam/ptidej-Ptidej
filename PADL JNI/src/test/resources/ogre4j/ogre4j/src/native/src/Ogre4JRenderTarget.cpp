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
/// @file Ogre4JRenderTarget.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::RenderTarget class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     08.05.2005
///
/// $Revision: 1.2 $
/// $Date: 2005/08/22 02:24:13 $
/// $Author: earl3982 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_RenderTarget.h>

using namespace Ogre4JHelper;

/// Viewport addViewport(Camera cam)
JNIEXPORT jint JNICALL Java_org_ogre4j_RenderTarget_addViewport__II
  (JNIEnv *env, jclass that, jint pInstance, jint pCam)
{
    Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
    Ogre::Camera * camera = GET_PTR(Ogre::Camera,pCam);
    Ogre::Viewport * viewport = target->addViewport(camera);
    RETURN_PTR(viewport);
}

/// Viewport addViewport(Camera cam, int ZOrder, float left, float top,
///            float width, float height)
JNIEXPORT jint JNICALL Java_org_ogre4j_RenderTarget_addViewport__IIIFFFF
  (JNIEnv *env, jclass that, jint pInstance, jint pCam, jint ZOrder, 
  jfloat left, jfloat right, jfloat width, jfloat height)
{
    Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
    Ogre::Camera * camera = GET_PTR(Ogre::Camera,pCam);
    Ogre::Viewport * viewport = target->addViewport(camera, ZOrder, left, right, width, height);
    RETURN_PTR(viewport);
}

/// float getAverageFPS()
JNIEXPORT jfloat JNICALL Java_org_ogre4j_RenderTarget_getAverageFPS
  (JNIEnv *, jclass, jint pInstance)
{
    Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
    return target->getAverageFPS();
}

/// float getBestFPS() 
JNIEXPORT jfloat JNICALL Java_org_ogre4j_RenderTarget_getBestFPS
  (JNIEnv *, jclass, jint pInstance)
{
    Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
    return target->getBestFPS();
}

/// float getBestFrameTime()
JNIEXPORT jfloat JNICALL Java_org_ogre4j_RenderTarget_getBestFrameTime
  (JNIEnv *, jclass, jint pInstance)
{
    Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
    return target->getBestFrameTime();
}

/// int getColourDepth()
JNIEXPORT jint JNICALL Java_org_ogre4j_RenderTarget_getColourDepth
  (JNIEnv *, jclass, jint pInstance)
{
	Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
	return target->getColourDepth();
}

/// String getDebugText()
JNIEXPORT jstring JNICALL Java_org_ogre4j_RenderTarget_getDebugText
  (JNIEnv * env, jclass, jint pInstance)
{
	Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
	return env->NewStringUTF(target->getDebugText().c_str());
}

///  int getHeight()
JNIEXPORT jint JNICALL Java_org_ogre4j_RenderTarget_getHeight
  (JNIEnv *, jclass, jint pInstance)
{
	Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
	return target->getHeight();
}

/// float getLastFPS()
JNIEXPORT jfloat JNICALL Java_org_ogre4j_RenderTarget_getLastFPS
  (JNIEnv *, jclass, jint pInstance)
{
    Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
    return target->getLastFPS();
}

/// String getName()
JNIEXPORT jstring JNICALL Java_org_ogre4j_RenderTarget_getName
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
    Ogre::String sName = target->getName();
    return env->NewStringUTF(sName.c_str());
}

/// int getNumViewports()
JNIEXPORT jint JNICALL Java_org_ogre4j_RenderTarget_getNumViewports
  (JNIEnv *, jclass, jint pInstance)
{
    Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
	return target->getNumViewports();
}

/// long getTriangleCount()
JNIEXPORT jlong JNICALL Java_org_ogre4j_RenderTarget_getTriangleCount
  (JNIEnv *, jclass, jint pInstance)
{
	Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
	return target->getTriangleCount();
}


/// int getWidth()
JNIEXPORT jint JNICALL Java_org_ogre4j_RenderTarget_getWidth
  (JNIEnv *, jclass, jint pInstance)
{
	Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
	return target->getWidth();
}

/// float getWorstFPS()
JNIEXPORT jfloat JNICALL Java_org_ogre4j_RenderTarget_getWorstFPS
  (JNIEnv *, jclass, jint pInstance)
{
    Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
    return target->getWorstFPS();
}

/// float getWorstFrameTime()
JNIEXPORT jfloat JNICALL Java_org_ogre4j_RenderTarget_getWorstFrameTime
  (JNIEnv *, jclass, jint pInstance)
{
    Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
    return target->getWorstFrameTime();
}

/// boolean isActive()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_RenderTarget_isActive
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
    return target->isActive();
}

/// boolean isAutoUpdated()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_RenderTarget_isAutoUpdated
  (JNIEnv *, jclass, jint pInstance)
{
    Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
	return target->isAutoUpdated();
}

/// boolean isPrimary()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_RenderTarget_isPrimary
  (JNIEnv *, jclass, jint pInstance)
{
	Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
	bool ret = target->isAutoUpdated();
	return (ret) ? JNI_TRUE : JNI_FALSE;
}

/// void removeAllListeners()
JNIEXPORT void JNICALL Java_org_ogre4j_RenderTarget_removeAllListeners
  (JNIEnv *, jclass, jint pInstance)
{
	Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
	target->removeAllListeners();
}

/// void removeAllViewports() 
JNIEXPORT void JNICALL Java_org_ogre4j_RenderTarget_removeAllViewports
  (JNIEnv *, jclass, jint pInstance)
{
	Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
	target->removeAllViewports();
}

/// void removeListener(RenderTargetListener listener)
JNIEXPORT void JNICALL Java_org_ogre4j_RenderTarget_removeListener
  (JNIEnv *, jclass, jint pInstance, jint ptrListener)
{
	Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
	Ogre::RenderTargetListener * listener = GET_PTR(Ogre::RenderTargetListener,ptrListener);
	
	target->removeListener(listener);
}

/// void removeViewport(int ZOrder)
JNIEXPORT void JNICALL Java_org_ogre4j_RenderTarget_removeViewport
  (JNIEnv *, jclass, jint pInstance, jint jZOrder)
{
	Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
	target->removeViewport(jZOrder);
}

/// boolean requiresTextureFlipping()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_RenderTarget_requiresTextureFlipping
  (JNIEnv *, jclass, jint pInstance)
{
    Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
	bool ret = target->requiresTextureFlipping();
	return (ret) ? JNI_TRUE : JNI_FALSE;
}

/// void resetStatistics()
JNIEXPORT void JNICALL Java_org_ogre4j_RenderTarget_resetStatistics
  (JNIEnv *, jclass, jint pInstance)
{
    Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
	target->resetStatistics();
}

/// void setActive(boolean state)
JNIEXPORT void JNICALL Java_org_ogre4j_RenderTarget_setActive
  (JNIEnv *, jclass, jint pInstance, jboolean jstate)
{
    Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
	bool state = (jstate == JNI_TRUE) ? true : false;
	target->setActive(state);
}

/// void setAutoUpdated(boolean autoupdate)
JNIEXPORT void JNICALL Java_org_ogre4j_RenderTarget_setAutoUpdated
  (JNIEnv *, jclass, jint pInstance, jboolean jautoupdate)
{
    Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
	bool autoupdate = (jautoupdate == JNI_TRUE) ? true : false;
	target->setAutoUpdated(autoupdate);
}

/// void setDebugText(String text)
JNIEXPORT void JNICALL Java_org_ogre4j_RenderTarget_setDebugText
  (JNIEnv * env, jclass, jint pInstance, jstring jtext)
{
   Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
   Ogre::String text;
   CopyJString(env, jtext, text);
   target->setDebugText(text);
}

/// void update()
JNIEXPORT void JNICALL Java_org_ogre4j_RenderTarget_update
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::RenderTarget * target = GET_PTR(Ogre::RenderTarget,pInstance);
    target->update();
}



