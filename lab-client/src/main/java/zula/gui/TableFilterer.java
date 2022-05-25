package zula.gui;

import zula.common.data.Color;
import zula.common.data.Dragon;
import zula.common.data.DragonType;
import zula.common.util.StringConverterRealisation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TableFilterer {


    String[] types = new String[]{"More than", "Less than", "Equals"};
    private final String[] columnsNames = {"id", "name", "x", "y", "creationDate", "age", "wingspan", "color", "type", "depth", "Number ot Treasures", "owner_id"};
    private final static int EQUALS = 0;
    private final static int MORE = 1;
    private final static int LESS = 2;

    public static String[][] filterList(List<Dragon> dragons, String field, String value, String typeOfFiltering) {
        List<Dragon> resultOfFiltering = dragons;
        try {
            if ("More than".equals(typeOfFiltering)) {
                resultOfFiltering = filter(dragons, field, value, MORE);
            }
            if ("Less than".equals(typeOfFiltering)) {
                resultOfFiltering = filter(dragons, field, value, LESS);
            }
            if ("Equals".equals(typeOfFiltering)) {
                resultOfFiltering = filter(dragons, field, value, EQUALS);
            }
        } catch (NumberFormatException e) {
            resultOfFiltering = new ArrayList<>();
        }
        Parcers parcers = new Parcers();
        return parcers.parseTableFromDragons(resultOfFiltering);
    }

    private static List<Dragon> filter(List<Dragon> dragons, String field, String value, int typeOfFilter) {
        List filteredList = null;
        if ("id".equals(field)) {
            filteredList = filterById(dragons, Integer.parseInt(value), typeOfFilter);
        }
        if ("name".equals(field)) {
            filteredList = filterByName(dragons, value, typeOfFilter);
        }
        if ("x".equals(field)) {
            filteredList = filterByX(dragons, Double.parseDouble(value), typeOfFilter);
        }
        if ("y".equals(field)) {
            filteredList = filterByY(dragons, Integer.parseInt(value), typeOfFilter);
        }
        if ("creationDate".equals(field)) {
            filteredList = filterByDate(dragons, StringConverterRealisation.parseDate(value), typeOfFilter);
        }
        if ("age".equals(field)) {
            filteredList = filterByAge(dragons, Long.parseLong(value), typeOfFilter);
        }
        if ("wingspan".equals(field)) {
            filteredList = filterByWingspan(dragons, Float.parseFloat(value), typeOfFilter);
        }
        if ("color".equals(field)) {
            filteredList = filterByColor(dragons, Color.valueOf(value), typeOfFilter);
        }
        if ("type".equals(field)) {
            filteredList = filterByType(dragons, DragonType.valueOf(value), typeOfFilter);
        }
        if ("depth".equals(field)) {
            filteredList = filterByDepth(dragons, Float.parseFloat(value), typeOfFilter);
        }
        if ("Number ot Treasures".equals(field)) {
            filteredList = filterByNumOfTres(dragons, Double.parseDouble(value), typeOfFilter);
        }
        if ("owner_id".equals(field)) {
            filteredList = filterByOwner(dragons, Integer.parseInt(value), typeOfFilter);
        }
        return filteredList;
    }


    private static List<Dragon> filterById(List<Dragon> dragons, int value, int typeOfFilter) {
        if (typeOfFilter == LESS) {
            return dragons.stream().filter(x -> x.getId() < value).collect(Collectors.toList());
        }
        if (typeOfFilter == MORE) {
            return dragons.stream().filter(x -> x.getId() > value).collect(Collectors.toList());
        }
        return dragons.stream().filter(x -> x.getId() == value).collect(Collectors.toList());


    }

    private static List<Dragon> filterByName(List<Dragon> dragons, String value, int typeOfFilter) {
        if (typeOfFilter == LESS) {

            return dragons.stream().filter(x -> x.getName().compareTo(value) < 0).collect(Collectors.toList());
        }
        if (typeOfFilter == MORE) {
            return dragons.stream().filter(x -> x.getName().compareTo(value) > 0).collect(Collectors.toList());

        }
        return dragons.stream().filter(x -> x.getName().compareTo(value) == 0).collect(Collectors.toList());

    }

    private static List<Dragon> filterByX(List<Dragon> dragons, Double xCoord, int typeOfFilter) {
        if (typeOfFilter == LESS) {
            return dragons.stream().filter(x -> x.getCoordinates().getX() < xCoord).collect(Collectors.toList()); //todo
        }
        if (typeOfFilter == MORE) {
            return dragons.stream().filter(x -> x.getCoordinates().getX() > xCoord).collect(Collectors.toList()); //todo
        }
        return dragons.stream().filter(x -> x.getCoordinates().getX() == xCoord).collect(Collectors.toList()); //todo

    }

    private static List<Dragon> filterByY(List<Dragon> dragons, int yCoord, int typeOfFilter) {
        if (typeOfFilter == LESS) {
            return dragons.stream().filter(x -> x.getCoordinates().getY() < yCoord).collect(Collectors.toList()); //todo

        }
        if (typeOfFilter == MORE) {
            return dragons.stream().filter(x -> x.getCoordinates().getY() > yCoord).collect(Collectors.toList()); //todo

        }
        return dragons.stream().filter(x -> x.getCoordinates().getY() == yCoord).collect(Collectors.toList()); //todo

    }

    private static List<Dragon> filterByDate(List<Dragon> dragons, Date date, int typeOfFilter) {
        if (typeOfFilter == LESS) {
            return dragons.stream().filter(x -> x.getCreationDate().compareTo(date) < 0).collect(Collectors.toList()); //todo

        }
        if (typeOfFilter == MORE) {
            return dragons.stream().filter(x -> x.getCreationDate().compareTo(date) > 0).collect(Collectors.toList());

        }
        return dragons.stream().filter(x -> x.getCreationDate().compareTo(date) == 0).collect(Collectors.toList());

    }

    private static List<Dragon> filterByAge(List<Dragon> dragons, long age, int typeOfFilter) {
        if (typeOfFilter == LESS) {
            return dragons.stream().filter(x -> x.getAge() < age).collect(Collectors.toList());

        }
        if (typeOfFilter == MORE) {
            return dragons.stream().filter(x -> x.getAge() > age).collect(Collectors.toList());

        }
        return dragons.stream().filter(x -> x.getAge() == age).collect(Collectors.toList());
    }

    private static List<Dragon> filterByWingspan(List<Dragon> dragons, float wingspan, int typeOfFilter) {

        if (typeOfFilter == LESS) {
            return dragons.stream().filter(x -> x.getWingspan() < wingspan).collect(Collectors.toList());
        }
        if (typeOfFilter == MORE) {
            return dragons.stream().filter(x -> x.getWingspan() > wingspan).collect(Collectors.toList());

        }
        return dragons.stream().filter(x -> x.getWingspan() == wingspan).collect(Collectors.toList());
    }

    private static List<Dragon> filterByColor(List<Dragon> dragons, Color color, int typeOfFilter) {
        if (typeOfFilter == LESS) {
            return dragons.stream().filter(x -> {
                if (x.getColor() == null) {
                    return false;
                } else {
                    return x.getColor().compareTo(color) < 0;
                }
            }).collect(Collectors.toList());
        }
        if (typeOfFilter == MORE) {
            return dragons.stream().filter(x -> {
                if (x.getColor() == null) {
                    return false;
                } else {
                    return x.getColor().compareTo(color) > 0;
                }
            }).collect(Collectors.toList());
        }
        return dragons.stream().filter(x -> {
            if (x.getColor() == null) {
                return false;
            } else {
                return x.getColor().compareTo(color) == 0;
            }
        }).collect(Collectors.toList());
    }

    private static List<Dragon> filterByType(List<Dragon> dragons, DragonType type, int typeOfFilter) {
        if (typeOfFilter == LESS) {
            return dragons.stream().filter(x -> x.getType().compareTo(type) < 0).collect(Collectors.toList());
        }
        if (typeOfFilter == MORE) {
            return dragons.stream().filter(x -> x.getType().compareTo(type) > 0).collect(Collectors.toList());
        }
        return dragons.stream().filter(x -> x.getType().compareTo(type) == 0).collect(Collectors.toList());
    }

    private static List<Dragon> filterByDepth(List<Dragon> dragons, Float depth, int typeOfFilter) {
        if (typeOfFilter == LESS) {
            return dragons.stream().filter(x -> {
                if (x.getCave().getDepth() == null) {
                    return false;
                } else {
                    return x.getCave().getDepth().compareTo(depth) < 0;
                }
            }).collect(Collectors.toList());
        }
        if(typeOfFilter == MORE) {
            return dragons.stream().filter(x -> {
                if (x.getCave().getDepth() == null) {
                    return false;
                } else {
                    return x.getCave().getDepth().compareTo(depth) > 0;
                }
            }).collect(Collectors.toList());
        }
        return dragons.stream().filter(x -> {
            if (x.getCave().getDepth() == null) {
                return false;
            } else {
                return x.getCave().getDepth().compareTo(depth) == 0;
            }
        }).collect(Collectors.toList());
    }

    private static List<Dragon> filterByNumOfTres(List<Dragon> dragons, Double numOfTres, int typeOfFilter) {
        if(typeOfFilter == LESS) {
            return dragons.stream().filter(x -> {
                if (x.getCave().getNumberOfTreasures() == null) {
                    return false;
                } else {
                    return x.getCave().getNumberOfTreasures().compareTo(numOfTres) < 0;
                }
            }).collect(Collectors.toList());
        }
        if(typeOfFilter == MORE) {
            return dragons.stream().filter(x -> {
                if (x.getCave().getNumberOfTreasures() == null) {
                    return false;
                } else {
                    return x.getCave().getNumberOfTreasures().compareTo(numOfTres) > 0;
                }
            }).collect(Collectors.toList());
        }
        return dragons.stream().filter(x -> {
            if (x.getCave().getNumberOfTreasures() == null) {
                return false;
            } else {
                return x.getCave().getNumberOfTreasures().compareTo(numOfTres) == 0;
            }
        }).collect(Collectors.toList());
    }

    private static List<Dragon> filterByOwner(List<Dragon> dragons, int ownerId, int typeOfFilter) {
        if(typeOfFilter == LESS) {
            return dragons.stream().filter(x -> x.getOwnerId() < ownerId).collect(Collectors.toList());
        }
        if(typeOfFilter == MORE) {
            return dragons.stream().filter(x -> x.getOwnerId() > ownerId).collect(Collectors.toList());

        }
        return dragons.stream().filter(x -> x.getOwnerId() == ownerId).collect(Collectors.toList());
    }
}
