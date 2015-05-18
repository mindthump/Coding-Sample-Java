package com.actionsets;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a cluster of nodes.
 */

public class Cluster {
    // I normally want everything private, but I want the monitor to get to the nodes directly
    protected List<Node> clusterNodes;
    private String clusterName;

    public Cluster(String clusterName) {
        this.clusterNodes = new ArrayList<>();
        this.clusterName = clusterName;
    }

    public void addNode(Node newNode) {
        clusterNodes.add(newNode);
    }

    public void refreshNodeLoads() {
        Iterator<Node> nodes = this.clusterNodes.iterator();
        System.out.println("Refreshing nodes...");
        while (nodes.hasNext()) {
            Node node = nodes.next();
            // A new (random) value
            node.refreshNodeLoad();
        }
        System.out.println("Refresh complete.\n");
    }

    public void balance() {
        // TODO better exceptions
        // TODO better algorithm
        // This is exceptionally brute force, just for the sake of completing this exercise.
        // Algorithm: Move load items from the largest load to the smallest load until the smallest
        // load is equal to the average load, and the largest load is equal to the average load + 1.
        // There may be more than one smallest or largest, but any of them will do.
        System.out.println("Balancing...");
        int averageLoad = getNodeLoadAverage();
        NodeLoadInfo nodeLoadInfo = getNodeLoadInfo();
        while ((nodeLoadInfo.smallestNode.getCurrentLoad() < averageLoad) || (nodeLoadInfo.biggestNode.getCurrentLoad() > averageLoad + 1)) {
            // Just in case there is nothing to move - all loads are zero
            if (nodeLoadInfo.smallestNode.getCurrentLoad() == 0 && nodeLoadInfo.biggestNode.getCurrentLoad() == 0) {
                return;
            }
            // This increment/decrement might be done inside Node,
            // but moving actual load items around would probably be done here.
            System.out.println(String.format("Moving item from node %1$s (%2$d) to node %3$s (%4$d).", nodeLoadInfo.biggestNode.getNode_name(), nodeLoadInfo.biggestNode.getCurrentLoad(), nodeLoadInfo.smallestNode.getNode_name(), nodeLoadInfo.smallestNode.getCurrentLoad()));
            nodeLoadInfo.biggestNode.setCurrentLoad(nodeLoadInfo.biggestNode.getCurrentLoad() - 1);
            nodeLoadInfo.smallestNode.setCurrentLoad(nodeLoadInfo.smallestNode.getCurrentLoad() + 1);
            nodeLoadInfo = getNodeLoadInfo();
        }
        // Make sure the monitor has time to see the balanced load.
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        System.out.println("Balance complete.\n");
    }

    /**
     * Run through the node list calculating average load
     *
     * @return an object with the load information
     */
    private int getNodeLoadAverage() {
        // If the individual loads are all big, the total could overflow an int.
        long nodeTotalLoad = 0;
        Iterator<Node> nodes = this.clusterNodes.iterator();
        while (nodes.hasNext()) {
            Node node = nodes.next();
            nodeTotalLoad += node.getCurrentLoad();
        }
        // The nodeTotalLoad is a long, but the average will (should) never be more than an int.
        return (int) nodeTotalLoad / this.clusterNodes.size();
    }

    /**
     * Run through the node list looking for biggest, smallest, and average load
     *
     * @return an object with the load information
     */
    private NodeLoadInfo getNodeLoadInfo() {
        NodeLoadInfo nodeLoadInfo = new NodeLoadInfo();
        Iterator<Node> nodes = this.clusterNodes.iterator();
        // Pre-populate interesting nodes with the "first" in collection
        if (!nodes.hasNext()) {
            // This should really be an exception
            System.out.println("No nodes were found to balance ???");
            System.exit(0);
        }
        Node biggestNode = nodes.next();
        Node smallestNode = biggestNode;
        while (nodes.hasNext()) {
            Node node = nodes.next();
            if (node.getCurrentLoad() > biggestNode.getCurrentLoad()) {
                biggestNode = node;
            }
            if (node.getCurrentLoad() < smallestNode.getCurrentLoad()) {
                smallestNode = node;
            }
        }
        nodeLoadInfo.biggestNode = biggestNode;
        nodeLoadInfo.smallestNode = smallestNode;
        return nodeLoadInfo;
    }

    public void dump() {
        System.out.println("Cluster " + clusterName + " status:");
        Iterator<Node> nodes = clusterNodes.iterator();
        while (nodes.hasNext()) {
            Node node = nodes.next();
            System.out.println(node.toString());
        }
        System.out.println();
    }
}