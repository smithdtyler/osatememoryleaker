package com.example.osate.leaker.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class SampleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
	    // I suspect that this is our problem.
	    // Let's try ot break everything.
	    for (int i = 0; i < 1000; i++) {
	      System.out.println("Build whole workspace iteration: " + i);
	      try {
	        ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.FULL_BUILD,
	            new NullProgressMonitor());
	      } catch (CoreException e) {
	        System.out.println("Failed to complete workspace build before system instantiation.");
	      }
	      try {
	        if (i % 10 == 0) {
	          Thread.sleep(1000);
	        } else {
	          Thread.sleep(50);
	        }
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	    }
		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(
				window.getShell(),
				"Leaker",
				"Hello, Eclipse world");
		return null;
	}
}
