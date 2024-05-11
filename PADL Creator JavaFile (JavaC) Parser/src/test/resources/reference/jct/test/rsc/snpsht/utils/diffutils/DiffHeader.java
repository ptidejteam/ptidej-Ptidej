package jct.test.rsc.snpsht.utils.diffutils;
public class DiffHeader
{
private this.Range oldRevisionRange;

private this.Range newRevisionRange;

public void <init>(this.Range sourceRange, this.Range patchRange)
{
this.<init>();
this.newRevisionRange = patchRange;
this.oldRevisionRange = sourceRange;

}

public void <init>(int oldRevisionStartLine, int oldRevisionNumberOfLines, int newRevisionStartLine, int newRevisionNumberOfLines)
{
this.<init>();
this.newRevisionRange = new this.Range(newRevisionStartLine, newRevisionNumberOfLines);
this.oldRevisionRange = new this.Range(oldRevisionStartLine, oldRevisionNumberOfLines);

}

public void setOlRevisionRange(this.Range sourceRange)
{
this.oldRevisionRange = sourceRange;

}

public this.Range getOldRevisionRange()
{
return this.oldRevisionRange;

}

public void setNewRevisionRange(this.Range patchRange)
{
this.newRevisionRange = patchRange;

}

public this.Range getNewRevisionRange()
{
return this.newRevisionRange;

}

public class Range
{
private int startLine;

private int numberOfLines;

public void <init>(int startLine, int numberOfLines)
{
this.<init>();
this.numberOfLines = numberOfLines;
this.startLine = startLine;

}

public void setStartLine(int startLine)
{
this.startLine = startLine;

}

public int getStartLine()
{
return this.startLine;

}

public void setNumberOfLines(int numberOfLines)
{
this.numberOfLines = numberOfLines;

}

public int getNumberOfLines()
{
return this.numberOfLines;

}


}


}
