public class Bresenham {

    public static void walkLineA(int x1, int y1, int x2, int y2, LineWalker walker) {
        // delta of exact value and rounded value of the dependent variable
        int d = 0;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int dx2 = 2 * dx; // slope scaling factors to
        int dy2 = 2 * dy; // avoid floating point

        int ix = x1 < x2 ? 1 : -1; // increment direction
        int iy = y1 < y2 ? 1 : -1;

        int x = x1;
        int y = y1;

        if (dx >= dy) {
            while (true) {
                walker.walk(x, y);
                if (x == x2)
                    break;
                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= dx2;
                }
            }
        } else {
            while (true) {
                walker.walk(x, y);
                if (y == y2)
                    break;
                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= dy2;
                }
            }
        }
    }

    public static void walkLineB(int x0, int y0, int x1, int y1, LineWalker walker) {
        int dx = Math.abs(x1 - x0);
        int sx = x0 < x1 ? 1 : -1;
        int dy = -Math.abs(y1 - y0);
        int sy = y0 < y1 ? 1 : -1;
        int error = dx + dy;

        while (true) {
            walker.walk(x0, y0);
            if (x0 == x1 && y0 == y1)
                break;
            int e2 = 2 * error;
            if (e2 >= dy) {
                if (x0 == x1)
                    break;
                error += dy;
                x0 += sx;
            }
            if (e2 <= dx) {
                if (y0 == y1)
                    break;
                error += dx;
                y0 += sy;
            }
        }
    }

    @FunctionalInterface
    public interface LineWalker {
        void walk(int x, int y);
    }
}
