package guruqa;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class WebStepsReserved {

    @CsvSource(value = {
            "12345@ya.ru,test",
            "admin@gmail.com,admin",
            "TE-ST@gmail.com,пароль"
    })

    @Step("Открыть сайт www.reserved.com/ru/ru/")
    public void openMainPage() {
        open("https://www.reserved.com/ru/ru/");
    }

    @Step("Перейти на страницу авторизации")
    public void goToTheAuthorizationPage() {
        $("div[data-testid='account-info-logged-false']").click();
    }

    @Step("Ввести адрес электронной почты.")
    public void inputEmail(String login) {
        $("input[type='email']").setValue(login);
    }

    @Step("Ввести пароль.")
    public void inputPassword(String password) {
        $("input[type='password']").setValue(password);
    }

    @Step("Нажать кнопку 'Войти'.")
    public void pressOnButtonEnter() {
        $("button[data-selen='login-submit']").click();
    }

    @Step("Проверка наличия предупреждающего сообщения")
    public void shouldSeeWarningMessage() {
        $(".hqKtAx").shouldHave(text("Неверный логин или пароль"));
    }

    @Attachment(value = "Screenshot", type = "image/png", fileExtension = "png")
    public byte[] takeScreenshot() {
        final WebDriver driver = WebDriverRunner.getWebDriver();
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
