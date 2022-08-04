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
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class ComplexElementsTest {

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
    public void cityFromTheList() {
        Configuration.holdBrowserOpen = true;
        $("[placeholder=\"Город\"]").setValue("Мо");
        $x("//*[text()=\"Москва\"]").click();
        $("[placeholder=\"Дата встречи\"]").doubleClick();
        $("[placeholder=\"Дата встречи\"]").sendKeys(" ");
        $("[placeholder=\"Дата встречи\"]").setValue(date(4));
        $("[name=\"name\"]").setValue("Иванов Иван");
        $("[name=\"phone\"]").setValue("+79137364555");
        $(".checkbox").click();
        $(".button__text").click();
        $("[data-test-id=\"notification\"]").shouldHave(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на "
                + date(3)));
    }
}
