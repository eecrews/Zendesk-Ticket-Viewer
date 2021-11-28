import java.io.BufferedReader;

import org.json.JSONObject;
import org.json.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class TicketViewer {
	
	private static final String INVALID_INPUT = "Invalid input. Please try again.";
	private static final String PAGE_NONEXISTENT = "Page does not exist. Please try again.";
	
	private static String email = "eecrews@wisc.edu";
	private static String password = "Kiradog101";
	private static String subdomain = "zcceecrews";
	
	private static List<JSONObject> tickets = new ArrayList<JSONObject>();
	private static List<List<JSONObject>> ticketPages = new ArrayList<List<JSONObject>>();
	private static int numTickets;
	private static int numPages = 1;
	
	private static void pagePartition() {
		// Does not iterate over the last page
		for(int i=1; i<numPages; i++) {
			List<JSONObject> newPage;

			newPage = tickets.subList((i-1)*25, i*25);

			ticketPages.add(newPage);
		}
		
		// Manual addition of tickets on the last page
		int curr = (numPages-1)*25;
		List<JSONObject> finalPage = new ArrayList<JSONObject>();
		
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
	
	private static void getTicketsFromAPI() {
		String curl = "curl https://" + subdomain + ".zendesk.com/api/v2/tickets.json \\ -v -u " 
				+ email + ":" + password;
		ProcessBuilder processBuilder = new ProcessBuilder(curl.split(" "));

		try {
			Process process = processBuilder.start();
			InputStream inputStream = process.getInputStream();
			BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
			
			StringBuilder sb = new StringBuilder();
			String line;
			
			while((line = r.readLine()) != null) {
				sb.append(line);
			}
			
			try {
		        JSONObject jr = new JSONObject(sb.toString());
		        JSONArray arr = jr.getJSONArray("tickets");
		        
		        
		        for(int i=0; i < arr.length(); i++){
		            tickets.add(arr.getJSONObject(i));
		       }
		        
				System.out.println(tickets.toString());

		    } catch (JSONException e) {
		       e.printStackTrace();
		    }
			
			


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static void driver() {
		numTickets = tickets.size();
		
		// Updating numPages 
		if(tickets.size() > 25) {
			numPages = (int)Math.ceil(tickets.size()/25.0);
		}
		
		pagePartition();

		try {
			
			getTicketsFromAPI();
			
		} catch(Exception e) {
		}
		
		System.out.println(tickets.size());
		
		
		System.out.println("Welcome " + email + ". Here are your available tickets:");
		
		viewAllTickets();
		
					
	}
	

	
	public static void main(String[] args) {
		
		/*tickets.add("One");
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
		tickets.add("One"); */

		
		
		driver();

	}
	

}
