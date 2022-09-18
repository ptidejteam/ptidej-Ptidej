/**
 * @author Mathieu Lemoine
 * @created 2009-03-03 (火)
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

import jct.test.rsc.jct.kernel.JCTKind;


/**
 * This class builds a {@link JCTPathPart}.
 *
 * @author Mathieu Lemoine
 */
class JCTPathPartBuilder 
{
    /**
     * kind of this path part builder
     */
    private JCTKind kind;
    
    /**
     * index of this path part builder
     */
    private Integer index = null;
    
    /**
     * data of this path part builder
     */
    private String data = null;
    
    /**
     * informative data of this path part builder
     */
    private byte[] informativeData = null;
    public JCTPathPartBuilder(final JCTKind aKind)
    { this.kind = aKind; }
    
    public JCTPathPart createPathPart()
    { return this.kind == null ? null : new JCTPathPart(this.kind, this.index, this.data, this.informativeData); }
    
    /**
     * Modifies the kind of this path part builder
     *
     * @param kind the new kind
     * @return {@code this}
     */
    public JCTPathPartBuilder setKind(final JCTKind kind)
    {
        this.kind = kind;
        return this;
    }
    
    /**
     * Returns the kind of this path part builder
     */
    public JCTKind getKind()
    {
        return this.kind;
    }
    
    /**
     * Modifies the index of this path part builder
     *
     * @param index the new index
     * @return {@code this}
     */
    public JCTPathPartBuilder setIndex(final Integer index)
    {
        this.index = index;
        return this;
    }
    
    /**
     * Returns the index of this path part builder
     */
    public Integer getIndex()
    {
        return this.index;
    }
    
    /**
     * Modifies the data of this path part builder
     *
     * @param data the new data
     * @return {@code this}
     */
    public JCTPathPartBuilder setData(final String data)
    {
        this.data = data;
        return this;
    }
    
    /**
     * Returns the data of this path part builder
     */
    public String getData()
    {
        return this.data;
    }
    
    /**
     * Modifies the informative data of this path part builder
     *
     * @param informativeData the new informative data
     * @return {@code this}
     */
    public JCTPathPartBuilder setInformativeData(final byte[] informativeData)
    {
        this.informativeData = informativeData;
        return this;
    }
    
    /**
     * Returns the informative data of this path part builder
     */
    public byte[] getInformativeData()
    {
        return this.informativeData;
    }

}
