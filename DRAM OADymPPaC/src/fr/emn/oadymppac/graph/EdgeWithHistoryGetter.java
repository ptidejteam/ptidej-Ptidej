package fr.emn.oadymppac.graph;

import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import salvo.jesus.graph.Edge;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.3 $
 */
public class EdgeWithHistoryGetter implements EdgeWeightGetter {
	BoundedRangeModel range;

	public EdgeWithHistoryGetter() {
		this(new DefaultBoundedRangeModel());
	}

	public EdgeWithHistoryGetter(final BoundedRangeModel model) {
		this.range = model;
	}
	/**
	 * @see fr.emn.oadymppac.graph.EdgeWeightGetter#getWeight(Edge)
	 */
	public float getWeight(final Edge e) {
		final EdgeWithHistory edge = (EdgeWithHistory) e;
		final float valuedelta =
			edge.getDelta(this.range.getValue(), this.range.getValue()
					+ this.range.getExtent());
		if (valuedelta == 0.0) {
			return valuedelta;
		}
		else {
			return (float) edge.getWeight();
		}
		//return edge.getDelta(range.getValue(), range.getValue()+range.getExtent());
	}
}
