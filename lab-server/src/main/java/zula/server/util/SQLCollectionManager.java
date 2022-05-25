package zula.server.util;

import zula.common.data.Color;
import zula.common.data.Coordinates;
import zula.common.data.Dragon;
import zula.common.data.DragonCave;
import zula.common.data.DragonType;
import zula.common.data.ResponseCode;
import zula.common.util.AbstractClient;
import zula.common.util.CollectionManager;
import zula.common.util.SQLManager;
import zula.common.util.StringConverterRealisation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.logging.Logger;

public class SQLCollectionManager implements SQLManager {

    private static final int NAME_COLUMN_INDEX = 1;
    private static final int X_COORDINATE_COLUMN_INDEX = 2;
    private static final int Y_COORDINATE_COLUMN_INDEX = 3;
    private static final int DATE_COLUMN_INDEX = 4;
    private static final int AGE_COLUMN_INDEX = 5;
    private static final int WINGSPAN_COLUMN_INDEX = 6;
    private static final int COLOR_COLUMN_INDEX = 7;
    private static final int TYPE_COLUMN_INDEX = 8;
    private static final int DEPTH_COLUMN_INDEX = 9;
    private static final int NUMBER_OF_TREASURE_COLUMN_INDEX = 10;
    private static final int OWNER_ID_COLUMN_INDEX = 11;
    private static final int ID_COLUMN_INDEX = 12;



    private final Connection connection;
    private final Logger logger = Logger.getLogger("SQLManager");
    private final EncryptingManager encryptingManager = new EncryptingManager();


    public SQLCollectionManager(Connection connection) {
        this.connection = connection;
    }

