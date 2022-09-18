package jct.test.rsc.snpsht.parser.sax;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
public class DefaultUnknowMakerStateSaxFsm
extends jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm
{
private jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm callBackState;

private java.lang.String uri;

private java.lang.String localName;

private java.lang.String name;

private java.lang.String content;

private org.xml.sax.Attributes attributes;

public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm callBackState, org.xml.sax.Attributes attributes, java.lang.String localName, java.lang.String name, java.lang.String uri)
{
this.<init>(fsm, previewState);
this.attributes = new org.xml.sax.helpers.AttributesImpl(attributes);
this.callBackState = callBackState;
this.localName = localName;
this.name = name;
this.uri = uri;
this.content = "";

}

public jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm getCallBackState()
{
return this.callBackState;

}

public java.lang.String getUri()
{
return this.uri;

}

public java.lang.String getLocalName()
{
return this.localName;

}

public java.lang.String getName()
{
return this.name;

}

public java.lang.String getContent()
{
return this.content;

}

public org.xml.sax.Attributes getAttributes()
{
return this.attributes;

}

public void characters(char[] ch, int start, int length) throws org.xml.sax.SAXException
{
this.content += new java.lang.String(ch, start, length);

}

public void endElement(java.lang.String uri, java.lang.String localName, java.lang.String name) throws org.xml.sax.SAXException
{
if(this.name.compareTo(name.trim()) == 0) 
{
this.getFsm().changeState(this.callBackState);

}
 else 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.sax.DefaultErrorStateSaxFsm(this.getFsm(), this, "Misformed xml document.
End marker unexpected.
Received: " + name + "
Expected: " + this.name));

}

}

public void startElement(java.lang.String uri, java.lang.String localName, java.lang.String name, org.xml.sax.Attributes attributes) throws org.xml.sax.SAXException
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.sax.DefaultUnknowMakerStateSaxFsm(this.getFsm(), this, attributes, localName, name, uri));

}

public java.lang.String toString()
{
java.lang.String toRet = "<DefaultUnknowMakerStateSaxFsm>
" + "Unknow maker: <" + this.name;
if(this.attributes != null) 
{
for(int i = 0; i < this.attributes.getLength(); i ++) 
{
java.lang.String aName = this.attributes.getLocalName(i);
if("".equals(aName)) 
{
aName = this.attributes.getQName(i);

}
toRet += " " + aName + "=\"" + this.attributes.getValue(i) + "\"";

}

}
toRet += ">
Content: " + this.content.trim();
return toRet;

}


}
