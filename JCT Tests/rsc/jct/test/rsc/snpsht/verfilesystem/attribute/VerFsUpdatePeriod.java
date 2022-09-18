package jct.test.rsc.snpsht.verfilesystem.attribute;
import java.util.Date;
import jct.test.rsc.snpsht.utils.Pair;
public class VerFsUpdatePeriod
extends jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdateTime
implements jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsPeriod
{
private boolean startTimeAsDefault;

private jct.test.rsc.snpsht.utils.Pair period;

public void <init>(java.util.Date startTime, java.util.Date endTime)
{
this.<init>(startTime, endTime, true);

}

public void <init>(java.util.Date startTime, java.util.Date endTime, boolean startTimeAsDefault)
{
this.<init>(null);
this.period = new jct.test.rsc.snpsht.utils.Pair();
this.period.cdr(endTime);
this.period.car(startTime);
this.startTimeAsDefault = startTimeAsDefault;

}

public boolean isStartTimeAsDefault()
{
return this.startTimeAsDefault;

}

public java.util.Date getTime()
{
if((this.startTimeAsDefault && this.period.car() != null) || (! this.startTimeAsDefault && this.period.cdr() == null)) return this.period.car();
 else return this.period.cdr();

}

public java.util.Date getEndTime()
{
return this.period.cdr();

}

public java.util.Date getStartTime()
{
return this.period.car();

}

public jct.test.rsc.snpsht.utils.Pair getPeriod()
{
return this.period;

}


}
