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

/**
 * This class represents an import
 * Imports respect Java restrictions:
 * <ul>
 * <li>if an import is on demand and static, the imported element must be a top level class.</li>
 * <li>if an import is on demand but not static, the imported element must be a package</li>
 * <li>if an import is static but not on demand, the imported element must be a static class member</li>
 * <li>if an import is neither static nor on demand, the imported element must be a class</li>
 * </ul>
 *
 * @author Mathieu Lemoine
 */
public interface IJCTImport extends IJCTSourceCodePart
{
    /**
     * Modifies the imported element of this import
     *
     * @param importedElement the new imported element
     */
    public void setImportedElement(final IJCTImportable importedElement);
    
    /**
     * Returns the imported element of this import
     */
    public IJCTImportable getImportedElement();
    
    /**
     * Modifies the is static of this import
     *
     * @param isStatic the new is static
     */
    public void setIsStatic(final boolean isStatic);
    
    /**
     * Returns the is static of this import
     */
    public boolean getIsStatic();
    
    /**
     * Modifies the is on demand of this import
     *
     * @param isOnDemand the new is on demand
     */
    public void setIsOnDemand(final boolean isOnDemand);
    
    /**
     * Returns the is on demand of this import
     */
    public boolean getIsOnDemand();
    

}
