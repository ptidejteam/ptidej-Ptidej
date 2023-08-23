/**
 * @author Mathieu Lemoine
 * @created 2008-10-06 (月)
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
 * Interface representing a member select expression ({@code selector.member})
 * used <strong>only</strong> (i.e. the methods <strong>must</strong>
 * throw an UnsupportedOperationException or an IllegalArgumentException otherwise)
 * when a {@link IJCTSimpleSelector} is not usable
 * (i.e. when a qualifying expression is required to resolve the element).
 *
 * @author Mathieu Lemoine
 */
public interface IJCTMemberSelector<Member extends IJCTClassMember> extends IJCTSelector<Member>, IJCTElementContainer<IJCTExpression>
{
    /**
     * Returns the element
     *
     * @return null iff the element is not resolved, ~
                     which is possible only in case of an erroneous tree
     */
    @Override
	public Member getElement();
    
    /**
     * Modifies the resolved element
     *
     * @throws UnsupportedOperationException iff erroneous selector
     */
    @Override
	public void setElement(final Member anElement);
    
    /**
     * Modifies the qualifying expression of this member selector
     *
     * @param qualifyingExpression the new qualifying expression
     */
    public void setQualifyingExpression(final IJCTExpression qualifyingExpression);
    
    /**
     * Returns the qualifying expression of this member selector
     * <em>Included in the enclosed elements.</em>
     */
    public IJCTExpression getQualifyingExpression();
    
    /**
     * Modifies the member selector of this member selector
     *
     * @param memberSelector the new member selector
     */
    public void setMemberSelector(final IJCTSimpleSelector<Member> memberSelector);
    
    /**
     * Returns the member selector of this member selector
     * <em>Included in the enclosed elements.</em>
     */
    public IJCTSimpleSelector<Member> getMemberSelector();
    

}
