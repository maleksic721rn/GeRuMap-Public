package raf.dsw.gerumap.app.mapRepository.composite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MapNodeNotification {
    private Object property;
    private MapNodeNotificationType type;
    private MapNode<?> source;
}