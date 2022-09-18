package jct.test.rsc.snpsht.utils;
public class Pair
{
private java.lang.Object car;

private java.lang.Object cdr;

public void <init>()
{
this.<init>();

}

public void <init>(java.lang.Object car)
{
this.<init>();
this.car = car;

}

public void <init>(java.lang.Object car, java.lang.Object cdr)
{
this.<init>();
this.car = car;
this.cdr = cdr;

}

public void <init>(jct.test.rsc.snpsht.utils.Pair pair)
{
this.<init>();
this.copy(pair);

}

public java.lang.Object car()
{
return this.car;

}

public java.lang.Object cdr()
{
return this.cdr;

}

public void car(java.lang.Object car)
{
this.car = car;

}

public void cdr(java.lang.Object cdr)
{
this.cdr = cdr;

}

public void copy(jct.test.rsc.snpsht.utils.Pair pair)
{
this.car = this.car();
this.cdr = this.cdr();

}


}
