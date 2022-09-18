
#include <Ogre4J.h>
#include <org_ogre4j_EventTargetImpl.h>
/*

class Ogre4JEventTarget: public Ogre::EventTarget
{
public:
	virtual bool processEvent (InputEvent *e);
	void hook(JNIEnv *env, jmethodID methodStarted, jmethodID methodEnded, jobject listener);

private:
	JNIEnv *env;
	jmethodID methodStarted;
	jmethodID methodEnded;
	jobject listener;
};

/// void hook(EventTargetImpl impl)
JNIEXPORT void JNICALL Java_org_ogre4j_EventTargetImpl_hook
  (JNIEnv * env, jobject, jint)
{

}

void Ogre4JFrameListener::hook(JNIEnv *env, jmethodID methodStarted, jmethodID methodEnded, jobject listener)
{
	this->env = env;
	this->methodStarted = methodStarted;
	this->methodEnded = methodEnded;
	this->listener = listener;
}*/