package org.anil;

import java.util.HashMap;
import java.util.Map;

public class ToolsData {
    public static Map<String, Tool> getTools() {
        Map<String, Tool> tools = new HashMap<>();
        tools.put("CHNS", new Tool("CHNS", "Chainsaw", "Stihl", 1.49, true, false, true));
        tools.put("LADW", new Tool("LADW", "Ladder", "Werner", 1.99, true, true, false));
        tools.put("JAKD", new Tool("JAKD", "Jackhammer", "DeWalt", 2.99, true, false, false));
        tools.put("JAKR", new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, false, false));
        return tools;
    }
}
