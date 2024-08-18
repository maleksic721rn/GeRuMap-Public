package raf.dsw.gerumap.app.serializer;

import flexjson.ObjectBinder;
import flexjson.ObjectFactory;

import java.awt.*;
import java.lang.reflect.Type;

public class ColorFactory implements ObjectFactory {
    @Override
    public Object instantiate(ObjectBinder objectBinder, Object o, Type type, Class aClass) {
        return new Color(((Number) o).intValue(), true);
    }
}
