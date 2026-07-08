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
/// Ogre::D3D9RenderWindow class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     07.05.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <OgreD3D9RenderWindow.h>
#include <org_ogre4j_D3D9RenderWindow.h>

#if 0
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_D3D9RenderWindow_createInstance
  (JNIEnv * env, jclass that)
{
    Ogre4JHelper::normalLogMsg( "-- Create D3D9RenderWindow Instance --" );
    if (tlsIndex == 0) 
    {
        tlsIndex = TlsAlloc();
        if (tlsIndex == -1) return (jint) 0;
        javaClass = env->NewGlobalRef( static_cast<jobject>(that) );
    }
    TlsSetValue(tlsIndex, (LPVOID) env);

    Ogre::D3D9RenderWindow * renderWnd; //= new Ogre::D3D9RenderWindow();
    RETURN_PTR(renderWnd);
}
#endif
///////////////////////////////////////////////////////////////////////////////
/// @param name                 The name of the window. Used in other methods later like setRenderTarget and getRenderWindow.
/// @param width                The width of the new window. 
/// @param height               The height of the new window. 
/// @param fullScreen           Specify true to make the window full screen without borders, title bar or menu bar. 
/// @param title                The title of the window that will appear in the title bar Values: string Default: RenderTarget name
/// @param colourDepth          Colour depth of the resulting rendering window; only applies if fullScreen is set. Values: 16 or 32 Default: desktop depth Notes: [W32 specific]
/// @param left                 screen x coordinate from left Values: positive integers Default: 'center window on screen' Notes: Ignored in case of full screen
/// @param top                  screen y coordinate from top Values: positive integers Default: 'center window on screen' Notes: Ignored in case of full screen
/// @param depthBuffer          Use depth buffer Values: false or true Default: true
/// @param externalWindowHandle External window handle, for embedding the OGRE context Values: positive integer for W32 (HWND handle) posint:posint:posint for GLX (display:screen:windowHandle) Default: 0 (None)
/// @param parentWindowHandle   Parent window handle, for embedding the OGRE context Values: positive integer for W32 (HWND handle) posint:posint:posint for GLX (display:screen:windowHandle) Default: 0 (None)
/// @param FSAA                 Full screen antialiasing factor Values: 0,2,4,6,... Default: 0
/// @param displayFrequency     Display frequency rate, for fullscreen mode Values: 60...? Default: Desktop vsync rate
/// @param vsync                Synchronize buffer swaps to vsync Values: true, false Default: 0
// @return                     Pointer to a object of type Ogre::D3D9RenderWindow
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_D3D9RenderWindow_createRenderWindow
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
    Ogre4JHelper::normalLogMsg( "-- Create D3D9Renderwindow --");
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

    Ogre::D3D9RenderWindow * renderWnd = GET_PTR(Ogre::D3D9RenderWindow,pInstance);
    renderWnd->create( sName, jiWidth, jiHeight, jbFullScreen?true:false, &miscParam );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_D3D9RenderWindow_destroy
  (JNIEnv * env, jclass that, jint pInstance)
{
    Ogre4JHelper::normalLogMsg( "-- Destroy D3D9Renderwindow --");
    Ogre::D3D9RenderWindow * renderWnd = GET_PTR( Ogre::D3D9RenderWindow,pInstance );
    renderWnd->destroy();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_D3D9RenderWindow_resize
  (JNIEnv * env, jclass that, jint pInstance, jint jiWidth, jint jiHeight)
{
    Ogre4JHelper::normalLogMsg( "-- Resize D3D9Renderwindow --");
    Ogre::D3D9RenderWindow * renderWnd = GET_PTR( Ogre::D3D9RenderWindow,pInstance );
    renderWnd->resize( jiWidth, jiHeight );
}
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_D3D9RenderWindow_reposition
  (JNIEnv * env, jclass that, jint pInstance, jint jiLeft, jint jiTop)
{
    Ogre4JHelper::normalLogMsg( "-- Reposition D3D9Renderwindow --");
    Ogre::D3D9RenderWindow * renderWnd = GET_PTR( Ogre::D3D9RenderWindow,pInstance );
    renderWnd->reposition( jiLeft, jiTop );
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jboolean JNICALL Java_org_ogre4j_D3D9RenderWindow_isClosed
  (JNIEnv * env, jclass that, jint pInstance)
{
    Ogre::D3D9RenderWindow * wnd = GET_PTR(Ogre::D3D9RenderWindow,pInstance);
    return wnd->isClosed();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_D3D9RenderWindow_update
  (JNIEnv *, jclass, jint pInstance)
{
    Ogre4JHelper::normalLogMsg( "-- Update D3D9Renderwindow --");
    Ogre::D3D9RenderWindow * renderWnd = GET_PTR( Ogre::D3D9RenderWindow, pInstance );
    renderWnd->update();
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_D3D9RenderWindow_writeContentsToFile
  (JNIEnv * env, jclass that, jint pInstance, jstring jsFileName)
{
        Ogre::D3D9RenderWindow * wnd = GET_PTR(Ogre::D3D9RenderWindow,pInstance);
    Ogre::String sFileName;
    Ogre4JHelper::CopyJString(env, jsFileName, sFileName);
    wnd->writeContentsToFile(sFileName);
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT void JNICALL Java_org_ogre4j_D3D9RenderWindow_swapBuffers
  (JNIEnv * env, jclass that, jint pInstance, jboolean jbWaitForVSync)
{
    Ogre::D3D9RenderWindow * wnd = GET_PTR(Ogre::D3D9RenderWindow,pInstance);
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
JNIEXPORT jboolean JNICALL Java_org_ogre4j_D3D9RenderWindow_requiresTextureFlipping
  (JNIEnv * env, jclass that, jint pInstance)
{
    Ogre::D3D9RenderWindow * wnd = GET_PTR(Ogre::D3D9RenderWindow,pInstance);
    return wnd->requiresTextureFlipping();
}