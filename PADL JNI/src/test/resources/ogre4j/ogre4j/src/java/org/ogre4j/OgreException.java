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
 * OgreException.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.1 $
 * $Date: 2005/07/29 09:58:55 $
 * $Author: yavin02 $
 */
package org.ogre4j;

/**
 * @author Kai Klesatschke <yavin@ogre4j.org>
 * TODO Exception type/class description.
 */
public class OgreException extends Exception
{
    private static final long serialVersionUID = 3617854162017400120L;
    public static enum ExceptionCodes { 
        UNIMPLEMENTED_FEATURE,
        ERR_CANNOT_WRITE_TO_FILE,
        ERR_NO_RENDERSYSTEM_SELECTED,
        ERR_DIALOG_OPEN_ERROR, 
        ERR_INVALIDPARAMS,
        ERR_RENDERINGAPI_ERROR,
        ERR_DUPLICATE_ITEM,
        ERR_ITEM_NOT_FOUND, 
        ERR_FILE_NOT_FOUND,
        ERR_INTERNAL_ERROR,
        ERR_RT_ASSERTION_FAILED 
      }
    
    public OgreException()
    {
        super();
    }
    
    public OgreException(String message)
    {
        super(message);
    }
    
    public OgreException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public OgreException(Throwable cause) {
        super(cause);
    }
}
