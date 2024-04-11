/** 
 * Copyright (c) 2004, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * IBM Corporation - initial API and implementation
 */
package org.eclipse.core.internal.localstore;
import java.io.InputStream;
import java.util.*;
import org.eclipse.core.filesystem.*;
import org.eclipse.core.internal.localstore.Bucket.Entry;
import org.eclipse.core.internal.localstore.HistoryBucket.HistoryEntry;
import org.eclipse.core.internal.resources.*;
import org.eclipse.core.internal.utils.*;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
public class HistoryStore2 implements IHistoryStore {
class HistoryCopyVisitor extends Bucket.Visitor {
    private List changes=new ArrayList();
    private IPath destination;
    private IPath source;
    public HistoryCopyVisitor(    IPath source,    IPath destination){
      this.source=source;
      this.destination=destination;
    }
    public void afterSaving(    Bucket bucket) throws CoreException {
      saveChanges();
      changes.clear();
    }
    private void saveChanges() throws CoreException {
      if (changes.isEmpty())       return;
      Iterator i=changes.iterator();
      HistoryEntry entry=(HistoryEntry)i.next();
      tree.loadBucketFor(entry.getPath());
      HistoryBucket bucket=(HistoryBucket)tree.getCurrent();
      bucket.addBlobs(entry);
      while (i.hasNext())       bucket.addBlobs((HistoryEntry)i.next());
      bucket.save();
    }
    public int visit(    Entry sourceEntry){
      IPath destinationPath=destination.append(sourceEntry.getPath().removeFirstSegments(source.segmentCount()));
      HistoryEntry destinationEntry=new HistoryEntry(destinationPath,(HistoryEntry)sourceEntry);
      changes.add(destinationEntry);
      return CONTINUE;
    }
  }
  private BlobStore blobStore;
  private Set blobsToRemove=new HashSet();
  final BucketTree tree;
  private Workspace workspace;
  public HistoryStore2(  Workspace workspace,  IFileStore store,  int limit){
    this.workspace=workspace;
    try {
      store.mkdir(EFS.NONE,null);
    }
 catch (    CoreException e) {
    }
    this.blobStore=new BlobStore(store,limit);
    this.tree=new BucketTree(workspace,new HistoryBucket());
  }
  /** 
 * @see IHistoryStore#addState(IPath,IFileStore,IFileInfo,boolean)
 */
  public synchronized IFileState addState(  IPath key,  IFileStore localFile,  IFileInfo info,  boolean moveContents){
    long lastModified=info.getLastModified();
    if (Policy.DEBUG_HISTORY)     System.out.println("History: Adding state for key: " + key + ", file: "+ localFile+ ", timestamp: "+ lastModified+ ", size: "+ localFile.fetchInfo().getLength());
    if (!isValid(localFile,info))     return null;
    UniversalUniqueIdentifier uuid=null;
    try {
      uuid=blobStore.addBlob(localFile,moveContents);
      tree.loadBucketFor(key);
      HistoryBucket currentBucket=(HistoryBucket)tree.getCurrent();
      currentBucket.addBlob(key,uuid,lastModified);
    }
 catch (    CoreException e) {
      log(e);
    }
    return new FileState(this,key,lastModified,uuid);
  }
  public synchronized Set allFiles(  IPath root,  int depth,  IProgressMonitor monitor){
    final Set allFiles=new HashSet();
    try {
      tree.accept(new Bucket.Visitor(){
        public int visit(        Entry fileEntry){
          allFiles.add(fileEntry.getPath());
          return CONTINUE;
        }
      }
,root,depth == IResource.DEPTH_INFINITE ? BucketTree.DEPTH_INFINITE : depth);
    }
 catch (    CoreException e) {
      log(e);
    }
    return allFiles;
  }
  /** 
 * Applies the clean-up policy to an entry.
 */
  protected void applyPolicy(  HistoryEntry fileEntry,  int maxStates,  long minTimeStamp){
    for (int i=0; i < fileEntry.getOccurrences(); i++) {
      if (i < maxStates && fileEntry.getTimestamp(i) >= minTimeStamp)       continue;
      blobsToRemove.add(fileEntry.getUUID(i));
      fileEntry.deleteOccurrence(i);
    }
  }
  /** 
 * Applies the clean-up policy to a subtree.
 */
  private void applyPolicy(  IPath root) throws CoreException {
    IWorkspaceDescription description=workspace.internalGetDescription();
    final long minimumTimestamp=System.currentTimeMillis() - description.getFileStateLongevity();
    final int maxStates=description.getMaxFileStates();
    tree.accept(new Bucket.Visitor(){
      public int visit(      Entry entry){
        applyPolicy((HistoryEntry)entry,maxStates,minimumTimestamp);
        return CONTINUE;
      }
    }
,root,BucketTree.DEPTH_INFINITE);
    tree.getCurrent().save();
  }
  public synchronized void clean(  final IProgressMonitor monitor){
    long start=System.currentTimeMillis();
    try {
      monitor.beginTask(Messages.resources_pruningHistory,IProgressMonitor.UNKNOWN);
      IWorkspaceDescription description=workspace.internalGetDescription();
      final long minimumTimestamp=System.currentTimeMillis() - description.getFileStateLongevity();
      final int maxStates=description.getMaxFileStates();
      final int[] entryCount=new int[1];
      tree.accept(new Bucket.Visitor(){
        public int visit(        Entry fileEntry){
          if (monitor.isCanceled())           return STOP;
          entryCount[0]+=fileEntry.getOccurrences();
          applyPolicy((HistoryEntry)fileEntry,maxStates,minimumTimestamp);
          removeUnreferencedBlobs(100);
          return monitor.isCanceled() ? STOP : CONTINUE;
        }
      }
,Path.ROOT,BucketTree.DEPTH_INFINITE);
      if (Policy.DEBUG_HISTORY) {
        Policy.debug("Time to apply history store policies: " + (System.currentTimeMillis() - start) + "ms.");
        Policy.debug("Total number of history store entries: " + entryCount[0]);
      }
      removeUnreferencedBlobs(0);
    }
 catch (    Exception e) {
      String message=Messages.history_problemsCleaning;
      ResourceStatus status=new ResourceStatus(IResourceStatus.FAILED_DELETE_LOCAL,null,message,e);
      Policy.log(status);
    }
 finally {
      monitor.done();
    }
  }
  void removeUnreferencedBlobs(  int limit){
    if (limit <= 0 || limit <= blobsToRemove.size()) {
      long start=System.currentTimeMillis();
      blobStore.deleteBlobs(blobsToRemove);
      if (Policy.DEBUG_HISTORY)       Policy.debug("Time to remove " + blobsToRemove.size() + " unreferenced blobs: "+ (System.currentTimeMillis() - start)+ "ms.");
      blobsToRemove=new HashSet();
    }
  }
  public void closeHistoryStore(  IResource resource){
    try {
      tree.getCurrent().save();
      tree.getCurrent().flush();
    }
 catch (    CoreException e) {
      log(e);
    }
  }
  public synchronized void copyHistory(  IResource sourceResource,  IResource destinationResource,  boolean moving){
    if (sourceResource == null || destinationResource == null) {
      String message=Messages.history_copyToNull;
      ResourceStatus status=new ResourceStatus(IResourceStatus.INTERNAL_ERROR,null,message,null);
      Policy.log(status);
      return;
    }
    if (sourceResource.equals(destinationResource)) {
      String message=Messages.history_copyToSelf;
      ResourceStatus status=new ResourceStatus(IResourceStatus.INTERNAL_ERROR,sourceResource.getFullPath(),message,null);
      Policy.log(status);
      return;
    }
    final IPath source=sourceResource.getFullPath();
    final IPath destination=destinationResource.getFullPath();
    Assert.isLegal(source.segmentCount() > 0);
    Assert.isLegal(destination.segmentCount() > 0);
    Assert.isLegal(source.segmentCount() > 1 || destination.segmentCount() == 1);
    try {
      if (moving && sourceResource.getType() == IResource.PROJECT) {
        final Bucket bucket=tree.getCurrent();
        bucket.save();
        bucket.flush();
        return;
      }
      HistoryCopyVisitor copyVisitor=new HistoryCopyVisitor(source,destination);
      tree.accept(copyVisitor,source,BucketTree.DEPTH_INFINITE);
      applyPolicy(destinationResource.getFullPath());
    }
 catch (    CoreException e) {
      log(e);
    }
  }
  public boolean exists(  IFileState target){
    return blobStore.fileFor(((FileState)target).getUUID()).fetchInfo().exists();
  }
  public InputStream getContents(  IFileState target) throws CoreException {
    if (!target.exists()) {
      String message=Messages.history_notValid;
      throw new ResourceException(IResourceStatus.FAILED_READ_LOCAL,target.getFullPath(),message,null);
    }
    return blobStore.getBlob(((FileState)target).getUUID());
  }
  public synchronized IFileState[] getStates(  IPath filePath,  IProgressMonitor monitor){
    try {
      tree.loadBucketFor(filePath);
      HistoryBucket currentBucket=(HistoryBucket)tree.getCurrent();
      HistoryEntry fileEntry=currentBucket.getEntry(filePath);
      if (fileEntry == null || fileEntry.isEmpty())       return new IFileState[0];
      IFileState[] states=new IFileState[fileEntry.getOccurrences()];
      for (int i=0; i < states.length; i++)       states[i]=new FileState(this,fileEntry.getPath(),fileEntry.getTimestamp(i),fileEntry.getUUID(i));
      return states;
    }
 catch (    CoreException ce) {
      log(ce);
      return new IFileState[0];
    }
  }
  public BucketTree getTree(){
    return tree;
  }
  /** 
 * Return a boolean value indicating whether or not the given file
 * should be added to the history store based on the current history
 * store policies.
 * @param localFile the file to check
 * @return <code>true</code> if this file should be added to the history
 * store and <code>false</code> otherwise
 */
  private boolean isValid(  IFileStore localFile,  IFileInfo info){
    WorkspaceDescription description=workspace.internalGetDescription();
    long length=info.getLength();
    boolean result=length <= description.getMaxFileStateSize();
    if (Policy.DEBUG_HISTORY && !...