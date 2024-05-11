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
package caffeine.logic;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import util.io.ProxyConsole;
import JIP.engine.JIPAtom;
import JIP.engine.JIPEngine;
import JIP.engine.JIPFunctor;
import JIP.engine.JIPList;
import JIP.engine.JIPNumber;
import JIP.engine.JIPTerm;
import JIP.engine.JIPXCall;
import caffeine.Constants;
import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ArrayReference;
import com.sun.jdi.BooleanValue;
import com.sun.jdi.ByteValue;
import com.sun.jdi.CharValue;
import com.sun.jdi.ClassType;
import com.sun.jdi.DoubleValue;
import com.sun.jdi.Field;
import com.sun.jdi.FloatValue;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.IntegerValue;
import com.sun.jdi.InterfaceType;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.LongValue;
import com.sun.jdi.Method;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.ShortValue;
import com.sun.jdi.StackFrame;
import com.sun.jdi.StringReference;
import com.sun.jdi.ThreadGroupReference;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.Value;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.AccessWatchpointEvent;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.ClassUnloadEvent;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.LocatableEvent;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;
import com.sun.jdi.event.ModificationWatchpointEvent;
import com.sun.jdi.event.ThreadDeathEvent;
import com.sun.jdi.event.ThreadStartEvent;
import com.sun.jdi.event.VMDeathEvent;
import com.sun.jdi.event.VMStartEvent;
import com.sun.jdi.request.AccessWatchpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.ClassUnloadRequest;
import com.sun.jdi.request.MethodEntryRequest;
import com.sun.jdi.request.MethodExitRequest;
import com.sun.jdi.request.ModificationWatchpointRequest;
import com.sun.jdi.request.ThreadDeathRequest;
import com.sun.jdi.request.ThreadStartRequest;

/**
 * @version 1.0
 * @author	Yann-Gaël Guéhéneuc
 */
public final class EventManager extends JIPXCall {
	/*
	 * Some private static variable to link the
	 * EventManager, the corresponding Java Virtual
	 * Machine, and the Prolog engine.
	 * These fields are static because the Prolog
	 * engine creates an instance of this class each
	 * time the invoke(JIPTerm) method returns.
	 */
	private static boolean IsJVMInitialized = false;

	private static boolean GenerateConstructorEntryEvent = false;
	private static boolean GenerateConstructorExitEvent = false;
	private static boolean GenerateFieldAccessEvent = false;
	private static boolean GenerateFieldModificationEvent = false;
	private static boolean GenerateFinalizerEntryEvent = false;
	private static boolean GenerateFinalizerExitEvent = false;
	private static boolean GenerateMethodEntryEvent = false;
	private static boolean GenerateMethodExitEvent = false;
	private static boolean GenerateProgramEndEvent = false;
	// No need for a method about returned value,
	// the name of the special method is enough.

	private static ReferenceType CollectionInterfaceType = null;
	private static ReferenceType MapInterfaceType = null;
	private static int RequiredEventMask = 0;
	private static List CaffeineListeners = null;
	private static String[] ClassNameFilter = null;
	private static VirtualMachine JVM = null;
	private static List FieldAccesses = null;
	private static List SpecialFieldAccesses = null;
	private static String FieldName;
	private static long DeclaringClassID = 0;
	private static String DeclaringClassName;
	private static boolean IsDeferredContainerWatchpoint = false;
	private static List AllRequests = new ArrayList();

	private static List AllEvents = new ArrayList();
	private static Map AllThreads = new HashMap();
	private static int CurrentEventNumber = 0;
	private static String ReturnedValue = Event.NoReturnedValue;

	private static long VMStartTimeStamp;
	private static long VMDeathTimeStamp;
	private static long NumberOfSteps;
	private static Writer TraceWriter = null;

	private static void addEvent(
		final String sourceName,
		final int lineNumber,
		final String eventType,
		final String eventName,
		final long receiverID,
		final long argumentID,
		final String comment,
		final Object returnedValue) {

		EventManager.CurrentEventNumber++;
		EventManager.AllEvents.add(new Event(
			sourceName,
			lineNumber,
			eventType,
			eventName,
			receiverID,
			argumentID,
			comment,
			EventManager.CurrentEventNumber,
			returnedValue));

		if (Constants.DEBUG) {
			System.err.println(EventManager.AllEvents.get(
				EventManager.AllEvents.size() - 1).toString());
		}
	}

	//	private static boolean buildEvents(
	//		final com.sun.jdi.event.Event event,
	//		final JIPEngine engine,
	//		final JIPTerm input) {
	//
	//		if (event instanceof VMStartEvent) {
	//			EventManager.VMStartTimeStamp = System.currentTimeMillis();
	//		}
	//		else if (event instanceof VMDeathEvent) {
	//			EventManager.VMDeathTimeStamp = System.currentTimeMillis();
	//		}
	//		else {
	//			return EventManager.buildEvent(event);
	//		}
	//		return false;
	//	}

	private static boolean buildEvent(final ThreadDeathEvent event) {
		final ThreadReference thread = ((ThreadDeathEvent) event).thread();
		final String threadName = thread.name();

		// System.err.println('(' + threadName + " just ended.)");

		if (EventManager.AllThreads.containsKey(threadName)) {
			final int count =
				((Integer) EventManager.AllThreads.get(threadName)).intValue();
			if (count == 1) {
				EventManager.AllThreads.remove(threadName);
			}
			else {
				EventManager.AllThreads.put(threadName, Integer.valueOf(count - 1));
			}
			if (EventManager.AllThreads.size() == 0) {
				EventManager.addEvent(
					Event.NoSourceName,
					Event.NoLineNumber,
					Constants.PROGRAM_END_EVENT,
					Constants.PROGRAM_END_EVENT,
					Event.NoID,
					Event.NoID,
					thread.name(),
					Event.NoReturnedValue);

				// Yann 2002/06/06: Yup!
				// I take care not to produce more than one
				// program end event! This could happen with
				// AWT program where the System.exit(int) 
				// method is called and where the AWT-Windows
				// anyway dies normally.
				EventManager.GenerateProgramEndEvent = false;
				return true;
			}
		}
		return false;
	}

