/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package padl.path;

import java.util.Arrays;
import java.util.StringTokenizer;
import org.apache.commons.lang3.ArrayUtils;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IContainer;

public class Finder {
	private static final StringBuffer Header = new StringBuffer();

	public static IConstituent find(
		final String aPath,
		final IAbstractModel anAbstractModel) throws FormatException {

		return (IConstituent) Finder.find0(aPath, anAbstractModel);
	}
	private static Object find0(
		final String aPath,
		final IAbstractModel anAbstractModel) throws FormatException {

		final StringTokenizer tokenizer =
			new StringTokenizer(aPath, IConstants.ALL_SYMBOLS, true);
		Finder.Header.setLength(0);

		if (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			Finder.Header.append(token);

			if (token.charAt(0) == IConstants.ABSTRACT_MODEL_SYMBOL) {
				token = tokenizer.nextToken();
				Finder.Header.append(token);

				// Yann 2009/06/05: Name!
				// We do not need to check the name of the 
				// model because it does not mean anything. 
				//	if (token.equals(anAbstractModel.getDisplayName())) {
				if (tokenizer.hasMoreTokens()) {
					token = tokenizer.nextToken();
					Finder.Header.append(token);
					token = tokenizer.nextToken();
					Finder.Header.append(token);

					final IConstituent constituent = anAbstractModel
						.getConstituentFromID(token.toCharArray());
					if (constituent != null) {
						if (tokenizer.hasMoreTokens()) {
							return Finder.find0(
								aPath.substring(Finder.Header.length() + 1),
								constituent,
								token.charAt(0));
						}
						else {
							return constituent;
						}
					}
					else {
						throw new FormatException(
							"Abstract model does not contain first constituent in the path.");
					}
					//	}
					//	else {
					//		throw new FormatException(
					//			"Abstract model name and path are one and the same.");
					//	}
				}
				else {
					if (Arrays.equals(
						anAbstractModel.getName(),
						token.toCharArray())) {

						return anAbstractModel;
					}
					else {
						throw new FormatException(
							"Abstract model name and path do not match.");
					}
				}
			}
			else {
				throw new FormatException("Not an abstract model-level path.");
			}
		}
		else {
			throw new FormatException("Empty path.");
		}
	}
	private static IConstituent find0(
		final String aPath,
		final IConstituent aConstituent,
		final char aDelimiter) throws FormatException {

		final StringTokenizer tokenizer =
			new StringTokenizer(aPath, IConstants.ALL_SYMBOLS, true);
		Finder.Header.setLength(0);

		if (tokenizer.hasMoreTokens()) {
			if (aConstituent instanceof IContainer) {
				String token = tokenizer.nextToken();
				Finder.Header.append(token);

				// Yann 2014/04/21: Member parameter types!
				// It could be that the path contains:
				//	AssociateWithTest(ProfileSyncService$SyncTest *[])
				// In that case, I want to look for that constituent
				// (method) now, not split on the $ sign *inside* the
				// parentheses! Ah, C++...
				if (token.indexOf("(") > -1) {
					final char[] id = aPath.toCharArray();
					final IConstituent constituent =
						((IContainer) aConstituent).getConstituentFromID(id);
					if (constituent == null) {
						throw new FormatException(
							"Cannot find ID: \"" + token + "\" in \""
									+ aConstituent.getDisplayID() + "\".");
					}
					else {
						return constituent;
					}
				}

				final IConstituent constituent;
				if (aDelimiter == IConstants.MEMBER_ENTITY_SYMBOL) {
					// Yann 2010/06/29: Name vs. ID.
					// A member entity has for name its "simple name"
					// and for ID is fully qualified name, as any
					// other entity now...
					// Yann 2013/09/28: Name vs. ID, reloaded!
					// So, as per the previous comment, I need to
					// create the fully-qualified name of the member
					// entity OR look for it by name rather than by
					// ID BUT, if looking per name, I can retrieve
					// the wrong entity, in case a field of method
					// would have the same name as a member entity.
					// Yann 2014/04/21: Yet again...
					char[] id = aConstituent.getID();
					id = ArrayUtils.add(id, IConstants.MEMBER_ENTITY_SYMBOL);
					id = ArrayUtils.addAll(id, token.toCharArray());
					constituent =
						((IContainer) aConstituent).getConstituentFromID(id);
				}
				else {
					// Yann 2013/09/12: A bit of mismatch!
					// The path of an operation includes parentheses
					// and parameter types while the ID of an operation
					// is just that, it is ID, without anything else.
					// Therefore, if the token includes parentheses,
					// I must remove them...
					// ... which is really NOT a good idea: ID must
					// include parenthesis and parameter types to
					// distinguish a field "a" from a method "a()"
					// from the overloaded method "a(int)!
					//	final int startOpeningParenthesisInclusive =
					//		ArrayUtils.indexOf(id, '(');
					//	if (startOpeningParenthesisInclusive > 0) {
					//		id =
					//			ArrayUtils.subarray(
					//				id,
					//				0,
					//				startOpeningParenthesisInclusive);
					//	}
					char[] id = token.toCharArray();
					constituent =
						((IContainer) aConstituent).getConstituentFromID(id);
				}
				if (constituent != null) {
					if (tokenizer.hasMoreTokens()) {
						token = tokenizer.nextToken();
						Finder.Header.append(token);

						return Finder.find0(
							aPath.substring(Finder.Header.length()),
							constituent,
							token.charAt(0));
					}
					else {
						return constituent;
					}
				}
				else {
					throw new FormatException(
						"Cannot find ID: \"" + token + "\" in \""
								+ aConstituent.getDisplayID() + "\".");
				}
			}
			else {
				throw new FormatException(
					"Constituent with ID: \"" + aConstituent.getDisplayID()
							+ "\" is not a container.");
			}
		}
		else {
			throw new FormatException("Empty path.");
		}
	}
	public static IContainer findContainer(
		final String aPath,
		final IAbstractModel anAbstractModel) throws FormatException {

		final int indexElementSymbol =
			aPath.lastIndexOf(IConstants.ELEMENT_SYMBOL);
		if (indexElementSymbol == -1) {
			if (ArrayUtils.isEquals(anAbstractModel.getPath(), aPath)) {
				return anAbstractModel;
			}
			throw new FormatException(
				"Cannot find ID: \"" + aPath + "\" in \""
						+ anAbstractModel.getDisplayPath() + "\".");
		}
		else {
			final String newPath = aPath.substring(0, indexElementSymbol);

			return (IContainer) Finder.find0(newPath, anAbstractModel);
		}
	}
}
