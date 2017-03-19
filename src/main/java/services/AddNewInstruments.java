package services;

import dao.DaoFactory;
import model.Instrument;
import model.User;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class with static method for adding new instruments to the database
 */
public class AddNewInstruments {

    /**
     * Parses string with one or more instruments and creates new ones in the database,
     * also refreshes the Instruments session attribute
     * @param instrument String with instruments
     * @param daoFactory DaoFactory
     * @param session current session
     * @param user current User
     * @throws SQLException if something goes wrong
     */
    public static void addNewInstruments(String instrument, DaoFactory daoFactory, HttpSession session, User user) throws SQLException {
        if (instrument != null && !instrument.isEmpty()) {
            String[] instrumentsArray = instrument.trim().toLowerCase().split("[\\s\\.,]+");
            List<Instrument> instrumentsAll = daoFactory.getInstrumentDao().getAllInstruments();
            List<Instrument> instrumentsToAdd = new ArrayList<>();
            boolean flag;
            for (String anInstrumentsArray : instrumentsArray) {
                flag = true;
                for (Instrument inst : instrumentsAll) {
                    if (inst.getInstrumentName().equals(anInstrumentsArray)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    instrumentsToAdd.add(new Instrument(0, anInstrumentsArray));
                }
            }
            if (instrumentsToAdd.size() > 0) {
                daoFactory.getInstrumentDao().createInstruments(instrumentsToAdd);
            }
            daoFactory.getInstrumentDao().setInstrumentsToUser(user.getUserId(), instrumentsArray);
        } else {
            daoFactory.getInstrumentDao().deleteAllUserInstruments(user.getUserId());
        }
        session.setAttribute("Instruments", daoFactory.getInstrumentDao().getUserInstruments(user.getUserId()));
    }
}
