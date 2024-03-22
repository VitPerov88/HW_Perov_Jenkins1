package tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.RegistrationPage;
import static io.qameta.allure.Allure.step;

public class RegistrationTest extends TestBase {

    RegistrationPage registrationPage = new RegistrationPage();
    @Test
    @Tag("demoqa")
    void successfulRegistrationTest() {
        step("Open form", () -> {
                    registrationPage.openPage()
                            .removeBanners();
                });

        step("Fill form", () -> {
            registrationPage.setFirstName("Vit")
                            .setLastName("Perov")
                            .setEmail("vit.perov@gmail.com")
                            .setGender("Male")
                            .setUserNumber("9800000000")
                            .setDateOfBirth("02", "October", "1988")
                            .setSubjects("Economics")
                            .setHobbies("Reading")
                            .uploadPicture("photo.jpg")
                            .setAddress("Russia")
                            .setState("NCR")
                            .setCity("Delhi")
                            .clickSubmit();
            });

        step("Check form", () -> {
            registrationPage.checkResultModalTable("Student Name", "Vit Perov")
                    .checkResultModalTable("Student Email", "vit.perov@gmail.com")
                    .checkResultModalTable("Gender", "Male")
                    .checkResultModalTable("Mobile", "9800000000")
                    .checkResultModalTable("Date of Birth", "02 October,1988")
                    .checkResultModalTable("Subjects", "Economics")
                    .checkResultModalTable("Hobbies", "Reading")
                    .checkResultModalTable("Picture", "photo.jpg")
                    .checkResultModalTable("Address", "Russia")
                    .checkResultModalTable("State and City", "NCR Delhi");
            });
        }
    }
