package tests;
import environment.EnvironmentManager;
import environment.RunEnvironment;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;
import static java.lang.Thread.sleep;

public class demotest {
    WebDriver driver;
    JavascriptExecutor jse;
    Actions hover;

    @Before
    public void startBrowser() {
        EnvironmentManager.initWebDriver();
        driver = RunEnvironment.getWebDriver();
        driver.manage().window().maximize();
        driver.get("https://www.adidas.co.uk");
        jse = (JavascriptExecutor)driver;
        hover = new Actions(driver);
    }

    @Test
    public void searchProduct() throws InterruptedException {
        WebElement searchbar = driver.findElement(By.name("q"));
        searchbar.sendKeys("NMD");
        searchbar.submit();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        sleep(3000);
        String URL = driver.getCurrentUrl();
        Assert.assertTrue(URL.contains("nmd"));
        System.out.println(URL);
    }

    @Test
    public void scrollforinfo() {
        //Infopage
        WebElement info  = driver.findElement(By.xpath("//a[contains(text(),'Info')]"));
        jse.executeScript("arguments[0].scrollIntoView(true);", info );
        info.click();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        String URL = driver.getCurrentUrl();
        Assert.assertTrue(URL.contains("group"));
        System.out.println(URL);
    }

    @Test
    public void dropdownFrauen() {
        //Header-menu -> Frauen -> Neu
        WebElement frauen = driver.findElement(By.xpath("//a[@class='label'][contains(text(),'Frauen')]"));
        hover.moveToElement(frauen).build().perform();
        driver.findElement(By.xpath("//a[@href='/frauen-neu'][contains(text(),'Neu eingetroffen')]")).click();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        String URL = driver.getCurrentUrl();
        Assert.assertTrue(URL.contains("frauen-neu"));
        System.out.println(URL);
    }


    @Test
    public void inCart()  {
        //Jetzt Kaufen -> first product -> in shopping cart
        driver.findElements(By.xpath("//a[contains(text(),'JETZT KAUFEN')]")).get(0).click();
        driver.findElements(By.xpath("//div[contains(@class, 'product-container___')]//img")).get(0).click();
        driver.findElement(By.className("gl-modal__close")).click();
        jse.executeScript ("scroll(0, 200)");
        driver.findElement(By.xpath("//span[contains(text(),'Wähle deine Größe aus')]")).click();
        driver.findElement(By.xpath("//button[@title='40']")).click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.xpath("//div[@class='gl-modal__main']//a[contains(@data-auto-id, 'view-bag-')]")).click();
        String amount = driver.findElement(By.xpath("//div[@class='col-4 title-wrapper']//span")).getText();
        Assert.assertTrue(amount.contains("1"));
    }

    @Test
    public void stanSmith()  {
        //Stan Smith
        WebElement kollektionen = driver.findElement(By.xpath("//a[contains(text(),'KOLLEKTIONEN')]"));
        hover.moveToElement(kollektionen).build().perform();
        driver.findElement(By.xpath("//a[@href='/stan_smith'][contains(text(),'Stan Smith')]")).click();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        String URL = driver.getCurrentUrl();
        Assert.assertTrue(URL.contains("stan_smith"));
        System.out.println(URL);
    }


   @After
   public void quitBrowser() throws InterruptedException {
        sleep(6000);
        EnvironmentManager.shutDownDriver();

   }
}

