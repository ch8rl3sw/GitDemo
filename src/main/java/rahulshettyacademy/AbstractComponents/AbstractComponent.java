package rahulshettyacademy.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import rahulshettyacademy.pageobjects.OrdersPage;
import rahulshettyacademy.pageobjects.ProductCart;

public class AbstractComponent {
	
	protected WebDriver driver;
	
	public AbstractComponent(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[routerlink*='cart']")
	WebElement cartLink;
	
	@FindBy(css="[routerlink*='myorders']")
	WebElement orderHeader;


	public void waitForElementToAppear(By findBy) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));		
	}
	
	public void waitForWebElementToAppear(WebElement findBy) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(findBy));		
	}
	
	public ProductCart goToCartPage() {
		waitForElementToBeClickable(cartLink);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
				cartLink);
		ProductCart productCart = new ProductCart(driver);
		return productCart;
	}
	
	public OrdersPage goToOrdersPage() {
		waitForElementToBeClickable(orderHeader);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
				orderHeader);
		OrdersPage orderPage = new OrdersPage(driver);
		return orderPage;
	}
	
	public void waitForElementToDisappear(WebElement elem) throws InterruptedException {
		Thread.sleep(2000);
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//		wait.until(ExpectedConditions.invisibilityOf(elem));
	}
	
	public void waitForElementToBeClickable(WebElement elem)  {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(elem));
	}

}
