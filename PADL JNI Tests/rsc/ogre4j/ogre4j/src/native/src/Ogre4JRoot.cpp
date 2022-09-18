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
/// @file Ogr4JRoot.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::D3D9RenderWindow class
///
/// @author   Kai Klesatschke <yavin@ogre4j.org>
/// @date     07.05.2005
///
/// $Revision: 1.6 $
/// $Date: 2005/08/26 06:52:30 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_Root.h>

using namespace Ogre4JHelper;

/// void addFrameListener(FrameListener newListener)
JNIEXPORT void JNICALL Java_org_ogre4j_Root_addFrameListener
  (JNIEnv *, jclass, jint ptrSelf, jint ptrNewListener)
{
	Ogre::Root *self = GET_PTR(Ogre::Root, ptrSelf);
	Ogre::FrameListener *newListener = GET_PTR(Ogre::FrameListener, ptrNewListener);
	self->addFrameListener(newListener);
}

/// void addResourceLocation(String name, String locType, String groupName, boolean recursive)
JNIEXPORT void JNICALL Java_org_ogre4j_Root_addResourceLocation
  (JNIEnv * env, jclass, jint ptrSelf, jstring jname, jstring jlocType, jstring jgroupName, jboolean jrecursive)
{
	Ogre::Root *self = GET_PTR(Ogre::Root, ptrSelf);
	Ogre::String name, locType, groupName;
	CopyJString(env, jname, name);
	CopyJString(env, jlocType, locType);
	CopyJString(env, jgroupName, groupName);
	bool recursive = (jrecursive == JNI_TRUE) ? true : false;
	self->addResourceLocation(name, locType, groupName, recursive);
}

/// void clearEventTimes();
JNIEXPORT void JNICALL Java_org_ogre4j_Root_clearEventTimes
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Root *self = GET_PTR(Ogre::Root, ptrSelf);
	self->clearEventTimes();
}

/// createCppInstance(String pluginFileName, String configFileName, String logFileName)
JNIEXPORT jint JNICALL Java_org_ogre4j_Root_createCppInstance
  (JNIEnv * env, jclass, jstring jpluginFileName, jstring jconfigFileName, jstring jlogFileName)
{
	Ogre::String pluginFileName, configFileName, logFileName;
	CopyJString(env, jpluginFileName, pluginFileName);
	CopyJString(env, jconfigFileName, configFileName);
	CopyJString(env, jlogFileName, logFileName);
	Ogre::Root * ret = new Ogre::Root(pluginFileName, configFileName, logFileName);
	RETURN_PTR(ret);
}


