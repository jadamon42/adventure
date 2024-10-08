package com.github.jadamon42.adventure.runner.console;

import com.github.jadamon42.adventure.common.node.LinkedTextChoice;

import java.util.List;
import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {
    private final Scanner scanner;

    public ConsoleInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String getFreeTextInput() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    @Override
    public int getMultipleChoiceInput(List<LinkedTextChoice> choices) {
        for (int i = 0; i < choices.size(); i++) {
            System.out.println((i + 1) + ". " + choices.get(i).getText());
        }
        System.out.print("> ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice < 1 || choice > choices.size()) {
                throw new NumberFormatException();
            }
            return choice - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice. Please enter a number between 1 and " + choices.size());
            return getMultipleChoiceInput(choices);
        }
    }
}

