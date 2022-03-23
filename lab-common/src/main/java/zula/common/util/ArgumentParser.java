package zula.common.util;


import zula.common.exceptions.WrongArgumentException;

import java.util.function.Predicate;


public class ArgumentParser {
    public <T> T parseArgFromString(String readLine, Predicate<T> predicate, StringConverter<T> stringConverter) throws WrongArgumentException {
            T t;
            if ("".equals(readLine)) {
                t = null;
            } else {
                try {
                    t = stringConverter.convert(readLine);
                } catch (IllegalArgumentException e) {
                    throw new WrongArgumentException();
                }
            }
            if (predicate.test(t)) {
                return t;
            } else {
                throw new WrongArgumentException();
            }

    }
    public boolean checkIfTheArgsEmpty(String readLine) {
        return "".equals(readLine);
    }





}
