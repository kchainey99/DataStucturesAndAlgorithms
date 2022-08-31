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
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof City){
			City c = (City)o;
			return equals(c);
		}
		else
			return false;
	}
	
	public Boolean equals(City other) {
		return this.name.equals(other.name) ? true : false; 
	}
}
