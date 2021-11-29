/*
 * Created by Erin Crews for the 2022 Zendesk 
 * 	Internship Coding Challenge.
 * 
 * See README for more information.
 * 
 * Submitted on 28 November 2021.
 */

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
	 * Fill in your information below:
	 */
	private String email = "YourEmail@domain.com";
	private String subdomain = "YourSubdomain";
	private String apiKey = "YourAPIKey";

	private static final String INVALID_INPUT = "Invalid input. Please try again.";
	private static final String PAGE_NONEXISTENT = "Page does not exist. Please try again.";

	private static final String GENERIC_EXIT = "Exiting program.";
	private static final String API_ERROR = "Error connecting to API." + GENERIC_EXIT;
	private static final String STREAM_ERROR = "Stream error." + GENERIC_EXIT;

	private static final String MAIN_MENU = "Welcome. Press 1 to view all tickets.\n"
			+ "Press 2 to view a single ticket.\nPress e to exit the program.";
	private static final String SCROLL_INSTR = "Enter <n> to view the next page and "
			+ "<p> for the previous page.\nEnter <m> to return to the menu.";
	private static final String ENTER_TICKET_NUM = "Enter ticket number, or <m> to return to the menu.";

	private List<JSONObject> tickets = new ArrayList<JSONObject>();
	private List<List<JSONObject>> ticketPages = new ArrayList<List<JSONObject>>();

	/*
	 * Represents the total number of tickets in the user's account at the time the
	 * API is accessed. Default is 0 tickets.
	 */
	private int numTickets = 0;

	/*
	 * Represents the total number of pages that will be displayed by the program
	 * (max 25 tickets shown per page). Default is 1 page.
	 */
	private int numPages = 1;

	private static final Scanner INPUT = new Scanner(System.in);

	public static void main(String[] args) {
		TicketViewer viewer = new TicketViewer();
		viewer.driver();
	}

	/*
	 * Controls input for the main menu. Users have the option to enter 1 to view a
	 * list of all their tickets or 2 to view a single ticket. Users can enter 'e'
	 * to exit the program.
	 * 
	 * Continues running until user presses e or an error occurs.
	 * 
	 * Displays an error message if user tries to enter anything besides 1, 2, or e.
	 */
	void driver() {

		getTicketsFromAPI();
		setGlobals();
		pagePartition();

		do {
			System.out.println(MAIN_MENU);

			String in = INPUT.nextLine();

			switch (in) {
			case "1":
				viewAllTickets();
				break;
			case "2":
				singleTicketDriver();
				break;
			case "e":
				System.out.println(GENERIC_EXIT);
				INPUT.close();
				System.exit(0);
			default:
				System.out.println(INVALID_INPUT);
			}

		} while (true);
	}

	/*
	 * Attempts to connect to the Zendesk API to retrieve a list of all the user's
	 * tickets at the current time.
	 * 
	 * If the tickets are successfully retrieved, the tickets JSONArray is
	 * initialized with the JSON parsed from their account.
	 * 
	 * If the API cannot be reached or there is a problem parsing the JSON, an
	 * appropriate error message displays and the program is exited.
	 */
	void getTicketsFromAPI() {
		String curl = "curl -u " + email + "/token:" + apiKey + " https://" + subdomain
				+ ".zendesk.com/api/v2/tickets.json";
		ProcessBuilder processBuilder = new ProcessBuilder(curl.split(" "));

		try {
			Process process = processBuilder.start();
			InputStream inputStream = process.getInputStream();
			BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));

			StringBuilder sb = new StringBuilder();
			String line;

			while ((line = r.readLine()) != null) {
				sb.append(line);
			}

			try {
				JSONObject jr = new JSONObject(sb.toString());
				JSONArray arr = jr.getJSONArray("tickets");

				for (int i = 0; i < arr.length(); i++) {
					tickets.add(arr.getJSONObject(i));
				}

			} catch (JSONException e) {
				System.out.println(API_ERROR);
				INPUT.close();
				System.exit(0);
			}

		} catch (IOException e) {
			System.out.println(STREAM_ERROR);
			INPUT.close();
			System.exit(0);
		}

	}

	/*
	 * Divides the list of tickets into smaller sublists of max size 25. This will
	 * allow the user to scroll through all their tickets without seeing more than
	 * 25 per page.
	 */
	void pagePartition() {
		// Does not iterate over the last page
		for (int i = 1; i < numPages; i++) {
			List<JSONObject> newPage;

			newPage = tickets.subList((i - 1) * 25, i * 25);

			ticketPages.add(newPage);
		}

		// Manual addition of tickets on the last page
		int curr = (numPages - 1) * 25;
		List<JSONObject> finalPage = new ArrayList<JSONObject>();

		while (curr < numTickets) {
			finalPage.add(tickets.get(curr));
			curr++;
		}

		ticketPages.add(finalPage);
	}

	/*
	 * Controls input for viewing all the tickets in the user's account at the
	 * current time (user enters 1 on main menu).
	 * 
	 * User can enter n to view the next page of tickets, p to view the previous
	 * page, and m to return to the main menu.
	 * 
	 * If the user attempts to view a page that does not exist, (ie. page 0), an
	 * error message is displayed and they can try another input.
	 */
	private void viewAllTickets() {
		System.out.println(SCROLL_INSTR);

		int currPage = 1;

		do {
			System.out.println(viewSinglePage(currPage));

			String in = INPUT.nextLine();

			switch (in) {
			case "n":
				if (currPage + 1 > numPages)
					System.out.println(PAGE_NONEXISTENT);
				else
					currPage++;
				break;

			case "p":
				if (currPage - 1 == 0)
					System.out.println(PAGE_NONEXISTENT);
				else
					currPage--;
				break;

			case "m":
				return;
			default:
				System.out.println(INVALID_INPUT);
			}

		} while (true);

	}

	/*
	 * Outputs a list of all the tickets on the page currently being viewed by the
	 * user.
	 * 
	 * For example, if the user has 45 tickets in their account and they are viewing
	 * page 2, this method returns a view of the subjects of tickets 26-45.
	 * 
	 * Each ticket is given a number based on its index in the tickets list.
	 * 
	 * @param current page number being viewed by user
	 * 
	 * @return formatted view of the tickets on the current page
	 */
	private String viewSinglePage(int pageNum) {
		String result = "\n--Page " + (pageNum) + " out of " + numPages + "--\n";
		List<JSONObject> currTicketPage = ticketPages.get(pageNum - 1);

		for (int i = 0; i < currTicketPage.size(); i++) {
			result += "\n[" + (((pageNum - 1) * 25) + i + 1) + "] " + 
						currTicketPage.get(i).getString("subject");
		}

		return result;

	}

	/*
	 * Controls input for viewing details of a single ticket (user enters 2 on main
	 * menu).
	 * 
	 * This method prompts the user to enter the number of a ticket they would like
	 * to view. If they enter a number outside the scope of tickets, they are
	 * re-prompted.
	 * 
	 * The user can also enter 'm' to return to the main menu.
	 */
	private void singleTicketDriver() {
		do {
			System.out.println(ENTER_TICKET_NUM);

			String in = INPUT.nextLine();

			if (in.equals("m"))
				return;

			int ticketToView;

			try {
				ticketToView = Integer.parseInt(in);

				if (ticketToView > 0 && ticketToView <= numTickets)
					System.out.println(viewSingleTicket(ticketToView));
				else
					System.out.println(INVALID_INPUT);
			} catch (NumberFormatException e) {
				System.out.println(INVALID_INPUT);
			}
		} while (true);
	}

	/*
	 * Displays more details regarding a specific ticket the user wants to view. The
	 * ticket number they pass in is based on the ticket's index value in the list
	 * and is displayed next to the ticket's subject in the program's output of all
	 * tickets.
	 * 
	 * @param number of the ticket the user wants to view
	 * 
	 * @return formatted view of the subject, description, and priority of the
	 * desired ticket, if such values exist
	 */
	private String viewSingleTicket(int ticketNum) {
		String result = "-----------\nViewing ticket number " + ticketNum + ":";
		JSONObject curr = tickets.get(ticketNum - 1);

		try {
			result += "\nSubject: " + curr.getString("subject");
		} catch (JSONException e) {
			result += "\nNo subject.";
		}

		try {
			result += "\nDescription: " + WordUtils.wrap(curr.getString("description"), 60);
		} catch (JSONException e) {
			result += "\nNo description.";
		}

		try {
			result += "\nPriority: " + curr.getString("priority");
		} catch (JSONException e) {
			result += "\nNo priority value set.";
		}

		return result;
	}

	/*
	 * Helper method for setting global variables numTickets and numPages.
	 * 
	 * The reason this method exists is to help with unit testing, otherwise these
	 * variables would have been updated in driver().
	 */
	void setGlobals() {
		numTickets = tickets.size();

		// Only updates numPages if more than 1 page is needed to display all tickets
		if (tickets.size() > 25) {
			numPages = (int) Math.ceil(tickets.size() / 25.0);
		}
	}

	/*
	 * Get method to assist with unit testing.
	 * 
	 * @return total number of tickets in user's account at time of API access
	 */
	int getNumTickets() {
		return numTickets;
	}

	/*
	 * Get method to assist with unit testing.
	 * 
	 * @return total number of pages needed to display all tickets (max 25 per page)
	 */
	int getNumPages() {
		return numPages;
	}

	/*
	 * Get method to assist with unit testing.
	 * 
	 * @return List of JSONObject Lists holding tickets on each page
	 */
	List<List<JSONObject>> getTicketPages() {
		return ticketPages;
	}
}