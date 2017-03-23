package c09

import org.jcsp.lang.*
import org.jcsp.groovy.*

class MissedEventCounter implements CSProcess
{
	def ChannelInput inChannel
	def ChannelOutput outChannel
	
	void run()
	{
		def Data = 99 //Initial Data set to avoid mismatch (first data is 100)
		
		while (true)
		{
			def e = inChannel.read()
			
			outChannel.write(e) //outputting e to outChannel
			
			def prevData = Data + e.missed + 1 //Setting the previous data variable 
			
			if(prevData != e.data) //if there's a mismatch
			{
				println "Mismatch! Data $e.data was expected to be $prevData"
			}
			else
			{
				//println "$e.data == $prevData -- Correct!" 
			}
			Data = e.data //setting Data variable to the real data
		}
	}
}
