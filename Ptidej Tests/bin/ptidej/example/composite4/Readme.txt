#-------------------------------------------------------------------------------
# Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the GNU Public License v2.0
# which accompanies this distribution, and is available at
# http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
# 
# Contributors:
#     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
#-------------------------------------------------------------------------------
A simple architecture that implements a distorted Composite design pattern.
The superclass Paragraph should not know its subclass IndentedParagraph.
The Element class plays the role of Component in the distorted Composite
design pattern, it should not know about the Paragraph class (playing the
role of Leaf).
The Title class plays the role of Leaf in the distorted Composite design
pattern, it should not know about classes Element and Document (playing
the roles of Component and Composite, respectively).
