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

WebUI.openBrowser('')

WebUI.navigateToUrl('https://www.autohero.com/it/search/')

WebUI.click(findTestObject('autohero/dropdown_immatricolazione'))

WebUI.selectOptionByValue(findTestObject('autohero/immFrom2015'), '6', true)

WebUI.selectOptionByValue(findTestObject('autohero/immTo2020'), '1', true)

WebUI.click(findTestObject('autohero/dropdown_km'))

WebUI.selectOptionByValue(findTestObject('autohero/kmTo5k'), '1', true)

WebUI.click(findTestObject('autohero/dropdown_km'))

WebUI.verifyElementPresent(findTestObject('autohero/buttonImmatricolazioneFilter'), 20)

WebUI.verifyElementPresent(findTestObject('autohero/buttonKmFilter'), 20)

WebUI.selectOptionByValue(findTestObject('autohero/selectHighPrice'), '2', true)

WebUI.openBrowser('')

WebUI.navigateToUrl('https://www.autohero.com/it/search/')

WebUI.click(findTestObject('Object Repository/autohero/dropdown_immatricolazione'))

WebUI.selectOptionByValue(findTestObject('Object Repository/autohero/immFrom2015'), '6', true)

WebUI.selectOptionByValue(findTestObject('Object Repository/autohero/immTo2020'), '1', true)

WebUI.click(findTestObject('Object Repository/autohero/dropdown_km'))

WebUI.selectOptionByValue(findTestObject('Object Repository/autohero/kmTo5k'), '1', true)

WebUI.click(findTestObject('Object Repository/autohero/dropdown_km'))

WebUI.selectOptionByValue(findTestObject('Object Repository/autohero/selectHighPrice'), '2', true)

WebUI.verifyElementPresent(findTestObject('Object Repository/autohero/buttonImmatricolazioneFilter'), 20)

WebUI.verifyElementPresent(findTestObject('Object Repository/autohero/buttonKmFilter'), 20)

/*WebElement element = WebUiCommonHelper.findWebElement(findTestObject('autohero/yearOfCar'), 30)

List<WebElement> rows = element.findElements(By.className('infoContainer___1OcRd'))
System.out.println(rows.size())*/
//data-qa-selector="spec"
WebDriver driver = DriverFactory.getWebDriver()

ArrayList<WebElement> fields = driver.findElements(By.cssSelector('[data-qa-selector=\'spec\']'))

for (int i = 0; i < fields.size(); i++) {
    System.out.println((fields[i]).getText())
}

int rows_count5 = fields.size()

System.out.println(rows_count5)


while (!Mobile.verifyElementVisible(findTestObject('autohero/react_grid'), 5)){
	
	WebUI.scrollToPosition(0, 20)
	
}


