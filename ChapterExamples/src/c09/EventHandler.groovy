package c09;
 
// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel


import org.jcsp.lang.*
import org.jcsp.groovy.*

class EventHandler implements CSProcess { 
	 
  def ChannelInput inChannel
  def ChannelOutput outChannel  
  
  void run () {    
    def get = Channel.one2one()
    def transfer = Channel.one2one()
    def toBuffer = Channel.one2one() 
	def toCounter = Channel.one2one() //Channel for communicating with MissedEventCounter
	   
    def handlerList = [ new EventReceiver ( eventIn: inChannel, 
                                            eventOut: toBuffer.out()),
                        new EventOWBuffer ( inChannel: toBuffer.in(), 
                                            getChannel: get.in(),
                                            outChannel: transfer.out() ),
                        new EventPrompter ( inChannel: transfer.in(), 
                                            getChannel: get.out(),
                                            outChannel: toCounter.out() ),
						//Defining the MissedEventCounter class 
						new MissedEventCounter ( inChannel: toCounter.in(),
												 outChannel: outChannel)
                      ]
    new PAR ( handlerList ).run()
  }
}
