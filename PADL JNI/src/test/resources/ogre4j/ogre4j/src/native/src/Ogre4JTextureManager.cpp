
#include <Ogre4J.h>
#include <org_ogre4j_TextureManager.h>

/// static TextureManager getSingleton()
JNIEXPORT jint JNICALL Java_org_ogre4j_TextureManager_getSingletonImpl
  (JNIEnv *, jclass)
{
	Ogre::TextureManager * ret = Ogre::TextureManager::getSingletonPtr();
	RETURN_PTR(ret);
}

/// void getDefaultNumMipmaps()
JNIEXPORT jlong JNICALL Java_org_ogre4j_TextureManager_getDefaultNumMipmaps
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::TextureManager * self = GET_PTR(Ogre::TextureManager, ptrSelf);
	return self->getDefaultNumMipmaps();
}

/// void enable32BitTextures(boolean setting)
JNIEXPORT void JNICALL Java_org_ogre4j_TextureManager_enable32BitTextures
  (JNIEnv *, jclass, jint ptrSelf, jboolean jsetting)
{
	Ogre::TextureManager * self = GET_PTR(Ogre::TextureManager, ptrSelf);
	bool setting = (jsetting == JNI_TRUE) ? true : false;
	self->enable32BitTextures(setting);
}

/// void setDefaultNumMipmaps(int num)
JNIEXPORT void JNICALL Java_org_ogre4j_TextureManager_setDefaultNumMipmaps
  (JNIEnv *, jclass, jint ptrSelf, jlong jnum)
{
	Ogre::TextureManager * self = GET_PTR(Ogre::TextureManager, ptrSelf);
	self->setDefaultNumMipmaps(jnum);
}


