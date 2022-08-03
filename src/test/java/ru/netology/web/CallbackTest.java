package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class CallbackTest {

    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999/");
    }

    public String date(int days) {
        DateFormat newDate = new SimpleDateFormat("dd.MM.yyyy");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 3);
        return (newDate.format(c.getTime()));
    }

    @Test
    public void formSubmission() {
        Configuration.holdBrowserOpen = true;
        $("[placeholder=\"Город\"]").setValue("Москва");
        $("[placeholder=\"Дата встречи\"]").doubleClick();
        $("[placeholder=\"Дата встречи\"]").sendKeys(" ");
        $("[placeholder=\"Дата встречи\"]").setValue(date(3));
        $("[name=\"name\"]").setValue("Иванов Иван");
        $("[name=\"phone\"]").setValue("+79137364555");
        $(".checkbox").click();
        $(".button__text").click();
        $("[data-test-id=\"notification\"]").shouldHave(visible, Duration.ofSeconds(15));
        $(".notification").shouldHave(Condition.text("Встреча успешно забронирована на "
                + date(3)));
    }

    @Test
    public void notFoundCity() {
        Configuration.holdBrowserOpen = true;
        $x("//input[@placeholder=\"Город\"]").setValue("Ласвегас");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick();
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(" ");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(date(3));
        $(byName("name")).setValue("Иванов Иван");
        $(byName("phone")).setValue("+79137364555");
        $(".checkbox").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(".input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    public void invalidDate() {
        Configuration.holdBrowserOpen = true;
        $("[placeholder=\"Город\"]").setValue("Москва");
        $("[placeholder=\"Дата встречи\"]").doubleClick();
        $("[placeholder=\"Дата встречи\"]").sendKeys(" ");
        $("[name=\"name\"]").setValue("Иванов Иван");
        $("[name=\"phone\"]").setValue("+79137364555");
        $(".checkbox").click();
        $(".button__text").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    public void hyphenInSurname() {
        Configuration.holdBrowserOpen = true;
        $("[placeholder=\"Город\"]").setValue("Москва");
        $("[placeholder=\"Дата встречи\"]").doubleClick();
        $("[placeholder=\"Дата встречи\"]").sendKeys(" ");
        $("[placeholder=\"Дата встречи\"]").setValue(date(3));
        $("[name=\"name\"]").setValue("Иванов-Петров Иван");
        $("[name=\"phone\"]").setValue("+79137364555");
        $(".checkbox").click();
        $(".button__text").click();
        $("[data-test-id=\"notification\"]").shouldHave(visible, Duration.ofSeconds(15));
    }

    @Test
    public void invalidSurname() {
        Configuration.holdBrowserOpen = true;
        $("[placeholder=\"Город\"]").setValue("Москва");
        $("[placeholder=\"Дата встречи\"]").doubleClick();
        $("[placeholder=\"Дата встречи\"]").sendKeys(" ");
        $("[placeholder=\"Дата встречи\"]").setValue(date(3));
        $("[name=\"name\"]").setValue("Worner Kevin");
        $("[name=\"phone\"]").setValue("+79137364555");
        $(".checkbox").click();
        $(".button__text").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void phoneMoreBorder() {
        Configuration.holdBrowserOpen = true;
        $("[placeholder=\"Город\"]").setValue("Москва");
        $("[placeholder=\"Дата встречи\"]").doubleClick();
        $("[placeholder=\"Дата встречи\"]").sendKeys(" ");
        $("[placeholder=\"Дата встречи\"]").setValue(date(3));
        $("[name=\"name\"]").setValue("Иванов Иван");
        $("[name=\"phone\"]").setValue("+791373645557");
        $(".checkbox").click();
        $(".button__text").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, " +
                "например, +79012345678."));
    }

    @Test
    public void phoneLessThanBorder() {
        Configuration.holdBrowserOpen = true;
        $("[placeholder=\"Город\"]").setValue("Москва");
        $("[placeholder=\"Дата встречи\"]").doubleClick();
        $("[placeholder=\"Дата встречи\"]").sendKeys(" ");
        $("[placeholder=\"Дата встречи\"]").setValue(date(3));
        $("[name=\"name\"]").setValue("Иванов Иван");
        $("[name=\"phone\"]").setValue("+791373645557");
        $(".checkbox").click();
        $(".button__text").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, " +
                "например, +79012345678."));
    }

    @Test
    public void phoneNoPlus() {
        Configuration.holdBrowserOpen = true;
        $("[placeholder=\"Город\"]").setValue("Москва");
        $("[placeholder=\"Дата встречи\"]").doubleClick();
        $("[placeholder=\"Дата встречи\"]").sendKeys(" ");
        $("[placeholder=\"Дата встречи\"]").setValue(date(3));
        $("[name=\"name\"]").setValue("Иванов Иван");
        $("[name=\"phone\"]").setValue("89137364557");
        $(".checkbox").click();
        $(".button__text").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, " +
                "например, +79012345678."));
    }

    @Test
    public void noConsentCheckbox() {
        Configuration.holdBrowserOpen = true;
        $("[placeholder=\"Город\"]").setValue("Москва");
        $("[placeholder=\"Дата встречи\"]").doubleClick();
        $("[placeholder=\"Дата встречи\"]").sendKeys(" ");
        $("[placeholder=\"Дата встречи\"]").setValue(date(3));
        $("[name=\"name\"]").setValue("Иванов Иван");
        $("[name=\"phone\"]").setValue("+79137364557");
        $(".button__text").click();
    }
}