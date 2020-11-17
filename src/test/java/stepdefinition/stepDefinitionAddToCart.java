package stepdefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;

public class stepDefinitionAddToCart
{
    WebDriver driver;
    String url = "http://www.fashiondays.ro";
    String firstItemName;
    String secondItemName;

    @Before
    public void setUpBeforeClass()
    {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        System.setProperty("webdriver.chrome.driver", "/home/jaul/IdeaProjects/WebsiteTask/src/test/java/resources/chromedriver");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
    }

    @After
    public void tearDown()
    {
        driver.manage().deleteAllCookies();
        driver.quit();
    }


    @Given("I am on website home page")
    public void i_am_on_website_home_page()
    {
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertEquals(driver.getTitle(),"Destinatia de fashion #1 in Europa Centrala si de Est");
    }

    @And("I select some category")
    public void i_select_some_category()
    {
        WebElement categoryElement = driver.findElement(By.xpath("//span[contains(text(),'INCALTAMINTE')]"));
        categoryElement.click();

        WebElement subcategoryElement = driver.findElement(By.xpath("//span[contains(text(),'Pantofi clasici')]"));
        subcategoryElement.click();
    }

    @And("I sort items by the highest price")
    public void i_sort_items_by_the_highest_price()
    {
        String currentURL = driver.getCurrentUrl();
        new WebDriverWait(driver,Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'filter-sort products-toolbar__btn-container')]//button[@class='filterDrop']"))).click();
        WebElement selectHighestPriceElement = driver.findElement(By.xpath("//*[text()='Cel mai mare pret']"));
        selectHighestPriceElement.click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentURL)));
    }

    @And("I select the first item")
    public void i_select_the_first_item()
    {
        new WebDriverWait(driver,Duration.ofSeconds(10))
               .until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@id='products-listing-list']/li[1]/a"))).click();
        WebElement itemSize = driver.findElement(By.xpath("//*[@id='sizeContainer']/div[2]/label"));
        itemSize.click();
        firstItemName = driver.findElement(By.xpath("//div[@class='product-buy-box']/h1[@class='product-brand-desc']")).getText();
        System.out.println(firstItemName);
    }

    @And("I click the button to add to cart")
    public void i_click_the_button_to_add_to_cart()
    {
        WebElement addToCartButton = driver.findElement(By.xpath("//span[contains(text(),'Adauga in cos')]"));
        addToCartButton.click();
    }

    @And("I navigate back to previous page")
    public void i_navigate_back_to_previous_page()
    {
        String oldURl = driver.getCurrentUrl();
        driver.navigate().back();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.not(ExpectedConditions.urlToBe(oldURl)));
    }

    @And("I select the second item")
    public void i_select_the_second_item()
    {
        new WebDriverWait(driver,Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@id='products-listing-list']/li[2]/a"))).click();
        WebElement itemSize = driver.findElement(By.xpath("//*[@id='sizeContainer']/div[2]/label"));
        itemSize.click();
        secondItemName = driver.findElement(By.xpath("//div[@class='product-buy-box']/h1[@class='product-brand-desc']")).getText();
    }

    @And("I add the second item to cart")
    public void i_add_the_second_item_to_cart()
    {
        WebElement addToCartButton = driver.findElement(By.xpath("//span[contains(text(),'Adauga in cos')]"));
        addToCartButton.click();
    }

    @When("I go to Cart")
    public void i_go_to_cart()
    {
        WebElement cartButton = driver.findElement(By.id("prodCartCountHeader"));
        cartButton.click();
    }

    @Then("I should see both products added to cart")
    public void i_should_see_both_products_added_to_cart()
    {
        //Count products from cart
        List<WebElement> numberOfItemsAddedToCart = driver.findElements(By.xpath("//div[@class='cart-products-box']/div[contains(@class, 'cart-product-box')]"));
        Assert.assertEquals(2,numberOfItemsAddedToCart.size());

        WebElement cartTitle = driver.findElement(By.xpath("//div[contains(text(),'Cosul de cumparaturi')]"));
        Assert.assertEquals("COSUL DE CUMPARATURI", cartTitle.getText());

        WebElement firstItemAdded = driver.findElement(By.xpath("//div[@class='cart-products-box']/div[contains(@class, 'cart-product-box')][1]//div[@class='product-name']"));
        Assert.assertEquals(firstItemName, firstItemAdded.getText());

        WebElement secondItemAdded = driver.findElement(By.xpath("//div[@class='cart-products-box']/div[contains(@class, 'cart-product-box')][2]//div[@class='product-name']"));
        Assert.assertEquals(secondItemName, secondItemAdded.getText());
    }
}
