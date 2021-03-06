package c07

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import org.jcsp.lang.*
import org.jcsp.groovy.*


class Client implements CSProcess{  
	
  def ChannelInput receiveChannel
  def ChannelOutput requestChannel
  def clientNumber   
  def selectList = [ ] 
   
  void run () {
    def iterations = selectList.size
	def success = true //Test variable
    
	println "Client $clientNumber has $iterations values in $selectList"
	
    for ( i in 0 ..< iterations) {
      def key = selectList[i]
	  println "Client number ${clientNumber} requests key ${key}"
      requestChannel.write(key)
      def v = receiveChannel.read()
	  println "Client number ${clientNumber} receives key ${key}"
    if (v != key * 10)
	  {
		success = false	
		println "$v != $key"
	  }
    }
	if (success == false)
	{
		println "Client $clientNumber has failed the test"
	}
	else
	{
		println "Client $clientNumber has successfully completed the test"
	}
  }
}

