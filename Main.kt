import com.google.gson.Gson
import java.io.File
import java.lang.Thread.sleep
import java.net.InetAddress
import java.util.*
import kotlin.concurrent.thread

var myNode = Node()

fun main(args: Array<String>) {
    /**
     * This is a p2p implementation of 400-node capacity
     */
    val client = Client()
    val id : Int
    val uuid : String
    if(!File("files").exists()){
        File("files").mkdir()
    }
    if(File("device.txt").exists()) {
        val data = File("device.txt").readLines()
        id = data[0].toInt()
        uuid = data[1]
    }else {
        id = IntRange(1,400).random()
        uuid = UUID.randomUUID().toString()
        File("device.txt").writeText("$id\n$uuid")
    }

    myNode = myNode.copy(id = id, pid = uuid, ipAddress = InetAddress.getLocalHost().hostAddress)
    println("Node id: $id and Node pid : $uuid" )
    val server = Server(33456,33457,myNode, client)

    while (true) {
        println("\n1. Join Network\n2. Leave Network\n3. Search File\n4. Print Finger Table\n5. List Files\n")
        print("COMMAND ::>")
        when(readLine()) {
            "1" -> {
                print("Enter IP Address: ")
                val ip = readLine().toString()
                print("Enter port: ")
                val port = readLine()!!.toInt()

//                get the response from the server to make the necessary update
                val updateInfo = client.send("HELLO ${server.myNode.id} ${server.myNode.pid} ${server.myNode.ipAddress}#", ip, port)
                val data = updateInfo.split(":")
                val successor = data[1].split(" ")
                val predecessor = data[2].split(" ")
                server.myNode = server.myNode.copy(successor_id = successor[0].toInt(),successor_address = successor[1],successor_port = successor[2].toInt(),predecessor_address = predecessor[1],predecessor_id = predecessor[0].toInt(),predecessor_port = predecessor[2].toInt())
            }
            "2" -> {
                //leaving the network
                client.send("LEAVING PRE ${server.myNode.predecessor_id} ${server.myNode.predecessor_address} ${server.myNode.predecessor_port}#",server.myNode.successor_address,server.myNode.successor_port)
                client.send("LEAVING SUCC ${server.myNode.successor_id} ${server.myNode.successor_address} ${server.myNode.successor_port}#", server.myNode.predecessor_address,server.myNode.predecessor_port)

                //send files to the successor
                val files = File("files").listFiles()
                files?.forEach {
                    if(it.isDirectory) {
                        it.listFiles()?.forEach { file ->
                            val dFile = DFile(
                                fileName = file.name,
                                data = file.readBytes(),
                                nodePid = it.name,
                                type = "LEAVING"
                            )
                            client.send("${Gson().toJson(dFile)}#", server.myNode.successor_address,33457)
                        }
                    }else{
                        val dFile = DFile(
                            fileName = it.name,
                            data = it.readBytes(),
                            nodePid = server.myNode.pid,
                            type = "LEAVING"
                        )
                        client.send("${Gson().toJson(dFile)}#", server.myNode.successor_address,33457)

                    }
                }

            }
            "3" -> {
                print("Enter file Name: ")
                val fileName = readLine().toString()
                client.send("SEARCH $fileName#",server.myNode.successor_address,server.myNode.successor_port)

            }
            "4" -> {
                println("FINGER TABLE")
                println(server.fingerTable)

            }
            "5" -> {
                println("===== AVAILABLE FILES ====")
                val files = File("files").listFiles()
                files?.forEach {
                    if(it.isFile) println(it.name)
                    if(it.isDirectory){
                        it.listFiles()?.forEach { it1 ->
                            println(it1.name)
                        }
                    }
                }
            }
        }
    }


}