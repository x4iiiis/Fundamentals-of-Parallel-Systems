package c2;

import groovy.util.GroovyTestCase
import org.jcsp.groovy.PAR
import org.jcsp.lang.One2OneChannel
import org.jcsp.lang.*

class ThreeToEightTestCase extends GroovyTestCase 
{
	void test()
	{
		One2OneChannel connect1 = Channel.createOne2One()
		One2OneChannel connect2 = Channel.createOne2One()
		CreateSetsOfEight setsOf8 = new CreateSetsOfEight (parameter : 8, 
     inChannel: connect2.in() )
		GenerateSetsOfThree setsOf3 = new GenerateSetsOfThree (outChannel: connect1.out() )
		ListToStream listToStream = new ListToStream ( inChannel: connect1.in(),
 outChannel: connect2.out())
		
		def testList = [ setsOf3, listToStream, setsOf8 ]
		
		new PAR (testList).run()
		
		def expected = [17, 18, 19, 20, 21, 22, 23, 24]
		def actual = setsOf8.outList
		assertTrue(actual == expected)
	}
}
