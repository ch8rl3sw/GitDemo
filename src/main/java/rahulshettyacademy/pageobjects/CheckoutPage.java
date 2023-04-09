package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent {
	
	public CheckoutPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);		
	}
	
	//WebElement userEmail = driver.findElement(By.id("userEmail"));
	// Page Factory design pattern
	
	@FindBy(css="[placeholder='Select Country']")
	WebElement countryInput;
	
	@FindBy(css=".action__submit")
	WebElement placeOrderButton;
	
	@FindBy(css="span.ng-star-inserted")
	List<WebElement> countrySuggestions;
	
	By countryInputBy = By.cssSelector("[placeholder='Select Country']");
	By countrySuggestion = By.cssSelector("span.ng-star-inserted");
	
	public void selectCountry(String country) {
		waitForElementToAppear(countryInputBy);
		countryInput.clear();
		countryInput.sendKeys(country);
		waitForElementToAppear(countrySuggestion);
		WebElement desiredCountry = countrySuggestions.stream()
				.filter(countrySuggestion -> countrySuggestion.getText().equals(country)).findFirst().orElse(null);
		desiredCountry.click();
	}
	
	public OrderConfirmationPage placeOrder() {
		placeOrderButton.click();
		return new OrderConfirmationPage(driver);
	}
	

}
