/* Decompiler 158ms, total 328ms, lines 234 */
package com.ibm.toad.cfparse;

import com.ibm.toad.cfparse.attributes.AttrInfoList;
import com.ibm.toad.cfparse.attributes.SourceFileAttrInfo;
import com.ibm.toad.cfparse.utils.Access;
import com.ibm.toad.cfparse.utils.CPUtils;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;

public final class ClassFile {
   private int d_magic;
   private int d_minorVersion;
   private int d_majorVersion;
   private int d_accessFlags;
   private int d_thisClass;
   private int d_superClass;
   private ConstantPool d_constants;
   private InterfaceList d_interfaces;
   private FieldInfoList d_fields;
   private MethodInfoList d_methods;
   private AttrInfoList d_attributes;

   public InterfaceList getInterfaces() {
      return this.d_interfaces;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("Magic: 0x" + Integer.toHexString(this.d_magic) + "\n" + "Compiler Version: " + this.d_majorVersion + "." + this.d_minorVersion + "\n");
      var1.append(this.d_constants.toString() + "Class: \n" + Access.getClassAsString(this.d_accessFlags));
      String var2 = this.d_constants.getAsString(this.d_thisClass);
      String var3 = CPUtils.slashes2dots(var2);
      var1.append(var3);
      if (this.d_superClass != 0) {
         var2 = this.d_constants.getAsString(this.d_superClass);
         var3 = CPUtils.slashes2dots(var2);
         var1.append(" extends " + var3);
      }

      var1.append("\n");
      var1.append("" + this.d_interfaces + "\n" + this.d_fields + "\n" + this.d_methods + "\n" + this.d_attributes + "\n");
      return var1.toString();
   }

   public ClassFile() {
      this.d_constants = new ConstantPool();
      this.d_interfaces = new InterfaceList(this.d_constants);
      this.d_fields = new FieldInfoList(this.d_constants);
      this.d_methods = new MethodInfoList(this.d_constants);
      this.d_attributes = new AttrInfoList(this.d_constants, 1);
      this.d_magic = -889275714;
      this.d_minorVersion = 3;
      this.d_majorVersion = 45;
      this.d_accessFlags = 33;
      this.d_thisClass = this.d_constants.addClass("Default");
      this.d_superClass = this.d_constants.addClass("java.lang.Object");
   }

   public ClassFile(String var1) throws FileNotFoundException, IOException {
      this((InputStream)(new FileInputStream(var1)));
   }

   public ClassFile(InputStream var1) throws IOException {
      this(var1, (ConstantPool)null);
   }

   public ClassFile(InputStream var1, ConstantPool var2) throws IOException {
      DataInputStream var3 = new DataInputStream(var1);
      this.d_constants = var2 == null ? new ConstantPool() : var2;
      this.d_interfaces = new InterfaceList(this.d_constants);
      this.d_fields = new FieldInfoList(this.d_constants);
      this.d_methods = new MethodInfoList(this.d_constants);
      this.d_attributes = new AttrInfoList(this.d_constants, 1);
      this.d_magic = var3.readInt();
      this.d_minorVersion = var3.readShort();
      this.d_majorVersion = var3.readShort();
      if (var2 == null) {
         this.d_constants.read(var3);
      } else {
         ConstantPool var4 = new ConstantPool();
         var4.read(var3);
      }

      this.d_accessFlags = var3.readShort();
      this.d_thisClass = var3.readShort();
      this.d_superClass = var3.readShort();
      this.d_interfaces.read(var3);
      this.d_fields.read(var3);
      this.d_methods.read(var3);
      this.d_attributes.read(var3);
   }

   public void sortCF(int[] var1) {
      this.d_thisClass = var1[this.d_thisClass];
      this.d_superClass = var1[this.d_superClass];
   }

   public int getAccess() {
      return this.d_accessFlags;
   }

   public String getSuperName() {
      return this.d_superClass == 0 ? "" : this.d_constants.getAsJava(this.d_superClass);
   }

   public void setSuperName(String var1) {
      var1 = var1.replace('.', '/');
      this.d_constants.editClass(this.d_superClass, var1);
   }

   public void setAccess(int var1) {
      this.d_accessFlags = var1;
   }

   public void sort(int[] var1) {
      this.d_constants.sort(var1);
      this.d_thisClass = var1[this.d_thisClass];
      this.d_superClass = var1[this.d_superClass];
      this.d_interfaces.sort(var1);
      this.d_fields.sort(var1);
      this.d_methods.sort(var1);
      this.d_attributes.sort(var1);
   }

   public int getMagic() {
      return this.d_magic;
   }

   public void setMagic(int var1) {
      this.d_magic = var1;
   }

   public MethodInfoList getMethods() {
      return this.d_methods;
   }

   public FieldInfoList getFields() {
      return this.d_fields;
   }

   public void write(String var1) throws FileNotFoundException, IOException {
      File var2 = new File(var1);
      File var3 = var2.getParentFile();
      if (var3 != null) {
         var3.mkdirs();
      }

      var2.createNewFile();
      DataOutputStream var4 = new DataOutputStream(new FileOutputStream(var2));
      this.write(var4);
   }

   public AttrInfoList getAttrs() {
      return this.d_attributes;
   }

   public void write(DataOutputStream var1) throws IOException {
      var1.writeInt(this.d_magic);
      var1.writeShort(this.d_minorVersion);
      var1.writeShort(this.d_majorVersion);
      this.d_constants.write(var1);
      var1.writeShort(this.d_accessFlags);
      var1.writeShort(this.d_thisClass);
      var1.writeShort(this.d_superClass);
      this.d_interfaces.write(var1);
      this.d_fields.write(var1);
      this.d_methods.write(var1);
      this.d_attributes.write(var1);
   }

   public BitSet usesCF() {
      BitSet var1 = new BitSet(this.d_constants.length());
      var1.set(this.d_thisClass);
      var1.set(this.d_superClass);
      return var1;
   }

   public int getMajor() {
      return this.d_majorVersion;
   }

   public void setMajor(int var1) {
      this.d_majorVersion = var1;
   }

   public BitSet uses() {
      BitSet var1 = new BitSet(this.d_constants.length());
      var1.set(this.d_thisClass);
      var1.set(this.d_superClass);
      var1.or(this.d_interfaces.uses());
      var1.or(this.d_constants.uses());
      var1.or(this.d_methods.uses());
      var1.or(this.d_fields.uses());
      var1.or(this.d_attributes.uses());
      return var1;
   }

   public String getName() {
      return this.d_constants.getAsJava(this.d_thisClass);
   }

   public void setName(String var1) {
      var1 = var1.replace('.', '/');
      this.d_constants.editClass(this.d_thisClass, var1);
   }

   public int getMinor() {
      return this.d_minorVersion;
   }

   public void setMinor(int var1) {
      this.d_minorVersion = var1;
   }

   public ConstantPool getCP() {
      return this.d_constants;
   }

   public void setCP(ConstantPool var1) {
      this.d_constants = var1;
   }

   public String getSourceFilename() {
      SourceFileAttrInfo var1 = (SourceFileAttrInfo)this.d_attributes.get("SourceFile");
      return var1 == null ? null : var1.get();
   }
}
