# Code Sample for Ed Cardinal
The enclosed files are two separate ‘mock’ implementations of a cluster of machines running differing loads; this is in Java and there is another in Python.
This code is intended o​nly ​to be a very simple example of my approach to coding and coding style, and is not intended to be seen as a finished product or exhaustive treatment of the subject.
I have made commentary notes throughout the code in various places where I could make improvements in the future.
## Example Application Overview:
The mock cluster has a number of nodes, each with its own load value.
The load could represent any common load measurement (CPU, disk, etc.), and is represented by an integer.
The loads are periodically monitored in a separate thread. The loads are randomly ‘unbalanced’ then re­balanced twice.
The output includes the balancing activity as it occurs and a dump of the monitoring threads at the end.