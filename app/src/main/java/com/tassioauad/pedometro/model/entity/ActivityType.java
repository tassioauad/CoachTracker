package com.tassioauad.pedometro.model.entity;

public enum ActivityType {

    IN_VEHICLE(0), ON_BICYCLE(1), ON_FOOT(2), STILL(3), UNKNOWN(4), TILTING(5), DEFAULT(6), WALKING(7), RUNNING(8);

    private int value;

    ActivityType(int value) {
        this.value = value;
    }

    public String getName() {
        switch(value) {
            case 0:
                return "In Vehicle";
            case 1:
                return "On Bicycle";
            case 2:
                return "On Foot";
            case 3:
                return "Still";
            case 4:
                return "Unknown";
            case 5:
                return "Tilting";
            case 6:
            default:
                return Integer.toString(value);
            case 7:
                return "Walking";
            case 8:
                return "Runing";
        }
    }
}
