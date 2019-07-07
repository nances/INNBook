package com.kaqi.niuniu.ireader.event;

/**
 * Created by niqiao on 2019年07月01日18:12:18.
 */

public class TaskShareEvent {

    public int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public TaskShareEvent(int type) {
        this.type = type;
    }
}