    public ResponseCode remove(int id, int userId) {
        String query = "SELECT * FROM dragons WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            if (resultSet.getInt("owner_id") == userId) {
                query = "DELETE FROM dragons WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, id);
                    preparedStatement.execute();
                }
                return ResponseCode.OK;
            } else {
                return ResponseCode.ERROR;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseCode.ERROR;
        }
    }

    @Override
    public ResponseCode removeUsersDragons(int userId) {
        String query = "DELETE FROM dragons WHERE owner_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.execute();
            return ResponseCode.OK;
        } catch (SQLException e) {
            e.printStackTrace();

            logger.severe(e.getMessage());
            return ResponseCode.ERROR;
        }
    }



    @Override
    public ResponseCode removeLower(int userId, int id) {
        String checkIfIdExistsQuery = "SELECT * FROM dragons WHERE id = ?";
        String getAllDragonsQuery = "SELECT * FROM dragons";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkIfIdExistsQuery)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Dragon dragonWithSelectedId;
            if (!resultSet.next()) {
                return ResponseCode.ERROR;
            } else {
                dragonWithSelectedId = parseDragon(resultSet);
            }
            try (PreparedStatement statement = connection.prepareStatement(getAllDragonsQuery)) {
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Dragon dragon = parseDragon(resultSet);
                    if (dragonWithSelectedId.compareTo(dragon) > 0) {
                        remove(dragon.getId(), userId);
                    }
                }
                return ResponseCode.OK;
            }
        } catch (SQLException e) {
            e.printStackTrace();

            logger.severe(e.getMessage());
            return ResponseCode.ERROR;
        }
    }




    private void prepareStatement(PreparedStatement preparedStatement, Dragon dragon) throws SQLException {

        preparedStatement.setString(NAME_COLUMN_INDEX, dragon.getName());
        preparedStatement.setDouble(X_COORDINATE_COLUMN_INDEX, dragon.getCoordinates().getX());
        preparedStatement.setInt(Y_COORDINATE_COLUMN_INDEX, dragon.getCoordinates().getY());
        preparedStatement.setString(DATE_COLUMN_INDEX, dragon.getCreationDate().toString());
        preparedStatement.setLong(AGE_COLUMN_INDEX, dragon.getAge());
        preparedStatement.setFloat(WINGSPAN_COLUMN_INDEX, dragon.getWingspan());
        if (dragon.getColor() == null) {
            preparedStatement.setNull(COLOR_COLUMN_INDEX, Types.ARRAY);
        } else {
            preparedStatement.setString(COLOR_COLUMN_INDEX, dragon.getColor().toString());
        }
        preparedStatement.setString(TYPE_COLUMN_INDEX, dragon.getType().toString());

        if (dragon.getCave().getDepth() == null) {
            preparedStatement.setNull(DEPTH_COLUMN_INDEX, Types.DOUBLE);
        } else {
            preparedStatement.setDouble(DEPTH_COLUMN_INDEX, dragon.getCave().getDepth());
        }
        if (dragon.getCave().getNumberOfTreasures() == null) {
            preparedStatement.setNull(NUMBER_OF_TREASURE_COLUMN_INDEX, Types.ARRAY);
        } else {
            preparedStatement.setDouble(NUMBER_OF_TREASURE_COLUMN_INDEX, dragon.getCave().getNumberOfTreasures());
        }
        preparedStatement.setInt(OWNER_ID_COLUMN_INDEX, dragon.getOwnerId());
    }

    @Override
    public ResponseCode updateId(int userId, Dragon dragon) {
        String query = "INSERT INTO dragons VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        if (remove(dragon.getId(), userId) == ResponseCode.ERROR) {
            return ResponseCode.ERROR;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(ID_COLUMN_INDEX, dragon.getId());
            prepareStatement(preparedStatement, dragon);
            preparedStatement.execute();
            return ResponseCode.OK;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseCode.ERROR;
        }
    }
    public int add(Dragon dragon) { //returns id that the database has given to the object
        String query = "INSERT INTO dragons VALUES (?,?,?,?,?,?,?,?,?,?,?, default) RETURNING id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            prepareStatement(preparedStatement, dragon);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return resultSet.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();

            logger.severe(e.getMessage());
            return -1;
        }
    }
    public void start(CollectionManager collectionManager) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createUsers = "CREATE TABLE IF NOT EXISTS USERS (\n"
                    + "                        NAME VARCHAR(50) UNIQUE,\n"
                    + "                        ID serial primary key,\n"
                    + "                        PASSWORD VARCHAR(64)\n"
                    + ")";
            statement.execute(createUsers);
            String createTable = "CREATE TABLE IF NOT EXISTS DRAGONS(\n"
                    + "                        name VARCHAR(50) NOT NULL,\n"
                    + "                        x double precision CHECK(x >= -23) NOT NULL,\n"
                    + "                        y int CHECK(y < 161) NOT NULL,\n"
                    + "                        creation_date  VARCHAR(50) NOT NULL,\n"
                    + "                        age BIGINT NOT NULL,\n"
                    + "                        wingspan FLOAT NOT NULL,\n"
                    + "                        color VARCHAR(50),\n"
                    + "                        type VARCHAR(50) NOT NULL,\n"
                    + "                        depth float4,\n"
                    + "                        number_of_treasure DOUBLE PRECISION,\n"
                    + "                        owner_id integer NOT NULL,\n"
                    + "                        FOREIGN KEY(owner_id) REFERENCES users ON DELETE CASCADE,"
                    + "                        id SERIAL PRIMARY KEY)\n";
            statement.execute(createTable);
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM DRAGONS ")) {
                while (resultSet.next()) {
                    Dragon dragon = parseDragon(resultSet);
                    if (dragon != null) {
                        collectionManager.addDragonWithoutGeneratingId(dragon);
                    } else {
                        throw new SQLException();
                    }
                }
            }
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
            e.printStackTrace();

            logger.severe(e.getMessage());
            return null;
        }
    }



    @Override
    public ResponseCode register(String login, String password, AbstractClient abstractClient) {

        String register = "INSERT INTO USERS VALUES (?, default, ?) RETURNING id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(register)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, encryptingManager.encodeHash(password));
            preparedStatement.execute();
            login(login, password, abstractClient);
            return ResponseCode.OK;
        } catch (SQLException e) {
            e.printStackTrace();

            return ResponseCode.ERROR;
        }
    }

    @Override
    public ResponseCode login(String login, String password, AbstractClient client) {
        String loginQuery = "SELECT id FROM users WHERE name = ? AND password = ?";
        try (PreparedStatement statement =  connection.prepareStatement(loginQuery)) {
            statement.setString(1, login);
            statement.setString(2, encryptingManager.encodeHash(password));
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int userId = resultSet.getInt("id");
            client.setUserId(userId);
            return ResponseCode.OK;
        } catch (SQLException e) {
            e.printStackTrace();


            return ResponseCode.ERROR;

        }
    }

}
