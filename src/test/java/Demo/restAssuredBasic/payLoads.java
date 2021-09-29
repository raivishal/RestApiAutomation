package Demo.restAssuredBasic;

public class payLoads {
	
	public static String dumyResponse()
	{
		return "{\r\n"
				+ "\r\n"
				+ "\"dashboard\": {\r\n"
				+ "\r\n"
				+ "\"purchaseAmount\": 910,\r\n"
				+ "\r\n"
				+ "\"website\": \"rahulshettyacademy.com\"\r\n"
				+ "\r\n"
				+ "},\r\n"
				+ "\r\n"
				+ "\"courses\": [\r\n"
				+ "\r\n"
				+ "{\r\n"
				+ "\r\n"
				+ "\"title\": \"Selenium Python\",\r\n"
				+ "\r\n"
				+ "\"price\": 50,\r\n"
				+ "\r\n"
				+ "\"copies\": 6\r\n"
				+ "\r\n"
				+ "},\r\n"
				+ "\r\n"
				+ "{\r\n"
				+ "\r\n"
				+ "\"title\": \"Cypress\",\r\n"
				+ "\r\n"
				+ "\"price\": 40,\r\n"
				+ "\r\n"
				+ "\"copies\": 4\r\n"
				+ "\r\n"
				+ "},\r\n"
				+ "\r\n"
				+ "{\r\n"
				+ "\r\n"
				+ "\"title\": \"RPA\",\r\n"
				+ "\r\n"
				+ "\"price\": 45,\r\n"
				+ "\r\n"
				+ "\"copies\": 10\r\n"
				+ "\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "]\r\n"
				+ "\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "";
	}

	
	public static String addBook(String ISBN, String AISLE)
	{
		String response="{\r\n"
				+ "    \"name\":\"RD Sharma - 2021 print\",\r\n"
				+ "    \"isbn\":\""+ISBN+"\",\r\n"
				+ "    \"aisle\":\""+AISLE+"\",\r\n"
				+ "    \"author\":\"RD Sharma\"\r\n"
				+ "}";
		
		return response;
	}

	public static String jiraLoginDetails()
	{
		return "{\r\n"
				+ "    \"username\": \"vishal.rai\",\r\n"
				+ "    \"password\": \"admin\"\r\n"
				+ "}";
	}


	public static String createIssueInJira(String project, String summaryOfBug, String descriptionOfBug) {
		
		return "{\r\n"
				+ "    \"fields\": {\r\n"
				+ "       \"project\":\r\n"
				+ "       {\r\n"
				+ "          \"key\": \""+project+"\"\r\n"
				+ "       },\r\n"
				+ "       \"summary\": \""+summaryOfBug+"\",\r\n"
				+ "       \"description\": \""+descriptionOfBug+"\",\r\n"
				+ "       \"issuetype\": {\r\n"
				+ "          \"name\": \"Bug\"\r\n"
				+ "       }\r\n"
				+ "   }\r\n"
				+ "}";
	}


	public static String addComment(String comment) {
		
		return "{\r\n"
				+ "    \"body\": \""+comment+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}";
	}
}
