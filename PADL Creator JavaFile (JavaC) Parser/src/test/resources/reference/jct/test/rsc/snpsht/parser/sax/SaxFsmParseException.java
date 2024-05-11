package jct.test.rsc.snpsht.parser.sax;
public class SaxFsmParseException
extends java.lang.Exception
{
final private static long serialVersionUID = 1585065250990190682;

private jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm failedSt;

public void <init>(jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm failedSt)
{
this.<init>();
this.failedSt = failedSt;

}

public void <init>(java.lang.String message, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm failedSt)
{
this.<init>(message);
this.failedSt = failedSt;

}

public jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm getFailedState()
{
return this.failedSt;

}


}
