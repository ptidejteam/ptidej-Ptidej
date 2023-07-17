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
package util.lang;

import util.io.ProxyConsole;

public final class ConcreteReceiverGuard {
	private static ConcreteReceiverGuard UniqueInstance;

	public static ConcreteReceiverGuard getInstance() {
		if (ConcreteReceiverGuard.UniqueInstance == null) {
			ConcreteReceiverGuard.UniqueInstance = new ConcreteReceiverGuard();
		}
		return ConcreteReceiverGuard.UniqueInstance;
	}
	private ConcreteReceiverGuard() {
	}
	private void doCheck(
		final String aConcreteReceiverClassToEnforce,
		final String anErrorMessage) {

		class ConcreteReceiverGuardThrownException extends RuntimeException {
			private static final long serialVersionUID = -4100342857707204144L;
		}

		try {
			throw new ConcreteReceiverGuardThrownException();
		}
		catch (final ConcreteReceiverGuardThrownException e) {
			final StackTraceElement[] stackTrace = e.getStackTrace();
			if (stackTrace.length < 4) {
				ProxyConsole
					.getInstance()
					.errorOutput()
					.print(this.getClass().getName());
				ProxyConsole
					.getInstance()
					.errorOutput()
					.print(" cannot protect ");
				ProxyConsole
					.getInstance()
					.errorOutput()
					.println(aConcreteReceiverClassToEnforce);
			}
			else {
				// Yann 2013/07/10: Intermediaries
				// It is possible that the method calling the ConcreteReceivedGard
				// be actually an overloaded method or a super constructor. Hence,
				// I must first, based on the method name, search for the real 
				// "external" caller by traversing the stack as long as the method
				// called as the same name as the method directly calling the guard
				// as in:
				//	util.lang.ConcreteReceiverGuard.doCheck(ConcreteReceiverGuard.java:44)
				//	util.lang.ConcreteReceiverGuard.checkCallingClassName(ConcreteReceiverGuard.java:128)
				//	pom.metrics.repository.AbstractMetric.<init>(AbstractMetric.java:62)
				//	pom.metrics.repository.USELESS.<init>(USELESS.java:12)
				//	pom.test.general.TestMetricRepository.testRuntimeDeprecation(TestMetricRepository.java:66)
				final String nameOfTheMethodDirectlyCallingTheGuard =
					stackTrace[2].getMethodName();
				int positionOfNextInterestingMethodCall;
				for (positionOfNextInterestingMethodCall = 3; positionOfNextInterestingMethodCall < stackTrace.length
						&& stackTrace[positionOfNextInterestingMethodCall]
							.getMethodName()
							.equals(nameOfTheMethodDirectlyCallingTheGuard); positionOfNextInterestingMethodCall++)
					;
				positionOfNextInterestingMethodCall--;

				final StringBuffer buffer = new StringBuffer();
				buffer.append(stackTrace[positionOfNextInterestingMethodCall]
					.getClassName());
				buffer.append('.');
				buffer.append(stackTrace[positionOfNextInterestingMethodCall]
					.getMethodName());
				final String nameOfGuardedMethod = buffer.toString();
				final String nameOfClassCallingGuardedMethod =
					stackTrace[positionOfNextInterestingMethodCall + 1]
						.getClassName();

				boolean legit = false;
				if (nameOfClassCallingGuardedMethod
					.equals(aConcreteReceiverClassToEnforce)) {

					legit = true;
				}

				if (!legit) {
					ProxyConsole
						.getInstance()
						.warningOutput()
						.print(this.getClass().getName());
					ProxyConsole
						.getInstance()
						.warningOutput()
						.print(
							" reports a runtime deprecation: calling method \"");
					ProxyConsole
						.getInstance()
						.warningOutput()
						.print(nameOfGuardedMethod);
					ProxyConsole
						.getInstance()
						.warningOutput()
						.print("()\" from class \"");
					ProxyConsole
						.getInstance()
						.warningOutput()
						.print(nameOfClassCallingGuardedMethod);
					ProxyConsole.getInstance().warningOutput().println('\"');
					ProxyConsole
						.getInstance()
						.warningOutput()
						.println(anErrorMessage);
				}
			}
		}
	}
	public void checkCallingClassName(
		final String aConcreteReceiverClassToEnforce,
		final String anErrorMessage) {

		// Yann 2013/04/05: Stack!
		// I added this spurious method to make sure that the 
		// doCheck() method is always called with the same stack
		// depth from this class, i.e., 2 :-)
		this.doCheck(aConcreteReceiverClassToEnforce, anErrorMessage);
	}
	public void checkCallingClassName(
		final Class<?> aConcreteReceiverClassToEnforce,
		final String anErrorMessage) {

		this.checkCallingClassName(
			aConcreteReceiverClassToEnforce.getName(),
			anErrorMessage);
	}
}
