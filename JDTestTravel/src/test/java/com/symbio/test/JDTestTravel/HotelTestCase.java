package com.symbio.test.JDTestTravel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.symbio.test.JDTestTravel.PageObject.HotelDetailPageObject;
import com.symbio.test.JDTestTravel.PageObject.HotelSearchPageObject;
import com.symbio.test.JDTestTravel.PageObject.HotelSearchResultPageObject;
import com.symbio.test.JDTestTravel.PageObject.MainPageObject;
import com.symbio.test.JDTestTravel.PageObject.TravelPageObject;

/**
 * Test case for booking hotel test case
 * 
 * @author Stephen Raharja
 *
 */
public class HotelTestCase {

	private WebDriver driver;

	private MainPageObject mainPageObj;
	private TravelPageObject travelPageObj;
	private HotelSearchPageObject hotelSearchPageObj;
	private HotelSearchResultPageObject hotelSearchResultPageObj;
	private HotelDetailPageObject hotelDetailPageObject;

	private String destinationCityString1 = "北京";
	private String destinationCityString2 = "香港";
	private String startDateString = "2015-12-25";
	private String endDateString = "2016-01-04";

	private Date startDate;
	private Date endDate;

	/**
	 * test case to search hotel in Beijing
	 */
	@Test(groups = "jdhotel-test")
	public void testCase1() {
		mainPageObj.goToTravel();
		travelPageObj.goToThisPage();
		travelPageObj.goToBookHotel();
		hotelSearchPageObj.insertDestinationCity(destinationCityString1);
		hotelSearchPageObj.searchHotel();

		hotelSearchResultPageObj.filterHotelPriceAboveSixHundred();
		hotelSearchResultPageObj.filterHotelFiveStar();
		hotelSearchResultPageObj.sortHotelByPriceAscending();
		hotelSearchResultPageObj.bookFirstHotel();

		hotelDetailPageObject.goToThisPage();
		hotelDetailPageObject.bookFirstAvailableRoom();
	}

	/**
	 * test case to search hotel in HongKong
	 * 
	 * @throws Exception
	 */
	@Test(groups = "jdhotel-test")
	public void testCase2() throws InterruptedException {
		mainPageObj.goToTravel();

		travelPageObj.goToThisPage(); // go to the parent site page
		travelPageObj.goToBookHotel(); // go to hotel page

		hotelSearchPageObj.insertDestinationCity(destinationCityString2); // enter destination
		hotelSearchPageObj.searchHotel(); // search hotels

		hotelSearchResultPageObj.filterHotelFiveStar(); // get five star hotel
		hotelSearchResultPageObj.filterHotelFreeWifi(); // get free wifi hotel
		hotelSearchResultPageObj.bookHighestRatedHotel(); // get the one with highest rating
		
		hotelDetailPageObject.goToThisPage();
		hotelDetailPageObject.changeHotelBookingDate(startDate, endDate); // change the booking date
	
		hotelDetailPageObject.bookFirstAvailableRoom(); // pick the first available room
	}

	/**
	 * Initiate the necessary variables for the test
	 */
	@BeforeClass(groups = "jdhotel-test")
	public void initTest() throws ParseException {
		driver = new FirefoxDriver();
		mainPageObj = new MainPageObject(driver);
		travelPageObj = new TravelPageObject(driver);
		hotelSearchPageObj = new HotelSearchPageObject(driver);
		hotelSearchResultPageObj = new HotelSearchResultPageObject(driver);
		hotelDetailPageObject = new HotelDetailPageObject(driver);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		startDate = simpleDateFormat.parse(startDateString);
		endDate = simpleDateFormat.parse(endDateString);
	}

	/**
	 * Initiate the base website
	 */
	@BeforeMethod(groups = "jdhotel-test")
	public void initSite() {
		driver.get("http://www.jd.com");
	}

	/**
	 * Close all opened internet browser windows
	 */
	@AfterClass(groups = "jdhotel-test")
	public void destroyTest() {
		driver.quit();
	}
}
