package com.denver.test;


import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.denver.abs.AutomatedBaggageSystem;
import com.denver.model.BagPath;
import com.denver.model.BagPoint;

public class ConveyorSystemTest {
    
    @Test
    public void testReadAsStream() throws IOException{
        InputStream is = AutomatedBaggageSystem.class.getResourceAsStream("Input.txt");
        Assert.assertNotNull(is);
    }

	@Test
	public void canInstantiateABagPoint(){
		BagPoint a = new BagPoint("test");
		Assert.assertNotNull(a);
	}
	
	
	@Test
	public void CreateBagPointandReturnName(){
		
		//Given
		String name = "Concourser_B_Ticketing";
		
		BagPoint bpoint = new BagPoint(name);
		
		Assert.assertTrue(bpoint.getName().equals(name));
		
	}
	
	@Test
	public void CreateBagPathandReturnWeight(){
		BagPoint a = null;
		BagPoint b = null;
		int weight = 7;
		
		BagPath bpath = new BagPath( a, b, 7);
		
		Assert.assertTrue(bpath.getWeight() == weight);
		
	}
	
	@Test
	public void CreateBagPathandReturnSource(){
		BagPoint a = null;
		BagPoint b = null;
		int weight = 7;
		
		BagPath bpath = new BagPath( a, b, weight);
		
		Assert.assertTrue(bpath.getSource() == a);
		
	}
	
	
	@Test
	public void CreateBagPathandReturnDestination(){
		BagPoint a = null;
		BagPoint b = null;
		int weight = 7;
		
		BagPath bpath = new BagPath( a, b, weight);
		
		Assert.assertTrue(bpath.getDestination() == b);
		
	}
}
