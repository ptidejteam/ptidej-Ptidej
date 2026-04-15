// $Id: CommonBehaviorFactoryMDRImpl.java,v 1.2 2006/03/02 05:07:42 vauchers Exp $
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

import org.argouml.model.CommonBehaviorFactory;
import org.argouml.model.Model;
import org.argouml.model.ModelEventPump;
import org.omg.uml.behavioralelements.activitygraphs.ActionState;
import org.omg.uml.behavioralelements.commonbehavior.Action;
import org.omg.uml.behavioralelements.commonbehavior.ActionSequence;
import org.omg.uml.behavioralelements.commonbehavior.Argument;
import org.omg.uml.behavioralelements.commonbehavior.AttributeLink;
import org.omg.uml.behavioralelements.commonbehavior.CallAction;
import org.omg.uml.behavioralelements.commonbehavior.ComponentInstance;
import org.omg.uml.behavioralelements.commonbehavior.CreateAction;
import org.omg.uml.behavioralelements.commonbehavior.DataValue;
import org.omg.uml.behavioralelements.commonbehavior.DestroyAction;
import org.omg.uml.behavioralelements.commonbehavior.Instance;
import org.omg.uml.behavioralelements.commonbehavior.Link;
import org.omg.uml.behavioralelements.commonbehavior.LinkEnd;
import org.omg.uml.behavioralelements.commonbehavior.LinkObject;
import org.omg.uml.behavioralelements.commonbehavior.NodeInstance;
import org.omg.uml.behavioralelements.commonbehavior.Reception;
import org.omg.uml.behavioralelements.commonbehavior.ReturnAction;
import org.omg.uml.behavioralelements.commonbehavior.SendAction;
import org.omg.uml.behavioralelements.commonbehavior.Signal;
import org.omg.uml.behavioralelements.commonbehavior.Stimulus;
import org.omg.uml.behavioralelements.commonbehavior.TerminateAction;
import org.omg.uml.behavioralelements.commonbehavior.UmlException;
import org.omg.uml.behavioralelements.commonbehavior.UninterpretedAction;
import org.omg.uml.foundation.core.BehavioralFeature;
import org.omg.uml.foundation.core.Classifier;
import org.omg.uml.foundation.core.Operation;

/**
 * Factory to create UML classes for the UML BehaviorialElements::CommonBehavior
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
 * @author Tom Morris
 * Derived from NSUML implementation by: 
 * @author Thierry Lach
 */
