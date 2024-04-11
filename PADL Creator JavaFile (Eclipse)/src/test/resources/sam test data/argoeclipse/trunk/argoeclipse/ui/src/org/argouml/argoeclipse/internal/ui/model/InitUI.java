// $Id: InitUI.java 0.1.1 2006/09/15 00:00:00 b00__1 Exp $
// Copyright (c) 2006 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies. This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason. IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.argoeclipse.internal.ui.model;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.WorkbenchJob;

import org.argouml.application.api.ProgressMonitor;
import org.argouml.argoeclipse.internal.core.model.Init;
import org.argouml.argoeclipse.internal.core.model.ModelMessages;
import org.argouml.argoeclipse.internal.ui.Activator;
import org.argouml.argoeclipse.internal.ui.util.InformationDialog;
import org.argouml.persistence.ProgressEvent;

/**
 * Calls the Init from argoeclipse-core with additional UI stuff.
 *
 * @author Bogdan Pistol
 */
public class InitUI {
    
    private static boolean initialized = false;
    
    /**
     * Tests the initializer status.
     * @return true if it's initialized or false otherwise
     */
    public static boolean isInitialized() {
        return initialized;
    }

    /**
     * Starts the
     * {@link org.argouml.argoeclipse.internal.core.model.Init#initialize(
     *          ProgressMonitor)}
     * with additional GUI stuff.
     */
    public static void initialize() {
        if (initialized) {
            return;
        }
        final boolean fork = PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow().getActivePage() != null;
        
        Display.getDefault().syncExec(new Runnable() {

            public void run() {
                try {
                    Activator.getDefault().getWorkbench()
                        .getProgressService().run(fork, false,
                            getRunnableWithProgress());
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        
        // Create an Eclipse system job for the critics and schedule it
        WorkbenchJob job = new WorkbenchJob(ModelMessages.argoCritics) {
            public IStatus runInUIThread(IProgressMonitor monitor) {
                Init.initCritics();
                return Status.OK_STATUS;
            }
        };
        job.setSystem(true);
        job.schedule();
        
        initialized = true;
    }
    
    private static IRunnableWithProgress getRunnableWithProgress() {
        return new IRunnableWithProgress() {

            private int currentProgress;

            public void run(final IProgressMonitor monitor)
                throws InvocationTargetException, InterruptedException {
                
                Init.initialize(new ProgressMonitor() {

                    public void notifyMessage(final String title,
                            final String introduction,
                            final String problem) {
                        Display.getDefault().syncExec(new Runnable() {

                            public void run() {
                                new InformationDialog(
                                        Activator.getDefault().getWorkbench()
                                        .getActiveWorkbenchWindow().getShell(),
                                        title, introduction, problem).open();
                            }
                            
                        });
                    }

                    public void setMaximumProgress(int max) {
                        monitor.beginTask("", max); //$NON-NLS-1$
                    }

                    public void updateProgress(int progress) {
                        monitor.worked(progress - currentProgress);
                        currentProgress = progress;
                    }

                    public void progress(ProgressEvent event)
                            throws InterruptedException {
                        updateProgress((int) event.getPosition());
                    }

                    public void updateSubTask(String action) {
                        monitor.subTask(action);
                    }

                    public boolean isCanceled() {
                        return monitor.isCanceled();
                    }

                    public void updateMainTask(String task) {
                        monitor.setTaskName(task);
                    }

                    public void notifyNullAction() {
                    }

                    public void close() {
                        monitor.done();
                    }
                });
                monitor.done();
            }

        };
    }
    
}
