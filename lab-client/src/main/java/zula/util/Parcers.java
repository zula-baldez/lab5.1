package zula.util;

import zula.common.data.Dragon;
import zula.common.util.StringConverterRealisation;

import java.util.List;
import java.util.Locale;

public class Parcers {
    private static final int ID_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int X_INDEX = 2;
    private static final int Y_INDEX = 3;
    private static final int CREATION_DATE_INDEX = 4;
    private static final int AGE_INDEX = 5;
    private static final int WINGSPAN_INDEX = 6;
    private static final int COLOR_INDEX = 7;
    private static final int TYPE_INDEX = 8;
    private static final int DEPTH_INDEX = 9;
    private static final int NUM_OF_TRES_INDEX = 10;
    private static final int OWNER_ID_INDEX = 11;
    private static final int COLUMNS_NUMBER = 12;

    public String[][] parseTableFromDragons(List<Dragon> result, Locale locale) {
        String[][] table = new String[result.size()][COLUMNS_NUMBER];
        for (int i = 0; i < result.size(); i++) {
            Dragon dragon = result.get(i);
            table[i][ID_INDEX] = StringConverterRealisation.localeNumber(dragon.getId(), locale);
            table[i][NAME_INDEX] = dragon.getName();
            table[i][X_INDEX] = StringConverterRealisation.localeNumber(dragon.getCoordinates().getX(), locale);
            table[i][Y_INDEX] = StringConverterRealisation.localeNumber(dragon.getCoordinates().getY(), locale);
            table[i][CREATION_DATE_INDEX] =  StringConverterRealisation.formatDate(dragon.getCreationDate().toString(), locale);
            table[i][AGE_INDEX] = StringConverterRealisation.localeNumber(dragon.getAge(), locale);
            table[i][WINGSPAN_INDEX] = StringConverterRealisation.localeNumber(dragon.getWingspan(), locale);
            if (dragon.getColor() != null) {
                table[i][COLOR_INDEX] = dragon.getColor().toString();
            } else {
                table[i][COLOR_INDEX] = "null";
            }
            table[i][TYPE_INDEX] = dragon.getType().toString();
            if (dragon.getCave().getDepth() != null) {
                table[i][DEPTH_INDEX] =  StringConverterRealisation.localeNumber(dragon.getCave().getDepth(), locale);
            } else {
                table[i][DEPTH_INDEX] = "null";
            }
            if (dragon.getCave().getNumberOfTreasures() != null) {
                table[i][NUM_OF_TRES_INDEX] = StringConverterRealisation.localeNumber(dragon.getCave().getNumberOfTreasures(), locale);
            } else {
                table[i][NUM_OF_TRES_INDEX] = "null";
            }
            table[i][OWNER_ID_INDEX] = StringConverterRealisation.localeNumber(dragon.getOwnerId(), locale);

        }
        return table;
    }
}
