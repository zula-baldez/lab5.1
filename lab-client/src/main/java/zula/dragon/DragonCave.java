package zula.dragon;

import java.util.Objects;

public class DragonCave {
    private Float depth; //Поле может быть null
    private Double numberOfTreasures; //Поле может быть null, Значение поля должно быть больше 0
    public DragonCave(Float depth, Double numberOfTreasures) {
        this.depth = depth;
        this.numberOfTreasures = numberOfTreasures;
    }

    public Float getDepth() {
        return depth;
    }

    public Double getNumberOfTreasures() {
        return numberOfTreasures;
    }



    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        DragonCave that = (DragonCave) object;
        return Objects.equals(depth, that.depth) && Objects.equals(numberOfTreasures, that.numberOfTreasures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depth, numberOfTreasures);
    }
}
