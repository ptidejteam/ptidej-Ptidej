

#include <Ogre4J.h>
#include <org_ogre4j_MouseMotionTargetImpl.h>


/// void addMouseMotionListener(MouseMotionListener l)
JNIEXPORT void JNICALL Java_org_ogre4j_MouseMotionTargetImpl_removeMouseMotionListener
  (JNIEnv *, jobject, jint ptrSelf, jint ptrL)
{
	Ogre::MouseMotionTarget * self = GET_PTR(Ogre::MouseMotionTarget, ptrSelf);
	Ogre::MouseMotionListener * L = GET_PTR(Ogre::MouseMotionListener, ptrL);
	self->addMouseMotionListener(L);
}

/// void removeMouseMotionListener(MouseMotionListener l)
JNIEXPORT void JNICALL Java_org_ogre4j_MouseMotionTargetImpl_removeMouseMotionListener
  (JNIEnv *, jobject, jint ptrSelf, jint ptrL)
{
	Ogre::MouseMotionTarget * self = GET_PTR(Ogre::MouseMotionTarget, ptrSelf);
	Ogre::MouseMotionListener * L = GET_PTR(Ogre::MouseMotionListener, ptrL);
	self->removeMouseMotionListener(L);
}