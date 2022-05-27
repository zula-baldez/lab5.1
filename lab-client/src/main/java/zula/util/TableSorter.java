package zula.util;

import zula.common.data.Dragon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public final class TableSorter {

    private TableSorter() {
        throw new Error();
    }
    public static String[][] sortList(List<Dragon> dragons, String field, String typeOfSorting, ResourceBundle resourceBundle) {
        List<Dragon> sortedList = new ArrayList<>();
        if (resourceBundle.getString("From a to z").equals(typeOfSorting)) {
            sortedList = sortFromAToZ(field, dragons, resourceBundle);
        }
        if (resourceBundle.getString("From z to a").equals(typeOfSorting)) {
           sortedList = sortFromZToA(field, dragons, resourceBundle);
        }
        Parcers parcers = new Parcers();
        return parcers.parseTableFromDragons(sortedList, resourceBundle.getLocale());
    }
/*    public static List<Dragon> sortListWithoutParsing(List<Dragon> dragons, String field, String typeOfSorting, ResourceBundle resourceBundle) {
        List<Dragon> sortedList = new ArrayList<>();
        if (resourceBundle.getString("From a to z").equals(typeOfSorting)) {
            sortedList = sortFromAToZ(field, dragons, resourceBundle);
        }
        if (resourceBundle.getString("From z to a").equals(typeOfSorting)) {
            sortedList = sortFromZToA(field, dragons, resourceBundle);
        }
        return sortedList;
    }*/
    private static List<Dragon> sortFromZToA(String field, List<Dragon> dragons, ResourceBundle resourceBundle) {
        List<Dragon> fromAToZ = sortFromAToZ(field, dragons, resourceBundle);
        Collections.reverse(fromAToZ);
        return fromAToZ;
    }

    private static List<Dragon> sortFromAToZ(String field, List<Dragon> dragons, ResourceBundle resourceBundle) {
        List<Dragon> sortedList = null;
        if (resourceBundle.getString("id").equals(field)) {
            sortedList = sortById(dragons);
        }
        if (resourceBundle.getString("name").equals(field)) {
            sortedList = sortByName(dragons);
        }
        if (resourceBundle.getString("x").equals(field)) {
            sortedList = sortByX(dragons);
        }
        if (resourceBundle.getString("y").equals(field)) {
            sortedList = sortByY(dragons);
        }
        if (resourceBundle.getString("creationDate").equals(field)) {
            sortedList = sortByDate(dragons);
        }
        if (resourceBundle.getString("age").equals(field)) {
            sortedList = sortByAge(dragons);
        }
        if (resourceBundle.getString("wingspan").equals(field)) {
            sortedList = sortByWingspan(dragons);
        }
        if (resourceBundle.getString("color").equals(field)) {
            sortedList = sortByColor(dragons);
        }
        if (resourceBundle.getString("type").equals(field)) {
            sortedList = sortByType(dragons);
        }
        if (resourceBundle.getString("depth").equals(field)) {
            sortedList = sortByDepth(dragons);
        }
        if (resourceBundle.getString("Number ot Treasures").equals(field)) {
            sortedList = sortByNumOfTres(dragons);
        }
        if (resourceBundle.getString("owner_id").equals(field)) {
            sortedList = sortByOwner(dragons);
        }
        return sortedList;
    }

    private static List<Dragon> sortById(List<Dragon> dragons) {

        return dragons.stream().sorted(Comparator.comparingInt(Dragon::getId)).collect(Collectors.toList());
    }

    private static List<Dragon> sortByName(List<Dragon> dragons) {

        return dragons.stream().sorted(Comparator.comparing(Dragon::getName)).collect(Collectors.toList());
    }

    private static List<Dragon> sortByX(List<Dragon> dragons) {

        return dragons.stream().sorted((first, second) -> (int) (first.getCoordinates().getX() - second.getCoordinates().getX())).collect(Collectors.toList());
    }

    private static List<Dragon> sortByY(List<Dragon> dragons) {

        return dragons.stream().sorted(Comparator.comparingInt(o -> o.getCoordinates().getY())).collect(Collectors.toList());
    }

    private static List<Dragon> sortByDate(List<Dragon> dragons) {

        return dragons.stream().sorted(Comparator.comparing(Dragon::getCreationDate)).collect(Collectors.toList());
    }

    private static List<Dragon> sortByAge(List<Dragon> dragons) {

        return dragons.stream().sorted(Comparator.comparing(Dragon::getAge)).collect(Collectors.toList());
    }

    private static List<Dragon> sortByWingspan(List<Dragon> dragons) {

        return dragons.stream().sorted(Comparator.comparing(Dragon::getWingspan)).collect(Collectors.toList());
    }

    private static List<Dragon> sortByColor(List<Dragon> dragons) {

        return dragons.stream().sorted(new Comparator<Dragon>() {
            @Override
            public int compare(Dragon first, Dragon second) {
                if (first.getColor() != null && second.getColor() != null)       {
                    return first.getColor().compareTo(second.getColor());
                }
                if (first.getColor() == null) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }).collect(Collectors.toList());
    }

    private static List<Dragon> sortByType(List<Dragon> dragons) {

        return dragons.stream().sorted(Comparator.comparing(Dragon::getType)).collect(Collectors.toList());
    }

    private static List<Dragon> sortByDepth(List<Dragon> dragons) {

        return dragons.stream().sorted(new Comparator<Dragon>() {
            @Override
            public int compare(Dragon first, Dragon second) {
                Float depthFirst = first.getCave().getDepth();
                Float depthSecond = second.getCave().getDepth();
                if (depthFirst != null && depthSecond != null)       {
                    return depthFirst.compareTo(depthSecond);
                }
                if (depthFirst == null) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }).collect(Collectors.toList());
    }

    private static List<Dragon> sortByNumOfTres(List<Dragon> dragons) {

        return dragons.stream().sorted(new Comparator<Dragon>() {
            @Override
            public int compare(Dragon first, Dragon second) {
                Double numOfTresFirst = first.getCave().getNumberOfTreasures();
                Double numOfTresSecond = second.getCave().getNumberOfTreasures();
                if (numOfTresFirst != null && numOfTresSecond != null)       {
                    return numOfTresFirst.compareTo(numOfTresSecond);
                }
                if (numOfTresFirst == null) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }).collect(Collectors.toList());
    }

    private static List<Dragon> sortByOwner(List<Dragon> dragons) {

        return dragons.stream().sorted(Comparator.comparing(Dragon::getOwnerId)).collect(Collectors.toList());
    }
}
