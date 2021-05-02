package it.thedarksword.bedwarspractice.utils.formatter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Format {

    private static final DecimalFormat DECIMAL_FORMAT_1 = new DecimalFormat("#.#");
    private static final DecimalFormat DECIMAL_FORMAT_3 = new DecimalFormat("#.###");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private Format() {
        throw new AssertionError("Nope.");
    }

    public static String decimal1(long val) {
        return DECIMAL_FORMAT_1.format(val).replace(",", ".");
    }

    public static String decimal3(long val) {
        return DECIMAL_FORMAT_3.format(val).replace(",", ".");
    }

    public static String decimal1(double val) {
        return DECIMAL_FORMAT_1.format(val).replace(",", ".");
    }

    public static String decimal3(double val) {
        return DECIMAL_FORMAT_3.format(val).replace(",", ".");
    }

    public static String now() {
        return DATE_FORMAT.format(new Date());
    }

    public static String date(Date date) {
        return DATE_FORMAT.format(date);
    }
}
