/**
 * @author Mathieu Lemoine
 * @created 2008-08-17 (日)
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
package jct.kernel;

/**
 * Enum containing all the kinds of modifiers in a Javac AST
 * 
 * @author Mathieu Lemoine
 * @author Yann-Gaël Guéhéneuc
 */
// todo : implements more uncompatibility, like context-dependent modifiers
// TODO : add public get on flag, make it OR-able, move incompatibility to
// ClassMember descendants !!
public enum JCTModifiers {
	ABSTRACT(0x001, "ABSTRACT"), DEFAULT(0x800, "DEFAULT"),
	FINAL(0x002, "FINAL"), NATIVE(0x004, "NATIVE"),
	NONSEALED(0x2000, "NON-SEALED"), PRIVATE(0x008, "PRIVATE"),
	PROTECTED(0x010, "PROTECTED"), PUBLIC(0x020, "PUBLIC"),
	SEALED(0x1000, "SEALED"), STATIC(0x040, "STATIC"),
	STRICTFP(0x080, "STRICTFP"), SYNCHRONIZED(0x100, "SYNCHRONIZED"),
	TRANSIENT(0x200, "TRANSIENT"), VOLATILE(0x400, "VOLATILE");

	private final int flag;

	private JCTModifiers(final int flag, final String name) {
		this.flag = flag;
	}

	public int getFlag() {
		return this.flag;
	}
}
