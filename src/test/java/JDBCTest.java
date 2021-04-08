import org.bmn.jdbc.util.DBConnection;
import org.bmn.jdbc.service.JdbcItemService;
import org.bmn.entity.Item;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;


public class JDBCTest {

    private static Connection connection;

    @Test
    public void shouldGetJdbcConnection() throws SQLException, IOException {
        try (Connection connection = DBConnection.get()) {
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
        assertEquals(new JdbcItemService().save(new Item("КПК")), new Item("КПК"));
    }

    @Test
    public void saveAllItemTest() {
        List<Item> expected = Arrays.asList(new Item("cvb"), new Item("cvb"), new Item("qwe"));
        List<Item> actual = Arrays.asList(new Item("cvb"), new Item("cvb"), new Item("qwe"));
        assertEquals(new JdbcItemService().saveAll(expected), actual);
    }

    @Test
    public void existItemByIdTest() {

        assertEquals(new JdbcItemService().existsById(6L), true);
    }

    @Test
    public void deleteItemByIdTest() {

        assertEquals(new JdbcItemService().deleteById(5L), true);
    }

    @Test
    public void deleteItem() {
        assertEquals(new JdbcItemService().delete(new Item("zxc")), true);
    }

    @Test
    public void deleteAllItem() {
        List<Item> expected = Arrays.asList(new Item("cvb"), new Item("cvb"), new Item("qwe"));
        new JdbcItemService().deleteAll(expected);
    }
}
