package pl.pawc.exchangerates.shared.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ModelTest extends TestCase{
    
	public ModelTest(String testName){
        super(testName);
    }

    public static Test suite(){
        return new TestSuite(ModelTest.class);
    }

    public void testDatafeed(){
        assertTrue(true);
    }
}