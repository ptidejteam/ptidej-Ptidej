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
package mendel.part.output;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import mendel.Driver;
import mendel.filter.PackageFilter;
import mendel.model.IEntity;
import mendel.model.JClassEntity;

/**
 * @author Simon Denier
 * @since May 16, 2008
 *
 */
public class VersoHierarchyOutput extends VersoOutput {
	
	private PackageFilter ghostFilter;
	
	private Hashtable<String, Vector<String>> count = new Hashtable<String, Vector<String>>();

	private HashSet<JClassEntity> ghostSet;
	
	protected Map ghostProperties;
	
	
	/* (non-Javadoc)
	 * @see mendel.part.output.VersoOutput#initialize(mendel.Driver)
	 */
	@Override
	public void initialize(Driver driver) {
		super.initialize(driver);
		this.ghostFilter = new PackageFilter(getProject().getPackages());
		this.ghostSet = new HashSet<JClassEntity>();
		this.ghostProperties = new HashMap();
		initGhostProperties();
	}

	/**
	 * 
	 */
	public void initGhostProperties() {
		this.ghostProperties.put("NVS", "1");
		this.ghostProperties.put("NVI", "1");
		this.ghostProperties.put("Local G Count", "1");
	}

	public String inverseNumber(String number) {
		return "-" + number;
	}
	
	public StringBuffer computeDescriptions() {
		StringBuffer buffer = super.computeDescriptions();
		String[] minMax = findMinMax(getData(), "DIT");
		// Warning: we inverse min and max since we take their opposite
		buffer.append(propDescription("DIT", "integer", "None", inverseNumber(minMax[1]), inverseNumber(minMax[0])));
		minMax = findMinMax(getData(), "NOC");
		buffer.append(propDescription("NOC", "integer", "None", minMax[0], minMax[1]));
		return buffer;
	}

	public StringBuffer computeAssociations(StringBuffer buffer) {
		buffer.append(association("NVS", "Blue to Red")).append("\n");
		buffer.append(association("Local G Count", "height")).append("\n");
		buffer.append(association("NVI", "Twist")).append("\n");
		buffer.append(association("DIT", "sort")).append("\n");
		return buffer;
	}

	/* (non-Javadoc)
	 * @see mendel.part.output.VersoOutput#computeProperties(java.util.Map, java.lang.StringBuffer)
	 */
	@Override
	public StringBuffer computeProperties(Map record, StringBuffer props) {
		StringBuffer properties = super.computeProperties(record, props);
		properties.append(property("DIT", inverseNumber(record.get("DIT").toString())));
		properties.append(property(record, "NOC"));
		return properties;
	}
	
	
	@Override
	public String getObjectType(IEntity entity) {
		if( this.ghostFilter.accept(entity) ) {
			return "Class";
		} else {
			return "Interface"; // use Interface for Ghost type
		}
	}

	
	/* (non-Javadoc)
	 * @see mendel.part.output.VersoOutput#getObjectId(mendel.model.IEntity)
	 */
	@Override
	public String getObjectId(IEntity entity) {
		if( entity==null ) {
			return "object_p"; // Hack: taking care of java.lang.Object "parent"
		}
		String parentId = "";
		if( entity instanceof JClassEntity ) {
			JClassEntity centity = (JClassEntity) entity;
//			if( !centity.isRootClass() ) {
				if( centity.familySize() > 0 ) { // push parent class in their own hierarchy
					parentId = buildParentId(centity).toString() + ".";
				} else { // childless class
					parentId = buildParentId(centity.getSuperClass()).toString() + ".";
				}
//			}
		}
		return parentId + entity.getBaseName() + count(entity.getBaseName(), entity.getEntityName());
	}

	public String count(String basename, String fullname) {
		if( !this.count.containsKey(basename) ) {
			this.count.put(basename, new Vector<String>());
		}
		Vector<String> vector = this.count.get(basename);
		if( !vector.contains(fullname) ) {
			vector.add(fullname);
		}
		int index = vector.indexOf(fullname);
		return (index==0)? "" : "_" + index;
	}

	/**
	 * @param entity
	 */
	public StringBuffer buildParentId(JClassEntity entity) {
		registerGhostClass(entity); // Hack: we branch here because that's where we iterate over each class
		String baseId = entity.getBaseName().toLowerCase() + "_p";
		if( entity.isRootClass() ) {
			return new StringBuffer(baseId);
		} else {
			return buildParentId(entity.getSuperClass()).append(".").append(baseId); 
		}
	}
	
	/**
	 * @param entity
	 */
	public void registerGhostClass(JClassEntity entity) {
		if( !this.ghostFilter.accept(entity) && !this.ghostSet.contains(entity) ) {
			this.ghostSet.add(entity);
//			System.out.println("New Ghost " + this.ghostSet.size() + " " + entity.getEntityName());
		}
	}

	/* (non-Javadoc)
	 * @see mendel.part.output.VersoOutput#endMe()
	 */
	@Override
	public void endMe() {
		Set<JClassEntity> set = (Set<JClassEntity>) this.ghostSet.clone();
//		System.out.println(set.size());
		for (JClassEntity ghost : set) {
//			if( !set.equals(this.ghostSet) ) {
//				Set<JClassEntity> clone = (Set<JClassEntity>) this.ghostSet.clone();
//				clone.removeAll(set);
//				System.err.println("New Ghost? " + clone.size());
//			}
			StringBuffer result = buildEntity(ghost);
			addGhostProperties(ghost, result);
			xmlEnclose("Object", result);
			write(result);		
		}
		super.endMe();
	}

	/**
	 * @param result
	 */
	public void addGhostProperties(JClassEntity ghost, StringBuffer result) {
		this.ghostProperties.put("DIT", ghost.inheritanceDepth());
		this.ghostProperties.put("NOC", ghost.familySize());
		buildProperties(this.ghostProperties, result);
	}

}
