package it.thedarksword.bedwarspractice.utils.values;

public abstract class MutableValue<N extends Number> extends Number implements Comparable<N> {

    private static final long serialVersionUID = -4976712689860095931L;

    protected N n;

    public MutableValue(N n) {
        this.n = n;
    }

    public N value() {
        return n;
    }

    public void setValue(N n) {
        this.n = n;
    }

    public int intValue() {
        return n.intValue();
    }

    public byte byteValue() {
        return n.byteValue();
    }

    public short shortValue() {
        return n.shortValue();
    }

    public double doubleValue() {
        return n.doubleValue();
    }

    public float floatValue() {
        return n.floatValue();
    }

    public long longValue() {
        return n.longValue();
    }

    @Override
    public String toString() {
        return n.toString();
    }

    @Override
    public boolean equals(Object o) {
        return n == o;
    }

    public abstract <E extends MutableValue<N>> E increment();
    public abstract <E extends MutableValue<N>> E increment(N val);

    public abstract <E extends MutableValue<N>> E decrement();
    public abstract <E extends MutableValue<N>> E decrement(N val);

    public <E extends MutableValue<N>> E increase() {
        return increment();
    }

    public <E extends MutableValue<N>> E increase(N val) {
        return increment(val);
    }

    public <E extends MutableValue<N>> E decrease() {
        return decrement();
    }
    public <E extends MutableValue<N>> E decrease(N val) {
        return decrement(val);
    }

    protected static class MutableByte extends MutableValue<Byte> {

        protected MutableByte(Byte aByte) {
            super(aByte);
        }

        protected MutableByte() {
            this((byte) 0);
        }

        @Override
        public MutableByte increment(Byte val) {
            n = (byte) (n + val);
            return this;
        }

        @Override
        public MutableByte increment() {
            n++;
            return this;
        }

        @Override
        public MutableByte decrement(Byte val) {
            n = (byte) (n - val);
            return this;
        }

        @Override
        public MutableByte decrement() {
            n--;
            return this;
        }

        @Override
        public int compareTo(Byte b) {
            return n.compareTo(b);
        }
    }

    protected static class MutableFloat extends MutableValue<Float> {

        protected MutableFloat(Float aFloat) {
            super(aFloat);
        }

        protected MutableFloat() {
            this(0.0f);
        }

        @Override
        public MutableFloat increment() {
            n++;
            return this;
        }

        @Override
        public MutableFloat increment(Float val) {
            n += val;
            return this;
        }

        @Override
        public MutableFloat decrement() {
            n--;
            return this;
        }

        @Override
        public MutableFloat decrement(Float val) {
            n -= val;
            return this;
        }

        @Override
        public int compareTo(Float f) {
            return n.compareTo(f);
        }

    }

    protected static class MutableLong extends MutableValue<Long> {

        protected MutableLong(Long aLong) {
            super(aLong);
        }

        protected MutableLong() {
            this(0L);
        }

        @Override
        public MutableLong increment() {
            n++;
            return this;
        }

        @Override
        public MutableLong increment(Long val) {
            n += val;
            return this;
        }

        @Override
        public MutableLong decrement() {
            n--;
            return this;
        }

        @Override
        public MutableLong decrement(Long val) {
            n -= val;
            return this;
        }

        @Override
        public int compareTo(Long l) {
            return n.compareTo(l);
        }

    }

    protected static class MutableShort extends MutableValue<Short> {

        protected MutableShort(Short aShort) {
            super(aShort);
        }

        protected MutableShort() {
            this((short) 0);
        }

        @Override
        public MutableShort increment() {
            n++;
            return this;
        }

        @Override
        public MutableShort increment(Short val) {
            n = (short) (n + val);
            return this;
        }

        @Override
        public MutableShort decrement() {
            n--;
            return this;
        }

        @Override
        public MutableShort decrement(Short val) {
            n = (short) (n - val);
            return this;
        }

        @Override
        public int compareTo(Short s) {
            return n.compareTo(s);
        }

    }
}
