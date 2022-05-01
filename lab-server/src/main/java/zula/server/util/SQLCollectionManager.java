package zula.server.util;

import zula.common.data.Color;
import zula.common.data.Coordinates;
import zula.common.data.Dragon;
import zula.common.data.DragonCave;
import zula.common.data.DragonType;
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.util.AbstractClient;
import zula.common.util.CollectionManager;
import zula.common.util.SQLManager;
import zula.common.util.StringConverterRealisation;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;

public class SQLCollectionManager implements SQLManager {
    private static final String ADD_ELEMENT = "";

    private static final String REGISTER = "INSERT INTO USERS VALUES (?, default, ?) RETURNING id";
    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS DRAGONS(\n"
                    + "                        id SERIAL PRIMARY KEY ,\n"
                    + "                        name VARCHAR(50),\n"
                    + "                        x double precision CHECK(x >= -23) NOT NULL,\n"
                    + "                        y int CHECK(y < 161),\n"
                    + "                        creation_date  VARCHAR(50) NOT NULL,\n"
                    + "                        age BIGINT,\n"
                    + "                        wingspan FLOAT,\n"
                    + "                        color VARCHAR(50),\n"
                    + "                        type VARCHAR(50),\n"
                    + "                        depth float4,\n"
                    + "                        number_of_treasure DOUBLE PRECISION,\n"
                    + "                        owner_id integer NOT NULL,\n"
                    + "                        FOREIGN KEY(owner_id) REFERENCES users ON DELETE CASCADE)";
    private static final String CREATE_USERS =
            "CREATE TABLE IF NOT EXISTS USERS (\n"
                    + "                        NAME VARCHAR(50) UNIQUE,\n"
                    + "                        ID serial primary key,\n"
                    + "                        PASSWORD VARCHAR(64)\n"
                    + ")";
    private Connection connection;
    private Logger logger = Logger.getLogger("SQLManager");



    public SQLCollectionManager(Connection connection1) {
        connection = connection1;
    }

    public int remove(int id, int userId) {
        String query = "SELECT * FROM dragons WHERE id = " + id;

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            System.out.println(resultSet);
            if (resultSet.getInt("owner_id") == userId) {
                query = "DELETE FROM dragons WHERE id = " + id;
                statement.execute(query);

                return 1;
            } else {
                return -1;
            }
        } catch (SQLException e) {
            return -1;
        }
    }

