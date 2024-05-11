/**
 * This package contains the default implementation of JCT.
 *
 * <h2>Internal references and enclosed elements:</h2>
 *
 * <p>{@link jct.test.rsc.jct.kernel.impl.JCTElementContainer} contains four nested classes responsible to contains
 * the enclosed elements and keeping the enclosingElement backlink up-to-date. there are four such
 * classes: {@link jct.test.rsc.jct.kernel.impl.JCTElementContainer.InternalList},
 * {@link jct.test.rsc.jct.kernel.impl.JCTElementContainer.InternalSet},
 * {@link jct.test.rsc.jct.kernel.impl.JCTElementContainer.InternalReference} and
 * {@link jct.test.rsc.jct.kernel.impl.JCTElementContainer.InternalNullableReference}.<br>
 * Those classes may be built only via method factory, in order to comply with Java Generics
 * restrictions.<br>
 * The accesors of those containers call
 * {@link jct.test.rsc.jct.kernel.impl.JCTElement#updateEnclosingElement(jct.test.rsc.jct.kernel.impl.JCTElementContainer)}
 * which is repsonsible for:
 * <ol>
 * <li>removing the element from the old container,</li>
 * <li>adding the element in the orphan set if the new enclosing element is null, or check that
 * enclosing and enclosed elements are owned by the same root node,</li>
 * <li>updating the enclosing element backlink,</li>
 * <li>and finally, discard the index cached in the path builder.</li>
 * </ol>
 * Those containers are also responsible for discard the index cached in the path builder of any
 * other enclosed element as needed.</p>
 *
 *
 * <h2>Path implementation:</h2>
 *
 * The implementation of Paths is a little complexe, and some "implementation-dependant" value has
 * been used.
 *
 * <h3>Generation:</h3>
 *
 * <p>First, the generation of paths is done using a
 * {@link jct.test.rsc.jct.kernel.impl.JCTPathPartBuilder builder}, each element has one, and each attribute of
 * the path is cached within this builder.<br>
 * Index is changed each time the element is moved or each time an element is inserted before in the
 * enclosed element list,
 * {@link jct.test.rsc.jct.kernel.impl.JCTElement#updateEnclosingElement(JCTElementContainer)},
 * {@link jct.test.rsc.jct.kernel.impl.JCTElementContainer#discardEnclosedElementsCachedPathPartBuilderIndex(JCTElement)}
 * and the internal reference ensure that.<br>
 * When a path is requested via {@link jct.test.rsc.jct.kernel.impl.JCTElement#getPath()},
 * {@link jct.test.rsc.jct.kernel.impl.JCTElement#createPathPart()} updates the builder and returned it, after
 * what, the new path part is put at the end of the path to the enclosing element.</p>
 *
 * <h2>License:</h2>
 *
 * <div>
 * Licensed under 3-clause BSD License:<br>
 * Copyright © 2009, Mathieu Lemoine<br>
 * All rights reserved.<br>
 *<br>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:<br>
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.<br>
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.<br>
 *  * Neither the name of Mathieu Lemoine nor the
 *    names of contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.<br>
 *<br>
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
 * </div>
 */
package jct.test.rsc.jct.kernel.impl;
