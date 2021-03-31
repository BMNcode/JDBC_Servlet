import org.bmn.jdbc.config.DBConnection;
import org.bmn.jdbc.service.JdbcItemService;
import org.bmn.model.Item;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;


public class JDBCTest {

    private static Connection connection;

    @Test
    public void shouldGetJdbcConnection() throws SQLException, IOException {
        try (Connection connection = new DBConnection().getNewConnection()) {
            assertTrue(connection.isValid(1));
            assertFalse(connection.isClosed());
        }
    }

    @Test
    public void getFindAll() {
        assertThat(new JdbcItemService().findAll(), hasSize(3));
    }

    @Test
    public void getFindAllAndSort() {
        assertThat(new JdbcItemService().findAllAndSort(), contains(new Item("ноутбук"), new Item("планшет"), new Item("смартфон")));
    }

    @Test
    public void getFindById() {
        assertEquals(new JdbcItemService().findById(1L), new Item("смартфон"));
    }

    @Test
    public void getFindAllValue() {
        List<Item> expected = new JdbcItemService().findAllByValue("п");
        List<Item> actual = new ArrayList<>();
        actual.add(new Item("планшет"));
        assertEquals(expected, actual);
    }

    @Test
    public void saveItemTest() {
        Item item = new Item("KПК");
        new JdbcItemService().save(item);
        List<Item> expected = new JdbcItemService().findAllByValue("KПК");
        List<Item> actual = new ArrayList<>();
        actual.add(new Item("KПК"));
        assertEquals(expected, actual);
    }
}
