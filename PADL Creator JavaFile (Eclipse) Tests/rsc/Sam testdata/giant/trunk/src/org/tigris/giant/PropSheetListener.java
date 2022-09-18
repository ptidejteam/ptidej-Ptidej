package org.tigris.giant;

import java.util.*;

import org.tigris.giant.model.*;
import org.tigris.gef.event.*;
import org.tigris.gef.presentation.*;


public class PropSheetListener implements GraphSelectionListener {

  public void selectionChanged(GraphSelectionEvent gse){
      Vector v = gse.getSelections();

      if( v.size() == 1 ){

          try{
              FigNode f = ( FigNode) v.get(0);
              if( f.getOwner() instanceof TargetNode ){
                TargetNode o = ( TargetNode ) f.getOwner();
                PSTable.setTargetNode( o );
              }
              else{}
          }
          catch( Exception e ){   }
      }
  }
}


