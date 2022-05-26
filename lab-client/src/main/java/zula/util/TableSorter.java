package zula.util;

import zula.common.data.Dragon;

import java.util.*;
import java.util.stream.Collectors;

public final class TableSorter {

    private TableSorter() {
        throw new Error();
    }
    public static String[][] sortList(List<Dragon> dragons, String field, String typeOfSorting, ResourceBundle resourceBundle) {
        List<Dragon> sortedList = new ArrayList<>();
        if (resourceBundle.getString("From a to z").equals(typeOfSorting)) {
            sortedList = sortFromAToZ(field, dragons);
        }
        if (resourceBundle.getString("From z to a").equals(typeOfSorting)) {
           sortedList = sortFromZToA(field, dragons);
        }
        Parcers parcers = new Parcers();
        return parcers.parseTableFromDragons(sortedList, resourceBundle.getLocale());
    }
    public static List<Dragon> sortListWithoutParsing(List<Dragon> dragons, String field, String typeOfSorting, ResourceBundle resourceBundle) {
        List<Dragon> sortedList = new ArrayList<>(); //todo might have not been inizialuzed
        if (resourceBundle.getString("From a to z").equals(typeOfSorting)) {
            sortedList = sortFromAToZ(field, dragons);
        }
        if (resourceBundle.getString("From z to a").equals(typeOfSorting)) {
            sortedList = sortFromZToA(field, dragons);
        }
        return sortedList;
    }
    private static List<Dragon> sortFromZToA(String field, List<Dragon> dragons) {
        List<Dragon> fromAToZ = sortFromAToZ(field, dragons);
        Collections.reverse(fromAToZ);
        return fromAToZ;
    }

    private static List<Dragon> sortFromAToZ(String field, List<Dragon> dragons) {
        List sortedList = null;
        if ("id".equals(field)) {
            sortedList = sortById(dragons);
        }
        if ("name".equals(field)) {
            sortedList = sortByName(dragons);
        }
        if ("x".equals(field)) {
            sortedList = sortByX(dragons);
        }
        if ("y".equals(field)) {
            sortedList = sortByY(dragons);
        }
        if ("creationDate".equals(field)) {
            sortedList = sortByDate(dragons);
        }
        if ("age".equals(field)) {
            sortedList = sortByAge(dragons);
        }
        if ("wingspan".equals(field)) {
            sortedList = sortByWingspan(dragons);
        }
        if ("color".equals(field)) {
            sortedList = sortByColor(dragons);
        }
        if ("type".equals(field)) {
            sortedList = sortByType(dragons);
        }
        if ("depth".equals(field)) {
            sortedList = sortByDepth(dragons);
        }
        if ("Number ot Treasures".equals(field)) {
            sortedList = sortByNumOfTres(dragons);
        }
        if ("owner_id".equals(field)) {
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

        return dragons.stream().sorted((o1, o2) -> (int) (o1.getCoordinates().getX() - o2.getCoordinates().getX())).collect(Collectors.toList()); //todo
    }

    private static List<Dragon> sortByY(List<Dragon> dragons) {

        return dragons.stream().sorted((o1, o2) -> (int) (o1.getCoordinates().getY() - o2.getCoordinates().getY())).collect(Collectors.toList()); //todo
    }

    private static List<Dragon> sortByDate(List<Dragon> dragons) {

        return dragons.stream().sorted(Comparator.comparing(Dragon::getCreationDate)).collect(Collectors.toList()); //todo
    }

    private static List<Dragon> sortByAge(List<Dragon> dragons) {

        return dragons.stream().sorted(Comparator.comparing(Dragon::getAge)).collect(Collectors.toList()); //todo
    }

    private static List<Dragon> sortByWingspan(List<Dragon> dragons) {

        return dragons.stream().sorted(Comparator.comparing(Dragon::getWingspan)).collect(Collectors.toList()); //todo
    }

    private static List<Dragon> sortByColor(List<Dragon> dragons) {

        return dragons.stream().sorted(new Comparator<Dragon>() {
            @Override
            public int compare(Dragon o1, Dragon o2) {
                if (o1.getColor() != null && o2.getColor() != null)       {
                    return o1.getColor().compareTo(o2.getColor());
                }
                if (o1.getColor() == null) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }).collect(Collectors.toList()); //todo
    }

    private static List<Dragon> sortByType(List<Dragon> dragons) {

        return dragons.stream().sorted(Comparator.comparing(Dragon::getType)).collect(Collectors.toList()); //todo
    }

    private static List<Dragon> sortByDepth(List<Dragon> dragons) {

        return dragons.stream().sorted(new Comparator<Dragon>() {
            @Override
            public int compare(Dragon o1, Dragon o2) {
                Float depthFirst = o1.getCave().getDepth();
                Float depthSecond = o2.getCave().getDepth();
                if (depthFirst != null && depthSecond != null)       {
                    return depthFirst.compareTo(depthSecond);
                }
                if (depthFirst == null) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }).collect(Collectors.toList()); //todo
    }

    private static List<Dragon> sortByNumOfTres(List<Dragon> dragons) {

        return dragons.stream().sorted(new Comparator<Dragon>() {
            @Override
            public int compare(Dragon o1, Dragon o2) {
                Double numOfTresFirst = o1.getCave().getNumberOfTreasures();
                Double numOfTresSecond = o2.getCave().getNumberOfTreasures();
                if (numOfTresFirst != null && numOfTresSecond != null)       {
                    return numOfTresFirst.compareTo(numOfTresSecond);
                }
                if (numOfTresFirst == null) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }).collect(Collectors.toList()); //todo
    }

    private static List<Dragon> sortByOwner(List<Dragon> dragons) {

        return dragons.stream().sorted(Comparator.comparing(Dragon::getOwnerId)).collect(Collectors.toList()); //todo
    }
}
