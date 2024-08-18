package raf.dsw.gerumap.app.core;

import java.io.File;

public interface Serializer {
    <T> T deserialize(File file, Class<T> object_class);
    void serialize(File file, Object object);
}
