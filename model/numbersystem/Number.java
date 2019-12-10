import java.math.BigInteger;

public class Number {
    private String decimal;
    private boolean negated = false;

    public Number(String number, NumberSystem numberSystem) {
        switch (numberSystem) {
            case DEC:
                decimal = number;
                break;
            case HEX:
                decimal = Long.toString(Long.parseUnsignedLong(number, 16));
                break;
            case OCT:
                decimal = String.valueOf(Long.parseUnsignedLong(number, 8));
                break;
            case BIN:
                decimal = String.valueOf(Long.parseUnsignedLong(number, 2));
                break;
        }
    }

    public String getFormattedNumber(NumberSystem numberSystem, Word word) {
        String formattedNumber = getNumber(numberSystem, word);
        switch (numberSystem) {
            case HEX:
                return formatHex(formattedNumber);
            case OCT:
                return formatOctal(formattedNumber);
            case BIN:
                return formatBinary(formattedNumber, true);
            default:
                return formatDecimal(formattedNumber);
        }
    }

    public String getNumber(NumberSystem numberSystem, Word word) {
        switch (numberSystem) {
            case HEX:
                return Long.toHexString(getByWord(word)).toUpperCase();
            case BIN:
                return Long.toBinaryString(getByWord(word));
            case OCT:
                return Long.toOctalString(getByWord(word));
            default:
                return Long.toString(getByWord(word), numberSystem.BASE);
        }
    }

    public void negate() {
        if (!decimal.equals("0")) {
            if (decimal.substring(0, 1).equals("-"))
                decimal = decimal.substring(1);
            else
                decimal = "-" + decimal;
            negated = !negated;
        }
    }

    public long getQWORD() {
        return Long.parseLong(decimal);
    }

    private long getByWord(Word word) {
        long qword = getQWORD();
        switch (word) {
            case DWORD:
                return (int)qword;
            case WORD:
                return (short)qword;
            case BYTE:
                return (byte)qword;
            default:
                return qword;
        }
    }

    @Override
    public String toString() {
        return decimal;
    }

    public static boolean isWithinRange(String number, NumberSystem numberSystem, Word word) {
        BigInteger bigInt = new BigInteger(number, numberSystem.BASE);
        if (numberSystem != NumberSystem.DEC)
            return bigInt.bitLength() <= word.SIZE;
        else
            return bigInt.compareTo(BigInteger.valueOf(word.MAX_VALUE)) <= 0 &&
                    bigInt.compareTo(BigInteger.valueOf(word.MIN_VALUE)) >= 0;
    }

    public static String formatDecimal(String decimal) {
        boolean negative = decimal.charAt(0) == '-';
        if (negative) decimal = decimal.substring(1);

        StringBuilder result = new StringBuilder(decimal);
        for(int i=decimal.length()-3; i>=1; i-=3) {
            result.insert(i, ',');
        }

        return negative ? "-" + result.toString() : result.toString();
    }

    public static String formatHex(String hex) {
        StringBuilder result = new StringBuilder(hex);
        for(int i=hex.length()-4; i>=1; i-=4) {
            result.insert(i, ' ');
        }
        return result.toString();
    }

    public static String formatBinary(String binary, boolean leadingZeros) {
        if (binary.equals("0")) return "0";
        StringBuilder result = new StringBuilder(binary);
        int i;
        for(i=binary.length()-4; i>=1; i-=4) {
            result.insert(i, ' ');
        }
        if (leadingZeros) {
            for (; i<0; i++)
                result.insert(0, '0');
        }
        return result.toString();
    }

    public static String formatOctal(String octal) {
        StringBuilder result = new StringBuilder(octal);
        for(int i=octal.length()-3; i>=1; i-=3) {
            result.insert(i, ' ');
        }
        return result.toString();
    }
}
