/**
 * @author Mathieu Lemoine
 * @created 2008-12-02
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


import java.io.Writer;
import java.io.IOException;

import java.util.List;


/**
 * This class represents a package
 *
 * @author Mathieu Lemoine
 */
public interface IJCTPackage extends IJCTImportable, IJCTElementContainer<IJCTCompilationUnit>
{
    /**
     * Returns whether the package is annonymous or named
     */
    public boolean isUnnamed();
    
    /**
     * Writes the textual representation in the stream, the name and its separator is not wrote in the writer.
     */
    @Override
	public Writer getSourceCode(final Writer aWriter) throws IOException;
    
    /**
     * Returns the textual representation. 0x1C (File Separator) Unicode Character is used to separate compilation units.
     */
    @Override
	public String getSourceCode();
    
    /**
     * Modifies the package declaration of this package
     *
     * @param packageDeclaration the new package declaration, can be {@code null}
     */
    public void setPackageDeclaration(final IJCTCompilationUnit packageDeclaration);
    
    /**
     * Returns the package declaration of this package
     * <em>Included in the enclosed elements.</em>
     *
     * @return null iff there is no package declaration
     */
    public IJCTCompilationUnit getPackageDeclaration();
    
    /**
     * Adds a "compilation unit" at the index (or move it there)
     */
    public void addCompilationUnit(final int anIndex, final IJCTCompilationUnit aCompilationUnit);
    
    /**
     * Adds a "compilation unit" at the end of the list (or move it there)
     */
    public void addCompilationUnit(final IJCTCompilationUnit aCompilationUnit);
    
    /**
     * Removes this compilation unit from the list
     */
    public void removeCompilationUnit(final IJCTCompilationUnit aCompilationUnit);
    
    /**
     * Remove the compilation unit at the index
     */
    public void removeCompilationUnit(final int anIndex);
    
    /**
     * Returns the list of compilation units of this package
     * <em>Part of the enclosed elements.</em>
     */
    public List<IJCTCompilationUnit> getCompilationUnits();
    
    /**
     * Modifies the is ghost of this package
     *
     * @param isGhost the new is ghost
     */
    public void setIsGhost(final boolean isGhost);
    
    /**
     * Returns the is ghost of this package
     */
    public boolean getIsGhost();
    

}
