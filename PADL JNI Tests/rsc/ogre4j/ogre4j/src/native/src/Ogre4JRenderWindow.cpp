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
/// @file Ogre4JRenderWindowt.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::RenderWindow class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     08.05.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_RenderWindow.h>

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_RenderWindow_update
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::RenderWindow * rWindow = GET_PTR(Ogre::RenderWindow,pInstance);
    rWindow->update();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jboolean JNICALL Java_org_ogre4j_RenderWindow_isPrimary
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::RenderWindow * rWindow = GET_PTR(Ogre::RenderWindow,pInstance);
    return rWindow->isPrimary();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_RenderWindow_destroy
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::RenderWindow * wnd = GET_PTR(Ogre::RenderWindow,pInstance);
    wnd->destroy();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_RenderWindow_resize
  (JNIEnv *env, jclass that, jint pInstance, jint jiWidth, jint jiHeight)
{
    Ogre::RenderWindow * wnd = GET_PTR(Ogre::RenderWindow,pInstance);
    wnd->resize( jiWidth, jiHeight );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_RenderWindow_reposition
  (JNIEnv *env, jclass that, jint pInstance, jint jiLeft, jint jiTop)
{
    Ogre::RenderWindow * wnd = GET_PTR(Ogre::RenderWindow,pInstance);
    wnd->reposition( jiLeft, jiTop );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jboolean JNICALL Java_org_ogre4j_RenderWindow_isClosed
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::RenderWindow * wnd = GET_PTR(Ogre::RenderWindow,pInstance);
    return wnd->isClosed();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_RenderWindow_swapBuffers
  (JNIEnv *env, jclass that, jint pInstance, jboolean jbWaitForVSync )
{
    Ogre::RenderWindow * wnd = GET_PTR(Ogre::RenderWindow,pInstance);
    try
    {
        wnd->swapBuffers(jbWaitForVSync?true:false);
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
JNIEXPORT jboolean JNICALL Java_org_ogre4j_RenderWindow_requiresTextureFlipping
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::RenderWindow * wnd = GET_PTR(Ogre::RenderWindow,pInstance);
    return wnd->requiresTextureFlipping();
}
