/*
  The Lexi word processor.
  This is Registry.java

  It stores the properties of lexi.

 */
package com.jmonkey.export;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

/**
* The service Registry class stores 
* properties used by the services.<BR>
* To get a working registry, you must
* use the static methods:<BR>
* <UL>
* 	<LI> Registry.loadForClass(Class)::Registry           
*		<LI> Registry.loadForName(String)::Registry
* </UL><P>
* loadForClass(Class) returns a registry that 
* is guaranteed to be unique to the class. It 
* essentially calles loadForName(class.getName()), 
* except that the class name will be MD5 encrypted.
* If you requre the that the registry be accessible
* to more basic operation, then user the loadForName(String).<P>
* Be careful with loadForName(String) as it id possible to
* load the registry for another application.<P>
* A check for Registry.isRegistry(String|Class) will tell you if 
* there is already a registry on a particular name in the system.<P>
* get methods the return numbers may thor NumberFormatException if you
* try and get a value that is not a number, with a numeric getter.<P>
* Array value may also throw unexpected exceptions if the data type 
* in incorrect, or if the stored version of the property is corrupt.
* @version 0.1 Revision 3
* @author Brill Pappin 14-SEP-1999
*/
public class Registry {
	/**
	* The version fo this object.
	* index 0 if the major, 1 is monor, 
	* and 2 is revision.
	*/
	public static final int[] VERSION = { 0, 1, 1 };

	public static final int TYPE_NULL = 0x0; // None
	public static final int TYPE_STRING_SINGLE = 0x2; // String
	public static final int TYPE_STRING_ARRAY = 0x4; // String Array
	public static final int TYPE_OBJECT_SINGLE = 0x8; // Object
	public static final int TYPE_OBJECT_ARRAY = 0x10; // Object Array
	public static final int TYPE_BOOLEAN_SINGLE = 0x20; // boolean
	public static final int TYPE_BYTE_SINGLE = 0x40; // byte
	public static final int TYPE_BYTE_ARRAY = 0x080; // byte Array
	public static final int TYPE_CHAR_SINGLE = 0xF0; // char
	public static final int TYPE_CHAR_ARRAY = 0x100; // char Array
	public static final int TYPE_SHORT_SINGLE = 0x200; // short
	public static final int TYPE_INT_SINGLE = 0x400; // int
	public static final int TYPE_INT_ARRAY = 0x800; // int Array
	public static final int TYPE_LONG_SINGLE = 0xF00; // long
	public static final int TYPE_DOUBLE_SINGLE = 0x1000; // double
	public static final int TYPE_FLOAT_SINGLE = 0x2000; // float
	public static final int TYPE_CORRUPT = 0x4000; // float

	/**
	* The directory that resources will be stored in.<BR>
	* The directory will be called <user_home>/.lexi.
	*/
	public static final File RESOURCE_DIRECTORY =
		new File(
			ensureDirectory(
				System.getProperty("user.home")
					+ java.io.File.separator
					+ ".lexi"
					+ java.io.File.separator
					+ "export"));
	public static final File REGISTRY_DIRECTORY =
		new File(
			ensureDirectory(
				RESOURCE_DIRECTORY.getAbsolutePath()
					+ File.separator
					+ "registry"));

	private static Registry _INSTANCE = null;

	/**
	* The default registry implementation.
	* @version 0.1 Revision 0
	* @author Brill Pappin 14-SEP-1999
	*/
	protected final class Impl extends Registry implements Serializable {
		private static final String _PROPERTY_CHAR_ENCODING = "ASCII";
		private static final String _ID_STR = "ST@"; // String
		private static final String _ID_STA = "SA@"; // String Array
		private static final String _ID_OBJ = "OB@"; // Object
		private static final String _ID_OBA = "OA@"; // Object Array
		private static final String _ID_BOO = "BO@"; // boolean
		private static final String _ID_BYT = "BY@"; // byte
		private static final String _ID_BYA = "BA@"; // byte Array
		private static final String _ID_CHR = "CH@"; // char
		private static final String _ID_CHA = "CA@"; // char Array
		private static final String _ID_SHO = "SH@"; // short
		private static final String _ID_INT = "IN@"; // int
		private static final String _ID_INA = "IA@"; // int Array
		private static final String _ID_LON = "LO@"; // long
		private static final String _ID_DBL = "DO@"; // double
		private static final String _ID_FLT = "FL@"; // float
		private boolean _ALTERED = false;

		private Hashtable _GROUPS = new Hashtable();
		private File _DATA_FILE = null;

		protected Impl() {
			super();
		}

		protected Impl(Reader reader) throws IOException {
			super();
			this.read(reader);
		}

		protected Impl(File data_file) throws IOException {
			super();
			this.setFile(data_file);
			this.loadData();
		}

		public void setFile(File file) {
			if (_DATA_FILE != null) {
				// if the file is not null, 
				// then this is probably not 
				// a new instance.
				_ALTERED = true;
			}
			_DATA_FILE = file;
		}

		public File getFile() {
			return _DATA_FILE;
		}

		/**
		* indicates that the registry is no longer in sync 
		* with the stored version. This occures when the contents
		* of the registry have been changed, and are not stored. 
		* Calling commit() will sync the registry with the stored 
		* version.
		*/
		public boolean isAltered() {
			return _ALTERED;
		}

