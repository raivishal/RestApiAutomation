package Demo.restAssuredBasic;

import io.restassured.path.json.JsonPath;

public class jsonParsing {

	public static void main(String[] args) {
		
		int coursePrice=0;
		JsonPath js=new JsonPath(payLoads.dumyResponse());
		System.out.println("Number of courses : "+js.getInt("courses.size()"));
		System.out.println("Purchase amount : "+js.getInt("dashboard.purchaseAmount"));
		System.out.println("Title of first course : "+js.getString("courses[2].title"));
		System.out.println("Title and price of each cources :");
		for (int i=0;i<js.getInt("courses.size()");i++)
		{
			System.out.println(js.getString("courses["+i+"].title")+" - "+js.getString("courses["+i+"].price"));
			int priceOfEachCourse=js.getInt("courses["+i+"].price")*js.getInt("courses["+i+"].copies");
			coursePrice=coursePrice+priceOfEachCourse;
		}
		
		for(int i=0;i<js.getInt("courses.size()");i++)
		{
			if(js.getString("courses["+i+"].title").equalsIgnoreCase("RPA"))
			{
				System.out.println("Number of coppies sold for RPA courses : "+js.getInt("courses["+i+"].copies"));
				break;
			}	
		}
		
		System.out.println(coursePrice);
		if(js.getInt("dashboard.purchaseAmount")==coursePrice)
		{
			System.out.println("purchase amount and total price of courses are equal");
		}
		
		
	}

	public static JsonPath rawToJson(String data)
	{
		JsonPath js=new JsonPath(data);
		
		return js;
	}
}
