package com.actionsets;

/**
 * Represents a node in a cluster.
 */


public class Node {
    // Currently IP and name are only set during construction
    private String ip_address;
    private String node_name;
    // In this example, for the sake of simplicity the load is an integer like number of users, number of processes, etc.
    // TODO Implement the load as a real collection of objects that can be moved between nodes
    private Integer currentLoad;

    public Node(String ip_address, String node_name) {
        this.ip_address = ip_address;
        this.node_name = node_name;
        // don't initialize the load on creation, wait for 'refresh'
        currentLoad = 0;
    }

    public String getIp_address() {
        return ip_address;
    }

    public String getNode_name() {
        return node_name;
    }

    public Integer getCurrentLoad() {
        return currentLoad;
    }

    /**
     * Update the currentLoad field.
     *
     * @param currentLoad The current load value for this node.
     */
    public void setCurrentLoad(Integer currentLoad) {

        this.currentLoad = currentLoad;
    }

    @Override
    public String toString() {
        return "Node{" +
                "Name='" + node_name + '\'' +
                ", IP='" + ip_address + '\'' +
                ", Load=" + currentLoad +
                '}';
    }

    /**
     * This is a random load for the present, it would fetch the actual value from the source
     */
    public void refreshNodeLoad() {
        int minLoad = 0;
        int maxLoad = 20;
        // TODO Get an actual load value
        setCurrentLoad(minLoad + (int) (Math.random() * maxLoad));
        System.out.println(String.format("Refreshed node %1$s to load %2$d", this.node_name, currentLoad));
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}
