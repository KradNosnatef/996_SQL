package utils;

public class Datautils {
    public static String DateFormat(String date) {
        int num_year = -1;
        int num_month = -1;
        int num_day = -1;
        int string_id = 0;

        // Recgonize year number in a string
        while (string_id < date.length()) {
            if (date.charAt(string_id) >= '0' && date.charAt(string_id) <= '9') break;
            string_id++;
        }
        while (string_id < date.length()) {
            if (num_year == -1) num_year = 0;
            if (date.charAt(string_id) >= '0' && date.charAt(string_id) <= '9')
            num_year = num_year * 10 + date.charAt(string_id) - '0';
            if (date.charAt(string_id) < '0' || date.charAt(string_id) > '9') break;
            string_id++;
        }
        // Recgonize month number in a string
        while (string_id < date.length()) {
            if (date.charAt(string_id) >= '0' && date.charAt(string_id) <= '9') break;
            string_id++;
        }
        while (string_id < date.length()) {
            if (num_month == -1) num_month = 0;
            if (date.charAt(string_id) >= '0' && date.charAt(string_id) <= '9')
            num_month = num_month * 10 + date.charAt(string_id) - '0';
            if (date.charAt(string_id) < '0' || date.charAt(string_id) > '9') break;
            string_id++;
        }
        // Recgonize date number in a string
        while (string_id < date.length()) {
            if (date.charAt(string_id) >= '0' && date.charAt(string_id) <= '9') break;
            string_id++;
        }
        while (string_id < date.length()) {
            if (num_day == -1) num_day = 0;
            if (date.charAt(string_id) >= '0' && date.charAt(string_id) <= '9')
            num_day = num_day * 10 + date.charAt(string_id) - '0';
            if (date.charAt(string_id) < '0' || date.charAt(string_id) > '9') break;
            string_id++;
        }

        // Format year number into digit of length 4
        if (num_year < 100) {
            if (num_year < 10) num_year += 2000;
            else num_year += 1900;
        }

        return String.format("%04d", num_year) + "-" +
                String.format("%02d", num_month) + "-" +
                String.format("%02d", num_day);
    }
}
