package dao;

import model.Instrument;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface which defines all necessary methods for operations with Instrument objects
 */
public interface InstrumentDao {

    /**
     * Creates one or more instruments from the list
     * @param instruments - list of Instruments to create
     * @return - number of created instruments
     */
    long createInstruments(List<Instrument> instruments);

    /**
     * Read one Instrument from DB by the specified id
     * @param instrumentId - id of the instrument to read
     * @return - Instrument object with the specified id
     */
    @SuppressWarnings("unchecked")
    Instrument readInstrument(long instrumentId);

    /**
     * Clear all instruments for the specified user
     * @param userId - user id
     */
    void deleteAllUserInstruments(long userId);

    /**
     * Reads all instruments of the specified user
     * @param userId - user id
     * @return - ArrayList of Instruments
     */
    List<Instrument> getUserInstruments(long userId);

    /**
     * Reads all instruments registered in the database
     * @return - ArrayList of all Instruments
     */
    List<Instrument> getAllInstruments();

    /**
     * Assigns one or more instruments to the specified user
     * @param userId - user id
     * @param instruments - array of the instruments' names
     * @return number of instruments that has been added
     * @throws SQLException if the arguments are incorrect
     */
    int setInstrumentsToUser(long userId, String[] instruments) throws SQLException;
}
