# poc-p2p
### This is a class assignment of basic peer to peer implementation.
#### This project is a peer-to-peer implementation that utilizes the chord algorithm for navigation and peer discovery. Chord algorithm implements a round-robin architecture with data and peers identified using a key in the network. Every node in this network is able to join or leave the network at any time, search for a file in the network, serve requested a file and also stabilize the network by frequently checking on its neighbours as present in the Hash Map.
#### The project has various classes to handle the various concerns and functionality associated with the project implementation: 
    Client class:
        This is responsible for initializing the client connection to the server socket of the neighbouring peer for command and file transfer.

    Server class: 
        This bears both the command and file-transfer sockets to service requests from the neighbouring peers.

    DFile class:
        This encapsulates the file to be transferred over the network for marshalling to json and then un-marshalls in on reaching the client that initiated the file-search.
    Node class:
        This is a data class for holding data about the node as described;
            ipAddress - This is the internal address of the node in a network
            publicAddress - This is the ip address of the node on the internet.
            Id - This is a random number assigned to the node to mark its position in the p2p network.
            pid  - A unique string assigned to a node to uniquely identify it in the network.
            Successor_id - The id of the successor node
            successor_ address - The address of the successor node
            successor_port - The port of the successor node
            predecessor_id -  The id of the predecessor node.
            Predecessor_address- The ip address of the predecessor
            predecessor_port - The port to server socket of the predecessor node
    
    Main.kt:
        This is the entry file to starting the node 

    device.txt :
        This is a configuration file created at the very start of the node and holds permanently required data like the node id and the node pid.

    files directory:
        This is a directory that contains files to be serviced on the network as well as files from predecessor nodes that are leaving the network marked with their pid as the directory name for the backup files.

### Deployment Procedure:
#### For this project, Kotlin programming language was used, Object Oriented Programming language based on Java, to implement the node.
    The deployment environment must have Java JDK and any Kotlin programming language compiler like Intellij installed.
    The node is started by running the Main.kt file to start the peer after which a menu option will be availed to them by the node to carry out the various operations.