		/**
		* Dumps the contents of the registry 
		* to System.out. Used for debugging.
		*/
		public void dump() {
			System.out.println(
				"Registry dump: " + (new java.util.Date()).toString());
			Enumeration ge = _GROUPS.keys();
			while (ge.hasMoreElements()) {
				String key = (String) ge.nextElement();
				System.out.println(key);
				Properties temp = (Properties) _GROUPS.get(key);
				Enumeration pe = temp.keys();
				while (pe.hasMoreElements()) {
					String pkey = (String) pe.nextElement();
					System.out.println(
						"\t" + pkey + "=" + temp.getProperty(pkey));
				}
			}
		}

		public void read(Reader reader) throws IOException {
			BufferedReader br = new BufferedReader(reader);
			String current_group = null;
			StringBuffer buffer = new StringBuffer();
			// int idx;

			if (!_GROUPS.isEmpty()) {
				deleteAll();
			}

			try {
				String line = null;
				while ((line = br.readLine()) != null) {
					if ((!line.startsWith("//"))
						&& (!line.startsWith(";"))
						&& (!line.startsWith("#"))) {
						if ((line.charAt(0) == '[')
							&& (line.charAt(line.length() - 1) == ']')) {
							if ((buffer.length() > 0)
								&& (current_group != null)) {
								// load the group...
								ByteArrayInputStream bais =
									new ByteArrayInputStream(
										buffer.toString().getBytes(
											_PROPERTY_CHAR_ENCODING));
								(
									(Properties) _GROUPS.get(
										current_group)).load(
									bais);

								bais.close();
								buffer.setLength(0);
							}
							current_group =
								line.substring(1, line.length() - 1);
							this.ensureGroup(current_group);
							// } else if ((idx = line.indexOf('=')) != -1) {
						}
						else if (line.indexOf('=') != -1) {
							buffer.append(line + "\n");
						}
					}
				}

				// check for the last group...
				if ((buffer.length() > 0) && (current_group != null)) {
					// load the group...
					ByteArrayInputStream bais =
						new ByteArrayInputStream(
							buffer.toString().getBytes(
								_PROPERTY_CHAR_ENCODING));
					((Properties) _GROUPS.get(current_group)).load(bais);

					bais.close();
					buffer.setLength(0);
				}

			}
			finally {
				try {
					br.close();
				}
				catch (IOException ioe0) {
				}

			}
		}

		public void write(Writer writer) throws IOException {
			//BufferedWriter bw = new BufferedWriter(writer);
			try {
				Enumeration groups = _GROUPS.keys();
				while (groups.hasMoreElements()) {
					String group = (String) groups.nextElement();
					String writeSec =
						"["
							+ group
							+ "]"
							+ System.getProperty("line.separator");
					//bw.write(writeSec, 0, writeSec.length());
					writer.write(writeSec);
					Properties temp = (Properties) _GROUPS.get(group);

					// Debug Line...
					//System.out.println("Stored: " + group + " - size: " + temp.size());

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					temp.store(baos, _DATA_FILE.getName());
					writer.write(baos.toString(_PROPERTY_CHAR_ENCODING));
					baos.close();
				}
			}
			finally {
				try {
					writer.flush();
				}
				catch (IOException ioe0) {
				}

				try {
					writer.close();
				}
				catch (IOException ioe1) {
				}

			}
		}

		private void loadData() throws IOException {
			if (_DATA_FILE != null) {
				_DATA_FILE.createNewFile();
				this.read(new FileReader(_DATA_FILE));
				_ALTERED = false;
			}
			else {
				throw new IOException("Data file not set.");
			}
		}

		private void storeData() throws IOException {
			if (_DATA_FILE != null) {
				_DATA_FILE.createNewFile();
				this.write(new FileWriter(_DATA_FILE));
				_ALTERED = false;
			}
			else {
				throw new IOException("Data file not set.");
			}
		}

		private void ensureGroup(String group) {
			if (!_GROUPS.containsKey(group)) {
				_GROUPS.put(group, new Properties());
				_ALTERED = true;
			}
		}

		private void setBasicProperty(
			String group,
			String key,
			String value) {
			if (value == null) {
				throw new IllegalArgumentException("Can not set a property to null.");
			}
			else {
				this.ensureGroup(group);
				((Properties) _GROUPS.get(group)).setProperty(key, value);
				_ALTERED = true;
			}
		}

		private String getBasicProperty(
			String group,
			String key,
			String value) {
			if (value == null) {
				if (this.isGroup(group)) {
					return ((Properties) _GROUPS.get(group)).getProperty(key);
				}
				else {
					return null;
				}
			}
			else {
				this.ensureGroup(group);
				if (!((Properties) _GROUPS.get(group)).containsKey(key)) {
					this.setProperty(group, key, value);
					return ((Properties) _GROUPS.get(group)).getProperty(key);
				}
				else {
					return ((Properties) _GROUPS.get(group)).getProperty(key);
				}
			}
		}

		/**
		* Tests if the property specified is an array type.
		* @param group java.lang.String
		* @param key java.lang.String
		* @return boolean
		*/
		public boolean isArrayType(String group, String key) {
			int type = this.getType(group, key);
			switch (type) {
				case Registry.TYPE_STRING_ARRAY :
				case Registry.TYPE_OBJECT_ARRAY :
				case Registry.TYPE_BYTE_ARRAY :
				case Registry.TYPE_CHAR_ARRAY :
				case Registry.TYPE_INT_ARRAY :
					return true;
				default :
					return false;
			}
		}

