package com.actionsets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A simple class to contain the log of monitor checks
 */
public class MonitorLog {
    private List<MonitorLogEntry> log;

    public MonitorLog() {
        // Maybe not necessary since in this case we shutdown before the dump,
        // but in general would allow us to safely dump() while still collecting records
        this.log = Collections.synchronizedList(new ArrayList<>());
    }

    public void addEntry(MonitorLogEntry mle) {
        this.log.add(mle);
    }

    public void dump() {
        System.out.println("Monitor Log:");
        synchronized (this.log) {
            Iterator<MonitorLogEntry> entries = this.log.listIterator();
            while (entries.hasNext()) {
                MonitorLogEntry entry = entries.next();
                System.out.println(entry.toString());
            }
        }
    }
}