	private static boolean buildEvent(final ThreadStartEvent event) {
		final ThreadReference thread = ((ThreadStartEvent) event).thread();
		final String threadName = thread.name();
		final ThreadGroupReference group = thread.threadGroup();
		final ObjectReference target =
			(ObjectReference) thread.getValue(thread
				.referenceType()
				.fieldByName("target"));

		// I cannot restrict the events according to a particular
		// class or target's class, because the ThreadStartRequest
		// does not provide such a functionality.

		// This part of the code is rather tricky. Indeed, there
		// are several conditions intermixing together and the
		// JVM does not help, because it creates undocumented,
		// user-level threads for its own purposes.

		boolean isUserThread = true;
		if (group == null || group.parent() == null) {
			isUserThread = false;
		}
		else {
			boolean isUserBasedSystemThread = false;
			for (int i = 0; i < Constants.USER_SYSTEM_THREAD_NAMES.length
					&& !isUserBasedSystemThread; i++) {

				if (threadName.equals(Constants.USER_SYSTEM_THREAD_NAMES[i])) {
					isUserBasedSystemThread = true;
				}
			}
			if (!isUserBasedSystemThread) {
				if (target != null) {
					final String targetName = target.referenceType().name();
					for (int i = 0; i < Constants.SYSTEM_CLASS_NAMES.length
							&& isUserThread; i++) {
						if (targetName.regionMatches(
							0,
							Constants.SYSTEM_CLASS_NAMES[i],
							0,
							Constants.SYSTEM_CLASS_NAMES[i].lastIndexOf('.'))) {

							isUserThread = false;
						}
					}
				}
				else {
					final String typeName = thread.referenceType().name();
					for (int i = 0; i < Constants.SYSTEM_CLASS_NAMES.length
							&& isUserThread; i++) {
						if (typeName.regionMatches(
							0,
							Constants.SYSTEM_CLASS_NAMES[i],
							0,
							Constants.SYSTEM_CLASS_NAMES[i].lastIndexOf('.'))) {

							isUserThread = false;
						}
					}
				}
			}
		}

		if (isUserThread) {
			try {
				if (EventManager.AllThreads.containsKey(threadName)) {
					final int count =
						((Integer) EventManager.AllThreads.get(threadName))
							.intValue();
					EventManager.AllThreads.put(threadName, Integer.valueOf(
						count + 1));
				}
				else {
					EventManager.AllThreads.put(threadName, Integer.valueOf(1));
				}
			}
			catch (final Exception e) {
				if (Constants.EXCEPTION_DEBUG) {
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
			}
		}
		return false;
	}

	private static boolean buildEvent(final ClassUnloadEvent classUnloadEvent) {
		final String className = classUnloadEvent.className();

		if (!className.equals("CaffeineWrapper")
				&& !className.equals("caffeine.remote.Vector")
				&& !className.equals("caffeine.remote.Hashtable")) {

			EventManager.addEvent(
				Event.NoSourceName,
				Event.NoLineNumber,
				Constants.CLASS_UNLOAD_EVENT,
				className,
				Event.NoID,
				Event.NoID,
				Event.NoComment,
				Event.NoReturnedValue);
			return true;
		}
		return false;
	}

	private static boolean buildEvent(final com.sun.jdi.event.Event event) {
		if (Constants.DEBUG) {
			System.err.println(event);
		}

		if (event instanceof VMStartEvent) {
			EventManager.VMStartTimeStamp = System.currentTimeMillis();
		}
		else if (event instanceof VMDeathEvent) {
			EventManager.VMDeathTimeStamp = System.currentTimeMillis();
		}
		else if (event instanceof ClassPrepareEvent) {
			return EventManager.buildEvent((ClassPrepareEvent) event);
		}
		else if (event instanceof ClassUnloadEvent) {
			return EventManager.buildEvent((ClassUnloadEvent) event);
		}
		else if (event instanceof ThreadStartEvent) {
			return EventManager.buildEvent((ThreadStartEvent) event);
		}
		else if (event instanceof ThreadDeathEvent
				&& EventManager.GenerateProgramEndEvent) {

			return EventManager.buildEvent((ThreadDeathEvent) event);
		}
		else if (event instanceof LocatableEvent) {
			return EventManager.buildEvent((LocatableEvent) event);
		}

		return false;
	}
	private static boolean buildEvent(final LocatableEvent event) {
		final LocatableEvent locatableEvent = (LocatableEvent) event;
		final ThreadReference thread = locatableEvent.thread();
		StackFrame currentStackFrame = null;
		long receiverID = Event.NoID;
		long callerID = Event.NoID;
		try {
			currentStackFrame = thread.frame(0);
			receiverID =
				EventManager.getHashCode(currentStackFrame.thisObject());
			callerID = EventManager.getHashCode(thread.frame(1).thisObject());
		}
		catch (final Exception e) {
			// It seems that all kinds of exception can happen here:
			// - com.sun.jdi.VMDisconnectedException
			// - com.sun.jdi.IncompatibleThreadStateException
			// So, for the moment, I just forget about them...
			if (Constants.DEBUG) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}

		String sourceName = Event.NoSourceName;
		int lineNumber = Event.NoLineNumber;
		try {
			sourceName = thread.frame(1).location().sourceName();
			lineNumber = thread.frame(1).location().lineNumber();
		}
		catch (final AbsentInformationException aie) {
		}
		catch (final IncompatibleThreadStateException itse) {
		}
		catch (final IndexOutOfBoundsException iobe) {
		}

		if (locatableEvent instanceof AccessWatchpointEvent) {
			return EventManager.buildEvent(
				(AccessWatchpointEvent) locatableEvent,
				receiverID,
				sourceName,
				lineNumber);
		}
		else if (locatableEvent instanceof ModificationWatchpointEvent) {
			return EventManager.buildEvent(
				(ModificationWatchpointEvent) locatableEvent,
				receiverID,
				sourceName,
				lineNumber);
		}
		else if (locatableEvent instanceof MethodEntryEvent) {
			return EventManager.buildEvent(
				(MethodEntryEvent) locatableEvent,
				thread,
				currentStackFrame,
				receiverID,
				callerID,
				sourceName,
				lineNumber);
		}
		else if (locatableEvent instanceof MethodExitEvent) {
			return EventManager.buildEvent(
				(MethodExitEvent) locatableEvent,
				currentStackFrame,
				receiverID,
				callerID,
				sourceName,
				lineNumber);
		}

		return false;
	}

	private static boolean buildEvent(
		final MethodExitEvent methodExitEvent,
		StackFrame currentStackFrame,
		long receiverID,
		long callerID,
		String sourceName,
		int lineNumber) {

		final Method method = methodExitEvent.location().method();
		final String methodName = method.name();
		final String declaringTypeName = method.declaringType().name();

		// Yann 2002/07/08: Composition relationship management!
		if (EventManager.IsDeferredContainerWatchpoint
				&& ((declaringTypeName.equals("caffeine.remote.Vector") && methodName
					.startsWith("add")) || (declaringTypeName
					.equals("caffeine.remote.Hashtable") && methodName
					.equals("put")))) {

			try {
				if (Constants.DEBUG) {
					System.err.println(method);
					System.err.println(currentStackFrame);
				}

				final LocalVariable argument;
				final ObjectReference objectReference;

				if (methodName.startsWith("add")) {
					argument = (LocalVariable) method.arguments().get(0);
					objectReference =
						(ObjectReference) currentStackFrame.getValue(argument);
				}
				else {
					argument = (LocalVariable) method.arguments().get(1);
					objectReference =
						(ObjectReference) currentStackFrame.getValue(argument);
				}

				EventManager.addEvent(
					sourceName,
					lineNumber,
					Constants.FIELD_MODIFICATION_EVENT,
					EventManager.FieldName,
					EventManager.DeclaringClassID,
					EventManager.getHashCode(objectReference),
					EventManager.DeclaringClassName,
					objectReference.type().name());
				return true;
			}
			catch (final Exception e) {
				if (Constants.EXCEPTION_DEBUG) {
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
			}
			finally {
				EventManager.IsDeferredContainerWatchpoint = false;
			}
		}
		else if (methodName.equals("<clinit>")
				|| methodName.equals("caffeineUniqueExit")
				|| declaringTypeName.equals("caffeine.remote.Vector")
				|| declaringTypeName.equals("caffeine.remote.Hashtable")) {

			// We generate no event for static initializers
			// and for the unique exit method. Anyway, the
			// latter case never happens because of the
			// call to:
			//		System.exit(int)
			// in the caffeineUniqueExit(int) method.
		}
		else if (methodName.equals("<init>")) {
			if (EventManager.GenerateConstructorExitEvent) {
				EventManager.addEvent(
					sourceName,
					lineNumber,
					Constants.CONSTRUCTOR_EXIT_EVENT,
					method.declaringType().name(),
					receiverID,
					Event.NoID,
					Event.NoComment,
					Event.NoReturnedValue);
				return true;
			}
		}
		else if (methodName.equals("finalize")
				&& method.signature().equals("()V")) {

			if (EventManager.GenerateFinalizerExitEvent) {
				EventManager.addEvent(
					sourceName,
					lineNumber,
					Constants.FINALIZER_EXIT_EVENT,
					method.declaringType().name(),
					receiverID,
					Event.NoID,
					Event.NoComment,
					Event.NoReturnedValue);
				return true;
			}
		}
		else if (methodName.equals("caffeineReturnedValueWrapper")) {
			try {
				final LocalVariable argument =
					(LocalVariable) method.arguments().get(0);
				EventManager.ReturnedValue =
					EventManager.getWrapper(
						currentStackFrame.getValue(argument)).toString();
			}
			catch (final Exception e) {
			}
		}
		else if (EventManager.GenerateMethodExitEvent) {
			EventManager.addEvent(
				sourceName,
				lineNumber,
				Constants.METHOD_EXIT_EVENT,
				methodName,
				callerID,
				receiverID,
				method.declaringType().name(),
				EventManager.ReturnedValue);

			// I reset the returnedValue
			EventManager.ReturnedValue = Event.NoReturnedValue;
			return true;
		}

		return false;
	}

	private static boolean buildEvent(
		final MethodEntryEvent methodEntryEvent,
		final ThreadReference thread,
		final StackFrame currentStackFrame,
		final long receiverID,
		final long callerID,
		final String sourceName,
		final int lineNumber) {

		final Method method = methodEntryEvent.method();
		final String methodName = method.name();
		final String declaringTypeName = method.declaringType().name();

		if (methodName.equals("<clinit>")
				|| methodName.equals("caffeineReturnedValueWrapper")
				|| declaringTypeName.equals("caffeine.remote.Vector")
				|| declaringTypeName.equals("caffeine.remote.Hashtable")) {

			// We generate no event for static initializers,
			// and the special method:
			//		caffeineReturnedValueWrapper.
		}
		else if (methodName.equals("<init>")) {
			if (EventManager.GenerateConstructorEntryEvent) {
				EventManager.addEvent(
					sourceName,
					lineNumber,
					Constants.CONSTRUCTOR_ENTRY_EVENT,
					declaringTypeName,
					receiverID,
					Event.NoID,
					Event.NoComment,
					Event.NoReturnedValue);
				return true;
			}
		}
		else if (methodName.equals("caffeineUniqueExit")
				&& EventManager.GenerateProgramEndEvent) {

			EventManager.addEvent(
				Event.NoSourceName,
				Event.NoLineNumber,
				Constants.PROGRAM_END_EVENT,
				Constants.PROGRAM_END_EVENT,
				Event.NoID,
				Event.NoID,
				thread.name(),
				Event.NoReturnedValue);

			// Yann 2002/06/06: Yup!
			// I take care not to produce more than one
			// program end event! This could happen with
			// AWT program where the System.exit(int) 
			// method is called and where the AWT-Windows
			// anyway dies normally.
			EventManager.GenerateProgramEndEvent = false;
			return true;
		}
		else if (methodName.equals("finalize")
				&& method.signature().equals("()V")) {
			if (EventManager.GenerateFinalizerEntryEvent) {
				EventManager.addEvent(
					sourceName,
					lineNumber,
					Constants.FINALIZER_ENTRY_EVENT,
					declaringTypeName,
					receiverID,
					Event.NoID,
					Event.NoComment,
					Event.NoReturnedValue);
				return true;
			}
		}
		else if (EventManager.GenerateMethodEntryEvent) {
			EventManager.addEvent(
				sourceName,
				lineNumber,
				Constants.METHOD_ENTRY_EVENT,
				methodName,
				callerID,
				receiverID,
				declaringTypeName,
				Event.NoReturnedValue);
			return true;
		}

		return false;
	}

	private static boolean buildEvent(
		final ModificationWatchpointEvent modificationWatchpointEvent,
		final long receiverID,
		final String sourceName,
		final int lineNumber) {

		final ObjectReference objectReference =
			(ObjectReference) modificationWatchpointEvent.valueToBe();

		if (objectReference != null) {
			EventManager.addEvent(
				sourceName,
				lineNumber,
				Constants.FIELD_MODIFICATION_EVENT,
				modificationWatchpointEvent.field().name(),
				EventManager.getHashCode(modificationWatchpointEvent.object()),
				EventManager.getHashCode(objectReference),
				modificationWatchpointEvent.object() == null ? ""
						: modificationWatchpointEvent
							.object()
							.referenceType()
							.name(),
				objectReference.referenceType().name());
			return true;
		}
		return false;
	}

	private static boolean buildEvent(
		final AccessWatchpointEvent accessWatchpointEvent,
		final long receiverID,
		final String sourceName,
		final int lineNumber) {

		// Yann 2002/07/08: Composition relationships management!
		// I check if this accessed field belongs to the list
		// of special field. If so, I add new access/modification
		// event requests and manage it here and in the 
		// ModificationWatchpointEvent-related code.
		if (!EventManager.IsDeferredContainerWatchpoint
				&& EventManager.SpecialFieldAccesses
					.contains(accessWatchpointEvent.request())) {

			EventManager.IsDeferredContainerWatchpoint = true;
			EventManager.FieldName = accessWatchpointEvent.field().name();
			EventManager.DeclaringClassID = receiverID;
			EventManager.DeclaringClassName =
				accessWatchpointEvent.field().declaringType().name();
		}
		else {
			EventManager.addEvent(
				sourceName,
				lineNumber,
				Constants.FIELD_ACCESS_EVENT,
				accessWatchpointEvent.field().name(),
				receiverID,
				Event.NoID,
				accessWatchpointEvent.field().declaringType().name(),
				Event.NoReturnedValue);
			return true;
		}

		return false;
	}
	private static boolean buildEvent(final ClassPrepareEvent classPrepareEvent) {
		final String className = classPrepareEvent.referenceType().name();

		// Yann 2002/07/08: Be careful!
		// When the JVM loads a new class, I must ensure that
		// field-related request are enabled as soons as possible.
		EventManager.observeFields();

		if (!className.equals("CaffeineWrapper")
				&& !className.equals("caffeine.remote.Vector")
				&& !className.equals("caffeine.remote.Hashtable")) {

			EventManager.addEvent(
				Event.NoSourceName,
				Event.NoLineNumber,
				Constants.CLASS_LOAD_EVENT,
				className,
				Event.NoID,
				Event.NoID,
				Event.NoComment,
				Event.NoReturnedValue);
			return true;
		}
		return false;
	}
	private static void createRequests() {
		// I reset the different requests,
		// in case of dynamically set requests.
		EventManager.JVM.eventRequestManager().deleteEventRequests(
			EventManager.AllRequests);
		EventManager.AllRequests.clear();

		EventManager.observeClasses();
		EventManager.observeMethodEntries();
		EventManager.observeMethodExits();

		if ((EventManager.RequiredEventMask & Constants.GENERATE_FIELD_ACCESS_EVENT) == Constants.GENERATE_FIELD_ACCESS_EVENT) {

			EventManager.GenerateFieldAccessEvent = true;
		}
		if ((EventManager.RequiredEventMask & Constants.GENERATE_FIELD_MODIFICATION_EVENT) == Constants.GENERATE_FIELD_MODIFICATION_EVENT) {

			EventManager.GenerateFieldModificationEvent = true;
		}

		// I add an extra method exit request to monitor the
		// methods of the CaffeineWrapper class.
		if ((EventManager.RequiredEventMask & Constants.GENERATE_METHOD_RETURNED_VALUE_EVENT) == Constants.GENERATE_METHOD_RETURNED_VALUE_EVENT) {

			final MethodExitRequest methodExitRequest =
				EventManager.JVM
					.eventRequestManager()
					.createMethodExitRequest();
			methodExitRequest
				.addClassFilter(Constants.CAFFEINE_RETURNED_VALUE_WRAPPER_CLASS_NAME);
			methodExitRequest.enable();
			EventManager.AllRequests.add(methodExitRequest);
		}

		// I add a specific request to monitor the death of threads,
		// to monitor the end of the program
		if ((EventManager.RequiredEventMask & Constants.GENERATE_PROGRAM_END_EVENT) == Constants.GENERATE_PROGRAM_END_EVENT) {

			final ThreadStartRequest threadStartRequest =
				EventManager.JVM
					.eventRequestManager()
					.createThreadStartRequest();
			threadStartRequest.enable();
			EventManager.AllRequests.add(threadStartRequest);

			final ThreadDeathRequest threadDeathRequest =
				EventManager.JVM
					.eventRequestManager()
					.createThreadDeathRequest();
			threadDeathRequest.enable();
			EventManager.AllRequests.add(threadDeathRequest);

			final MethodEntryRequest methodEntryRequest =
				EventManager.JVM
					.eventRequestManager()
					.createMethodEntryRequest();
			methodEntryRequest
				.addClassFilter(Constants.CAFFEINE_RETURNED_VALUE_WRAPPER_CLASS_NAME);
			methodEntryRequest.enable();
			EventManager.AllRequests.add(methodEntryRequest);

			EventManager.GenerateProgramEndEvent = true;
		}
	}
	private static long getHashCode(final ObjectReference object) {
		if (Constants.DEBUG) {
			System.err.print("EventManger.getHashCode(");
			System.err.print(object);
			System.err.println(')');
		}

		long identityHashCodeValue = Event.NoID;
		if (object != null) {
			// identityHashCodeValue = object.hashCode();
			// The thisObject().hashCode() value is
			// thread dependent!
			// There is an important bug in JDK 1.4.0.
			// When invoking a method on a remote object
			// (see commented code below), after the
			// invocation returns, the objects is still held
			// by the virtual machine and thus is not
			// finalizabale.
			// I use the following workaround: I add a public
			// field to all the instances created in the
			// remote JVM (see Loader and CaffeineObject class),
			// I initialize this field using the value of
			// System.identityHashCode(Object) so that the
			// identity of the instance is unique.
			try {
				final Field caffeineUniqueID =
					object.referenceType().fieldByName("caffeineUniqueID");
				identityHashCodeValue =
					((IntegerValue) object.getValue(caffeineUniqueID))
						.intValue();
			}
			catch (final Exception e) {
				// It seems that all kinds of exception can happen here:
				// - com.sun.jdi.VMDisconnectedException
				// - com.sun.jdi.IncompatibleThreadStateException
				// - java.lang.NullPointerException
				// So, for the moment, I just forget about them...
				if (Constants.EXCEPTION_DEBUG) {
					System.err.println(object.type());
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
			}

			// This is a special case for the constructor
			// entry (this is very very ugly).
			if (identityHashCodeValue == 0) {
				try {
					final Field caffeineUniqueID =
						object.referenceType().fieldByName("CaffeineUniqueID");
					identityHashCodeValue =
						((IntegerValue) object.getValue(caffeineUniqueID))
							.intValue();
				}
				catch (final Exception e) {
					// It seems that all kinds of exception can happen here:
					// - com.sun.jdi.VMDisconnectedException
					// - com.sun.jdi.IncompatibleThreadStateException
					// - java.lang.NullPointerException
					// So, for the moment, I just forget about them...
					if (Constants.EXCEPTION_DEBUG) {
						System.err.println(object.type());
						e.printStackTrace(ProxyConsole.getInstance().errorOutput());
					}
				}
			}
		}

		if (Constants.DEBUG) {
			System.err.print(object);
			System.err.print(".identityHashCodeValue == ");
			System.err.println(identityHashCodeValue);
		}

		return identityHashCodeValue;
	}
	public static long getNumberOfSteps() {
		return EventManager.NumberOfSteps;
	}
	public static long getVMDeathTimeStamp() {
		return EventManager.VMDeathTimeStamp;
	}
	public static long getVMStartTimeStamp() {
		return EventManager.VMStartTimeStamp;
	}
	private static Object getWrapper(final Value value) {
		if (value != null) {
			if (value instanceof BooleanValue) {
				return Boolean.valueOf(((BooleanValue) value).value());
			}
			else if (value instanceof ByteValue) {
				return Byte.valueOf(((ByteValue) value).value());
			}
			else if (value instanceof CharValue) {
				return Character.valueOf(((CharValue) value).value());
			}
			else if (value instanceof DoubleValue) {
				return Double.valueOf(((DoubleValue) value).value());
			}
			else if (value instanceof FloatValue) {
				return Float.valueOf(((FloatValue) value).value());
			}
			else if (value instanceof IntegerValue) {
				return Integer.valueOf(((IntegerValue) value).value());
			}
			else if (value instanceof LongValue) {
				return Long.valueOf(((LongValue) value).value());
			}
			else if (value instanceof ShortValue) {
				return Short.valueOf(((ShortValue) value).value());
			}
			else if (value instanceof ArrayReference) {
				return ((ArrayReference) value).getValues();
			}
			else if (value instanceof StringReference) {
				return '\"' + ((StringReference) value).value() + '\"';
			}
			else if (value instanceof ObjectReference) {
				return ((ObjectReference) value).getClass().getName() + '@'
						+ EventManager.getHashCode((ObjectReference) value);
			}
		}
		return Event.NoReturnedValue;
	}
	private static void initialize(
		final Engine caffeineEngine,
		final JIPTerm input) {

		try {
			if (!EventManager.IsJVMInitialized) {
				// if (!EventManager.IsJVMInitialized && caffeineEngine != null) {
				EventManager.NumberOfSteps = 0;
				EventManager.CaffeineListeners =
					caffeineEngine.getCaffeineListeners();
				EventManager.ClassNameFilter =
					caffeineEngine.getClassNameFilter();
				EventManager.FieldAccesses = new ArrayList() {
					private static final long serialVersionUID =
						-6343318162030425996L;

					{
						for (int i = 0; caffeineEngine.getFieldAccesses() != null
								&& i < caffeineEngine.getFieldAccesses().length; i++) {

							this.add(caffeineEngine.getFieldAccesses()[i]);
						}
					}
				};
				EventManager.SpecialFieldAccesses = new ArrayList();
				EventManager.RequiredEventMask =
					caffeineEngine.getRequiredEventMask();
				EventManager.TraceWriter = caffeineEngine.getTraceWriter();
				EventManager.JVM = caffeineEngine.getVirtualMachine();

				// Yann 2002/07/9: Composition relationship management!
				// Yann 2002/07/10: Bug...
				// Yann 2003/08/05: Test with new event!
				// I must monitor both method entry AND exit because of a limitation
				// in JPDA (one more...) that causes the stack to be the stack of the
				// calling method, not of the Hashtable.put(...) method.
				if ((EventManager.RequiredEventMask & Constants.GENERATE_COLLECTION_EVENT) == Constants.GENERATE_COLLECTION_EVENT) {

					MethodEntryRequest methodEntryRequest =
						EventManager.JVM
							.eventRequestManager()
							.createMethodEntryRequest();
					methodEntryRequest.addClassFilter("caffeine.remote.Vector");
					methodEntryRequest.enable();
					methodEntryRequest =
						EventManager.JVM
							.eventRequestManager()
							.createMethodEntryRequest();
					methodEntryRequest
						.addClassFilter("caffeine.remote.Hashtable");
					methodEntryRequest.enable();
					MethodExitRequest methodExitRequest =
						EventManager.JVM
							.eventRequestManager()
							.createMethodExitRequest();
					methodExitRequest.addClassFilter("caffeine.remote.Vector");
					methodExitRequest.enable();
					methodExitRequest =
						EventManager.JVM
							.eventRequestManager()
							.createMethodExitRequest();
					methodExitRequest
						.addClassFilter("caffeine.remote.Hashtable");
					methodExitRequest.enable();
				}

				List allClasses = null;
				while (allClasses == null) {
					//	try {
					allClasses = EventManager.JVM.allClasses();
					//	}
					//	catch (final RuntimeException re) {
					//		re.printStackTrace();
					//	}
				}
				final Iterator allClassesIterator = allClasses.iterator();
				while (allClassesIterator.hasNext()
						&& (EventManager.CollectionInterfaceType == null || EventManager.MapInterfaceType == null)) {

					final ReferenceType referenceType =
						(ReferenceType) allClassesIterator.next();
					if (referenceType.name().equals("java.util.Collection")) {
						EventManager.CollectionInterfaceType = referenceType;
					}
					else if (referenceType.name().equals("java.util.Map")) {
						EventManager.MapInterfaceType = referenceType;
					}
				}

				EventManager.createRequests();

				EventManager.IsJVMInitialized = true;
			}

			// I compute the dynamically required event mask
			// (if any). Then, I compare it with the current
			// mask. I modify the JVM event requests accordingly.
			// This computation and this test should not slow
			// down the performance (too) much, and they allow
			// to improve dynamically performances by adding/
			// removing JVM event requests at runtime.
			JIPList dynamicallyRequiredEvents = (JIPList) input;
			int dynamicallyRequiredEventMask = 0;
			final int key =
				(int) ((JIPNumber) dynamicallyRequiredEvents.getHead())
					.getValue();
			dynamicallyRequiredEvents = dynamicallyRequiredEvents.getTail();

			if (dynamicallyRequiredEvents != null) {
				switch (key) {
					case 3 :
						// We are in the case of nextEvent/3, i.e., nextEvent0/2 where
						// the input argument is a list of two lists:
						//		[<list of positive filters (class name as Strings)>,
						//		 <list of expected events>]
						final JIPAtom filterListHead =
							(JIPAtom) ((JIPList) dynamicallyRequiredEvents
								.getHead()).getHead();
						if (filterListHead != null) {
							final String dynamicallySetFilter =
								filterListHead.getName();
							if (EventManager.ClassNameFilter.length == 0
									|| !dynamicallySetFilter
										.equals(EventManager.ClassNameFilter[0])
									|| (!dynamicallySetFilter.equals("") && EventManager.ClassNameFilter.length > 1)) {

								EventManager.ClassNameFilter =
									new String[] { dynamicallySetFilter };
							}
						}

						dynamicallyRequiredEvents =
							dynamicallyRequiredEvents.getTail();
					case 2 :
						dynamicallyRequiredEvents =
							(JIPList) dynamicallyRequiredEvents.getHead();
						JIPAtom atom;
						while (dynamicallyRequiredEvents != null
								&& (atom =
									(JIPAtom) dynamicallyRequiredEvents
										.getHead()) != null) {

							dynamicallyRequiredEventMask |=
								EventConverter.convertRequiredEvent(atom
									.getName());
							dynamicallyRequiredEvents =
								dynamicallyRequiredEvents.getTail();
						}

						if (dynamicallyRequiredEventMask > 0
								&& dynamicallyRequiredEventMask != EventManager.RequiredEventMask) {

							EventManager
								.setDynamicallyRequiredEvents(dynamicallyRequiredEventMask);
							EventManager.createRequests();
						}
						break;
					case 1 :
						// Nothing to do!
						break;
					default :
						System.err.println("I do not recognize the nextEvent/"
								+ key + " predicate.");
				}
			}

			//	System.err.println(key);
			//	for (Iterator iterator = EventManager.AllRequests.iterator();
			//		iterator.hasNext();
			//		) {
			//		EventRequest request = (EventRequest) iterator.next();
			//		System.err.println(request);
			//	}

		}
		catch (final Exception e) {
			// if (Constants.EXCEPTION_DEBUG) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			// }
		}
	}
	private static boolean invoke(final JIPEngine engine, final JIPTerm input) {

		EventManager.NumberOfSteps++;

		if (Constants.DEBUG) {
			System.out.println("\nEventManager.invoke(JIPTerm)");
			System.out.print("JIPTerm = ");
			System.out.println(input.toString());
			System.out.print("JIPEngine = ");
			System.out.println(engine);
		}

		// To sort the finalizers out when the program exits and
		// before the JVM exits, I must store all the events in the
		// AllEvents queue and pretend I got the event from the
		// JVM still running...
		if (EventManager.AllEvents.size() > 0) {
			return true;
		}
		else {
			// I must initialize the EventManger in
			// the invoke(JIPTerm) method, not in the
			// constructor, because in the constructor
			// this.getJIPEngine() is always null.
			EventManager.initialize((Engine) engine, input);
			try {
				final VirtualMachine jvm = EventManager.JVM;
				final EventQueue eventQueue = jvm.eventQueue();

				boolean hasSucceeded = false;
				while (!hasSucceeded) {
					// The JVM loads the required classes on the fly.
					// Thus, it is not possible up-front to know all the
					// classes that we be present in the JVM. In
					// particular, the application classes (containing the
					// main(String[]) method are ready after a few steps
					// of the JVM. I must check at run-time the availability
					// of the classes involved in field accesses.
					// (I do that before resuming the JVM for the next step.)
					EventManager.observeFields();

					// I re-start the VM to get the next event(s).
					jvm.resume();

					// I get the next events generated by the JVM.
					final EventSet eventSet = eventQueue.remove();
					if (eventSet != null && eventSet.size() > 0) {
						final Iterator iterator = eventSet.iterator();
						while (!hasSucceeded && iterator.hasNext()) {
							hasSucceeded =
								EventManager
									.buildEvent((com.sun.jdi.event.Event) iterator
										.next());
						}
					}
				}
				return hasSucceeded;
			}
			catch (final Exception e) {
				// In case of erroneous termination of the
				// remote JVM, I record its time of death.
				EventManager.VMDeathTimeStamp = System.currentTimeMillis();
				if (Constants.EXCEPTION_DEBUG) {
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
			}
			return false;
		}
	}
	private static void observeClasses() {
		if ((EventManager.RequiredEventMask & Constants.GENERATE_CLASS_LOAD_EVENT) == Constants.GENERATE_CLASS_LOAD_EVENT) {

			for (int i = 0; i < EventManager.ClassNameFilter.length; i++) {
				final ClassPrepareRequest classPrepareRequest =
					EventManager.JVM
						.eventRequestManager()
						.createClassPrepareRequest();
				classPrepareRequest
					.addClassFilter(EventManager.ClassNameFilter[i]);
				classPrepareRequest.enable();
				EventManager.AllRequests.add(classPrepareRequest);
			}
		}
		if ((EventManager.RequiredEventMask & Constants.GENERATE_CLASS_UNLOAD_EVENT) == Constants.GENERATE_CLASS_UNLOAD_EVENT) {

			for (int i = 0; i < EventManager.ClassNameFilter.length; i++) {
				final ClassUnloadRequest classUnloadRequest =
					EventManager.JVM
						.eventRequestManager()
						.createClassUnloadRequest();
				classUnloadRequest
					.addClassFilter(EventManager.ClassNameFilter[i]);
				classUnloadRequest.enable();
				EventManager.AllRequests.add(classUnloadRequest);
			}
		}
	}
	private static void observeFields() {
		if (EventManager.FieldAccesses.size() > 0
				&& (EventManager.GenerateFieldAccessEvent || EventManager.GenerateFieldModificationEvent)) {

			Iterator fieldAccessIterator =
				EventManager.FieldAccesses.iterator();
			while (fieldAccessIterator.hasNext()) {
				final String[] fieldAccess =
					(String[]) fieldAccessIterator.next();

				final Iterator allClassesIterator =
					EventManager.JVM.allClasses().iterator();
				ReferenceType classA = null;
				ReferenceType classB = null;
				Field field = null;

				while (allClassesIterator.hasNext()
						&& (classA == null || field == null || classB == null)) {

					final ReferenceType referenceType =
						(ReferenceType) allClassesIterator.next();
					if (referenceType.name().equals(fieldAccess[0])) {
						classA = referenceType;
						field = referenceType.fieldByName(fieldAccess[2]);
					}
					else if (referenceType.name().equals(fieldAccess[1])) {
						classB = referenceType;
					}
				}

				if (classA != null && field != null && classB != null) {
					fieldAccessIterator.remove();

					if (EventManager.GenerateFieldAccessEvent) {
						AccessWatchpointRequest accessWatchpointRequest =
							EventManager.JVM
								.eventRequestManager()
								.createAccessWatchpointRequest(field);
						accessWatchpointRequest.enable();

						// Yann 2002/07/08: Composition relationship management!
						// The idea is to store in a list the field accesses that
						// need to be monitored *after* the corresponding field
						// access... Looking for a modification of the elementData
						// field of the class Vector, for instance.
						List allSuperinterfaces = null;
						if (classB instanceof ClassType) {
							allSuperinterfaces =
								((ClassType) classB).allInterfaces();
						}
						else if (classB instanceof InterfaceType) {
							allSuperinterfaces =
								((InterfaceType) classB).superinterfaces();
						}
						if (Constants.DEBUG) {
							for (final Iterator iter =
								allSuperinterfaces.iterator(); iter.hasNext();) {
								final InterfaceType interfaceType =
									(InterfaceType) iter.next();
								System.err.println(interfaceType);
							}
						}
						if (allSuperinterfaces != null
								&& (allSuperinterfaces
									.contains(EventManager.CollectionInterfaceType) || allSuperinterfaces
									.contains(EventManager.MapInterfaceType))) {

							EventManager.SpecialFieldAccesses
								.add(accessWatchpointRequest);
						}
					}

					if (EventManager.GenerateFieldModificationEvent) {
						ModificationWatchpointRequest modificationWatchpointRequest =
							EventManager.JVM
								.eventRequestManager()
								.createModificationWatchpointRequest(field);
						modificationWatchpointRequest.enable();
					}

					if (Constants.DEBUG) {
						System.err.print("New field event: ");
						System.err.print(classB.name());
						System.err.print(' ');
						System.err.print(classA.name());
						System.err.print('.');
						System.err.println(field.name());
					}
				}
			}
		}
	}
	private static void observeMethodEntries() {
		if ((EventManager.RequiredEventMask & Constants.GENERATE_CONSTRUCTOR_ENTRY_EVENT) == Constants.GENERATE_CONSTRUCTOR_ENTRY_EVENT) {

			EventManager.GenerateConstructorEntryEvent = true;
		}
		if ((EventManager.RequiredEventMask & Constants.GENERATE_FINALIZER_ENTRY_EVENT) == Constants.GENERATE_FINALIZER_ENTRY_EVENT) {

			EventManager.GenerateFinalizerEntryEvent = true;
		}
		if ((EventManager.RequiredEventMask & Constants.GENERATE_METHOD_ENTRY_EVENT) == Constants.GENERATE_METHOD_ENTRY_EVENT) {

			EventManager.GenerateMethodEntryEvent = true;
		}

		if (EventManager.GenerateConstructorEntryEvent
				|| EventManager.GenerateFinalizerEntryEvent
				|| EventManager.GenerateMethodEntryEvent) {

			for (int i = 0; i < EventManager.ClassNameFilter.length; i++) {
				final MethodEntryRequest methodEntryRequest =
					EventManager.JVM
						.eventRequestManager()
						.createMethodEntryRequest();
				methodEntryRequest
					.addClassFilter(EventManager.ClassNameFilter[i]);
				methodEntryRequest.enable();
				EventManager.AllRequests.add(methodEntryRequest);
			}
		}
	}
	private static void observeMethodExits() {
		if ((EventManager.RequiredEventMask & Constants.GENERATE_CONSTRUCTOR_EXIT_EVENT) == Constants.GENERATE_CONSTRUCTOR_EXIT_EVENT) {

			EventManager.GenerateConstructorExitEvent = true;
		}
		if ((EventManager.RequiredEventMask & Constants.GENERATE_FINALIZER_EXIT_EVENT) == Constants.GENERATE_FINALIZER_EXIT_EVENT) {

			EventManager.GenerateFinalizerExitEvent = true;
		}
		if ((EventManager.RequiredEventMask & Constants.GENERATE_METHOD_EXIT_EVENT) == Constants.GENERATE_METHOD_EXIT_EVENT) {

			EventManager.GenerateMethodExitEvent = true;
		}

		if (EventManager.GenerateConstructorExitEvent
				|| EventManager.GenerateFinalizerExitEvent
				|| EventManager.GenerateMethodExitEvent) {

			for (int i = 0; i < EventManager.ClassNameFilter.length; i++) {
				final MethodExitRequest methodExitRequest =
					EventManager.JVM
						.eventRequestManager()
						.createMethodExitRequest();
				methodExitRequest
					.addClassFilter(EventManager.ClassNameFilter[i]);
				methodExitRequest.enable();
				EventManager.AllRequests.add(methodExitRequest);
			}
		}
	}
	protected static void reset() {
		EventManager.RequiredEventMask = 0;
		EventManager.GenerateConstructorEntryEvent = false;
		EventManager.GenerateConstructorExitEvent = false;
		EventManager.GenerateFieldAccessEvent = false;
		EventManager.GenerateFieldModificationEvent = false;
		EventManager.GenerateFinalizerEntryEvent = false;
		EventManager.GenerateFinalizerExitEvent = false;
		EventManager.GenerateMethodEntryEvent = false;
		EventManager.GenerateMethodExitEvent = false;
		EventManager.GenerateProgramEndEvent = false;
		EventManager.AllRequests.clear();
		EventManager.AllThreads.clear();
		EventManager.IsJVMInitialized = false;
	}
	private static void setDynamicallyRequiredEvents(
		final int dynamicallyRequiredEventMask) {
		System.out.println("Dynamically required events set");

		// Yann 2003/09/30: Warning!
		// I warn the user that some events can only be
		// required statically:
		//	- Collection events;
		//	- Finalizer events;
		//	- Program-end events;
		//	- Method returned value events.
		if ((EventManager.RequiredEventMask & Constants.GENERATE_COLLECTION_EVENT) == 0
				&& (dynamicallyRequiredEventMask & Constants.GENERATE_COLLECTION_EVENT) == Constants.GENERATE_COLLECTION_EVENT) {

			System.out
				.println("\tWarning! Collection events can only be required statically.");
		}
		if ((EventManager.RequiredEventMask & Constants.GENERATE_FINALIZER_ENTRY_EVENT) == 0
				&& (EventManager.RequiredEventMask & Constants.GENERATE_FINALIZER_EXIT_EVENT) == 0
				&& ((dynamicallyRequiredEventMask & Constants.GENERATE_FINALIZER_ENTRY_EVENT) == Constants.GENERATE_FINALIZER_ENTRY_EVENT || (dynamicallyRequiredEventMask & Constants.GENERATE_FINALIZER_EXIT_EVENT) == Constants.GENERATE_FINALIZER_EXIT_EVENT)) {

			System.out
				.println("\tWarning! Finalizer events can only be required statically.");
		}
		if ((EventManager.RequiredEventMask & Constants.GENERATE_PROGRAM_END_EVENT) == 0
				&& (dynamicallyRequiredEventMask & Constants.GENERATE_PROGRAM_END_EVENT) == Constants.GENERATE_PROGRAM_END_EVENT) {

			System.out
				.println("\tWarning! Program-end events can only be required statically.");
		}
		if ((EventManager.RequiredEventMask & Constants.GENERATE_METHOD_RETURNED_VALUE_EVENT) == 0
				&& (dynamicallyRequiredEventMask & Constants.GENERATE_METHOD_RETURNED_VALUE_EVENT) == Constants.GENERATE_METHOD_RETURNED_VALUE_EVENT) {

			System.out
				.println("\tWarning! Method returned value events can only be required statically.");
		}

		EventManager.RequiredEventMask = dynamicallyRequiredEventMask;
	}

	public JIPTerm getOutput() {
		JIPTerm result = null;
		if (EventManager.CurrentEventNumber == 0) {
			result =
				JIPAtom
					.getNewAtom("\nEventManager error: CurrentEventNumber == 0");
		}
		else {
			// I consume the first event in the queue.
			final Event event = (Event) EventManager.AllEvents.remove(0);

			// I manage the trace output (if any).
			// (See Caffeine class.)
			try {
				EventManager.TraceWriter.write(event.toData());
				EventManager.TraceWriter.write('\n');
				EventManager.TraceWriter.flush();
			}
			catch (final IOException ioe) {
				ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}

			// Yann 2003/07/31: Listener.
			// I notify all the listerners that a new event
			// has been built and is sent to the Prolog engine.
			final Iterator iterator = EventManager.CaffeineListeners.iterator();
			while (iterator.hasNext()) {
				((EngineListener) iterator.next()).eventEmitted(event);
			}

			result =
				JIPFunctor.getNewFunctor(event.getType(), JIPList.getNewList(
					JIPNumber.getNewNumber(event.getIdentifier()),
					JIPList.getNewList(
						JIPAtom.getNewAtom(event.getName()),
						JIPList.getNewList(JIPNumber.getNewNumber(event
							.getReceiverID()), JIPList.getNewList(JIPNumber
							.getNewNumber(event.getArgumentID()), JIPList
							.getNewList(
								JIPAtom.getNewAtom(event.getComment()),
								JIPList.getNewList(JIPAtom.getNewAtom(event
									.getReturnedValue()
									.toString()), JIPList.getNewList(JIPAtom
									.getNewAtom(event.getSourceName()), JIPList
									.getNewList(JIPNumber.getNewNumber(event
										.getLineNumber()), null)))))))));
		}

		if (Constants.DEBUG) {
			System.out.print("EventManager.getOutput() = ");
			System.out.println(result);
		}

		return result;
	}
	public boolean invoke(final JIPTerm jipTerm) {
		return EventManager.invoke(this.getJIPEngine(), jipTerm);
	}
	public boolean isDeterministic() {
		return false;
	}
}