		/**
		* Returns the type of the specified property.
		* @param group java.lang.String
		* @param key java.lang.String
		* @return int
		*/
		public int getType(String group, String key) {
			String value = ((Properties) _GROUPS.get(group)).getProperty(key);
			if (value == null) {
				return Registry.TYPE_NULL;
			}
			else {
				String code = value.substring(0, 3);

				// debug line...
				System.out.println("TYPE: " + code);

				if (code.equals(Impl._ID_STR)) {
					return Registry.TYPE_STRING_SINGLE;
				}
				else if (code.equals(Impl._ID_STA)) {
					return Registry.TYPE_STRING_ARRAY;
				}
				else if (code.equals(Impl._ID_OBJ)) {
					return Registry.TYPE_OBJECT_SINGLE;
				}
				else if (code.equals(Impl._ID_OBA)) {
					return Registry.TYPE_OBJECT_ARRAY;
				}
				else if (code.equals(Impl._ID_BOO)) {
					return Registry.TYPE_BOOLEAN_SINGLE;
				}
				else if (code.equals(Impl._ID_BYT)) {
					return Registry.TYPE_BYTE_SINGLE;
				}
				else if (code.equals(Impl._ID_BYA)) {
					return Registry.TYPE_BYTE_ARRAY;
				}
				else if (code.equals(Impl._ID_CHR)) {
					return Registry.TYPE_CHAR_SINGLE;
				}
				else if (code.equals(Impl._ID_CHA)) {
					return Registry.TYPE_CHAR_ARRAY;
				}
				else if (code.equals(Impl._ID_SHO)) {
					return Registry.TYPE_SHORT_SINGLE;
				}
				else if (code.equals(Impl._ID_INT)) {
					return Registry.TYPE_INT_SINGLE;
				}
				else if (code.equals(Impl._ID_INA)) {
					return Registry.TYPE_INT_ARRAY;
				}
				else if (code.equals(Impl._ID_LON)) {
					return Registry.TYPE_LONG_SINGLE;
				}
				else if (code.equals(Impl._ID_DBL)) {
					return Registry.TYPE_DOUBLE_SINGLE;
				}
				else if (code.equals(Impl._ID_FLT)) {
					return Registry.TYPE_FLOAT_SINGLE;
				}
				else {
					return Registry.TYPE_CORRUPT;
				}
			}
		}

		private String encode(Object o)
			throws InvalidClassException, NotSerializableException, IOException {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(o);
			oos.close();
			// Convert the bytes into a format
			// that won't get corrupted when
			// its written by a writer. and that
			// we can decode later.
			byte[] output = baos.toByteArray();
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < output.length; i++) {
				buffer.append((int) output[i] + "|");
			}
			baos.close();
			return buffer.toString();

		}

		private Object decode(String in)
			throws OptionalDataException, ClassNotFoundException, IOException {
			// convert the int string to bytes...
			// this is not really the fastest way
			// to do this, there are to many string
			// creations. However, it will work for now.
			String byteStr = this.trimCode(in);
			StringTokenizer stok = new StringTokenizer(byteStr, "|");
			ArrayList list = new ArrayList();
			while (stok.hasMoreTokens()) {
				list.add((String) stok.nextToken());
			}
			byte[] byteList = new byte[list.size()];
			for (int l = 0; l < list.size(); l++) {
				byteList[l] = Byte.parseByte((String) list.get(l));
			}

			// Now we can try and reconstruct the object.
			ByteArrayInputStream bais = new ByteArrayInputStream(byteList);
			ObjectInputStream ois = new ObjectInputStream(bais);
			Object o = ois.readObject();
			ois.close();
			bais.close();
			return o;
		}

		private String trimCode(String in) {
			return in.substring(3, in.length());
		}

		// ===============================================================================
		// Getters
		// ===============================================================================

		public String getString(
			String group,
			String key,
			String defaultValue) {
			return this.trimCode(
				this.getBasicProperty(group, key, defaultValue));
		}

		public String[] getStringArray(
			String group,
			String key,
			String[] defaultValue) {
			try {
				String out = this.getBasicProperty(group, key, null);
				if (out == null) {
					this.setProperty(group, key, defaultValue);
					String so = this.getBasicProperty(group, key, null);
					if (so.startsWith(_ID_STA)) {
						return (String[]) this.decode(so);
					}
					else {
						return defaultValue;
					}
				}
				else {
					if (out.startsWith(_ID_STA)) {
						return (String[]) this.decode(out);
					}
					else {
						return defaultValue;
					}
				}
			}
			catch (Throwable t) {
				t.printStackTrace(System.out);
				return defaultValue;
			}
		}

