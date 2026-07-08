
#include <Ogre4J.h>
#include <org_ogre4j_EventProcessor.h>

/// static EventProcessor getSingleton()
JNIEXPORT jint JNICALL Java_org_ogre4j_EventProcessor_getSingletonImpl
  (JNIEnv *, jclass)
{
	Ogre::EventProcessor * ret = Ogre::EventProcessor::getSingletonPtr();
	RETURN_PTR(ret);
}


JNIEXPORT jint JNICALL Java_org_ogre4j_EventProcessor_createCppInstance
  (JNIEnv *, jclass)
{
	Ogre::EventProcessor * ret = new Ogre::EventProcessor();
	RETURN_PTR(ret);
}



/// void stopProcessingEvents()
JNIEXPORT void JNICALL Java_org_ogre4j_EventProcessor_stopProcessingEvents
  (JNIEnv *, jclass, jint)
{
	Ogre::EventProcessor * ret = new Ogre::EventProcessor();
	ret->stopProcessingEvents();
}
