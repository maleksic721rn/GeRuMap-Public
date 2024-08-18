package raf.dsw.gerumap.app.serializer;

import flexjson.transformer.AbstractTransformer;

import java.awt.*;

public class InsetsTransformer extends AbstractTransformer {
    @Override
    public void transform(Object o) {
        Insets i = (Insets) o;
        int[] a = new int[4];
        a[0] = i.top;
        a[1] = i.right;
        a[2] = i.bottom;
        a[3] = i.left;
        getContext().transform(a);
    }
}
