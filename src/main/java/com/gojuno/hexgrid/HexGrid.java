package com.gojuno.hexgrid;

import com.gojuno.morton.Morton64;

public class HexGrid {
    private Orientation orientation;
    private Point origin;
    private Point size;
    private Morton64 mort;

    public HexGrid(Orientation orientation, Point origin, Point size, Morton64 mort) {
        this.orientation = orientation;
        this.origin = origin;
        this.size = size;
        this.mort = mort;
    }

    public long hexToCode(Hex hex) {
        return mort.spack(hex.getQ(), hex.getR());
    }

    public Hex hexFromCode(long code) {
        long[] qr = mort.sunpack(code);
        return new Hex(qr[0], qr[1]);
    }

    public Hex hexAt(Point point) {
        double x = (point.getX() - origin.getX()) / size.getX();
        double y = (point.getY() - origin.getY()) / size.getY();
        double q = orientation.getB()[0] * x + orientation.getB()[1] * y;
        double r = orientation.getB()[2] * x + orientation.getB()[3] * y;
        return (new FractionalHex(q, r)).ToHex();
    }

    public Point hexCenter(Hex hex) {
        double x = (orientation.getF()[0] * hex.getQ() + orientation.getF()[1] * hex.getR()) * size.getX() + origin.getX();
        double y = (orientation.getF()[2] * hex.getQ() + orientation.getF()[3] * hex.getR()) * size.getY() + origin.getY();
        return new Point(x, y);
    }

    public Point[] hexCorners(Hex hex) {
        Point[] corners = new Point[6];
        Point center = hexCenter(hex);
        for (int i = 0; i < 6; i++) {
            double x = size.getX() * orientation.getCosinuses()[i] + center.getX();
            double y = size.getY() * orientation.getSinuses()[i] + center.getY();
            corners[i] = new Point(x, y);
        }
        return corners;
    }

    public Hex[] hexNeighbors(Hex hex, int layers) {
        int total = (layers + 1) * layers * 3;
        Hex[] neighbors = new Hex[total];
        int i = 0;
        for (long q = -layers; q <= layers; q++) {
            long r1 = Math.max(-layers, -q - layers);
            long r2 = Math.min(layers, -q + layers);
            for (long r = r1; r <= r2; r++) {
                if (q == 0 && r == 0) {
                    continue;
                }
                neighbors[i] = new Hex(q + hex.getQ(), r + hex.getR());
                i++;
            }
        }
        return neighbors;
    }

    public Region createRegion(Point[] geometry) {
        return new Region(this, geometry);
    }

    @Override
    public String toString() {
        return String.format("hex_grid{orientation: %s, origin: %s, size: %s, mort: %s}", orientation.toString(), origin.toString(), size.toString(), mort.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!HexGrid.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        HexGrid other = (HexGrid)obj;

        return other.orientation.equals(orientation) && other.origin.equals(origin) && other.size.equals(size) && other.mort.equals(mort);
    }
}
