import java.lang.ProcessBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TicketViewer {
	
	private static ArrayList<String> tickets = new ArrayList<String>();
	private static ArrayList<List<String>> ticketPages = new ArrayList<List<String>>();
	private static int numTickets;
	private static int numPages = 1;
	
	private static void pagePartition() {
		// Does not iterate over the last page
		for(int i=1; i<numPages; i++) {
			List<String> newPage;

			newPage = tickets.subList((i-1)*25, i*25);

			ticketPages.add(newPage);
		}
		
		// Manual addition of tickets on the last page
		int curr = (numPages-1)*25;
		List<String> finalPage = new ArrayList<String>();
		
		while(curr < numTickets) {
			finalPage.add(tickets.get(curr));
			curr++;
		}
		
		ticketPages.add(finalPage);
	}
	
	// TODO: Display only 5 tickets per line
	private static String viewAllTickets() {
		String allTickets = "";
		
		for(int i=0; i<numPages; i++) {
			allTickets += ticketPages.get(i).toString() + "\n";
		}

		return allTickets;
	}
	
	private static void driver() {
		numTickets = tickets.size();
		
		// Updating numPages 
		if(tickets.size() > 25) {
			numPages = (int)Math.ceil(tickets.size()/25.0);
		}
		
		pagePartition();
		
		Scanner input = new Scanner(System.in);
		do {
			System.out.println("Welcome. Please enter <tickets> to view all tickets" + 
					" and <end> to end the program.");
			
			String in = input.nextLine();
			switch(in) {
				case "tickets":
					System.out.println(viewAllTickets());
					break;
				case "end":
					System.exit(0);
				default:
					System.out.println("Invalid input. Please try again.");
			}
			
		} while(true);
		
		//input.close();
		
		//System.out.println(viewAllTickets());
		
		
		
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

		
		
		driver();

	}
	

}
