package fr.emn.oadymppac.graph;

import java.net.URL;

/**
 * Towards InstrumentalJazz
 * @author Jean-Daniel Fekete
 * @date 5 sept. 2002
 */
public interface GraphLoader {
	public float load(URL url, NamedGraphDelegator graph);
}
