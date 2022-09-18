package fr.emn.oadymppac.widgets;

import gl4java.GLEnum;
import gl4java.GLFunc;
import gl4java.drawable.GLDrawable;
import javax.swing.BoundedRangeModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 * Class to apply a Fisheye to a focal point. See " M. S. T.
 * Carpendale , Catherine Montagnese, A framework for unifying
 * presentation space, Proceedings of the 14th annual ACM symposium
 * on User interface software and technology November 2001, ACM
 * Press, pp 61 - 70,  ISBN:1-58113-438-X
 * 
 * @version $Revision: 1.2 $
 * @author Jean-Daniel Fekete
 */
public class Fisheye {
	public static class DistanceL1 implements Metric {
		public float distance(
			final float x,
			final float y,
			final float x0,
			final float y0) {
			return Math.abs(x - x0) + Math.abs(y - y0);
		}
	}
	/**
	 * DOCUMENT ME!
	 */
	public static class DistanceL2 implements Metric {
		public float distance(
			final float x,
			final float y,
			final float x0,
			final float y0) {
			final float dx = x - x0;
			final float dy = y - y0;

			return (float) Math.sqrt(dx * dx + dy * dy);
		}
	}
	public static class DistanceLInf implements Metric {
		public float distance(
			final float x,
			final float y,
			final float x0,
			final float y0) {
			return Math.max(Math.abs(x - x0), Math.abs(y - y0));
		}
	}
	public interface Metric {
		public float distance(float x1, float y1, float x2, float y2);
	}
	public interface Profile {
		public float profile(float t);
	}
	public static class ProfileCos implements Profile {
		/**
		 * @see fr.emn.oadymppac.widgets.Fisheye.Profile#profile(float)
		 */
		public float profile(final float t) {
			return (float) Math.cos(t * Math.PI / 2);
		}
	}
	public static class ProfileGuassian implements Profile {
		static final double ro = 2;
		static final double denom = 1 / (ProfileGuassian.ro * Math
			.sqrt(2 * Math.PI));

		/**
		 * @see fr.emn.oadymppac.widgets.Fisheye.Profile#profile(float)
		 */
		public float profile(final float t) {
			return (float) (Math.exp(-t * t
					/ (2 * ProfileGuassian.ro * ProfileGuassian.ro)) * ProfileGuassian.denom);
		}
	}
	public static class ProfileInverse implements Profile {
		Profile profile;

		public ProfileInverse(final Profile profile) {
			this.profile = profile;
		}

		/**
		 * @see fr.emn.oadymppac.widgets.Fisheye.Profile#profile(float)
		 */
		public float profile(final float t) {
			return 1 - this.profile.profile(1 - t);
		}
	}
	public static class ProfileLinear implements Profile {
		/**
		 * @see fr.emn.oadymppac.widgets.Fisheye.Profile#profile(float)
		 */
		public float profile(final float t) {
			return 1 - t;
		}
	}
	public static class ProfileOneMinusSin implements Profile {
		/**
		 * @see fr.emn.oadymppac.widgets.Fisheye.Profile#profile(float)
		 */
		public float profile(final float t) {
			return 1 - (float) Math.sin(t);
		}
	}
	/** constant value for setDistanceMetric to use a L1 distance */
	public static final short DISTANCE_L1 = 0;
	/** constant value for setDistanceMetric to use a L2 distance  */
	public static final short DISTANCE_L2 = 1;
	/** constant value for setDistanceMetric to use a L infinity distance  */
	public static final short DISTANCE_LINF = 2;
	/** constant value for setLensType to use a gaussian lens types */
	public static final short LENS_GAUSSIAN = 0;
	/** constant value for setLensType to use a cosine lens types  */
	public static final short LENS_COSINE = 1;
	/** constant value for setLensType to use a hemisphere lens types  */
	public static final short LENS_HEMISPHERE = 2;
	/** constant value for setLensType to use a linear lens types  */
	public static final short LENS_LINEAR = 3;
	/** constant value for setLensType to use an inverse cosine lens types  */
	public static final short LENS_INVERSE_COSINE = 4;
	/** constant value for setLensType to use a manhattan lens types  */
	public static final short LENS_MANHATTAN = 5;
	protected transient ChangeEvent changeEvent = null;
	EventListenerList listenerList = new EventListenerList();

	float focusX;

	float focusY;

	float contextRadius;

	float focusRadius;

	float maxAltitude;

	float plateauAltitude;

	short distanceMetric;

	Metric metric;

	short lensType;

	Profile profile;

	/**
	 * Creates a new Fisheye object.
	 */
	public Fisheye() {
		this(50, 5);
	}

	/**
	 * Creates a new Fisheye object.
	 * 
	 * @param radius DOCUMENT ME!
	 * @param maxAltitude DOCUMENT ME!
	 */
	public Fisheye(final float radius, final float maxAltitude) {
		this.contextRadius = radius;
		this.focusRadius = 0;
		this.maxAltitude = maxAltitude;
		this.setPlateauAltitude(maxAltitude);
		this.setDistanceMetric(Fisheye.DISTANCE_L2);
		this.setLensType(Fisheye.LENS_LINEAR);
	}

