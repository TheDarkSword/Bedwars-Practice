package it.thedarksword.bedwarspractice.utils.values;

import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.Optional;

public final class Numeric {

    private Numeric() {
        throw new AssertionError("Nope.");
    }

    public static boolean isNumeric(String string) {
        Validate.notNull(string, "Cannot check numeric values from null String param");

        for(int i = 0; i < string.length(); ++i) {
            if (Character.isDigit(string.charAt(i)))
                continue;

            return false;
        }

        return true;
    }

    public static final class Doubles {

        private static final double MIN_VAL = 0.0D;

        public Doubles() {
            throw new AssertionError("Nope.");
        }

        public static MutableValue<Double> mutable(double value) {
            return new Mutable(value);
        }

        public static MutableValue<Double> mutable() {
            return mutable(0.0D);
        }

        public static double sum(double... doubles) {
            double result = MIN_VAL;

            for (Double dub : doubles)
                result += dub;

            return result;
        }

        public static double sum(Collection<Double> doubles) {
            double result = MIN_VAL;

            for (Double dub : doubles)
                result += dub;

            return result;
        }

        public static Optional<Double> parse(String string) {
            try {
                return Optional.of(Double.parseDouble(string));
            }catch (NumberFormatException ignored) {}

            return Optional.empty();
        }

        private static class Mutable extends MutableValue<Double> {

            private static final long serialVersionUID = 815038939955000726L;

            private Mutable(Double aDouble) {
                super(aDouble);
            }

            private Mutable() {
                this(0.0D);
            }

            @Override
            public Mutable increment() {
                n++;
                return null;
            }

            @Override
            public Mutable increment(Double val) {
                n += val;
                return this;
            }

            @Override
            public Mutable decrement() {
                n--;
                return this;
            }

            @Override
            public Mutable decrement(Double val) {
                n -= val;
                return this;
            }

            @Override
            public int compareTo(Double d) {
                return n.compareTo(d);
            }

        }
    }

    public static final class Integers {

        public Integers() {
            throw new AssertionError("Nope.");
        }

        public static MutableValue<Integer> mutable(int value) {
            return new Mutable(value);
        }

        public static MutableValue<Integer> mutable() {
            return mutable(0);
        }

        public static Optional<Integer> parse(String string) {
            try {
                return Optional.of(Integer.parseInt(string));
            }catch (NumberFormatException ignored) {}

            return Optional.empty();
        }

        private static class Mutable extends MutableValue<Integer> {

            private static final long serialVersionUID = 2859268721778877220L;

            private Mutable(Integer integer) {
                super(integer);
            }

            private Mutable() {
                this(0);
            }

            @Override
            public Mutable increment(Integer val) {
                n += val;
                return this;
            }

            @Override
            public Mutable increment() {
                n++;
                return this;
            }

            @Override
            public Mutable decrement(Integer val) {
                n -= val;
                return this;
            }

            @Override
            public Mutable decrement() {
                n--;
                return this;
            }

            @Override
            public int compareTo(Integer i) {
                return n.compareTo(i);
            }

        }
    }
}
