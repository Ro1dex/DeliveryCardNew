import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class UpdateDeliveryCardTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void DeliveryCardTest(){
        GenerateUser.UserInfo user = GenerateUser.registration("ru");
        $("[placeholder=\"Город\"]").setValue(user.getCity());
        LocalDate currentDate = LocalDate.now().plusDays(3);
        SelenideElement dateField = $("[placeholder=\"Дата встречи\"]");

        // сымитировал ручное удаление в поле ввода методом >Keys< так как выпадающие поле городов иногда перекрывает поле даты фигня какая-то :(
        dateField.sendKeys(Keys.chord(Keys.TAB));
        dateField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        dateField.sendKeys(Keys.chord(Keys.BACK_SPACE));

        //вводимый паттерн даты толжен соответствовать >dd/MM/yyyy< по умолчанию >yyyy/MM/dd<
        dateField.setValue(currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        $("[name='name']").setValue(user.getName());
        $("[name='phone']").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        //поменяли кнопку Т_Т
        $$("button").find(Condition.exactText("Запланировать")).click();
        //теперь перенесём дату на 7 дней вперёд
        currentDate = LocalDate.now().plusDays(7);
        dateField.sendKeys(Keys.chord(Keys.TAB));
        dateField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        dateField.sendKeys(Keys.chord(Keys.BACK_SPACE));
        dateField.setValue(currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(Condition.visible, Duration.ofSeconds(1));
        $$("button").find(Condition.exactText("Перепланировать")).click();
        $(withText("Встреча успешно запланирована на")).shouldBe(Condition.visible, Duration.ofSeconds(1));
    }
}
