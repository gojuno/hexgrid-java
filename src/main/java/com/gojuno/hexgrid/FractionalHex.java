package com.gojuno.hexgrid;

public class FractionalHex {
    private double q;
    private double r;

    public FractionalHex(double q, double r) {
        this.q = q;
        this.r = r;
    }

    public double getQ() {
        return q;
    }

    public double getR() {
        return r;
    }

    public double getS() {
        return -(q + r);
    }

    public Hex toHex() {
        long q = Math.round(getQ());
        long r = Math.round(getR());
        long s = Math.round(getS());
        double qDiff = Math.abs(q - getQ());
        double rDiff = Math.abs(r - getR());
        double sDiff = Math.abs(s - getS());

        if (qDiff > rDiff && qDiff > sDiff) {
            q = -(r + s);
        } else if (rDiff > sDiff) {
            r = -(q + s);
        }

        return new Hex(q, r);
    }

    @Override
    public String toString() {
        return String.format("fraction_hex{q: %d, r: %d}", q, r);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!FractionalHex.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        FractionalHex other = (FractionalHex)obj;

        return other.q == q && other.r == r;
    }
}
