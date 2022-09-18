package fr.emn.oadymppac.widgets;

/**
 * @author Mohammad Ghoniem
 *
 * This interface should be implemented by components
 * reacting to <code>ClusterEvent</code>s.
 */
public interface ClusterListener {
	public void clusterChanged(ClusterEvent event);
}
