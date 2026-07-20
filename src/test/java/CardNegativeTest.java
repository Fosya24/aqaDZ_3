import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardNegativeTest {
    WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldFailNameFormWithLatin() {
        WebElement nameForm = driver.findElement(By.cssSelector("[data-test-id='name']"));
        nameForm.findElement(By.cssSelector("[name='name']"))
                .sendKeys("Plod");

        WebElement phoneForm = driver.findElement(By.cssSelector("[data-test-id='phone']"));
        phoneForm.findElement(By.cssSelector("[name='phone']"))
                .sendKeys("+79290000000");

        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.trim());
    }

    @Test
    void shouldFailNameFormWithNumber() {
        WebElement nameForm = driver.findElement(By.cssSelector("[data-test-id='name']"));
        nameForm.findElement(By.cssSelector("[name='name']"))
                .sendKeys("2222");

        WebElement phoneForm = driver.findElement(By.cssSelector("[data-test-id='phone']"));
        phoneForm.findElement(By.cssSelector("[name='phone']"))
                .sendKeys("+79290000000");

        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.trim());
    }

    @Test
    void shouldFailNameWithEmpty() {
        WebElement phoneForm = driver.findElement(By.cssSelector("[data-test-id='phone']"));
        phoneForm.findElement(By.cssSelector("[name='phone']"))
                .sendKeys("+79290000000");

        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", actual.trim());
    }

    @Test
    void shouldFailNumberWithOneSymbol() {
        WebElement nameForm = driver.findElement(By.cssSelector("[data-test-id='name']"));
        nameForm.findElement(By.cssSelector("[name='name']"))
                .sendKeys("Пр-рл");

        WebElement phoneForm = driver.findElement(By.cssSelector("[data-test-id='phone']"));
        phoneForm.findElement(By.cssSelector("[name='phone']"))
                .sendKeys("7");

        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.trim());
    }

    @Test
    void shouldFailWith12Symbol() {
        WebElement nameForm = driver.findElement(By.cssSelector("[data-test-id='name']"));
        nameForm.findElement(By.cssSelector("[name='name']"))
                .sendKeys("Пр-рл");

        WebElement phoneForm = driver.findElement(By.cssSelector("[data-test-id='phone']"));
        phoneForm.findElement(By.cssSelector("[name='phone']"))
                .sendKeys("+792900000001");

        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.trim());
    }

    @Test
    void shouldFailNumberWithEmpty() {
        WebElement nameForm = driver.findElement(By.cssSelector("[data-test-id='name']"));
        nameForm.findElement(By.cssSelector("[name='name']"))
                .sendKeys("Пр-рл");

        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", actual.trim());
    }

    @Test
    public void shouldFailWithoutCheckBox() {

        WebElement nameForm = driver.findElement(By.cssSelector("[data-test-id='name']"));
        nameForm.findElement(By.cssSelector("[name='name']"))
                .sendKeys("Пр-рл ");

        WebElement phoneForm = driver.findElement(By.cssSelector("[data-test-id='phone']"));
        phoneForm.findElement(By.cssSelector("[name='phone']"))
                .sendKeys("+79290000000");

        driver.findElement(By.cssSelector("[type='button']")).click();

        assertTrue(driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).isDisplayed());
    }
}