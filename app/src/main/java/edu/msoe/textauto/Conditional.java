package edu.msoe.textauto;

import androidx.room.Entity;

@Entity
public class Conditional {

    public Conditional(Conditions condition) {
        this.condition = condition;
    }

    public enum Conditions{
        TIME
    }

    private final Conditions condition;


}
