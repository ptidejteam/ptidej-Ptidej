package padl.analysis.selective;

import padl.kernel.IClass;
import padl.kernel.IConstituent;
import padl.visitor.IFilter;

public class ServiceClassFilter implements IFilter{

    @Override
    public boolean accept(IConstituent c) {
        if(c instanceof IClass aClass) {
            String name = aClass.getDisplayName();
            return name != null && !name.contains("Main");
        }
        return true;
    }
}
