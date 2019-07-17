package subscribers_positive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import common_base.BaseClass;
import common_utilities.TestData;
import common_utilities.Utilities;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import subscribers_common_utilities.ParseGetSubscribersResponse;
import subscribers_common_utilities.RequestDataForUnsubscriber;
import subscribers_common_utilities.ResponseGeneral;
import subscribers_common_utilities.VerifySubscriberExists;

public class Unsubscribe  extends BaseClass {

	String methodName = "UnSubscriber";
	String responseCode = "";
	String responseTime = "";
	String api_version ="";
	String status = "";
	String expected, actual;
	String response_data ="";
	String errormessage ="";

	boolean isalreadyExists = false;

	TestData data = new TestData();	
	Utilities utils = new Utilities();
	RequestDataForUnsubscriber request_xml = new RequestDataForUnsubscriber();
	VerifySubscriberExists verifyexists = new VerifySubscriberExists();
	ResponseGeneral responseGeneralvalid = new ResponseGeneral();
	ParseGetSubscribersResponse parseGetSubResponse = new ParseGetSubscribersResponse();

	@Test
	public void DeleteSubscribers() {
		ArrayList<String[]> tdata_array = data.getdeleteSubscriberData();
		String[] tdata = tdata_array.get(1);

		logger = extent.createTest(methodName + " - Positive Testcase ");

		System.out.println("processing "+ methodName +" record - "+ tdata[0]);

		api_version = utils.getproperty("config", "api_version");
		String request_para = request_xml.requestdata(tdata);	

		isalreadyExists = verifyexists.VerifyGetSubscribers(tdata[2], tdata[1]);

		RestAssured.baseURI=uri;
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/xml");
		httpRequest.body(request_para);
		Response response = httpRequest.request(Method.POST);
		String responsedata = response.getBody().asString();	

		responseCode = Integer.toString(response.getStatusCode());
		responseTime = Long.toString(response.getTimeIn(TimeUnit.MILLISECONDS)) + "ms";
		reportDetails = reportDetails + "METHOD NAME: " + methodName;
		reportDetails = reportDetails + "\nRESPONSE CODE: " + responseCode;
		reportDetails = reportDetails + "\nRESPONSE TIME: " + responseTime;

		HashMap<String, String> responseelement = responseGeneralvalid.response_Status(response);
		status = responseelement.get("status");
		reportDetails = reportDetails + "\nRESPONSE -> Status: " + status;
		String version = responseelement.get("version");
		reportDetails = reportDetails + "\nRESPONSE -> Version: " + version;
		String elapsed = responseelement.get("elapsed");
		reportDetails = reportDetails + "\nRESPONSE -> Elapsed: " + elapsed;

		if(!status.toLowerCase().trim().equals("success")) {

			errormessage = responseelement.get("errormessage");
			reportDetails = reportDetails + "\nRESPONSE -> Error Message: " + errormessage;

			String errordetail = responseelement.get("errordetail");
			reportDetails = reportDetails + "\nRESPONSE -> Error Detail: " + errordetail;
		}
		else{

			reportDetails = reportDetails + "\n\nTest Case Status:\n===========================================\nSuccessfully unsubscribed the subscriber from the list.\n\n";
		}

		reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(request_para);
		reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responsedata);		


		if(!isalreadyExists) {
			String validationstatus = "failed";
			Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status in response are not in SUCCESS");
		}
		else {
			String validationstatus = "success";
			Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status in response are not in SUCCESS");
			VerifyGetSubscribers(tdata[2], tdata[1],tdata);
		}


	}

	private void VerifyGetSubscribers(String mailinglistid, String emailId, String[] addedPara) {
		HashMap<String, String> responseDataparsed = parseGetSubResponse.callGetSubscriberAndParse(mailinglistid, emailId, uri);

		int totalCount =Integer.parseInt(responseDataparsed.get("count"));

		String subscriberid = responseDataparsed.get("subscriberid"+1); 
		String listid = responseDataparsed.get("listid"+1); 
		String emailaddress = responseDataparsed.get("emailaddress"+1); 
		String domainname = responseDataparsed.get("domainname"+1); 
		String format = responseDataparsed.get("format"+1); 
		String confirmed = responseDataparsed.get("confirmed"+1); 
		String confirmcode = responseDataparsed.get("confirmcode"+1); 
		String requestdate = responseDataparsed.get("requestdate"+1); 
		String requestip = responseDataparsed.get("requestip"+1); 
		String confirmdate = responseDataparsed.get("confirmdate"+1); 
		String confirmip = responseDataparsed.get("confirmip"+1); 
		String subscribedate = responseDataparsed.get("subscribedate"+1); 
		String updatedate = responseDataparsed.get("updatedate"+1); 
		String bounced = responseDataparsed.get("bounced"+1); 
		String unsubscribed = responseDataparsed.get("unsubscribed"+1); 
		String unsubscribeconfirmed = responseDataparsed.get("unsubscribeconfirmed"+1); 
		String feedbacklooped = responseDataparsed.get("feedbacklooped"+1); 
		String feedbackloopconfirmed = responseDataparsed.get("feedbackloopconfirmed"+1); 
		String status = responseDataparsed.get("status"+1); 
		String last_visitorid = responseDataparsed.get("last_visitorid"+1); 
		String formid = responseDataparsed.get("formid"+1); 
		String total_opens = responseDataparsed.get("total_opens"+1); 
		String total_clicks = responseDataparsed.get("total_clicks"+1); 
		String last_open = responseDataparsed.get("last_open"+1); 
		String last_click = responseDataparsed.get("last_click"+1); 
		String dateadded = responseDataparsed.get("dateadded"+1); 
		String activitystatus = responseDataparsed.get("activitystatus"+1); 
		String listname = responseDataparsed.get("listname"+1); 
		String expectedTags = responseDataparsed.get("ExpectedTags"+1); 
		String actualTags =responseDataparsed.get("ActualTags"+1); 


		reportDetails = reportDetails + "\n GETTING RESPONSE -> subscriberid: " + subscriberid;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> listid: " + listid;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> emailaddress: " + emailaddress;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> domainname: " + domainname;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> format: " + format;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> confirmed: " + confirmed;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> confirmcode: " + confirmcode;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> requestdate: " + requestdate;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> requestip: " + requestip;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> confirmdate: " + confirmdate;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> confirmip: " + confirmip;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> subscribedate: " + subscribedate;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> updatedate: " + updatedate;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> bounced: " + bounced;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> unsubscribed: " + unsubscribed;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> unsubscribeconfirmed: " + unsubscribeconfirmed;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> feedbacklooped: " + feedbacklooped;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> feedbackloopconfirmed: " + feedbackloopconfirmed;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> status: " + status;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> last_visitorid: " + last_visitorid;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> formid: " + formid;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> total_opens: " + total_opens;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> total_clicks: " + total_clicks;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> last_open: " + last_open;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> last_click: " + last_click;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> dateadded: " + dateadded;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> activitystatus: " + activitystatus;
		reportDetails = reportDetails + "\n GETTING RESPONSE -> listname: " + listname;


		expected = unsubscribeconfirmed; 
		actual = "1"; 
		Assert.assertEquals(actual, expected, "Unsubscribeconfirmed tags are not matched.");

		expected = responseDataparsed.get("Elements"+1);  
		actual = "Expected Elements are appeared"; 
		Assert.assertEquals(actual, expected, "Expect tags and actual tags are not matched.");

	}

}

