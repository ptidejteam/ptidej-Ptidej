package jct.test.rsc.jct.util.misc;
import jct.test.rsc.jct.util.reference.StrongReference;
public class Counter
extends jct.test.rsc.jct.util.reference.StrongReference
{
public void <init>()
{
this.<init>((java.lang.Integer)null);

}

public void <init>(int start)
{
this.<init>(start);

}

public void reset()
{
this.set(0);

}

public void inc()
{
if(null != this.get()) this.set(this.get() + 1);

}

public java.lang.Integer postfixedInc()
{
final java.lang.Integer previousValue = this.get();
this.inc();
return previousValue;

}

public java.lang.Integer prefixedInc()
{
this.inc();
return this.get();

}


}
