import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import org.openqa.selenium.WebDriver
import com.kms.katalon.core.webui.driver.DriverFactory

import org.openqa.selenium.WebElement as WebElement
import org.junit.After
import org.openqa.selenium.By
import com.kms.katalon.core.util.KeywordUtil


WebUI.openBrowser('')
WebUI.maximizeWindow()

WebUI.navigateToUrl('https://www.autohero.com/it/search/')

WebDriver driver = DriverFactory.getWebDriver()

WebUI.click(findTestObject('autohero/dropdown_immatricolazione'))

WebUI.selectOptionByValue(findTestObject('autohero/immFrom2015'), '5', true) // seleziono immatricolazione da 2016....

WebUI.selectOptionByValue(findTestObject('autohero/immTo2020'), '1', true) // ...a 2020

WebUI.click(findTestObject('autohero/dropdown_km'))

WebUI.selectOptionByValue(findTestObject('autohero/kmTo5k'), '1', true) // seleziono da dropdown il chilometraggio to:5000km

WebUI.click(findTestObject('autohero/dropdown_km')) // chiudo dropdown

WebUI.verifyElementPresent(findTestObject('autohero/buttonImmatricolazioneFilter'), 20) //checkpoint con verifica presenza filtro immatricolazione

WebUI.verifyElementPresent(findTestObject('autohero/buttonKmFilter'), 20) //checkpoint con verifica presenza filtro chilometraggio

//WebUI.selectOptionByValue(findTestObject('autohero/selectHighPrice'), '2', true) // ordino per prezzo alto

int totalHeight =WebUI.getElementHeight(findTestObject('autohero/react_grid'))
int scrollAction=3; // scroll pagina in 3 passi

boolean checkImmatricolazione = false
boolean checkOrderPrice = false // variabili booleane di controllo



while (scrollAction>0){
	int minimumValuePrice = -1
	
	ArrayList<WebElement> listOfAttributes = driver.findElements(By.cssSelector('[data-qa-selector=\'spec\']')) // Navigo questo array con step di 4
	ArrayList<WebElement> fieldsPrice = driver.findElements(By.cssSelector('[data-qa-selector=\'price\']')) // Navigo questo array con step di 1
	
	for (j = 0; j < (listOfAttributes.size()); j += 4) {
		int valuePrice = (int)j/4 // correggo il disallineamento degli indici tra gli array sopra citati
		
		int intPrice =Integer.parseInt( fieldsPrice[valuePrice].getText().replace(".", "").replace(" €", "")).value //ripulisco stringa per effettuarne il parsing
		
		int annoImmatricolazione = Integer.parseInt( listOfAttributes[j].getText())
		if (j==0){
			minimumValuePrice = intPrice // il primo prezzo osservato lo reputo il più piccolo
		} else if (intPrice > minimumValuePrice){
			checkOrderPrice = true // se si presentassero prezzi inferiori al primo osservato, il booleano evidenzierà la presenza di un errore
		}
		
		if (annoImmatricolazione < 2016 ) // se si presentasse un'immatricolazione inferiore al 2016, il booleano evidenzierà la presenza di un errore
			checkImmatricolazione = true
	}
	
	int scrolling = (int)(totalHeight/scrollAction) // effettuo uno scroll nella pagina per recuperare dinamicamente gli oggetti successivi, che non vedrei diversamente 
	WebUI.scrollToPosition(0,scrolling )
	WebUI.delay(3)
	scrollAction--;
}

if (checkImmatricolazione)
	KeywordUtil.markError("Veicolo immatricolato  controllo data immatricolazione")
	
if (checkOrderPrice)
	KeywordUtil.markError("Mancato ordinamento decrescente")
	
WebUI.closeBrowser()