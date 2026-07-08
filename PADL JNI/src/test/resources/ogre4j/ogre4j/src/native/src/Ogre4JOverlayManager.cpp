
#include <Ogre4J.h>
#include <org_ogre4j_OverlayManager.h>

using namespace Ogre4JHelper;

/// static OverlayManager getSingleton()
JNIEXPORT jint JNICALL Java_org_ogre4j_OverlayManager_getSingletonImpl
  (JNIEnv *, jclass)
{
	Ogre::OverlayManager * ret = Ogre::OverlayManager::getSingletonPtr();
    RETURN_PTR(ret);
}

/// void destroy(String name)
JNIEXPORT void JNICALL Java_org_ogre4j_OverlayManager_destroy
  (JNIEnv * env, jclass, jint ptrSelf, jstring jname)
{
	Ogre::OverlayManager * self = GET_PTR(Ogre::OverlayManager, ptrSelf);
	Ogre::String name;
	CopyJString(env, jname, name);
	self->destroy(name);
}

/// void destroyAll()
JNIEXPORT void JNICALL Java_org_ogre4j_OverlayManager_destroyAll
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::OverlayManager * self = GET_PTR(Ogre::OverlayManager, ptrSelf);
	self->destroyAll();
}

/// Overlay getByName(String name)
JNIEXPORT jint JNICALL Java_org_ogre4j_OverlayManager_getByName
  (JNIEnv * env, jclass, jint ptrSelf, jstring jname)
{
	Ogre::OverlayManager * self = GET_PTR(Ogre::OverlayManager, ptrSelf);
	Ogre::String name;
	CopyJString(env, jname, name);
	Ogre::Overlay * ret = self->getByName(name);
	RETURN_PTR(ret);
}


/// OverlayElement getOverlayElement(String name, boolean isTemplate)
JNIEXPORT jint JNICALL Java_org_ogre4j_OverlayManager_getOverlayElement
  (JNIEnv * env, jclass, jint ptrSelf, jstring jname, jboolean jisTemplate)
{
	Ogre::OverlayManager * self = GET_PTR(Ogre::OverlayManager, ptrSelf);
	Ogre::String name;
	CopyJString(env, jname, name);
	bool isTemplate = (jisTemplate == JNI_TRUE) ? true : false;
	Ogre::OverlayElement * ret = self->getOverlayElement(name, isTemplate);
	RETURN_PTR(ret);
}

/// int getViewportHeight()
JNIEXPORT jint JNICALL Java_org_ogre4j_OverlayManager_getViewportHeight
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::OverlayManager * self = GET_PTR(Ogre::OverlayManager, ptrSelf);
	return self->getViewportHeight();
}

/// int getViewportWidth()
JNIEXPORT jint JNICALL Java_org_ogre4j_OverlayManager_getViewportWidth
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::OverlayManager * self = GET_PTR(Ogre::OverlayManager, ptrSelf);
	return self->getViewportWidth();
}
