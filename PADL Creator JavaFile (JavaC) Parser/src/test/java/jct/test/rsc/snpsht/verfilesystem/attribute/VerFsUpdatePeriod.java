/*
 * (c) Copyright 2008 and following years, Julien Tanteri, University of
 * Montreal.
 * 
 * Use and copying of this software and preparation of derivative works based
 * upon this software are permitted. Any copy of this software or of any
 * derivative work must include the above copyright notice of the author, this
 * paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS ALL
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND NOT
 * WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN, ANY LIABILITY FOR DAMAGES
 * RESULTING FROM THE SOFTWARE OR ITS USE IS EXPRESSLY DISCLAIMED, WHETHER
 * ARISING IN CONTRACT, TORT (INCLUDING NEGLIGENCE) OR STRICT LIABILITY, EVEN IF
 * THE AUTHOR IS ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package jct.test.rsc.snpsht.verfilesystem.attribute;

import java.util.Date;

import jct.test.rsc.snpsht.utils.Pair;

public class VerFsUpdatePeriod extends VerFsUpdateTime implements IVerFsPeriod {
	private boolean startTimeAsDefault;
	private Pair<Date, Date> period;

	public VerFsUpdatePeriod(Date startTime, Date endTime) {
		this(startTime, endTime, true);
	}

	public VerFsUpdatePeriod(
		Date startTime,
		Date endTime,
		boolean startTimeAsDefault) {
		super(null);

		this.period = new Pair<Date, Date>();
		this.period.cdr(endTime);
		this.period.car(startTime);

		this.startTimeAsDefault = startTimeAsDefault;
	}

	@Override
	public boolean isStartTimeAsDefault() {
		return this.startTimeAsDefault;
	}

	@Override
	public Date getTime() {
		if ((this.startTimeAsDefault && this.period.car() != null)
				|| (!this.startTimeAsDefault && this.period.cdr() == null))
			return this.period.car();
		else
			return this.period.cdr();
	}
	
	@Override
	public Date getEndTime() {
		return this.period.cdr();
	}

	@Override
	public Date getStartTime() {
		return this.period.car();
	}

	@Override
	public Pair<Date, Date> getPeriod() {
		return this.period;
	}
}
