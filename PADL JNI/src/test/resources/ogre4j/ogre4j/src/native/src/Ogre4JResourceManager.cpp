

#include <Ogre4J.h>
#include <org_ogre4j_ResourceManager.h>

using namespace Ogre4JHelper;

/// long getMemoryBudget()
JNIEXPORT jlong JNICALL Java_org_ogre4j_ResourceManager_getMemoryBudget
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::ResourceManager *self = GET_PTR(Ogre::ResourceManager, ptrSelf);
	return self->getMemoryBudget();
}

/// void remove(String name)
JNIEXPORT void JNICALL Java_org_ogre4j_ResourceManager_remove
  (JNIEnv * env, jclass, jint ptrSelf, jstring jname)
{
	Ogre::ResourceManager *self = GET_PTR(Ogre::ResourceManager, ptrSelf);
	Ogre::String name;
	CopyJString(env, jname, name);
	self->remove(name);
}

/// void removeAll()
JNIEXPORT void JNICALL Java_org_ogre4j_ResourceManager_removeAll
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::ResourceManager *self = GET_PTR(Ogre::ResourceManager, ptrSelf);
	self->removeAll();
}


/// void setMemoryBudget(long bytes)
JNIEXPORT void JNICALL Java_org_ogre4j_ResourceManager_setMemoryBudget
  (JNIEnv *, jclass, jint ptrSelf, jlong jbytes)
{
	Ogre::ResourceManager *self = GET_PTR(Ogre::ResourceManager, ptrSelf);
	self->setMemoryBudget(jbytes);
}

/// void unloadAll()
JNIEXPORT void JNICALL Java_org_ogre4j_ResourceManager_unloadAll
  (JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::ResourceManager *self = GET_PTR(Ogre::ResourceManager, ptrSelf);
	self->unloadAll();
}