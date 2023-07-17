// *************************************************************************
// *                                                                       *
// * PaLM(C):   an object-oriented explanation-based constraint            *
// *	        propagation kernel written in choco                        *
// *    Copyright (C) N. Jussien, 01/1998-02/2002                          *
// *                                                                       *
// * This program is free software; you can redistribute it and/or         *
// * modify it under the terms of the GNU General Public License           *
// * as published by the Free Software Foundation; either version 2        *
// * of the License, or (at your option) any later version.                *
// *                                                                       *
// * This program is distributed in the hope that it will be useful,       *
// * but WITHOUT ANY WARRANTY; without even the implied warranty of        *
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
// * GNU General Public License for more details.                          *
// *                                                                       *
// * you should have received a copy of the GNU General Public License     *
// * along with this program; if not, write to the Free Software           *
// * Foundation, Inc., 59 Temple Place - Suite 330,                        *
// * Boston, MA  02111-1307, USA.                                          *
// *                                                                       *
// * to contact the author, send a mail to Narendra.Jussien@emn.fr         *
// *                                                                       *
// * Redistribution and use in source and binary forms are permitted       *
// * provided that source distribution retains this entire copyright       *
// * notice and comments.                                                  *
// *                                                                       *
// *************************************************************************

// version 1.324 - Dec. 1, 2002

// ------------------  Library Overview  -----------------------------------
// *  readme.txt       : library overview                                  *
// *  license.txt      : the license                                       *
// *  p-namespace.cl   : making a transparent connection with choco/ice    *
// *  p-model.cl       : the object-oriented basis and global tools        *
// *  p-domain.cl      : handling lazy enumerated domains                  *
// *  p-explain.cl     : explanation related tools and interface           *
// *  p-variables.cl   : variable definitions for PALM                     *
// *  p-constraints.cl : arithmetic constraint definitions for PALM        *
// *  p-ac4.cl         : constraints in extension à la AC4                 *
// *  p-symbolic.cl    : some symbolic constraints for PALM                *
// *  p-bool.cl        : handling boolean connectors as constraints        *
// *  p-solve.cl       : solving problems in e-cp                          *
// *  palmapi.cl       : application programmer interface (public methods) *
// -------------------------------------------------------------------------


// Detailed contents

// ** file: p-model.cl
// * Part 1 : Release information (version number, release date, ...)
// * Part 2 : Object hierarchy (storing explanation and specific tools)
// * Part 3 : Global parameters 

// ** file: p-domain.cl
// * Part 1 : Tools 
// * Part 2 : API
// * Part 3 : Interface for propagation

// ** file: p-explain.cl 
// * Part 1 : Set operations ... 
// * Part 2 : New events (tools and API)                      
// * Part 3 : Bound explanations 
// * Part 4 : Value Removal explanations
// * Part 5 : Initialising the PalmEngine ...
// * Part 6 : Flushing and resetting event Queues upon contradiction
// * Part 7 : Handling contradictions
// * Part 8 : Removing constraints
// * Part 9 : Explanation generation API 

// ** file: p-variables.cl 
// * Part 1 : Tools and basic stuff 
// * Part 2 : Data Structure maintenance (generic methods)
// * Part 3 : Domain restoration
// * Part 4 : Domain modification

// ** file: p-constraints.cl
// * Part 1  : Storing specific information within constraints
// * Part 2  : Basic tools for AbstractConstraints                    
// * Part 3  : (Re|dis)connection to(from) the constraint network
// * Part 4  : Unary constraints (general purpose methods)
// * Part 5  : Binary constraints (general purpose methods)
// * Part 6  : Large constraints (general purpose methods)
// * Part 7  : Basic unary constraint (propagation)
//             PalmGreaterOrEqualxc, PalmLessOrEqualxc, PalmNotEqualxc, PalmEqualxc
// * Part 8  : Basic binary constraint (propagation)
//	  	       PalmNotEqualxyc, PalmEqualxyc, PalmGreaterOrEqualxyc, PalmLessOrEqualxyc 
// * Part 9  : Linear combination of variables (PalmLinComb)
// * Part 10 : Handling delayers within PaLMchoco

// ** file: p-ac4.cl
// * Part 1 : Constraint declaration                         
// * Part 2 : API                                            
// * Part 3 : Propagation                                    
// * Part 4 : Data structure management                      

// ** file: p-symbolic.cl    
// * Part 1 : Complete all-diff (Régin implementation ...)
// * Part 2 : Permutation constraint (dual representation of variables and values)
//            Permutation(x,y) <=> (x_i = j <=> y_j = i)
// * Part 3 : GCC constraint
// * Part 4 : GCCVar constraint
// * Part 5 : Occur (nb of occurrence of a value (possibly an IntVar) in a list of vars
 	      a tool for atleast, atmost, ...
// * Part 6 : Element (correspondance between variables and indices in a list)

// ** file: p-bool.cl
// * Part 1 : OR : PalmDisjunction
// * Part 2 : IFTHEN : PalmGuard 
// * Part 3 : IFF : PalmEquiv
// * Part 4 : AND : PalmConjunction
// * Part 5 : Large AND : PalmLargeConjunction
// * Part 6 : Large OR : PalmLargeDisjunction
// * Part 7 : Cardinality : PalmCardinality

// ** file: p-solve.cl 
// * Part 1 : Runtime statistics          
// * Part 2 : palmSolver initialisation
// * Part 3 : Solving Problems                    
// * Part 4 : Extending a solution                 
// * Part 5 : Repairing a solution                 
// * Part 6 : Maintaining the search state          
// * Part 7 : Learning from a contradiction        
// * Part 8 : Choosing a constraint to undo        
// * Part 9 : Default extension                    
// * Part 10: Variable selection tools             
// * Part 11: Solution management tools            
// * Part 12: A generic learner for Path Repair   

// ** file: palmapi.cl
// * Part 1  : Problems                
// * Part 2  : Variables
// * Part 3  : Posting constraints                             
// * Part 4  : Removing constraints                             
// * Part 5  : Propagating and solving problems
// * Part 6  : Assignment constraints
// * Part 7  : Negating constraints
// * Part 8  : arithmetic constraints                         
// * Part 9  : global constraints 
// * Part 10 : boolean connectors 
// * Part 11 : user-friendly tools                           


