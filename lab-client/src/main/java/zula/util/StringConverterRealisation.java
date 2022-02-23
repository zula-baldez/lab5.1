package zula.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * util class that contains some realisations of StringConverter
 */

public final class StringConverterRealisation {
    private StringConverterRealisation() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }
    public static Date parseDate(String arguments) {


        SimpleDateFormat parser = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        try {
            return parser.parse(arguments);
        } catch (ParseException e) {
            throw new IllegalArgumentException();
        }
    }
}
