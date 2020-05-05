package utils;

import static org.junit.jupiter.api.Assertions.*;

class DatautilsTest {

    @org.junit.jupiter.api.Test
    void dateFormat() {
        String test_date = "99 - 3 - 31";
        String formater = utils.Datautils.DateFormat(test_date);
        System.out.println(formater);
    }
}