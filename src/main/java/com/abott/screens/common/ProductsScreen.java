package com.abott.screens.common;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.testng.Assert;
import utlilities.UIHelper;

public class ProductsScreen {
    private final UIHelper app;

    public ProductsScreen(UIHelper app){
        this.app= app;
    }

    public UIHelper screenTitle() {
        return app.setBy(By.id("toolbar_title"));
    }
    public UIHelper cartIcon() {
        return app.setBy(By.id("appbar_btn_cart"));
    }

    public UIHelper addToCart_Btn(String productName) {
        return app.setBy(By.xpath("//*[@text='"+productName+"']/..//*[@text='ADD TO CART']"));
    }

    public UIHelper addedToCart() {
        return app.setBy(By.xpath("//*[@text='ADDED TO CART']"));
    }

    //METHODS
    @Step("Verify screen title = 'Products'")
    public boolean verifyScreen() throws Exception {
        System.out.println("Verify screen title = 'Products'");
        return screenTitle().isElementPresent(10) && screenTitle().getText().equals("Products");
    }

    @Step("Verify Cart icon is displayed at the top right on Products screen")
    public void verifyCart() throws Exception {
        System.out.println("Verify Cart icon is displayed at the top right on Products screen");
        Assert.assertTrue(cartIcon().isElementPresent(3),"Cart is NOT displayed on Products screen");
    }

    @Step("Verify user is able to add product to the cart")
    public void addProductToCart(String productName) throws Exception {
        System.out.println("Adding product : "+productName+" to the Cart");
        try {
            app.androidScrollToText(productName);
            addToCart_Btn(productName).click();
        }
        catch(Exception e) {
            System.out.println(productName+" is not present in the list of products");
            e.printStackTrace();
        }
    }

    @Step("Verify button 'Add to Cart' is changed to -> 'Added to Cart' after adding product to Cart")
    public void verifyAddedToCart_btns() throws Exception {
        System.out.println("Verify button 'Add to Cart' is changed to -> 'Added to Cart' after adding product to Cart");
        int size = addedToCart().getAll().size();
        Assert.assertEquals(size, 2,"Both products are NOT added successfully");
    }

    @Step("Click Cart button to open the list of items added to the Cart")
    public void tapCart() throws Exception {
        System.out.println("Click Cart button to open the list of items added to the Cart");
        cartIcon().click();
    }
}
