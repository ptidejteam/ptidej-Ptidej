// $Id: UseCasesHelperMDRImpl.java,v 1.2 2006/03/02 05:07:41 vauchers Exp $
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.argouml.model.Model;
import org.argouml.model.ModelEventPump;
import org.argouml.model.UseCasesHelper;
import org.omg.uml.behavioralelements.usecases.Actor;
import org.omg.uml.behavioralelements.usecases.Extend;
import org.omg.uml.behavioralelements.usecases.ExtensionPoint;
import org.omg.uml.behavioralelements.usecases.Include;
import org.omg.uml.behavioralelements.usecases.UseCase;
import org.omg.uml.foundation.core.ModelElement;
import org.omg.uml.foundation.core.Namespace;
import org.omg.uml.foundation.core.UmlClass;
import org.omg.uml.foundation.datatypes.BooleanExpression;
import org.omg.uml.modelmanagement.Subsystem;

/**
 * UseCase Helper for MDR ModelImplementation.
 * <p>
 * Emulates synchronous event delivery as implemented in NSUML library.
 * Any methods which call org.omg.uml or NetBeans MDR methods that generate
 * events must call flushModelEvents() before returning.  'Get' methods do
 * not needed to be handled specially.
 * <p>
 * 
 * @since ARGO0.19.5
 * @author Ludovic Ma�tre
 * @author Tom Morris

 */
public class UseCasesHelperMDRImpl implements UseCasesHelper {

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
    UseCasesHelperMDRImpl(MDRModelImplementation implementation) {
        nsmodel = implementation;
        eventPump = nsmodel.getModelEventPump();
    }

    /**
     * This method returns all extension points of a given use case.
     * <p>
     * 
     * Here for completeness, but actually just a wrapper for the NSUML
     * function.
     * <p>
     * 
     * @param useCase
     *            The use case for which we want the extension points.
     * 
     * @return A collection of the extension points.
     */
    public Collection getExtensionPoints(Object/* UseCase */useCase) {

        return ((UseCase) useCase).getExtensionPoint();
    }

