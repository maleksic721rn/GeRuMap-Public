module gerumap.tim.markoaleksic.lazarkukolj {
    requires static lombok;
    requires java.desktop;
    requires java.base;
    requires flexjson;
    opens raf.dsw.gerumap.app.mapRepository.implementation;
    opens raf.dsw.gerumap.app.mapRepository.composite;
    opens raf.dsw.gerumap.app.serializer;
}