// $Id: StateMachinesFactoryMDRImpl.java,v 1.2 2006/03/02 05:07:41 vauchers Exp $
// Copyright (c) 1996-2005 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.model.mdr;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.argouml.model.Model;
import org.argouml.model.ModelEventPump;
import org.argouml.model.StateMachinesFactory;
import org.omg.uml.behavioralelements.statemachines.CallEvent;
import org.omg.uml.behavioralelements.statemachines.ChangeEvent;
import org.omg.uml.behavioralelements.statemachines.CompositeState;
import org.omg.uml.behavioralelements.statemachines.Event;
import org.omg.uml.behavioralelements.statemachines.FinalState;
import org.omg.uml.behavioralelements.statemachines.Guard;
import org.omg.uml.behavioralelements.statemachines.Pseudostate;
import org.omg.uml.behavioralelements.statemachines.SignalEvent;
import org.omg.uml.behavioralelements.statemachines.SimpleState;
import org.omg.uml.behavioralelements.statemachines.State;
import org.omg.uml.behavioralelements.statemachines.StateMachine;
import org.omg.uml.behavioralelements.statemachines.StateVertex;
import org.omg.uml.behavioralelements.statemachines.StubState;
import org.omg.uml.behavioralelements.statemachines.SubmachineState;
import org.omg.uml.behavioralelements.statemachines.SynchState;
import org.omg.uml.behavioralelements.statemachines.TimeEvent;
import org.omg.uml.behavioralelements.statemachines.Transition;
import org.omg.uml.foundation.core.BehavioralFeature;
import org.omg.uml.foundation.core.Classifier;
import org.omg.uml.foundation.core.ModelElement;
import org.omg.uml.foundation.core.Namespace;
import org.omg.uml.foundation.datatypes.PseudostateKindEnum;

/**
 * Factory to create UML classes for the UML BehaviorialElements::StateMachines
 * package.
 * <p>
 *
 * Abstract metatypes from the UML metamodel do not have create methods.
 * <p>
 * Emulates synchronous event delivery as implemented in NSUML library.
 * Any methods which call org.omg.uml or NetBeans MDR methods that generate
 * events must call flushModelEvents() before returning.  'Get' methods do
 * not needed to be handled specially.
 * <p>
 * TODO: Change visibility to package after reflection problem solved.
 *
 * @since ARGO0.19.3
 * @author Bob Tarling
 * @author Ludovic Maître
 * @author Tom Morris
 */
