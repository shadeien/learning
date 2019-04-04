package com.shadeien.concurrent;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashSet;

public class CleanupFinalizer extends WeakReference {

    private static ReferenceQueue<CleanupFinalizer> finalizerReferenceQueue;
    private static HashSet<CleanupFinalizer> pendingRefs = new HashSet<>();

    private boolean closed = false;

    public CleanupFinalizer(Object referent) {
        super(referent, finalizerReferenceQueue);
        allocateNative();
        pendingRefs.add(this);
    }

    public void setClosed() {
        closed = true;
        doNativeCleanup();
    }

    public void cleanup() {
        if (!closed) {
            doNativeCleanup();
        }
    }

    private native void allocateNative();
    private native void doNativeCleanup();

    static {
        finalizerReferenceQueue = new ReferenceQueue<>();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                CleanupFinalizer fr;
                while (true) {
                    try {
                        fr = (CleanupFinalizer) finalizerReferenceQueue.remove();
                        fr.cleanup();
                        pendingRefs.remove(fr);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }
}
