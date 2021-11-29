import java.io.BufferedReader;

import org.json.*;
import org.apache.commons.text.*;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class TicketViewer {
	
	/*
	* 	Fill in your information below:
	*/
	private String email = "eecrews@wisc.edu";
	private String subdomain = "zcceecrews";
	private String apiKey = "cssVjedmhJuxJqCmwUiGiPb9mUgynXj58QSQRbzA";
	
	private static final String INVALID_INPUT = "Invalid input. Please try again.";
	private static final String PAGE_NONEXISTENT = "Page does not exist. Please try again.";
	
	private List<JSONObject> tickets = new ArrayList<JSONObject>();
	private List<List<JSONObject>> ticketPages = new ArrayList<List<JSONObject>>();
	private int numTickets = 0;
	private int numPages = 1;
	
	private static final Scanner INPUT = new Scanner(System.in);
	
	int getNumTickets() {
		return numTickets;
	}
	
	int getNumPages() {
		return numPages;
	}
	
	List<List<JSONObject>> getTicketPages() {
		return ticketPages;
	}
	
	
	void getTicketsFromAPI() {
		String curl = "curl -u " + email + "/token:" + apiKey + 
				" https://" + subdomain + ".zendesk.com/api/v2/tickets.json";
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
		        
		    } catch (JSONException e) {
		       System.out.println("Error connecting to API. Exiting program.");
		       INPUT.close();
		       System.exit(0);
		    }
			
		} catch (IOException e) {
			System.out.println("Stream error. Exiting program.");
			INPUT.close();
			System.exit(0);
		}

	}
	
	void pagePartition() {
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
	
	void viewAllTickets() {		
		System.out.println("Enter <n> to view the next page and <p> for the previous page.\n" +
					"Enter <m> to return to the menu.");
		
		int currPage = 1;
		
		do {
			viewSinglePage(currPage);

			String in = INPUT.nextLine();
			
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
					
				case "m":
					return;
				default: 
					System.out.println(INVALID_INPUT);
			}
			
		} while(true);
		
	}
	
	void viewSinglePage(int pageNum) {
		System.out.println("\n--Page " + (pageNum) + " out of " + numPages + "--\n");
		List<JSONObject> currTicketPage = ticketPages.get(pageNum-1);
				
		for(int i=0; i<currTicketPage.size(); i++) {
			System.out.println("[" + (((pageNum-1)*25)+i+1) + "] " + currTicketPage.get(i).getString("subject"));
		}

	}
	
	void singleTicketDriver() {
		do {
			System.out.println("\nEnter ticket number, or <m> to return to the menu.");
			
			String in = INPUT.nextLine();
			
			if(in.equals("m"))
				return;
			
			int ticketToView;
			
			try {
				ticketToView = Integer.parseInt(in);
				
				if(ticketToView > 0 && ticketToView <= numTickets)
					viewSingleTicket(ticketToView);
				else
					System.out.println(INVALID_INPUT);				
			} catch(NumberFormatException e) {
				System.out.println(INVALID_INPUT);
			}
		} while(true);
	}
	
	void viewSingleTicket(int ticketNum) {
		JSONObject curr = tickets.get(ticketNum-1);
		
		System.out.println("----------\nViewing ticket number " + ticketNum + ":");
		
		try {
			System.out.println("\nSubject: " + curr.getString("subject"));
		} catch(JSONException e) {
			System.out.println("\nNo subject.");
		}
		
		try {
			System.out.println("\nDescription: " + WordUtils.wrap(curr.getString("description"), 60));
		} catch(JSONException e) {
			System.out.println("\nNo description.");
		}
		
		try {
			System.out.println("\nPriority: " + curr.getString("priority"));
		} catch(JSONException e)
		{
			System.out.println("\nNo priority value set.");
		}
	}
	
	void setGlobals() {
		numTickets = tickets.size();
		
		// Updating numPages 
		if(tickets.size() > 25) {
			numPages = (int)Math.ceil(tickets.size()/25.0);
		}
	}
	
	void driver() {
		try {
			getTicketsFromAPI();
			
		} catch(Exception e) {
		}
		
		
		setGlobals();		
		pagePartition();
		
		do {
			System.out.println("Welcome. Press 1 to view all tickets.\nPress 2 to view a single ticket.\n"
					+ "Press e to exit the program.");
			
			String in = INPUT.nextLine();
			
			switch(in) {
				case "1":
					viewAllTickets();
					break;		
				case "2":
					singleTicketDriver();
					break;	
				case "e":
					System.out.println("Exiting program.");
					INPUT.close();
					System.exit(0);
				default: 
					System.out.println(INVALID_INPUT);
			}
			
		} while(true);
	}
	
	public static void main(String[] args) {	
		TicketViewer viewer = new TicketViewer();
		viewer.driver();
	}
}
