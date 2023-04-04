package com.abott.mobile.testcases;

import com.abott.screens.common.CartScreen;
import com.abott.screens.common.GeneralStoreScreen;
import com.abott.screens.common.GoogleScreen;
import com.abott.screens.common.ProductsScreen;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.qameta.allure.Link;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utlilities.UIHelper;


public class EndToEndTC {
    UIHelper app;
    GeneralStoreScreen generalStoreScreen;
    ProductsScreen productsScreen;
    CartScreen cartScreen;
    GoogleScreen googleScreen;
    String productName1;
    String productName2;
    String country;
    String name;
    String genderValue;

    @BeforeClass(alwaysRun = true)
    public void setup() throws Exception {
         app                    = new UIHelper();
         generalStoreScreen     = new GeneralStoreScreen(app);
         productsScreen         = new ProductsScreen(app);
         cartScreen             = new CartScreen(app);
         googleScreen           = new GoogleScreen(app);
         productName1           = app.prop.getProperty("productName1");
         productName2           = app.prop.getProperty("productName2");
         country                = app.prop.getProperty("country");
         name                   = app.prop.getProperty("username");
         genderValue             = app.prop.getProperty("genderValue");
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() throws InterruptedException {
        app.quitDriver();
    }

    @Link("JIRA LINK OF TC")
    @Test(priority=1, description="Launch the app & verify Home screen")
    public void isHomeScreenDisplayed() throws Exception {
        System.out.println("---Start of the test---");
        System.out.println("Launch the app & verify Home screen");
        Assert.assertTrue(generalStoreScreen.verifyScreen(),"General Store / Home screen is NOT displayed");
    }

    @Test(priority=2, dependsOnMethods="isHomeScreenDisplayed", description="Launch the app & verify Home screen")
    public void testHomescreen() throws Exception {
        generalStoreScreen.selectCountry(country);
        generalStoreScreen.enterName(name);
        generalStoreScreen.selectGender(genderValue);
        generalStoreScreen.clickLetsShop();
    }

    @Test(priority=3, dependsOnMethods="isHomeScreenDisplayed", description="Launch the app & verify Home screen")
    public void verifyProductsScreenIsDisplayed() throws Exception {
        System.out.println("Verify user is redirected to Products screen successfully after clicking on 'Let's Shop' button");
        if(generalStoreScreen.verifyScreen()==true)
            generalStoreScreen.clickLetsShop();
        Assert.assertTrue(productsScreen.verifyScreen(), "User is NOT redirected to Products screen");
        productsScreen.verifyCart();
    }

    @Test(priority=4, dependsOnMethods="verifyProductsScreenIsDisplayed", description="Launch the app & verify Home screen")
    public void verifyIfUserCanAddProductsToCart() throws Exception {
        System.out.println("Verify user is able to see products list & add it to the Cart");
        productsScreen.addProductToCart(productName1);
        productsScreen.addProductToCart(productName2);
        productsScreen.verifyAddedToCart_btns();
        productsScreen.tapCart();
    }

    @Test(priority=5, dependsOnMethods="isHomeScreenDisplayed", description="Launch the app & verify Home screen")
    public void verifyCart() throws Exception {
        System.out.println("Verify user is redirected to Cart screen");
        if(productsScreen.verifyScreen()==true)
            productsScreen.tapCart();
        Assert.assertTrue(cartScreen.verifyScreen(), "User is NOT redirected to Cart screen");
        cartScreen.verifyScreen();
    }

    @Test(priority=6, description="Launch the app & verify Home screen")
    public void verifyTotalPurchaseAmount() throws Exception {
        System.out.println("Verify user is able to see products added to the Cart");
        Assert.assertTrue(cartScreen.cartProducts(productName1).isElementPresent(5), productName1 + " is NOT added to cart");
        Assert.assertTrue(cartScreen.cartProducts(productName2).isElementPresent(5), productName2 + " is NOT added to cart");
        cartScreen.verifyProductsCountInCart();
        cartScreen.getSumOfAllIndividualProducts();
        cartScreen.getTotalPurchaseAmountOnCart();
        System.out.println("Verify IF 'Total Purchase Amount' displayed = Sum of Individual prices of all products added in the Cart");
        Assert.assertEquals(cartScreen.totalPurchaseAmountOnCart, cartScreen.sumOfIndividualProductPrices, "Sum(Individual products) != Total Purchase Amount displayed on Cart");
        cartScreen.getDiscountEmails();
        cartScreen.visitWebsiteToCompletePurchase();
    }

    @Test(priority=7, description="Launch the app & verify Home screen")
    public void verifyGoogleWebsite() throws Exception {
        System.out.println("Verify user is redirected to Google web site");
        if (cartScreen.verifyScreen() == true)
            cartScreen.visitWebsiteToCompletePurchase();
        Assert.assertTrue(googleScreen.verifyScreen(), "User is NOT redirected to Google website");
        String searchInput = app.prop.getProperty("googleSearchInput");
        googleScreen.enterInputToSearchOnGoogle(searchInput);
        AndroidKey enter = AndroidKey.ENTER;
        app.pressKeyboardKey(enter);
        app.navigateBack();
    }

    @Test(priority=8, description="Launch the app & verify Home screen")
    public void verifyRedirectToApp() throws Exception {
        if(googleScreen.verifyScreen()==true)
            app.navigateBack();
        System.out.println("Verify IF user is redirected back to mobile app when clicked on back button");
        Assert.assertTrue(generalStoreScreen.verifyScreen(), "General Store / Home screen is NOT displayed");
    }


}
