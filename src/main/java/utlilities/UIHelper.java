package utlilities;

import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static java.time.Duration.ofMillis;

public class UIHelper {

    private WebDriver driver;
    public By by;
    public Properties prop;
    private WebElement element;
    private String platform;
    private String execution ;
    Properties prop1;
    int timeOut = 3;
    public static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();
    DesiredCapabilities cap;

    public UIHelper() throws Exception {
        propertiesLoadFile();
        if (isAndroid())
            android_LaunchApp();
    }

    public WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    public void android_LaunchApp() throws IOException {
        try {
            System.out.println("Trying to Launch Android");
             cap = new DesiredCapabilities();
            if (execution.equalsIgnoreCase("LOCAL")) {
                System.out.println("Android Local execution");
                cap = new DesiredCapabilities();
                cap.setCapability("platformName", "Android");
                cap.setCapability("app", prop.get("localApkFilePath"));
                cap.setCapability("noReset", false);
                cap.setCapability("fullReset", true);
                cap.setCapability("automationName", "UiAutomator2");
                cap.setCapability("autoGrantPermissions", true);
                cap.setCapability("autoLaunch", true);
                cap.setCapability("appWaitActivity", "*.SplashActivity");
                cap.setCapability("adbExecTimeout", 200000);
                cap.setCapability("deviceName", prop.getProperty("udid"));
                cap.setCapability("settings[waitForIdleTimeout]", 100);
                cap.setCapability("enforceXPath1", true);
                threadLocalDriver.set(new AndroidDriver<MobileElement>(new URL("http://localhost:4723/wd/hub"), cap)); //WD/Hub path is no longer valid with new Appium
                driver = getDriver();
            }
        }
        catch (Exception e) {
            System.out.println("Error occurred while setting up the Android Drvier" +e.getMessage());
            }
        }

        public void propertiesLoadFile() throws IOException {
          prop1 = new Properties();
          System.out.println(System.getProperty("user.dir"));
          FileInputStream configFile = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/testdata/config.properties");
          prop1.load(configFile);
            String platform = "";
            if (System.getProperty("os") == null)
                platform = prop1.getProperty("os");
            else
                platform = System.getProperty("os");
            this.platform = platform;
            System.out.println("platform Supplied is : " + platform);

            String execution = "";
            if (System.getProperty("execution") == null)
                execution = prop1.getProperty("execution");
            else
                execution = System.getProperty("execution");
            this.execution = execution;
            System.out.println("execution Supplied is : " + execution);
            System.out.println("user.directory = " + System.getProperty("user.dir"));//Ans= /Users/kami/IdeaProjects/Test
        prop = new Properties();
        FileInputStream f = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/testdata/"+platform+"_config.properties");
        prop.load(f);
    }

    public UIHelper setBy(By android) {
        if(isAndroid())
            by = android;
        return this;
    }

    public boolean isAndroid() {
        return platform.equalsIgnoreCase("android");
    }

    public String getPlatform() {
        return this.platform;
    }

    public void sendkeys(String data) {
        try {
            element = waitForElement();
            element.click();
            element.clear();
            element.sendKeys(data);
        } catch (Exception e) {
            System.out.println("Failed to type : " + e);
        }
    }

    public void click() {
        try {
            element = waitForElement(timeOut);
            element.click();
        } catch (Exception e) {
            System.out.println("click not worked 1st time. Retrying again");
            element.click();
        }
    }

    private WebElement waitForElement() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementToBeVisible() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by)).get(0);
    }

    public boolean isElementPresent(int timeout) throws Exception {
        try {
            waitForElement(timeout);
            return true;
        }
        catch (Exception e) {
            System.out.println("Element not found : "+e.getMessage());
            return false;
        }
    }
    private WebElement waitForElement(int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public boolean isElementVisible(int timeout) {
        try {
            timeOut = timeout;
            element = waitForElementToBeVisible();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getText() throws Exception {
        element = waitForElement();
        return element.getText();
    }

    public String getAttribute(String attributeName) throws Exception {
        element = waitForElement();
        return element.getAttribute(attributeName);
    }

    public void androidScrollToText(String text) {
        String uiSelector = "new UiSelector().textMatches(\"" + text + "\")";
        String command = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(" + uiSelector + ");";
        try {
            driver.findElement(MobileBy.AndroidUIAutomator(command));
        } catch (Exception e) {
            System.out.println("Something wrong happens when scrolling to text");
        }
    }

    public List<WebElement> getAll() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void navigateBack() {
        System.out.println("Clicking Back button of Android device");
        ((AndroidDriver) driver).pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }

    public void hideKeyboard() {
            try {
                System.out.println("Hiding keyboard..");
                ((AppiumDriver) driver).hideKeyboard();
            }
            catch (Exception e) {
                System.out.println("Unable to hide keyboard");
                e.getMessage();
            }
    }

    public void pressKeyboardKey(AndroidKey key) {
        System.out.println("Pressing "+key+" of the Android device");
        ((AndroidDriver) driver).pressKey(new KeyEvent().withKey(key));
    }

    public void tapClick() throws Exception {
        element = waitForElement();
        Point point = element.getLocation();
        new TouchAction((PerformsTouchActions) driver)
                .tap(PointOption.point(point.getX(), point.getY()))
                .waitAction(waitOptions(ofMillis(250))).perform();
    }

    public void quitDriver() {
        driver.quit();
    }
}
