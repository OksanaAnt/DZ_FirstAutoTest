import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class AutoTest {
    WebDriver driver;

    @BeforeAll
    public static void init() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp(){
        driver = new ChromeDriver();
    }

    private String login = "degap85327@paxven.com";
    private String pas = "Jrcfyf!1";

    @Test
    public void openAndWorkWithLk() {
//        Открыть в полноэкранном режиме
        driver.manage().window().maximize();
//        Открыть https://otus.ru
        driver.get("https://otus.ru");
//        Авторизоваться на сайте
        loginInOtus();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-name='user-info']")));
//        Переход в ЛК на вкладку Профиль
        WebElement element = driver.findElement(By.xpath("//div[@data-name='user-info']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        driver.findElement(By.cssSelector("a[href='/lk/biography/personal/']")).click();

//      Раздел "О себе"
        clearAndEnter(By.id("id_fname"), "Оксана");
        clearAndEnter(By.id("id_lname"), "Антонова");
        clearAndEnter(By.id("id_fname_latin"), "Oksana");
        clearAndEnter(By.id("id_lname_latin"), "Antonova");
        clearAndEnter(By.id("id_blog_name"), "Оксана Антонова");
        clearAndEnter(By.cssSelector("input[name='date_of_birth']"), "13.09.2000");

//        Раздел "Основная информация"
        driver.findElement(By.xpath("//input[@name='country']/parent::label//div")).click();
        driver.findElement(By.xpath("//button[@title='Россия']")).click();
        driver.findElement(By.xpath("//input[@name='english_level']//parent::label//div")).click();;
        driver.findElement(By.xpath("//button[@title='Начальный уровень (Beginner)']")).click();
        driver.findElement(By.id("id_ready_to_relocate_0"));
        driver.findElement(By.xpath("//input[@value='full']//parent::label")).click();
        driver.findElement(By.cssSelector("input[name='city']~div")).click();
        driver.findElement(By.xpath("//button[@title='Москва']")).click();


//      Раздел "Контактная информация"
        driver.findElement(By.cssSelector("input[name='contact-0-service'] ~ .lk-cv-block__input")).click();
        driver.findElement(By.xpath("//div[@data-num='0']//button[@data-value='skype']")).click();
        clearAndEnter(By.id("id_contact-0-value"), "oksant");
        driver.findElement(By.cssSelector("div[data-prefix='contact']> button[class*='add']")).click();
        driver.findElement(By.cssSelector("input[name='contact-1-service'] ~ .lk-cv-block__input")).click();
        driver.findElement(By.xpath("//div[@data-num='1']//button[@data-value='viber']")).click();
        clearAndEnter(By.id("id_contact-1-value"), "89877877777");

//      Раздел "Другое"
        driver.findElement(By.id("id_gender")).click();
        driver.findElement(By.cssSelector("select[name='gender']>option[value='f']")).click();
        clearAndEnter(By.id("id_company"), "ИТ1");
        clearAndEnter(By.id("id_work"), "Test Lead");

//        Нажать сохранить
        driver.findElement(By.cssSelector("button.button_md-4:nth-child(1)")).submit();

//        Перейти на вкладку "Персональные данные"
        driver.findElement(By.cssSelector("div[class='nav-sidebar']>a[title='Персональные данные']")).click();
//        Проверить данные
        Assertions.assertEquals("Оксана", driver.findElement(By.id("id_fname")).getAttribute("value"));
        Assertions.assertEquals("Антонова", driver.findElement(By.id("id_lname")).getAttribute("value"));
        Assertions.assertEquals("Oksana", driver.findElement(By.id("id_fname_latin")).getAttribute("value"));
        Assertions.assertEquals("Antonova", driver.findElement(By.id("id_lname_latin")).getAttribute("value"));
        Assertions.assertEquals("Оксана Антонова", driver.findElement(By.id("id_blog_name")).getAttribute("value"));
        Assertions.assertEquals("1", driver.findElement(By.cssSelector("input[name='country']")).getAttribute("value"));
        Assertions.assertEquals("317", driver.findElement(By.cssSelector("input[name='city']")).getAttribute("value"));
        Assertions.assertEquals("1", driver.findElement(By.cssSelector("input[name='english_level']")).getAttribute("value"));
        Assertions.assertEquals("13.09.2000", driver.findElement(By.cssSelector("input[name='date_of_birth']")).getAttribute("value"));
        Assertions.assertEquals("oksant", driver.findElement(By.id("id_contact-0-value")).getAttribute("value"));
        Assertions.assertEquals("89877877777", driver.findElement(By.id("id_contact-1-value")).getAttribute("value"));
        Assertions.assertEquals("ИТ1", driver.findElement(By.id("id_company")).getAttribute("value"));
        Assertions.assertEquals("Test Lead", driver.findElement(By.id("id_work")).getAttribute("value"));
        Assertions.assertEquals("False", driver.findElement(By.id("id_ready_to_relocate_0")).getAttribute("value"));
        Assertions.assertEquals("skype", driver.findElement(By.cssSelector("input[name=contact-0-service]")).getAttribute("value"));
        Assertions.assertEquals("viber", driver.findElement(By.cssSelector("input[name=contact-1-service]")).getAttribute("value"));

        WebElement fulltime = driver.findElement(By.cssSelector("input[title='Полный день'][type='checkbox']"));
        System.out.println("Формат работы = Полный день:" + fulltime.isSelected());
    }

    private void loginInOtus() {

        driver.findElement(By.cssSelector(".header3__button-sign-in")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='modal-container new-log-reg-container']//input[@placeholder='Электронная почта']")));

        clearAndEnter(By.cssSelector("div[class='new-log-reg__body'] input[name='email']"), login);
        clearAndEnter(By.cssSelector("div[class='new-log-reg__body'] input[name='password']"), pas);
        driver.findElement(By.cssSelector("div.new-input-line_last:nth-child(5) > button:nth-child(1)")).submit();
    }

    private void clearAndEnter(By by, String text) {
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(text);
    }

    @AfterEach
    public void close(){
        if (driver != null)
            driver.quit();
    }
}
