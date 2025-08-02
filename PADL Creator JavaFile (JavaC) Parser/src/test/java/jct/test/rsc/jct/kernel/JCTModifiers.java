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
package jct.test.rsc.jct.kernel;

/**
 * Enum containing all the kinds of modifiers in a Javac AST
 *
 * @author Mathieu Lemoine
 */
// todo : implements more uncompatibility, like context-dependent modifiers
// TODO : add public get on flag, make it OR-able, move incompatibility to ClassMember descendants !!
public enum JCTModifiers {
	ABSTRACT(0x001), FINAL(0x002), NATIVE(0x004), PRIVATE(0x008),
	PROTECTED(0x010), PUBLIC(0x020), STATIC(0x040), STRICTFP(0x080),
	SYNCHRONIZED(0x100), TRANSIENT(0x200), VOLATILE(0x400);

	private final int flag;

	private JCTModifiers(final int flag) {
		this.flag = flag;
	}

	/**
	 * Returns the flag of this modifier.
	 * The flags are such that they can be combined using a bit-wise OR (|)
	 */
	public int getFlag() {
		return this.flag;
	}
}
