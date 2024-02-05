package pageObjects;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.fasterxml.jackson.core.sym.Name;

import freemarker.log.Logger;
import utilityFiles.ExcelUtils;
import utilityFiles.Screenshots;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	// Elements
	
	@FindBy(xpath="//div[@class='row qlc']//div[@class='col-lg-2']")
	WebElement home;					
	
	@FindBy(xpath = "//div[@class='col-lg-12 pl-0']/ul/li[3]")
	WebElement newBikes;
	
	@FindBy(xpath="//span[@onclick=\"goToUrl('/upcoming-bikes')\"]")
	WebElement UpcomingBikes;
	

	@FindBy(xpath="//select[@id='makeId']")
	WebElement manufactureres;
	
	@FindBy(xpath="//h1[@class='mt-0 pt-2 pull-left zm-cmn-colorBlack']")
	WebElement honda;
	
	@FindBy(xpath ="//li[@class='txt-c clr moreModels mb-20']//span[@class='zw-cmn-loadMore']")
	WebElement viewMoreBikes;
	
	@FindBy(xpath="//ul[@class='mk-row srp_main_div clr']//li[contains(@class,'col-lg-4 txt-c rel modelItem')]")
	List<WebElement> bikesAvailable;
	
	@FindBy(xpath="//a[@data-track-label='model-name']")	
	List<WebElement> names;
	
	@FindBy(xpath="//a[@data-track-label='model-name']//following-sibling::div[1]")
	List<WebElement> prices;
	
	@FindBy(xpath="//a[@data-track-label='model-name']//following-sibling::div[2]")
	List<WebElement> dates;
	
	//Actions
	
	public void clickUpcomingBikes() throws InterruptedException
	{
		Actions action = new Actions(driver);
		action.moveToElement(newBikes).build().perform();
		
		Thread.sleep(3000);
		UpcomingBikes.click();
	}
	
	public void selectBikeManufacturer() throws InterruptedException {
		Select objSelect = new Select(manufactureres);
		objSelect.selectByVisibleText("Honda");
	}
	
	public void scroll() throws InterruptedException, IOException {
		
		Screenshots sc = new Screenshots(driver);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,1200);", "");
		sc.ScreenShot("bikes");
		Thread.sleep(2000);		
		viewMoreBikes.click();
		js.executeScript("window.scrollBy(1200,1250);", "");
		Thread.sleep(3000);
	}
	
	public void bikeDetails() throws InterruptedException, IOException {
		
		int k = 1;
		for(int i=0;i<names.size();i++) {
			if(Integer.parseInt(bikesAvailable.get(i).getAttribute("data-price"))<400000 ) {
				System.out.println(names.get(i).getText()+" : "+prices.get(i).getText()+" : "+dates.get(i).getText());
				String file = System.getProperty("user.dir")+"\\testData\\data_hackathon.xlsx";
				ExcelUtils ex = new ExcelUtils();
				ex.setCellData(file, "Sheet1", k, 0, names.get(i).getText());
				ex.setCellData(file, "Sheet1", k, 1, prices.get(i).getText());
				k++;
			}

		}
	}
	
	public void home() throws InterruptedException {
		home.click();
		Thread.sleep(1000);
	}
	

}








