package zula.parser;

@FunctionalInterface
public interface StringConverter<T> {
    T convert(String argument);
}
