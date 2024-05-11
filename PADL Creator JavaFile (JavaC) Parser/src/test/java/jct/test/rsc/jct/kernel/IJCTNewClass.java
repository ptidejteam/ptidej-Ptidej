/**
 * @author Mathieu Lemoine
 * @created 2009-01-0 8 (木)
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
 * This class represents a new class expression
 *
 * @author Mathieu Lemoine
 */
public interface IJCTNewClass extends IJCTExpression, IJCTElementContainer<IJCTElement>
{
    /**
     * Modifies the class type of this new class
     *
     * @param classType the new class type
     */
    public void setClassType(final IJCTClassType classType);
    
    /**
     * Returns the class type of this new class
     */
    public IJCTClassType getClassType();
    
    /**
     * Modifies the annonymous class of this new class
     *
     * @param annonymousClass the new annonymous class, can be {@code null}
     */
    public void setAnnonymousClass(final IJCTClass annonymousClass);
    
    /**
     * Returns the annonymous class of this new class
     * <em>Included in the enclosed elements.</em>
     *
     * @return null iff there is no annonymous class
     */
    public IJCTClass getAnnonymousClass();
    
    /**
     * Modifies the selecting expression of this new class
     *
     * @param selectingExpression the new selecting expression, can be {@code null}
     */
    public void setSelectingExpression(final IJCTExpression selectingExpression);
    
    /**
     * Returns the selecting expression of this new class
     * <em>Included in the enclosed elements.</em>
     *
     * @return null iff there is no selecting expression
     */
    public IJCTExpression getSelectingExpression();
    
    /**
     * Adds a "argument" at the index (or move it there)
     */
    public void addArgument(final int anIndex, final IJCTExpression argument);
    
    /**
     * Adds a "argument" at the end of the list (or move it there)
     */
    public void addArgument(final IJCTExpression argument);
    
    /**
     * Removes this argument from the list
     */
    public void removeArgument(final IJCTExpression argument);
    
    /**
     * Remove the argument at the index
     */
    public void removeArgument(final int anIndex);
    
    /**
     * Returns the list of arguments of this new class
     * <em>Part of the enclosed elements.</em>
     */
    public List<IJCTExpression> getArguments();
    

}
