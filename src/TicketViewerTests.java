import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class TicketViewerTests {
	
	TicketViewer testViewer;
	
	@BeforeAll
	void setUp() {
		testViewer = new TicketViewer();
		testViewer.getTicketsFromAPI();
		testViewer.setGlobals();
	}  

	@Disabled
	@Test
	void badAPICredentials() {
		Throwable e = assertThrows(Exception.class, () -> testViewer.getTicketsFromAPI());
		assertEquals("Error connecting to API. Exiting program.", e.getMessage());
	}
	
	@Test
	// Test setGlobals
	void ticketNumUnder100() {
		assertTrue(testViewer.getNumPages() == 4);
		assertTrue(testViewer.getNumTickets() == 95);
		
	}
	
	@Test
	void pagePartitionTest() {
		testViewer.pagePartition();
		assertTrue(testViewer.getTicketPages().size() == 4);
	} 

}
