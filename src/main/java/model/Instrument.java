package model;

/**
 * DTO class that represents a musical instrument
 */
public class Instrument {

    private long instrumentId;
    private String instrumentName;

    /**
     * All-args constructor for Instrument instance
     * @param instrumentId instrument id
     * @param instrumentName name of the instrument
     */
    public Instrument(long instrumentId, String instrumentName) {
        this.instrumentId = instrumentId;
        this.instrumentName = instrumentName;
    }

    /**
     * No-args constructor for Instrument instance
     */
    public Instrument() {
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
