package jct.test.rsc.snpsht.utils;
public class Base64Coder
{
private static char[] map1 = new char[64];

final private static void <clinit>()
{
int i = 0;
for(char c = 'A'; c <= 'Z'; c ++) jct.test.rsc.snpsht.utils.Base64Coder.map1[i ++] = c;
for(char c = 'a'; c <= 'z'; c ++) jct.test.rsc.snpsht.utils.Base64Coder.map1[i ++] = c;
for(char c = '0'; c <= '9'; c ++) jct.test.rsc.snpsht.utils.Base64Coder.map1[i ++] = c;
jct.test.rsc.snpsht.utils.Base64Coder.map1[i ++] = '+';
jct.test.rsc.snpsht.utils.Base64Coder.map1[i ++] = '/';

}

private static byte[] map2 = new byte[128];

final private static void <clinit>()
{
for(int i = 0; i < jct.test.rsc.snpsht.utils.Base64Coder.map2.length; i ++) jct.test.rsc.snpsht.utils.Base64Coder.map2[i] = -1;
for(int i = 0; i < 64; i ++) jct.test.rsc.snpsht.utils.Base64Coder.map2[jct.test.rsc.snpsht.utils.Base64Coder.map1[i]] = (byte)i;

}

public static java.lang.String encodeString(java.lang.String s)
{
return new java.lang.String(jct.test.rsc.snpsht.utils.Base64Coder.encode(s.getBytes()));

}

public static char[] encode(byte[] in)
{
return jct.test.rsc.snpsht.utils.Base64Coder.encode(in, in.length);

}

public static char[] encode(byte[] in, int iLen)
{
int oDataLen = (iLen * 4 + 2) / 3;
int oLen = ((iLen + 2) / 3) * 4;
char[] out = new char[oLen];
int ip = 0;
int op = 0;
while(ip < iLen) 
{
int i0 = in[ip ++] & 255;
int i1 = ip < iLen ? in[ip ++] & 255 : 0;
int i2 = ip < iLen ? in[ip ++] & 255 : 0;
int o0 = i0 >>> 2;
int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
int o2 = ((i1 & 15) << 2) | (i2 >>> 6);
int o3 = i2 & 63;
out[op ++] = jct.test.rsc.snpsht.utils.Base64Coder.map1[o0];
out[op ++] = jct.test.rsc.snpsht.utils.Base64Coder.map1[o1];
out[op] = op < oDataLen ? jct.test.rsc.snpsht.utils.Base64Coder.map1[o2] : '=';
op ++;
out[op] = op < oDataLen ? jct.test.rsc.snpsht.utils.Base64Coder.map1[o3] : '=';
op ++;

}
return out;

}

public static java.lang.String decodeString(java.lang.String s)
{
return new java.lang.String(jct.test.rsc.snpsht.utils.Base64Coder.decode(s));

}

public static byte[] decode(java.lang.String s)
{
return jct.test.rsc.snpsht.utils.Base64Coder.decode(s.toCharArray());

}

public static byte[] decode(char[] in)
{
int iLen = in.length;
if(iLen % 4 != 0) throw new java.lang.IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
while(iLen > 0 && in[iLen - 1] == '=') iLen --;
int oLen = (iLen * 3) / 4;
byte[] out = new byte[oLen];
int ip = 0;
int op = 0;
while(ip < iLen) 
{
int i0 = in[ip ++];
int i1 = in[ip ++];
int i2 = ip < iLen ? in[ip ++] : 'A';
int i3 = ip < iLen ? in[ip ++] : 'A';
if(i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127) throw new java.lang.IllegalArgumentException("Illegal character in Base64 encoded data.");
int b0 = jct.test.rsc.snpsht.utils.Base64Coder.map2[i0];
int b1 = jct.test.rsc.snpsht.utils.Base64Coder.map2[i1];
int b2 = jct.test.rsc.snpsht.utils.Base64Coder.map2[i2];
int b3 = jct.test.rsc.snpsht.utils.Base64Coder.map2[i3];
if(b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0) throw new java.lang.IllegalArgumentException("Illegal character in Base64 encoded data.");
int o0 = (b0 << 2) | (b1 >>> 4);
int o1 = ((b1 & 15) << 4) | (b2 >>> 2);
int o2 = ((b2 & 3) << 6) | b3;
out[op ++] = (byte)o0;
if(op < oLen) out[op ++] = (byte)o1;
if(op < oLen) out[op ++] = (byte)o2;

}
return out;

}

private void <init>()
{
this.<init>();

}


}
