package com.abott.screens.common;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import utlilities.UIHelper;

public class GeneralStoreScreen {
    private final UIHelper app;

    public GeneralStoreScreen(UIHelper app){
        this.app= app;
    }

    public UIHelper screenTitle() {
        return app.setBy(By.id("toolbar_title"));
    }

    public UIHelper countryList_dd() {
        return app.setBy(By.id("spinnerCountry"));
    }

    public UIHelper countryName(String country) {
        return app.setBy(By.xpath("//*[@text='"+country+"']"));
    }

    public UIHelper name_txtbox() {
        return app.setBy(By.id("nameField"));
    }

   // public UIHelper male() {
//        return app.setBy(By.id("radioMale")); radioFemale
//    }

    public UIHelper gender(String genderValue) {
        return app.setBy(By.xpath("//*[@text='"+genderValue+"']"));
    }

    public UIHelper letsShop_btn() {
        return app.setBy(By.id("btnLetsShop"));
    }

    public UIHelper errorMsg() {
        return app.setBy(By.xpath("//*[@class='android.widget.Toast' and @text='Please enter your name']"));
    }

    //METHODS
    @Step("Verify screen is 'General Store'")
    public boolean verifyScreen() throws Exception {
        System.out.println("Verify screen title = 'General Store'");
        return screenTitle().isElementPresent(10) && screenTitle().getText().equals("General Store");
    }

    @Step("Select Country name")
    public void selectCountry(String country) throws Exception {
        System.out.println("Select Country name : "+country);
        countryList_dd().click();
        countryList_dd().androidScrollToText(country);
        countryName(country).click();
    }

    @Step("Enter your name")
    public void enterName(String name){
        System.out.println("Enter your name");
        name_txtbox().sendkeys(name);
        app.hideKeyboard();
    }

    @Step("Select Gender : Male/Female")
    public void selectGender(String genderValue){
        System.out.println("Select Gender : Male/Female");
        gender(genderValue).click();
    }

    @Step("Click on 'Let's Shop' CTA")
    public void clickLetsShop(){
        System.out.println("Click on 'Let's Shop' CTA on General Store screen");
        letsShop_btn().click();
    }
}
