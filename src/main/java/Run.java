import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class Run {
    static WebDriver driver;
    public static void main(String[] args) throws IOException {
        driver= WebDriverManager.chromedriver().create();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().deleteAllCookies();
        driver.get("http://www.deadlinkcity.com/");
        checkBrokenLinks();
    }
    public static void checkBrokenLinks() throws IOException {
        int brokenLinks=0;
         List<WebElement> myLinks=driver.findElements(By.tagName("a"));
         for(WebElement element:myLinks){
             String url=element.getAttribute("href");
             if(url==null || url.isEmpty()){
                 continue;
             }
             try {
                 URL link = new URL(url);
                 HttpURLConnection httpURLConnection = (HttpURLConnection) link.openConnection();
                 httpURLConnection.connect();
                 if(httpURLConnection.getResponseCode()>=400){
                     System.out.println(httpURLConnection.getResponseCode()+" "+url+" Is Broken Link");
                     brokenLinks++;
                 }
             }catch (MalformedURLException e){
                 e.printStackTrace();
             }

         }

        System.out.println(brokenLinks);



    }
}
