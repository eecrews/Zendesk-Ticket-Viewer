import java.lang.ProcessBuilder;
import java.util.ArrayList;
import java.util.Arrays;

public class TicketViewer {
	
	private static ArrayList<String> tickets = new ArrayList<String>();
	private static int numTickets;
	private static int numPages = 1;
	
	private static String viewPage(int pageNum) {
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
