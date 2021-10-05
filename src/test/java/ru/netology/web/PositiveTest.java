package ru.netology.web;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.By.cssSelector;

public class PositiveTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        //System.setProperty("webdriver.chrome.driver", "./driver/win/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSurnameWithSpaceTest() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] .input__control")).sendKeys("Курбатова Юлия");
        driver.findElement(cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79038190000");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=order-success]"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldSurnameWithHyphenTest() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] .input__control")).sendKeys("Курбатова-Минакова Юлия");
        driver.findElement(cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79038190000");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=order-success]"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldMinSurnameTest() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] .input__control")).sendKeys("К");
        driver.findElement(cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79038190000");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=order-success]"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }
}