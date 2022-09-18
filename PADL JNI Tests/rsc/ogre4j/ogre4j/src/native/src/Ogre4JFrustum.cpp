

#include <Ogre4J.h>
#include <org_ogre4j_Frustum.h>


/// float getFarClipDistance()
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Frustum_getFarClipDistance
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Frustum * self = GET_PTR(Ogre::Frustum, ptrSelf);
	return self->getFarClipDistance();
}

/// float getFOVy()
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Frustum_getFOVy
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Frustum * self = GET_PTR(Ogre::Frustum, ptrSelf);
	Ogre::Radian ret = self->getFOVy();
	return ret.valueRadians();
}

/// float getNearClipDistance()
JNIEXPORT jfloat JNICALL Java_org_ogre4j_Frustum_getNearClipDistance
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::Frustum * self = GET_PTR(Ogre::Frustum, ptrSelf);
	return self->getNearClipDistance();
}

/// void setFarClipDistance(float farDist)
JNIEXPORT void JNICALL Java_org_ogre4j_Frustum_setFarClipDistance
(JNIEnv *, jclass, jint ptrSelf, jfloat jFarDist)
{
	Ogre::Frustum * self = GET_PTR(Ogre::Frustum, ptrSelf);
	self->setFarClipDistance(jFarDist);
}

/// void setAspectRatio(float ratio)
JNIEXPORT void JNICALL Java_org_ogre4j_Frustum_setAspectRatio
(JNIEnv *, jclass, jint ptrSelf, jfloat jratio)
{
	Ogre::Frustum * self = GET_PTR(Ogre::Frustum, ptrSelf);
	self->setAspectRatio(jratio);
}

/// void setFOVy(float fovy)
JNIEXPORT void JNICALL Java_org_ogre4j_Frustum_setFOVy
(JNIEnv *, jclass, jint ptrSelf, jfloat jofvy)
{
	Ogre::Frustum * self = GET_PTR(Ogre::Frustum, ptrSelf);
	Ogre::Radian fovy = (Ogre::Radian)jofvy;
	self->setFOVy(fovy);
}

/// void setNearClipDistance(float nearDist)
JNIEXPORT void JNICALL Java_org_ogre4j_Frustum_setNearClipDistance
(JNIEnv *, jclass, jint ptrSelf, jfloat jnearDist)
{
	Ogre::Frustum * self = GET_PTR(Ogre::Frustum, ptrSelf);
	self->setNearClipDistance(jnearDist);
}
