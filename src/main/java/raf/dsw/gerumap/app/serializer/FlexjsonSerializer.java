package raf.dsw.gerumap.app.serializer;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.core.Serializer;
import raf.dsw.gerumap.app.messageGenerator.Message;
import raf.dsw.gerumap.app.messageGenerator.MessageSeverity;
import raf.dsw.gerumap.app.messageGenerator.MessageType;

import java.awt.*;
import java.io.*;

public class FlexjsonSerializer implements Serializer {
    @Override
    public <T> T deserialize(File file, Class<T> object_class) {
        try {
            return new JSONDeserializer<T>()
                    .use(Point.class, new PointFactory())
                    .use(Dimension.class, new DimensionFactory())
                    .use(Insets.class, new InsetsFactory())
                    .use(Color.class, new ColorFactory())
                    .deserialize(new FileReader(file));
        } catch (FileNotFoundException e) {
            AppCore.getInstance().getMessageGenerator().sendMessage(
                    new Message(MessageSeverity.ERROR,
                            MessageType.FILE_NOT_FOUND,
                            "File " + file + " not found.")
            );
        }
        return null;
    }

    @Override
    public void serialize(File file, Object object) {
        try {
            FileWriter fs = new FileWriter(file);
            new JSONSerializer().serialize(object, fs);
            fs.close();
        } catch (IOException e) {
            AppCore.getInstance().getMessageGenerator().sendMessage(
                    new Message(MessageSeverity.ERROR,
                            MessageType.FAILED_TO_WRITE_FILE,
                            "Failed to write file " + file)
            );
        }
    }
}
