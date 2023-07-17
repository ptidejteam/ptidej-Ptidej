package gr.uom.java.pattern.gui.progress;

import gr.uom.java.pattern.PatternInstance;

import java.util.Vector;

public class PatternDetectionSource {

    private String patternName;
    private Vector<PatternInstance> patternInstanceVector;

    public PatternDetectionSource(String patternName, Vector<PatternInstance> patternInstanceVector) {
        this.patternName = patternName;
        this.patternInstanceVector = patternInstanceVector;
    }

    public String getPatternName() {
        return patternName;
    }

    public Vector<PatternInstance> getPatternInstanceVector() {
        return patternInstanceVector;
    }

}
