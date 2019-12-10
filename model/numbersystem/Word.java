public enum Word {
    QWORD (Long.SIZE, Long.MIN_VALUE, Long.MAX_VALUE),
    DWORD (Integer.SIZE, Integer.MIN_VALUE, Integer.MAX_VALUE),
    WORD  (Short.SIZE, Short.MIN_VALUE, Short.MAX_VALUE),
    BYTE  (Byte.SIZE, Byte.MIN_VALUE, Byte.MAX_VALUE);

    public final int SIZE;
    public final long MIN_VALUE;
    public final long MAX_VALUE;
    private static final Word[] wordArray = values();

    Word(int numberOfBits, long minValue, long maxValue) {
        this.SIZE = numberOfBits;
        this.MIN_VALUE = minValue;
        this.MAX_VALUE = maxValue;
    }

    public Word next() {
        return wordArray[(this.ordinal()+1) % wordArray.length];
    }
}
