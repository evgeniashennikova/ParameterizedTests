package guruqa;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


@Owner("eshennikova")
public class ParameterizedTests {

    WebStepsReserved steps = new WebStepsReserved();

    @BeforeAll
    public static void start() {
        Configuration.browserSize = "1900x1000";
    }

    @Disabled
    @ParameterizedTest(name = "{displayName} с логином {0} и паролем {1}")
    @DisplayName("Авторизация на сайте www.reserved.com ")
    @Link(name = "Интернет-магазин Reserved", url = "https://www.reserved.com/ru/ru/")
    @Severity(SeverityLevel.CRITICAL)
    @CsvSource(value = {
            "12345@ya.ru,test",
            "admin@gmail.com,admin",
            "TE-ST@gmail.com,пароль"
    })
    public void authorizationOnWebsiteTestWithCsvSource(String login, String password) {

        steps.openMainPage();
        steps.goToTheAuthorizationPage();
        steps.inputEmail(login);
        steps.inputPassword(password);
        steps.pressOnButtonEnter();
        steps.shouldSeeWarningMessage();
        steps.takeScreenshot();
    }

    @ParameterizedTest(name = "{displayName} на www.wildberries.ru")
    @DisplayName("Поиск товара {0} ")
    @Link(name = "Интернет-магазин Wildberries", url = "https://www.wildberries.ru/")
    @Severity(SeverityLevel.CRITICAL)
    @EnumSource(SearchQuery.class)
    public void searchOnWebsiteTestWithEnumSource(SearchQuery searchQuery) {
        open("https://www.wildberries.ru/");
        $("#searchInput").setValue(String.valueOf(searchQuery)).pressEnter();
        $(".searching-results__inner").shouldHave(text(String.valueOf(searchQuery)));
    }


    private static Stream<Arguments> yaTranslateTest() {
        return Stream.of(
                Arguments.of("сайт", "website"),
                Arguments.of("дом", "house")
        );
    }

    @ParameterizedTest(name = "{displayName}")
    @DisplayName("Проверка перевода в ЯндексПереводчике")
    @Link(name = "ЯндексПереводчик", url = "https://translate.yandex.ru")
    @Severity(SeverityLevel.BLOCKER)
    @MethodSource

    public void yaTranslateTest(String searchValue, String searchResult) {
        open("https://translate.yandex.ru");
        $("#fakeArea").setValue(searchValue).pressEnter();
        $("#translation").shouldHave(text(searchResult));
    }
}
