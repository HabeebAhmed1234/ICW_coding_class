import java.util.Calendar;
import java.util.Locale;

public class builderexample {

  public void example() {
    Calendar.Builder builder = new Calendar.Builder();

    builder.setLocale(Locale.CANADA);
    builder.setTimeOfDay(6,2,3);

    Calendar calendar = new Calendar();
  }
}