		public boolean getBoolean(
			String group,
			String key,
			boolean defaultValue) {
			String out = this.getBasicProperty(group, key, null);
			if (out == null) {
				this.setProperty(group, key, defaultValue);
				String so = this.getBasicProperty(group, key, null);
				if (so.startsWith(_ID_BOO)) {
					return (new Boolean(this.trimCode(so))).booleanValue();
				}
				else {
					return defaultValue;
				}
			}
			else {
				if (out.startsWith(_ID_BOO)) {
					return (new Boolean(this.trimCode(out))).booleanValue();
				}
				else {
					return defaultValue;
				}
			}
		}
		public int getInteger(String group, String key, int defaultValue) {
			String out = this.getBasicProperty(group, key, null);
			if (out == null) {
				this.setProperty(group, key, defaultValue);
				String so = this.getBasicProperty(group, key, null);
				if (so.startsWith(_ID_INT)) {
					return Integer.parseInt(this.trimCode(so));
				}
				else {
					return defaultValue;
				}
			}
			else {
				if (out.startsWith(_ID_INT)) {
					return Integer.parseInt(this.trimCode(out));
				}
				else {
					return defaultValue;
				}
			}
		}
		public int[] getIntegerArray(
			String group,
			String key,
			int[] defaultValue) {
			try {
				String out = this.getBasicProperty(group, key, null);
				if (out == null) {
					this.setProperty(group, key, defaultValue);
					String so = this.getBasicProperty(group, key, null);
					if (so.startsWith(_ID_INA)) {
						return (int[]) this.decode(so);
					}
					else {
						return defaultValue;
					}
				}
				else {
					if (out.startsWith(_ID_INA)) {
						return (int[]) this.decode(out);
					}
					else {
						return defaultValue;
					}
				}
			}
			catch (Throwable t) {
				t.printStackTrace(System.out);
				return defaultValue;
			}
		}
		public long getLong(String group, String key, long defaultValue) {
			String out = this.getBasicProperty(group, key, null);
			if (out == null) {
				this.setProperty(group, key, defaultValue);
				String so = this.getBasicProperty(group, key, null);
				if (so.startsWith(_ID_LON)) {
					return Long.parseLong(this.trimCode(so));
				}
				else {
					return defaultValue;
				}
			}
			else {
				if (out.startsWith(_ID_LON)) {
					return Long.parseLong(this.trimCode(out));
				}
				else {
					return defaultValue;
				}
			}
		}
		public byte getByte(String group, String key, byte defaultValue) {
			String out = this.getBasicProperty(group, key, null);
			if (out == null) {
				this.setProperty(group, key, defaultValue);
				String so = this.getBasicProperty(group, key, null);
				if (so.startsWith(_ID_BYT)) {
					return Byte.parseByte(this.trimCode(so));
				}
				else {
					return defaultValue;
				}
			}
			else {
				if (out.startsWith(_ID_BYT)) {
					return Byte.parseByte(this.trimCode(out));
				}
				else {
					return defaultValue;
				}
			}
		}
		public byte[] getByteArray(
			String group,
			String key,
			byte[] defaultValue) {
			try {
				String out = this.getBasicProperty(group, key, null);
				if (out == null) {
					this.setProperty(group, key, defaultValue);
					String so = this.getBasicProperty(group, key, null);
					if (so.startsWith(_ID_BYA)) {
						return (byte[]) this.decode(so);
					}
					else {
						return defaultValue;
					}
				}
				else {
					if (out.startsWith(_ID_BYA)) {
						return (byte[]) this.decode(out);
					}
					else {
						return defaultValue;
					}
				}
			}
			catch (Throwable t) {
				t.printStackTrace(System.out);
				return defaultValue;
			}
		}
		public char getChar(String group, String key, char defaultValue) {
			String out = this.getBasicProperty(group, key, null);
			if (out == null) {
				this.setProperty(group, key, defaultValue);
				String so = this.getBasicProperty(group, key, null);
				if (so.startsWith(_ID_CHR)) {
					return this.trimCode(so).charAt(0);
				}
				else {
					return defaultValue;
				}
			}
			else {
				if (out.startsWith(_ID_CHR)) {
					return this.trimCode(out).charAt(0);
				}
				else {
					return defaultValue;
				}
			}
		}
		public char[] getCharArray(
			String group,
			String key,
			char[] defaultValue) {
			try {
				String out = this.getBasicProperty(group, key, null);
				if (out == null) {
					this.setProperty(group, key, defaultValue);
					String so = this.getBasicProperty(group, key, null);
					if (so.startsWith(_ID_CHA)) {
						return (char[]) this.decode(so);
					}
					else {
						return defaultValue;
					}
				}
				else {
					if (out.startsWith(_ID_CHA)) {
						return (char[]) this.decode(out);
					}
					else {
						return defaultValue;
					}
				}
			}
			catch (Throwable t) {
				t.printStackTrace(System.out);
				return defaultValue;
			}
		}
		public double getDouble(
			String group,
			String key,
			double defaultValue) {
			String out = this.getBasicProperty(group, key, null);
			if (out == null) {
				this.setProperty(group, key, defaultValue);
				String so = this.getBasicProperty(group, key, null);
				if (so.startsWith(_ID_DBL)) {
					return Double.parseDouble(this.trimCode(so));
				}
				else {
					return defaultValue;
				}
			}
			else {
				if (out.startsWith(_ID_DBL)) {
					return Double.parseDouble(this.trimCode(out));
				}
				else {
					return defaultValue;
				}
			}
		}
		public float getFloat(String group, String key, float defaultValue) {
			String out = this.getBasicProperty(group, key, null);
			if (out == null) {
				this.setProperty(group, key, defaultValue);
				String so = this.getBasicProperty(group, key, null);
				if (so.startsWith(_ID_FLT)) {
					return Float.parseFloat(this.trimCode(so));
				}
				else {
					return defaultValue;
				}
			}
			else {
				if (out.startsWith(_ID_FLT)) {
					return Float.parseFloat(this.trimCode(out));
				}
				else {
					return defaultValue;
				}
			}
		}

		public Object getObject(
			String group,
			String key,
			Serializable defaultValue) {
			try {
				String out = this.getBasicProperty(group, key, null);
				if (out == null) {
					this.setProperty(group, key, defaultValue);
					String so = this.getBasicProperty(group, key, null);
					if (so.startsWith(_ID_OBJ)) {
						return this.decode(so);
					}
					else {
						return defaultValue;
					}
				}
				else {
					if (out.startsWith(_ID_OBJ)) {
						return this.decode(out);
					}
					else {
						return defaultValue;
					}
				}
			}
			catch (Throwable t) {
				return defaultValue;
			}
		}

