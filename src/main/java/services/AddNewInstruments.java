package services;

import dao.DaoFactory;
import model.Instrument;
import model.Subscription;
import model.User;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by iMac on 13/03/17.
 */
public class AddNewInstruments {

    public static void addNewInstruments(String instrument, DaoFactory daoFactory, HttpSession session, User user) throws SQLException {
        if (instrument != null && !instrument.isEmpty()) {
            String[] instrumentsArray = instrument.trim().toLowerCase().split("[\\s\\.,]+");
            List<Instrument> instrumentsAll = daoFactory.getInstrumentDao().getAllInstruments();
            List<Instrument> instrumentsToAdd = new ArrayList<>();
            List<Instrument> instrumentsToAttribute = new ArrayList<>();
            boolean flag;
            for (int i = 0; i < instrumentsArray.length; i++) {
                flag = true;
                for (Instrument inst : instrumentsAll) {
                    if (inst.getInstrumentName().equals(instrumentsArray[i])) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    instrumentsToAdd.add(new Instrument(0, instrumentsArray[i]));
                }
                instrumentsToAttribute.add(new Instrument(0, instrumentsArray[i]));
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
