package zula.commands;

import zula.dragon.Color;
import zula.dragon.Coordinates;
import zula.dragon.DragonCave;
import zula.dragon.Dragon;
import zula.dragon.DragonType;
import zula.util.ArgumentReader;
import zula.util.ConsoleManager;


import java.util.Date;

/**
 * Realisation of add command
 */
public class Add extends Command {
    @Override
    public void doInstructions(ConsoleManager consoleManager, String argument) {
        ArgumentReader argumentReader = new ArgumentReader(consoleManager);

        String name = argumentReader.readName();
        Coordinates coordinates = argumentReader.readCoordinates();
        java.util.Date creationDate = new Date();
        long age = argumentReader.readAge();
        float wingspan = argumentReader.readWingspan();
        Color color = argumentReader.readColor();
        DragonType type = argumentReader.readType();
        DragonCave cave = argumentReader.readCave();
        Dragon dragon = new Dragon(name, coordinates, age, wingspan, color, type, cave);
        consoleManager.getListManager().addDragon(dragon);
        dragon.addAttributes(creationDate, consoleManager.getListManager().generateID());
    }







}
