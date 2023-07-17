package net.intensicode.idea;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;



/**
 * TODO: Describe this!
 */
public final class ActionReload extends AnAction
{
    public final void actionPerformed( final AnActionEvent aEvent )
    {
        final Application application = ApplicationManager.getApplication();
        final SimpleSyntax simpleSyntax = ( SimpleSyntax ) application.getComponent( SIMPLE_SYNTAX );
        simpleSyntax.disposeComponent();
        simpleSyntax.initComponent();
    }



    private static final String SIMPLE_SYNTAX = "net.intensicode.idea.SimpleSyntax";
}
