package utils;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DButilsTest {
    @org.junit.jupiter.api.Test
    void dbTest1() {
        Connection conn = DButils.getConnection();
    }
}