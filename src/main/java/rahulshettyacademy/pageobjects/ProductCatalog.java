package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class ProductCatalog extends AbstractComponent {
	
	public ProductCatalog(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);		
	}
	
	//WebElement userEmail = driver.findElement(By.id("userEmail"));
	// Page Factory design pattern
	
	@FindBy(css=".offset-sm-1")
	List<WebElement> products;
	
	@FindBy(css=".ng-animating")
	WebElement spinner;
	
	@FindBy(css="[routerlink*='cart']")
	WebElement cartLink;
	
	By productsBy = By.cssSelector(".offset-sm-1");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");
	
	public List<WebElement> getProductList() {
		waitForElementToAppear(productsBy);
		return products;
	}
	
	public WebElement getProductByName(String productName) {
		WebElement desiredProduct = products.stream().filter(p -> 
			p.findElement(By.cssSelector("b")).getText().equalsIgnoreCase(
					productName)).findFirst().orElse(null);
		return desiredProduct;
	}
	
	public void addProductToCart(String productName) throws InterruptedException {
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click();
		waitForElementToAppear(toastMessage);
		waitForElementToDisappear(spinner);
	}
	
	public ProductCart goToCart() {
		waitForElementToBeClickable(cartLink);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
				cartLink);
		ProductCart productCart = new ProductCart(driver);
		return productCart;
	}

}