    /**
     * Returns all usecases in some namespace ns.
     * 
     * @param ns
     *            is the namespace
     * @return Collection
     */
    public Collection getAllUseCases(Object ns) {
        if (!(ns instanceof Namespace)) {
            throw new IllegalArgumentException();
        }

        Iterator it = ((Namespace) ns).getOwnedElement().iterator();
        List list = new ArrayList();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof Namespace) {
                list.addAll(getAllUseCases(o));
            }
            if (o instanceof UseCase) {
                list.add(o);
            }

        }
        return list;
    }

    /**
     * Returns all actors in some namespace ns.
     * 
     * @param ns
     *            is the namespace
     * @return Collection
     */
    public Collection getAllActors(Object ns) {
        if (!(ns instanceof Namespace)) {
            throw new IllegalArgumentException();
        }

        Iterator it = ((Namespace) ns).getOwnedElement().iterator();
        List list = new ArrayList();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof Namespace) {
                list.addAll(getAllActors(o));
            }
            if (o instanceof Actor) {
                list.add(o);
            }

        }
        return list;
    }

    /**
     * Returns all usecases this given usecase extends.
     * <p>
     * 
     * @param ausecase
     *            the given usecase
     * @return Collection all usecases this given usecase extends
     */
    public Collection getExtendedUseCases(Object ausecase) {
        if (ausecase == null) {
            return new ArrayList();
        }
        UseCase usecase = (UseCase) ausecase;
        Iterator it = usecase.getExtend().iterator();
        List list = new ArrayList();
        while (it.hasNext()) {
            Extend extend = (Extend) it.next();
            UseCase base = extend.getBase();
            list.add(base);
        }
        return list;
    }

    /**
     * @param usecase
     *            the given usecase
     * @return Collection all usecases that extend the given usecase
     */
    public Collection getExtendingUseCases(Object usecase) {
        if (usecase == null) {
            return new ArrayList();
        }
        Iterator it = Model.getFacade().getExtends2(usecase).iterator();
        List list = new ArrayList();
        while (it.hasNext()) {
            Extend ext = (Extend) it.next();
            UseCase extension = ext.getExtension();
            list.add(extension);
        }
        return list;
    }

    /**
     * Returns the extend relation between two usecases base and extension. If
     * there is none null is returned.
     * 
     * @param abase
     *            the given base usecase
     * @param anextension
     *            the given extension usecase
     * @return Extend the extend relation
     */
    public Object getExtends(Object/* UseCase */abase,
            Object/* UseCase */anextension) {
        UseCase base = (UseCase) abase;
        UseCase extension = (UseCase) anextension;
        if (base == null || extension == null) {
            return null;
        }
        Iterator it = extension.getExtend().iterator();
        while (it.hasNext()) {
            Extend extend = (Extend) it.next();
            if (extend.getBase() == base) {
                return extend;
            }
        }
        return null;
    }

    /**
     * Returns all usecases this usecase includes.
     * 
     * @param ausecase
     *            the given usecase
     * @return Collection all usecases the given usecase includes
     */
    public Collection getIncludedUseCases(Object/* UseCase */ausecase) {
        if (ausecase == null) {
            return new ArrayList();
        }
        UseCase usecase = (UseCase) ausecase;
        Iterator it = usecase.getInclude().iterator();
        List list = new ArrayList();
        while (it.hasNext()) {
            Include include = (Include) it.next();
            UseCase addition = include.getBase();
            list.add(addition);
        }
        return list;
    }

    /**
     * Returns the include relation between two usecases base and inclusion. If
     * there is none null is returned.
     * 
     * @param abase
     *            the given base usecase
     * @param aninclusion
     *            the given inclusion usecase
     * @return Extend the include relation
     */
    public Object getIncludes(Object abase, Object aninclusion) {
        UseCase base = (UseCase) abase;
        UseCase inclusion = (UseCase) aninclusion;
        if (base == null || inclusion == null) {
            return null;
        }
        Iterator it = inclusion.getInclude().iterator();
        while (it.hasNext()) {
            Include include = (Include) it.next();
            if (include.getBase() == base) {
                return include;
            }
        }
        return null;
    }

    /**
     * Returns the specificationpath operation of some usecase. See section
     * 2.11.3.5 of the UML 1.3 spec for a definition.
     * <p>
     * 
     * @param ausecase
     *            the given usecase
     * @return Collection the specificationpath operation of the given usecase
     */
    public Collection getSpecificationPath(Object ausecase) {
        UseCase uc = (UseCase) ausecase;
        Set set = new HashSet();
        set.addAll(nsmodel.getModelManagementHelper().
                getAllSurroundingNamespaces(uc));
        Set set2 = new HashSet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof Subsystem || o instanceof UmlClass) {
                set2.add(o);
            }
        }
        return set2;
    }

    /**
     * Sets the base usecase of a given extend. Updates the extensionpoints of
     * the extend too.
     * 
     * @param extend
     *            the given extend
     * @param base
     *            the base usecase
     */
    public void setBase(Object extend, Object base) {
        if (base == null) {
            throw new IllegalArgumentException(
                    "The base cannot be null");
        }
        
        if (!(base instanceof UseCase)) {
            throw new IllegalArgumentException(
                    "The base cannot be a " + base.getClass().getName());
        }

        if (extend == null) {
            throw new IllegalArgumentException("extend");
        }

        if (extend instanceof Extend) {
            Extend theExtend = ((Extend) extend);
            if (base == theExtend.getBase()) {
                return;
            }
            Iterator it = theExtend.getExtensionPoint().iterator();
            while (it.hasNext()) {
                ExtensionPoint point = (ExtensionPoint) it.next();
                removeExtend(point, theExtend);
            }
            ExtensionPoint point = (ExtensionPoint) nsmodel.
                    getUseCasesFactory().buildExtensionPoint(base);
            theExtend.setBase((UseCase) base);
            addExtensionPoint(theExtend, point);
            eventPump.flushModelEvents();
        } else if (extend instanceof Include) {
            Include theInclude = ((Include) extend);
            if (base == theInclude.getBase()) {
                return;
            }
            theInclude.setAddition((UseCase) base);
            eventPump.flushModelEvents();
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Remove an extend to a Use Case or Extension Point.
     * 
     * @param elem
     *            The Use Case or Extension Point.
     * @param extend
     *            The Extend to add.
     */
    public void removeExtend(Object elem, Object extend) {
        if (elem instanceof UseCase && extend instanceof Extend) {
            ((UseCase) elem).getExtend().remove(extend);
            eventPump.flushModelEvents();
            return;
        }
        if (elem instanceof ExtensionPoint && extend instanceof Extend) {
            ((Extend) extend).getExtensionPoint().remove(elem);
            eventPump.flushModelEvents();
            return;
        }

        throw new IllegalArgumentException("elem: " + elem + " or extend: "
                + extend);
    }

    /**
     * This method removes an Extension Point from a Use Case or an Extend.
     * 
     * @param elem
     *            is The Use Case or Extend.
     * @param ep
     *            is the extension point
     */
    public void removeExtensionPoint(Object elem, Object ep) {
        if (ep instanceof ExtensionPoint) {
            if (elem instanceof UseCase) {
                ((UseCase) elem).getExtensionPoint().remove(ep);
                eventPump.flushModelEvents();
                return;
            }
            if (elem instanceof Extend) {
                ((Extend) elem).getExtensionPoint().remove(ep);
                eventPump.flushModelEvents();
                return;
            }
        }
        throw new IllegalArgumentException("elem: " + elem + " or ep: " + ep);
    }

    /**
     * Remove an include from a Use Case.
     * 
     * @param usecase
     *            The Use Case.
     * @param include
     *            The Include.
     */
    public void removeInclude(Object usecase, Object include) {
        if (usecase instanceof UseCase && include instanceof Include) {
            ((UseCase) usecase).getInclude().remove(include);
            eventPump.flushModelEvents();
            return;
        }

        throw new IllegalArgumentException("usecase: " + usecase
                + " or include: " + include);
    }

    /**
     * Add an extend to a Use Case or Extension Point.
     * 
     * @param elem
     *            The Use Case or Extension Point.
     * @param extend
     *            The Extend to add.
     */
    public void addExtend(Object elem, Object extend) {
        if (elem instanceof UseCase && extend instanceof Extend) {
            ((UseCase) elem).getExtend().add(extend);
            eventPump.flushModelEvents();
            return;
        }
        if (elem instanceof ExtensionPoint && extend instanceof Extend) {
            ((Extend) extend).getExtensionPoint().add(elem);
            eventPump.flushModelEvents();
            return;
        }

        throw new IllegalArgumentException("elem: " + elem + " or extend: "
                + extend);
    }

    /**
     * Adds an extension point to some model element.
     * 
     * @param handle
     *            is the model element
     * @param extensionPoint
     *            is the extension point
     */
    public void addExtensionPoint(Object handle, Object extensionPoint) {
        if (extensionPoint instanceof ExtensionPoint) {
            if (handle instanceof UseCase) {
                ((UseCase) handle).getExtensionPoint().add(extensionPoint);
                eventPump.flushModelEvents();
                return;
            }
            if (handle instanceof Extend) {
                ((Extend) handle).getExtensionPoint().add(extensionPoint);
                eventPump.flushModelEvents();
                return;
            }
        }
        throw new IllegalArgumentException("handle: " + handle
                + " or extensionPoint: " + extensionPoint);
    }

    /**
     * Add an include to a Use Case.
     * 
     * @param usecase
     *            The Use Case.
     * @param include
     *            The Include.
     */
    public void addInclude(Object usecase, Object include) {
        if (usecase instanceof UseCase && include instanceof Include) {
            ((UseCase) usecase).getInclude().add(include);
            eventPump.flushModelEvents();
            return;
        }

        throw new IllegalArgumentException("usecase: " + usecase
                + " or include: " + include);
    }

    /**
     * Sets the addition to an include. There is a bug in NSUML that reverses
     * additions and bases for includes.
     * 
     * @param handle
     *            Include
     * @param useCase
     *            UseCase
     */
    public void setAddition(Object handle, Object useCase) {
        if ((handle instanceof ModelElement)
                && Model.getUmlFactory().isRemoved(handle)) {
            throw new IllegalArgumentException("Operation on a removed object ["
                    + handle + "]");
        }
        
        if (!(useCase instanceof UseCase)) {
            throw new IllegalArgumentException("A UseCase was expected ["
                    + useCase + "]");
        }
        
        if (Model.getUmlFactory().isRemoved(useCase)) {
            throw new IllegalArgumentException("Operation on a removed object ["
                    + useCase + "]");
        }

        if (handle instanceof Include) {
            // See issue 2034
            ((Include) handle).setBase((UseCase) useCase);
            eventPump.flushModelEvents();
            return;
        }
        throw new IllegalArgumentException("handle: " + handle);
    }

    /**
     * Set the condition of an extend.
     * 
     * @param handle
     *            is the extend
     * @param booleanExpression
     *            is the condition
     */
    public void setCondition(Object handle, Object booleanExpression) {
        if (handle instanceof Extend
                && booleanExpression instanceof BooleanExpression) {
            ((Extend) handle).
                    setCondition((BooleanExpression) booleanExpression);
            eventPump.flushModelEvents();
            return;
        }
        throw new IllegalArgumentException("handle: " + handle
                + " or booleanExpression: " + booleanExpression);
    }

    /**
     * Set the extension of a usecase.
     * 
     * @param handle
     *            Extend
     * @param useCase
     *            UseCase
     */
    public void setExtension(Object handle, Object useCase) {
        if (!(useCase instanceof UseCase)) {
            throw new IllegalArgumentException("A use case must be supplied");
        }
        
        if ((handle instanceof ModelElement)
                && Model.getUmlFactory().isRemoved(handle)) {
            throw new IllegalStateException("Operation on a removed object ["
                    + handle + "]");
        }
        if (Model.getUmlFactory().isRemoved(useCase)) {
            throw new IllegalStateException("Operation on a removed object ["
                    + useCase + "]");
        }

        if (handle instanceof Extend 
                && (useCase instanceof UseCase)) {
            ((Extend) handle).setExtension((UseCase) useCase);
            eventPump.flushModelEvents();
            return;
        }
        throw new IllegalArgumentException(
                "handle: " + handle + " or ext: "
                + useCase);
    }

    /**
     * Sets the extension points of some use cases or extend relationships
     * 
     * @param handle
     *            the use case or extend
     * @param extensionPoints
     *            is the extension points
     */
    public void setExtensionPoints(Object handle, Collection extensionPoints) {
        if (handle instanceof UseCase || handle instanceof Extend) {
            Collection eps = Model.getFacade().getExtensionPoints(handle);
            if (!eps.isEmpty()) {
                Vector extPts = new Vector();
                extPts.addAll(eps);
                Iterator toRemove = extPts.iterator();
                while (toRemove.hasNext())
                    removeExtensionPoint(handle, toRemove.next());
            }
            if (!extensionPoints.isEmpty()) {
                Iterator toAdd = extensionPoints.iterator();
                while (toAdd.hasNext())
                    addExtensionPoint(handle, toAdd.next());
            }
            eventPump.flushModelEvents();
            return;
        }
        throw new IllegalArgumentException("handle: " + handle
                + " or extensionPoints: " + extensionPoints);
    }

    /**
     * Set the collection of Include relationships for a usecase.
     * 
     * @param handle
     *            UseCase
     * @param includes
     *            the collection of Include relationships
     */
    public void setIncludes(Object handle, Collection includes) {
        if (handle instanceof UseCase) {
            Collection inc = Model.getFacade().getIncludes(handle);
            if (!inc.isEmpty()) {
                Vector in = new Vector();
                in.addAll(inc);
                Iterator toRemove = in.iterator();
                while (toRemove.hasNext())
                    removeInclude(handle, toRemove.next());
            }
            if (!includes.isEmpty()) {
                Iterator toAdd = includes.iterator();
                while (toAdd.hasNext())
                    addInclude(handle, toAdd.next());
            }
            eventPump.flushModelEvents();
            return;
        }
        throw new IllegalArgumentException("handle: " + handle
                + " or includes: " + includes);
    }

    /**
     * Sets a location of some extension point.
     * 
     * @param handle
     *            is the extension point
     * @param loc
     *            is the location
     */
    public void setLocation(Object handle, String loc) {
        if (handle instanceof ExtensionPoint) {
            ((ExtensionPoint) handle).setLocation(loc);
            eventPump.flushModelEvents();
            return;
        }
        throw new IllegalArgumentException("handle: " + handle);
    }

    /**
     * Set a Use Case for an Extension Point.
     * 
     * @param elem
     *            The Extension Point.
     * @param usecase
     *            The Use Case.
     */
    public void setUseCase(Object elem, Object usecase) {
        if (elem instanceof ExtensionPoint
                && (usecase instanceof UseCase || usecase == null)) {
            ((ExtensionPoint) elem).setUseCase((UseCase) usecase);
            eventPump.flushModelEvents();
            return;
        }

        throw new IllegalArgumentException("elem: " + elem + " or usecase: "
                + usecase);
    }
}
