// $Id: ExtensionMechanismsHelperMDRImpl.java,v 1.2 2006/03/02 05:07:41 vauchers Exp $
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
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.argouml.model.ExtensionMechanismsHelper;
import org.argouml.model.ModelEventPump;
import org.omg.uml.foundation.core.ModelElement;
import org.omg.uml.foundation.core.Namespace;
import org.omg.uml.foundation.core.Stereotype;
import org.omg.uml.foundation.core.TagDefinition;
import org.omg.uml.foundation.core.TaggedValue;
import org.omg.uml.modelmanagement.Model;
import org.omg.uml.modelmanagement.UmlPackage;

/**
 * Helper class for UML Foundation::ExtensionMechanisms Package.
 * 
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
 * derived from NSUML implementation by:
 * @author Thierry Lach
 */
class ExtensionMechanismsHelperMDRImpl implements ExtensionMechanismsHelper {

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
    ExtensionMechanismsHelperMDRImpl(MDRModelImplementation implementation) {
        nsmodel = implementation;
        eventPump = nsmodel.getModelEventPump();
    }

    /**
     * Returns all stereotypes in a namespace, and also those in the UmlPackages
     * contained in the subnamespace.
     * 
     * @param ns
     *            is the namespace.
     * @return a Collection with the stereotypes.
     */
    public Collection getStereotypes(Object ns) {
        if (!(ns instanceof Namespace)) {
            throw new IllegalArgumentException();
        }

        List l = new ArrayList();
        if (ns == null) {
            return l;
        }
        // TODO: this could be a huge collection - find a more efficient way
        Iterator it = ((Namespace) ns).getOwnedElement().iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof Stereotype) {
                l.add(o);
            } else if (o instanceof UmlPackage) {
                l.addAll(getStereotypes(o));
            }
        }
        return l;
    }

    /**
     * Finds a stereotype in some namespace, but not in its subnamespaces.
     * Returns null if no such stereotype is found.
     * 
     * @return the stereotype found or null.
     * @param ns
     *            is the namespace.
     * @param stereo
     *            is the stereotype.
     */
    public Object getStereotype(Object ns, Object stereo) {
        if (ns == null || !(ns instanceof Namespace)) {
            throw new IllegalArgumentException("namespace");
        }

        if (ns == null || !(stereo instanceof Stereotype)) {
            throw new IllegalArgumentException("stereotype");
        }

        String name = ((ModelElement) stereo).getName();
        Collection baseClasses = ((Stereotype) stereo).getBaseClass();
        if (name == null || baseClasses.size() != 1) {
            return null;
        }
        String baseClass = (String) baseClasses.iterator().next();
        
        Iterator it = getStereotypes(ns).iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof Stereotype
                    && name.equals(((Stereotype) o).getName())
                    && ((Stereotype) o).getBaseClass().contains(baseClass)) {
                return (Stereotype) o;
            }
        }
        return null;
    }

    /**
     * Searches for a stereotype just like the given stereotype in the given
     * collection of models. The given stereotype can not have its namespace set
     * yet; otherwise it will be returned itself!
     * 
     * The stereotype to be matched must have a single BaseClass.
     * 
     * @param models
     *            a collection of models
     * @param stereo
     *            is the given stereotype
     * @return Stereotype
     */
    public Object getStereotype(Collection models, Object stereo) {
        if (stereo == null) {
            return null;
        }
        if (!(stereo instanceof Stereotype)) {
            throw new IllegalArgumentException("stereotype");
        }

        String name = ((Stereotype) stereo).getName();
        Collection baseClasses = ((Stereotype) stereo).getBaseClass();
        if (name == null || baseClasses.size() != 1) {
            return null;
        }
        String baseClass = (String) baseClasses.iterator().next();
        
        Iterator it2 = models.iterator();
        while (it2.hasNext()) {
            // TODO: this should call the single namespace form
            // getStereotype(it2.next(); stereo);
            Model model = (Model) it2.next();
            Iterator it = getStereotypes(model).iterator();
            while (it.hasNext()) {
                Object o = it.next();
                if (o instanceof Stereotype
                        && name.equals(((Stereotype) o).getName())
                        && ((Stereotype) o).getBaseClass().
                            contains(baseClass)) {
                    return (Stereotype) o;
                }
            }
        }
        return null;
    }

    /**
     * @param m
     *            the ModelElement
     * @return the meta name of the ModelElement
     */
    public String getMetaModelName(Object m) {
        if (m == null) {
            return null;
        }
        if (!(m instanceof ModelElement)) {
            throw new IllegalArgumentException();
        }

        return getMetaModelName(m.getClass());
    }

    /**
     * @param clazz
     *            the UML class
     * @return the meta name of the UML class
     */
    protected String getMetaModelName(Class clazz) {
        return nsmodel.getMetaTypes().getName(clazz);
    }

    /**
     * Returns all possible stereotypes for some modelelement. Possible
     * stereotypes are those stereotypes that are owned by the same namespace
     * the modelelement is owned by and that have a baseclass that is the same
     * as the metamodelelement name of the modelelement.
     * 
     * @param modelElement
     *            is the model element
     * @param models
     *            the models to search in
     * @return Collection
     */
    public Collection getAllPossibleStereotypes(Collection models,
            Object modelElement) {
        ModelElement m = (ModelElement) modelElement;
        List ret = new ArrayList();
        if (m == null) {
            return ret;
        }
        Iterator it = getStereotypes(models).iterator();
        while (it.hasNext()) {
            Stereotype stereo = (Stereotype) it.next();
            if (isValidStereoType(m.getClass(), stereo)) {
                ret.add(stereo);
            }
        }
        return ret;
    }

    /**
     * This function answers the question: Can we apply the given stereotype to
     * the given class?
     * 
     * @param clazz
     *            the class we want to apply the stereotype to
     * @param stereo
     *            the given stereotype
     * @return true if the stereotype may be applied
     */
    private boolean isValidStereoType(Class clazz, Object stereo) {
        if (clazz == null || stereo == null 
                || !(stereo instanceof Stereotype)) {
            return false;
        }
        if (((Stereotype) stereo).getBaseClass().contains(
                getMetaModelName(clazz))) {
            return true;
        }
        if (getMetaModelName(clazz).equals("ModelElement")) {
            return false;
        }
        //Any stereotype is applicable to a tagdefinition
        if (getMetaModelName(clazz).equals("TagDefinition"))
            return true;
        Class[] interfaces = clazz.getInterfaces();
        //TODO: I suppose that this also deal with multiple inheritance
        for (int i = 0; i < interfaces.length; i++) {
            if (isValidStereoType(interfaces[i], stereo))
                return true;
        }
        //Old check on getSuperClass removed since this return the MDR classes
        return false;
    }

    /**
     * Returns true if the given stereotype has a baseclass that equals the
     * baseclass of the given modelelement or one of the superclasses of the
     * given modelelement.
     * 
     * @param theModelElement
     *            is the model element
     * @param theStereotype
     *            is the stereotype
     * @return boolean
     */
    public boolean isValidStereoType(Object theModelElement,
            Object theStereotype) {
        if (theModelElement == null) {
            return false;
        }
        return isValidStereoType(theModelElement.getClass(), theStereotype);
    }

    /**
     * Get all stereotypes from all Models in the list.
     * 
     * Finds all stereotypes owned by the Model objects and the UmlPackage owned
     * by them.
     * 
     * @return the collection of stereotypes in all models in the current
     *         project
     * @param models
     *            the models to search
     * @throws IllegalArgumentException
     *             if an member in the models is not a Model.
     */
    public Collection getStereotypes(Collection models) {
        List ret = new ArrayList();
        Iterator it = models.iterator();
        while (it.hasNext()) {
            Object model = it.next();
            if (!(model instanceof Model)) {
                throw new IllegalArgumentException(
                        "Expected to receive a collection of Models. "
                                + "The collection contained a "
                                + model.getClass().getName());
            }
            ret.addAll(getStereotypes(model));
        }
        return ret;
    }

    /**
     * Sets the stereotype of some modelelement. The method also copies a
     * stereotype that is not a part of the current model to the current model.
     * <p>
     * 
     * @param modelElement
     *            is the model element
     * @param stereotype
     *            is the stereotype
     */
    public void setStereoType(Object modelElement, Object stereotype) {
        if (stereotype != null) {
            stereotype = nsmodel.getModelManagementHelper().
                    getCorrespondingElement(stereotype,
                            nsmodel.getFacade().getModel(modelElement), true);
        }
        nsmodel.getCoreHelper().setStereotype(modelElement, stereotype);
    }

    /**
     * Tests if a stereotype is a stereotype with some name and base class.
     * 
     * @param object
     *            is the stereotype.
     * @param name
     *            is the name of the stereotype.
     * @param base
     *            is the base class of the stereotype.
     * @return true if object is a stereotype with the desired characteristics.
     */
    public boolean isStereotype(Object object, String name, String base) {
        if (object == null || !(object instanceof Stereotype)) {
            return false;
        }

        Stereotype st = (Stereotype) object;
        if (name == null && st.getName() != null) {
            return false;
        }
        if (base == null && !(st.getBaseClass().isEmpty()) ) {
            return false;
        }

        return name.equals(st.getName()) && st.getBaseClass().contains(base);
    }

    /**
     * Tests if a stereotype is or inherits from a stereotype with some name and
     * base class.
     * 
     * @param object
     *            is the stereotype.
     * @param name
     *            is the name of the stereotype.
     * @param base
     *            is the base class of the stereotype.
     * @return true if object is a (descendant of a) stereotype with the desired
     *         characteristics.
     */
    public boolean isStereotypeInh(Object object, String name, String base) {
        if (object == null || !(object instanceof Stereotype)) {
            return false;
        }
        if (isStereotype(object, name, base)) {
            return true;
        }
        Iterator it = nsmodel.getCoreHelper().getSupertypes(object).iterator();
        while (it.hasNext()) {
            if (isStereotypeInh(it.next(), name, base)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add an extended element to a stereotype.
     * 
     * @param handle
     *            Stereotype
     * @param extendedElement
     *            ExtensionPoint
     */
    public void addExtendedElement(Object handle, Object extendedElement) {
        if (handle instanceof Stereotype
                && extendedElement instanceof ModelElement) {
            ((ModelElement) extendedElement).getStereotype().add(handle);
            eventPump.flushModelEvents();
            return;
        }
        throw new IllegalArgumentException("handle: " + handle
                + " or extendedElement: " + extendedElement);
    }

    /**
     * Set the baseclass of some stereotype.
     * 
     * @param handle
     *            the stereotype
     * @param baseClass
     *            the baseclass
     */
    public void setBaseClass(Object handle, Object baseClass) {
        if (handle instanceof Stereotype && baseClass instanceof String) {
            /*
             * TODO: UML 1.4 allows multiple baseclassses, but we restrict our
             * implementation to a single baseclass for compatibility with other
             * tools.
             */
            ((Stereotype) handle).getBaseClass().clear();
            ((Stereotype) handle).getBaseClass().add(baseClass);
            eventPump.flushModelEvents();
            return;
        }
        throw new IllegalArgumentException("handle: " + handle
                + " or baseClass: " + baseClass);
    }

    /**
     * Set the icon for a stereotype.
     * 
     * @param handle
     *            Stereotype
     * @param icon
     *            String
     */
    public void setIcon(Object handle, Object icon) {
        if (handle instanceof Stereotype
                && (icon == null || icon instanceof String)) {
            ((Stereotype) handle).setIcon((String) icon);
            eventPump.flushModelEvents();
            return;
        }
        throw new IllegalArgumentException("handle: " + handle + " or icon: "
                + icon);
    }

    /**
     * Set the Tag of a TaggedValue.
     * 
     * @param handle
     *            TaggedValue
     * @param tag
     *            String
     */
    public void setTag(Object handle, Object tag) {
        if (handle instanceof TaggedValue) {
            TaggedValue tv = (TaggedValue) handle;
            if (tag instanceof TagDefinition) {
                tag = nsmodel.getModelManagementHelper()
                        .getCorrespondingElement(tag,
                                nsmodel.getFacade().getModel(handle), true);                
                tv.setType((TagDefinition) tag);
            } else {
                //preserve old behavior
                TagDefinition td = tv.getType();
                if (tag == null)
                    tag = "";
                if (td == null) {
                    td = (TagDefinition) ((ExtensionMechanismsFactoryMDRImpl) nsmodel
                            .getExtensionMechanismsFactory())
                            .getTagDefinition(tag.toString());
                    tv.setType(td);
                } else
                    td.setName(tag.toString());
            }
            eventPump.flushModelEvents();
        }
    }

    /**
     * Sets a value of some taggedValue.
     * 
     * @param handle
     *            is the tagged value
     * @param value
     *            is the value
     */
    public void setValueOfTag(Object handle, String value) {
        if (handle instanceof TaggedValue) {
            TaggedValue tv = (TaggedValue) handle;
            // TODO: It *seems* that the other CASE tools manage only one
            // dataValue.
            tv.getDataValue().clear();
            tv.getDataValue().add(value);
            eventPump.flushModelEvents();
        }
    }

    /**
     * @see org.argouml.model.ExtensionMechanismsHelper#addTaggedValue(java.lang.Object,
     *      java.lang.Object)
     */
    public void addTaggedValue(Object handle, Object taggedValue) {
        if (handle instanceof ModelElement
                && taggedValue instanceof TaggedValue) {
            ((ModelElement) handle).getTaggedValue().add(taggedValue);
            eventPump.flushModelEvents();
            return;
        }
        throw new IllegalArgumentException("handle: " + handle
                + " or taggedValue: " + taggedValue);
    }

    /**
     * @see org.argouml.model.ExtensionMechanismsHelper#removeTaggedValue(java.lang.Object,
     *      java.lang.Object)
     */
    public void removeTaggedValue(Object handle, Object taggedValue) {
        if (handle instanceof ModelElement
                && taggedValue instanceof TaggedValue) {
            ((ModelElement) handle).getTaggedValue().remove(taggedValue);
            eventPump.flushModelEvents();
            return;
        }
        throw new IllegalArgumentException("handle: " + handle
                + " or taggedValue: " + taggedValue);
    }

    /**
     * @see org.argouml.model.ExtensionMechanismsHelper#setTaggedValue(java.lang.Object,
     *      java.util.Collection)
     */
    public void setTaggedValue(Object handle, Collection taggedValues) {
        if (handle instanceof ModelElement) {
            Collection tv = org.argouml.model.Model.getFacade().
                    getTaggedValuesCollection(handle);
            if (!tv.isEmpty()) {
                Vector tvs = new Vector(tv);
                Iterator toRemove = tvs.iterator();
                while (toRemove.hasNext()) {
                    Object value = toRemove.next();
                    if (!taggedValues.contains(value)) {
                        tv.remove(value);
                    }
                }
            }
            if (!taggedValues.isEmpty()) {
                Iterator toAdd = taggedValues.iterator();
                while (toAdd.hasNext()) {
                    Object value = toAdd.next();
                    if (!tv.contains(value)) {
                        tv.add(value);
                    }
                }
            }
            return;
        }
        throw new IllegalArgumentException("handle: " + handle
                + " or taggedValues: " + taggedValues);
    }

}
