/**
 * @author Mathieu Lemoine
 * @created 2009-01-08 (木)
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

package jct.kernel.impl;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import jct.kernel.IJCTBlock;
import jct.kernel.IJCTCatch;
import jct.kernel.IJCTElement;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTTry;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;
import jct.util.collection.IndirectCollection;
import jct.util.reference.NotNullableReference;
import jct.util.reference.NullableReference;

/**
 * This class represents a try statement
 * 
 * Default implementation for {@link jct.kernel.IJCTTry}
 *
 * @author Mathieu Lemoine
 */
class JCTTry extends JCTSourceCodePart<IJCTElement> implements IJCTTry {
	/**
	 * try block of this try
	 */
	private final NotNullableReference<IJCTBlock> tryBlock;

	/**
	 * List of catch blocks of this try
	 */
	private final List<IJCTCatch> catchBlocks = this.createInternalList();

	/**
	 * finally block of this try
	 */
	private final NullableReference<IJCTBlock> finallyBlock =
		this.createNullableInternalReference();

	JCTTry(final IJCTRootNode aRootNode) {
		super(aRootNode);
		this.tryBlock =
			this.createInternalReference(this
				.getRootNode()
				.getFactory()
				.createBlock());
		super.backpatchElements(new IndirectCollection<IJCTElement>(
			this.tryBlock,
			this.catchBlocks,
			this.finallyBlock));
	}

	/**
	 * Modifies the try block of this try
	 *
	 * @param tryBlock the new try block
	 */
	public void setTryBlock(final IJCTBlock tryBlock) {
		this.tryBlock.set(tryBlock);
	}

	/**
	 * Returns the try block of this try
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTBlock getTryBlock() {
		return this.tryBlock.get();
	}

	/**
	 * Adds a "catch block" at the index (or move it there)
	 */
	public void addCatchBlock(final int anIndex, final IJCTCatch catchBlock) {
		this.catchBlocks.add(anIndex, catchBlock);
	}

	/**
	 * Adds a "catch block" at the end of the list (or move it there)
	 */
	public void addCatchBlock(final IJCTCatch catchBlock) {
		this.catchBlocks.add(catchBlock);
	}

	/**
	 * Removes this catch block from the list
	 */
	public void removeCatchBlock(final IJCTCatch catchBlock) {
		this.catchBlocks.remove(catchBlock);
	}

	/**
	 * Remove the catch block at the index
	 */
	public void removeCatchBlock(final int anIndex) {
		this.catchBlocks.remove(anIndex);
	}

	/**
	 * Returns the list of catch blocks of this try
	 * <em>Part of the enclosed elements.</em>
	 */
	public List<IJCTCatch> getCatchBlocks() {
		return Collections.unmodifiableList(this.catchBlocks);
	}

	/**
	 * Modifies the finally block of this try
	 *
	 * @param finallyBlock the new finally block, can be {@code null}
	 */
	public void setFinallyBlock(final IJCTBlock finallyBlock) {
		this.finallyBlock.set(finallyBlock);
	}

	/**
	 * Returns the finally block of this try
	 * <em>Included in the enclosed elements.</em>
	 *
	 * @return null iff there is no finally block
	 */
	public IJCTBlock getFinallyBlock() {
		return this.finallyBlock.get();
	}

	/**
	 * Returns the kind of this constituent (JCTKind.TRY)
	 */
	public JCTKind getKind() {
		return JCTKind.TRY;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitTry(this, aP);
	}

	public Writer getSourceCode(final Writer aWriter) throws IOException {
		aWriter.append("try");
		this.getTryBlock().getSourceCode(aWriter);

		for (final IJCTCatch c : this.getCatchBlocks())
			c.getSourceCode(aWriter);

		if (null != this.getFinallyBlock()) {
			if (0 == this.getCatchBlocks().size())
				aWriter.append('\n');

			aWriter.append("finally");
			this.getFinallyBlock().getSourceCode(aWriter);
		}

		return aWriter;
	}

	private static final long serialVersionUID = 3699474650123258854L;

}
