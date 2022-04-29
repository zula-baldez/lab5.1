package zula.common.data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Class that contains information about objects we work with
 */
public class Dragon implements Comparable<Dragon>, Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long age; //Значение поля должно быть больше 0
    private float wingspan; //Значение поля должно быть больше 0
    private Color color; //Поле может быть null
    private DragonType type; //Поле не может быть null
    private DragonCave cave; //Поле не может быть null
    private int ownerId;
    public Dragon(String name, Coordinates coordinates,
                  long age, float wingspan, Color color, DragonType type, DragonCave cave) {
        this.name = name;
        this.coordinates = coordinates;
        this.age = age;
        this.wingspan = wingspan;
        this.color = color;
        this.type = type;
        this.cave = cave;
    }
    public void addAttributes(Date date, int idArg, int ownerId1) {
        this.id = idArg;
        this.creationDate = date;
        ownerId = ownerId1;
    }
    public void addAttributes(Date date, int ownerId1) {
        this.creationDate = date;
        ownerId = ownerId1;
    }
    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public long getAge() {
        return age;
    }

    public float getWingspan() {
        return wingspan;
    }

    public Color getColor() {
        return color;
    }

    public DragonType getType() {
        return type;
    }

    public DragonCave getCave() {
        return cave;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Dragon{" + "id=" + id + ", name='"
                + name + '\'' + ", coordinates=" + coordinates.getX()
                + " " + coordinates.getY() + ", creationDate=" + creationDate
                + ", age=" + age + ", wingspan=" + wingspan + ", color=" + color
                + ", type=" + type + ", cave=" + cave.getDepth() + " "
                + cave.getNumberOfTreasures() + ", userId = " + ownerId + '}';
    }

    @Override
    public int compareTo(Dragon o) {
        if (this.getId() <= o.getId()) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dragon dragon = (Dragon) o;
        return age == dragon.age && Float.compare(dragon.wingspan, wingspan) == 0 && Objects.equals(name, dragon.name) && Objects.equals(coordinates, dragon.coordinates) && color == dragon.color && type == dragon.type && Objects.equals(cave, dragon.cave);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, age, wingspan, color, type, cave);
    }
}



