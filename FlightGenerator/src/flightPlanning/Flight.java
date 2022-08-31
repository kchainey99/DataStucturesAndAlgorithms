package flightPlanning;

public class Flight{ // flights = edges with city @ source
	String destination; //only need destination, since source is our key
	int cost, time;
	Flight(String dest, int cost, int time){
		destination = new String(dest);
		this.cost = cost;
		this.time = time;
	}
	
	public boolean isEqual(Flight other){
		if (this.destination.equals(other.destination) && this.cost == other.cost && this.time == other.time)
			return true;
		return false;	
	}
}
