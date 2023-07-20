import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataGenerate {
    public static String generate(int days){
        LocalDate currentDate = LocalDate.now().plusDays(days);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return formatter.format(currentDate);
    }
}