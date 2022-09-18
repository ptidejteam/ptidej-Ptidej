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

package jct.test.rsc.jct.kernel;


import java.util.List;


/**
 * This class represents a try statement
 *
 * @author Mathieu Lemoine
 */
public interface IJCTTry extends IJCTStatement, IJCTElementContainer<IJCTElement>
{
    /**
     * Modifies the try block of this try
     *
     * @param tryBlock the new try block
     */
    public void setTryBlock(final IJCTBlock tryBlock);
    
    /**
     * Returns the try block of this try
     * <em>Included in the enclosed elements.</em>
     */
    public IJCTBlock getTryBlock();
    
    /**
     * Adds a "catch block" at the index (or move it there)
     */
    public void addCatchBlock(final int anIndex, final IJCTCatch catchBlock);
    
    /**
     * Adds a "catch block" at the end of the list (or move it there)
     */
    public void addCatchBlock(final IJCTCatch catchBlock);
    
    /**
     * Removes this catch block from the list
     */
    public void removeCatchBlock(final IJCTCatch catchBlock);
    
    /**
     * Remove the catch block at the index
     */
    public void removeCatchBlock(final int anIndex);
    
    /**
     * Returns the list of catch blocks of this try
     * <em>Part of the enclosed elements.</em>
     */
    public List<IJCTCatch> getCatchBlocks();
    
    /**
     * Modifies the finally block of this try
     *
     * @param finallyBlock the new finally block, can be {@code null}
     */
    public void setFinallyBlock(final IJCTBlock finallyBlock);
    
    /**
     * Returns the finally block of this try
     * <em>Included in the enclosed elements.</em>
     *
     * @return null iff there is no finally block
     */
    public IJCTBlock getFinallyBlock();
    

}
