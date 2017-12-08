package io.gsonfire.gson;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.gsonfire.ClassConfig;
import io.gsonfire.TypeSelector;

import java.io.IOException;
import java.util.Set;

/**
 * Creates a {@link TypeAdapter} that will run the {@link TypeSelector} and find the {@link TypeAdapter} for the selected
 * type.
 */
public class TypeSelectorTypeAdapterFactory<T> implements TypeAdapterFactory {

    private final ClassConfig<T> classConfig;
    private final Set<TypeToken> alreadyResolvedTypeTokensRegistry;

    public TypeSelectorTypeAdapterFactory(ClassConfig<T> classConfig, Set<TypeToken> alreadyResolvedTypeTokensRegistry) {
        this.classConfig = classConfig;
        this.alreadyResolvedTypeTokensRegistry = alreadyResolvedTypeTokensRegistry;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (alreadyResolvedTypeTokensRegistry.contains(type)) {
            return null;
        }


        if (classConfig.getConfiguredClass().isAssignableFrom(type.getRawType())) {
            TypeAdapter<T> delegateAdapter;
//            if (HooksTypeAdapterFactory.factory == null)
                delegateAdapter = (TypeAdapter<T>) gson.getDelegateAdapter(this, type);
//            else
//                delegateAdapter = (TypeAdapter<T>) gson.getDelegateAdapter(HooksTypeAdapterFactory.factory, type);


            TypeAdapter<T> fireTypeAdapter =
                    new NullableTypeAdapter<T>(
                            new TypeSelectorTypeAdapter<T>(type.getRawType(), classConfig.getTypeSelector(), gson, delegateAdapter)
                    );
            return fireTypeAdapter;
        } else {
            return null;
        }
    }

    private class TypeSelectorTypeAdapter<T> extends TypeAdapter<T> {

        private final Class superClass;
        private final TypeSelector typeSelector;
        private final Gson gson;
        private final TypeAdapter<T> delAdapter;

        private TypeSelectorTypeAdapter(Class superClass, TypeSelector typeSelector, Gson gson, TypeAdapter<T> delegateAdapter) {
            this.superClass = superClass;
            this.typeSelector = typeSelector;
            this.gson = gson;
            this.delAdapter = delegateAdapter;
        }

        @Override
        public void write(JsonWriter out, T value) throws IOException {
            TypeAdapter otherTypeAdapter = gson.getDelegateAdapter(TypeSelectorTypeAdapterFactory.this, TypeToken.get(value.getClass()));
            gson.toJson(otherTypeAdapter.toJsonTree(value), out);
        }

        @Override
        public T read(JsonReader in) throws IOException {
            JsonElement json = new JsonParser().parse(in);
            Class deserialize = this.typeSelector.getClassForElement(json);
            if (deserialize == null) {
                return (T) this.delAdapter.fromJsonTree(json);
            }


            TypeToken typeToken = TypeToken.get(deserialize);
            alreadyResolvedTypeTokensRegistry.add(typeToken);
            TypeAdapter otherTypeAdapter;
            try {
                if (deserialize != superClass) {
                    otherTypeAdapter = gson.getAdapter(typeToken);
                } else {
                    otherTypeAdapter = gson.getDelegateAdapter(TypeSelectorTypeAdapterFactory.this, typeToken);
                }
            } finally {
                alreadyResolvedTypeTokensRegistry.remove(typeToken);
            }
            return (T) otherTypeAdapter.fromJsonTree(json);
        }
    }

}