		public Object[] getObject(
			String group,
			String key,
			Serializable[] defaultValue) {
			try {
				String out = this.getBasicProperty(group, key, null);
				if (out == null) {
					this.setProperty(group, key, defaultValue);
					String so = this.getBasicProperty(group, key, null);
					if (so.startsWith(_ID_OBA)) {
						return (Object[]) this.decode(so);
					}
					else {
						return defaultValue;
					}
				}
				else {
					if (out.startsWith(_ID_OBA)) {
						return (Object[]) this.decode(out);
					}
					else {
						return defaultValue;
					}
				}
			}
			catch (Throwable t) {
				return defaultValue;
			}
		}

		public short getShort(String group, String key, short defaultValue) {
			String out = this.getBasicProperty(group, key, null);
			if (out == null) {
				this.setProperty(group, key, defaultValue);
				String so = this.getBasicProperty(group, key, null);
				if (so.startsWith(_ID_SHO)) {
					return Short.parseShort(this.trimCode(so));
				}
				else {
					return defaultValue;
				}
			}
			else {
				if (out.startsWith(_ID_SHO)) {
					return Short.parseShort(this.trimCode(out));
				}
				else {
					return defaultValue;
				}
			}
		}

		// ===============================================================================
		// Setters
		// ===============================================================================

		public void setProperty(String group, String key, String value) {
			this.setBasicProperty(group, key, _ID_STR + value);
		}
		public void setProperty(String group, String key, String[] value) {
			try {
				this.setBasicProperty(
					group,
					key,
					_ID_STA + this.encode(value));
			}
			catch (Throwable t) {
				// don't set anything if an exception was thrown.
				t.printStackTrace(System.out);
			}
		}
		public void setProperty(String group, String key, boolean value) {
			this.setBasicProperty(group, key, _ID_BOO + value);
		}
		public void setProperty(String group, String key, int value) {
			this.setBasicProperty(group, key, _ID_INT + value);
		}
		public void setProperty(String group, String key, int[] value) {
			try {
				this.setBasicProperty(
					group,
					key,
					_ID_INA + this.encode(value));
			}
			catch (Throwable t) {
				// don't set anything if an exception was thrown.
				t.printStackTrace(System.out);
			}
		}
		public void setProperty(String group, String key, long value) {
			this.setBasicProperty(group, key, _ID_LON + value);
		}
		public void setProperty(String group, String key, byte value) {
			this.setBasicProperty(group, key, _ID_BYT + value);
		}
		public void setProperty(String group, String key, byte[] value) {
			try {
				this.setBasicProperty(
					group,
					key,
					_ID_BYA + this.encode(value));
			}
			catch (Throwable t) {
				// don't set anything if an exception was thrown.
				t.printStackTrace(System.out);
			}
		}
		public void setProperty(String group, String key, char value) {
			this.setBasicProperty(group, key, _ID_CHR + value);
		}
		public void setProperty(String group, String key, char[] value) {
			try {
				this.setBasicProperty(
					group,
					key,
					_ID_CHA + this.encode(value));
			}
			catch (Throwable t) {
				// don't set anything if an exception was thrown.
				t.printStackTrace(System.out);
			}
		}
		public void setProperty(String group, String key, double value) {
			this.setBasicProperty(group, key, _ID_DBL + value);
		}
		public void setProperty(String group, String key, float value) {
			this.setBasicProperty(group, key, _ID_FLT + value);
		}

		public void setProperty(
			String group,
			String key,
			Serializable value) {
			try {
				this.setBasicProperty(
					group,
					key,
					_ID_OBJ + this.encode(value));
			}
			catch (Throwable t) {
				// don't set anything if an exception was thrown.
			}
		}

		public void setProperty(
			String group,
			String key,
			Serializable[] value) {
			try {
				this.setBasicProperty(
					group,
					key,
					_ID_OBA + this.encode(value));
			}
			catch (Throwable t) {
				// don't set anything if an exception was thrown.
			}
		}

		public void setProperty(String group, String key, short value) {
			this.setBasicProperty(group, key, _ID_SHO + value);
		}

		// ===============================================================================
		// Others
		// ===============================================================================

		public boolean isProperty(String group, String key) {
			if (this.isGroup(group)) {
				return ((Properties) _GROUPS.get(group)).containsKey(key);
			}
			else {
				return false;
			}
		}

		public boolean isGroup(String group) {
			return _GROUPS.containsKey(group);
		}

		public Enumeration getGroups() {
			return _GROUPS.keys();
		}

		public Enumeration getKeys(String group) {
			//if(!this.isGroup(group)) {
			this.ensureGroup(group);
			//}
			return ((Properties) _GROUPS.get(group)).keys();
			//return null;
		}

		public int sizeOf(String group) {
			if (this.isGroup(group)) {
				return ((Properties) _GROUPS.get(group)).size();
			}
			return 0;
		}

		public int size() {
			return _GROUPS.size();
		}

		public void deleteGroup(String group) {
			_GROUPS.remove(group);
			_ALTERED = true;
		}

		public void deleteProperty(String group, String key) {
			if (this.isGroup(group)) {
				((Properties) _GROUPS.get(group)).remove(key);
				_ALTERED = true;
			}
		}
		public void deleteAll() {
			_GROUPS.clear();
			_ALTERED = true;
		}

		public Properties referenceGroup(String group) {
			if (this.isGroup(group)) {
				return (Properties) _GROUPS.get(group);
			}
			else {
				return null;
			}
		}

