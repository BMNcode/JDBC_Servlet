package org.bmn.jdbc.service;

import org.bmn.jdbc.config.DBConnection;
import org.bmn.jdbc.repository.JdbcRepository;
import org.bmn.model.Item;

import java.sql.*;
import java.util.*;

public class JdbcItemService implements JdbcRepository<Item, Long> {

    public static final String SQL_SELECT_ALL_ITEMS = "SELECT * FROM items";
    public static final String SQL_SELECT_ITEM_ID = "SELECT * FROM items WHERE id=?";
    public static final String SQL_SELECT_ITEM_NAME = "SELECT * FROM items WHERE item_name like ?";

    public static final String SQL_INSERT_ITEM = "INSERT INTO items (item_name) VALUES (?)";

    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        try (Connection connection = DBConnection.getNewConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_ITEMS);
            while (rs.next()) {
                String itemName = rs.getString(2);
                items.add(new Item(itemName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public List<Item> findAllAndSort() {
        List<Item> items = new ArrayList<>();
        try (Connection connection = DBConnection.getNewConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_ITEMS);
            while (rs.next()) {
                String itemName = rs.getString(2);
                items.add(new Item(itemName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(items);
        return items;
    }

    @Override
    public List<Item> findAllByValue(String value) {
        List<Item> items = new ArrayList<>();
        try(Connection connection = DBConnection.getNewConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ITEM_NAME)) {
            statement.setString(1, value+"%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String v = rs.getString(2);
                items.add(new Item(v));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public <S extends Item> List<S> saveAll(Iterable<S> var1) {

        return null;
    }

    @Override
    public int save(Item item) {

        int affectedRows = 0;

        try(Connection connection = DBConnection.getNewConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ITEM)) {

            statement.setString(1, item.getName());

            affectedRows = statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return affectedRows;
    }

    @Override
    public Item findById(Long id) {
        Item item = null;
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ITEM_ID)) {

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String name = rs.getString(2);
                item = new Item(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public boolean existsById(Long var1) {
        return false;
    }

    @Override
    public void deleteById(Long var1) {

    }

    @Override
    public void delete(Item var1) {

    }

    @Override
    public void deleteAll(Iterable<? extends Item> var1) {

    }

    @Override
    public void deleteAll() {

    }
}
