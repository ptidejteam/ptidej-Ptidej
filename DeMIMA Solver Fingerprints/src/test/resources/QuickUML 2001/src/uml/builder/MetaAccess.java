/**
 *
    QuickUML; A simple UML tool that demonstrates one use of the 
    Java Diagram Package 

    Copyright (C) 2001  Eric Crahen <crahen@cse.buffalo.edu>

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

 */

package uml.builder;

/**
 * @class MetaAccess
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * This class helps manipulate access modifiers
 */
public abstract class MetaAccess {

  public static int NONE      = 0x00;
  public static int PRIVATE   = 0x01;
  public static int PROTECTED = 0x02;
  public static int PUBLIC    = 0x04;
  public static int STATIC    = 0x08;
  public static int ABSTRACT  = 0x10;

  /**
   * Reduce a set of flags to the smallest reasonable representation.
   * Only considers the general access modifiers (public, protected, ...)
   */
  public static int compress(int access) {

    if(isPublic(access))
      access &= ~PROTECTED & ~PRIVATE;

    else if(isProtected(access))
      access &= ~PROTECTED & ~PRIVATE;

    return access;

  }

  /**
   * Test two sets of access flags are complimentary.
   *
   * @return boolean
   */
  public static boolean isComplementary(int a, int b) {

    // static && abstract are not valid
    return !((a == b) || (isAbstract(a) && isStatic(b)) || (isAbstract(b) && isStatic(a)));

  }

  /**
   * Test two sets of access flags to see a is compatible (can override) with b.
   *
   * @return boolean
   */
  public static boolean isCompatible(int a, int b) {

    if(isStatic(a) != isStatic(b))
      return false;

    if(isAbstract(a) && !isAbstract(b))
      return false;

    // Can not make more private
    if((isPublic(b) && !isPublic(a)) || (isProtected(b) && isPrivate(a)))
      return false;

    return true;

  }

  public static boolean isPublic(int access) {
    return (access & PUBLIC) != 0;
  }

  public static boolean isProtected(int access) {
    return (access & PROTECTED) != 0;
  }

  public static boolean isPrivate(int access) {
    return (access & PRIVATE) != 0;
  }

  public static boolean isAbstract(int access) {
    return (access & ABSTRACT) != 0;
  }

  public static boolean isStatic(int access) {
    return (access & STATIC) != 0;
  }

  /**
   * Check a set of flags to be sure they are valid
   */
  public static boolean isValid(int access) {

    if(isAbstract(access) && isStatic(access))
      return false;

    return !((isPublic(access) && isProtected(access)) || (isPrivate(access) && isProtected(access)) || (isPublic(access) && isPrivate(access)));

  }

  /**
   */
  public static int parse(String access) {

    int x = NONE;

    if(access.indexOf("public") >= 0)
      x |= PUBLIC;

    if(access.indexOf("protected") >= 0)
      x |= PROTECTED;

    if(access.indexOf("private") >= 0)
      x |= PRIVATE;

    if(access.indexOf("abstract") >= 0)
      x |= ABSTRACT;

    if(access.indexOf("static") >= 0)
      x |= STATIC;

    return x;

  }

  /**
   * Convert a set of access flags into a String
   *
   * @return String
   */
  public static String toString(int access) {

    StringBuffer buf = null;

    if(isPrivate(access))
      buf = append(buf, "private");

    if(isProtected(access))
      buf = append(buf, "protected");

    if(isPublic(access))
      buf = append(buf, "public");

    if(isStatic(access))
      buf = append(buf, "static");

    if(isAbstract(access))
      buf = append(buf, "abstract");

    return (buf == null) ? "" : buf.toString();

  }

  /**
   * Used internally to construct the access String
   */
  private static StringBuffer append(StringBuffer buf, String s) {

    if(buf != null)
      buf.append(' ').append(s);
    else
      buf = new StringBuffer(s);

    return buf;

  }

}
