import java.lang.ProcessBuilder;
import java.util.ArrayList;
import java.util.Arrays;

public class TicketViewer {
	
	private static ArrayList<String> tickets = new ArrayList<String>();
	private static int numTickets;
	private static int numPages = 1;
	
	private static String viewPage(int pageNum) {
		String allTickets = "";
		
		allTickets = Arrays.toString(tickets.toArray());

		return allTickets;
	}
	
	private static void driver() {
		numTickets = tickets.size();
		
		// Updating numPages 
		if(tickets.size() > 25) {
			numPages = (int)Math.ceil(tickets.size()/25.0);
		}
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