public class StateMachinesFactoryMDRImpl extends AbstractUmlModelFactoryMDR
        implements StateMachinesFactory {

    /**
     * Logger
     */
    private Logger LOG = Logger.getLogger(StateMachinesFactoryMDRImpl.class);
	
    /**
     * The model implementation.
     */
    private MDRModelImplementation nsmodel;
    
    /**
     * The model event pump.
     */
    private ModelEventPump eventPump;
    
    /**
     * Don't allow instantiation.
     *
     * @param implementation
     *            To get other helpers and factories.
     */
    StateMachinesFactoryMDRImpl(MDRModelImplementation implementation) {
        nsmodel = implementation;
        eventPump = nsmodel.getModelEventPump();
    }

    /**
     * Create an empty but initialized instance of a UML CallEvent.
     *
     * @return an initialized UML CallEvent instance.
     */
    public Object createCallEvent() {
        CallEvent myCallEvent = nsmodel.getUmlPackage().getStateMachines()
                .getCallEvent().createCallEvent();
        super.initialize(myCallEvent);
        eventPump.flushModelEvents();
        return myCallEvent;
    }

    /**
     * Create an empty but initialized instance of a UML ChangeEvent.
     *
     * @return an initialized UML ChangeEvent instance.
     */
    public Object createChangeEvent() {
        ChangeEvent myChangeEvent = nsmodel.getUmlPackage().getStateMachines()
                .getChangeEvent().createChangeEvent();
        super.initialize(myChangeEvent);
        eventPump.flushModelEvents();
        return myChangeEvent;
    }

    /**
     * Create an empty but initialized instance of a UML CompositeState.
     *
     * @return an initialized UML CompositeState instance.
     */
    public Object createCompositeState() {
        CompositeState myCompositeState = nsmodel.getUmlPackage()
                .getStateMachines().getCompositeState().createCompositeState();
        super.initialize(myCompositeState);
        eventPump.flushModelEvents();
        return myCompositeState;
    }

    /**
     * Create an empty but initialized instance of a UML FinalState.
     *
     * @return an initialized UML FinalState instance.
     */
    public Object createFinalState() {
        FinalState myFinalState = nsmodel.getUmlPackage().getStateMachines()
                .getFinalState().createFinalState();
        super.initialize(myFinalState);
        eventPump.flushModelEvents();
        return myFinalState;
    }

    /**
     * Create an empty but initialized instance of a UML Guard.
     *
     * @return an initialized UML Guard instance.
     */
    public Object createGuard() {
        Guard myGuard = nsmodel.getUmlPackage().getStateMachines().getGuard()
                .createGuard();
        super.initialize(myGuard);
        eventPump.flushModelEvents();
        return myGuard;
    }

    /**
     * Create an empty but initialized instance of a UML Pseudostate.
     *
     * @return an initialized UML Pseudostate instance.
     */
    public Object createPseudostate() {
        Pseudostate myPseudostate = nsmodel.getUmlPackage().getStateMachines()
                .getPseudostate().createPseudostate();
        super.initialize(myPseudostate);
        eventPump.flushModelEvents();
        return myPseudostate;
    }

    /**
     * Create an empty but initialized instance of a UML SignalEvent.
     *
     * @return an initialized UML SignalEvent instance.
     */
    public Object createSignalEvent() {
        SignalEvent mySignalEvent = nsmodel.getUmlPackage().getStateMachines()
                .getSignalEvent().createSignalEvent();
        super.initialize(mySignalEvent);
        eventPump.flushModelEvents();
        return mySignalEvent;
    }

    /**
     * Create an empty but initialized instance of a UML SimpleState.
     *
     * @return an initialized UML SimpleState instance.
     */
    public Object createSimpleState() {
        SimpleState mySimpleState = nsmodel.getUmlPackage().getStateMachines()
                .getSimpleState().createSimpleState();
        super.initialize(mySimpleState);
        eventPump.flushModelEvents();
        return mySimpleState;
    }

    /**
     * Create an empty but initialized instance of a UML StateMachine.
     *
     * @return an initialized UML StateMachine instance.
     */
    public Object createStateMachine() {
        StateMachine myStateMachine = nsmodel.getUmlPackage()
                .getStateMachines().getStateMachine().createStateMachine();
        super.initialize(myStateMachine);
        eventPump.flushModelEvents();
        return myStateMachine;
    }

    /**
     * Create an empty but initialized instance of a UML StubState.
     *
     * @return an initialized UML StubState instance.
     */
    public Object createStubState() {
        StubState myStubState = nsmodel.getUmlPackage().getStateMachines()
                .getStubState().createStubState();
        super.initialize(myStubState);
        eventPump.flushModelEvents();
        return myStubState;
    }

    /**
     * Create an empty but initialized instance of a UML SubmachineState.
     *
     * @return an initialized UML SubmachineState instance.
     */
    public Object createSubmachineState() {
        SubmachineState mySubmachineState = nsmodel.getUmlPackage()
                .getStateMachines().getSubmachineState()
                .createSubmachineState();
        super.initialize(mySubmachineState);
        eventPump.flushModelEvents();
        return mySubmachineState;
    }

    /**
     * Create an empty but initialized instance of a UML SynchState.
     *
     * @return an initialized UML SynchState instance.
     */
    public Object createSynchState() {
        SynchState mySynchState = nsmodel.getUmlPackage().getStateMachines()
                .getSynchState().createSynchState();
        super.initialize(mySynchState);
        eventPump.flushModelEvents();
        return mySynchState;
    }

    /**
     * Create an empty but initialized instance of a UML TimeEvent.
     *
     * @return an initialized UML TimeEvent instance.
     */
    public Object createTimeEvent() {
        TimeEvent myTimeEvent = nsmodel.getUmlPackage().getStateMachines()
                .getTimeEvent().createTimeEvent();
        super.initialize(myTimeEvent);
        eventPump.flushModelEvents();
        return myTimeEvent;
    }

    /**
     * Create an empty but initialized instance of a UML Transition.
     *
     * @return an initialized UML Transition instance.
     */
    public Object createTransition() {
        Transition myTransition = nsmodel.getUmlPackage().getStateMachines()
                .getTransition().createTransition();
        super.initialize(myTransition);
        eventPump.flushModelEvents();
        return myTransition;
    }

    /**
     * Builds a compositestate as top for some statemachine.
     * <p>
     *
     * @param statemachine
     *            the given statemachine
     * @return MCompositeState the newly build top state
     * @see #buildCompositeState(Object)
     */
    public Object buildCompositeStateOnStateMachine(Object statemachine) {
        if (statemachine instanceof StateMachine) {
            CompositeState state = (CompositeState) createCompositeState();
            state.setStateMachine((StateMachine) statemachine);
            state.setName("top");
            eventPump.flushModelEvents();
            return state;
        }
        throw new IllegalArgumentException("statemachine");
    }

    /**
     * Builds a state machine owned by the given context.
     *
     * @param oContext
     *            the given context
     * @return MStateMachine the newly build statemachine
     */
    public Object buildStateMachine(Object oContext) {
        if (oContext != null
                && (nsmodel.getStateMachinesHelper().
                        isAddingStatemachineAllowed(oContext))) {
            
            StateMachine machine = (StateMachine) createStateMachine();
            ModelElement context = (ModelElement) oContext;
            machine.setContext(context);
            if (context instanceof Classifier) {
                machine.setNamespace((Classifier) context);
            } else if (context instanceof BehavioralFeature) {
                BehavioralFeature feature = (BehavioralFeature) context;
                machine.setNamespace(feature.getOwner());
            }
            Object top = buildCompositeStateOnStateMachine(machine);
            machine.setTop((State) top);
            eventPump.flushModelEvents();
            return machine;
        }
        throw new IllegalArgumentException("In buildStateMachine: "
                + "context null or not legal");
    }

    /**
     * Builds a complete transition including all associations (composite state
     * the transition belongs to, source the transition is coming from,
     * destination the transition is going to). The transition is owned by the
     * compositestate.
     * <p>
     *
     * @param owningState
     *            the composite state that owns the transition
     * @param source
     *            the source of the transition (a StateVertex)
     * @param dest
     *            the destination of the transition (a StateVertex)
     * @return The newly build Transition.
     */
    public Object buildTransition(Object owningState, Object source, 
            Object dest) {
        if (!(owningState instanceof CompositeState)) {
            throw new IllegalArgumentException("owningState");
        }
        if (!(source instanceof StateVertex)) {
            throw new IllegalArgumentException("source");
        }
        if (!(dest instanceof StateVertex)) {
            throw new IllegalArgumentException("dest");
        }

        CompositeState compositeState = (CompositeState) owningState;
        if (compositeState.getSubvertex().contains(source)
                && compositeState.getSubvertex().contains(dest)) {
    	    Transition trans = (Transition) createTransition();
    	    compositeState.getInternalTransition().add(trans);
    	    trans.setSource((StateVertex) source);
    	    trans.setTarget((StateVertex) dest);
            eventPump.flushModelEvents();
    	    return trans;

      	}
        throw new IllegalArgumentException("In buildTransition: "
                + "arguments not legal");
    }

    /**
     * Builds a pseudostate initialized as a choice pseudostate. The pseudostate
     * will be a subvertix of the given compositestate. The parameter
     * compositeState is of type Object to decouple the factory and NSUML as
     * much as possible from the rest of ArgoUML.
     * <p>
     *
     * @param compositeState
     *            the parent
     * @return MPseudostate
     */
    public Object buildPseudoState(Object compositeState) {
        if (compositeState instanceof CompositeState) {
            Pseudostate state = (Pseudostate) createPseudostate();
            state.setKind(PseudostateKindEnum.PK_CHOICE);
            state.setContainer((CompositeState) compositeState);
            ((CompositeState) compositeState).getSubvertex().add(state);
            eventPump.flushModelEvents();
            return state;
        }
        return null;
    }

    /**
     * Builds a synchstate initalized with bound 0. The synchstate will be a
     * subvertix of the given compositestate. The parameter compositeState is of
     * type Object to decouple the factory and NSUML as much as possible from
     * the rest of ArgoUML.
     *
     * @param compositeState
     *            the given compositestate
     * @return MSynchState the newly created SynchState
     */
    public Object buildSynchState(Object compositeState) {
        if (compositeState instanceof CompositeState) {
            SynchState state = (SynchState) createSynchState();
            state.setBound(0);
            state.setContainer((CompositeState) compositeState);
            eventPump.flushModelEvents();
            return state;
        }
        return null;
    }

    /**
     * Builds a stubstate initalized with an empty referenced state. The
     * stubstate will be a subvertix of the given compositestate. The parameter
     * compositeState is of type Object to decouple the factory and NSUML as
     * much as possible from the rest of ArgoUML.
     *
     * @param compositeState
     *            the given composite state
     * @return MSynchState the newly build stubstate
     */
    public Object buildStubState(Object compositeState) {
        if (compositeState instanceof CompositeState) {
            StubState state = (StubState) createStubState();
            state.setReferenceState("");
            state.setContainer((CompositeState) compositeState);
            eventPump.flushModelEvents();
            return state;
        }
        return null;
    }

    /**
     * Builds a compositestate initalized as a non-concurrent composite state.
     * The compositestate will be a subvertix of the given compositestate. The
     * parameter compositeState is of type Object to decouple the factory and
     * NSUML as much as possible from the rest of ArgoUML.
     *
     * @param compositeState
     *            the given compositestate
     * @return MSynchState the newly build synchstate
     * @see #buildCompositeStateOnStateMachine(Object)
     */
    public Object buildCompositeState(Object compositeState) {
        if (compositeState instanceof CompositeState) {
            CompositeState state = (CompositeState) createCompositeState();
            state.setConcurrent(false);
            state.setContainer((CompositeState) compositeState);
            eventPump.flushModelEvents();
            return state;
        }
        return null;
    }

    /**
     * Builds a simplestate. The simplestate will be a subvertex of the given
     * compositestate. The parameter compositeState is of type Object to
     * decouple the factory and MDR as much as possible from the rest of
     * ArgoUML.
     *
     * @param compositeState
     *            the given compositestate
     * @return SimpleState the newly built simple state
     */
    public Object buildSimpleState(Object compositeState) {
        if (compositeState instanceof CompositeState) {
            SimpleState state = (SimpleState) createSimpleState();
            state.setContainer((CompositeState) compositeState);
            eventPump.flushModelEvents();
            return state;
        }
        return null;
    }

    /**
     * Builds a finalstate. The finalstate will be a subvertex of the given
     * compositestate. The parameter compositeState is of type Object to
     * decouple the factory and MDR as much as possible from the rest of
     * ArgoUML.
     *
     * @param compositeState
     *            the given compositestate
     * @return FinalState the created FinalState
     */
    public Object buildFinalState(Object compositeState) {
        if (compositeState instanceof CompositeState) {
            FinalState state = (FinalState) createFinalState();
            state.setContainer((CompositeState) compositeState);
            eventPump.flushModelEvents();
            return state;
        }
        return null;
    }

    /**
     * Builds a submachinestate. The submachinestate will be a subvertix of the
     * given compositestate. The parameter compositeState is of type Object to
     * decouple the factory and NSUML as much as possible. from the rest of
     * ArgoUML.
     *
     * @param compositeState
     *            the given compositestate
     * @return MSubmachineState the given submachinestate
     */
    public Object buildSubmachineState(Object compositeState) {
        if (compositeState instanceof CompositeState) {
            SubmachineState state = (SubmachineState) createSubmachineState();
            state.setStateMachine(null);
            state.setContainer((CompositeState) compositeState);
            eventPump.flushModelEvents();
            return state;
        }
        return null;
    }

    /**
     * Builds an internal transition for a given state. The parameter state is
     * of type Object to decouple the factory and NSUML as much as possible.
     *
     * @param state
     *            The state the internal transition should belong to
     * @return MTransition The internal transition constructed
     */
    public Object buildInternalTransition(Object state) {
        if (state instanceof State) {
            Transition trans = (Transition) createTransition();
            ((State) state).getInternalTransition().add(trans);
            trans.setSource((State) state);
            trans.setTarget((State) state);
            eventPump.flushModelEvents();
            return trans;
        }
        return null;        
    }

    /**
     * Build a transition between a source state and a target state.
     *
     * This should not be used for internal transitions!
     *
     * @param source
     *            The source state
     * @param target
     *            The target state
     * @return MTransition The resulting transition between source an state
     */
    public Object buildTransition(Object source, Object target) {
        if (source instanceof StateVertex && target instanceof StateVertex) {
            Transition trans = (Transition) createTransition();
            trans.setSource((StateVertex) source);
            trans.setTarget((StateVertex) target);
            trans.setStateMachine((StateMachine) nsmodel.
                    getStateMachinesHelper().getStateMachine(source));
            eventPump.flushModelEvents();
            return trans;
        }
        return null;
    }

    /**
     * Builds a callevent whose namespace (and therefore the ownership) is
     * given.
     * 
     * @param ns
     *            the namespace
     * @return MCallEvent
     */
    public Object buildCallEvent(Object ns) {
        CallEvent event = (CallEvent) createCallEvent();
        event.setNamespace((Namespace) ns);
        event.setName("");
        return event;
    }

    /**
     * Create a initialized instance of a CallEvent with a name as a trigger for
     * a Transition. If an operation with corresponding name can be found, it is
     * linked.
     *
     * @param trans
     *            Object MTransition for which the CallEvent is a trigger
     * @param name
     *            String with the trigger name - should not include "()"
     * @param ns
     *            the namespace
     * @return an initialized UML CallEvent instance.
     */
    public Object buildCallEvent(Object trans, String name, Object ns) {
        if (!(trans instanceof Transition)) {
            throw new IllegalArgumentException();
        }
        CallEvent evt = (CallEvent) createCallEvent();
        evt.setNamespace((Namespace) ns);

        String operationName = (name.indexOf("(") > 0) ? name.substring(0,
                name.indexOf("(")).trim() : name.trim();
        nsmodel.getCoreHelper().setName(evt, operationName);
        Object op = nsmodel.getStateMachinesHelper().findOperationByName(trans,
                operationName);
        if (op != null) {
            nsmodel.getCommonBehaviorHelper().setOperation(evt, op);
        }
        eventPump.flushModelEvents();
        return evt;        
    }

    /**
     * Builds a signalevent whose namespace (and therefore the ownership) is
     * given.
     * 
     * @param ns
     *            the Namespace
     * @return MSignalEvent
     */
    public Object buildSignalEvent(Object ns) {
        SignalEvent event = (SignalEvent) createSignalEvent();
        event.setNamespace((Namespace) ns);
        event.setName("");
        eventPump.flushModelEvents();
        return event;
    }

    /**
     * Builds a named signalevent whose namespace (and therefore the ownership)
     * is given.
     * 
     * @param ns
     *            the Namespace
     * @param name
     *            String the name of the SignalEvent
     * @return MSignalEvent
     */
    public Object buildSignalEvent(String name, Object ns) {
        SignalEvent event = (SignalEvent) createSignalEvent();
        event.setNamespace((Namespace) ns);
        event.setName(name);
        eventPump.flushModelEvents();
        return event;
    }

    /**
     * Builds a timeevent whose namespace (and therefore the ownership) is
     * given.
     * 
     * @param ns
     *            the Namespace
     * @return MTimeEvent
     */
    public Object buildTimeEvent(Object ns) {
        TimeEvent event = (TimeEvent) createTimeEvent();
        event.setNamespace((Namespace) ns);
        event.setName("");
        eventPump.flushModelEvents();
        return event;
    }

    /**
     * Builds a timeevent whose namespace (and therefore the ownership) is
     * given.
     *
     * @param s
     *            String for creating the TimeExpression
     * @param ns
     *            the Namespace
     * @return MTimeEvent
     */
    public Object buildTimeEvent(String s, Object ns) {
        TimeEvent event = (TimeEvent) createTimeEvent();
        event.setNamespace((Namespace) ns);
        event.setName("");
        Object te = nsmodel.getDataTypesFactory().createTimeExpression("", s);
        nsmodel.getStateMachinesHelper().setWhen(event, te);
        eventPump.flushModelEvents();
        return event;
    }

    /**
     * Builds a changeevent whose namespace (and therefore the ownership) is
     * given.
     * 
     * @param ns
     *            the Namespace
     * @return MChangeEvent
     */
    public Object buildChangeEvent(Object ns) {
        ChangeEvent event = (ChangeEvent) createChangeEvent();
        event.setNamespace((Namespace) ns);
        event.setName("");
        eventPump.flushModelEvents();
        return event;
    }

    /**
     * Builds a changeevent whose namespace (and therefore the ownership) is
     * given.
     * 
     * @param ns
     *            the Namespace
     * @param s
     *            String for creating the BooleanExpression
     * @return MChangeEvent
     */
    public Object buildChangeEvent(String s, Object ns) {
        ChangeEvent event = (ChangeEvent) createChangeEvent();
        event.setNamespace((Namespace) ns);
        event.setName("");
        Object ce = nsmodel.getDataTypesFactory().
                createBooleanExpression("", s);
        nsmodel.getStateMachinesHelper().setExpression(event, ce);
        eventPump.flushModelEvents();
        return event;
    }

    /**
     * Builds a guard condition with a given transition. The guard condition is
     * empty by default. The parameter is of type Object to decouple the factory
     * and NSUML as much as possible.
     * 
     * @param transition
     *            The transition that owns the resulting guard condition
     * @return MGuard The resulting guard condition
     */
    public Object/*MGuard*/ buildGuard(Object transition) {
        if (transition instanceof Transition) {
            Object guard = createGuard();
            nsmodel.getCommonBehaviorHelper().setTransition(guard, transition);
            eventPump.flushModelEvents();
            return guard;
        }
        throw new IllegalArgumentException("transition: " + transition);
    }

    /**
     * @param elem
     *            the UML element to be deleted
     */
    void deleteCallEvent(Object elem) {
        if (!(elem instanceof CallEvent)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @param elem
     *            the UML element to be deleted
     */
    void deleteChangeEvent(Object elem) {
        if (!(elem instanceof ChangeEvent)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Deletes any associated subVertices.
     *
     * @param elem
     *            the UML element to be deleted
     */
    void deleteCompositeState(Object elem) {
        if (!(elem instanceof CompositeState)) {
            throw new IllegalArgumentException();
        }

        Collection vertices = ((CompositeState) elem).getSubvertex();
        Iterator it = vertices.iterator();
        while (it.hasNext()) {
            StateVertex vertex = (StateVertex) it.next();
            nsmodel.getUmlFactory().delete(vertex);
        }
        eventPump.flushModelEvents();
    }

    /**
     * @param elem
     *            the UML element to be deleted
     */
    void deleteEvent(Object elem) {
        if (!(elem instanceof Event)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @param elem
     *            the UML element to be deleted
     */
    void deleteFinalState(Object elem) {
        if (!(elem instanceof FinalState)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @param elem
     *            the UML element to be deleted
     */
    void deleteGuard(Object elem) {
        if (!(elem instanceof Guard)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @param elem
     *            the UML element to be deleted
     */
    void deletePseudostate(Object elem) {
        if (!(elem instanceof Pseudostate)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @param elem
     *            the UML element to be deleted
     */
    void deleteSignalEvent(Object elem) {
        if (!(elem instanceof SignalEvent)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @param elem
     *            the UML element to be deleted
     */
    void deleteSimpleState(Object elem) {
        if (!(elem instanceof SimpleState)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * deletes its top state, which is a composite state (state vertex).
     *
     * @param elem
     *            the state machine to be removed.
     */
    void deleteStateMachine(Object elem) {
        if (!(elem instanceof StateMachine)) {
            throw new IllegalArgumentException();
        }

        State top = (State) Model.getFacade().getTop(elem);
        if (top != null) {
            nsmodel.getUmlFactory().delete(top);
        }
    }

    /**
     * Deletes the outgoing and incoming transitions of a statevertex.
     * <p>
     *
     * @param elem
     *            the UML element to be deleted
     */
    void deleteStateVertex(Object elem) {
        if (!(elem instanceof StateVertex)) {
            throw new IllegalArgumentException();
        }

        Collection col = ((StateVertex) elem).getIncoming();
        Iterator it = col.iterator();
        while (it.hasNext()) {
            nsmodel.getUmlFactory().delete(it.next());
        }
        col = ((StateVertex) elem).getOutgoing();
        it = col.iterator();
        while (it.hasNext()) {
            nsmodel.getUmlFactory().delete(it.next());
        }
    }

    /**
     * @param elem
     *            the UML element to be deleted
     */
    void deleteStubState(Object elem) {
        if (!(elem instanceof StubState)) {
            throw new IllegalArgumentException();
        }

    }

    /**
     * @param elem
     *            the UML element to be deleted
     */
    void deleteSubmachineState(Object elem) {
        if (!(elem instanceof SubmachineState)) {
            throw new IllegalArgumentException();
        }

    }

    /**
     * @param elem
     *            the UML element to be deleted
     */
    void deleteSynchState(Object elem) {
        if (!(elem instanceof SynchState)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @param elem
     *            the UML element to be deleted
     */
    void deleteTimeEvent(Object elem) {
        if (!(elem instanceof TimeEvent)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @param elem
     *            the UML element to be deleted
     */
    void deleteTransition(Object elem) {
        if (!(elem instanceof Transition)) {
            throw new IllegalArgumentException();
        }
    }

}

