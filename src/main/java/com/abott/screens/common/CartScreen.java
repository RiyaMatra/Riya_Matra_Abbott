package com.abott.screens.common;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import utlilities.UIHelper;

public class CartScreen {
    private final UIHelper app;
    public double totalPurchaseAmountOnCart;
    public double sumOfIndividualProductPrices=0.00;

    public CartScreen(UIHelper app){
        this.app= app;
    }

    public UIHelper screenTitle() {
        return app.setBy(By.id("toolbar_title"));
    }

    public UIHelper cartProducts(String productName) {
        return app.setBy(By.xpath("//*[@text='"+productName+"']"));
    }

    public UIHelper productList() {
        return app.setBy(By.id("productImage"));
    }

    public UIHelper individualProductPrice() {
        return app.setBy(By.id("productPrice"));
    }

    public UIHelper purchaseAmountOnCart() {
        return app.setBy(By.id("totalAmountLbl"));
    }
    public UIHelper discountEmails_checkbox() {
        return app.setBy(By.className("android.widget.CheckBox"));
    }

    public UIHelper visitWebsite_btn() {
        return app.setBy(By.id("btnProceed"));
    }

    //METHODS
    @Step("Verify screen title = 'Cart'")
    public boolean verifyScreen() throws Exception {
        System.out.println("Verify screen title = 'Cart'");
        return screenTitle().isElementPresent(10) && screenTitle().getText().equals("Cart");
    }

    @Step("Verify products are added to cart")
    public void verifyProductsCountInCart() throws Exception {
        System.out.println("Verify products are added to cart");
        int size = productList().getAll().size();
        System.out.println("Total products added to cart is = "+size);
        Assert.assertEquals(size, 2,"Both products are NOT added successfully");
    }

    @Step("Get sum of all individual Products")
    public double getSumOfAllIndividualProducts() throws Exception {
        System.out.println("Get sum of all individual Products");
        int size = individualProductPrice().getAll().size();
        System.out.println("Total sum needs to be calculated for "+size+" products");
        for(int i =0; i<size; i++) {
            String $Price = individualProductPrice().getAll().get(i).getText();
            $Price = $Price.replaceAll("[$]","");
            double price = Double.parseDouble($Price);
            System.out.println("Price of product "+i+" = "+price);
            sumOfIndividualProductPrices = sumOfIndividualProductPrices + price;
        }
        System.out.println("Total Price Of "+size+" Individual Product Prices = "+sumOfIndividualProductPrices);
        return sumOfIndividualProductPrices;
    }

    @Step("Verify Total Purchase Amount displayed on Cart")
    public double getTotalPurchaseAmountOnCart() throws Exception {
        System.out.println("Verify Total Purchase Amount displayed on Cart");
        totalPurchaseAmountOnCart = Double.parseDouble(purchaseAmountOnCart().getText().replaceAll("[$] ",""));
        System.out.println("Total Purchase Amount displayed on Cart = "+totalPurchaseAmountOnCart);
        return totalPurchaseAmountOnCart;
    }

    @Step("Check the checkbox to get Discount emails")
    public void getDiscountEmails() throws Exception {
       System.out.println("Verifying if Discount emails is already checked. If not, Check the checkbox");
       if(discountEmails_checkbox().getAttribute("checked").equals("false"))
           discountEmails_checkbox().click();
    }

    @Step("Click 'Visit to the website to complete purchase' button to place the order successfully")
    public void visitWebsiteToCompletePurchase() throws Exception {
        System.out.println("Click 'Visit to the website to complete purchase' button to place the order successfully");
        visitWebsite_btn().click();
    }

}
