package flightPlanning;

public class City{
	String name;
	protected Boolean isVisited;
	
	// Weighted Edge holding source & destination cities and cost & time for a leg.
	public City(String city) {
		name = city;
		isVisited = false;
	}

	void markVisited() {
		isVisited = true;
	}
	
	public Boolean isEqual(City other) {
		return this.name.contentEquals(other.name) ? true : false; 
	}
}
