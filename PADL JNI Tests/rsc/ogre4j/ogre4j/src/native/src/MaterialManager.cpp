

#include <Ogre4J.h>
#include <org_ogre4j_MaterialManager.h>

/// static getSingleton()
JNIEXPORT jint JNICALL Java_org_ogre4j_MaterialManager_getSingletonImpl
(JNIEnv *, jclass)
{
	Ogre::MaterialManager * ret = Ogre::MaterialManager::getSingletonPtr();
	RETURN_PTR(ret);
}

/// void setDefaultAnisotropy(int maxAniso)
JNIEXPORT void JNICALL Java_org_ogre4j_MaterialManager_setDefaultAnisotropy
(JNIEnv *, jclass, jint ptrSelf, jint jmaxAniso)
{
	Ogre::MaterialManager * self = GET_PTR(Ogre::MaterialManager, ptrSelf);
	self->setDefaultAnisotropy(jmaxAniso);
}

/// void setDefaultTextureFiltering(int fo)
JNIEXPORT void JNICALL Java_org_ogre4j_MaterialManager_setDefaultTextureFiltering
(JNIEnv *, jclass, jint ptrSelf, jint jfo)
{
	Ogre::MaterialManager * self = GET_PTR(Ogre::MaterialManager, ptrSelf);
	Ogre::TextureFilterOptions fo = static_cast<Ogre::TextureFilterOptions>(jfo);
	self->setDefaultTextureFiltering(fo);
}
