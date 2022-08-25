import java.io.*
import java.net.Socket

class Client {
    private lateinit var outputStream : OutputStream

    private lateinit var outputStreamWriter : OutputStreamWriter

    private lateinit var inputStream : InputStream

    private lateinit var inputStreamReader : InputStreamReader

    fun send(message : String, address : String, port : Int) : String{
        try {
            val socket = Socket(address, port)
//            send message to another node
            outputStream = socket.getOutputStream()
            outputStreamWriter = OutputStreamWriter(outputStream)

            outputStreamWriter.write(message) //message sent
            outputStreamWriter.flush() //clear the steam

            //wait for response from other node
            inputStream = socket.getInputStream()

            inputStreamReader = InputStreamReader(inputStream)

            val stringBuffer = StringBuilder()

            while(true)
            {
                val x = inputStreamReader.read()
                if(x==-1) break
                stringBuffer.append(x.toChar())
            }
            return stringBuffer.toString()

        }catch (e : Throwable) {
            println("Something went wrong during transfer")
            return ""
        }
    }

    fun getRtt() : Float {
        return 0.0F
    }
}