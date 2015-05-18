package com.actionsets;

// Obviously there are a lot of things missing, primarily error checking and exception handling.
// Many decisions were made for expedience.
// TODO Maybe someday...
// Maybe use a framework (Spring?)
// Interfaces for easier maintenance and flexibility
// Live monitoring of something
// Add exception handling everywhere
// Make the int for node-load a long everywhere

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        int nodesInCluster = 5;

        // Create the cluster and nodes
        String clusterName = "primary";
        int nodeNum;
        Cluster cluster = new Cluster(clusterName);
        MonitorLog monitorLog = new MonitorLog();
        for (nodeNum = 0; nodeNum < nodesInCluster; nodeNum++) {
            // Add one to the loop index for the sake of mocking up a nice IP & name
            cluster.addNode(new Node(String.format("10.0.0.%1$d", nodeNum + 1), String.format("Node-%1$d", nodeNum + 1)));
        }

        // Start the monitor thread
        // It needs to know what cluster to monitor and where the log is
        // Executor & Schedule
        ScheduledExecutorService monitorSchedule = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> monitor = monitorSchedule.scheduleAtFixedRate(new Monitor(cluster, monitorLog), 0, 1, TimeUnit.SECONDS);

        cluster.refreshNodeLoads();
        cluster.dump();

        cluster.balance();
        cluster.dump();

        cluster.refreshNodeLoads();
        cluster.dump();

        cluster.balance();
        cluster.dump();

        // Stop the monitor repeat schedule
        monitorSchedule.shutdown();

        // Output the log.
        monitorLog.dump();

        // Enable breakpoint to stop debugger just before finishing
        int nothing = 0;
    }

}
