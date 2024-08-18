package raf.dsw.gerumap.app.messageGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileLogger extends StringLogger {
    public FileLogger(MessageGeneratorImplementation messageGeneratorImplementation) {
        super(messageGeneratorImplementation);
    }

    @Override
    protected void output(String message) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(getClass().getResource("/log.txt").getFile(), true)) {
            OutputStreamWriter s = new OutputStreamWriter(fileOutputStream);
            s.write(message);
            s.write("\n");
            s.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
