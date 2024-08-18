package raf.dsw.gerumap.app.messageGenerator;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class Message {
    @NonNull
    private MessageSeverity severity;
    @NonNull
    private MessageType type;
    @NonNull
    private String text;
    private Date timestamp = Date.from(Instant.now());
}
