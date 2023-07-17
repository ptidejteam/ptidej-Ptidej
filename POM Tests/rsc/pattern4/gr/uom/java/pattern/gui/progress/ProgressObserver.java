package gr.uom.java.pattern.gui.progress;

public class ProgressObserver {
    protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();

    public void addProgressListener(ProgressListener listener) {
        listenerList.add(ProgressListener.class, listener);
    }

    public void removeProgressEventListener(ProgressListener listener) {
        listenerList.remove(ProgressListener.class, listener);
    }

    public void fireDetectionFinishedEvent(DetectionFinishedEvent event) {
        Object[] listeners = listenerList.getListenerList();
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==ProgressListener.class) {
                ((ProgressListener)listeners[i+1]).detectionFinished(event);
            }
        }
    }
}
