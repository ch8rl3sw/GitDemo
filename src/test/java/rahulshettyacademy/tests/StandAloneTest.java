package rahulshettyacademy.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;

public class StandAloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String productName = "zara coat 3";
		String country = "United States";
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.manage().window().maximize();
		driver.get("http://rahulshettyacademy.com/client");
		LandingPage landingPage = new LandingPage(driver);
		
		driver.findElement(By.id("userEmail")).sendKeys("huskies@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Cr8zyd0gs");
		driver.findElement(By.id("login")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".offset-sm-1")));
		
		List<WebElement> products = 
				driver.findElements(By.cssSelector(".offset-sm-1"));
		
		// Find WebElement for the "zara coat 3" product
		WebElement desiredProduct = products.stream().filter(p -> 
			getProductTitle(p).equalsIgnoreCase(productName)).findFirst().orElse(null);
		
		if (desiredProduct != null) {
			// Click Add To Cart button for "zara coat 3"
			desiredProduct.findElement(
					By.cssSelector(".card-body button:last-of-type")).click();
			
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
			
			wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
			
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[routerlink*='cart']")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", 
					driver.findElement(By.cssSelector("[routerlink*='cart']")));
			
			List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
			
			boolean match = cartProducts.stream().anyMatch(p -> p.getText().equalsIgnoreCase(productName));
			Assert.assertTrue(match);
			
			driver.findElement(By.cssSelector(".totalRow button")).click();
			
			// Enter credit card number
			WebElement creditCardElement = driver.findElement(By.xpath("//div[contains(text(),'Credit Card Number')]/following-sibling::input"));
			creditCardElement.clear();
			creditCardElement.sendKeys("1234 5678 9012 3456");
			
			// Provide credit card expiration date
			WebElement expiryDateMonth = 
					driver.findElement(By.xpath("//div[contains(text(), 'Expiry Date')]/following-sibling::select"));
			Select monthSelect = new Select(expiryDateMonth);
			monthSelect.selectByVisibleText("02");
			
			WebElement expiryDateYear = expiryDateMonth.findElement(By.xpath("following-sibling::select"));
			Select yearSelect = new Select(expiryDateYear);
			yearSelect.selectByVisibleText("26");
			
			// Provide CVV
			driver.findElement(By.xpath("//div[contains(text(), 'CVV Code')]/following-sibling::input")).sendKeys("317");
			
			// Provide credit card name
			driver.findElement(By.xpath("//div[contains(text(), 'Name on Card')]/following-sibling::input")).sendKeys("Harley Summer");
			
			// Provide email address
			WebElement email = driver.findElement(By.xpath("//label[contains(text(), 'huskies@gmail.com')]/following-sibling::input"));
			email.clear();
			email.sendKeys("huskies@gmail.com");
			
//			Actions a = new Actions(driver);
//			a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), country).build().perform();
						
			driver.findElement(By.cssSelector("[placeholder='Select Country']")).sendKeys(country);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.ng-star-inserted")));
//			
//			// Select the shipping country
			List<WebElement> suggestedCountries = driver.findElements(By.cssSelector("span.ng-star-inserted"));
			WebElement desiredCountry = 
					suggestedCountries.stream().filter(suggestedCountry -> suggestedCountry.getText().equals(country)).findFirst().orElse(null);
			desiredCountry.click();
			
			// Click Place Order
			driver.findElement(By.cssSelector(".action__submit")).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1")));
			
			String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
			Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
			
			String orderNumberText = driver.findElement(By.cssSelector(".em-spacer-1 label.ng-star-inserted")).getText();
			String orderNumber = orderNumberText.split("\\|")[1].trim();
			System.out.println("Order number is " + orderNumber);
			
			driver.close();
		
		}
		

	}
	
	private static String getProductTitle(WebElement p) {
//		String productTitle = p.findElement(
//				By.xpath("div[@class='card']/div[@class='card-body']/h5/b"))
//				.getText();
		String productTitle = p.findElement(By.cssSelector("b")).getText();
		return productTitle;
	}

}
