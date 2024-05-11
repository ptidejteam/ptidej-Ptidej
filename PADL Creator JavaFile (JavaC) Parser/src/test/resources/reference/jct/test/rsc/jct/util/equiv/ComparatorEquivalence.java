package jct.test.rsc.jct.util.equiv;
import java.util.Comparator;
final public class ComparatorEquivalence
implements jct.test.rsc.jct.util.equiv.Equivalence
{
final public java.util.Comparator comparator;

public void <init>(java.util.Comparator comparator)
{
this.<init>();
this.comparator = comparator;

}

public boolean areEquivalent(java.lang.Object o1, java.lang.Object o2)
{
return 0 == this.comparator.compare(o1, o2);

}


}
