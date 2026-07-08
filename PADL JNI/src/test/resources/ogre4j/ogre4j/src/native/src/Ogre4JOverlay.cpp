

#include <Ogre4J.h>
#include <org_ogre4j_Overlay.h>

using namespace Ogre4JHelper;

/// OverlayContainer getChild(String name)
JNIEXPORT jint JNICALL Java_org_ogre4j_Overlay_getChild
  (JNIEnv * env, jclass, jint ptrSelf, jstring jname)
{
    Ogre::Overlay * self = GET_PTR(Ogre::Overlay, ptrSelf);
	Ogre::String name;
	CopyJString(env, jname, name);
	Ogre::OverlayContainer * ret = self->getChild(name);
	RETURN_PTR(ret);
}

/// String getName()
JNIEXPORT jstring JNICALL Java_org_ogre4j_Overlay_getName
  (JNIEnv * env, jclass, jint ptrSelf)
{
	Ogre::Overlay * self = GET_PTR(Ogre::Overlay, ptrSelf);
	return env->NewStringUTF(self->getName().c_str());
}

/// short getZOrder()
JNIEXPORT jshort JNICALL Java_org_ogre4j_Overlay_getZOrder
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Overlay * self = GET_PTR(Ogre::Overlay, ptrSelf);
	return self->getZOrder();
}

/// void hide()
JNIEXPORT void JNICALL Java_org_ogre4j_Overlay_hide
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Overlay * self = GET_PTR(Ogre::Overlay, ptrSelf);
	return self->hide();
}

/// void setZOrder(short zorder)
JNIEXPORT void JNICALL Java_org_ogre4j_Overlay_setZOrder
  (JNIEnv *, jclass, jint ptrSelf, jshort jZOrder)
{
	Ogre::Overlay * self = GET_PTR(Ogre::Overlay, ptrSelf);
	self->setZOrder(jZOrder);
}

/// void show()
JNIEXPORT void JNICALL Java_org_ogre4j_Overlay_show
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Overlay * self = GET_PTR(Ogre::Overlay, ptrSelf);
	return self->show();
}
