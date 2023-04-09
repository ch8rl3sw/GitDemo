package rahulshettyacademy.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.OrderConfirmationPage;
import rahulshettyacademy.pageobjects.OrdersPage;
import rahulshettyacademy.pageobjects.ProductCart;
import rahulshettyacademy.pageobjects.ProductCatalog;

public class SubmitOrderTest extends BaseTest {
	String productName = "zara coat 3";

	@Test(dataProvider="getData", groups= {"Purchase"})
	public void submitOrder(HashMap<String,String> input) 
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		String country = "United States";
				
		ProductCatalog productCatalog =
				landingPage.loginApplication(input.get("email"), 
						input.get("password"));		
		List<WebElement> products = productCatalog.getProductList();
		productCatalog.addProductToCart(input.get("productName"));
		ProductCart productCart = productCatalog.goToCartPage();
		
		boolean foundProduct = productCart.isProductInCart(input.get("productName"));
		AssertJUnit.assertTrue(foundProduct);
		
		CheckoutPage checkoutPage = productCart.goToCheckout();
		
		checkoutPage.selectCountry(country);
		OrderConfirmationPage confirmationPage = checkoutPage.placeOrder();

		String confirmMessage = confirmationPage.getConfirmationMsgText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

		String orderNumber = confirmationPage.getOrderNumber();
		System.out.println("Order number is " + orderNumber);
	}
	
	// To verify ZARA COAT 3 is displaying in orders page
	@Test(dependsOnMethods={"submitOrder"})
	public void orderHistoryTest() {
		ProductCatalog productCatalog =
				landingPage.loginApplication("huskies@gmail.com", "Cr8zyd0gs");
		OrdersPage ordersPage = productCatalog.goToOrdersPage();
		Assert.assertTrue(ordersPage.verifyOrderDisplay(productName));
	}
	
	
	
	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String,String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + 
				"/src/test/java/rahulshettyacademy/data/PurchaseOrder.json");		
		return new Object[][] {{data.get(0)}, {data.get(1)}};
	}

}
