package com.abott.screens.common;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.testng.Assert;
import utlilities.UIHelper;

public class GoogleScreen {
    private final UIHelper app;

    public GoogleScreen(UIHelper app){
        this.app= app;
    }

    public UIHelper screenTitle() {
        return app.setBy(By.xpath("//*[@text='Google' and @class='android.widget.Image']"));
    }

    public UIHelper googleSearchBox() {
        return app.setBy(By.xpath("//*[@class='android.widget.EditText']"));
    }

    public UIHelper googleSearch_btn() {
        return app.setBy(By.xpath("//*[@text='Google Search']"));
    }

    //METHODS
    @Step("Verify google webpage is displayed with Google logo")
    public boolean verifyScreen() throws Exception {
        System.out.println("Verify google webpage is displayed with Google logo");
        return screenTitle().isElementPresent(10);
    }

    @Step("Enter the text to search on Google..")
    public void enterInputToSearchOnGoogle(String searchInput) throws Exception {
        System.out.println("Enter the text to search on Google..");
        Assert.assertTrue(googleSearchBox().isElementPresent(3),"Google search bar is NOT displayed");
        googleSearchBox().sendkeys(searchInput);
    }

    @Step("Verify User is able to browse on Google successfully")
    public void clickSearch(AndroidKey key) throws Exception {
        System.out.println("Verify User is able to browse on Google successfully");
        app.pressKeyboardKey(key);
    }
}
