import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;


public class ParsingProject {
	
	/**
	 * Main construction and runtime happens in this method
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			/**
			 * A list of all of the rides in the file
			 */
			ArrayList<Ride> rides = new ArrayList<Ride>();
			
			/**
			 * Reads in the file and puts all lines into an array of Strings
			 */
			String[] lines = Files.readAllLines(new File("ridelog.dat").toPath()).toArray(new String[0]);
			
			/**
			 * Since there is only one line, it will be set as this variable
			 */
			String information = lines[0];
			
			/**
			 * Total duration over all rides
			 */
			double totalDuration = 0;
			
			/**
			 * Amount of riders over forty
			 */
			int ridersOverForty = 0;
			
			/**
			 * Amount of subscribers on rides
			 */
			int subscribers = 0;
			
			/**
			 * While there is still a starting token
			 */
			while(information.charAt(0) == '!'){
				
				/**
				 * Take a substring, starting after the !! and ending before the first,
				 * Then parse that string to an integer, and that is the first variable of a ride
				 * The rest of the variables go through a similar process
				 */
				int duration = Integer.parseInt(information.substring(2, information.indexOf(',')));
				
				/**
				 * Throughout the process, we basically delete all pieces of information we have taken from the string
				 * So that the new information is always at the beginning of the string
				 */
				information = clipInformation(information);
				double startTime = Double.parseDouble(information.substring(0, information.indexOf(',')));
				information = clipInformation(information);
				double endTime = Double.parseDouble(information.substring(0, information.indexOf('|')));
				information = clipInformation(information);
				Integer startStationId;
				
				/**
				 * If there not null at the beginning of the string at this point, parse normally
				 * Otherwise, set Integer variable to null
				 */
				if(!information.substring(0, information.indexOf(',')).equals("NULL")){
					startStationId = Integer.parseInt(information.substring(0, information.indexOf(',')));
				}else{
					startStationId = null;
				}
				information = clipInformation(information);
				String startStation = information.substring(0, information.indexOf('|'));
				information = clipInformation(information);
				double startLat = Double.parseDouble(information.substring(0, information.indexOf(',')));
				information = clipInformation(information);
				double startLong = Double.parseDouble(information.substring(0, information.indexOf('|')));
				information = clipInformation(information);
				Integer endStationId;
				
				/**
				 * If there not null at the beginning of the string at this point, parse normally
				 * Otherwise, set Integer variable to null
				 */
				if(!information.substring(0, information.indexOf(',')).equals("NULL")){
					endStationId = Integer.parseInt(information.substring(0, information.indexOf(',')));
				}else{
					endStationId = null;
				}
				information = clipInformation(information);
				String endStation = information.substring(0, information.indexOf('|'));
				information = clipInformation(information);
				double endLat = Double.parseDouble(information.substring(0, information.indexOf(',')));
				information = clipInformation(information);
				double endLong = Double.parseDouble(information.substring(0, information.indexOf('|')));
				information = clipInformation(information);
				int bikeId = Integer.parseInt(information.substring(0, information.indexOf(',')));
				information = clipInformation(information);
				char bikeType = information.substring(0, information.indexOf('|')).charAt(0);
				information = clipInformation(information);
				int userType = Integer.parseInt(information.substring(0, information.indexOf(',')));
				information = clipInformation(information);
				int userBirthYear = Integer.parseInt(information.substring(0, information.indexOf(',')));
				information = clipInformation(information);
				
				/**
				 * This variable always ends a ride, so we look for the % and take the variable before it
				 */
				int userGender = Integer.parseInt(information.substring(0, information.indexOf('%')));
				
				/**
				 * We are now starting a new ride. Move the string to the beginning of the next ride
				 */
				information = information.substring(information.indexOf("%")+2, information.length());
				
				/**
				 * Create Ride object with the information gathered
				 */
				Ride newRide = new Ride(duration, startTime, endTime, startStationId, startStation, startLat, startLong, endStationId,
						endStation, endLat, endLong, bikeId, bikeType, userType, userBirthYear, userGender);
				
				/**
				 * Print the new ride through the objects toString method
				 */
				System.out.println("\n\n" + newRide);
				
				/**
				 * Add the Ride to the list
				 */
				rides.add(newRide);
				
				/**
				 * Add duration of this ride to the total duration
				 */
				totalDuration += duration;
				
				/**
				 * If someone is born before 1981, they are over 40
				 */
				if(userBirthYear <= 1981){
					ridersOverForty++;
				}	
				
				/**
				 * If the user type is labeled as 01, they are a subscriber
				 */
				if(userType == 01){
					subscribers++;
				}
			}
			
			/**
			 * Print total duration in hours
			 */
			System.out.println("\nThe total duration of all races is: " + (totalDuration/60)/60 + " hours.");
			
			/**
			 * Calculate and print the percent of people over 40 on the rides
			 */
			double ridersMultiple = ridersOverForty*100;
			double ridersOverFortyPercentage = ridersMultiple/rides.size();
			System.out.println("The percent of riders over the age of 40 is: " + ridersOverFortyPercentage + "%");
			
			/**
			 * Print the amount of subscribers
			 */
			System.out.println("There were " + subscribers + " trips completed by subscribers.");
			
			int [] highestCounts = findHighest(rides);
			
