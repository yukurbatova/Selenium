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

public class NegativeTest {
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
    void shouldEmptyAllFieldsTest() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=name] .input__sub"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldEmptyPhoneTest() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] .input__control")).sendKeys("Курбатова Юлия");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=phone] .input__sub"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldEmptySurnameTest() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79038190000");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=name] .input__sub"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldEmptyAgreementTest() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] .input__control")).sendKeys("Курбатова Юлия");
        driver.findElement(cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79038190000");
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__text"));
        String actualMessage = messageElement.getCssValue("color");
        String expectedMessage = "rgba(255, 92, 92, 1)";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldNotRussianSurnameTest() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] .input__control")).sendKeys("Kurbatova Yuliya");
        driver.findElement(cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79038190000");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=name] .input__sub"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldWrongSurnameFormatTest() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] .input__control")).sendKeys("Юлия К.");
        driver.findElement(cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79038190000");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=name] .input__sub"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldWrongPhoneFormatTest() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] .input__control")).sendKeys("Курбатова Юлия");
        driver.findElement(cssSelector("[data-test-id=phone] .input__control")).sendKeys("+7(903)-819-00-00");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=phone] .input__sub"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }
}