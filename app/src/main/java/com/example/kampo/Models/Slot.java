package com.example.kampo.Models;

public class Slot  {
    String Slot1,Slot2,Slot3;

    public Slot() {
    }

    public Slot(String slot1, String slot2, String slot3) {
        Slot1 = slot1;
        Slot2 = slot2;
        Slot3 = slot3;
    }

    public String getSlot1() {
        return Slot1;
    }

    public void setSlot1(String slot1) {
        Slot1 = slot1;
    }

    public String getSlot2() {
        return Slot2;
    }

    public void setSlot2(String slot2) {
        Slot2 = slot2;
    }

    public String getSlot3() {
        return Slot3;
    }

    public void setSlot3(String slot3) {
        Slot3 = slot3;
    }
}
