
#include <Ogre4J.h>
#include <org_ogre4j_InputReader.h>


/// void addCursorMoveListener(MouseMotionListener c)
JNIEXPORT void JNICALL Java_org_ogre4j_InputReader_addCursorMoveListener
  (JNIEnv *, jclass, jint ptrSelf, jint ptrC)
{
	Ogre::InputReader * self = GET_PTR(Ogre::InputReader, ptrSelf);
	Ogre::MouseMotionListener * c = GET_PTR(Ogre::MouseMotionListener, ptrSelf);
	self->addCursorMoveListener(c);
}

/// void capture()
JNIEXPORT void JNICALL Java_org_ogre4j_InputReader_capture
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::InputReader * self = GET_PTR(Ogre::InputReader, ptrSelf);
	self->capture();
}

/// long getMouseAbsX()
JNIEXPORT jlong JNICALL Java_org_ogre4j_InputReader_getMouseAbsX
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::InputReader * self = GET_PTR(Ogre::InputReader, ptrSelf);
	return self->getMouseAbsX();
}

/// long getMouseAbsY()
JNIEXPORT jlong JNICALL Java_org_ogre4j_InputReader_getMouseAbsY
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::InputReader * self = GET_PTR(Ogre::InputReader, ptrSelf);
	return self->getMouseAbsY();
}

/// long getMouseAbsZ()
JNIEXPORT jlong JNICALL Java_org_ogre4j_InputReader_getMouseAbsZ
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::InputReader * self = GET_PTR(Ogre::InputReader, ptrSelf);
	return self->getMouseAbsZ();
}

/// boolean getMouseButton(char button)
JNIEXPORT jboolean JNICALL Java_org_ogre4j_InputReader_getMouseButton
  (JNIEnv *, jclass, jint ptrSelf, jchar jbutton)
{
	Ogre::InputReader * self = GET_PTR(Ogre::InputReader, ptrSelf);
	return self->getMouseButton(jbutton);
}

/// long getMouseRelativeX()
JNIEXPORT jlong JNICALL Java_org_ogre4j_InputReader_getMouseRelativeX
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::InputReader * self = GET_PTR(Ogre::InputReader, ptrSelf);
	return self->getMouseRelativeX();
}

/// long getMouseRelativeY()
JNIEXPORT jlong JNICALL Java_org_ogre4j_InputReader_getMouseRelativeY
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::InputReader * self = GET_PTR(Ogre::InputReader, ptrSelf);
	return self->getMouseRelativeY();
}

/// long getMouseRelativeZ()
JNIEXPORT jlong JNICALL Java_org_ogre4j_InputReader_getMouseRelativeZ
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::InputReader * self = GET_PTR(Ogre::InputReader, ptrSelf);
	return self->getMouseRelativeZ();
}

/// long getMouseRelX()
JNIEXPORT jlong JNICALL Java_org_ogre4j_InputReader_getMouseRelX
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::InputReader * self = GET_PTR(Ogre::InputReader, ptrSelf);
	return self->getMouseRelX();
}

/// long getMouseRelY()
JNIEXPORT jlong JNICALL Java_org_ogre4j_InputReader_getMouseRelY
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::InputReader * self = GET_PTR(Ogre::InputReader, ptrSelf);
	return self->getMouseRelY();
}

/// long getMouseRelZ()
JNIEXPORT jlong JNICALL Java_org_ogre4j_InputReader_getMouseRelZ
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::InputReader * self = GET_PTR(Ogre::InputReader, ptrSelf);
	return self->getMouseRelZ();
}

/// void initialise(RenderWindow pWindow, boolean useKeyboard,
///            boolean useMouse, boolean useGameController)
JNIEXPORT void JNICALL Java_org_ogre4j_InputReader_initialise
  (JNIEnv *, jclass, jint ptrSelf, jint ptrWindow, jboolean juseKeyboard, 
  jboolean juseMouse, jboolean juseGameController)
{
	Ogre::InputReader * self = GET_PTR(Ogre::InputReader, ptrSelf);
	Ogre::RenderWindow * window = GET_PTR(Ogre::RenderWindow, ptrWindow);
	bool useKeyboard = (juseKeyboard == JNI_TRUE) ? true : false;
	bool useMouse = (juseMouse == JNI_TRUE) ? true : false;
	bool useGameController = (juseGameController == JNI_TRUE) ? true : false;
	self->initialise(window, useKeyboard, useMouse, useGameController);
}

/// boolean isKeyDown(int kc)
JNIEXPORT jboolean JNICALL Java_org_ogre4j_InputReader_isKeyDown
  (JNIEnv *, jclass, jint ptrSelf, jint jkc)
{
	Ogre::InputReader * self = GET_PTR(Ogre::InputReader, ptrSelf);
	Ogre::KeyCode kc = static_cast<Ogre::KeyCode>(jkc);
	return self->isKeyDown(kc);
}

/// void removeCursorMoveListener(MouseMotionListener c)
JNIEXPORT void JNICALL Java_org_ogre4j_InputReader_removeCursorMoveListener
  (JNIEnv *, jclass, jint ptrSelf, jint ptrC)
{
	Ogre::InputReader * self = GET_PTR(Ogre::InputReader, ptrSelf);
	Ogre::MouseMotionListener * c = GET_PTR(Ogre::MouseMotionListener, ptrSelf);
	self->removeCursorMoveListener(c);
}

/// void setBufferedInput(boolean keys, boolean mouse)
JNIEXPORT void JNICALL Java_org_ogre4j_InputReader_setBufferedInput
  (JNIEnv *, jclass, jint ptrSelf, jboolean jkeys, jboolean jmouse)
{
	Ogre::InputReader * self = GET_PTR(Ogre::InputReader, ptrSelf);
	bool keys = (jkeys == JNI_TRUE) ? true : false;
	bool mouse = (jmouse == JNI_TRUE) ? true : false;
	self->setBufferedInput(keys, mouse);
}


