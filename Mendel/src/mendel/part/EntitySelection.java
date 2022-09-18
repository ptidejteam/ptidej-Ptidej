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
package mendel.part;

import java.util.Collection;
import java.util.Properties;

import mendel.Driver;
import mendel.Util;
import mendel.filter.EntityFilter;
import mendel.filter.PackageFilter;
import mendel.model.IEntity;

/**
 * Initializating Properties:
 * - filters: comma-separated list of qualified classnames of EntityFilter
 * 
 * Input: IEntity
 * Output: IEntity
 * 
 * @author Simon Denier
 * @since Feb 15, 2008
 *
 */
public class EntitySelection extends AbstractPart {

	private EntityFilter[] filters;

	
	public EntitySelection() {
		initialize();
	}
	
	public EntitySelection initialize() {
		// TODO: introduce a classpathfilter by default? config with project
		this.filters = new EntityFilter[0];
		return this;
	}
	
	
	/* (non-Javadoc)
	 * @see mendel.ISelection#initialize(java.util.Properties)
	 */
	public void initialize(Properties properties) {
		String[] filterNames = Util.extractValues(properties, "filters");
		EntityFilter[] filters = new EntityFilter[filterNames.length];
		for (int i = 0; i < filterNames.length; i++) {
			filters[i] = (EntityFilter) Util.createInstance(filterNames[i]);
		}
		setFilters(filters);
	}
	

	@Override
	public void initialize(Driver driver) {
		super.initialize(driver);
		// TODO: I dont like hard-coded parameter and specific protocol, 
		// but it is the easy way here
		// has to happen after driver initialization though
		for (int j = 0; j < getFilters().length; j++) {
			EntityFilter filter = getFilters()[j];
			if( filter.getClass().equals(PackageFilter.class) ) {
				setupPackageFilter((PackageFilter) filter);
			}			
		}
	}

	private void setupPackageFilter(PackageFilter filter) {
		filter.setPackages(getProject().getPackages());
	}

	/**
	 * @return the filters
	 */
	public EntityFilter[] getFilters() {
		return this.filters;
	}

	public void setFilters(EntityFilter[] filters) {
		this.filters = filters;
	}

	
	protected boolean passFilter(IEntity entity) {
		int i = 0;
		boolean pass = true;
		while( pass && i < this.filters.length ) {
			pass &= this.filters[i].accept(entity);
			i++;
		}
		return pass;
	}


	public Object compute(Object data) {
		if( passFilter((IEntity) data) ) {
			return data;
		} else {
			return null;
		}
	}
	
	public void computeAll() {
		Collection filtered = getData();
		// remove data?
		for (int i = 0; i < this.filters.length; i++) {
			filtered = this.filters[i].filter(filtered);
			// TODO: log filter ratio
		}
		next().addAll(filtered);
	}

}
