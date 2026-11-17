package padl.alternativeVisitor;

import padl.kernel.IConstituent;

/* An action done by the alternative visitor class */
public final class Action {
    public enum Kind {
        Open, Close, Visit;
    }
    private final Kind kind;
    private final IConstituent aConstituent;
    Action(final Kind kind, final IConstituent aConstituent) {
        this.kind = kind;
        this.aConstituent = aConstituent;
    }
    public Kind getKind() {return this.kind;}
    public IConstituent getConstituent() {return this.aConstituent;}
}
