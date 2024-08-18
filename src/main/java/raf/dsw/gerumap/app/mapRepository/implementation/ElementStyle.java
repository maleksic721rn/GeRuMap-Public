package raf.dsw.gerumap.app.mapRepository.implementation;

import flexjson.JSON;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import raf.dsw.gerumap.app.serializer.ColorFactory;
import raf.dsw.gerumap.app.serializer.ColorTransformer;

import java.awt.*;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
public class ElementStyle implements Serializable {
    public static final Color HIGHLIGHT_COLOR = Color.orange;
    public static final Color PREVIEW_COLOR = Color.magenta;
    public static final float PREVIEW_WIDTH = 1.445f;

    private ElementStyle() {}

    @JSON(transformer = ColorTransformer.class, objectFactory = ColorFactory.class)
    @NonNull private Color fillColor;
    @JSON(transformer = ColorTransformer.class, objectFactory = ColorFactory.class)
    @NonNull private Color strokeColor;
    @JSON(transformer = ColorTransformer.class, objectFactory = ColorFactory.class)
    @NonNull private Color textColor;
    @NonNull private float strokeWidth;

    public static ElementStyle getDefaultStyle() {
        return new ElementStyle(new Color(0xFF5F9EA0, true),
                                Color.black,
                                Color.black,
                                1);
    }
}
