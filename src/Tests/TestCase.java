package Tests;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

public class TestCase { private static ChromeDriverService service;
public static WebDriver driver;
public static String baseUrl;


@BeforeClass
public static void createAndStartService() throws IOException {
    service = new ChromeDriverService.Builder()
       .usingDriverExecutable(new File("C:\\webdeiver\\chromedriver.exe"))
       .usingAnyFreePort()
       .build();
    service.start();
}

@Before
public void setUp(){
    driver = new ChromeDriver(service);
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    baseUrl = "https://www.google.com/ncr";
}

@Test
public void simpleTest() {     
    getUrl(); //1. перейти на сайт http://google.com/ncr
    
    
    
    clearElement("//input[@type='text']");
    sendKeys("//input[@type='text']", "selenium");
    clickByElement("//button[@name='btnG']");//2. Выполнить поиск по слову "selenium"
    assertText("//div[1]/div[@class='rc']/.//cite", "www.seleniumhq.org/"); //3. ожидается, что первый результат в выдаче ведет на какую-то страницу сайта seleniumhq.org. Если в начале поисковой выдачи появились какие-то виджеты, то их не учитывать (виджет Википедии, виджет "Top stories" и т.п.)
    log("Выполнен поиск по слову Selenium, результат поиска ведет на www.seleniumhq.org");
    clickByElement("//div[@id='hdtb-msb']/.//a[text()='Images']");// 4. перейти на вкладку "Images"
    log("Перешли на вкладку images");
    clickByElement("//*[@id='rg_s']/div[1]/a/img");
    sleep(1);
    assertText(".//*[@id='irc_cc']/div[2]/div[3]/div[1]/div[1]/div/span[1]/a/span", "www.seleniumhq.org"); //3. ожидается, что первый результат в выдаче ведет на какую-то страницу сайта seleniumhq.org. Если в начале поисковой выдачи появились какие-то виджеты, то их не учитывать (виджет Википедии, виджет "Top stories" и т.п.)    //5. ожидается, что первая картинка в выдаче как-либо относится к сайту seleniumhq.org
    log("Первая ссылка ведет на seleniumhq.org");
    clickByElement("//div[@id='hdtb-msb']/.//a[text()='All']");// 6. вернуться на вкладку "All"
    log("Открыта вкладка all");
    assertText("//div[1]/div[@class='rc']/.//cite", "www.seleniumhq.org/"); 
    log("Первая ссылка ведет на www.seleniumhq.org");//7. ожидается, что первый результат по-прежнему ведет на ту же страницу, что и на шаге 3.

}

private void sleep(int seconds) {
	try {
		TimeUnit.SECONDS.sleep(seconds);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

private void log(String textLog) {
	System.out.println(textLog);
}

private void assertText(String locator, String text) {
	assertTrue(driver.findElement(By.xpath(locator)).getText().contains(text));
}



private void getUrl() {
	driver.get(baseUrl);
}

private void clearElement(String locator) {
	driver.findElement(By.xpath(locator)).clear();
}

private void sendKeys(String locator, String text) {
	driver.findElement(By.xpath(locator)).sendKeys(text);
}

private void clickByElement(String locator) {
	driver.findElement(By.xpath(locator)).click();
}

@After
public void tearDown(){
    driver.quit();
}

@AfterClass
public static void createAndStopService() {
    service.stop();
}
}
