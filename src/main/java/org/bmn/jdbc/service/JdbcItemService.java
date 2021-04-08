package org.bmn.jdbc.service;

import org.bmn.entity.Item;
import org.bmn.jdbc.exception.DaoException;
import org.bmn.jdbc.repository.JdbcRepository;
import org.bmn.jdbc.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class JdbcItemService implements JdbcRepository<Item, Long> {

    public static final String SQL_SELECT_ALL_ITEMS = "SELECT * FROM items";
    public static final String SQL_SELECT_ITEM_ID = "SELECT * FROM items WHERE id=?";
    public static final String SQL_SELECT_EXIST_ID = "SELECT id FROM items WHERE id=?";
    public static final String SQL_SELECT_ITEM_NAME = "SELECT * FROM items WHERE item_name like ?";

    public static final String SQL_INSERT_ITEM = "INSERT INTO items (item_name) VALUES (?)";

    public static final String SQL_DELETE_ITEM_BY_VALUE = "DELETE FROM Items WHERE item_name like ?";
    public static final String SQL_DELETE_ITEM_BY_ID = "DELETE FROM Items WHERE id = ?";
    public static final String SQL_DELETE_ITEM_BY_NAME = "DELETE FROM Items WHERE item_name = ?";

    @Override
    public List<Item> findAll() {
        try (Connection connection = DBConnection.get();
             Statement statement = connection.createStatement()) {
            List<Item> items = new ArrayList<>();
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_ITEMS);
            while (rs.next()) {
                String itemName = rs.getString(2);
                items.add(new Item(itemName));
            }
            return items;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public List<Item> findAllAndSort() {
        try (Connection connection = DBConnection.get();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_ITEMS);
            List<Item> items = new ArrayList<>();
            while (rs.next()) {
                String itemName = rs.getString(2);
                items.add(new Item(itemName));
            }
            Collections.sort(items);
            return items;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Item> findAllByValue(String value) {
        try(Connection connection = DBConnection.get();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ITEM_NAME)) {
            statement.setString(1, value+"%");
            ResultSet rs = statement.executeQuery();
            List<Item> items = new ArrayList<>();
            while (rs.next()) {
                String v = rs.getString(2);
                items.add(new Item(v));
            }
            return items;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Item> saveAll(Iterable<Item> items) {
        try(Connection connection = DBConnection.get();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ITEM, Statement.RETURN_GENERATED_KEYS)) {
            List<Item> itemResult = new ArrayList<Item>();
            Iterator iterator = items.iterator();
            while (iterator.hasNext()) {
                Item item = (Item)iterator.next();
                statement.setString(1, item.getName());
                statement.executeUpdate();
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getLong("id"));
                    itemResult.add(item);
                }
            }
            return itemResult;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }





    @Override
    public Item save(Item item) {
        try(Connection connection = DBConnection.get();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ITEM, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                item.setId(generatedKeys.getLong("id"));
            }
            return item;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Item findById(Long id) {
        Item item = null;
        try (Connection connection = DBConnection.get();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ITEM_ID)) {

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String name = rs.getString(2);
                item = new Item(name);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return item;
    }

    @Override
    public boolean existsById(Long id) {
        try (Connection connection = DBConnection.get();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_EXIST_ID)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = DBConnection.get();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ITEM_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public boolean delete(Item item) {
        try (Connection connection = DBConnection.get();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ITEM_BY_VALUE)) {
            statement.setString(1, item.getName());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public void deleteAll(Iterable<? extends Item> items) {
        Connection connection = DBConnection.get();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_ITEM_BY_NAME);
            connection.setAutoCommit(false);
            Iterator iterator = items.iterator();
            while (iterator.hasNext()) {
                Item item = (Item)iterator.next();
                statement.setString(1, item.getName());
                statement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