public class CommonBehaviorFactoryMDRImpl extends AbstractUmlModelFactoryMDR
	implements CommonBehaviorFactory {

    /**
     * The model implementation.
     */
    private MDRModelImplementation nsmodel;
    
    /** 
     * The model event pump
     */
    private ModelEventPump eventPump;

    /**
     * Don't allow instantiation.
     *
     * @param implementation
     *            To get other helpers and factories.
     */
    CommonBehaviorFactoryMDRImpl(MDRModelImplementation implementation) {
        nsmodel = implementation;
        eventPump = nsmodel.getModelEventPump();
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createActionSequence()
     */
    public Object createActionSequence() {
        ActionSequence myActionSequence = nsmodel.getUmlPackage()
                .getCommonBehavior().getActionSequence().createActionSequence();
        super.initialize(myActionSequence);
        eventPump.flushModelEvents();
        return myActionSequence;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createArgument()
     */
    public Object createArgument() {
        Argument myArgument = nsmodel.getUmlPackage().getCommonBehavior()
                .getArgument().createArgument();
        super.initialize(myArgument);
        eventPump.flushModelEvents();
        return myArgument;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createAttributeLink()
     */
    public Object createAttributeLink() {
        AttributeLink myAttributeLink = nsmodel.getUmlPackage()
                .getCommonBehavior().getAttributeLink().createAttributeLink();
        super.initialize(myAttributeLink);
        eventPump.flushModelEvents();
        return myAttributeLink;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createCallAction()
     */
    public Object createCallAction() {
        CallAction myCallAction = nsmodel.getUmlPackage().getCommonBehavior()
                .getCallAction().createCallAction();
        super.initialize(myCallAction);
        eventPump.flushModelEvents();
        return myCallAction;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createComponentInstance()
     */
    public Object createComponentInstance() {
        ComponentInstance myComponentInstance = nsmodel.getUmlPackage()
                .getCommonBehavior().getComponentInstance()
                .createComponentInstance();
        super.initialize(myComponentInstance);
        eventPump.flushModelEvents();
        return myComponentInstance;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createCreateAction()
     */
    public Object createCreateAction() {
        CreateAction myCreateAction = nsmodel.getUmlPackage()
                .getCommonBehavior().getCreateAction().createCreateAction();
        super.initialize(myCreateAction);
        eventPump.flushModelEvents();
        return myCreateAction;
    }

    /**
     * Create an empty but initialized instance of a UML DataValue.
     *
     * TODO: This method is not part of the interface, but it is
     * invoked directly by tests using reflection.  
     * 
     * @return an initialized UML DataValue instance.
     */
    public DataValue createDataValue() {
        DataValue myDataValue = nsmodel.getUmlPackage().getCommonBehavior()
                .getDataValue().createDataValue();
        super.initialize(myDataValue);
        eventPump.flushModelEvents();
        return myDataValue;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createDestroyAction()
     */
    public Object createDestroyAction() {
        DestroyAction myDestroyAction = nsmodel.getUmlPackage()
                .getCommonBehavior().getDestroyAction().createDestroyAction();
        super.initialize(myDestroyAction);
        eventPump.flushModelEvents();
        return myDestroyAction;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createException()
     */
    public Object createException() {
        UmlException myUmlException = nsmodel.getUmlPackage()
                .getCommonBehavior().getUmlException().createUmlException();
        super.initialize(myUmlException);
        eventPump.flushModelEvents();
        return myUmlException;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createLink()
     */
    public Object createLink() {
        Link myLink = nsmodel.getUmlPackage().getCommonBehavior().getLink()
                .createLink();
        super.initialize(myLink);
        eventPump.flushModelEvents();
        return myLink;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createLinkEnd()
     */
    public Object createLinkEnd() {
        LinkEnd myLinkEnd = nsmodel.getUmlPackage().getCommonBehavior()
                .getLinkEnd().createLinkEnd();
        super.initialize(myLinkEnd);
        eventPump.flushModelEvents();
        return myLinkEnd;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createLinkObject()
     */
    public Object createLinkObject() {
        LinkObject myLinkObject = nsmodel.getUmlPackage().getCommonBehavior()
                .getLinkObject().createLinkObject();
        super.initialize(myLinkObject);
        eventPump.flushModelEvents();
        return myLinkObject;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createNodeInstance()
     */
    public Object createNodeInstance() {
        NodeInstance myNodeInstance = nsmodel.getUmlPackage()
                .getCommonBehavior().getNodeInstance().createNodeInstance();
        super.initialize(myNodeInstance);
        eventPump.flushModelEvents();
        return myNodeInstance;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createObject()
     */
    public Object createObject() {
        org.omg.uml.behavioralelements.commonbehavior.Object myObject = nsmodel
                .getUmlPackage().getCommonBehavior().getObject().createObject();
        super.initialize(myObject);
        eventPump.flushModelEvents();
        return myObject;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createReception()
     */
    public Object createReception() {
        Reception myReception = nsmodel.getUmlPackage().getCommonBehavior()
                .getReception().createReception();
        super.initialize(myReception);
        eventPump.flushModelEvents();
        return myReception;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createReturnAction()
     */
    public Object createReturnAction() {
        ReturnAction myReturnAction = nsmodel.getUmlPackage()
                .getCommonBehavior().getReturnAction().createReturnAction();
        super.initialize(myReturnAction);
        eventPump.flushModelEvents();
        return myReturnAction;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createSendAction()
     */
    public Object createSendAction() {
        SendAction mySendAction = nsmodel.getUmlPackage().getCommonBehavior()
                .getSendAction().createSendAction();
        super.initialize(mySendAction);
        eventPump.flushModelEvents();
        return mySendAction;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createSignal()
     */
    public Object createSignal() {
        Signal mySignal = nsmodel.getUmlPackage().getCommonBehavior()
                .getSignal().createSignal();
        super.initialize(mySignal);
        eventPump.flushModelEvents();
        return mySignal;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createStimulus()
     */
    public Object createStimulus() {
        Stimulus myStimulus = nsmodel.getUmlPackage().getCommonBehavior()
                .getStimulus().createStimulus();
        super.initialize(myStimulus);
        eventPump.flushModelEvents();
        return myStimulus;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createTerminateAction()
     */
    public Object createTerminateAction() {
        TerminateAction myTerminateAction = nsmodel.getUmlPackage()
                .getCommonBehavior().getTerminateAction()
                .createTerminateAction();
        super.initialize(myTerminateAction);
        eventPump.flushModelEvents();
        return myTerminateAction;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#createUninterpretedAction()
     */
    public Object createUninterpretedAction() {
        UninterpretedAction myUninterpretedAction = nsmodel.getUmlPackage()
                .getCommonBehavior().getUninterpretedAction()
                .createUninterpretedAction();
        super.initialize(myUninterpretedAction);
        eventPump.flushModelEvents();
        return myUninterpretedAction;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#buildCallAction(java.lang.Object,
     *      java.lang.String)
     */
    public Object buildCallAction(Object oper, String name) {
        if (!(oper instanceof Operation)) {
            throw new IllegalArgumentException("There should be an operation"
                    + " with a callaction.");
        }
        Object action = createCallAction();
        nsmodel.getCoreHelper().setName(action, name);
        nsmodel.getCommonBehaviorHelper().setOperation(action, oper);
        eventPump.flushModelEvents();
        return action;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#buildUninterpretedAction(java.lang.Object)
     */
    public Object buildUninterpretedAction(Object actionState) {
        Object action = createUninterpretedAction();
        if (actionState instanceof ActionState) {
            nsmodel.getStateMachinesHelper().setEntry(actionState, action);
        }
        return action;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#buildLink(java.lang.Object,
     *      java.lang.Object)
     */
    public Object buildLink(Object fromInstance, Object toInstance) {
        Object link = nsmodel.getCommonBehaviorFactory().createLink();
        Object /* MLinkEnd */le0 = nsmodel.getCommonBehaviorFactory().
            createLinkEnd();
        nsmodel.getCommonBehaviorHelper().setInstance(le0, fromInstance);
        Object /* MLinkEnd */le1 = nsmodel.getCommonBehaviorFactory().
            createLinkEnd();
        nsmodel.getCommonBehaviorHelper().setInstance(le1, toInstance);
        nsmodel.getCoreHelper().addConnection(link, le0);
        nsmodel.getCoreHelper().addConnection(link, le1);
        return link;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#buildAction(java.lang.Object)
     */
    public Object buildAction(Object message) {
        Object action = createCallAction();
        nsmodel.getCoreHelper().setName(action, "action");
        nsmodel.getCollaborationsHelper().setAction(message, action);
        Object interaction = nsmodel.getFacade().getInteraction(message);
        if (interaction != null
            && nsmodel.getFacade().getContext(interaction) != null) {
            nsmodel.getCoreHelper().setNamespace(action,
                nsmodel.getFacade().getContext(interaction));
        } else {
            throw new IllegalStateException("In buildaction: message does not "
                    + "have an interaction or the "
                    + "interaction does not have " + "a context");
        }
        return action;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#buildSignal(java.lang.Object)
     */
    public Object buildSignal(Object feature) {
        if (!(feature instanceof BehavioralFeature)) {
            return null;
        }

        Signal signal = (Signal) createSignal();
        nsmodel.getUmlPackage().getCommonBehavior().getAContextRaisedSignal()
                .add((BehavioralFeature) feature, signal);
        eventPump.flushModelEvents();
        return signal;
    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#buildStimulus(java.lang.Object)
     */
    public Object buildStimulus(Object link) {
        if (link instanceof Link
            && nsmodel.getCoreHelper().getSource(link) != null
            && nsmodel.getCoreHelper().getDestination(link) != null) {

            Object stimulus = createStimulus();
            Object sender = nsmodel.getCoreHelper().getSource(link);
            Object receiver = nsmodel.getCoreHelper().getDestination(link);
            nsmodel.getCommonBehaviorHelper().setReceiver(stimulus, receiver);
            nsmodel.getCollaborationsHelper().setSender(stimulus, sender);
            nsmodel.getCommonBehaviorHelper().setCommunicationLink(stimulus,
                    link);
            return stimulus;
        }
        throw new IllegalArgumentException("Argument is not a link or "
                + "does not have " + "a source or " + "destination instance");

    }

    /**
     * @see org.argouml.model.CommonBehaviorFactory#buildReception(java.lang.Object)
     */
    public Object buildReception(Object aClassifier) {
        Object reception = createReception();
        if (aClassifier instanceof Classifier) {
            nsmodel.getCoreHelper().setOwner(reception, aClassifier);
        }
        return reception;
    }

    /**
     * @param elem
     *            the Action to be deleted
     */
    void deleteAction(Object elem) {
        if (!(elem instanceof Action)) {
            throw new IllegalArgumentException("elem: " + elem);
        }

    }

    /**
     * @param elem
     *            the ActionSequence to be deleted
     */
    void deleteActionSequence(Object elem) {
        if (!(elem instanceof ActionSequence)) {
            throw new IllegalArgumentException("elem: " + elem);
        }

    }

    /**
     * @param elem
     *            the element to be deleted
     */
    void deleteArgument(Object elem) {
        if (!(elem instanceof Argument)) {
            throw new IllegalArgumentException("elem: " + elem);
        }
    }

    /**
     * @param elem
     *            the element to be deleted
     */
    void deleteAttributeLink(Object elem) {
        if (!(elem instanceof AttributeLink)) {
            throw new IllegalArgumentException("elem: " + elem);
        }
    }

    /**
     * @param elem
     *            the element to be deleted
     */
    void deleteCallAction(Object elem) {
        if (!(elem instanceof CallAction)) {
            throw new IllegalArgumentException("elem: " + elem);
        }
    }

    /**
     * @param elem
     *            the element to be deleted
     */
    void deleteComponentInstance(Object elem) {
        if (!(elem instanceof ComponentInstance)) {
            throw new IllegalArgumentException("elem: " + elem);
        }
    }

    /**
     * @param elem
     *            the element to be deleted
     */
    void deleteCreateAction(Object elem) {
        if (!(elem instanceof CreateAction)) {
            throw new IllegalArgumentException("elem: " + elem);
        }
    }

    /**
     * @param elem
     *            the element to be deleted
     */
    void deleteDataValue(Object elem) {
        if (!(elem instanceof DataValue)) {
            throw new IllegalArgumentException("elem: " + elem);
        }
    }

    /**
     * @param elem
     *            the element to be deleted
     */
    void deleteDestroyAction(Object elem) {
        if (!(elem instanceof DestroyAction)) {
            throw new IllegalArgumentException("elem: " + elem);
        }
    }

    /**
     * @param elem
     *            the element to be deleted
     */
    void deleteException(Object elem) {
        if (!(elem instanceof UmlException)) {
            throw new IllegalArgumentException("elem: " + elem);
        }
    }

    /**
     * when an instance is deleted, delete its linkend's. similar to deleting a
     * classifier in the CoreFactory.
     *
     * @param elem
     *            the element to be deleted
     */
    void deleteInstance(Object elem) {
        if (!(elem instanceof Instance)) {
            throw new IllegalArgumentException("elem: " + elem);
        }

        if (elem != null) {
            Collection col = ((Instance) elem).getLinkEnd();
            Iterator it = col.iterator();
            while (it.hasNext()) {
                nsmodel.getUmlFactory().delete(it.next());
            }
        }
    }

    /**
     * @param elem
     *            the element to be deleted
     */
    void deleteLink(Object elem) {
        if (!(elem instanceof Link)) {
            throw new IllegalArgumentException("elem: " + elem);
        }
    }

    /**
     * when a linkend is deleted, delete its Links.
     *
     * @param elem
     *            the element to be deleted
     */
    void deleteLinkEnd(Object elem) {
        if (!(elem instanceof LinkEnd)) {
            throw new IllegalArgumentException("elem: " + elem);
        }

        Link link = ((LinkEnd) elem).getLink();
        if (link != null && link.getConnection() != null
            && link.getConnection().size() == 2) { // binary link
            nsmodel.getUmlFactory().delete(link);
        }
    }

    /**
     * @param elem
     *            the element to be deleted
     */
    void deleteLinkObject(Object elem) {
        if (!(elem instanceof LinkObject)) {
            throw new IllegalArgumentException("elem: " + elem);
        }
    }

    /**
     * @param elem
     *            the element to be deleted
     */
    void deleteNodeInstance(Object elem) {
        if (!(elem instanceof NodeInstance)) {
            throw new IllegalArgumentException("elem: " + elem);
        }
    }

    /**
     * @param elem
     *            the element to be deleted
     */
    void deleteObject(Object elem) {
        if (!(Model.getFacade().isAObject(elem))) {
            throw new IllegalArgumentException("elem: " + elem);
        }

    }

    /**
     * @param elem
     *            the element to be deleted
     */
    void deleteReception(Object elem) {
        if (!(elem instanceof Reception)) {
            throw new IllegalArgumentException("elem: " + elem);
        }
    }

    /**
     * @param elem
     *            the element to be deleted
     */
    void deleteReturnAction(Object elem) {
        if (!(elem instanceof ReturnAction)) {
            throw new IllegalArgumentException("elem: " + elem);
        }
    }

    /**
     * @param elem
     *            the element to be deleted
     */
    void deleteSendAction(Object elem) {
        if (!(elem instanceof SendAction)) {
            throw new IllegalArgumentException("elem: " + elem);
        }
    }

    /**
     * @param elem
     *            the element to be deleted
     */
    void deleteSignal(Object elem) {
        if (!(elem instanceof Signal)) {
            throw new IllegalArgumentException("elem: " + elem);
        }
    }

    /**
     * @param elem
     *            the element to be deleted
     */
    void deleteStimulus(Object elem) {
        if (!(elem instanceof Stimulus)) {
            throw new IllegalArgumentException("elem: " + elem);
        }
    }

    /**
     * @param elem
     *            the element to be deleted
     */
    void deleteTerminateAction(Object elem) {
        if (!(elem instanceof TerminateAction)) {
            throw new IllegalArgumentException("elem: " + elem);
        }
    }

    /**
     * @param elem
     *            the element to be deleted
     */
    void deleteUninterpretedAction(Object elem) {
        if (!(elem instanceof UninterpretedAction)) {
            throw new IllegalArgumentException("elem: " + elem);
        }
    }
    
}
