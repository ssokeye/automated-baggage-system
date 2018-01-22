package com.denver.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.denver.model.BagPath;
import com.denver.model.BagPoint;
import com.denver.model.ConveyorSystem;

/**
 * 
 *The Automated Bag Service determines the fastest way to route bags  to their flights
 *or the correct Baggage claim at the Denver International airport.
 *
 *This class implements the Dijkstra Algorithm, to find the shortest and most efficient
 *way to get a Bag from one point to another. The graph created is a bi-directional graph
 *
 * 
 *
 * @author Samuel Sokeye
 * 
 */

public class AutomatedBagService {
	
	/**
	 * Member fields
	 * 
	 * @see BagPoint
	 * @see BagPath
	 * 
	 */
	private final List<BagPoint> bpoints;
	private final List<BagPath>  bpaths;

	
	/**
	 * List of Bag Points that have been visited
	 */
	private Set<BagPoint> visited;
	
	/**
	 * Set of Bag Points that have been visited
	 */	
	private Set<BagPoint> unvisited;
	
	/**
	 * Maps a BagPoint to another BagPoint, which is the BagPoint that was previously
	 * visited
	 */
    private Map<BagPoint, BagPoint> predecessors;
    
    /**
     * Map a BagPoint to an Integer representing the shortest distance from the starting BagPoint.
     */
    private Map<BagPoint, Integer> distance;
    
    
    /**
     * Constructor given a graph that describes the Conveyor System initializes the 
     * Bag points and paths between them.
     * 
     * @param cs conveyor system Object made up of BagPoints and BagPaths
     */
    public AutomatedBagService(  ConveyorSystem cs ){
    	this.bpoints = new ArrayList<BagPoint>(cs.getBpoints());
    	this.bpaths  = new ArrayList<BagPath>(cs.getBpaths());
    }
    
    
    /**
     * Engine that implements the Dijkstra Algorithm and finds the shortest path from 
     * the source to all other Baggage Points in the conveyor system
     * 
     * @param source is the starting location of a Bag Point to all other Bag Points in the 
     * conveyor system
     * 
     * Example: BaggageClaim to A4 the source here is BaggageClaim
     */
    public void run(BagPoint source){
    	
    	//Define SET and MAP datastructures
    	visited = new HashSet<BagPoint>();
    	unvisited = new HashSet<BagPoint>();
    	predecessors = new HashMap<BagPoint, BagPoint>();
    	distance = new HashMap<BagPoint, Integer>();
    	
    	//Place BagPoint source inside unvisited set
    	unvisited.add(source);
    	
    	//Place distance form source to source is zero
    	distance.put(source, 0);
    	
    	
    	//Table of information
    	while(unvisited.size() > 0){
    		
    		//Get unvisited BagPoint with the smallest know distance from the source(start Point)
    		//First time around, this return concourse_A_Ticketing as the current BagPoint
    		BagPoint current = getBagPointWithShortestDistancefromSource(unvisited);
    		
    		//For the current BagPoint we examine the unvisited neighbors
    		List<BagPoint> unvisitedNeighbor = findUnvisitedNeighbours(current);
    		
    		/*
    		 * Calculate distance of current BagPoint to each neighbor
    		 * and update the shortest distance to starting source for each neighbor
    		 * 
    		 */
    		findShortestPath(unvisitedNeighbor, current );
    		
    		//Add current BagPoint to List of Visited BagPoints
    		visited.add(current);
    		
    		//Remove current BagPoint form List of UnVisited  BagPoints
    		unvisited.remove(current);
    		
    	}
    	
    	
    }


