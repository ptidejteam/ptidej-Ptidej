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
/// @file Ogre4JViewport.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::Viewport class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     07.05.2005
///
/// $Revision: 1.2 $
/// $Date: 2005/08/18 23:49:35 $
/// $Author: earl3982 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_Viewport.h>

/// int getActualHeight()
JNIEXPORT jint JNICALL Java_org_ogre4j_Viewport_getActualHeight
(JNIEnv *env, jclass, jint pInstance)
{
	Ogre::Viewport * pViewport = GET_PTR(Ogre::Viewport,pInstance);
	return pViewport->getActualHeight();
}

/// int getActualLeft()
JNIEXPORT jint JNICALL Java_org_ogre4j_Viewport_getActualLeft
(JNIEnv *env, jclass, jint pInstance)
{
	Ogre::Viewport * pViewport = GET_PTR(Ogre::Viewport,pInstance);
	return pViewport->getActualLeft();
}

/// int getActualTop()
JNIEXPORT jint JNICALL Java_org_ogre4j_Viewport_getActualTop
(JNIEnv *env, jclass, jint pInstance)
{
	Ogre::Viewport * pViewport = GET_PTR(Ogre::Viewport,pInstance);
	return pViewport->getActualTop();
}

/// int getActualWidth()
JNIEXPORT jint JNICALL Java_org_ogre4j_Viewport_getActualWidth
(JNIEnv *env, jclass, jint pInstance)
{
	Ogre::Viewport * pViewport = GET_PTR(Ogre::Viewport,pInstance);
	return pViewport->getActualWidth();
}

/// ColourValue getBackgroundColour()
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Viewport_getBackgroundColour
(JNIEnv *env, jclass, jint pInstance)
{
	Ogre::Viewport * pViewport = GET_PTR(Ogre::Viewport,pInstance);
	RETURN_COLOUR_VALUE(pViewport->getBackgroundColour())
}

/// Camera getCamera()
JNIEXPORT jint JNICALL Java_org_ogre4j_Viewport_getCamera
(JNIEnv *env, jclass, jint pInstance)
{
	Ogre::Viewport * pViewport = GET_PTR(Ogre::Viewport,pInstance);
	RETURN_PTR(pViewport->getCamera());
}

/// boolean getClearEveryFrame()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_Viewport_getClearEveryFrame
(JNIEnv *env, jclass, jint pInstance)
{
	Ogre::Viewport * pViewport = GET_PTR(Ogre::Viewport,pInstance);
	return pViewport->getClearEveryFrame();
}

/// float getHeight()
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Viewport_getHeight
(JNIEnv *env, jclass, jint pInstance)
{
	Ogre::Viewport * pViewport = GET_PTR(Ogre::Viewport,pInstance);
	return pViewport->getHeight();
}

/// float getLeft()
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Viewport_getLeft
(JNIEnv *env, jclass, jint pInstance)
{
	Ogre::Viewport * pViewport = GET_PTR(Ogre::Viewport,pInstance);
	return pViewport->getLeft();
}

/// boolean getOverlaysEnabled()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_Viewport_getOverlaysEnabled
(JNIEnv *env, jclass, jint pInstance)
{
	Ogre::Viewport * pViewport = GET_PTR(Ogre::Viewport,pInstance);
	return pViewport->getOverlaysEnabled();
}

/// RenderTarget getTarget()
JNIEXPORT jint JNICALL Java_org_ogre4j_Viewport_getTarget
  (JNIEnv *, jclass, jint pInstance)
{
	Ogre::Viewport * pViewport = GET_PTR(Ogre::Viewport,pInstance);
	Ogre::RenderTarget * ret = pViewport->getTarget();
	RETURN_PTR(ret);
}

/// float getTop()
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Viewport_getTop
(JNIEnv *env, jclass, jint pInstance)
{
	Ogre::Viewport * pViewport = GET_PTR(Ogre::Viewport,pInstance);
	return pViewport->getTop();
}

/// float getWidth()
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Viewport_getWidth
(JNIEnv *env, jclass, jint pInstance)
{
	Ogre::Viewport * pViewport = GET_PTR(Ogre::Viewport,pInstance);
	return pViewport->getLeft();
}

/// void setBackgroundColour(ColourValue color)
JNIEXPORT void JNICALL Java_org_ogre4j_Viewport_setBackgroundColour
(JNIEnv *, jclass, jint pInstance, jfloat jfR, jfloat jfG, jfloat jfB, jfloat jfA)
{
	Ogre::Viewport * pViewport = GET_PTR(Ogre::Viewport,pInstance);
	pViewport->setBackgroundColour( Ogre::ColourValue( jfR, jfG, jfB, jfA ) );
}

/// void setCamera(Camera cam)
JNIEXPORT void JNICALL Java_org_ogre4j_Viewport_setCamera
(JNIEnv *env, jclass, jint pInstance, jint pCInstance)
{
	Ogre::Viewport * pViewport = GET_PTR(Ogre::Viewport,pInstance);
	Ogre::Camera * pCamera = GET_PTR(Ogre::Camera,pCInstance);
	pViewport->setCamera(pCamera);
}

/// void setClearEveryFrame(boolean clear)
JNIEXPORT void JNICALL Java_org_ogre4j_Viewport_setClearEveryFrame
(JNIEnv *env, jclass, jint pInstance, jboolean clear)
{
	Ogre::Viewport * pViewport = GET_PTR(Ogre::Viewport,pInstance);
	pViewport->setClearEveryFrame(clear?true:false);
}

/// void setDimensions(float left, float top, float width, float height)
JNIEXPORT void JNICALL Java_org_ogre4j_Viewport_setDimensions
(JNIEnv *env, jclass, jint pInstance, jfloat l, jfloat t, jfloat w, jfloat h)
{
	Ogre::Viewport * pViewport = GET_PTR(Ogre::Viewport,pInstance);
	pViewport->setDimensions(l,t,w,h);
}

/// void setOverlaysEnabled(boolean enabled)
JNIEXPORT void JNICALL Java_org_ogre4j_Viewport_setOverlaysEnabled
  (JNIEnv *, jclass, jint ptrSelf, jboolean jenabled)
{
	Ogre::Viewport * pViewport = GET_PTR(Ogre::Viewport,ptrSelf);
	bool enabled = (jenabled == JNI_TRUE) ? true : false;
	pViewport->setOverlaysEnabled(enabled);
}

/// void update()
JNIEXPORT void JNICALL Java_org_ogre4j_Viewport_update
(JNIEnv *env, jclass, jint pInstance)
{
	Ogre::Viewport * pViewport = GET_PTR(Ogre::Viewport,pInstance);
	pViewport->update();
}



