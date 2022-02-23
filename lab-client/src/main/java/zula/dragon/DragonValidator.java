package zula.dragon;

import zula.util.ConsoleManager;

import java.util.Date;
import java.util.HashSet;

/*
 * Class that contains methods that check if the data correct
 */
public class DragonValidator {
    private final int maxY = 160;
    private final double minX = -23;
    private final HashSet<Integer> usedId;
    private final ConsoleManager consoleManager;
    public DragonValidator(ConsoleManager consoleManagerArg) {
        usedId = consoleManagerArg.getListManager().getUsedId();
        consoleManager = consoleManagerArg;
    }
    public boolean nameValidator(String name) {
        return (name != null && !"".equals(name));
    }

    public boolean ageValidator(Long age) {
        return (age != null && age > 0);
    }

    public boolean wingspanValidator(Float wingspan) {
        return wingspan != null && wingspan > 0;
    }

    public boolean typeValidator(DragonType type) {
        return !(type == null);
    }

    public boolean colorValidator(Color color) {
        return true;
    }

    public boolean idValidator(Integer id) {
        return consoleManager.getListManager().isIdValid(id);
    }
    public boolean xValidator(Double x) {
        return (x != null && x > minX);
    }
    public boolean yValidator(Integer y) {
        return (y != null && y <= maxY);
    }
    public boolean depthValidator(Float depth) {
        return true;
    }
    public boolean numberOfTreasuresValidator(Double numberOfTreasures) {
        if (numberOfTreasures != null) {
            return numberOfTreasures > 0;
        } else {
            return true;
        }
    }
    public boolean dateValidator(Date date) {
        return !(date == null);
    }
}
