package flightPlanning;

public class Flight{ // flights = edges with city @ source
	City destination; //only need destination, since source is our key
	int cost, time;
	Flight(String dest, int cost, int time){
		destination = new City(dest);
		this.cost = cost;
		this.time = time;
	}
	
	Flight(City dest, int cost, int time){
		destination = dest;
		this.cost = cost;
		this.time = time;
	}
	
	public boolean compareTo(Flight other){
		if (this.destination.equals(other.destination) && this.cost == other.cost && this.time == other.time)
			return true;
		return false;	
	}
}
