/**
 * @author Mathieu Lemoine
 * @created 2008-08-24 (日)
 *
 * Licensed under 3-clause BSD License:
 * Copyright © 2009, Mathieu Lemoine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of Mathieu Lemoine nor the
 *    names of contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Mathieu Lemoine ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Mathieu Lemoine BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package jct.test.rsc.jct.kernel;



/**
 * Enum representing primitive types
 *
 * @author Mathieu Lemoine
 */
public enum JCTPrimitiveTypes
{
    VOID("V", "void"),
    CHARACTER("C", "char"),
    BOOLEAN("Z", "boolean"),
    BYTE("B", "byte"),
    SHORT("S", "short"),
    INTEGER("I", "int"),
    LONG("L", "long"),
    FLOAT("F", "float"),
    DOUBLE("D", "double");

    private final String name;
    private final String sourceCode;

    JCTPrimitiveTypes(final String name, final String sourceCode)
    {
        this.name = name;
        this.sourceCode = sourceCode;
    }

    public String getTypeName()
    { return this.name; }

    public String getSourceCode()
    { return this.sourceCode; }

    public static JCTPrimitiveTypes resolveType(final String n)
    {
        for(final JCTPrimitiveTypes t : JCTPrimitiveTypes.values())
            if(n.equals(t.getTypeName()))
                return t;
        throw new IllegalArgumentException("Invalid Primitive Type Name");
    }
}
