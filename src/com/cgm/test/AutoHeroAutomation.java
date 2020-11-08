package com.cgm.test;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AutoHeroAutomation {
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
     driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void TestCaseHomePage() throws Exception {
	  /**
	   * Navigo nella search page del sito
	   */
	    driver.get("https://www.autohero.com/it/search/");
	    driver.findElement(By.xpath("//button[@id='firstRegistrationFilter']/span")).click();
	    
	  /**
	   * Attivo filtro immatricolazione 2016 - 2020
       */
	    new Select(driver.findElement(By.xpath("(//select[@id='rangeStart'])[2]"))).selectByVisibleText("2016");
	    driver.findElement(By.xpath("(//option[@value='5'])[3]")).click();
	    new Select(driver.findElement(By.xpath("(//select[@id='rangeEnd'])[2]"))).selectByVisibleText("2020");
	    driver.findElement(By.xpath("(//option[@value='1'])[4]")).click();
	    driver.findElement(By.xpath("//button[@id='firstRegistrationFilter']/span")).click();
	    
		  /**
		   * Attivo filtro chilometraggio < 5.000 km
	       */
	    driver.findElement(By.xpath("//button[@id='kilometersFilter']/span")).click();
	    new Select(driver.findElement(By.xpath("(//select[@id='rangeEnd'])[3]"))).selectByVisibleText("5.000 km");
	    driver.findElement(By.xpath("(//option[@value='1'])[6]")).click();
	    driver.findElement(By.xpath("//button[@id='kilometersFilter']/span")).click();
		  /**
		   * Attivo filtro per prezzo più alto
	       */
	    new Select(driver.findElement(By.id("sortBy"))).selectByVisibleText("Prezzo più alto");
	    driver.findElement(By.xpath("(//option[@value='2'])[15]")).click();
	    
	    
	    // da qui il codice è pressapoco identico a quello utilizzato nel progetto di Katalon
	    try {
	    	
		 /**
		  * Checkpoint sui filtri attivi in pagina
		  */
	 
	    	assertTrue(isElementPresent(By.xpath("//button[contains(text(),'Immatricolazione')]")));
	    	assertTrue(isElementPresent(By.xpath("//button[contains(text(),'Chilometraggio')]")));
	        //assertTrue(isElementPresent(By.xpath("(//button[@type='button'])[12]")));
	      } catch (Error e) {
	        verificationErrors.append(e.toString());
	      }
	    WebElement ele = driver.findElement(By.className("ReactVirtualized__Grid__innerScrollContainer"));
	    int totalHeight = ele.getSize().getHeight();

	    int scrollAction=3; // scroll pagina in 3 passi

	    boolean checkImmatricolazione = false;
	    boolean checkOrderPrice = false; // variabili booleane di controllo
	    
		  /**
		   * Itero sul datagrid, effettuando uno scroll dinamico della pagina
		   * per poter recuperare le informazioni utili ( prezzi e data di immatricolazione )
	       */
	    
	    while (scrollAction>0){
	    	int minimumValuePrice = -1;
	    	
	    	ArrayList<WebElement> listOfAttributes = (ArrayList<WebElement>) driver.findElements(By.cssSelector("[data-qa-selector=\'spec\']")); // Navigo questo array con step di 4
	    	ArrayList<WebElement> fieldsPrice = (ArrayList<WebElement>) driver.findElements(By.cssSelector("[data-qa-selector=\'price\']")); // Navigo questo array con step di 1
	    	
	    	
	    	for (int j = 0; j < (listOfAttributes.size()); j += 4) {
	    		int valuePrice = (int)j/4; // correggo il disallineamento degli indici tra gli array sopra citati
	    		
	    		
	    		int intPrice =Integer.parseInt( fieldsPrice.get(valuePrice).getText().replace(".", "").replace(" €", "")); //ripulisco stringa per effettuarne il parsing
	    		
	    		int annoImmatricolazione = Integer.parseInt( listOfAttributes.get(j).getText());
	    		if (j==0){
	    			minimumValuePrice = intPrice; // il primo prezzo osservato lo reputo il più piccolo
	    		} else if (intPrice > minimumValuePrice){
	    			checkOrderPrice = true; // se si presentassero prezzi inferiori al primo osservato, il booleano evidenzierà la presenza di un errore
	    		}
	    		
	    		if (annoImmatricolazione < 2016 ) // se si presentasse un'immatricolazione inferiore al 2016, il booleano evidenzierà la presenza di un errore
	    			checkImmatricolazione = true;
	    	}
	    	
	    	int scrollingY = (int)(totalHeight/scrollAction); // effettuo uno scroll nella pagina per recuperare dinamicamente gli oggetti successivi, che non vedrei diversamente 
	    	
	    	JavascriptExecutor jse = (JavascriptExecutor)driver;
	    	jse.executeScript("window.scrollBy(0,"+scrollingY+")");
	    	

	    	Thread.sleep(2000);
   			scrollAction--;
	    }
	    
		  /**
		   * Checkpoint sull'ordinamento del prezzo e la data di immatricolazione
	       */
	    Assert.assertEquals(checkImmatricolazione, false);
	    Assert.assertEquals(checkOrderPrice, false);	
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

}
