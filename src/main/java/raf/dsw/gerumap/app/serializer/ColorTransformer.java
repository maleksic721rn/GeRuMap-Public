package raf.dsw.gerumap.app.serializer;

import flexjson.transformer.AbstractTransformer;

import java.awt.*;

public class ColorTransformer extends AbstractTransformer {
    @Override
    public void transform(Object o) {
        getContext().transform(((Color) o).getRGB());
    }
}