			/**
			 * Calculate the highest occurring start time and bike id
			 */
			int[] idAndTime = calculateHighestAmount(highestCounts[0], rides, highestCounts[1]);
			
			/**
			 * Prints the highest occurring bike id
			 */
			System.out.println("The highest occurring Bike ID is: " + idAndTime[0]);
			
			/**
			 * Prints which bike type occurs more
			 */
			String highestType = "The highest occurring Bike Type is: ";
			if(highestCounts[2] == 0){
				highestType += "Classic";
			}else{
				highestType += "Dockless";
			}
			System.out.println(highestType);
			
			/**
			 * Prints the highest occurring start time
			 */
			System.out.println("The highest occurring start hour is: " + idAndTime[1] + ":00 Military Time");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The method clips the string of information so that the next variable's information is at the beginning of the string
	 * @param information The string holding all the information
	 * @return A clipped version of the string that only has unused and assigned information
	 */
	public static String clipInformation(String information){
		
		/**
		 * If a line is still in the string and comes before a comma, trim before that line
		 * Otherwise, trim before the next comma
		 */
		if(information.indexOf(",") > information.indexOf("|") && information.indexOf("|") != -1){
			return information.substring(information.indexOf("|")+1, information.length());
		}else{
			return information.substring(information.indexOf(",")+1, information.length());
		}
	}
	
	/**
	 * Calculates which bike id and start time is most commonly used
	 * @param highestId The highest possible bike id
	 * @param rides The list of rides
	 * @param highestStartHour
	 * @return An array holding the highest used bike id and start time
	 */
	@SuppressWarnings("deprecation")
	public static int[] calculateHighestAmount(int highestId, ArrayList<Ride> rides, int highestStartHour){
		int idHighestAmount = 0;
		int idHighestAmountOccurrences = 0;
		int startTimeHighestAmount = 0;
		int TimeHighestAmountOccurrences = 0;
		int[] idAndTime = new int[2];
		
		/**
		 * Counts the occurrences of bike ids specifically starting at 0
		 */
		for(int i = 0; i < highestId; i++){
			int idOccurances = 0;
			int timeOccurances = 0;
			for(int j = 0; j < rides.size(); j++){
				
				/**
				 * Add up how many occurrences of that one bike id
				 */
				if(rides.get(j).getBikeId() == i){
					idOccurances++;
				}
				
				/**
				 * If i is still lower than the max start hour, add up how many occurrences of that one start time
				 */
				if(i < highestStartHour && fromDoubleToDateTime(rides.get(j).getStartTime()).getHours() == i){
					timeOccurances++;
				}
			}
			
			/**
			 * If the current bike id occurrences are higher than the previous most used bike id,
			 * The current bike id becomes the new most used bike id
			 */
			if(idHighestAmountOccurrences < idOccurances){
				idHighestAmountOccurrences = idOccurances;
				idHighestAmount = i;
			}
			
			/**
			 * If the current start time occurrences are higher than the previous most used start time,
			 * The current start time becomes the new most used start time
			 */
			if(TimeHighestAmountOccurrences < timeOccurances){
				TimeHighestAmountOccurrences = timeOccurances;
				startTimeHighestAmount = i;
			}
		}
		idAndTime[0] = idHighestAmount;
		idAndTime[1] = startTimeHighestAmount;
		return idAndTime;
	}
	
	@SuppressWarnings("deprecation")
	public static int[] findHighest(ArrayList<Ride> rides){
		
		/**
		 * Calculates what the highest bike id number there is and counts the occurrences of bike types
		 * in order to find out which is more popular
		 */
		int highestId = 0;
		int highestStartHour = 0;
		int classicOccurrence = 0;
		int docklessOccurrence = 0;
		int[] highestCounts = new int[3];
		for(int i = 0; i < rides.size(); i++){
			Date currentDate = fromDoubleToDateTime(rides.get(i).getStartTime());

			/**
			 * Finds highest bike id number
			 */
			if(rides.get(i).getBikeId() > highestId){
				highestId = rides.get(i).getBikeId();
			}
			
			if(currentDate.getHours() > highestStartHour){
				highestStartHour = currentDate.getHours();
			}
			
			/**
			 * Counts the occurrences of bike types
			 */
			if(rides.get(i).getBikeType() == 'C'){
				classicOccurrence++;
			}else{
				docklessOccurrence++;
			}
		}
		
		/**
		 * Assigns highest counts to places in the array to return them
		 */
		highestCounts[0] = highestId;
		highestCounts[1] = highestStartHour;
 		if(classicOccurrence > docklessOccurrence){
			highestCounts[2] = 0;
		}else{
			highestCounts[2] = 1;
		}
 		return highestCounts;
	}
	
	/**
	 * Converts date to gregorian calendar system in order to grab
	 * @param OADate Date in 1900 date system
	 * @return Date in gregorian calendar system
	 * @author https://stackoverflow.com/questions/23670516/what-is-the-equivalent-of-datetime-fromoadate-in-java-double-to-datetime-in-j
	 */
	public static Date fromDoubleToDateTime(double OADate) 
	{
	    long num = (long) ((OADate * 86400000.0) + ((OADate >= 0.0) ? 0.5 : -0.5));
	    if (num < 0L) {
	        num -= (num % 0x5265c00L) * 2L;
	    }
	    num += 0x3680b5e1fc00L;
	    num -=  62135596800000L;

	    return new Date(num);
	}
}
