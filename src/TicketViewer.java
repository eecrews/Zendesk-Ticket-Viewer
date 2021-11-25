import java.lang.ProcessBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TicketViewer {
	
	private static final String INVALID_INPUT = "Invalid input. Please try again.";
	private static final String PAGE_NONEXISTENT = "Page does not exist. Please try again.";
	
	private static String username = "";
	private static String password = "";
	
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
	private static void viewAllTickets() {		
		System.out.println("Enter <n> to view the next page and <p> for the previous page.\n" +
					"Enter <e> to exit the program.");

		// TODO: Close scanner?
		Scanner input = new Scanner(System.in);
		
		int currPage = 1;
		
		do {
			viewSinglePage(currPage);

			String in = input.nextLine();
			
			switch(in) {
				case "n":
					if(currPage + 1 > numPages)
						System.out.println(PAGE_NONEXISTENT);
					else
						currPage++;
					break;
					
				case "p":
					if(currPage - 1 == 0)
						System.out.println(PAGE_NONEXISTENT);
					else
						currPage--;
					break;
					
				case "e":
					input.close();
					System.exit(0);
				default: 
					System.out.println(INVALID_INPUT);
			}
			
		} while(true);
		
	}
	
	private static void viewSinglePage(int pageNum) {
		System.out.println("\nPage " + (pageNum) + " out of " + numPages + "\n" + ticketPages.get(pageNum-1).toString());

	}
	
	private static void driver() {
		numTickets = tickets.size();
		
		// Updating numPages 
		if(tickets.size() > 25) {
			numPages = (int)Math.ceil(tickets.size()/25.0);
		}
		
		pagePartition();
		
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter your username.");
		username = input.nextLine();
		
		System.out.println("Please enter your password.");
		password = input.nextLine();
		
		
		System.out.println("Welcome " + username + ". Here are your available tickets:");
		
		viewAllTickets();
		
		input.close();
					
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
		
		tickets.add("pageTwo");
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
