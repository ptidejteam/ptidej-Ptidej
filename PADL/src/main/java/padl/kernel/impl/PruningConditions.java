package padl.kernel.impl;

import padl.kernel.IConstituent;
import padl.visitor.IPruningConditions;

// Simple pruning conditions for now, but can be made more as a composite in order to have "and" and "or" conditions
// as well as have conditions on different attributes.
public class PruningConditions implements IPruningConditions {

    private String displayNameContainsString = "";

    public PruningConditions(String displayNameContainsString) {
        this.displayNameContainsString = displayNameContainsString;
    }

    public boolean shouldBePruned(IConstituent constituent) {

        try {
            return displayNameContainsString != null
                && !displayNameContainsString.isEmpty()
                && !displayNameContainsString.isBlank()
                && constituent.getDisplayName().contains(displayNameContainsString);
        }
        catch (Exception ex) {
            return false;
        }
    }
}
