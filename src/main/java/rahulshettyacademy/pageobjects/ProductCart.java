package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class ProductCart extends AbstractComponent {
	
	public ProductCart(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);		
	}
	
	//WebElement userEmail = driver.findElement(By.id("userEmail"));
	// Page Factory design pattern
	
	@FindBy(css=".cartSection h3")
	List<WebElement> cartProducts;
	
	@FindBy(css=".totalRow button")
	WebElement checkoutButton;
	
	By cartProductsBy = By.cssSelector(".cartSection h3");
	
	public List<WebElement> getCartProducts() {
		waitForElementToAppear(cartProductsBy);
		return cartProducts;
	}
	
	public boolean isProductInCart(String productName) {
		return cartProducts.stream().anyMatch(
				p -> p.getText().equalsIgnoreCase(productName));
	}
	
	public CheckoutPage goToCheckout() {
		checkoutButton.click();
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		return checkoutPage;
	}
	

}
