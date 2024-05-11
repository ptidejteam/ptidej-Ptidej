/**
 * @author Mathieu Lemoine
 * @created 2009-01-31 (土)
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

package jct.test.rsc.jct.kernel.impl;



import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTElementContainer;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTSourceCodePart;
import jct.test.rsc.jct.kernel.IJCTStatement;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;


/**
 * This class implements the Do While and While interfaces
 *
 * @author Mathieu Lemoine
 */
abstract class JCTDoWhileImpl extends JCTSourceCodePart<IJCTElement> implements IJCTElementContainer<IJCTElement>, IJCTSourceCodePart
{
    /**
     * condition of this do while impl
     */
    private final NotNullableReference<IJCTExpression> condition;
    
    /**
     * body of this do while impl
     */
    private final NotNullableReference<IJCTStatement> body;
    
    
    JCTDoWhileImpl(final IJCTRootNode aRootNode, final IJCTExpression condition)
    {
        super(aRootNode);
        this.condition = this.createInternalReference(condition);
        this.body = this.createInternalReference(((IJCTStatement)this.getRootNode().getFactory().createEmptyStatement()));
        super.backpatchElements(new IndirectCollection<IJCTElement>(this.condition, this.body));
    }
    
    /**
     * Modifies the condition of this do while impl
     *
     * @param condition the new condition
     */
    public void setCondition(final IJCTExpression condition)
    {
        this.condition.set(condition);
    }
    
    /**
     * Returns the condition of this do while impl
     * <em>Included in the enclosed elements.</em>
     */
    public IJCTExpression getCondition()
    {
        return this.condition.get();
    }
    
    /**
     * Modifies the body of this do while impl
     *
     * @param body the new body
     */
    public void setBody(final IJCTStatement body)
    {
        this.body.set(body);
    }
    
    /**
     * Returns the body of this do while impl
     * <em>Included in the enclosed elements.</em>
     */
    public IJCTStatement getBody()
    {
        return this.body.get();
    }
    

}
