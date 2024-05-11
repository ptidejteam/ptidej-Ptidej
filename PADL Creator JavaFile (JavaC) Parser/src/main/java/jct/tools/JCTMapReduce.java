/**
 * @author Mathieu Lemoine
 * @created 2009-05-07 (木)
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

package jct.tools;

import java.util.List;

import jct.kernel.IJCTVisitor;

/**
 * This class is just an helper to implement an {@link jct.kernel.IJCTVisitor}
 * with a default browsing algorithm of the JCT and a easy-to-redefine reduce behaviour.
 *
 * To visit the enclosed elements, just call super.visit*(t, p).
 * You have to define a reduce behaviour by implementing reduce(current, step)
 */
public abstract class JCTMapReduce<R, P> extends JCTMap<R, P> implements IJCTVisitor<R, P>
{
    protected abstract R reduce(R current, R step);

	@Override
    protected R reduceAnd(final R left, final R right)
    { return this.reduce(left, right); }

    @Override
    protected R reduceAndAssignment(final R var, final R val)
    { return this.reduce(var, val); }

    @Override
    protected R reduceArrayAccess(final R array, final R index)
    { return this.reduce(array, index); }

    @Override
    protected R reduceAssert(final R condition, final R detail)
    { return this.reduce(condition, detail); }

    @Override
    protected R reduceAssignment(final R var, final R val)
    { return this.reduce(var, val); }

    @Override
    protected R reduceBlock(final List<R> results)
    {
        R result = null;
        for(final R step : results)
            result = this.reduce(result, step);
        return result;
    }

    @Override
    protected R reduceCase(final R expr, final List<R> body)
    {
        R result = expr;
        for(final R step : body)
            result = this.reduce(result, step);
        return result;
    }

    @Override
    protected R reduceCatch(final R param, final R body)
    { return this.reduce(param, body); }

    @Override
    protected R reduceClass(final List<R> results)
    {
        R result = null;
        for(final R step : results)
            result = this.reduce(result, step);
        return result;
    }

    @Override
    protected R reduceCompilationUnit(final List<R> imports, final List<R> classes, final List<R> comments)
    {
        R result = null;
        for(final R step : imports)
            result = this.reduce(result, step);
        for(final R step : classes)
            result = this.reduce(result, step);
        for(final R step : comments)
            result = this.reduce(result, step);
        return result;
    }

    @Override
    protected R reduceConditionalAnd(final R left, final R right)
    { return this.reduce(left, right); }

    @Override
    protected R reduceConditionalOperator(final R condition, final R then, final R relse)
    { return this.reduce(this.reduce(condition, then), relse); }

    @Override
    protected R reduceConditionalOr(final R left, final R right)
    { return this.reduce(left, right); }

    @Override
    protected R reduceDivide(final R left, final R right)
    { return this.reduce(left, right); }

    @Override
    protected R reduceDivideAssignment(final R var, final R val)
    { return this.reduce(var, val); }

    @Override
    protected R reduceDoWhile(final R condition, final R body)
    { return this.reduce(condition, body); }

    @Override
    protected R reduceEnhancedFor(final R var, final R it, final R body)
    { return this.reduce(this.reduce(var, it), body); }

    @Override
    protected R reduceEqualTo(final R left, final R right)
    { return this.reduce(left, right); }

    @Override
    protected R reduceErroneousExpression(final List<R> results)
    {
        R result = null;
        for(final R step : results)
            result = this.reduce(result, step);
        return result;
    }

    @Override
    protected R reduceFor(final List<R> inits, final R condition, final List<R> updates, final R body)
    {
        R result = this.reduce(condition, body);
        for(final R step : inits)
            result = this.reduce(result, step);
        for(final R step : updates)
            result = this.reduce(result, step);
        return result;
    }

    @Override
    protected R reduceGreaterThan(final R left, final R right)
    { return this.reduce(left, right); }

    @Override
    protected R reduceGreaterThanOrEqual(final R left, final R right)
    { return this.reduce(left, right); }

    @Override
    protected R reduceIf(final R cond, final R then, final R relse)
    { return this.reduce(this.reduce(cond, then), relse); }

    @Override
    protected R reduceInstanceOf(final R expr, final R type)
    { return this.reduce(expr, type); }

    @Override
    protected R reduceIntersectionType(final List<R> results)
    {
        R result = null;
        for(final R step : results)
            result = this.reduce(result, step);
        return result;
    }

    @Override
    protected R reduceLeftShift(final R left, final R right)
    { return this.reduce(left, right); }

