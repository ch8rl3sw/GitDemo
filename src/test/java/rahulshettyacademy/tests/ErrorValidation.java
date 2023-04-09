package rahulshettyacademy.tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.TestComponents.Retry;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.OrderConfirmationPage;
import rahulshettyacademy.pageobjects.ProductCart;
import rahulshettyacademy.pageobjects.ProductCatalog;

public class ErrorValidation extends BaseTest {

	@Test(groups= {"ErrorHandling"}, retryAnalyzer=Retry.class)
	public void loginErrorValidation() throws IOException, InterruptedException {
				
		ProductCatalog productCatalog =
				landingPage.loginApplication("huskies@gmail.com", "Cr87zyd0gs");
		Assert.assertEquals("Incorrect email or password.", 
				landingPage.getErrorMessage());
		
	}
	
	@Test
	public void productErrorValidation() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		String productName = "zara coat 3";
		String country = "United States";
				
		ProductCatalog productCatalog =
				landingPage.loginApplication("mwilliams@gmail.com", "Welcome1$");		
		List<WebElement> products = productCatalog.getProductList();
		productCatalog.addProductToCart(productName);
		ProductCart productCart = productCatalog.goToCartPage();		
		boolean foundProduct = productCart.isProductInCart("zara coat 33");
		AssertJUnit.assertFalse(foundProduct);
		
	}

}
