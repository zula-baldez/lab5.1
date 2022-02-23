package zula.util;

import zula.dragon.*;

import java.util.Date;
import java.util.function.Predicate;

public class ArgumentReader {
    private final ConsoleManager consoleManager;
    private final DragonValidator dragonValidator;
    public ArgumentReader(ConsoleManager consoleManager) {

        this.consoleManager = consoleManager;
        this.dragonValidator = new DragonValidator(consoleManager);
    }
    private <T> T readArg(Predicate<T> predicate, StringConverter<T> stringConverter) {
        while (true) {
            T t;
            String readedLine = consoleManager.getInputManager().read(consoleManager);
            System.out.println(readedLine);
            if ("".equals(readedLine)) {
                t = null;
            } else {
                try {
                    t = stringConverter.convert(readedLine);
                } catch (IllegalArgumentException e) {
                    consoleManager.getOutputManager().write("Неверные входные данные");
                    continue;
                }
            }
            if (predicate.test(t)) {
                return t;
            } else {
                consoleManager.getOutputManager().write("Неверные входные данные");
            }
        }
    }


    public String readName() {
        consoleManager.getOutputManager().write("Введите имя:");
        return readArg(dragonValidator::nameValidator, (String s) -> s);
    }

    public Coordinates readCoordinates() {
        consoleManager.getOutputManager().write("Введите x. Double, >= -23");
        Double x = readArg(dragonValidator::xValidator, Double::parseDouble);
        consoleManager.getOutputManager().write("Введите y. Integer, <= 160");
        Integer y = readArg(dragonValidator::yValidator, Integer::parseInt);
        return new Coordinates(x, y);
    }

    public DragonCave readCave() {
        consoleManager.getOutputManager().write("Введите depth. Float, может быть null. Для ввода null введите пустую строку");
        Float depth = readArg(dragonValidator::depthValidator, Float::parseFloat);
        consoleManager.getOutputManager().write("Введите numberOfTreasure. Double, может быть null. Для ввода null введите пустую строку");
        Double numberOfTreasure = readArg(dragonValidator::numberOfTreasuresValidator, Double::parseDouble);
        return new DragonCave(depth, numberOfTreasure);
    }
    public DragonType readType() {

        consoleManager.getOutputManager().write("Введите тип дракона из предложенных вариантов: ");
        DragonType[] types = DragonType.values();
        for (DragonType dragon : types) {
            consoleManager.getOutputManager().write(dragon.toString());
        }
        return readArg(dragonValidator::typeValidator, DragonType::valueOf);


    }

    public Long readAge() {

        consoleManager.getOutputManager().write("Введите возраст, Long:");
        return readArg(dragonValidator::ageValidator, Long::parseLong);

    }

    public Float readWingspan() {
        consoleManager.getOutputManager().write("Введите wingspan, Float");
        return readArg(dragonValidator::wingspanValidator, Float::parseFloat);
    }

    public Color readColor() {
        consoleManager.getOutputManager().write("Введите цвет из предложенных вариантов: ");
        Color[] colors = Color.values();
        for (Color color : colors) {
            consoleManager.getOutputManager().write(color.toString());
        }
        return readArg(dragonValidator::colorValidator, Color::valueOf);
    }
    public Date readDate() {
        return readArg(dragonValidator::dateValidator, StringConverterRealisation::parseDate);
    }
    public int readId() {
        return readArg(dragonValidator::idValidator, Integer::parseInt);
    }
}
