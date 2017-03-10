package dao;

import model.Instrument;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Air on 25/02/2017.
 */
public interface InstrumentDao {

    long createInstruments(Instrument[] instruments);

    Instrument readInstrument(long instrumentId);

    void updateInstrument(Instrument instrument) throws SQLException;

    void deleteInstrument(long instrumentId) throws SQLException;

    List<Instrument> getUserInstruments(long userId);

}
