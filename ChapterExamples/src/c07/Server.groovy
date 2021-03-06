package c07

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import org.jcsp.lang.*
import org.jcsp.groovy.*


class Server implements CSProcess{
	  
  def ChannelInput clientRequest
  def ChannelOutput clientSend  
  def ChannelOutput thisServerRequest
  def ChannelInput thisServerReceive  
  def ChannelInput otherServerRequest
  def ChannelOutput otherServerSend  
  def dataMap = [ : ]
  //Identifying servers
  def serverID    
                
  void run () {
    def CLIENT = 0
    def OTHER_REQUEST = 1
    def THIS_RECEIVE = 2
    def serverAlt = new ALT ([clientRequest, 
		                      otherServerRequest, 
							  thisServerReceive])
    while (true) {
      def index = serverAlt.select()
	  
      switch (index) {		  
        case CLIENT :
          def key = clientRequest.read()
		  println "Server number ${serverID} receives key ${key} request from client"
          if ( dataMap.containsKey(key) ) 
		  {
			  println "Server number $serverID has key $key"
			  clientSend.write(dataMap[key])
			  println "Server number $serverID sends key $key to client"
		  }      
          else 
		  {
			  println "Server number $serverID requests key $key"
			  thisServerRequest.write(key)
		  }
          //end if 
          break
		// requests from other server
        case OTHER_REQUEST :
          def key = otherServerRequest.read()
		  if ( dataMap.containsKey(key) )
		  {
			   println "Server number $serverID sends key $key to other server"
			   otherServerSend.write(dataMap[key])  
		  }                    
          else 
            otherServerSend.write(-1)
          //end if 
          break
		// receiving from other server
        case THIS_RECEIVE :
		  println "Server number $serverID gets key from other server"
          clientSend.write(thisServerReceive.read() )
          break
      } // end switch              
    } //end while   
  } //end run
}
