/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.statement.creator.classfiles.loc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import util.io.ProxyConsole;

/**
 * @author Stephane Vaucher
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/03/09
 */
public class BCELLOCFinderSimplified {
	private static final Pattern INSTRUCTION_PATTERN = Pattern
			.compile("^\\d+: *");
	private BCEL2PADLAdaptor adaptor;
	private JavaClass currentClass;
	private final Map instructionMap = new HashMap();

	public BCEL2PADLAdaptor getAdaptor() {
		return this.adaptor;
	}

	public Integer getInstructionCount(final String aClassId,
			final String aMethodId) {

		final Map methodMap = this.methodMap(aClassId);
		final Integer instCount = (Integer) methodMap.get(aMethodId);
		return instCount;
	}

	private Integer getLOC(final Code aCode) throws IOException {
		final String codeText = aCode.toString();
		final BufferedReader reader = new BufferedReader(
				new StringReader(codeText));

		String line = reader.readLine();
		int count = 0;
		do {
			if (this.isInstruction(line)) {
				count++;
			}
			line = reader.readLine();
		} while (line != null);

		return Integer.valueOf(count);
	}

	private boolean isInstruction(final String aLine) {
		final Matcher matcher = INSTRUCTION_PATTERN.matcher(aLine);
		return matcher.find();
	}

	private Map methodMap(final String aClassId) {
		Map methodMap = (Map) this.instructionMap.get(aClassId);

		if (methodMap == null) {
			methodMap = new HashMap();
			this.instructionMap.put(aClassId, methodMap);
		}

		return methodMap;
	}

	public void setAdaptor(final BCEL2PADLAdaptor anAdaptor) {
		this.adaptor = anAdaptor;
	}

	@Override
	public String toString() {
		return this.instructionMap.toString();
	}

	public void visitJavaClass(final JavaClass aClass) {
		this.currentClass = aClass;
		final Method[] methods = aClass.getMethods();

		for (int i = 0; i < methods.length; i++) {
			this.visitMethod(methods[i]);
		}
	}

	public void visitMethod(final Method aMethod) {
		Integer count = null;
		final String ckey = this.adaptor.adapt(this.currentClass);
		final String mkey = this.adaptor.adapt(this.currentClass, aMethod);
		final Map methodMap = this.methodMap(ckey);
		try {
			final Code code = aMethod.getCode();
			if (code != null) {
				count = this.getLOC(code);
			}
		}
		catch (final IOException ioe) {
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		methodMap.put(mkey, count);
	}
}
