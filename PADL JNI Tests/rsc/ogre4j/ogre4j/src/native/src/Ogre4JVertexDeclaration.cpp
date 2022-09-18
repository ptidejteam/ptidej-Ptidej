///////////////////////////////////////////////////////////////////////////////
/// This source file is part of ogre4j
///     (The JNI bindings for OGRE)
/// For the latest info, see http://www.ogre4j.org/
/// 
/// Copyright (c) 2005 netAllied GmbH, Tettnang
/// Also see acknowledgements in Readme.html
/// 
/// This program is free software; you can redistribute it and/or modify it under
/// the terms of the GNU Lesser General Public License as published by the Free Software
/// Foundation; either version 2 of the License, or (at your option) any later
/// version.
/// 
/// This program is distributed in the hope that it will be useful, but WITHOUT
/// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
/// FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
/// 
/// You should have received a copy of the GNU Lesser General Public License along with
/// this program; if not, write to the Free Software Foundation, Inc., 59 Temple
/// Place - Suite 330, Boston, MA 02111-1307, USA, or go to
/// http://www.gnu.org/copyleft/lesser.txt.
///
///
/// @file VertexDeclaration.cpp
/// Implementation of the JNI bindings for the usage of the 
/// Ogre::VertexDeclaration class
///
/// @author   Gerhard Maier <shinobi@ogre4j.org>
/// @date     10.05.2005
///
/// $Revision: 1.1 $
/// $Date: 2005/07/29 09:58:55 $
/// $Author: yavin02 $
///////////////////////////////////////////////////////////////////////////////
#include <Ogre4J.h>
#include <org_ogre4j_VertexDeclaration.h>


//==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==

JNIEXPORT jint JNICALL Java_org_ogre4j_VertexDeclaration_addElement
  (JNIEnv *env, jclass that, jint pInstance,
   jint source, jint offset, jint theType, jint semantic, jint index)
{
    Ogre::VertexDeclaration* decl = GET_PTR(Ogre::VertexDeclaration, pInstance);
    Ogre::VertexElement vElement = decl->addElement(
			source,
			offset,
            static_cast<Ogre::VertexElementType>(theType),
			static_cast<Ogre::VertexElementSemantic>(semantic),
			index);

    RETURN_PTR(&vElement);
}

//==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==~==

