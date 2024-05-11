/*
 * (c) Copyright 2008 and following years, Julien Tanteri, University of
 * Montreal.
 * 
 * Use and copying of this software and preparation of derivative works based
 * upon this software are permitted. Any copy of this software or of any
 * derivative work must include the above copyright notice of the author, this
 * paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS ALL
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND NOT
 * WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN, ANY LIABILITY FOR DAMAGES
 * RESULTING FROM THE SOFTWARE OR ITS USE IS EXPRESSLY DISCLAIMED, WHETHER
 * ARISING IN CONTRACT, TORT (INCLUDING NEGLIGENCE) OR STRICT LIABILITY, EVEN IF
 * THE AUTHOR IS ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package jct.test.rsc.snpsht.utils;

public class Pair<T, U> {
	private T car;
	private U cdr;

	public Pair() {
	}

	public Pair(T car) {
		this.car = car;
	}

	public Pair(T car, U cdr) {
		this.car = car;
		this.cdr = cdr;
	}

	public Pair(Pair<T, U> pair) {
		this.copy(pair);
	}

	public T car() {
		return this.car;
	}
	
	public U cdr() {
		return this.cdr;
	}
	
	public void car(T car) {
		this.car = car;
	}
	
	public void cdr(U cdr) {
		this.cdr = cdr;
	}
	
	public void copy(Pair<T, U> pair) {
		this.car = pair.car();
		this.cdr = pair.cdr();
	}
}
