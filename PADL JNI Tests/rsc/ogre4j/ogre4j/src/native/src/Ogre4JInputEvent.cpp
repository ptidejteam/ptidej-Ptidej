

#include <Ogre4J.h>
#include <org_ogre4j_InputEvent.h>
#include <OgreInputEvent.h>

/// void consume()
JNIEXPORT void JNICALL Java_org_ogre4j_InputEvent_consume
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::InputEvent * self = GET_PTR(Ogre::InputEvent, ptrSelf);
	self->consume();
}

/// int getID()
JNIEXPORT jint JNICALL Java_org_ogre4j_InputEvent_getID
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::InputEvent * self = GET_PTR(Ogre::InputEvent, ptrSelf);
	return self->getID();
}

/// int getModifiers()
JNIEXPORT jint JNICALL Java_org_ogre4j_InputEvent_getModifiers
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::InputEvent * self = GET_PTR(Ogre::InputEvent, ptrSelf);
	return self->getModifiers();
}

/// EventSource getSource()
JNIEXPORT jint JNICALL Java_org_ogre4j_InputEvent_getSource
  (JNIEnv *, jclass, jint ptrSelf)
{
		Ogre::InputEvent * self = GET_PTR(Ogre::InputEvent, ptrSelf);
		Ogre::EventTarget * ret = self->getSource();
		RETURN_PTR(ret);
}

/// float getWhen()
JNIEXPORT jfloat JNICALL Java_org_ogre4j_InputEvent_getWhen
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::InputEvent * self = GET_PTR(Ogre::InputEvent, ptrSelf);
	return self->getWhen();
}

/// boolean isAltDown()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_InputEvent_isAltDown
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::InputEvent * self = GET_PTR(Ogre::InputEvent, ptrSelf);
	return self->isAltDown();
}

/// boolean isConsumed()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_InputEvent_isConsumed
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::InputEvent * self = GET_PTR(Ogre::InputEvent, ptrSelf);
	return self->isConsumed();
}

/// boolean isControlDown()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_InputEvent_isControlDown
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::InputEvent * self = GET_PTR(Ogre::InputEvent, ptrSelf);
	return self->isControlDown();
}

/// boolean isEventBetween(int start, int end)
JNIEXPORT jboolean JNICALL Java_org_ogre4j_InputEvent_isEventBetween
  (JNIEnv *, jclass, jint ptrSelf, jint jstart, jint jend)
{
	Ogre::InputEvent * self = GET_PTR(Ogre::InputEvent, ptrSelf);
	return self->isEventBetween(jstart, jend);
}

/// boolean isMetaDown()
JNIEXPORT jboolean JNICALL Java_org_ogre4j_InputEvent_isMetaDown
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::InputEvent * self = GET_PTR(Ogre::InputEvent, ptrSelf);
	return self->isMetaDown();
}

/// boolean isShiftDown() 
JNIEXPORT jboolean JNICALL Java_org_ogre4j_InputEvent_isShiftDown
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::InputEvent * self = GET_PTR(Ogre::InputEvent, ptrSelf);
	return self->isShiftDown();
}




