import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GoogleSearchAutomation {
    public static void main(String[] args) {
        // Setup Firefox WebDriver using WebDriverManager
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();

        try {
            // Step 1: Launch Google and search for 'automation'
            driver.get("https://www.google.com");

            // Handle the cookie dialog (for Google, it’s a common element)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Correct usage with Duration
            try {
                WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("L2AGLb"))); // Google Accept Cookies button
                acceptCookiesButton.click();
            } catch (Exception e) {
                System.out.println("No cookie dialog appeared.");
            }

            // Perform the search
            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys("automation");
            searchBox.submit();

            // Step 2: Find the Wikipedia link for automation using XPath
            WebElement wikipediaLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href, 'wikipedia.org')]")
            ));
            wikipediaLink.click();

            // Step 3: Find the year of the first automatic process (assuming it is in the infobox)
            WebElement infobox = driver.findElement(By.className("infobox"));
            String firstProcessYear = infobox.getText(); // This would need to be refined based on the actual structure

            // Print the result (this is just an example - adjust based on page content)
            System.out.println("First automatic process year (from Wikipedia page): " + firstProcessYear);

            // Step 4: Take a screenshot of the Wikipedia page
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get("screenshot.png"));

            System.out.println("Screenshot taken and saved as screenshot.png");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
