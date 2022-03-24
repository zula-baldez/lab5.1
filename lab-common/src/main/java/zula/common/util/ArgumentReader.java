package zula.common.util;

import zula.common.data.*;
import zula.common.exceptions.PrintException;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.function.Predicate;

public class ArgumentReader {
    private final IoManager ioManager;
    private final DragonValidator dragonValidator;
    public ArgumentReader(IoManager ioManager1) {

        this.ioManager = ioManager1;
        this.dragonValidator = new DragonValidator();
    }
    private <T> T readArg(Predicate<T> predicate, StringConverter<T> stringConverter) throws IOException, PrintException {
        while (true) {
            T t;
            String readedLine = ioManager.getInputManager().read(ioManager);
            if ("".equals(readedLine)) {
                t = null;
            } else {
                try {
                    t = stringConverter.convert(readedLine);
                } catch (IllegalArgumentException e) {
                    ioManager.getOutputManager().write("Неверные входные данные");

                    continue;
                }
            }
            if (predicate.test(t)) {
                return t;
            } else {
                ioManager.getOutputManager().write("Неверные входные данные");
            }
        }
    }


    public String readName() throws IOException, PrintException {
        ioManager.getOutputManager().write("Введите имя:");
        return readArg(dragonValidator::nameValidator, (String s) -> s);
    }

    public Coordinates readCoordinates() throws IOException, PrintException {
        ioManager.getOutputManager().write("Введите x. Double, >= -23");
        Double x = readArg(dragonValidator::xValidator, Double::parseDouble);
        ioManager.getOutputManager().write("Введите y. Integer, <= 160");
        Integer y = readArg(dragonValidator::yValidator, Integer::parseInt);
        return new Coordinates(x, y);
    }

    public DragonCave readCave() throws IOException, PrintException {
        ioManager.getOutputManager().write("Введите depth. Float, может быть null. Для ввода null введите пустую строку");
        Float depth = readArg(dragonValidator::depthValidator, Float::parseFloat);
        ioManager.getOutputManager().write("Введите numberOfTreasure. Double, может быть null. Для ввода null введите пустую строку");
        Double numberOfTreasure = readArg(dragonValidator::numberOfTreasuresValidator, Double::parseDouble);
        return new DragonCave(depth, numberOfTreasure);
    }
    public DragonType readType() throws PrintException, IOException {

        ioManager.getOutputManager().write("Введите тип дракона из предложенных вариантов: ");
        DragonType[] types = DragonType.values();
        for (DragonType dragon : types) {
            ioManager.getOutputManager().write(dragon.toString());
        }
        return readArg(dragonValidator::typeValidator, DragonType::valueOf);


    }

    public Long readAge() throws IOException, PrintException {

        ioManager.getOutputManager().write("Введите возраст, Long:");
        return readArg(dragonValidator::ageValidator, Long::parseLong);

    }

    public Float readWingspan() throws IOException, PrintException {
        ioManager.getOutputManager().write("Введите wingspan, Float");
        return readArg(dragonValidator::wingspanValidator, Float::parseFloat);
    }

    public Color readColor() throws IOException, PrintException {
        ioManager.getOutputManager().write("Введите цвет из предложенных вариантов. может быть null. Для ввода null введите пустую строку");
        Color[] colors = Color.values();
        for (Color color : colors) {
            ioManager.getOutputManager().write(color.toString());
        }
        return readArg(dragonValidator::colorValidator, Color::valueOf);
    }

}
