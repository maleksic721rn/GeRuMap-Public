package raf.dsw.gerumap.app.serializer;

import flexjson.JsonNumber;
import flexjson.ObjectBinder;
import flexjson.ObjectFactory;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class DimensionFactory implements ObjectFactory {
    @Override
    public Object instantiate(ObjectBinder objectBinder, Object o, Type type, Class aClass) {
        ArrayList<JsonNumber> a = (ArrayList<JsonNumber>) o;
        return new Dimension(a.get(0).toInteger(), a.get(1).toInteger());
    }
}