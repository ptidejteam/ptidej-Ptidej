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
package padl.creator.csharpfile.v2.parser;

/**
 * Simple enumeration that maps properly the TOKENs from the CSharp grammar.
 * See file cs.tokens
 */
public enum CSharpTokens {

	USING(csParser.USING), MEMBER_DECLARATION(csParser.MEMBER_DECL), CLASS(
			csParser.CLASS), CLASS_MEMBER(csParser.CLASSMEMBER), CLASS_METHOD(
			csParser.CLASSMETHOD), CONSTRUCTOR(csParser.CONSTRUCTOR), INTERFACE(
			csParser.INTERFACE), INTERFACE_METHOD(csParser.INTERFACEMETHOD), IDENTIFIER(
			csParser.IDENTIFIER), SEMI(34), METHOD(csParser.METHOD), EQUALS(75),

	PUBLIC(78), PROTECTED(79), PRIVATE(80), ABSTRACT(83), VOID(91), VIRTUAL(88), TILDE(
			115), STATIC(85), COLUMN(99), // ':'

	BOOLEAN(205), BYTE(170), CHAR(177), DECIMAL(206), DOUBLE(207), FLOAT(208), INT(
			173), LONG(175), OBJECT(209), SBYTE(169), SHORT(171), STRING(210), UINT(
			174), ULONG(176), USHORT(172),

	NEW(77),

	OPENING_BLOCK(71), // {
	CLOSING_BLOCK(72), // }
	OPENING_BRACE(97), // (
	CLOSING_BRACE(35), // )
	LOWER_THEN(108), // <
	GREATER_THAN(27) // >
	;

	private int type;

	CSharpTokens(int type) {
		this.type = type;
	}

	public int getType() {
		return this.type;
	}

	public static CSharpTokens findByType(int type) {
		for (CSharpTokens item : values())
			if (item.type == type)
				return item;

		return null;
	}
}
