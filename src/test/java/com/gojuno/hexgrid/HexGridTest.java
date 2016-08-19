package com.gojuno.hexgrid;

import com.gojuno.morton.Morton64;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class HexGridTest {
    @Test
    public void testFlat() {
        HexGrid grid = new HexGrid(Orientation.FLAT, new Point(10, 20), new Point(20, 10), new Morton64(2, 32));
        assertEquals(new Hex(0, 37), grid.hexAt(new Point(13, 666)));
        assertEquals(new Hex(22, -11), grid.hexAt(new Point(666, 13)));
        assertEquals(new Hex(-1, -39), grid.hexAt(new Point(-13, -666)));
        assertEquals(new Hex(-22, 9), grid.hexAt(new Point(-666, -13)));
    }

    @Test
    public void testPointy() {
        HexGrid grid = new HexGrid(Orientation.POINTY, new Point(10, 20), new Point(20, 10), new Morton64(2, 32));
        assertEquals(new Hex(-21, 43), grid.hexAt(new Point(13, 666)));
        assertEquals(new Hex(19, 0), grid.hexAt(new Point(666, 13)));
        assertEquals(new Hex(22, -46), grid.hexAt(new Point(-13, -666)));
        assertEquals(new Hex(-19, -2), grid.hexAt(new Point(-666, -13)));
    }

    private void validatePoint(Point e, Point r, double precision) {
        assertEquals(e.getX(), r.getX(), precision);
        assertEquals(e.getY(), r.getY(), precision);
    }

    @Test
    public void testCoordinatesFlat() {
        HexGrid grid = new HexGrid(Orientation.FLAT, new Point(10, 20), new Point(20, 10), new Morton64(2, 32));
        Hex hex = grid.hexAt(new Point(666, 666));
        validatePoint(new Point(670.00000, 660.85880), grid.hexCenter(hex), 0.00001);
        Point[] expectedCorners = new Point[]{
                new Point(690.00000, 660.85880),
                new Point(680.00000, 669.51905),
                new Point(660.00000, 669.51905),
                new Point(650.00000, 660.85880),
                new Point(660.00000, 652.19854),
                new Point(680.00000, 652.19854)};
        Point[] corners = grid.hexCorners(hex);
        for (int i = 0; i < 6; i++) {
            validatePoint(expectedCorners[i], corners[i], 0.00001);
        }
     }

    @Test
    public void testCoordinatesPointy() {
        HexGrid grid = new HexGrid(Orientation.POINTY, new Point(10, 20), new Point(20, 10), new Morton64(2, 32));
        Hex hex = grid.hexAt(new Point(666, 666));
        validatePoint(new Point(650.85880, 665.00000), grid.hexCenter(hex), 0.00001);
        Point[] expectedCorners = new Point[]{
                new Point(668.17930, 670.00000),
                new Point(650.85880, 675.00000),
                new Point(633.53829, 670.00000),
                new Point(633.53829, 660.00000),
                new Point(650.85880, 655.00000),
                new Point(668.17930, 660.00000)};
        Point[] corners = grid.hexCorners(hex);
        for (int i = 0; i < 6; i++) {
            validatePoint(expectedCorners[i], corners[i], 0.00001);
        }
    }

    @Test
    public void testNeighbors() {
        HexGrid grid = new HexGrid(Orientation.FLAT, new Point(10, 20), new Point(20, 10), new Morton64(2, 32));
        Hex hex = grid.hexAt(new Point(666, 666));
        long[] expectedNeighbors = new long[]{
                920, 922, 944, 915, 921, 923, 945, 916, 918,
                926, 948, 917, 919, 925, 927, 960, 962, 968};
        Hex[] neighbors = grid.hexNeighbors(hex, 2);
        for (int i = 0; i < neighbors.length; i++) {
            assertEquals(expectedNeighbors[i], grid.hexToCode(neighbors[i]));
        }
    }

    @Test
    public void testRegion() {
        HexGrid grid = new HexGrid(Orientation.FLAT, new Point(10, 20), new Point(20, 10), new Morton64(2, 32));
        Point[] geometry = new Point[]{
                new Point(20, 19.99999), new Point(20, 40), new Point(40, 60),
                new Point(60, 40), new Point(50, 30), new Point(40, 40)};
        Region region = grid.createRegion(geometry);
        Hex[] hexes = region.getHexes();
        long[] hexCodes = new long[hexes.length];
        for (int i = 0; i < hexes.length; i++) {
            hexCodes[i] = grid.hexToCode(hexes[i]);
        }
        long[] expectedHexCodes = new long[]{0, 2, 1, 3, 9, 4};
        assertArrayEquals(expectedHexCodes, hexCodes);
    }
}
