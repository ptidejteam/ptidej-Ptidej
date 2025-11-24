// $Id: ActivityGraphsFactoryMDRImpl.java,v 1.2 2006/03/02 05:07:42 vauchers Exp $
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

import org.argouml.model.ActivityGraphsFactory;
import org.argouml.model.ModelEventPump;
import org.omg.uml.behavioralelements.activitygraphs.ActionState;
import org.omg.uml.behavioralelements.activitygraphs.ActivityGraph;
import org.omg.uml.behavioralelements.activitygraphs.CallState;
import org.omg.uml.behavioralelements.activitygraphs.ClassifierInState;
import org.omg.uml.behavioralelements.activitygraphs.ObjectFlowState;
import org.omg.uml.behavioralelements.activitygraphs.Partition;
import org.omg.uml.behavioralelements.activitygraphs.SubactivityState;
import org.omg.uml.behavioralelements.statemachines.CompositeState;
import org.omg.uml.behavioralelements.statemachines.State;
import org.omg.uml.foundation.core.BehavioralFeature;
import org.omg.uml.foundation.core.Classifier;
import org.omg.uml.foundation.core.ModelElement;
import org.omg.uml.foundation.core.Namespace;

/**
 * Factory to create UML classes for the UML BehaviorialElements::ActivityGraphs
 * package.
 * 
 * TODO: Change visibility to package after reflection problem solved.
 * 
 * <p>
 * Emulates synchronous event delivery as implemented in NSUML library.
 * Any methods which call org.omg.uml or NetBeans MDR methods that generate
 * events must call flushModelEvents() before returning.  'Get' methods do
 * not needed to be handled specially.
 * <p>
 * 
 * @since ARGO0.19.5
 * @author Ludovic Maître
 * Derived from NSUML implementation by:
 * @author Thierry Lach
 */
