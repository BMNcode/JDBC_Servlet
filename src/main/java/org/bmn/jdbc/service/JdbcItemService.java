package org.bmn.jdbc.service;

import org.bmn.jdbc.config.DBConnection;
import org.bmn.jdbc.repository.JdbcRepository;
import org.bmn.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class JdbcItemService implements JdbcRepository<Item, Long> {

    public static final String SQL_SELECT_ALL_ITEMS = "SELECT * FROM items";
    public static final String SQL_SELECT_ITEM_ID = "SELECT * FROM items WHERE id=?";

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
    public List<Item> findAllByValue(Item v) {
        return null;
    }

    @Override
    public <S extends Item> List<S> saveAll(Iterable<S> var1) {
        return null;
    }

    @Override
    public <S extends Item> S save(S var1) {
        return null;
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
