import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

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
        String generateNewDate = DataGenerate.generate(3);
        SelenideElement dateField = $("[placeholder=\"Дата встречи\"]");

        dateField.sendKeys(Keys.chord(Keys.TAB));
        dateField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        dateField.sendKeys(Keys.chord(Keys.BACK_SPACE));

        dateField.setValue(generateNewDate);
        $("[name='name']").setValue(user.getName());
        $("[name='phone']").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        //поменяли кнопку Т_Т
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Встреча успешно запланирована на")).shouldBe(Condition.visible, Duration.ofSeconds(1));
        $(withText(generateNewDate)).shouldBe(Condition.visible, Duration.ofSeconds(1));
        //теперь перенесём дату на 7 дней вперёд
        String generateUpdateDate = DataGenerate.generate(7);
        dateField.sendKeys(Keys.chord(Keys.TAB));
        dateField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        dateField.sendKeys(Keys.chord(Keys.BACK_SPACE));
        dateField.setValue(generateUpdateDate);
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(Condition.visible, Duration.ofSeconds(1));
        $$("button").find(Condition.exactText("Перепланировать")).click();
        $(withText("Встреча успешно запланирована на")).shouldBe(Condition.visible, Duration.ofSeconds(1));
        $(withText(generateUpdateDate)).shouldBe(Condition.visible, Duration.ofSeconds(1));
    }
}
