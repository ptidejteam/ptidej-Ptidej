// $Id: UseCasesFactoryMDRImpl.java,v 1.2 2006/03/02 05:07:41 vauchers Exp $
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

import org.argouml.model.ModelEventPump;
import org.argouml.model.UseCasesFactory;
import org.omg.uml.behavioralelements.usecases.Actor;
import org.omg.uml.behavioralelements.usecases.Extend;
import org.omg.uml.behavioralelements.usecases.ExtensionPoint;
import org.omg.uml.behavioralelements.usecases.Include;
import org.omg.uml.behavioralelements.usecases.UseCase;
import org.omg.uml.behavioralelements.usecases.UseCaseInstance;
import org.omg.uml.foundation.core.Namespace;

/**
 * Factory to create UML classes for the UML BehaviorialElements::UseCases
 * package.
 * 
 * TODO: Change visibility to package after reflection problem solved.
 * <p>
 * Emulates synchronous event delivery as implemented in NSUML library.
 * Any methods which call org.omg.uml or NetBeans MDR methods that generate
 * events must call flushModelEvents() before returning.  'Get' methods do
 * not needed to be handled specially.
 * <p>
 * @since ARGO0.19.3
 * @author Thierry Lach & Bob Tarling
 * @author Ludovic Maître
 * @author Tom Morris
 */
