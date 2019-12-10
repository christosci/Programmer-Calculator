public enum Operation {
    ADD      ("+", "+"),
    SUBTRACT ("-", "-"),
    MULTIPLY ("*", "×"),
    DIVIDE   ("/", "÷"),
    MODULO   ("%", "Mod"),
    OPEN     ("(", "("),
    CLOSE    (")", ")");

    private final String machineReadableSymbol;
    private final String humanReadableSymbol;

    Operation(String machineReadableSymbol, String humanReadableSymbol) {
        this.machineReadableSymbol = machineReadableSymbol;
        this.humanReadableSymbol = humanReadableSymbol;
    }

    public String getMachineReadableSymbol() {
        return machineReadableSymbol;
    }

    public String getHumanReadableSymbol() {
        return humanReadableSymbol;
    }
}
