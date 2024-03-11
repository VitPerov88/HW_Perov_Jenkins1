import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class RegistrationTest {

    @BeforeAll
    static void beforeAll() {
        Configuration.pageLoadStrategy = "eager";
        Configuration.browserSize = "1500x980";
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();

    }
    @Test
    @Tag("demoqa")
    void successfulRegistrationTest() {
        step("Open form", () -> {
            open("https://demoqa.com/automation-practice-form");
            executeJavaScript("$('#fixedban').remove()");
            executeJavaScript("$('footer').remove()");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        });

        step("Fill form", () -> {
            $("#firstName").setValue("Vit");
            $("#lastName").setValue("Perov");
            $("#userEmail").setValue("vit.perov@gmail.com");
            $(byText("Male")).click();
            $("#userNumber").setValue("9800000000");
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-dropdown-container").$(byText("October")).click();
            $(".react-datepicker__year-select").selectOption("1988");
            $(".react-datepicker__day--002").click();
            $("#subjectsInput").setValue("Economics").pressEnter();
            $(byText("Reading")).click();
            $("#uploadPicture").uploadFromClasspath("photo.jpg");
            $("#currentAddress").setValue("Russia");
            $("#state").click();
            $("#stateCity-wrapper").$(byText("NCR")).click();
            $("#city").click();
            $("#stateCity-wrapper").$(byText("Delhi")).click();
            $("#submit").click();
        });

        step("Check form", () -> {
            $(".modal-dialog").shouldHave(text("Thanks for submitting the form"));
            $(".modal-content").shouldHave(
                    text("Vit Perov"),
                    text("vit.perov@gmail.com"),
                    text("Male"),
                    text("9800000000"),
                    text("2 October,1988"),
                    text("Economics"),
                    text("photo.jpg"),
                    text("Russia"),
                    text("NCR Delhi")
            );
        });
    }
}