package trefan.minequery

import java.net.{ BindException, DatagramPacket, DatagramSocket, InetAddress }
import trefan.minequery.utils.ByteUtils

class MineQuery(host: String, port: Int = 25565) {
	val socket = createSocket(port)
	val address = InetAddress.getByName(host)

	def getStatus: QueryResponse = {
		val request = new QueryRequest(0, handshake, false)
		executeRequest(request, false)
	}
	
	def getFullStatus: QueryResponse = {
		val request = new QueryRequest(0, handshake, true)
		executeRequest(request, true)
	}
	
	private def executeRequest(request: QueryRequest, full: Boolean): QueryResponse = {
		val input = request.toBytes
    val result = sendUDP(input)
    QueryResponse(result, full)
	}
	
	
	private def createSocket(socketPort: Int): DatagramSocket =
		try {
			return new DatagramSocket(socketPort)
		} catch {
			case be: BindException => createSocket(socketPort + 1)
		}

	private def handshake: Int = {
		val request = new QueryRequest(9, 0, false)
		val value = request.toBytes
		val input = ByteUtils.padArrayEnd(value, value.length)
		val result = sendUDP(input)
		new String(result).trim().toInt
	}

	private def sendUDP(input: Array[Byte]): Array[Byte] = {
		socket.send(new DatagramPacket(input, input.length, address, port))
		val packet = new DatagramPacket(new Array[Byte](1024), 1024)
		socket.setSoTimeout(500)
		socket.receive(packet)
		packet.getData
	}

}