package fr.emn.oadymppac.event;

/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2002</p>
 * <p>Société : </p>
 * @author non attribué
 * @version 1.0
 */

public class EventStackChangedEvent {

	BasicSolverEvent event;

	public EventStackChangedEvent() {
	}

	public EventStackChangedEvent(final BasicSolverEvent ev) {
		this.event = ev;
	}

	public BasicSolverEvent getEvent() {
		return this.event;
	}

	public void setEvent(final BasicSolverEvent ev) {
		this.event = ev;
	}
}