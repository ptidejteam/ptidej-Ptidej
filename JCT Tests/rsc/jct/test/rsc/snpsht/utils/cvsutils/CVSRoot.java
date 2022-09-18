package jct.test.rsc.snpsht.utils.cvsutils;
import java.io.IOException;
import java.io.InputStream;
public class CVSRoot
{
private java.lang.String protocol;

private java.lang.String server;

private java.lang.String user;

private java.lang.String password;

private java.lang.String rootRepository;

private java.lang.String repository;

private java.lang.String separator = "/";

public void <init>(java.lang.String protocol, java.lang.String server, java.lang.String user, java.lang.String password, java.lang.String rootRepository, java.lang.String repository)
{
this.<init>();
this.setPassword(password);
this.setProtocol(protocol);
this.setCurrentRepository(repository);
this.setRootRepository(rootRepository);
this.setServer(server);
this.setUser(user);

}

public void <init>(java.lang.String protocol, java.lang.String server, java.lang.String rootRepository, java.lang.String repository)
{
this.<init>();
this.setProtocol(protocol);
this.setCurrentRepository(repository);
this.setRootRepository(rootRepository);
this.setServer(server);

}

public java.lang.String getProtocol()
{
return this.protocol;

}

public void setProtocol(java.lang.String protocol)
{
this.protocol = protocol;

}

public java.lang.String getServer()
{
return this.server;

}

public void setServer(java.lang.String server)
{
this.server = server;

}

public java.lang.String getUser()
{
return this.user;

}

public void setUser(java.lang.String user)
{
this.user = user;

}

public java.lang.String getPassword()
{
return this.password;

}

public void setPassword(java.lang.String password)
{
this.password = password;

}

public java.lang.String getRootRepository()
{
return this.rootRepository;

}

public void setRootRepository(java.lang.String rootRepository)
{
if(rootRepository.substring(rootRepository.length() - 1).compareTo(this.separator) == 0) 
{
this.rootRepository = rootRepository;

}
 else 
{
this.rootRepository = rootRepository + this.separator;

}

}

public java.lang.String getCurrentRepository()
{
return this.repository;

}

public void setCurrentRepository(java.lang.String repository)
{
this.repository = repository;

}

public java.lang.String getSeparator()
{
return this.separator;

}

public void setSeparator(java.lang.String separator)
{
this.separator = separator;

}

public java.lang.String getCVSROOT(boolean withPassword)
{
if(withPassword) return ":" + this.protocol + ":" + this.user + ":" + this.password + "@" + this.server + this.rootRepository + this.repository;
 else return ":" + this.protocol + ":" + this.user + "@" + this.server + this.rootRepository + this.repository;

}

public java.lang.String getCVSROOT()
{
return this.getCVSROOT(true);

}

public java.lang.String toString()
{
return ":" + this.protocol + ":" + this.user + ":<password>" + "@" + this.server + this.rootRepository + this.repository;

}

public boolean checkCVSROOT() throws java.io.IOException
{
boolean error = false;
int c;
java.io.InputStream is;
java.lang.Process p;
java.lang.Runtime rt = java.lang.Runtime.getRuntime();
p = rt.exec("cvs -d " + this.getCVSROOT() + " login");
java.lang.System.out.println("cvs -d " + this.toString() + " login");
is = p.getInputStream();
while((c = is.read()) != -1) 
{
java.lang.System.out.print((char)c);

}
is = p.getErrorStream();
if((c = is.read()) != -1) 
{
error = true;
do 
{
java.lang.System.err.print((char)c);

}
 while((c = is.read()) != -1);

}
is.close();
return ! error;

}


}
