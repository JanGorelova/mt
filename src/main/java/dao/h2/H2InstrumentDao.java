package dao.h2;

import dao.InstrumentDao;
import model.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * InstrumentDao implementation for the H2 database.
 */
@SuppressWarnings("unchecked")
public class H2InstrumentDao implements InstrumentDao {

    private DataSource dataSource;
    private static final Logger log = LoggerFactory.getLogger(H2InstrumentDao.class);

    // SQL queries for all necessary operations:
    private static final String CREATE_INSTRUMENT_SQL = "INSERT INTO Instruments (instrument_name) VALUES (?) ;";

    private static final String READ_INSTRUMENT_SQL = "SELECT instrument_name FROM Instruments WHERE instrument_id = ?";

    private static final String GET_ALL_INSTRUMENTS_SQL = "SELECT instrument_id, instrument_name FROM Instruments;";

    private static final String GET_USER_INSTRUMENTS_SQL =
            "SELECT i.instrument_id, i.instrument_name " +
                    "FROM Instruments AS i INNER JOIN Users_Instruments AS u " +
                    "ON i.instrument_id = u.instrument_id " +
                    "WHERE u.user_id = ?";

    private static final String DELETE_ALL_USER_INSTRUMENTS = "DELETE FROM Users_Instruments WHERE user_id = ?;";

    private static final String SET_INSTRUMENTS_TO_USER_SQL =
            "INSERT INTO Users_Instruments (user_id, instrument_id) " +
                    "VALUES (?, (SELECT instrument_id FROM Instruments WHERE instrument_name = ?))";

    /**
     * Simple constructor of the InstrumentDao implementation for the H2 database.
     * @param dataSource any DataSource
     */
    H2InstrumentDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Creates one or more instruments from the list
     * @param instruments - list of Instruments to create
     * @return - number of created instruments
     */
    @Override
    public long createInstruments(List<Instrument> instruments) {
        int[] result = {0};
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_INSTRUMENT_SQL)) {
            for (Instrument instrument : instruments) {
                statement.setString(1, instrument.getInstrumentName().trim().toLowerCase());
                statement.addBatch();
            }
            result = statement.executeBatch();
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
        return result.length;
    }

    /**
     * Read one Instrument from DB by the specified id
     * @param instrumentId - id of the instrument to read
     * @return - Instrument object with the specified id
     */
    @Override
    public Instrument readInstrument(long instrumentId) {
        Instrument instrument = new Instrument();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_INSTRUMENT_SQL)) {
            statement.setLong(1, instrumentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                instrument.setInstrumentName(resultSet.getString("instrument_name"));
                instrument.setInstrumentId(instrumentId);
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
        return instrument;
    }

    /**
     * Reads all instruments of the specified user
     * @param userId - user id
     * @return - ArrayList of Instruments
     */
    @Override
    public List<Instrument> getUserInstruments(long userId) {
        List<Instrument> instruments = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_INSTRUMENTS_SQL)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Instrument instrument = new Instrument();
                    instrument.setInstrumentId(resultSet.getLong("instrument_id"));
                    instrument.setInstrumentName(resultSet.getString("instrument_name"));
                    instruments.add(instrument);
                }
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
        return instruments;
    }

    /**
     * Reads all instruments registered in the database
     * @return - ArrayList of all Instruments
     */
    @Override
    public List<Instrument> getAllInstruments() {
        List<Instrument> instruments = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_INSTRUMENTS_SQL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Instrument instrument = new Instrument();
                    instrument.setInstrumentId(resultSet.getLong("instrument_id"));
                    instrument.setInstrumentName(resultSet.getString("instrument_name"));
                    instruments.add(instrument);
                }
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
        return instruments;
    }

    /**
     * Assigns one or more instruments to the specified user
     * @param userId - user id
     * @param instruments - array of the instruments' names
     * @return number of instruments that has been added
     * @throws SQLException if the arguments are incorrect
     */
    @Override
    public int setInstrumentsToUser(long userId, String[] instruments) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_INSTRUMENTS_TO_USER_SQL)) {
            deleteAllUserInstruments(userId);
            for (String instrumentName : instruments) {
                statement.setLong(1, userId);
                statement.setString(2, instrumentName);
                statement.addBatch();
            }
            return statement.executeBatch().length;
        }
    }

    /**
     * Clear all instruments for the specified user
     * @param userId - user id
     */
    @Override
    public void deleteAllUserInstruments(long userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ALL_USER_INSTRUMENTS)) {
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
    }
}
