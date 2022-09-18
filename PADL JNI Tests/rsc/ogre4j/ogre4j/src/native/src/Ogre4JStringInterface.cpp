
#include <Ogre4J.h>
#include <org_ogre4j_StringInterface.h>

using namespace Ogre4JHelper;

/// void StringInterface.copyParametersTo(StringInterface dest)
JNIEXPORT void JNICALL Java_org_ogre4j_StringInterface_copyParametersTo
(JNIEnv *, jclass, jint ptrSelf, jint ptrDest)
{
	Ogre::StringInterface * self = GET_PTR(Ogre::StringInterface, ptrSelf);
	Ogre::StringInterface * dest = GET_PTR(Ogre::StringInterface, ptrDest);

	self->copyParametersTo(dest);
}

/// ParamDictionary StringInterface.getParamDictionary()
JNIEXPORT jint JNICALL Java_org_ogre4j_StringInterface_getParamDictionary
(JNIEnv *, jclass, jint ptrSelf)
{
	Ogre::StringInterface * self = GET_PTR(Ogre::StringInterface, ptrSelf);

	Ogre::ParamDictionary * ret = self->getParamDictionary();
	RETURN_PTR(ret);
}

/// String StringInterface.getParameter()
JNIEXPORT jstring JNICALL Java_org_ogre4j_StringInterface_getParameter
(JNIEnv * env, jclass, jint ptrSelf, jstring jsName)
{
	Ogre::StringInterface * self = GET_PTR(Ogre::StringInterface, ptrSelf);
	Ogre::String name;

	CopyJString(env, jsName, name);
	jstring ret = env->NewStringUTF(name.c_str());
	return ret;
}

/// List<ParameterDef> StringInterface.getParameters()
JNIEXPORT jobjectArray JNICALL Java_org_ogre4j_StringInterface_getParameters
(JNIEnv * env, jclass, jint ptrSelf)
{
	Ogre::StringInterface * self = GET_PTR(Ogre::StringInterface, ptrSelf);

	// get parameter list
	Ogre::ParameterList pl = self->getParameters();

	// definition of org.ogre4j.ParameterDef and its fields
	jclass paramDef = env->FindClass("org/ogre4j/ParameterDef");
	jfieldID name = env->GetFieldID(paramDef, "name", "Ljava/lang/String;");
	jfieldID description = env->GetFieldID(paramDef, "description", "Ljava/lang/String;");
	jmethodID paramDefCtor = env->GetMethodID(paramDef, "<init>", "(Ljava/lang/String;Ljava/lang/String;I)V");

	// JNI object to return
	jobjectArray ret = env->NewObjectArray(pl.size(), paramDef, NULL);

	for(unsigned int i = 0; i < pl.size(); i++) {
		Ogre::ParameterDef &pd = pl[i];
		jstring jsName = env->NewStringUTF(pd.name.c_str());
		jstring jsDescription = env->NewStringUTF(pd.description.c_str());

		jobject obj = env->NewObject(paramDef, paramDefCtor, jsName, jsDescription, pd.paramType);
		env->SetObjectArrayElement(ret, 0, obj);
	}
	return ret;
}

/// boolean StringInterface.setParameter(String name, String value)
JNIEXPORT jboolean JNICALL Java_org_ogre4j_StringInterface_setParameter
(JNIEnv * env, jclass, jint ptrSelf, jstring jsName, jstring jsValue)
{
	Ogre::StringInterface * self = GET_PTR(Ogre::StringInterface, ptrSelf);

	Ogre::String name, value;
	CopyJString(env, jsName, name);
	CopyJString(env, jsValue, value);

	bool ret = self->setParameter(name, value);
	return (ret) ? JNI_TRUE : JNI_FALSE;
}

/// void StringInterface.setParameterList(Map<String, String> paramList)
JNIEXPORT void JNICALL Java_org_ogre4j_StringInterface_setParameterList
(JNIEnv * env, jclass, jint ptrSelf, jobjectArray jKeys, jobjectArray jValues)
{
	Ogre::StringInterface * self = GET_PTR(Ogre::StringInterface, ptrSelf);

	Ogre::NameValuePairList pl;

	jsize len = env->GetArrayLength(jKeys);

	for(int i = 0; i < len; i++) {
		jobject key = env->GetObjectArrayElement(jKeys, NULL);
		jobject value = env->GetObjectArrayElement(jValues, NULL);

		jstring jsKey = static_cast<jstring>(key);
		jstring jsValue = static_cast<jstring>(value);

		Ogre::String k, v;
		CopyJString(env, jsKey, k);
		CopyJString(env, jsValue, v);

		pl.insert(make_pair(k, v));
	}

	self->setParameterList(pl);
}