    @Override
    public int removeUsersDragons(int userId) {
        String query = "DELETE FROM dragons WHERE owner_id = " + userId + " RETURNING id";
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            statement.executeQuery(query);
            return 1;
        } catch (SQLException e) {
            return -1;
        }
    }

    @Override
    public int removeLower(int userId, int id) {

        String query1 = "SELECT FROM DRAGONS WHERE id = " + id;
        String query2 = "DELETE FROM dragons WHERE owner_id = " + userId + " and id <" + id;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query1);
            if (!resultSet.next()) {
                throw new SQLException();
            }
            statement.execute(query2);
            return 1;
        } catch (SQLException e) {
            return -1;
        }
    }




    private void prepareStatement(PreparedStatement preparedStatement, int j, Dragon dragon) throws SQLException {
        int i = j;
        if (dragon.getName() == null) {
            preparedStatement.setString(i++, "null");
        } else {
            preparedStatement.setString(i++, dragon.getName());
        }
        preparedStatement.setDouble(i++, dragon.getCoordinates().getX());
        preparedStatement.setInt(i++, dragon.getCoordinates().getY());
        preparedStatement.setString(i++, dragon.getCreationDate().toString());
        preparedStatement.setLong(i++, dragon.getAge());
        preparedStatement.setFloat(i++, dragon.getWingspan());
        if (dragon.getColor() == null) {
            preparedStatement.setNull(i++, Types.ARRAY);
        } else {
            preparedStatement.setString(i++, dragon.getColor().toString());
        }


        preparedStatement.setString(i++, dragon.getType().toString());


        if (dragon.getCave().getDepth() == null) {
            preparedStatement.setNull(i++, Types.DOUBLE);
        } else {
            preparedStatement.setDouble(i++, dragon.getCave().getDepth());
        }
        if (dragon.getCave().getNumberOfTreasures() == null) {
            preparedStatement.setNull(i++, Types.ARRAY);
        } else {
            preparedStatement.setDouble(i++, dragon.getCave().getNumberOfTreasures());
        }
        preparedStatement.setInt(i++, dragon.getOwnerId());
    }

    @Override
    public int updateId(int userId, Dragon dragon) {
        String query = "INSERT INTO dragons VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        if (remove(dragon.getId(), userId) < 0) {
            return -1;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int i = 1;
            preparedStatement.setInt(i++, dragon.getId());
            prepareStatement(preparedStatement, i, dragon);
            preparedStatement.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.warning("impossible to update dragon with id" + dragon.getOwnerId());
            return -1;
        }
    }
    public int add(Dragon dragon) {
        String query = "INSERT INTO dragons VALUES (default,?,?,?,?,?,?,?,?,?,?,?) RETURNING id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int i = 1;
            prepareStatement(preparedStatement, i, dragon);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return resultSet.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.warning("impossible to add dragon with id" + dragon.getOwnerId());
            return -1;
        }
    }
    public void start(CollectionManager collectionManager) {
        try (Statement statement = connection.createStatement();) {
            statement.execute(CREATE_USERS);
            statement.execute(CREATE_TABLE);
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM DRAGONS ")) {
                while (resultSet.next()) {
                    Dragon dragon = parseDragon(resultSet);
                    if (dragon != null) {
                        collectionManager.addDragonWithoutGeneratingId(dragon);
                    } else {
                        return;
                    }
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private String encodeHash(String message) {
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest((message).getBytes(StandardCharsets.UTF_8));
            return encoder.encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }


    private Dragon parseDragon(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Double x = resultSet.getDouble("x");
            int y = resultSet.getInt("y");
            Coordinates coordinates = new Coordinates(x, y);
            Date creationDate = StringConverterRealisation.parseDate(resultSet.getString("creation_date"));
            long age = resultSet.getLong("age");
            float wingspan = resultSet.getFloat("wingspan");
            Color color;
            if (!(resultSet.getString("color") == null)) {
                color = Color.valueOf(resultSet.getString("color"));
            } else {
                color = null;
            }
            DragonType type = DragonType.valueOf(resultSet.getString("type"));
            Float depth;
            if (!(resultSet.getString("depth") == null)) {
                depth = resultSet.getFloat("depth");
            } else {
                depth = null;
            }
            Double numberOfTreasure;
            if (!(resultSet.getString("number_of_treasure") == null)) {
               numberOfTreasure = resultSet.getDouble("number_of_treasure");
            } else {
                numberOfTreasure = null;
            }

            int userId = resultSet.getInt("owner_id");
            DragonCave dragonCave = new DragonCave(depth, numberOfTreasure);
            Dragon dragon = new Dragon(name, coordinates, age, wingspan, color, type, dragonCave);
            dragon.addAttributes(creationDate, id, userId);
            return dragon;
        } catch (SQLException e) {
            return null;
        }
    }



    @Override
    public ServerMessage register(String login, String password, AbstractClient abstractClient) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(REGISTER)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, encodeHash(password));
            preparedStatement.execute();
            login(login, password, abstractClient);
            return new ServerMessage("Успешная регистрация!", ResponseCode.OK);
        } catch (SQLException e) {
            return new ServerMessage("Данный логин уже занят, попробуйте другой", ResponseCode.ERROR);
        }
    }

    @Override
    public ServerMessage login(String login, String password, AbstractClient client) {
        String loginQuery = "SELECT id FROM users WHERE name = " + "\'" + login + "\'" + " AND password = " + "\'" + encodeHash(password) + "\'";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(loginQuery);
            resultSet.next();
            int userId = resultSet.getInt("id");
            client.setUserId(userId);
            return new ServerMessage("Успешная авторизация!", ResponseCode.OK);

        } catch (SQLException e) {

            return new ServerMessage("Не удалось авторизоваться, проверьте правильность введенных данных", ResponseCode.ERROR);

        }
    }

}
