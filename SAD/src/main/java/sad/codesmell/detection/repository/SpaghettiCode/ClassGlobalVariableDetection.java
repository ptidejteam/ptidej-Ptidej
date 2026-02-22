/* (c) Copyright 2001 and following years, Yann-Gaël Guéhéneuc,
 * University of Montreal.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
   
package sad.codesmell.detection.repository.SpaghettiCode;
   
/**
 * This class represents the detection of the code smell ClassGlobalVariable
 * 
 * @author Auto generated
 *
 */
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
  
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IClass;
import padl.kernel.IEntity;
import padl.kernel.IField;
import padl.kernel.IGhost;
import sad.codesmell.property.impl.FieldProperty;
import sad.codesmell.property.impl.ClassProperty;
import sad.codesmell.detection.ICodeSmellDetection;
import sad.codesmell.detection.repository.AbstractCodeSmellDetection;
import sad.kernel.impl.CodeSmell;

import util.io.ProxyConsole;

/**
 * @author Auto generated
 */

public class ClassGlobalVariableDetection  extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	// David and Yann 2013/10/24: Useless?
	// This variable and its accessor should probably removed. 
	private static final int bool = 1;

	public boolean getValueBoolean() {
		return ClassGlobalVariableDetection.bool == 1;
	}
	 
	public String getName() {
		return "ClassGlobalVariable";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		Set classesClassGlobalVariable = new HashSet();
		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next(); 
			// Yann 26/02/20: IGhosts are both IClass and IInterface!
			// I must exclude IGhost when not desirable to be included.
			if (entity instanceof IClass && !(entity instanceof IGhost)) {
				final IClass aClass = (IClass) entity;
				final Set setOfVariables = new HashSet();
	   			final Iterator iter2 = aClass.getIteratorOnConstituents(IField.class);
				   while (iter2.hasNext()) {
					   final IField aField = (IField) iter2.next();
					   if (aField.isStatic()  && !(aField.isFinal()) && !(aField.isPrivate())) {
						   setOfVariables.add(new FieldProperty(aField));
					   }
				   }
	   
				   
				boolean presence = getValueBoolean();
	
				if (presence == true) {
					if (!(setOfVariables.isEmpty())) {
						classesClassGlobalVariable =
							createCodeSmell(
								classesClassGlobalVariable,
								aClass,
								setOfVariables);
					}
				} else {
					if (setOfVariables.isEmpty()) {
						classesClassGlobalVariable =
							createCodeSmell(
								classesClassGlobalVariable,
								aClass,
								setOfVariables);
					}
				}
			}
		}

		this.setSetOfSmells(classesClassGlobalVariable);
	}
	
	private Set createCodeSmell(
		Set classesClassGlobalVariable,
		final IClass aClass,
		final Set setOfVariables) {
		try {
			CodeSmell dc =
				new CodeSmell(
					"ClassGlobalVariable",
					"",
					new ClassProperty(aClass));
			dc.getClassProperty().addProperties(setOfVariables);

			classesClassGlobalVariable.add(dc);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return classesClassGlobalVariable;
	}
}
