import java.lang.ProcessBuilder;
import java.util.ArrayList;
import java.util.Arrays;

public class TicketViewer {
	
	private static ArrayList<String> tickets = new ArrayList<String>();
	
	private static String viewAllTickets() {
		String allTickets = "";
		
		allTickets = Arrays.toString(tickets.toArray());
		
		int numPages = 1; // Number of pages shown to user is 1 by default.
		
		if(tickets.size() > 25) {
			numPages = (int)Math.ceil(tickets.size()/25.0);
		}
		
		for(int i=0; i<numPages; i++) {
			
		}

		return allTickets;
	}
	
	private static void driver() {
		
	}
	

	
	public static void main(String[] args) {
		
		tickets.add("One");
		tickets.add("Two");
		tickets.add("Three");
		tickets.add("One");
		tickets.add("Two");
		tickets.add("Three");
		tickets.add("One");
		tickets.add("Two");
		tickets.add("Three");
		tickets.add("One");
		tickets.add("Two");
		tickets.add("Three");
		tickets.add("One");
		tickets.add("Two");
		tickets.add("Three");
		tickets.add("One");
		tickets.add("Two");
		tickets.add("Three");
		tickets.add("One");
		tickets.add("Two");
		tickets.add("Three");
		tickets.add("One");
		tickets.add("Two");
		tickets.add("Three");
		tickets.add("One");
		tickets.add("Two");
		tickets.add("Three");
		
		driver();

	}
	

}
