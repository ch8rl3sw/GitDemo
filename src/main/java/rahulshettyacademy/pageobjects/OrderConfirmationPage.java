package rahulshettyacademy.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class OrderConfirmationPage extends AbstractComponent {
	
	public OrderConfirmationPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);		
	}
	
	//WebElement userEmail = driver.findElement(By.id("userEmail"));
	// Page Factory design pattern
	
	@FindBy(css=".hero-primary")
	WebElement confirmationMsg;
	
	@FindBy(css=".em-spacer-1 label.ng-star-inserted")
	WebElement orderDetails;
	
	By confirmationMsgBy = By.cssSelector(".hero-primary");
	
	public String getConfirmationMsgText() {
		waitForElementToAppear(confirmationMsgBy);
		return confirmationMsg.getText();
	}
	
	public String getOrderNumber() {
		String orderNumberText = orderDetails.getText();
		return orderNumberText.split("\\|")[1].trim();
	}
	

}
