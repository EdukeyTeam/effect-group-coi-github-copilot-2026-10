package pl.workshop.qa;

/** Pure behavior already supplied; HTTP reservation writing remains a learner task. */
public final class AppointmentPolicy {
    public enum Decision { ACCEPTED, INVALID_PARTY, FULL }

    public Decision evaluate(int partySize, int remaining) {
        if (remaining < 0) throw new IllegalArgumentException("remaining must be non-negative");
        if (partySize < 1 || partySize > 4) return Decision.INVALID_PARTY;
        return partySize <= remaining ? Decision.ACCEPTED : Decision.FULL;
    }
}
