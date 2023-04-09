package rahulshettyacademy.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.AssertJUnit;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.OrderConfirmationPage;
import rahulshettyacademy.pageobjects.ProductCart;
import rahulshettyacademy.pageobjects.ProductCatalog;

public class StepDefinitionImpl extends BaseTest {
	
	public LandingPage landingPage;
	public ProductCatalog productCatalog;
	public OrderConfirmationPage confirmationPage;
	
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException {
		landingPage = launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_username_and_password(String username, String password) {
		productCatalog = landingPage.loginApplication(username, password);	
	}
	
	@When("^I add product (.+) to cart$")
	public void i_add_product_to_cart(String productName) throws InterruptedException {
		List<WebElement> products = productCatalog.getProductList();
		productCatalog.addProductToCart(productName);
	}
	
	@When("^Checkout (.+) and submit the order$")
	public void checkout_submit_order(String productName) {
		ProductCart productCart = productCatalog.goToCartPage();
		
		boolean foundProduct = productCart.isProductInCart(productName);
		AssertJUnit.assertTrue(foundProduct);
		
		CheckoutPage checkoutPage = productCart.goToCheckout();
		
		checkoutPage.selectCountry("United States");
		confirmationPage = checkoutPage.placeOrder();
	}
	
	@Then("{string} message is displayed on ConfirmationPage")
	public void message_displayed_confirmationPage(String string) {
		String confirmMessage = confirmationPage.getConfirmationMsgText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
		driver.close();
	}
	
	@Then("^\"([^\"]*)\" message is displayed$")
    public void something_message_is_displayed(String strArg1) throws Throwable {
		Assert.assertEquals(strArg1, landingPage.getErrorMessage());
		driver.close();
    }

}
