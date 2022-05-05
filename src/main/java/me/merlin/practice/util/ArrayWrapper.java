package me.merlin.practice.util;

import org.apache.commons.lang.Validate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

public class ArrayWrapper<E> {

    public ArrayWrapper(E... elements) {
        setArray(elements);
    }

    private E[] _array;

    public E[] getArray() {
        return _array;
    }

    public void setArray(E[] array) {
        Validate.notNull(array, "Array cannot be null");
        _array = array;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ArrayWrapper)) {
            return false;
        }
        return Arrays.equals(_array, ((ArrayWrapper) obj)._array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(_array);
    }

    public static <T> T[] toArray(Iterable<? extends T> list, Class<T> c) {
        int size = -1;
        if(list instanceof Collection<?>) {
            Collection collection = (Collection) list;
            size = collection.size();
        }

        if(size < 0) {
            size = 0;
            for(T element : list) {
                size++;
            }
        }

        T[] result = (T[]) Array.newInstance(c, size);
        int index = 0;
        for(T element : list) {
            result[index++] = element;
        }
        return result;
    }
}
