import com.github.javafaker.Faker;
import lombok.Value;
import java.util.Locale;

public class GenerateUser {
        private GenerateUser() {
        }


        public static String generateCity(String ru) {
            Faker faker = new Faker(new Locale(ru));
            return faker.address().cityName();
        }
    //порой cгенерированное имя не соответствует полу фамилии пр. Иванов Анна, и наоборот
        public static String generateName(String ru) {

            Faker faker = new Faker(new Locale(ru));
            return faker.name().lastName() + " " + faker.name().firstName();
        }
    //Faker не может сделать номер нужным форматом :(
        public static String generatePhone() {
            Faker faker = new Faker();
            return "+7"+faker.number().digits(10);
        }

    public static GenerateUser.UserInfo registration(String ru) {
        String city = generateCity(ru);
        String name = generateName(ru);
        String phone = generatePhone();

        return new GenerateUser.UserInfo(city, name, phone);
    }
        @Value
        public static class UserInfo {
            String city;
            String name;
            String phone;
        }
    }
