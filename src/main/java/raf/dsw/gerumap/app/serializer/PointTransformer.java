package raf.dsw.gerumap.app.serializer;

import flexjson.transformer.AbstractTransformer;

import java.awt.*;

public class PointTransformer extends AbstractTransformer {
    @Override
    public void transform(Object o) {
        Point p = (Point) o;
        int[] a = new int[2];
        a[0] = p.x;
        a[1] = p.y;
        getContext().transform(a);
    }
}
