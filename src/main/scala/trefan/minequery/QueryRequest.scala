package trefan.minequery

import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import trefan.minequery.utils.ByteUtils

case class QueryRequest(requestType: Byte, load: Int, full: Boolean) {
  val sessionId = 1
  val size = 1460
  val byteStream = new ByteArrayOutputStream(size)
  val dataStream = new DataOutputStream(byteStream)
  val magic = Array(0xFE.byteValue, 0xFD.byteValue)
  val payload = if(full) setFullPayload(load) else setPayload(load)
  
  def toBytes: Array[Byte] = {
  	byteStream.reset
  	dataStream.write(magic)
  	dataStream.write(requestType)
  	dataStream.writeInt(sessionId)
  	dataStream.write(payload)
  	byteStream.toByteArray
  }
  
  def setPayload(load: Int): Array[Byte] =
  	ByteUtils.intToBytes(load)
 
  def setFullPayload(load: Int): Array[Byte] = {
  	ByteUtils.padArrayEnd(setPayload(load), 4)
  }
}