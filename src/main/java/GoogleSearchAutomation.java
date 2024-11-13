import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;

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

            // Step 2: Perform the search
            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys("automation wikipedia english");
            searchBox.submit();

            // Step 2: Find the English Wikipedia link for automation using XPath
            WebElement wikipediaLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href, 'en.wikipedia.org')]")  // Ensuring it is the English Wikipedia
            ));
            wikipediaLink.click();

            // Step 3: Extract the first year mentioned in the Wikipedia page
            WebElement articleContent = driver.findElement(By.className("mw-page-container"));  // The main content section of the Wikipedia page
            String pageText = articleContent.getText(); // Extract the text from the article content

            // Use regex to find the first 3-digit year
            Pattern pattern = Pattern.compile("\\b\\d{3}\\b");  // Match any 3-digit number (likely a year)
            Matcher matcher = pattern.matcher(pageText);

            // Find and print the first year
            if (matcher.find()) {
                String firstYear = matcher.group();
                System.out.println("First year mentioned and likely the first year of an automated process: " + firstYear);
            } else {
                System.out.println("No year found.");
            }

            // Step 4: Take a screenshot of the Wikipedia page
            File screenshotFile = new File("screenshot.png");
            if (screenshotFile.exists()) {
                screenshotFile.delete(); // Delete the existing file
            }

            System.out.println("Screenshot taken and saved as screenshot.png");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
