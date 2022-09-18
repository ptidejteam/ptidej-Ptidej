/*
 * @(#)AbstractLocator.java 5.1
 *
 */

package CH.ifa.draw.standard;

import java.io.IOException;

import CH.ifa.draw.framework.Locator;
import CH.ifa.draw.util.Storable;
import CH.ifa.draw.util.StorableInput;
import CH.ifa.draw.util.StorableOutput;

/**
 * AbstractLocator provides default implementations for
 * the Locator interface.
 *
 * @see Locator
 * @see Handle
 */

public abstract class AbstractLocator
				implements Locator, Storable, Cloneable {

	protected AbstractLocator() {
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
	        throw new InternalError();
		}
	}

	/**
	 * Stores the arrow tip to a StorableOutput.
	 */
	public void write(StorableOutput dw) {
	}

	/**
	 * Reads the arrow tip from a StorableInput.
	 */
	public void read(StorableInput dr) throws IOException {
	}
}


