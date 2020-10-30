package com.ghf.email.model;

public class EmailData {
    private int sizeOfHalfAnHour = 0;
    private int sizeOfWithinADay = 0;
    private boolean stop = false;

    public Integer getSizeOfHalfAnHour() {
        return sizeOfHalfAnHour;
    }

    public void setSizeOfHalfAnHour(Integer sizeOfHalfAnHour) {
        this.sizeOfHalfAnHour = sizeOfHalfAnHour;
    }

    public Integer getSizeOfWithinADay() {
        return sizeOfWithinADay;
    }

    public void setSizeOfWithinADay(Integer sizeOfWithinADay) {
        this.sizeOfWithinADay = sizeOfWithinADay;
    }

    public Boolean getStop() {
        return stop;
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }
}
