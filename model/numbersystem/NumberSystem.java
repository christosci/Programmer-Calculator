public enum NumberSystem {
    HEX(16),
    DEC(10),
    OCT(8),
    BIN(2);

    public final int BASE;

    NumberSystem(int BASE) {
        this.BASE = BASE;
    }
}
