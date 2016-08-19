package com.gojuno.hexgrid;

import java.util.Arrays;

public class Orientation {
    public final static Orientation POINTY = new Orientation(
            "pointy",
            new double[]{Math.sqrt(3.0), Math.sqrt(3.0) / 2.0, 0.0, 3.0 / 2.0},
            new double[]{Math.sqrt(3.0) / 3.0, -1.0 / 3.0, 0.0, 2.0 / 3.0},
            0.5);

    public final static Orientation FLAT = new Orientation(
            "flat",
            new double[]{3.0 / 2.0, 0.0, Math.sqrt(3.0) / 2.0, Math.sqrt(3.0)},
            new double[]{2.0 / 3.0, 0.0, -1.0 / 3.0, Math.sqrt(3.0) / 3.0},
            0.0);

    private String name;
    private double[] f;
    private double[] b;
    private double startAngle;
    private double[] sinuses;
    private double[] consinuses;

    public Orientation(String name, double[] f, double[] b, double startAngle) {
        this.name = name;
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
        sinuses = new double[6];
        consinuses = new double[6];
        for (int i = 0; i < 6; i++) {
            double angle = 2.0 * Math.PI * (i + getStartAngle()) / 6.0;
            sinuses[i] = Math.sin(angle);
            consinuses[i] = Math.cos(angle);
        }
    }

    @Override
    public String toString() {
        return String.format("orientation{name: %s}", name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Orientation.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        Orientation other = (Orientation) obj;

        return other.name.equals(name) && Arrays.equals(other.f, f) && Arrays.equals(other.b, b);
    }
}
