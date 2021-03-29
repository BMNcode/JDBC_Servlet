import org.junit.Test;

import org.bmn.jdbc.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JDBCTest {
    @Test
    public void shouldGetJdbcConnection() throws SQLException, IOException {
        try(Connection connection = new DBConnection().getNewConnection()) {
            assertTrue(connection.isValid(1));
            assertFalse(connection.isClosed());
        }
    }
}
