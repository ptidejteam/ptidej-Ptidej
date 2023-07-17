/**
 * @author Mathieu Lemoine
 * @created 2009-07-10 (金)
 *
 * Licensed under 3-clause BSD License:
 * Copyright © 2009, Mathieu Lemoine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of Mathieu Lemoine nor the
 *    names of contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Mathieu Lemoine ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Mathieu Lemoine BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package jct.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jct.kernel.IJCTComment;
import jct.kernel.IJCTCompilationUnit;
import jct.kernel.JCTKind;
import jct.kernel.impl.JCTFileOffsetPath;

/**
 * This class extract the comments from a JCT with Stored Source Code information.
 */
public class JCTCommentExtractor
    extends JCTMap<Void, Void>
{
    private static Pattern commentStart = Pattern.compile("//|/\\*");
    private static Pattern multiLineCommentEnd = Pattern.compile("\\*/");
    private static Pattern endOfLineCommentEnd = Pattern.compile("(?m)$");

    private static int countLines(final String s)
    {
        final java.io.LineNumberReader r = new java.io.LineNumberReader(new java.io.StringReader(s));
        try { r.skip(s.length()); } catch(final java.io.IOException e) { throw new java.io.IOError(e); }
        return r.getLineNumber();
    }

    @Override
    public Void visitCompilationUnit(final IJCTCompilationUnit t, final Void p)
    {
        final String originalCode = t.getStoredSourceCode();
        if(null == originalCode)
            return null;

        int codeStart = 0;
        String code = originalCode;
        Matcher m = JCTCommentExtractor.commentStart.matcher(code);

        while(m.find())
        {
            if(JCTKind.STRING_LITERAL == new JCTFileOffsetPath(t.getSourceFile(), m.start()).walk(t.getRootNode()).getKind())
                continue;

            code = code.substring(m.start());
            codeStart += m.start();
            final Matcher end;
            final boolean endOfLineComment = '*' != code.charAt(1);

            if(!endOfLineComment)
            {
                end = JCTCommentExtractor.multiLineCommentEnd.matcher(code);
                if(!end.find())
                    throw new IllegalStateException("Multi-line comment is not closed in file " + t.getSourceFile() + " (opened on line : " + JCTCommentExtractor.countLines(originalCode.substring(0, codeStart + m.start())));
            }
            else
            {
                end = JCTCommentExtractor.endOfLineCommentEnd.matcher(code);
                end.find();
            }

            final IJCTComment c = t.getRootNode().getFactory().createComment(endOfLineComment, code.substring(2, end.start()));
            c.setStoredSourceCodeOffset(codeStart);
            c.setStoredSourceCodeLength(end.end());
            t.addComment(c);

            code = code.substring(end.end());
            codeStart += end.end();

            m = JCTCommentExtractor.commentStart.matcher(code);
        }

        return null;
    }
}
