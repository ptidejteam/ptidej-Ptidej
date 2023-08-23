/**
 * @author Mathieu Lemoine
 * @created 2008-10-20 (月)
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


import jct.test.rsc.jct.kernel.IJCTPath;
import jct.test.rsc.jct.kernel.IJCTPathPart;
import jct.test.rsc.jct.kernel.JCTKind;


/**
 * Interface representing a path from the root node to a specific element of a model
 * 
 * Default implementation for {@link jct.test.rsc.jct.kernel.IJCTPath}
 *
 * @author Mathieu Lemoine
 */
public class JCTPath extends JCTPathPart implements IJCTPath
{
    public JCTPath()
    {
        super(JCTKind.ROOT_NODE, null);
    }
    
    JCTPath(final JCTKind resultKind, final Integer index)
    {
        super(resultKind, index);
    }
    
    JCTPath(final JCTKind resultKind, final Integer index, final String data)
    {
        super(resultKind, index, data);
    }
    
    JCTPath(final JCTKind resultKind, final Integer index, final String data, final byte[] informativeData)
    {
        super(resultKind, index, data, informativeData);
    }
    
    /**
     * Returns the first part of this path
     */
    @Override
	public IJCTPathPart getFirstPart()
    {
        return super.getNextPart();
    }
    
    /**
     * Returns the path to the enclosing element.
     */
    @Override
	public IJCTPath getPathToEnclosing()
    {
        final IJCTPath result = new JCTPath();
        result.addPart(null == this.getFirstPart() ? null : ((JCTPathPart)this.getFirstPart()).getPathPartToEnclosing());
        return result;
    }
    
    /**
     * Return the data of the element designated by this path
     */
    @Override
    public JCTKind getResultKind()
    {
        return this == this.getLastPart() ? super.getResultKind() : this.getLastPart().getResultKind();
    }
    
    /**
     * Return the index of the element designated by this path
     */
    @Override
    public String getData()
    {
        return this == this.getLastPart() ? super.getData() : this.getLastPart().getData();
    }
    
    /**
     * Return the kind of the element designated by this path
     */
    @Override
    public Integer getIndex()
    {
        return this == this.getLastPart() ? super.getIndex() : this.getLastPart().getIndex();
    }
    
    /**
     * Return the informative data of this path
     */
    @Override
    public byte[] getInformativeData()
    {
        return this == this.getLastPart() ? super.getInformativeData() : this.getLastPart().getInformativeData();
    }
    
    /**
     * Returns a clone of this path part
     */
    @Override
    public JCTPath clone()
    {
        return (JCTPath)super.clone();
    }
    
    @Override
    public boolean equals(final Object that)
    {
        if(this == that)
            return true;
        
        if(!(that instanceof IJCTPath))
            return false;
        
        IJCTPathPart thisPart = this.getFirstPart();
        IJCTPathPart thatPart = ((IJCTPath)that).getFirstPart();
        
        while(null != thisPart)
            if(!thisPart.equals(thatPart))
                return false;
            else
            {
                thisPart = thisPart.getNextPart();
                thatPart = thatPart.getNextPart();
            }
        
        return null == thatPart;
    }
    
    /**
     * Returns whether this is designating an (indirect) element of that
     */
    @Override
	public boolean isEnclosing(final IJCTPath that)
    {
        IJCTPathPart thisPart = this.getFirstPart();
        IJCTPathPart thatPart = that.getFirstPart();
        
        while(null != thisPart)
            if(!thisPart.equals(thatPart))
                return false;
            else
            {
                thisPart = thisPart.getNextPart();
                thatPart = thatPart.getNextPart();
            }
        
        return true;
    }
    

}
