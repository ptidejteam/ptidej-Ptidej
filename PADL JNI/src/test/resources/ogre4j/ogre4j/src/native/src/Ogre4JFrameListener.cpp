
#include <Ogre4J.h>
#include <org_ogre4j_FrameListener.h>


class Ogre4JFrameListener : public Ogre::FrameListener
{
public:
	virtual bool frameStarted(const Ogre::FrameEvent &evt);
	virtual bool frameEnded(const Ogre::FrameEvent &evt);
	void hook(JNIEnv *env, jmethodID methodStarted, jmethodID methodEnded, jobject listener);

private:
	JNIEnv *env;
	jmethodID methodStarted;
	jmethodID methodEnded;
	jobject listener;
};


bool Ogre4JFrameListener::frameEnded(const Ogre::FrameEvent &evt)
{
	jboolean ret = env->CallBooleanMethod(listener, methodStarted, evt.timeSinceLastEvent, evt.timeSinceLastFrame);

	return (ret == JNI_TRUE) ? true : false;
}

bool Ogre4JFrameListener::frameStarted(const Ogre::FrameEvent &evt)
{
	jboolean ret = env->CallBooleanMethod(listener, methodStarted, evt.timeSinceLastEvent, evt.timeSinceLastFrame);

	return (ret == JNI_TRUE) ? true : false;
}

void Ogre4JFrameListener::hook(JNIEnv *env, jmethodID methodStarted, jmethodID methodEnded, jobject listener)
{
	this->env = env;
	this->methodStarted = methodStarted;
	this->methodEnded = methodEnded;
	this->listener = listener;
}

JNIEXPORT jint JNICALL Java_org_ogre4j_FrameListener_createCppInstance
(JNIEnv *env, jclass)
{
	Ogre4JFrameListener *ret = new Ogre4JFrameListener();
	RETURN_PTR(ret);
}

JNIEXPORT void JNICALL Java_org_ogre4j_FrameListener_hook
(JNIEnv *env, jobject obj, jint ptrSelf)
{
	Ogre4JFrameListener *self = GET_PTR(Ogre4JFrameListener, ptrSelf);

	jclass cls = env->GetObjectClass(obj);
	jmethodID methodStarted = env->GetMethodID(cls, "frameStarted", "(FF)Z");
	jmethodID methodEnded = env->GetMethodID(cls, "frameEnded", "(FF)Z");
	jobject listener = env->NewGlobalRef(obj);

	self->hook(env, methodStarted, methodEnded, listener);
}


