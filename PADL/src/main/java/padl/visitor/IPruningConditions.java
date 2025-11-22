package padl.visitor;

import padl.kernel.IConstituent;

public interface IPruningConditions {

    boolean shouldBePruned(IConstituent constituent);
}