	/**
	 * Adds a <code>ChangeListener</code>.  The change listeners are
	 * run each time any one of the properties changes.
	 * 
	 * @param l the ChangeListener to add
	 * 
	 * @see #removeChangeListener
	 * @see BoundedRangeModel#addChangeListener
	 */
	public void addChangeListener(final ChangeListener l) {
		this.listenerList.add(ChangeListener.class, l);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param dist DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public float altitude(final float dist) {
		if (this.maxAltitude == 0) {
			return 0;
		}

		if (dist > this.contextRadius) {
			return 0;
		}

		//        else if (dist <= focusRadius) {
		//            return maxAltitude;
		//        }
		else {
			float ret;
			ret =
				Math.min(
					this.maxAltitude
							* this.lens((dist - this.focusRadius)
									/ (this.contextRadius - this.focusRadius)),
					this.plateauAltitude);

			//System.out.println("altitude("+dist+")="+ret);
			return ret;
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param drawable DOCUMENT ME!
	 * @param width DOCUMENT ME!
	 * @param height DOCUMENT ME!
	 */
	public void beginTransform(
		final GLDrawable drawable,
		final int width,
		final int height) {
		final GLFunc gl = drawable.getGL();

		gl.glMatrixMode(GLEnum.GL_PROJECTION);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glFrustum(
			-this.focusX / 10f,
			(width - this.focusX) / 10f,
			(height - this.focusY) / 10f,
			-this.focusY / 10f,
			1,
			10);
		gl.glTranslated(-this.focusX, -this.focusY, -10);

		//gl.glScaled(1, 1, 8);
		gl.glMatrixMode(GLEnum.GL_MODELVIEW);
		gl.glPushMatrix();
		gl.glLoadIdentity();
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param x DOCUMENT ME!
	 * @param y DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public float distance(final float x, final float y) {
		return this.metric.distance(x, y, this.focusX, this.focusY);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param drawable DOCUMENT ME!
	 */
	public void endTransform(final GLDrawable drawable) {
		final GLFunc gl = drawable.getGL();

		gl.glMatrixMode(GLEnum.GL_PROJECTION);
		gl.glPopMatrix();
		gl.glMatrixMode(GLEnum.GL_MODELVIEW);
		gl.glPopMatrix();
	}

	/**
	 * Runs each <code>ChangeListener</code>'s
	 * <code>stateChanged</code> method.
	 * 
	 * @see #setRangeProperties
	 * @see EventListenerList
	 */
	protected void fireStateChanged() {
		final Object[] listeners = this.listenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChangeListener.class) {
				if (this.changeEvent == null) {
					this.changeEvent = new ChangeEvent(this);
				}

				((ChangeListener) listeners[i + 1])
					.stateChanged(this.changeEvent);
			}
		}
	}

	/**
	 * Returns an array of all the change listeners registered on
	 * this <code>BasicAjacencyMatrix</code>.
	 * 
	 * @return all of this object <code>ChangeListener</code>s or an
	 *         empty array if no change listeners are currently
	 *         registered
	 * 
	 * @since 1.4
	 * @see #addChangeListener
	 * @see #removeChangeListener
	 */
	public ChangeListener[] getChangeListeners() {
		return (ChangeListener[]) this.listenerList
			.getListeners(ChangeListener.class);
	}

	/**
	 * Returns the contextRadius.
	 * 
	 * @return float
	 */
	public float getContextRadius() {
		return this.contextRadius;
	}

	/**
	 * Returns the distanceMetric.
	 * 
	 * @return short the distanceMetric
	 */
	public short getDistanceMetric() {
		return this.distanceMetric;
	}

	/**
	 * Returns the focusRadius.
	 * 
	 * @return float
	 */
	public float getFocusRadius() {
		return this.focusRadius;
	}

	/**
	 * Returns the focusX.
	 * 
	 * @return float
	 */
	public float getFocusX() {
		return this.focusX;
	}

	/**
	 * Returns the focusY.
	 * 
	 * @return float
	 */
	public float getFocusY() {
		return this.focusY;
	}

	/**
	 * Returns the lensType.
	 * 
	 * @return short
	 */
	public short getLensType() {
		return this.lensType;
	}

	/**
	 * Returns the maxAltitude.
	 * 
	 * @return float
	 */
	public float getMaxAltitude() {
		return this.maxAltitude;
	}

	/**
	 * Returns the plateauAltitude.
	 * 
	 * @return float
	 */
	public float getPlateauAltitude() {
		return this.plateauAltitude;
	}

	/**
	 * Return the applied plateau scale
	 *
	 * @return the applied scale
	 */
	public float getPlateauScale() {
		if (this.plateauAltitude == 0) {
			return this.getScale();
		}

		return 10f / (10f - this.plateauAltitude);
	}

	/**
	* Return the applied scale
	*
	* @return the applied scale
	*/
	public float getScale() {
		if (this.maxAltitude == 0) {
			return 0;
		}

		return 10f / (10f - this.maxAltitude);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param t DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public float lens(final float t) {
		//    	if (t < 0.5f)
		//    		return profile1.profile(2*t)*t;
		//    	else
		//    		return profile2.profile(2*(1-t))*t;
		//assert (t >= 0 && t <= 1);

		return this.profile.profile(t);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param x DOCUMENT ME!
	 * @param y DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public float pointAltitude(final float x, final float y) {
		return this.altitude(this.distance(x, y));
	}

	/**
	 * Removes a <code>ChangeListener</code>.
	 * 
	 * @param l the <code>ChangeListener</code> to remove
	 * 
	 * @see #addChangeListener
	 * @see BoundedRangeModel#removeChangeListener
	 */
	public void removeChangeListener(final ChangeListener l) {
		this.listenerList.remove(ChangeListener.class, l);
	}

	/**
	 * Sets the contextRadius.
	 * 
	 * @param radius The contextRadius to set
	 */
	public void setContextRadius(final float radius) {
		this.contextRadius = radius;
		this.fireStateChanged();
	}

	/**
	 * Sets the distanceMetric.
	 * 
	 * @param distanceMetrics The distanceMetric to set
	 */
	public void setDistanceMetric(final short distanceMetrics) {
		this.distanceMetric = distanceMetrics;

		switch (distanceMetrics) {
			case DISTANCE_L1 :
				this.metric = new DistanceL1();

				break;

			case DISTANCE_L2 :
				this.metric = new DistanceL2();

				break;

			case DISTANCE_LINF :
				this.metric = new DistanceLInf();

				break;
		}

		this.fireStateChanged();
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param x DOCUMENT ME!
	 * @param y DOCUMENT ME!
	 */
	public void setFocus(final float x, final float y) {
		this.focusX = x;
		this.focusY = y;
	}

	/**
	 * Sets the focusRadius.
	 * 
	 * @param focusRadius The focusRadius to set
	 */
	public void setFocusRadius(final float focusRadius) {
		this.focusRadius = focusRadius;
		this.fireStateChanged();
	}

	/**
	 * Sets the focusX.
	 * 
	 * @param focusX The focusX to set
	 */
	public void setFocusX(final float focusX) {
		this.focusX = focusX;
		this.fireStateChanged();
	}

	/**
	 * Sets the focusY.
	 * 
	 * @param focusY The focusY to set
	 */
	public void setFocusY(final float focusY) {
		this.focusY = focusY;
		this.fireStateChanged();
	}

	/**
	 * Sets the lensType.
	 * 
	 * @param lensType The lensType to set
	 */
	public void setLensType(final short lensType) {
		this.lensType = lensType;

		switch (lensType) {
			case LENS_GAUSSIAN :
				this.profile = new ProfileGuassian();

				break;

			case LENS_COSINE :
				this.profile = new ProfileCos();

				break;

			case LENS_HEMISPHERE :
				this.profile = new ProfileCos();

				break;

			case LENS_INVERSE_COSINE :
				this.profile = new ProfileInverse(new ProfileCos());

				break;

			case LENS_LINEAR :
				this.profile = new ProfileLinear();

				break;

			case LENS_MANHATTAN :
				this.profile = new Profile() {
					/**
					 * @see fr.emn.oadymppac.widgets.Fisheye.Profile#profile(float)
					 */
					public float profile(final float t) {
						return t == 0 ? 1 : 0;
					}
				};

				break;
		}

		this.fireStateChanged();
	}

	/**
	 * Sets the maxAltitude.
	 * 
	 * @param maxAltitude The maxAltitude to set
	 */
	public void setMaxAltitude(float maxAltitude) {
		if (maxAltitude < 0) {
			maxAltitude = 0;
		}
		else if (maxAltitude > 9) {
			maxAltitude = 9;
		}

		this.maxAltitude = maxAltitude;
		this.fireStateChanged();
	}

	/**
	 * Sets the plateauAltitude.
	 * 
	 * @param plateauAltitude The plateauAltitude to set
	 */
	public void setPlateauAltitude(final float plateauAltitude) {
		this.plateauAltitude = plateauAltitude;
		this.fireStateChanged();
	}

	/**
	     * Change the plateau scale
	     *
	     * @param scale the new scale
	     */
	public void setPlateauScale(final float scale) {
		if (scale == 0) {
			this.setPlateauAltitude(this.maxAltitude);
		}
		else {
			this.setPlateauAltitude(10 - 10 / scale);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param focus DOCUMENT ME!
	 * @param context DOCUMENT ME!
	 */
	public void setRadii(final float focus, final float context) {
		this.focusRadius = focus;
		this.contextRadius = context;
		this.fireStateChanged();
	}

	/**
	 * Change the scale
	 *
	 * @param scale the new scale
	 */
	public void setScale(final float scale) {
		if (scale == 0) {
			this.setMaxAltitude(0);
		}
		else {
			this.setMaxAltitude(10 - 10 / scale);
		}
	}
}