public class ActivityGraphsFactoryMDRImpl extends AbstractUmlModelFactoryMDR
        implements ActivityGraphsFactory {

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
    ActivityGraphsFactoryMDRImpl(MDRModelImplementation implementation) {
        nsmodel = implementation;
        eventPump = nsmodel.getModelEventPump();
    }

    /**
     * @see org.argouml.model.ActivityGraphsFactory#createActionState()
     */
    public Object createActionState() {
        ActionState myActionState = nsmodel.getUmlPackage().getActivityGraphs().
            getActionState().createActionState();
        super.initialize(myActionState);
        eventPump.flushModelEvents();
        return myActionState;
    }

    /**
     * @see org.argouml.model.ActivityGraphsFactory#createActivityGraph()
     */
    public Object createActivityGraph() {
        ActivityGraph myActivityGraph = nsmodel.getUmlPackage().
            getActivityGraphs().getActivityGraph().createActivityGraph();
        super.initialize(myActivityGraph);
        eventPump.flushModelEvents();
        return myActivityGraph;
    }

    /**
     * @see org.argouml.model.ActivityGraphsFactory#createCallState()
     */
    public Object createCallState() {
        CallState myCallState = nsmodel.getUmlPackage().getActivityGraphs().
            getCallState().createCallState();
        super.initialize(myCallState);
        eventPump.flushModelEvents();
        return myCallState;
    }

    /**
     * @see org.argouml.model.ActivityGraphsFactory#createClassifierInState()
     */
    public Object createClassifierInState() {
        ClassifierInState myClassifierInState = nsmodel.getUmlPackage().
            getActivityGraphs().getClassifierInState().
                createClassifierInState();
        super.initialize(myClassifierInState);
        eventPump.flushModelEvents();
        return myClassifierInState;
    }

    /**
     * @see org.argouml.model.ActivityGraphsFactory#createObjectFlowState()
     */
    public Object createObjectFlowState() {
        ObjectFlowState myObjectFlowState = nsmodel.getUmlPackage().
            getActivityGraphs().getObjectFlowState().
                createObjectFlowState();
        super.initialize(myObjectFlowState);
        eventPump.flushModelEvents();
        return myObjectFlowState;
    }

    /**
     * @see org.argouml.model.ActivityGraphsFactory#createPartition()
     */
    public Object createPartition() {
        Partition myPartition = nsmodel.getUmlPackage().getActivityGraphs().
            getPartition().createPartition();
        super.initialize(myPartition);
        eventPump.flushModelEvents();
        return myPartition;
    }

    /**
     * @see org.argouml.model.ActivityGraphsFactory#createSubactivityState()
     */
    public Object createSubactivityState() {
        SubactivityState mySubactivityState = nsmodel.getUmlPackage().
            getActivityGraphs().getSubactivityState().
                createSubactivityState();
        super.initialize(mySubactivityState);
        eventPump.flushModelEvents();
        return mySubactivityState;
    }

    /**
     * @see org.argouml.model.ActivityGraphsFactory#buildActivityGraph(java.lang.Object)
     */
    public Object buildActivityGraph(Object theContext) {
        if (theContext instanceof ModelElement) {
            ActivityGraph myActivityGraph = (ActivityGraph) 
                createActivityGraph();
            myActivityGraph.setContext((ModelElement) theContext);
            if (theContext instanceof Namespace) {
                myActivityGraph.setNamespace((Namespace) theContext);
            } else if (theContext instanceof BehavioralFeature) {
                myActivityGraph.setNamespace(((BehavioralFeature) theContext).
                        getOwner());
            }
            Object top = nsmodel.getStateMachinesFactory().
                buildCompositeStateOnStateMachine(myActivityGraph);
            myActivityGraph.setTop((State) top);
            eventPump.flushModelEvents();
            return myActivityGraph;
        }
        throw new IllegalArgumentException(
                "Cannot create an ActivityGraph with context " + theContext);
    }

    /**
     * Builds an objectflowstate. The objectflowstate will be a subvertix of the
     * given compositestate. The parameter compositeState is of type Object to
     * decouple the factory and NSUML as much as possible from the rest of
     * ArgoUML.
     * 
     * @author MVW
     * @param compositeState
     *            the given compositestate
     * @return Object the newly build objectflow state
     */
    public Object buildObjectFlowState(Object compositeState) {
        if (!(compositeState instanceof CompositeState)) {
            throw new IllegalArgumentException();
        }

        ObjectFlowState state = (ObjectFlowState) createObjectFlowState();
        state.setContainer((CompositeState) compositeState);
        eventPump.flushModelEvents();
        return state;
    }

    /**
     * @see org.argouml.model.ActivityGraphsFactory#buildClassifierInState(java.lang.Object,
     *      java.lang.Object)
     */
    public Object buildClassifierInState(Object classifier, Object state) {
        if (!(classifier instanceof Classifier)) {
            throw new IllegalArgumentException();
        }
        if (!(state instanceof State)) {
            throw new IllegalArgumentException();
        }

        ClassifierInState c = (ClassifierInState) createClassifierInState();
        c.setType((Classifier) classifier);
        c.getInState().add(state);
        eventPump.flushModelEvents();
        return c;
    }

    /**
     * @param elem
     *            the ActionState to be deleted
     */
    void deleteActionState(Object elem) {
        if (!(elem instanceof ActionState)) {
            throw new IllegalArgumentException();
        }

    }

    /**
     * @param elem
     *            the ActivityGraph to be deleted
     */
    void deleteActivityGraph(Object elem) {
        if (!(elem instanceof ActivityGraph)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @param elem
     *            the CallState to be deleted
     */
    void deleteCallState(Object elem) {
        if (!(elem instanceof CallState)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @param elem
     *            the ClassifierInState to be deleted
     */
    void deleteClassifierInState(Object elem) {
        if (!(elem instanceof ClassifierInState)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @param elem
     *            ObjectFlowState
     */
    void deleteObjectFlowState(Object elem) {
        if (!(elem instanceof ObjectFlowState)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @param elem
     *            Partition
     */
    void deletePartition(Object elem) {
        if (!(elem instanceof Partition)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @param elem
     *            SubactivityState
     */
    void deleteSubactivityState(Object elem) {
        if (!(elem instanceof SubactivityState)) {
            throw new IllegalArgumentException();
        }
    }

}
