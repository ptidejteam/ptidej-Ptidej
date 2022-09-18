/**
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France
 * -------------------------------------------------------------------------
 * This software is published under the terms of the QPL Software License
 * a copy of which has been included with this distribution in the
 * license-infovis.txt file.
 */
package infovis.column;

import infovis.Column;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Link together two columns, applying an abstract method each time the first
 * is modified to recompute the second.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class ColumnLink implements ChangeListener {
    protected Column fromColumn;
    protected Column toColumn;
    static boolean   inNotification;

    /**
     * Creates a new ColumnLink object.
     *
     * @param from the column to track.
     * @param to the column to update.
     */
    public ColumnLink(Column from, Column to) {
        this.fromColumn = from;
        this.toColumn = to;
        from.addChangeListener(this);
    }

    /**
     * This method is called each time the from Column is modified.
     * It should recompute the other column.
     */
    protected abstract void update();

    /**
     * Removes the link.
     */
    public void dispose() {
        if (this.fromColumn != null) {
            this.fromColumn.removeChangeListener(this);
            this.fromColumn = null;
        }
    }

    /**
     * @see javax.swing.event.ChangeListener#stateChanged(ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
        updateColumn();
    }

    public void updateColumn() {
        if (inNotification) {
            throw new RuntimeException("Cycle in Column links");
        }
        try {
            inNotification = true;
            this.toColumn.disableNotify();
            update();
        } finally {
            this.toColumn.enableNotify();
            inNotification = false;
        }
    }
}
