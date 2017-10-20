package pl.pawc.exchangerates;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DatafeedTest extends TestCase{
    
	public DatafeedTest(String testName){
        super(testName);
    }

    public static Test suite(){
        return new TestSuite(DatafeedTest.class);
    }

    public void testDatafeed(){
        assertTrue( true );
    }
}