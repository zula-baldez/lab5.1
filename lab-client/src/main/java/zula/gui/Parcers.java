package zula.gui;

import zula.common.data.Dragon;

import java.io.Serializable;
import java.util.List;

public class Parcers {
    private final int idIndex = 0;
    private final int nameIndex = 1;
    private final int xIndex = 2;
    private final int yIndex = 3;
    private final int creationDateIndex = 4;
    private final int ageIndex = 5;
    private final int wingspanIndex = 6;
    private final int colorIndex = 7;
    private final int typeIndex = 8;
    private final int depthIndex = 9;
    private final int numOfTresIndex = 10;
    private final int ownerIdIndex = 11;

    public String[][] parseTableFromDragons(List<Dragon> result) {
        String[][] table = new String[result.size()][12];
        for(int i = 0; i < result.size(); i++) {
            Dragon dragon = result.get(i);

            table[i][idIndex] = Integer.toString(dragon.getId());
            table[i][nameIndex] = dragon.getName();
            table[i][xIndex] = Double.toString(dragon.getCoordinates().getX());
            table[i][yIndex] = Integer.toString(dragon.getCoordinates().getY());
            table[i][creationDateIndex] = dragon.getCreationDate().toString();
            table[i][ageIndex] = Long.toString(dragon.getAge());
            table[i][wingspanIndex] = Float.toString(dragon.getWingspan());
            if(dragon.getColor() != null) {
                table[i][colorIndex] = dragon.getColor().toString();
            } else {
                table[i][colorIndex] = "null";
            }
            table[i][typeIndex] = dragon.getType().toString();
            if(dragon.getCave().getDepth() != null) {
                table[i][depthIndex] = dragon.getCave().getDepth().toString();
            } else {
                table[i][depthIndex] = "null";
            }
            if(dragon.getCave().getNumberOfTreasures() != null) {
                table[i][numOfTresIndex] = dragon.getCave().getNumberOfTreasures().toString();
            } else {
                table[i][numOfTresIndex] = "null";
            }
            table[i][ownerIdIndex] = Integer.toString(dragon.getOwnerId());

        }
        return table;
    }
}
