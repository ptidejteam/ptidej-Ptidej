/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package java.lang;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class Class<T> {
	/*+
	  private static var classCache:Object = new Object();
	 +*/
	//TODO: e4 added ActionScript native
	
	public T[] getEnumConstants() {
		return null;
	}
	
	private String name;
	
	Class() {		
	}

	Class(String name) {
		this.name = name;
	}
	
	public static native Class<?> forName(String str) throws ClassNotFoundException /*{
		import flash.utils.*;
		if (str == null) throw new NullPointerException();
		if (str.length == 0) throw new ClassNotFoundException();
		var cls:Object = null;
		if (str.charAt(0) == "[") {
			cls = classCache[str];
			if (cls != null) return java.lang.Class__(cls);
			cls = java.lang.Class__.getArrayLeaf__Ljava_lang_String_2(str);
			cls = new java.lang.Class__();
			cls.name = str;
			classCache[str] = cls;
			return java.lang.Class__(cls);
		}
		if (str ==  "java.lang.Object") {
			cls = Object;
		} else if (str ==  "java.lang.String") {
			cls = String;
		} else {
			try {
				cls = getDefinitionByName(str);
			} catch (e:*) {}
			if (cls == null) 
				throw new ClassNotFoundException(str);
		}
		if (cls.__javaClass__ == undefined) {
			var javaCls:java.lang.Class__ = new java.lang.Class__();
			javaCls.name = str;
			cls.__javaClass__  = javaCls;
		}
		return cls.__javaClass__;
	}*/;
	
	static int getArrayDepth(String str) {
		int start = 0;
		int end = str.length();
		for (;start<end; start++) {
			if (str.charAt(start) != '[') break;
		}
		return start;
	}

	static Class getArrayLeaf(String str) throws ClassNotFoundException {
		int start = 0;
		int end = str.length();
		for (;start<end; start++) {
			if (str.charAt(start) != '[') break;
		}
		if (str.charAt(start) == 'L') {
			start++;
			end--;
		}
		str = str.substring(start, end);
		if (str == "Z") return Boolean.TYPE;
		if (str == "B") return Byte.TYPE;
		if (str == "C") return Character.TYPE;
		if (str == "S") return Short.TYPE;
		if (str == "I") return Integer.TYPE;
		if (str == "J") return Long.TYPE;
		if (str == "D") return Double.TYPE;
		if (str == "F") return Float.TYPE;
		if (str == "V") return Void.TYPE;
		return Class.forName(str);
	}
	
	static native Class getClass(Object obj)/*{
		import flash.utils.*;
		if (obj.constructor.__javaClass__ == undefined) {
			var name:String;
			if (obj.constructor == Object) {
				name = "java.lang.Object";
			} else if (obj.constructor == String) {
				name = "java.lang.String";
			} else {
				name = getQualifiedClassName(obj).replace("::", ".");
			}
			var cls:java.lang.Class__ = new java.lang.Class__();
			cls.name = name; 
			obj.constructor.__javaClass__  = cls;
		}
		return obj.constructor.__javaClass__;
	}*/;
	
	public boolean equals(Object obj) {
		return (obj instanceof Boolean) && (((Class)obj).getName() == getName());
	}
	
	public Class<?> getComponentType() {
		if (!isArray()) return null;
		try {
			String str = getName().substring(1);
			if (str == "Z") return Boolean.TYPE;
			if (str == "B") return Byte.TYPE;
			if (str == "C") return Character.TYPE;
			if (str == "S") return Short.TYPE;
			if (str == "I") return Integer.TYPE;
			if (str == "J") return Long.TYPE;
			if (str == "D") return Double.TYPE;
			if (str == "F") return Float.TYPE;
			if (str == "V") return Void.TYPE;
			return Class.forName(str);
		} catch (ClassNotFoundException e) {
			// should not happen
			return null;
		}
	}
	public native Field getField(String fieldName) throws NoSuchFieldException, SecurityException /*{
		import flash.utils.*;
		var cls:Object = null;
		if (name ==  "java.lang.Object") cls = Object;
		else if (name ==  "java.lang.String") cls = String;
		else cls = getDefinitionByName(name);
		var type:XML = describeType(cls);
		var field:XMLList = type.factory.variable.(@name == fieldName);
		if (field.length() == 0) {
			return null; 
			//TODO throw NoSuchFieldException
		}
		var javaField:java.lang.reflect.Field = new java.lang.reflect.Field(fieldName, this);
		return javaField;	
	}*/;
	
	public Field[] getFields() {
		//TODO
		return new Field[0];
	}
	
	public native Class[] getInterfaces()/*{
		import flash.utils.*;
		var cls:Object = null;
		if (name ==  "java.lang.Object") cls = Object;
		else if (name ==  "java.lang.String") cls = String;
		else cls = getDefinitionByName(name);
		var type:XML = describeType(cls);
		var i:int = 0;
		var list:XMLList = type.factory.implementsInterface.@type;
		var result:JavaArray = new JavaArray("java.lang.Class__").lengths(list.length);
		for each (var item:* in list) {
			result[i++] = java.lang.Class__.forName__Ljava_lang_String_2(item.toString().replace("::", "."));
		}
		return result;
	}*/;
	
	public Method[] getMethods()  {
		//TODO
		return new Method[0];
	}
	 
	public Method getMethod(String name, Class[] parameterTypes) {
		//TODO
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	public native Class<? super T> getSuperclass()/*{
		import flash.utils.*;
		if (isPrimitive__()) return null;
		if (this == java.lang.Void.TYPE) return null;
		if (name ==  "java.lang.Object") return null;
		if (this.isArray__() || name == "java.lang.String") {
			return java.lang.Class__.forName__Ljava_lang_String_2("java.lang.Object");
		}
		if (this.isInterface__()) return null;
		var str:String = getQualifiedSuperclassName(getDefinitionByName(name));
		return java.lang.Class__.forName__Ljava_lang_String_2(str);
	}*/;

	
	public java.io.InputStream getResourceAsStream(String name) {
		if (name == null) throw new NullPointerException();
		if (!name.startsWith("/")) {
			String className = getName();
			int index = className.lastIndexOf('.');
			if (index != -1) {
				className = className.substring(0, index + 1);
				className = className.replace('.', '/');
				name = className + name;
			}
		}
		//TODO: e4 use the class' Classloader
		return ClassLoader.getSystemResourceAsStream(name);
	};
	
	public boolean isArray() {
		return getName().charAt(0) == '[';
	}
	
	public native boolean isAssignableFrom(Class<?> cls)/*{
		if (cls == null) throw new NullPointerException();
		if (this === cls) return true;
		if (isPrimitive__()) return false;
		if (isArray__()) {
			if (!cls.isArray__()) return false;
			if (java.lang.Class__.getArrayDepth__Ljava_lang_String_2(name) != java.lang.Class__.getArrayDepth__Ljava_lang_String_2(cls.name)) return false;
			return java.lang.Class__.getArrayLeaf__Ljava_lang_String_2(name).isAssignableFrom__Ljava_lang_Class_2(java.lang.Class__.getArrayLeaf__Ljava_lang_String_2(cls.name));
		}
		if (!isInterface__()) {
			cls = cls.getSuperclass__();
			while (cls != null) {
				if (this == cls) return true;
				cls = cls.getSuperclass__();
			}
		}
		var interfaces:JavaArray = cls.getInterfaces__();
		for (var i:int; i<interfaces.length; i++) {
			if (interfaces[i] == this) return true;
		}
		return false;
	}*/;
	
	public native boolean isInstance(Object obj)/*{
		import flash.utils.*;
		if (obj == null) return false;
		if (name ==  "java.lang.Object") return true;
		if (isArray__()) {
			if  (!(obj is JavaArray)) return false;
			return this.isAssignableFrom__Ljava_lang_Class_2(obj.getClass__())
		}
		var cls:Object = null;
		if (name ==  "java.lang.String") cls = String;
		else cls = getDefinitionByName(name);
		return obj is Class(cls);
	}*/;
	
	public native boolean isInterface()/*{
		import flash.utils.*;
		if (name == "java.lang.Object") return false;
		if (name == "java.lang.String") return false;
		if (isArray__()) return false;
		var type:XML = describeType(getDefinitionByName(name));
		return type.factory.extendsClass.length () == 0;
	}*/;
	
	public boolean isPrimitive() {
		if (this == Boolean.TYPE) return true;
		if (this == Byte.TYPE) return true;
		if (this == Character.TYPE) return true;
		if (this == Double.TYPE) return true;
		if (this == Float.TYPE) return true;
		if (this == Integer.TYPE) return true;
		if (this == Long.TYPE) return true;
		if (this == Short.TYPE) return true;
		return false;
	}
	
	public native T	 newInstance() throws InstantiationException, IllegalAccessException/*{
		import flash.utils.*;
		if (isInterface__() || isPrimitive__() || isArray__()) {
			throw new InstantiationException();
		}
		var cls:Object = null;
		if (name ==  "java.lang.Object") cls = Object;
		else if (name ==  "java.lang.String") cls = String;
		else cls = getDefinitionByName(name);
		return new cls();
	}*/; 
	
	public String toString() {
		if (isPrimitive()) return getName();
		return (isInterface() ? "interface " : "class ") + getName();
	}
}