    /**
     * Calculate distance of current BagPoint to each neighbor
     * and update the shortest distance to starting source for each neighbor
     *if the calculated distance of a BagPoint is less than the known distance of 
     *that BagPoint, update the distance of the unvisited neighbors and the previous BagPoint.
     *
     * @param unvisitedNeighbor 
     * 		  is a BagPoint that has not yet been processed
     * @param current 
     * 		  is a BagPoint that is currently being processed
     */
	private void findShortestPath(List<BagPoint> unvisitedNeighbor, BagPoint current) {
		
		for(BagPoint dest : unvisitedNeighbor){
			int pathdistance = getDistance(current, dest);
			
			//if the calculated distance of a BagPoint is less than the known distance of 
			//that BagPoint, update the distance of the unvisited neighbors and the previous BagPoint
			
			if(getShortestDistancefromSource(current) + pathdistance < getShortestDistancefromSource(dest)){
				
				distance.put(dest, (getShortestDistancefromSource(current) + pathdistance));
				
				//We arrived at dest via current
				predecessors.put(dest, current);
				unvisited.add(dest);
				
			}
		
		}
		
	}
	
	/**
	 * Get the BagPoint with the shortest distance from the source BagPoint, the starting location
	 * of a Bag.
	 * 
	 * @param unvisitedlist 
	 * 		  set of unvisited BagPoints
	 * @return BagPoint with the shortest distance from the starting BagPoint,
	 */
	private BagPoint getBagPointWithShortestDistancefromSource(Set<BagPoint> unvisitedlist) {
		
		BagPoint shortest = null;
		for(BagPoint bp : unvisitedlist){
			if(shortest == null)
				shortest = bp;
			else{
				if(getShortestDistancefromSource(bp) < getShortestDistancefromSource(shortest))
					shortest = bp;
			}
		}
		return shortest;
	}
	
	/**
	 * Get a List of Unvisited BagPoints that are neighbors to the current BagPoint
	 * 
	 * @param current 
	 * 		  current BagPoint that is being processed.
	 * 
	 * @return A Collection of Bag Points that are adjacent to the current Bag Point and has not
	 * an already visited Bag Point
	 * 
	 */
	private List<BagPoint> findUnvisitedNeighbours(BagPoint current) {	
		List<BagPoint> neighbors = new ArrayList<BagPoint>();
		
		for(BagPath bp : bpaths){
			if(bp.getSource().equals(current) && !isVisited(bp.getDestination())){
				neighbors.add(bp.getDestination());	
			}
		}
		return neighbors;
		
	}

	/**
	 * Helper method to get Shortest distance from the source.
	 * 
	 * @see getBagPointWithShortestDistancefromSource
	 * 
	 * @param dest
	 * 
	 * @return the shortest distance from current Bag Point to the Source
	 */
	private int getShortestDistancefromSource(BagPoint dest) {
		Integer d = distance.get(dest);
		if(d == null)
			return Integer.MAX_VALUE;
		else
			return d;
	}

	private int getDistance(BagPoint source, BagPoint destination) {
	        for (BagPath bp : bpaths) {
	            if (bp.getSource().equals(source)
	                    && bp.getDestination().equals(destination)) {
	                return bp.getWeight();
	            }
	        }
	        throw new RuntimeException("Should not happen");
	    }

	/**
	 * Check if a BagPoint has been visited
	 * 
	 * @param destination
	 * 	
	 * @return Boolean
	 * 		   True if the destination BagPoint has already been visited
	 */
	private boolean isVisited(BagPoint destination) {
		return visited.contains(destination);
	}
	
	/**
	 * Method responsible for output information
	 * Prints the starting location of a Baggage(source) and the final location of a Baggage(location) and all the 
	 * bag location in between.
	 * 
	 * @param source
	 * 		  Starting location of a Bag
	 * @param destination
	 * 		  Final Location of a Bag
	 */
	public void getPathBetweenBagPoints(BagPoint source, BagPoint destination){
		
		ArrayList<String> path = new ArrayList<>();
		BagPoint a = destination;
		
		path.add( ": " + distance.get(destination).toString());
		
		path.add(a.getName());
		
		while(predecessors.get(a) != null && !predecessors.get(a).equals(source)){
			a = predecessors.get(a);
			path.add(a.getName());
		}
		
		path.add(source.getName());
		
		Collections.reverse(path);
		
		for(String p: path)
		{
			System.out.print(p + " ");
		}
		System.out.println();
		
	}

}
