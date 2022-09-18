

#include <Ogre4J.h>
#include <org_ogre4j_PlatformManager.h>

/// static getSingleton()
JNIEXPORT jint JNICALL Java_org_ogre4j_PlatformManager_getSingletonImpl
(JNIEnv *, jclass)
{
	Ogre::PlatformManager * ret = Ogre::PlatformManager::getSingletonPtr();
	RETURN_PTR(ret);
}

/// InputReader createInputReader()
JNIEXPORT jint JNICALL Java_org_ogre4j_PlatformManager_createInputReader
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::PlatformManager * self = GET_PTR(Ogre::PlatformManager, ptrSelf);
	Ogre::InputReader * ret = self->createInputReader();
	RETURN_PTR(ret);
}