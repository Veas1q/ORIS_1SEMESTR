package org.example;
import java.util.Map;
import java.util.TreeMap;

public class Application {
    public static final Map<String, IResourceService> resourceMap = new TreeMap<>();

    public void init() {
        resourceMap.put("/home", new HomeService());
    }
}