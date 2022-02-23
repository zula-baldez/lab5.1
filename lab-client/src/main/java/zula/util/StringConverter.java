package zula.util;

@FunctionalInterface
public interface StringConverter<T> {
    T convert(String argument);
}
