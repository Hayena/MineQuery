package trefan.minequery

import trefan.minequery.utils.ByteUtils

case class QueryResponse(
		motd: String,
		gameMode: String,
		mapName: String,
		onlinePlayer: Int,
		maxPlayers: Int,
		port: Short,
		hostname: String,
		gameId: String,
		version: String,
		playerList: Seq[String])

object QueryResponse {
	def apply(data: Array[Byte], full: Boolean): QueryResponse = {
		val array = ByteUtils.split(ByteUtils.trim(data))
		val motd = new String(if (full) array(3) else array(0))
		val gameMode = new String(if (full) array(5) else array(1))
		val mapName = new String(if (full) array(13) else array(2))
		val onlinePlayers = Integer.parseInt(new String(if (full) array(15) else array(3)))
		val maxPlayers = Integer.parseInt(new String(if (full) array(17) else array(4)))
		val port = ByteUtils.bytesToShort(if (full) array(19) else array(5))
		val hostname = new String(if (full) array(21) else array(5))
		val gameId = if (full) new String(array(7)) else ""
		val version = if (full) new String(array(11)) else ""
		val playerList = for (i <- 25 to (array.length - 1) if array.length > 25)
			yield new String(array(i))
		QueryResponse(motd, gameMode, mapName, onlinePlayers, maxPlayers, port, hostname, gameId, version, playerList)
	}
}