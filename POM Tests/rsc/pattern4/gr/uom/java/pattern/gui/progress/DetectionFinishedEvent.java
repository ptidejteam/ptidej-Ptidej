package gr.uom.java.pattern.gui.progress;

import gr.uom.java.pattern.PatternInstance;

import java.util.EventObject;
import java.util.Map;
import java.util.Vector;

public class DetectionFinishedEvent extends EventObject {
    
    public DetectionFinishedEvent(PatternDetectionSource source) {
        super(source);
    }
}
