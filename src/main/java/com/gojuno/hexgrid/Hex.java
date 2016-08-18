package com.gojuno.hexgrid;

public class Hex {
    private long q;
    private long r;

    public Hex(long q, long r) {
        this.q = q;
        this.r = r;
    }

    public long getQ() {
        return q;
    }

    public long getR() {
        return r;
    }

    public long getS() {
        return -(q + r);
    }

    @Override
    public String toString() {
        return String.format("hex{q: %d, r: %d}", q, r);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Hex.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        Hex other = (Hex)obj;

        return other.q == q && other.r == r;
    }
}