		public Properties exportGroup(String group) {
			if (this.isGroup(group)) {
				return (Properties) ((Properties) _GROUPS.get(group)).clone();
			}
			else {
				return null;
			}
		}
		public void importGroup(String group, Properties properties) {
			if (!this.isGroup(group)) {
				Properties p = (Properties) properties.clone();
				_GROUPS.put(group, p);
				_ALTERED = true;
			}
		}
		public void replaceGroup(String group, Properties properties) {
			_GROUPS.put(group, properties);
			_ALTERED = true;
		}
		public void mergRegistry(Registry registry) {
			if (registry instanceof Map) {
				_GROUPS.putAll((Map) registry);
				_ALTERED = true;
			}
		}

		/**
		* Commits the registry data to disk. Registry objects
		* created from streams, or as blanks mush have their file set 
		* before they can be commited.
		* @exception java.io.IOException if the commit fails.
		*/
		public void commit() throws IOException {
			this.storeData();
		}

		/**
		* Reverts to the registry last commited. Registry objects
		* created from streams, or as blanks mush have their file set 
		* and be commited, before they can be reverted.
		* @exception java.io.IOException if the commit fails.
		*/
		public void revert() throws IOException {
			if (_DATA_FILE != null && this.isAltered()) {
				// make sure there is a file to read.
				_DATA_FILE.createNewFile();
				this.loadData();
			}
		}
	}

	protected Registry() {
		super();
	}
	/**
	* Loads a registry with no data.
	* @return com.jmonkey.office.service.Registry
	*/
	public static final Registry blankRegistery() {
		return Registry.instance().new Impl();
	}
	/**
	* Commits the registry data to disk. Registry objects
	* created from streams, or as blanks mush have their file set 
	* before they can be commited.
	* @exception java.io.IOException if the commit fails.
	*/
	public void commit() throws IOException {
		return;
	}
	/**
	* Deletes all data in the Registry.
	*/
	public void deleteAll() {
		return;
	}
	/**
	* Removes the specified group from the Registry.
	* @param group java.lang.String
	*/
	public void deleteGroup(String group) {
		return;
	}
	/**
	* Deletes the specified key from the specified group.
	* @param group java.lang.String
	* @param key java.lang.String the key to delete.
	*/
	public void deleteProperty(String group, String key) {
		return;
	}
	/**
	* Dumps the contents of the registry 
	* to the comsole. Used for debugging.
	*/
	public void dump() {
	}
	/**
	* Creates an MD5 Hash from the input string.
	* @param decrypted java.lang.String
	* @return java.lang.String
	*/
	public static final String encryptMD5(String decrypted) {
		try {
			java.security.MessageDigest md5 =
				java.security.MessageDigest.getInstance("MD5");
			md5.update(decrypted.getBytes());
			byte[] hash = md5.digest();
			md5.reset();
			return hashToHex(hash);
		}
		catch (java.security.NoSuchAlgorithmException nsae0) {
			return null;
		}
	}
	/**
	* This method checks a directory to make sure it 
	* exists. If it doesn't, it is created.
	* @return java.lang.String the absolute path to the ensured directory.
	* @param directory java.lang.String the directory to ensure.
	*/
	public final static String ensureDirectory(String directory) {
		java.io.File dir = new java.io.File(directory);
		if (!dir.exists() || (dir.exists() && !dir.isDirectory())) {
			dir.mkdirs();
		}
		return dir.getAbsolutePath();
	}
	/**
	* Return the group data for the specified group.
	* The group data will no longer be referenced to 
	* the regisatry, as if group.clone() was called.
	* @param group java.lang.String
	* @return java.util.Properties
	*/
	public Properties exportGroup(String group) {
		return null;
	}
	/**
	* Gets a boolean value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param defaultValue boolean
	* @return boolean
	*/
	public boolean getBoolean(
		String group,
		String key,
		boolean defaultValue) {
		return false;
	}
	/**
	* Gets a byte value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param defaultValue byte
	* @return byte
	*/
	public byte getByte(String group, String key, byte defaultValue) {
		return 0;
	}
	/**
	* Gets a byte array value
	* @param group java.lang.String
	* @param key java.lang.String
	* @param defaultValue byte[]
	* @return byte[]
	*/
	public byte[] getByteArray(
		String group,
		String key,
		byte[] defaultValue) {
		return null;
	}
	/**
	* Gets a char value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param defaultValue char
	* @return char
	*/
	public char getChar(String group, String key, char defaultValue) {
		return (char) 0;
	}
	/**
	* Gets a char array value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param defaultValue char[]
	* @return char[]
	*/
	public char[] getCharArray(
		String group,
		String key,
		char[] defaultValue) {
		return null;
	}
	/**
	* Gets a double value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param defaultValue double
	* @return double
	*/
	public double getDouble(String group, String key, double defaultValue) {
		return 0.0;
	}
	/**
	* Gets the file that data will be commited to.
	* @return java.io.File
	*/
	public File getFile() {
		return null;
	}
	/**
	* Gets a float value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param defaultValue float
	* @return float
	*/
	public float getFloat(String group, String key, float defaultValue) {
		return 0.0F;
	}
	/**
	* Return an array of of groups.
	* @return java.lang.String[]
	*/
	public Enumeration getGroups() {
		return null;
	}
	/**
	* Gets an int value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param defaultValue int
	* @return int
	*/
	public int getInteger(String group, String key, int defaultValue) {
		return 0;
	}
	/**
	* Gets an int array value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param defaultValue int[]
	* @return int[]
	*/
	public int[] getIntegerArray(
		String group,
		String key,
		int[] defaultValue) {
		return null;
	}
	/**
	* Return an array of keys for the specified group.
	* @param group java.lang.String
	* @return java.lang.String[]
	*/
	public Enumeration getKeys(String group) {
		return null;
	}
	/**
	* Gets a long value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param defaultValue long
	* @return long
	*/
	public long getLong(String group, String key, long defaultValue) {
		return 0L;
	}
	/**
	* Gets a value The default value will be 
	* added to the Registry, unless it is null. 
	* In the case where the default value is 
	* null, it may or may not be returned 
	* depending on the implementation.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param defaultValue java.lang.Object[]
	* @return java.lang.Object[]
	*/
	public Object[] getObject(
		String group,
		String key,
		Object[] defaultValue) {
		return null;
	}
	/**
	* Gets a value The default value will be 
	* added to the Registry, unless it is null. 
	* In the case where the default value is 
	* null, it may or may not be returned 
	* depending on the implementation.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param defaultValue lava.lang.Object
	* @return java.lang.Object
	*/
	public Object getObject(String group, String key, Object defaultValue) {
		return null;
	}
	/**
	* Gets a short value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param defaultValue short
	* @return short
	*/
	public short getShort(String group, String key, short defaultValue) {
		return 0;
	}
	/**
	* Gets a string value. The default value will be 
	* added to the Registry, unless it is null. 
	* In the case where the default value is 
	* null, it may or may not be returned 
	* depending on the implementation.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param defaultValue java.lang.String
	* @return java.lang.String
	*/
	public String getString(String group, String key, String defaultValue) {
		return null;
	}
	/**
	* Gets a string array value The default value will be 
	* added to the Registry, unless it is null. 
	* In the case where the default value is 
	* null, it may or may not be returned 
	* depending on the implementation.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param defaultValue java.lang.String[]
	* @return java.lang.String[]
	*/
	public String[] getStringArray(
		String group,
		String key,
		String[] defaultValue) {
		return null;
	}
	/**
	* Returns the type of the specified property.
	* @param group java.lang.String
	* @param key java.lang.String
	* @return int
	*/
	public int getType(String group, String key) {
		return Registry.TYPE_CORRUPT;
	}
	/**
	* Converts an array of hash bytes into a hex string.
	* @param byte hash[]
	* @return String
	* @version 1.0
	* @author Julian Yip Aug. 1998 (Modified Brill Pappin Aug. 1998)
	*/
	private static final String hashToHex(byte hash[]) {
		StringBuffer buf = new StringBuffer(hash.length * 2);
		for (int i = 0; i < hash.length; i++) {
			if (((int) hash[i] & 0xff) < 0x10)
				buf.append("0");
			buf.append(Integer.toHexString((int) hash[i] & 0xff));
		}
		return buf.toString();
	}
	/**
	* Imports the specified group. Does nothing 
	* if the group alreay exists. The original data will
	* be cloned before being added to the registry. so
	* that an external reference does not exist.
	* @param group java.lang.String
	* @param java.util.Properties
	*/
	public void importGroup(String group, Properties properties) {
	}
	private static Registry instance() {
		// avoid creating new objects if we can help it.
		if (_INSTANCE == null) {
			_INSTANCE = new Registry();
		}
		return _INSTANCE;
	}
	/**
	* indicates that the registry is no longer in sync 
	* with the stored version. This occures when the contents
	* of the registry have been changed, and are not stored. 
	* Calling commit() will sync the registry with the stored 
	* version.
	*/
	public boolean isAltered() {
		return false;
	}
	/**
	* Tests if the property specified is an array type.
	* @param group java.lang.String
	* @param key java.lang.String
	* @return boolean
	*/
	public boolean isArrayType(String group, String key) {
		return false;
	}
	/**
	* Tests for the existance of the specified group.
	* @param group java.lang.String
	* @return boolean true if it exists, false otherwise.
	*/
	public boolean isGroup(String group) {
		return false;
	}
	/**
	  * Tests for the existance of the specified property 
	  * in the specified group.
	  * @param group java.lang.String
	  * @param key java.lang.String
	  * @return boolean true if it exists, false otherwise.
	  */
	public boolean isProperty(String group, String key) {
		return false;
	}
	/**
	* Tests for the existance of a Registry for the specified class.
	* @param testClass java.lang.Class
	* @return boolean true if the Registry exists, false otherwise.
	*/
	public static final boolean isRegistry(Class testClass) {
		return Registry.isRegistry(testClass.getName(), false);
	}
	/**
	* Tests for the existance of a Registry for the specified name.
	* @param name java.lang.String
	* @return boolean true if the Registry exists, false otherwise.
	*/
	public static final boolean isRegistry(String name) {
		return Registry.isRegistry(name, false);
	}
	/**
	* Tests for the existance of a Registry for the specified name.
	* @param name java.lang.String
	* @return boolean true if the Registry exists, false otherwise.
	*/
	public static final boolean isRegistry(String name, boolean encrypted) {
		if (encrypted) {
			name = encryptMD5(name).toUpperCase();
		}
		return (new File(REGISTRY_DIRECTORY, name)).exists();
	}
	/**
	* Loads a registry guaranteed to be unique and constant 
	* for the specified class. If the Registry does not yet 
	* exist, it will be created.
	* @param requestingClass java.lang.Class the class 
	* requesting a Registry.
	* @return com.jmonkey.office.service.Registry
	* @exception java.io.IOException
	*/
	public static final Registry loadForClass(Class requestingClass)
		throws IOException {
		return Registry.loadForName(requestingClass.getName(), false);
	}
	/**
	* Loads a registry with the specified name.
	* If the Registry does not yet exist, it 
	* will be created.
	* @param name java.lang.String the name of the Registry.
	* @return com.jmonkey.office.service.Registry
	* @exception java.io.IOException
	*/
	public static final Registry loadForName(String name) throws IOException {
		// was thinking of encrypting the registry names...
		return Registry.loadForName(name, false);
	}
	/**
	* Loads a registry with the specified name.
	* If the Registry does not yet exist, it 
	* will be created.
	* @param name java.lang.String the name of the Registry.
	* @param encrypted boolean specify that the name should be MD% encrypted.
	* @return com.jmonkey.office.service.Registry
	* @exception java.io.IOException
	*/
	public static final Registry loadForName(String name, boolean encrypted)
		throws IOException {
		//File file = new File(ServiceRuntime.REGISTRY_DIRECTORY, ServiceRuntime.encryptMD5(service.getClass().getName()).toUpperCase());
		// was thinking of encrypting the registry names...
		if (encrypted) {
			name = encryptMD5(name).toUpperCase();
		}
		File file = new File(REGISTRY_DIRECTORY, name);
		return Registry.instance().new Impl(file);
	}
	/**
	* Loads a registry that reads its data from the specified Reader.
	* @param reader java.io.Reader
	* @return com.jmonkey.office.service.Registry
	* @exception java.io.IOException
	*/
	public static final Registry loadForReader(Reader reader)
		throws IOException {
		return Registry.instance().new Impl(reader);
	}
	/**
	* adds the specified Registry to this Registry, 
	* The current registry will have its elements replaced, 
	* if there are any duplicate keys.
	* @param com.jmonkey.office.service.Registry
	*/
	public void mergRegistry(Registry registry) {
	}
	/**
	* Reads data into this Registry from the specified Reader.
	* Any values previously stored in the Registry will be removed first.
	  * @param reader java.io.Reader
	  * @exception java.io.IOException
	*/
	public void read(Reader reader) throws IOException {
	}
	/**
	* Return the group data for the specified group.
	* The group will maintain its reference in the 
	* registry. This is used to change the group
	* externally fromt he registry. Not recomended
	* @param group java.lang.String
	* @return java.util.Properties
	*/
	public Properties referenceGroup(String group) {
		return null;
	}
	/**
	* Replaces the Registry in group, with the specified Registry.
	* @param group java.lang.String the group to replace.
	* @param properties java.util.Properties the group data.
	*/
	public void replaceGroup(String group, Properties properties) {
	}
	/**
	* Reverts to the registry last commited. Registry objects
	* created from streams, or as blanks mush have their file set 
	* and be commited, before they can be reverted.
	* @exception java.io.IOException if the commit fails.
	*/
	public void revert() throws IOException {
		return;
	}
	/**
	* Sets the file that data will be commited to.
	* @param file java.io.File
	*/
	public void setFile(File file) {
	}
	/**
	* Sets a byte array value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param value byte[]
	*/
	public void setProperty(String group, String key, byte[] value) {
		return;
	}
	/**
	* Sets a char array value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param value char[]
	*/
	public void setProperty(String group, String key, char[] value) {
		return;
	}
	/**
	* Sets an int array value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param value int[]
	*/
	public void setProperty(String group, String key, int[] value) {
		return;
	}
	/**
	* Sets a value
	* @param group java.lang.String
	* @param key java.lang.String
	* @param value lava.lang.Object[]
	*/
	public void setProperty(String group, String key, Object[] value) {
		return;
	}
	/**
	* Sets a string array value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param value java.lang.String[]
	*/
	public void setProperty(String group, String key, String[] value) {
		return;
	}
	/**
	* Sets a byte value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param value byte
	*/
	public void setProperty(String group, String key, byte value) {
		return;
	}
	/**
	* Sets a char value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param value char
	*/
	public void setProperty(String group, String key, char value) {
		return;
	}
	/**
	* Sets a double value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param value double
	*/
	public void setProperty(String group, String key, double value) {
		return;
	}
	/**
	* Sets a float value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param value float
	*/
	public void setProperty(String group, String key, float value) {
		return;
	}
	/**
	* Sets an int value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param value int
	*/
	public void setProperty(String group, String key, int value) {
		return;
	}
	/**
	* Sets a long value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param value long
	*/
	public void setProperty(String group, String key, long value) {
		return;
	}
	/**
	* Sets a value
	* @param group java.lang.String
	* @param key java.lang.String
	* @param value lava.lang.Object
	*/
	public void setProperty(String group, String key, Object value) {
		return;
	}
	/**
	  * Sets a string value.
	  * @param group java.lang.String
	  * @param key java.lang.String
	  * @param value java.lang.String
	  */
	public void setProperty(String group, String key, String value) {
		return;
	}
	/**
	* Sets a short value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param value short
	*/
	public void setProperty(String group, String key, short value) {
		return;
	}
	/**
	* Sets a boolean value.
	* @param group java.lang.String
	* @param key java.lang.String
	* @param value boolean
	*/
	public void setProperty(String group, String key, boolean value) {
		return;
	}
	/**
	* Returns the number of 
	* groups in the registry.
	* @return int
	*/
	public int size() {
		return 0;
	}
	/**
	* Returns the number of properties 
	* in the specified group.
	* @param group java.lang.String
	* @return int
	*/
	public int sizeOf(String group) {
		return 0;
	}
	/**
	* Writes the data in this Registry to the specified Writer.
	* @param writer java.io.Writer
	* @exception java.io.IOException
	*/
	public void write(Writer writer) throws IOException {
	}
}
