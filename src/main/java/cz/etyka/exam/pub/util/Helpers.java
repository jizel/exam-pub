package cz.etyka.exam.pub.util;

import java.util.ArrayList;
import java.util.List;

public class Helpers {

    public static <T> List<T> iterableToList(Iterable<T> iterable){
        List<T> result = new ArrayList<>();
        iterable.forEach(result::add);
        return result;
    }
}
