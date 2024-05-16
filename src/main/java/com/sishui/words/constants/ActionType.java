package com.sishui.words.constants;

public enum ActionType {
    LIKE("like", 2.0),
    COMMENT("comment", 4.0),
    VIEW("view", 1.0);

    private final String actionName;
    private final double weight;

    ActionType(String actionName, double weight) {
        this.actionName = actionName;
        this.weight = weight;
    }

    public String getActionName() {
        return actionName;
    }

    public double getWeight() {
        return weight;
    }
}
