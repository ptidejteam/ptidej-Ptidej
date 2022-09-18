package choco.palm.search.pathrepair;

import java.util.Comparator;
import choco.Constraint;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.explain.SearchInfo;

/**

 * Created by IntelliJ IDEA.

 * User: Administrateur

 * Date: 15 janv. 2004

 * Time: 14:59:44

 * To change this template use Options | File Templates.

 */

public class PathRepairSearchInfo implements SearchInfo {

	/*

	*  The weigth of a decision constraint appearing in the conflicts in memory

	*  It corresponds to its frequency among the all conflict set weighted by the size of each conflict

	*/

	private class PathRepairComparator implements Comparator {

		public int compare(final Object o1, final Object o2) {

			final PalmConstraintPlugin plug1 =
				(PalmConstraintPlugin) ((Constraint) o1).getPlugIn();

			final PalmConstraintPlugin plug2 =
				(PalmConstraintPlugin) ((Constraint) o2).getPlugIn();

			if (((PathRepairSearchInfo) plug1.getSearchInfo()).getWeigth() > ((PathRepairSearchInfo) plug2
				.getSearchInfo()).getWeigth()) {
				return -1;
			}
			else if (((PathRepairSearchInfo) plug1.getSearchInfo()).getWeigth() == ((PathRepairSearchInfo) plug2
				.getSearchInfo()).getWeigth()) {

				if (plug1.getTimeStamp() > plug2.getTimeStamp()) {
					return -1;
				}
				else if (plug1.getTimeStamp() < plug2.getTimeStamp()) {
					return 1;
				}
				else {
					return 0;
				}

			}
			else {
				return 1;
			}

		}

	}

	private float weigthInfo = 0;

	private final Comparator comparatorInfo;

	public PathRepairSearchInfo() {

		this.comparatorInfo = new PathRepairComparator();

	}

	public void add(final float val) {
		this.weigthInfo += val;
	}

	public Comparator getComparator() {

		return this.comparatorInfo;

	}

	public float getWeigth() {

		return this.weigthInfo;

	}

	public void set(final float val) {
		this.weigthInfo = val;
	}

}