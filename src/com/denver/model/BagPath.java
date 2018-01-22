package com.denver.model;

/**
 * 
 * The BagPath Object defines the path between two BagPoints 
 * and the distance between them.
 * The Constructor of this class takes 3 arguments, a source BagPoint, 
 * a destination BagPoint and a weight value, representing the distance
 * between them.
 * 
 * @author Samuel Sokeye
 * 
 */
public class BagPath {
	
	/**
	 * The start point of a Bag
	 */
	private final BagPoint source;
	
	/**
	 * The end point of a Bag
	 */	
	private final BagPoint destination;
	
	/**
	 * Distance from source to Destination
	 */
	private final int weight;
	
	/**
	 * Class constructor
	 * Construct a Path between two Bags
	 * 
	 * @param source 
	 * 		  Start point of a Bag
	 * 
	 * @param destination
	 * 		  End point of a Bag
	 * 
	 * @param weight
	 * 		  Distance between source and destination
	 */
	public BagPath(BagPoint source, BagPoint destination, int weight){
		this.source = source;
		this.destination = destination;
		this.weight = weight;
		
	}
	
	/**
	 * Gets the start Point of a Bag
	 * 
	 * @return BagPoint representing a Bag
	 */
	public BagPoint getSource() {
		return source;
	}
	
	/**
	 * Gets the end Point of a  Bag
	 * 
	 * @return BagPoint representing a Bag
	 */
	public BagPoint getDestination() {
		return destination;
	}
	
	/**
	 * Gets the distance between start and end points of a Bag
	 * 
	 * @return The distance between Bags
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * 
	 * @return Returns the source and destination information about the BagPath instance.
	 */
	@Override
	public String toString() {
		return  getSource() + " " + getDestination() + " " + getWeight();
	}
	
	
}
