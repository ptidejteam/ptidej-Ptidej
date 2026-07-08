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
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::Win32Window class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     08.05.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_Win32Window.h>
#include <OgreWin32Window.h>

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Win32Window_createInstance
  (JNIEnv *env, jclass that){return 0;}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Win32Window_create
  (JNIEnv * env, jclass that, jint pInstance, 
  jstring jsName, 
  jint jiWidth, 
  jint jiHeight, 
  jboolean jbFullScreen, 
  jstring jsTitle,
  jint jiColourDepth,
  jint jiLeft,
  jint jiTop,
  jboolean jbDepthBuffer,
  jint jiExternalWindowHandle,
  jint jiFSAA,
  jfloat jiDisplayFrequency,
  jboolean jiVsync)
{
    Ogre4JHelper::normalLogMsg( "-- Create Win32Window --");
    Ogre::String sName, sTitle;
    Ogre4JHelper::CopyJString(env, jsName, sName);
    Ogre4JHelper::CopyJString(env, jsTitle, sTitle);

    Ogre::NameValuePairList miscParam;
    if( sTitle.size() != 0 )  miscParam["title"]                = sTitle;
    if( jiColourDepth != -1 ) miscParam["colourDepth"]          = Ogre::StringConverter::toString(jiColourDepth);
    if( jiColourDepth != -1 ) miscParam["left"]                 = Ogre::StringConverter::toString(jiLeft);
    if( jiColourDepth != -1 ) miscParam["top"]                  = Ogre::StringConverter::toString(jiTop);
    if( jiColourDepth != -1 ) miscParam["FSAA"]                 = Ogre::StringConverter::toString(jiFSAA);
    if( jiColourDepth != -1 ) miscParam["displayFrequency"]     = Ogre::StringConverter::toString(jiDisplayFrequency);
    if( jiColourDepth != -1 ) miscParam["vsync"]                = Ogre::StringConverter::toString(jiVsync);    
    miscParam["depthBuffer"]          = jbDepthBuffer ? 1:0;
    Ogre::String sExtHWND = Ogre::StringConverter::toString(jiExternalWindowHandle);
    if( sExtHWND.compare( "0" ) ) miscParam["externalWindowHandle"] = sExtHWND;

    Ogre::Win32Window * wnd = GET_PTR(Ogre::Win32Window,pInstance);
    wnd->create( sName, jiWidth, jiHeight, jbFullScreen?true:false, &miscParam );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Win32Window_destroy
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Win32Window * wnd = GET_PTR(Ogre::Win32Window,pInstance);
    wnd->destroy();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Win32Window_resize
  (JNIEnv *env, jclass that, jint pInstance, jint jiWidth, jint jiHeight)
{
    Ogre::Win32Window * wnd = GET_PTR(Ogre::Win32Window,pInstance);
    wnd->resize( jiWidth, jiHeight );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Win32Window_reposition
  (JNIEnv *env, jclass that, jint pInstance, jint jiLeft, jint jiTop)
{
    Ogre::Win32Window * wnd = GET_PTR(Ogre::Win32Window,pInstance);
    wnd->reposition( jiLeft, jiTop );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jboolean JNICALL Java_org_ogre4j_Win32Window_isClosed
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Win32Window * wnd = GET_PTR(Ogre::Win32Window,pInstance);
    return wnd->isClosed();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Win32Window_writeContentsToFile
  (JNIEnv *env, jclass that, jint pInstance, jstring jsFileName )
{
    Ogre::Win32Window * wnd = GET_PTR(Ogre::Win32Window,pInstance);
    Ogre::String sFileName;
    Ogre4JHelper::CopyJString(env, jsFileName, sFileName);
    wnd->writeContentsToFile(sFileName);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_Win32Window_swapBuffers
  (JNIEnv *env, jclass that, jint pInstance, jboolean jbWaitForVSync )
{
    Ogre::Win32Window * wnd = GET_PTR(Ogre::Win32Window,pInstance);
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
JNIEXPORT jboolean JNICALL Java_org_ogre4j_Win32Window_requiresTextureFlipping
  (JNIEnv *env, jclass that, jint pInstance)
{
    Ogre::Win32Window * wnd = GET_PTR(Ogre::Win32Window,pInstance);
    return wnd->requiresTextureFlipping();
}