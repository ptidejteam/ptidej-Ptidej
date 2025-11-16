package padl.visitor;

import padl.kernel.IConstituent;

public interface IFilter {
    boolean accept(IConstituent constituent);
}
