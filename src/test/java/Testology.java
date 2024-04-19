import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Testology {
    WebDriver driver;
    String url = "https://rahulshettyacademy.com/loginpagePractise/";
    By hyperlink = By.xpath("//a[contains(text(),'Free Access to InterviewQues/ResumeAssistance/Mate')]");
    By username = By.id("username");
    By getEmailLink = By.xpath("//li[normalize-space()='contact@rahulshettyacademy.com']");

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.navigate().to(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void loginPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.visibilityOfElementLocated(hyperlink)).click();
        String originalWindow = driver.getWindowHandle();

        //Wait for the new window or tab
        wait.until(numberOfWindowsToBe(2));
        //Loop through until we find a new window handle
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }

        }
        //Wait for the new tab to finish loading content
        wait.until(titleIs("RS Academy"));
        WebElement getEmail = driver.findElement(getEmailLink);
        String emailText = getEmail.getText();
        //close the current tab
        driver.close();
        //go to original window
        driver.switchTo().window(originalWindow);
        driver.findElement(username).sendKeys(emailText);

    }
    
    @AfterTest
    public void TearDown(){
        driver.quit();
    }

}
