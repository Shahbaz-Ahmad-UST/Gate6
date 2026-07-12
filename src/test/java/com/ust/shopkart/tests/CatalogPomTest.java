package com.ust.shopkart.tests;
//
//import com.ust.shopkart.pages.CartPage;
//import com.ust.shopkart.pages.CatalogPage;
//import com.ust.shopkart.pages.LoginPage;
//import com.ust.shopkart.pages.ProductPage;
//import com.ust.shopkart.support.DriverFactory;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.openqa.selenium.WebDriver;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class CatalogPomTest {
//    private WebDriver driver;
//
//    @BeforeEach
//    void setup() {
//        driver = DriverFactory.createChromeDriver();
//    }
//
//    @AfterEach
//    void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//
//    @Test
//    @DisplayName("Exercise 1 :POM search query returns only matching catalog titles")
//    void searchFindsOnMatchingProduct() {
//        CatalogPage catalog = new CatalogPage(driver)
//                .open()
//                .searchFor("headphones", "Showing 1 product");
//
//        List<String> titles = catalog.titles();
//
//        assertAll(
//                () -> assertFalse(titles.isEmpty(), "search returned no products"),
//                () -> assertTrue(
//                        titles.stream().allMatch(title -> title.toLowerCase().contains("headphones")),
//                        "search result should be related to headphones"
//                )
//        );
//    }
//
//    @Test
//    @DisplayName("Exercise:POM sort hides the stale-element handling inside the page")
//    void sortLowToHighOnPom() {
//        List<Integer> prices = new CatalogPage(driver)
//                .open()
//                .sortBy("Price: Low to High")
//                .prices();
//
//        assertEquals(prices.stream().sorted().toList(), prices);
//    }
//
//    @Test
//    @DisplayName("POM header component expresses cart badge and cart navigation")
//    void headerComponentOpenCart() {
//        CatalogPage catalog = new CatalogPage(driver).open();
//        catalog.header().cartBadge().expectedCount(0);
//
//        CartPage cart = catalog.header().openCart();
//
//        assertEquals(0, cart.lineCount());
//    }
//
//    @Test
//    @DisplayName("Exercise 3: Catalog flow confirms the order from catalog to checkout")
//    void catalogToConfirmOrder() throws InterruptedException {
//
//        //  Step 1: Login
//        LoginPage login = new LoginPage(driver)
//                .openSignPage()
//                .login("customer@example.com", "Password@123");
//
//        //  Step 2: Verify login via Header component
//        String welcomeText = login.header()
//                .login_component()
//                .getWelcomeTitle();
//
//        assertTrue(welcomeText.contains("Welcome, Customer User"));
//
//        //Step 3: Go to Catalog and Search with category
//
//        CatalogPage catalog = new CatalogPage(driver)
//                .open()
//                .searchFor("headphones", "Showing 1 product");
//
//      // Step5: get first one and assert
//        ProductPage product = catalog.openFirstProduct();
//
//        assertTrue(product.name().toLowerCase().contains("headphones"));
//
//        //Step 6: add to cart and asserts
//
//        CartPage cart = product.addToCart();
//
//        cart.header().cartBadge().expectedCount(1);
//
//        assertAll(
//                () -> assertEquals(1, cart.lineCount()),
//                () -> assertFalse(cart.total().isBlank())
//        );
//         //step 7: proceed and place order and assert
//        String confirmation = cart.proceed().placeOrder().confirmationText();
//        assertTrue(confirmation.toLowerCase().contains("confirmed"));
//
//        //step 8: logout ans assert
//        String title = new LoginPage(driver).logout().header().logout_component().signOut();
//        assertEquals("Sign in to Retail Lab",title);
//
//    }
//}
