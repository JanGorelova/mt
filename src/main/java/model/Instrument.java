package model;

/**
 * Created by Air on 08/02/2017.
 */
public class Instrument {

    private long instrumentId;
    private String instrumentName;

    public Instrument() {
    }

    public Instrument(long instrumentId, String instrumentName) {
        this.instrumentId = instrumentId;
        this.instrumentName = instrumentName;
    }

    public long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }
}
