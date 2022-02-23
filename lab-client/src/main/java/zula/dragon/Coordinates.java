package zula.dragon;

import java.util.Objects;

public class Coordinates {
    private Double x; //Значение поля должно быть больше -23, Поле не может быть null
    private int y; //Максимальное значение поля: 160
    public Coordinates(double x, int y) {
        this.x = x;
        this.y = y;
    }



    public double getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinates that = (Coordinates) o;
        return y == that.y && x.equals(that.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
