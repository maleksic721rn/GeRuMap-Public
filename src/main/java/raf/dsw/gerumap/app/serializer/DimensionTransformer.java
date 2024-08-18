package raf.dsw.gerumap.app.serializer;

import flexjson.transformer.AbstractTransformer;

import java.awt.*;

public class DimensionTransformer extends AbstractTransformer {
    @Override
    public void transform(Object o) {
        Dimension d = (Dimension) o;
        int[] a = new int[2];
        a[0] = d.width;
        a[1] = d.height;
        getContext().transform(a);
    }
}
