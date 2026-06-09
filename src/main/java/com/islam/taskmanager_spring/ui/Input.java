package com.islam.taskmanager_spring.ui;

public interface Input {
    String readNonEmptyLine();
    int readInt();
    void closeInput();
}
