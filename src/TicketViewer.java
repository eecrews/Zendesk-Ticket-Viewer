import java.lang.ProcessBuilder;
import java.util.ArrayList;
import java.util.Arrays;

public class TicketViewer {
	
	private static ArrayList<String> tickets = new ArrayList<String>();
	private static ArrayList<ArrayList<String>> ticketPages = new ArrayList<ArrayList<String>>();
	private static int numTickets;
	private static int numPages = 1;
	
	private static void pagePartition() {
		for(int i=1; i<=numPages; i++) {
			ArrayList<String> newPage = new ArrayList<String>();

			for(int j=0; j<25; j++) {
				if(tickets.get(j) != null)
					newPage.add(tickets.remove(j));
			}

			ticketPages.add(newPage);
		}
	}
	
	private static String viewAllTickets() {
		String ticketsOnPage = "";
		int startIndex = (pageNum-1)*25;
		int endIndex = (pageNum*25);
		
		for(int i=startIndex; i<endIndex; i=i+5) {
			if(tickets.get(i) == null)
				break;
			
			for(int j=i; j<=i+5; j++) {
				ticketsOnPage = ticketsOnPage + tickets.get(j) + "\t";
			}
			
		}
		
		ticketsOnPage = Arrays.toString(tickets.toArray());

		return ticketsOnPage;
	}
	
	private static void driver() {
		numTickets = tickets.size();
		
		// Updating numPages 
		if(tickets.size() > 25) {
			numPages = (int)Math.ceil(tickets.size()/25.0);
			System.out.println(numPages);
		}
		
		for(int i=1; i<=numPages; i++) {
			System.out.println(viewPage(i));
			System.out.println("-----");
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