    @Override
    protected R reduceLeftShiftAssignment(final R var, final R val)
    { return this.reduce(var, val); }

    @Override
    protected R reduceLessThan(final R left, final R right)
    { return this.reduce(left, right); }

    @Override
    protected R reduceLessThanOrEqual(final R left, final R right)
    { return this.reduce(left, right); }

    @Override
    protected R reduceMemberSelector(final R expr, final R member)
    { return this.reduce(expr, member); }

    @Override
    protected R reduceMethod(final R returnType, final List<R> exception, final List<R> params, final R body)
    {
        R result = this.reduce(returnType, body);
        for(final R step : exception)
            result = this.reduce(result, step);
        for(final R step : params)
            result = this.reduce(result, step);
        return result;
    }

    @Override
    protected R reduceMethodInvocation(final R selector, final List<R> params)
    {
        R result = selector;
        for(final R step : params)
            result = this.reduce(result, step);
        return result;
    }

    @Override
    protected R reduceMinus(final R left, final R right)
    { return this.reduce(left, right); }

    @Override
    protected R reduceMinusAssignment(final R var, final R val)
    { return this.reduce(var, val); }

    @Override
    protected R reduceMultiply(final R left, final R right)
    { return this.reduce(left, right); }

    @Override
    protected R reduceMultiplyAssignment(final R var, final R val)
    { return this.reduce(var, val); }

    @Override
    protected R reduceNewArray(final R element, final List<R> dims, final List<R> inits)
    {
        R result = element;
        for(final R step : dims)
            result = this.reduce(result, step);
        for(final R step : inits)
            result = this.reduce(result, step);
        return result;
    }

    @Override
    protected R reduceNewClass(final R declared, final R selector, final List<R> parameters)
    {
        R result = this.reduce(declared, selector);
        for(final R step : parameters)
            result = this.reduce(result, step);
        return result;
    }

    @Override
    protected R reduceNotEqualTo(final R left, final R right)
    { return this.reduce(left, right); }

    @Override
    protected R reduceOr(final R left, final R right)
    { return this.reduce(left, right); }

    @Override
    protected R reduceOrAssignment(final R var, final R val)
    { return this.reduce(var, val); }

    @Override
    protected R reducePackage(final R declaration, final List<R> cus)
    {
        R result = declaration;
        for(final R step : cus)
            result = this.reduce(result, step);
        return result;
    }

    @Override
    protected R reducePlus(final R left, final R right)
    { return this.reduce(left, right); }

    @Override
    protected R reducePlusAssignment(final R var, final R val)
    { return this.reduce(var, val); }

    @Override
    protected R reduceRootNode(final List<R> results)
    {
        R result = null;
        for(final R step : results)
            result = this.reduce(result, step);
        return result;
    }

    @Override
    protected R reduceRemainder(final R left, final R right)
    { return this.reduce(left, right); }

    @Override
    protected R reduceRemainderAssignment(final R var, final R val)
    { return this.reduce(var, val); }

    @Override
    protected R reduceRightShift(final R left, final R right)
    { return this.reduce(left, right); }

    @Override
    protected R reduceRightShiftAssignment(final R var, final R val)
    { return this.reduce(var, val); }

    @Override
    protected R reduceSwitch(final R expr, final List<R> cases, final R rdefault)
    {
        R result = this.reduce(expr, rdefault);
        for(final R step : cases)
            result = this.reduce(result, step);
        return result;
    }

    @Override
    protected R reduceSynchronized(final R expr, final R body)
    { return this.reduce(expr, body); }

    @Override
    protected R reduceTry(final R tblock, final List<R> catches, final R rfinally)
    {
        R result = this.reduce(tblock, rfinally);
        for(final R step : catches)
            result = this.reduce(result, step);
        return result;
    }

    @Override
    protected R reduceCast(final R type, final R expr)
    { return this.reduce(type, expr); }

    @Override
    protected R reduceUnsignedRightShift(final R left, final R right)
    { return this.reduce(left, right); }

    @Override
    protected R reduceUnsignedRightShiftAssignment(final R var, final R val)
    { return this.reduce(var, val); }

    @Override
    protected R reduceVariable(final R type, final R init)
    { return this.reduce(type, init); }

    @Override
    protected R reduceWhile(final R condition, final R body)
    { return this.reduce(condition, body); }

    @Override
    protected R reduceXor(final R left, final R right)
    { return this.reduce(left, right); }

    @Override
    protected R reduceXorAssignment(final R var, final R val)
    { return this.reduce(var, val); }
}
