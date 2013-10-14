package trefan.minequery.utils

import java.nio.ByteBuffer
import scala.Array.canBuildFrom

object ByteUtils {
	def subarray(in: Array[Byte], first: Int, last: Int): Array[Byte] = {
		if (last - first > in.length)
			return in
		in.slice(first, last)
	}

	def trim(in: Array[Byte]): Array[Byte] = {
		if (in(0) != 0 && in(in.length) != 0)
			return in
		var end = in.length
		var result = for { i <- 0 to (in.length - 1) if in(i) != 0 } yield i
		val begin = result(0)
		result = for { i <- (in.length - 1) to 0 by -1 if in(i) != 0 } yield i
		end = result(0) + 1
		subarray(in, begin, end)
	}

	def padArrayEnd(in: Array[Byte], amount: Int): Array[Byte] = {
		in ++ new Array[Byte](amount)
	}

	def split(in: Array[Byte]): Array[Array[Byte]] = {
		var out = Array.empty[Array[Byte]]
		var out_index = 0
		var index_cache = 0
		for (i <- 0 to (in.length - 1) if (in(i) == 0)) {
			var byteArray = subarray(in, index_cache, i)
			out = out :+ byteArray
			index_cache = i + 1
		}
		if (index_cache > 0) {
			var byteArray = subarray(in, index_cache, in.length)
			out = out :+ byteArray
		}
		out
	}

	def bytesToShort(in: Array[Byte]): Short = {
		ByteBuffer.wrap(in).getShort
	}

	def intToBytes(in: Int): Array[Byte] = {
		val buffer = ByteBuffer.allocate(4)
		buffer.putInt(in).array
	}

	def bytesToInt(in: Array[Byte]): Int = {
		ByteBuffer.wrap(in).getInt
	}
}