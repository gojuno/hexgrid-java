package com.gojuno.hexgrid;

public class Orientation {
    public final static Orientation POINTY = new Orientation(
            new double[]{Math.sqrt(3.0), Math.sqrt(3.0) / 2.0, 0.0, 3.0 / 2.0},
            new double[]{Math.sqrt(3.0) / 3.0, -1.0 / 3.0, 0.0, 2.0 / 3.0},
            0.5);

    public final static Orientation FLAT = new Orientation(
            new double[]{3.0 / 2.0, 0.0, Math.sqrt(3.0) / 2.0, Math.sqrt(3.0)},
            new double[]{2.0 / 3.0, 0.0, -1.0 / 3.0, Math.sqrt(3.0) / 3.0},
            0.0);

    private double[] f;
    private double[] b;
    private double startAngle;
    private double[] sinuses;
    private double[] consinuses;

    public Orientation(double[] f, double[] b, double startAngle) {
        this.f = f;
        this.b = b;
        this.startAngle = startAngle;
        prehashAngles();
    }

    public double[] getF() {
        return f;
    }

    public double[] getB() {
        return b;
    }

    public double getStartAngle() {
        return startAngle;
    }

    public double[] getSinuses() {
        return sinuses;
    }

    public double[] getCosinuses() {
        return consinuses;
    }

    private void prehashAngles() {
        this.sinuses = new double[6];
        this.consinuses = new double[6];
        for (int i = 0; i < 6; i++) {
            double angle = 2.0 * Math.PI * (i + this.getStartAngle()) / 6.0;
            this.sinuses[i] = Math.sin(angle);
            this.consinuses[i] = Math.cos(angle);
        }
    }
}
