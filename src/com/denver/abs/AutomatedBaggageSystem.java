package com.denver.abs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.denver.model.BagPath;
import com.denver.model.BagPoint;
import com.denver.model.ConveyorSystem;
import com.denver.service.AutomatedBagService;

/**
 * Main class to handle the routing of baggage check-in's, baggage connections
 * and baggage arrivals at the Denver International Airport.
 * 
 * The Class reads an input file describing all Baggage locations and the paths
 * between each Baggage location.
 * 
 * @author Samuel Sokeye
 * @since Date: 1/15/2018
 * 
 */
public class AutomatedBaggageSystem {

	private static List<BagPoint> bpoints = new ArrayList<BagPoint>();
	private static List<BagPath> bpaths = new ArrayList<BagPath>();
	private static Map<String, BagPoint> departureGate = new HashMap<String, BagPoint>();
	private static TreeMap<String, HashMap<BagPoint, BagPoint>> baggage = new TreeMap<String, HashMap<BagPoint, BagPoint>>();

	/**
	 * Setting up logger for main class
	 */
	private static final Logger logger = Logger
			.getLogger(AutomatedBaggageSystem.class.getName());

	/**
	 * main method that invokes algorithm
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String... args) throws IOException {

		// Declare and Initialize the local method variables
		Scanner in = null;
		String section = new String("");
		BagPoint start = null;
		BagPoint end = null;

		try {
			in = new Scanner(new BufferedReader(new InputStreamReader(
					AutomatedBaggageSystem.class
							.getResourceAsStream("Input.txt"))));
			boolean token = false;
			int newsection = 0;

			while (in.hasNextLine()) {
				section = in.nextLine();

				if (Arrays.asList(section.split(" ")).contains("#")) {
					token = true;
					newsection++;
				} else
					token = false;

				switch (newsection) {

				case 1:
					if (!token)
						processSection(section);
					break;

				case 2:
					if (!token)

						processDepartures(section);
					break;
				case 3:
					if (!token)
						processBaggage(section);
					break;
				default:
					logger.log(Level.SEVERE, "The input file is empty", section);
				}

			}
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage(), new RuntimeException(ex));
		} finally {
			if (in != null)
				in.close();
		}

		try {

			ConveyorSystem cs = new ConveyorSystem(bpoints, bpaths);

			AutomatedBagService bagService = new AutomatedBagService(cs);

			for (Map.Entry<String, HashMap<BagPoint, BagPoint>> mapping : baggage
					.entrySet()) {
				for (BagPoint key : mapping.getValue().keySet()) {
					start = key;
				}
				for (BagPoint key : mapping.getValue().values()) {

					end = key;
				}

				bagService.run(start);
				System.out.print(mapping.getKey() + " ");
				bagService.getPathBetweenBagPoints(start, end);

			}
		}

		catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), new RuntimeException(e));
		}

	}

	/**
	 * Parse the input String from the Conveyor System section Add new Baggage
	 * Point encountered to the bpoints List collection. Add new Baggage Path
	 * encountered to the bpaths List collection.
	 * 
	 * @param inputString
	 * 
	 */
	private static void processSection(String inputString) {
		String[] section = inputString.split(" ");

		BagPoint source = new BagPoint(section[0]);
		BagPoint destination = new BagPoint(section[1]);

		// Add New Nodes to BagPoint List
		if (!bpoints.contains(source))
			bpoints.add(source);
		if (!bpoints.contains(destination))
			bpoints.add(destination);

		bpaths.add(new BagPath(source, destination, Integer.valueOf(section[2])));

		// make the graph a bidirectional
		bpaths.add(new BagPath(destination, source, Integer.valueOf(section[2])));

	}

	/**
	 * Parse the input String from the Departures section into a Map data
	 * structure. Maps <flight_id> to <flight_gate>
	 * 
	 * @param inputString
	 *            String made up of <flight_id> <flight_gate> <destination>
	 *            <flight_time>
	 * 
	 */
	private static void processDepartures(String inputString) {
		String[] departurelist = inputString.split(" ");

		String flight_id = departurelist[0];
		BagPoint flight_gate = new BagPoint(departurelist[1]);

		departureGate.put(flight_id, flight_gate);

	}

	/**
	 * Parse the input String from the Bag list section Data is parsed in to a
	 * Tree Map to keep Bags in the conveyor system ordered.
	 * 
	 * @param inputString
	 * 
	 */
	private static void processBaggage(String inputString) {

		String[] section = inputString.split(" ");
		HashMap<BagPoint, BagPoint> bag = new HashMap<BagPoint, BagPoint>();
		String bag_number = section[0];
		BagPoint entry_point = new BagPoint(section[1]);
		String flight_id = section[2];
		BagPoint drop_point;

		// Find the BagPoint for the flight_id
		if (flight_id.equals("ARRIVAL")) {
			drop_point = new BagPoint("BaggageClaim");
		} else
			drop_point = departureGate.get(flight_id);

		bag.put(entry_point, drop_point);

		baggage.put(bag_number, bag);
	}

}