public class UseCasesFactoryMDRImpl extends AbstractUmlModelFactoryMDR
        implements UseCasesFactory {

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
    UseCasesFactoryMDRImpl(MDRModelImplementation implementation) {
        nsmodel = implementation;
        eventPump = nsmodel.getModelEventPump();
    }

    /**
     * Create an empty but initialized instance of a Extend.
     * 
     * @return an initialized Extend instance.
     */
    public Object createExtend() {
        Extend myExtend = nsmodel.getUmlPackage().getUseCases().getExtend().
            createExtend();
        super.initialize(myExtend);
        eventPump.flushModelEvents();
        return myExtend;
    }

    /**
     * Create an empty but initialized instance of a ExtensionPoint.
     * 
     * @return an initialized ExtensionPoint instance.
     */
    public Object createExtensionPoint() {
        ExtensionPoint myExtensionPoint = nsmodel.getUmlPackage().
            getUseCases().getExtensionPoint().createExtensionPoint();
        super.initialize(myExtensionPoint);
        eventPump.flushModelEvents();
        return myExtensionPoint;
    }

    /**
     * Create an empty but initialized instance of a Actor.
     * 
     * @return an initialized Actor instance.
     */
    public Object createActor() {
        Actor myActor = nsmodel.getUmlPackage().getUseCases().getActor().
            createActor();
        super.initialize(myActor);
        eventPump.flushModelEvents();
        return myActor;
    }

    /**
     * Create an empty but initialized instance of a Include.
     * 
     * @return an initialized Include instance.
     */
    public Object createInclude() {
        Include myInclude = nsmodel.getUmlPackage().getUseCases().getInclude().
            createInclude();
        super.initialize(myInclude);
        eventPump.flushModelEvents();
        return myInclude;
    }

    /**
     * Create an empty but initialized instance of a UseCase.
     * 
     * @return an initialized UseCase instance.
     */
    public Object createUseCase() {
        UseCase myUseCase = nsmodel.getUmlPackage().getUseCases().getUseCase().
            createUseCase();
        super.initialize(myUseCase);
        eventPump.flushModelEvents();
        return myUseCase;

    }

    /**
     * Create an empty but initialized instance of a UseCaseInstance.
     * 
     * @return an initialized UseCaseInstance instance.
     */
    public Object createUseCaseInstance() {
        UseCaseInstance myUseCaseInstance = nsmodel.getUmlPackage().
            getUseCases().getUseCaseInstance().createUseCaseInstance();
        super.initialize(myUseCaseInstance);
        eventPump.flushModelEvents();
        return myUseCaseInstance;
    }

    /**
     * Build an extend relationship.
     * <p>
     * 
     * Set the namespace to the base (preferred) or else extension's namespace.
     * We don't do any checking on base and extension. They should be different,
     * but that is someone else's problem.
     * <p>
     * 
     * @param abase
     *            The base use case for the relationship
     * 
     * @param anextension
     *            The extension use case for the relationship
     * 
     * @return The new extend relationship or <code>null</code> if it can't be
     *         created.
     */
    public Object buildExtend(Object abase, Object anextension) {
        UseCase base = (UseCase) abase;
        UseCase extension = (UseCase) anextension;

        Extend extend = (Extend) createExtend();
        // Set the ends

        extend.setBase(base);
        extend.setExtension(extension);

        // Set the namespace to that of the base as first choice, or that of
        // the extension as second choice.

        if (base.getNamespace() != null) {
            extend.setNamespace(base.getNamespace());
        } else if (extension.getNamespace() != null) {
            extend.setNamespace(extension.getNamespace());
        }

        // build an extensionpoint in the base
        ExtensionPoint point = (ExtensionPoint) buildExtensionPoint(base);
        extend.getExtensionPoint().add(point);

        eventPump.flushModelEvents();
        return extend;
    }

    /**
     * Build an extend relationship.
     * <p>
     * 
     * @param abase
     *            The base use case for the relationship
     * @param anextension
     *            The extension use case for the relationship
     * @param apoint
     *            The insertion point for the extension
     * @return The new extend relationship or <code>null</code> if it can't be
     *         created.
     */
    public Object buildExtend(Object abase, Object anextension, Object apoint) {
        UseCase base = (UseCase) abase;
        UseCase extension = (UseCase) anextension;
        ExtensionPoint point = (ExtensionPoint) apoint;
        if (base == null || extension == null) {
            throw new IllegalArgumentException("Either the base usecase or "
                    + "the extension usecase is " + "null");
        }
        if (point != null) {
            if (!base.getExtensionPoint().contains(point)) {
                throw new IllegalArgumentException("The extensionpoint is no "
                        + "part of the base " + "usecase");
            }
        } else {
            point = (ExtensionPoint) buildExtensionPoint(base);
        }
        Extend extend = (Extend) createExtend();
        extend.setBase(base);
        extend.setExtension(extension);
        extend.getExtensionPoint().add(point);
        eventPump.flushModelEvents();
        return extend;
    }

    /**
     * Builds an extension point for a use case.
     * 
     * @param modelElement
     *            The owning use case for the extension point.
     * @return The new extension point.
     * @throws IllegalArgumentException
     *             if modelElement isn't a use-case.
     */
    public Object buildExtensionPoint(Object modelElement) {
        if (!(modelElement instanceof UseCase)) {
            throw new IllegalArgumentException("An extension point can only "
                    + "be built on a use case");
        }

        UseCase useCase = (UseCase) modelElement;
        ExtensionPoint extensionPoint = (ExtensionPoint) 
            createExtensionPoint();

        // Set the owning use case if there is one given.

        extensionPoint.setUseCase(useCase);

        // Set the namespace to that of the useCase if possible.

        // the usecase itself is a namespace...
        extensionPoint.setNamespace(useCase);
        /*
         * if (useCase.getNamespace() != null) {
         * extensionPoint.setNamespace(useCase.getNamespace()); }
         */

        // For consistency with attribute and operation, give it a default
        // name and location
        extensionPoint.setName("newEP");
        extensionPoint.setLocation("loc");

        eventPump.flushModelEvents();
        return extensionPoint;
    }

    /**
     * Build an include relationship.
     * <p>
     * 
     * Set the namespace to the base (preferred) or else extension's namespace.
     * We don't do any checking on base and extension. They should be different,
     * but that is someone else's problem.
     * <p>
     * 
     * <em>Note</em>. There is a bug in NSUML that gets the base and addition
     * associations back to front. We reverse the use of their accessors in the
     * code to correct this.
     * <p>
     * 
     * @param abase
     *            The base use case for the relationship
     * 
     * @param anaddition
     *            The extension use case for the relationship
     * 
     * @return The new include relationship or <code>null</code> if it can't
     *         be created.
     */
    public Object buildInclude(Object/* MUseCase */abase,
            Object/* MUseCase */anaddition) {
        UseCase base = (UseCase) abase;
        UseCase addition = (UseCase) anaddition;
        Include include = (Include) createInclude();

        include.setAddition(addition);
        include.setBase(base);

        // Set the namespace to that of the base as first choice, or that of
        // the addition as second choice.

        if (base.getNamespace() != null) {
            include.setNamespace(base.getNamespace());
        } else if (addition.getNamespace() != null) {
            include.setNamespace(addition.getNamespace());
        }
        
        eventPump.flushModelEvents();
        return include;
    }

    /**
     * Builds an actor in the given namespace.
     * 
     * @param ns
     *            the given namespace
     * @param model
     *            model to use for namespace if namespace is null
     * @return The newly build Actor.
     */
    private Actor buildActor(Namespace ns, Object model) {
        if (ns == null) {
            ns = (Namespace) model;
        }
        Actor actor = (Actor) createActor();
        actor.setNamespace(ns);
        actor.setLeaf(false);
        actor.setRoot(false);
        eventPump.flushModelEvents();
        return actor;
    }

    /**
     * Builds an actor in the same namespace of the given actor. If object is no
     * actor nothing is built. Did not give MActor as an argument but object to
     * seperate argouml better from NSUML.
     * <p>
     * 
     * @param model
     *            The current model.
     * @param actor
     *            the given Actor
     * @return The newly build Actor
     * 
     * @see org.argouml.model.UseCasesFactory#buildActor(java.lang.Object,
     *      java.lang.Object)
     */
    public Object buildActor(Object actor, Object model) {
        if (actor instanceof Actor) {
            return buildActor(((Actor) actor).getNamespace(), model);
        }
        return null;
    }

    /**
     * @param elem
     *            the UML element to be deleted
     */
    void deleteActor(Object elem) {
        if (!(elem instanceof Actor)) {
            throw new IllegalArgumentException();
        }

    }

    /**
     * @param elem
     *            the UML element to be deleted
     */
    void deleteExtend(Object elem) {
        if (!(elem instanceof Extend)) {
            throw new IllegalArgumentException();
        }

        nsmodel.getUmlHelper().deleteCollection(
                ((Extend) elem).getExtensionPoint());
        eventPump.flushModelEvents();
    }

    /**
     * @param elem
     *            the UML element to be deleted
     */
    void deleteExtensionPoint(Object elem) {
        if (!(elem instanceof ExtensionPoint)) {
            throw new IllegalArgumentException();
        }

    }

    /**
     * @param elem
     *            the UML element to be deleted
     */
    void deleteInclude(Object elem) {
        if (!(elem instanceof Include)) {
            throw new IllegalArgumentException();
        }

    }

    /**
     * @param elem
     *            the UML element to be deleted
     */
    void deleteUseCase(Object elem) {
        if (!(elem instanceof UseCase)) {
            throw new IllegalArgumentException();
        }

        UseCase useCase = ((UseCase) elem);
        nsmodel.getUmlHelper().deleteCollection(useCase.getExtend());
        nsmodel.getUmlHelper().deleteCollection(useCase.getInclude());
        eventPump.flushModelEvents();
    }

    /**
     * @param elem
     *            the UML element to be deleted
     */
    void deleteUseCaseInstance(Object elem) {
        if (!(elem instanceof UseCaseInstance)) {
            throw new IllegalArgumentException();
        }
    }
}
