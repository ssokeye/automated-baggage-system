package com.denver.model;

import java.util.List;

/**
 * 
 * The Conveyor System is defined by Bag points and the paths that link then all together
 * BagPoint defines the location of a Bag
 * BagPath defines the connection between a BagPoint and another BagPoint
 * 
 * @author Samuel Sokeye
 *
 */
public class ConveyorSystem {
	
	/**
	 * 
	 */
	private final List<BagPoint> bpoints;
	
	/**
	 * 
	 */
	private final List<BagPath>  bpaths;
	
	
	/**
	 * 
	 * @param bpoints
	 * @param bpaths
	 */
	public ConveyorSystem(List<BagPoint> bpoints, List<BagPath> bpaths)
	{
		this.bpaths = bpaths;
		this.bpoints = bpoints;
	}

	/**
	 * Get List of BagPoints
	 * 
	 * @return a list of BagPoint objects
	 */
	public List<BagPoint> getBpoints() {
		return bpoints;
	}

	/**
	 * Get List of BagPaths
	 * 
	 * @return a list of BagPath objects
	 */
	public List<BagPath> getBpaths() {
		return bpaths;
	}
	
 
}
