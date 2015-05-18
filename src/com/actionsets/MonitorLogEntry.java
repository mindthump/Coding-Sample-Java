package com.actionsets;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A single entry in the Monitor Log.
 */
public class MonitorLogEntry {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String nodeName;
    private String nodeIP;
    private Date dateTimeStamp;
    private int currentLoad;

    public MonitorLogEntry(String nodeName, String nodeIP, Date dateTimeStamp, int currentLoad) {
        this.nodeName = nodeName;
        this.nodeIP = nodeIP;
        this.dateTimeStamp = dateTimeStamp;
        this.currentLoad = currentLoad;
    }

    @Override
    public String toString() {
        return "MonitorLogEntry{" +
                "Name='" + nodeName + '\'' +
                ", IP='" + nodeIP + '\'' +
                ", DateTime=" + sdf.format(dateTimeStamp) +
                ", Load=" + currentLoad +
                '}';
    }
}