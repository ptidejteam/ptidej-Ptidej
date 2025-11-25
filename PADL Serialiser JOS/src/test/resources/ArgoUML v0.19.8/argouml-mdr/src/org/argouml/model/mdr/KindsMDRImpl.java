// $Id: KindsMDRImpl.java,v 1.2 2006/03/02 05:07:41 vauchers Exp $
// Copyright (c) 2005 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies. This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason. IN NO EVENT SHALL THE
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

import org.argouml.model.AggregationKind;
import org.argouml.model.ChangeableKind;
import org.argouml.model.ConcurrencyKind;
import org.argouml.model.DirectionKind;
import org.argouml.model.OrderingKind;
import org.argouml.model.PseudostateKind;
import org.argouml.model.ScopeKind;
import org.argouml.model.VisibilityKind;
import org.omg.uml.foundation.datatypes.AggregationKindEnum;
import org.omg.uml.foundation.datatypes.CallConcurrencyKindEnum;
import org.omg.uml.foundation.datatypes.ChangeableKindEnum;
import org.omg.uml.foundation.datatypes.OrderingKindEnum;
import org.omg.uml.foundation.datatypes.ParameterDirectionKindEnum;
import org.omg.uml.foundation.datatypes.PseudostateKindEnum;
import org.omg.uml.foundation.datatypes.ScopeKindEnum;
import org.omg.uml.foundation.datatypes.VisibilityKindEnum;

/**
 * Class that contains enums in the Model.
 */
public class KindsMDRImpl implements ChangeableKind, AggregationKind,
        PseudostateKind, ScopeKind, ConcurrencyKind, DirectionKind,
        OrderingKind, VisibilityKind {

    private MDRModelImplementation modelImplementation;
	
    /**
     * Constructor.
     */
    KindsMDRImpl(MDRModelImplementation mi) {
        modelImplementation = mi;
    }

    /**
     * @return Returns the AddOnly ChangeableKind.
     */
    public Object getAddOnly() {
        return ChangeableKindEnum.CK_ADD_ONLY;
    }

    /**
     * @return Returns the Aggregate AggregationKind.
     */
    public Object getAggregate() {
        return AggregationKindEnum.AK_AGGREGATE;
    }

    /**
     * @return Returns the Branch PseudostateKind.
     * @deprecated UML 1.3 only - use getChoice
     */
    public Object getBranch() {
        return getChoice();
    }

    /**
     * @return Returns the Choice PseudostateKind.
     */
    public Object getChoice() {
        return PseudostateKindEnum.PK_CHOICE;
    }

    /**
     * @return Returns the Changeable ChangeableKind.
     */
    public Object getChangeable() {
        return ChangeableKindEnum.CK_CHANGEABLE;
    }

    /**
     * @return Returns the Classifier ScopeKind.
     */
    public Object getClassifier() {
        return ScopeKindEnum.SK_CLASSIFIER;
    }

    /**
     * @return Returns the Composite AggregationKind.
     */
    public Object getComposite() {
        return AggregationKindEnum.AK_COMPOSITE;
    }

    /**
     * @return Returns the Concurrent CallConcurrencyKind.
     */
    public Object getConcurrent() {
        return CallConcurrencyKindEnum.CCK_CONCURRENT;
    }

    /**
     * @return Returns the DeepHistory PseudostateKind.
     */
    public Object getDeepHistory() {
        return PseudostateKindEnum.PK_DEEP_HISTORY;
    }

    /**
     * @return Returns the Fork PseudostateKind.
     */
    public Object getFork() {
        return PseudostateKindEnum.PK_FORK;
    }

    /**
     * @return Returns the Frozen ChangeableKind.
     */
    public Object getFrozen() {
        return ChangeableKindEnum.CK_FROZEN;
    }

    /**
     * @return Returns the Guarded CallConcurrencyKind.
     */
    public Object getGuarded() {
        return CallConcurrencyKindEnum.CCK_GUARDED;
    }

    /**
     * @return Returns the In ParameterDirectionKind.
     */
    public Object getInParameter() {
        return ParameterDirectionKindEnum.PDK_IN;
    }

    /**
     * @return Returns the Initial PseudostateKind.
     */
    public Object getInitial() {
        return PseudostateKindEnum.PK_INITIAL;
    }

    /**
     * @return Returns the Inout ParameterDirectionKind.
     */
    public Object getInOutParameter() {
        return ParameterDirectionKindEnum.PDK_INOUT;
    }

    /**
     * @return Returns the Instance ScopeKind.
     */
    public Object getInstance() {
        return ScopeKindEnum.SK_INSTANCE;
    }

    /**
     * @return Returns the Join PseudostateKind.
     */
    public Object getJoin() {
        return PseudostateKindEnum.PK_JOIN;
    }

    /**
     * @return Returns the Junction PseudostateKind.
     */
    public Object getJunction() {
        return PseudostateKindEnum.PK_JUNCTION;
    }

    /**
     * @return Returns the None AggregationKind.
     */
    public Object getNone() {
        return AggregationKindEnum.AK_NONE;
    }

    /**
     * @return Returns the Ordered OrderingKind.
     */
    public Object getOrdered() {
        return OrderingKindEnum.OK_ORDERED;
    }

    /**
     * @return Returns the Out ParameterDirectionKind.
     */
    public Object getOutParameter() {
        return ParameterDirectionKindEnum.PDK_OUT;
    }

    /**
     * @return Returns the Private VisibilityKind.
     */
    public Object getPrivate() {
        return VisibilityKindEnum.VK_PRIVATE;
    }

    /**
     * @return Returns the Protected VisibilityKind.
     */
    public Object getProtected() {
        return VisibilityKindEnum.VK_PROTECTED;
    }

    /**
     * @return Returns the Public VisibilityKind.
     */
    public Object getPublic() {
        return VisibilityKindEnum.VK_PUBLIC;
    }

    /**
     * @return Returns the Return ParameterDirectionKind.
     */
    public Object getReturnParameter() {
        return ParameterDirectionKindEnum.PDK_RETURN;
    }

    /**
     * @return Returns the Sequential CallConcurrencyKind.
     */
    public Object getSequential() {
        return CallConcurrencyKindEnum.CCK_SEQUENTIAL;
    }

    /**
     * @return Returns the ShallowHistory PseudostateKind.
     */
    public Object getShallowHistory() {
        return PseudostateKindEnum.PK_SHALLOW_HISTORY;
    }

    /**
     * @return Returns the Unordered OrderingKind.
     */
    public Object getUnordered() {
        return OrderingKindEnum.OK_UNORDERED;
    }

}

