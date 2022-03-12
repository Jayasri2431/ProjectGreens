package org.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.helper.DataUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public static WebDriver driver;

	public static WebDriver browserLaunch(String browserName) {
		switch (browserName) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;

		default:
			break;
		}
		return driver;
	}

	// url
	public static void urlLaunch(String url) {
		driver.get(url);
		driver.manage().window().maximize();
	}

	// wait
	public static void implicitWait(long sec) {
		driver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
	}

	// get current url
	public static String getCurrentUrl() {
		String currentUrl = driver.getCurrentUrl();
		return currentUrl;
	}

	// get Title
	public static String getTitle() {
		
		String title = driver.getTitle();
		return title;
	}

	// quit
	public static void quit() {
		driver.quit();
	}

	// sendkeys
	public static void sendkeys(WebElement e, String user) {
		e.sendKeys(user);
	}

	// get Text
	public static String getText(WebElement e) {
		String text = e.getText();
		return text;
	}

	// get Attributes
	public static String getAttribute(WebElement e) {
		String attribute = e.getAttribute("value");
		return attribute;
	}

	// click
	public static void btnClick(WebElement e) {
		e.click();
	}

	// clear
	public static void clear(WebElement e) {
		e.clear();
	}

	// close
	public static void close() {
		driver.close();
	}

	// movetoElement
	public static void movetoElement(WebElement e) {
		Actions a = new Actions(driver);
		a.moveToElement(e).perform();
	}

	// dragAndDrop
	public static void dragAndDrop(WebElement src, WebElement des) {
		Actions a = new Actions(driver);
		a.dragAndDrop(src, des).perform();
	}

	// doubleClick
	public static void doubleClick(WebElement e) {
		Actions a = new Actions(driver);
		a.doubleClick(e).perform();
	}

	// contextClick
	public static void contextClick(WebElement e) {
		Actions a = new Actions(driver);
		a.contextClick(e).perform();
	}

	// select by index
	public static void selectByIndex(WebElement e, int index) {
		Select s = new Select(e);
		s.selectByIndex(index);
	}

	// selectByValue
	public static void selectByValue(WebElement e, String value) {
		Select s = new Select(e);
		s.selectByValue(value);
	}

	// selectByVisibleText
	private void selectByVisibleText(WebElement e, String value) {
		Select s = new Select(e);
		s.selectByVisibleText(value);
	}

	// deselect by index
	public static void deselectByIndex(WebElement e, int index) {
		Select s = new Select(e);
		s.deselectByIndex(index);
	}

	// deselectByValue
	public static void deselectByValue(WebElement e, String value) {
		Select s = new Select(e);
		s.deselectByValue(value);
	}

	// deselectByVisibleText
	public static void deselectByVisibleText(WebElement e, String value) {
		Select s = new Select(e);
		s.deselectByVisibleText(value);
	}

	// single or multi
	public static boolean isMultiple(WebElement e) {
		Select s = new Select(e);
		boolean multiple = s.isMultiple();
		return multiple;
	}

	// deselectAll
	public static void deselectAll(WebElement e) {
		Select s = new Select(e);
		s.deselectAll();
	}

	// findElement
	public static WebElement findElement(String locatorName, String locator) {
		WebElement e = null;
		if (locatorName.equals("id")) {
			e = driver.findElement(By.id(locator));
		} else if (locatorName.equals("name")) {
			e = driver.findElement(By.name(locator));
		} else if (locatorName.equals("xpath")) {
			e = driver.findElement(By.xpath(locator));
		} else if (locatorName.equals("className")) {
			e = driver.findElement(By.className(locator));
		}
		return e;
	}

	// simpleAlert
	public static String simpleAlert(WebElement e) {
		e.click();
		Alert simple = driver.switchTo().alert();
		String text = simple.getText();
		simple.accept();
		return text;
	}

	// confirmAlert
	public static String confirmAlert(WebElement e) {
		e.click();
		Alert conform = driver.switchTo().alert();
		String text = conform.getText();
		conform.accept();
		return text;
	}

	// promptAlert
	public static String promptAlert(WebElement e, String user) {
		e.click();
		Alert prompt = driver.switchTo().alert();
		e.sendKeys(user);
		String text = prompt.getText();
		prompt.accept();
		return text;
	}

	// TakesScreenShot
	public static File TakesScreenshot() {
		TakesScreenshot tk = (TakesScreenshot) driver;
		File src = tk.getScreenshotAs(OutputType.FILE);
		return src;
	}

	// Excel
	public static String getExcel(String filename, String sheetName, int sheetRow, int sheetCell) throws IOException {
		File loc=new File("C:\\Users\\S.Srinivasan\\eclipse-workspace\\MavenProject\\src\\test\\resources\\"+filename+".xlsx");
		FileInputStream fi=new FileInputStream(loc);
		Workbook w=new XSSFWorkbook(fi);
        Sheet s = w.getSheet(sheetName);
		Row row =s.getRow(sheetRow);
		Cell cell= row.getCell(sheetCell);
		int type = cell.getCellType();
		
		String value;
		
		if(type==1) {
	         value = cell.getStringCellValue();
		}
		else {
			if(DateUtil.isCellDateFormatted(cell)) {
			Date date = cell.getDateCellValue();
			SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyy");
			value = sf.format(date);
			}
			else {
				double num = cell.getNumericCellValue();
				long ln=(long)num;
				value = String.valueOf(ln);
				}
		}
		return value;
		

	}

}
