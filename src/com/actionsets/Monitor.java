package com.actionsets;

/**
 */

import java.util.Date;
import java.util.Iterator;

public class Monitor implements Runnable {
    private Cluster cluster;
    private MonitorLog monitorLog;

    public Monitor(Cluster cluster, MonitorLog monitorLog) {
        this.cluster = cluster;
        this.monitorLog = monitorLog;
    }

    @Override
    public void run() {
        Iterator<Node> nodes = cluster.clusterNodes.iterator();
        while (nodes.hasNext()) {
            Node node = nodes.next();
            this.monitorLog.addEntry(new MonitorLogEntry(node.getNode_name(), node.getIp_address(), new Date(), node.getCurrentLoad()));
        }
    }
}
