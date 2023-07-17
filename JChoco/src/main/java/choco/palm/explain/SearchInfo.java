package choco.palm.explain;

import java.util.Comparator;

/**
 * Information concerning constraints that can be learned during the search
 * A Comparator has to be designed according to the searchInfo to compare.
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 15 janv. 2004
 * Time: 14:54:58
 * To change this template use Options | File Templates.
 */
public interface SearchInfo {

	public Comparator getComparator();

}