///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Root_createInstance__
(JNIEnv *env, jclass that)
{
	std::cout << "-- Create Root Instance --" << std::endl;
	try
	{
		Ogre::Root * root = new Ogre::Root();
		RETURN_PTR( root );
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
JNIEXPORT jint JNICALL Java_org_ogre4j_Root_createInstance__Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2
( JNIEnv * env, jclass that, jstring jsPluginFileName, jstring jsConfigFileName, jstring jsLogFileName )
{
	std::cout << "-- Create Root Instance --" << std::endl;
	Ogre::String sPluginFileName, sConfigFileName, sLogFileName;
	Ogre4JHelper::CopyJString(env, jsPluginFileName, sPluginFileName );
	Ogre4JHelper::CopyJString(env, jsConfigFileName, sConfigFileName );
	Ogre4JHelper::CopyJString(env, jsLogFileName, sLogFileName );
	try
	{
		Ogre::Root * root = new Ogre::Root( sPluginFileName, sConfigFileName, sLogFileName );
		RETURN_PTR( root );
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
JNIEXPORT jint JNICALL Java_org_ogre4j_Root__1getSingleton
(JNIEnv *env, jclass)
{
	try
	{
		Ogre::Root * pRoot = Ogre::Root::getSingletonPtr();
		RETURN_PTR(pRoot);
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
JNIEXPORT void JNICALL Java_org_ogre4j_Root_dispose
(JNIEnv * env, jclass that, jint pInstance )
{
	try
	{
		Ogre4JHelper::normalLogMsg( "-- Dispose Root --" );
		Ogre::Root * root = GET_PTR(Ogre::Root,pInstance);
		delete root;
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
JNIEXPORT jint JNICALL Java_org_ogre4j_Root_initialise
(JNIEnv * env, jclass that, jint pInstance, jboolean jbAutoCreateWindow, jstring jsWindowTitle)
{
	Ogre4JHelper::normalLogMsg( "-- Initialize Root --" );

	std::cout << "blah ffff" << std::endl;
	Ogre::Root * root = GET_PTR(Ogre::Root,pInstance);
	Ogre::String sWindowsTitle;
	Ogre4JHelper::CopyJString(env, jsWindowTitle, sWindowsTitle );
	Ogre::RenderWindow * rWnd = root->initialise( jbAutoCreateWindow?true:false, sWindowsTitle );
	RETURN_PTR( rWnd );
}
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
/// @return                     Pointer to a object of type Ogre::RenderWindow
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jint JNICALL Java_org_ogre4j_Root_createRenderWindow
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
 jint jiParentWindowHandle,
 jint jiFSAA,
 jfloat jiDisplayFrequency,
 jboolean jiVsync)
{
	try
	{
		Ogre4JHelper::normalLogMsg( "-- Create RenderWindow --" );
		Ogre::Root * root = GET_PTR(Ogre::Root,pInstance);
		Ogre::String sName, sTitle;
		Ogre4JHelper::CopyJString( env, jsName, sName );
		Ogre4JHelper::CopyJString( env, jsTitle, sTitle );

		Ogre::NameValuePairList miscParam;
		if( sTitle.size() != 0 )  miscParam["title"]                = sTitle;
		//    if( jiColourDepth != -1 ) miscParam["colourDepth"]          = Ogre::StringConverter::toString(jiColourDepth);
		//    if( jiColourDepth != -1 ) miscParam["left"]                 = Ogre::StringConverter::toString(jiLeft);
		//    if( jiColourDepth != -1 ) miscParam["top"]                  = Ogre::StringConverter::toString(jiTop);
		//    if( jiColourDepth != -1 ) miscParam["FSAA"]                 = Ogre::StringConverter::toString(jiFSAA);
		//    if( jiColourDepth != -1 ) miscParam["displayFrequency"]     = Ogre::StringConverter::toString(jiDisplayFrequency);
		//    if( jiColourDepth != -1 ) miscParam["vsync"]                = Ogre::StringConverter::toString(jiVsync);    
		//    miscParam["depthBuffer"]          = Ogre::StringConverter::toString(jbDepthBuffer ? 1:0);

		Ogre::String sExtHWND = Ogre::StringConverter::toString(jiExternalWindowHandle);
		if( sExtHWND.compare( "0" ) ) miscParam["externalWindowHandle"] = sExtHWND;

		Ogre::String sParentHWND = Ogre::StringConverter::toString(jiParentWindowHandle);
		if( sParentHWND.compare( "0" ) ) miscParam["parentWindowHandle"] = sParentHWND;


		Ogre::RenderWindow * rWnd = root->createRenderWindow( sName, jiWidth, jiHeight, jbFullScreen?true:false, &miscParam );
		RETURN_PTR( rWnd );
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


/// String Root.getErrorDescription(long errorNumber)
JNIEXPORT jstring JNICALL Java_org_ogre4j_Root_getErrorDescription
(JNIEnv * env, jclass, jint ptrSelf, jlong jErrorNumber)
{
	Ogre::Root *self = GET_PTR(Ogre::Root, ptrSelf);
	Ogre::String string = self->getErrorDescription(jErrorNumber);

	jstring ret = env->NewStringUTF(string.c_str());
	return ret;
}

/// SceneManager Root.getSceneManager(SceneType sType)
JNIEXPORT jint JNICALL Java_org_ogre4j_Root_getSceneManager
(JNIEnv * env, jclass that, jint pInstance, jint jiSceneType)
{
	Ogre4JHelper::normalLogMsg( "-- Get SceneManager, Type:" + Ogre::StringConverter::toString(jiSceneType) + "--" );
	Ogre::Root * root = GET_PTR(Ogre::Root,pInstance);
	Ogre::SceneManager * sceneMgr = root->getSceneManager( static_cast<Ogre::SceneType>(jiSceneType) );
	RETURN_PTR(sceneMgr);
}

/// void Root.loadPlugin(String pluginName)
JNIEXPORT void JNICALL Java_org_ogre4j_Root_loadPlugin
(JNIEnv *env, jclass that, jint pInstance, jstring jsPluginName)
{    
	Ogre::String sPluginName;
	Ogre4JHelper::CopyJString(env, jsPluginName, sPluginName);
	Ogre4JHelper::normalLogMsg( "-- Root: loadPlugin " + sPluginName + " --");
	Ogre::Root * root = GET_PTR(Ogre::Root,pInstance);
	try
	{
		root->loadPlugin(sPluginName);
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

/// void Root.queueEndRendering()
JNIEXPORT void JNICALL Java_org_ogre4j_Root_queueEndRendering
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Root *self = GET_PTR(Ogre::Root, ptrSelf);
	self->queueEndRendering();
}

/// boolean Root.renderOneFrame()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_Root_renderOneFrame
(JNIEnv *, jclass, jint ptrSelf)
{
    try
    {
	    Ogre::Root *self = GET_PTR(Ogre::Root, ptrSelf);
	    return (self->renderOneFrame()) ? JNI_TRUE : JNI_FALSE;
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

/// void Root.setRenderSystem(RenderSystem system)
JNIEXPORT void JNICALL Java_org_ogre4j_Root_setRenderSystem
(JNIEnv *env, jclass that, jint pInstance, jint pRenderSystem)
{
	Ogre::Root * root = GET_PTR(Ogre::Root,pInstance);
	Ogre::RenderSystem * renderSystem = GET_PTR(Ogre::RenderSystem, pRenderSystem);
	try
	{
		root->setRenderSystem(renderSystem);
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

/// boolean showConfigDialog()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_Root_showConfigDialog
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Root *self = GET_PTR(Ogre::Root, ptrSelf);
	return (self->showConfigDialog()) ? JNI_TRUE : JNI_FALSE;
}

/// void startRendering()
JNIEXPORT void JNICALL Java_org_ogre4j_Root_startRendering
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Root *self = GET_PTR(Ogre::Root, ptrSelf);
	self->startRendering();
}


/// void shutdown(void)
JNIEXPORT void JNICALL Java_org_ogre4j_Root_shutdown
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Root *self = GET_PTR(Ogre::Root, ptrSelf);
	self->shutdown();
}

/// void unloadPlugin(String pluginName)
JNIEXPORT void JNICALL Java_org_ogre4j_Root_unloadPlugin
(JNIEnv *env, jclass that, jint pInstance, jstring jsPluginName)
{
	Ogre::String sPluginName;
	Ogre4JHelper::CopyJString(env, jsPluginName, sPluginName);
	Ogre4JHelper::normalLogMsg( "-- Root: unloadPlugin " + sPluginName + " --");
	Ogre::Root * root = GET_PTR(Ogre::Root,pInstance);
	try
	{
		root->unloadPlugin(sPluginName);
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
/// Retrieves the list of available render systems from
/// Ogre::Root::getAvailableRendereres(void). If the lists pointer is not 0 and
/// is not empty the pointers and their types will be put in a new Java int array.
/// @see Ogre::Root::getAvailableRenderers(void)
/// @param[in] env          JNI environment pointer.
/// @param[in] that         org.ogre4j.Root
/// @param[in] pInstance    Root*
/// @return    Returns a Java int array which contains the pointers to the
///            available render systems and their types. If no render systems
///            available no array will be returned.
///////////////////////////////////////////////////////////////////////////////
JNIEXPORT jintArray JNICALL Java_org_ogre4j_Root_getAvailableRenderes
(JNIEnv *env, jclass that, jint pInstance)
{
	enum Type{DIRECTX7, DIRECTX9, OPENGL};

	Ogre::Root * root = GET_PTR(Ogre::Root,pInstance);
	Ogre::RenderSystemList * list = root->getAvailableRenderers();
	if(list == 0 || list->empty() )
		return 0;
	jsize size = list->size()*2;
	jint * objects = new jint[size];

	for(size_t i=0; i<size; i=i+2)
	{
		Ogre::RenderSystem * renderSystem = list->at(i);
		objects[i] = reinterpret_cast<jint>(renderSystem);
		Ogre::String rName = renderSystem->getName();

		if (rName.compare("Direct3D7 Rendering Subsystem") == 0)
		{
			objects[i+1] = DIRECTX7;
		}
		else if (rName.compare("Direct3D9 Rendering SubSystem") == 0)
		{
			objects[i+1] = DIRECTX9;
		}
		else if (rName.compare("OpenGL Rendering Subsystem") == 0)
		{
			objects[i+1] = OPENGL;
		}
	}

	jintArray objectsArray = env->NewIntArray( size );
	env->SetIntArrayRegion( objectsArray, 0, size, objects);
	delete [] objects;
	return objectsArray;
}

