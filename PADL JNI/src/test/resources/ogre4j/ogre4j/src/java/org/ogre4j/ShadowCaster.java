/* This source file is part of ogre4j
 *     (The JNI bindings for OGRE)
 * For the latest info, see http://www.ogre4j.org/
 * 
 * Copyright (c) 2005 netAllied GmbH, Tettnang
 * Also see acknowledgements in Readme.html
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA, or go to
 * http://www.gnu.org/copyleft/lesser.txt.
 * 
 *
 * ShadowCaster.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.2 $
 * $Date: 2005/08/13 19:45:34 $
 * $Author: earl3982 $
 */
package org.ogre4j;

import java.util.Vector;

/**
 * TODO ShadowCaster type/class description. status: 100% public c++ methods
 * except getShadowVolumeRenderableIterator
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 */
public interface ShadowCaster {
    class ShadowRenderableList extends Vector<ShadowRenderable> {
        private static final long serialVersionUID = -1430658846485897463L;
    };

    /**
     * Returns whether or not this object currently casts a shadow.
     * 
     * @return
     */
    public boolean getCastShadows();

    /**
     * Returns details of the edges which might be used to determine a
     * silhouette. TODO
     * 
     * @return
     */
    // public abstract EdgeData getEdgeList();
    /**
     * Get the world bounding box of the caster.
     */
    public AxisAlignedBox getWorldBoundingBox();

    /**
     * Get the world bounding box of the caster.
     * 
     * @param derive
     * @return
     */
    public AxisAlignedBox getWorldBoundingBox(boolean derive);

    /**
     * Gets the world space bounding box of the light cap.
     * 
     * @return
     */
    public AxisAlignedBox getLightCapBounds();

    /**
     * Gets the world space bounding box of the dark cap, as extruded using the
     * light provided.
     * 
     * @param light
     * @param dirLightExtrusionDist
     * @return
     */
    public AxisAlignedBox getDarkCapBounds(Light light,
            float dirLightExtrusionDist);

    // TODO: public abstract Iterator<ShadowRenderable>
    // getShadowVolumeRenderableIterator(ShadowTechnique shadowTechnique, Light
    // light,
    // HardwareIndexBuffer indexBuffer, boolean extrudeVertices, float
    // extrusionDistance, long flags);

    // TODO extrudeVertices
    /**
     * Get the distance to extrude for a point/spot light.
     */
    public float getPointExtrusionDistance(Light l);

}
