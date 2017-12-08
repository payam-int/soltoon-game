package io.gsonfire;

import com.google.gson.JsonElement;

/**
 * @autor: julio
 */
public interface TypeSelector<T> {

    Class<? extends T> getClassForElement(JsonElement readElement);

    default Class<? extends T> getClassForElement(JsonElement readElement, Class<? extends T> defaultClass) {
        if (readElement.isJsonNull())
            return null;
        
        Class<? extends T> classForElement = getClassForElement(readElement);

        if (classForElement == null)
            return defaultClass;

        return classForElement;
    }

}
