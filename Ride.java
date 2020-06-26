import java.text.DecimalFormat;

/**
 * Ride object that carries all of the attributes of a single ride
 * @author Chase Petersen
 *
 */
public class Ride {
	
	/**
	 * How long the ride is
	 */
	public int duration;
	
	/**
	 * The start time of the ride
	 */
	public double startTime;
	
	/**
	 * The end time of the ride
	 */
	public double endTime;
	/**
	 * The starting location's id
	 */
	public Integer startStationId;
	
	/**
	 * The starting location
	 */
	public String startStation;
	
	/**
	 * The starting latitude coordinate
	 */
	public double startLat;
	
	/**
	 * The starting longitude coordinate
	 */
	public double startLong;
	
	/**
	 * The ending location's id
	 */
	public Integer endStationId;
	
	/**
	 * The ending location
	 */
	public String endStation;
	
	/**
	 * The ending latitude coordinate
	 */
	public double endLat;
	
	/**
	 * The ending longitude coordinate
	 */
	public double endLong;
	
	/**
	 * The id of the bike on the ride
	 */
	public int bikeId;
	
	/**
	 * The type of bike on the ride (Classic or Dockless)
	 */
	public char bikeType;
	
	/**
	 * If the user is a member or just using a day pass
	 */
	public int userType;
	
	/**
	 * The user's birth year
	 */
	public int userBirthYear;
	
	/**
	 * The gender of the user
	 */
	public int userGender;
	
	/**
	 * Constructor that creates a Ride object
	 */
	public Ride(int newDuration, double newStartTime, double newEndTime, Integer newStartStationId,	String newStartStation,
			double newStartLat, double newStartLong, Integer newEndStationId, String newEndStation,	double newEndLat, 
			double newEndLong, int newBikeId, char newBikeType, int newUserType, int newUserBirthYear, int newUserGender){
		setDuration(newDuration);
		setStartTime(newStartTime);
		setEndTime(newEndTime);
		setStartStationId(newStartStationId);
		setStartStation(newStartStation);
		setStartLat(newStartLat);
		setStartLong(newStartLong);
		setEndStationId(newEndStationId);
		setEndStation(newEndStation);
		setEndLat(newEndLat);
		setEndLong(newEndLong);
		setBikeId(newBikeId);
		setBikeType(newBikeType);
		//Formats the user type to show two digits
		DecimalFormat userTypeFormatter = new DecimalFormat("00");
		String userTypeFormatted = userTypeFormatter.format(newUserType);
		setUserType(Integer.parseInt(userTypeFormatted));
		//Formats the users birth year to show four digits
		DecimalFormat userBirthYearFormatter = new DecimalFormat("0000");
		String userBirthYearFormatted = userBirthYearFormatter.format(newUserBirthYear);
		setUserBirthYear(Integer.parseInt(userBirthYearFormatted));
		setUserGender(newUserGender);
	}
	
	/**
	 * Necessary setter and getter methods
	 */
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public void setUserBirthYear(int userBirthYear) {
		this.userBirthYear = userBirthYear;
	}
	public void setUserGender(int userGender) {
		this.userGender = userGender;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public double getStartTime() {
		return startTime;
	}
	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(double endTime) {
		this.endTime = endTime;
	}
	public void setStartStationId(Integer startStationId) {
		this.startStationId = startStationId;
	}
	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}
	public void setStartLat(double startLat) {
		this.startLat = startLat;
	}
	public void setStartLong(double startLong) {
		this.startLong = startLong;
	}
	public void setEndStationId(Integer endStationId) {
		this.endStationId = endStationId;
	}
	public void setEndStation(String endStation) {
		this.endStation = endStation;
	}
	public void setEndLat(double endLat) {
		this.endLat = endLat;
	}
	public void setEndLong(double endLong) {
		this.endLong = endLong;
	}
	public int getBikeId() {
		return bikeId;
	}
	public void setBikeId(int bikeId) {
		this.bikeId = bikeId;
	}
	public char getBikeType() {
		return bikeType;
	}
	public void setBikeType(char bikeType) {
		this.bikeType = bikeType;
	}
	
	/**
	 * This method properly prints the information of a Ride
	 */
	public String toString(){
		String message = "Duration: " + duration + "\nStart Time: " + startTime  + "\nEnd Time: " + endTime + "\nStart Station ID: " 
				+ startStationId + "\nStart Station: " + startStation + "\nStart Latitude: " + startLat + "\nStart Longitude: " 
				+ startLong + "\nEnd Station" + " ID: " + endStationId + "\nEnd Station: " + endStation + "\nEnd Latitude: " 
				+ endLat + "\nEnd Longitude: " + endLong + "\nBike ID: " + bikeId + "\nBike Type: ";
		
		/**
		 * If the bike type is labeled as C, then it is a classic
		 * Otherwise, if it is labeled as D, then it is a dockless
		 */
		if(bikeType == 'C'){
			message += "Classic";
		}else if(bikeType == 'D'){
			message += "Dockless";
		}
		message += "\nUser Type: ";
		
		/**
		 * If the user type is labeled as 00, then they are using a day pass
		 * Otherwise, if it is labeled as 01, then they are a member
		 */
		if(userType == 00){
			message += "Single Ride or Day Pass";
		}else if(userType == 01){
			message += "Annual or Monthly Member";
		}
		message += "\nUser Birth Year: " + userBirthYear + "\nUser Gender: ";
		
		/**
		 * If the user gender is labeled as 1, then they are a male
		 * If it is labeled as 2, then they are female
		 * Otherwise, if it is labeled as a 0, then they are unknown
		 */
		if(userGender == 1){
			message += "Male";
		}else if(userGender == 2){
			message += "Female";
		}else{
			message += "Unknown/didn’t specify";
		}
		return message;
	}
}
