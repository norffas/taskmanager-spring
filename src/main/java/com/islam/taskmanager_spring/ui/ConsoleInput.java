package com.islam.taskmanager_spring.ui;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput implements Input {
    private final Scanner scanner;

    public ConsoleInput() {
        this.scanner = new Scanner(System.in);
    }

    public String readNonEmptyLine(){
        String line = scanner.nextLine();
        if(line == null || line.trim().isEmpty())
            throw new InputException("Строка не может быть пустой");
        else
            return line.trim();
    }

    public int readInt(){
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                throw new InputException("Введите корректное число");
            }
    }

    public void closeInput(){
        scanner.close();
    }
}
