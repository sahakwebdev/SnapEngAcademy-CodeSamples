// Gevorg Sahakyan
// Project 6

import java.util.Scanner;
import java.util.Random;
import zoo.*;

public class Project6 {
    // The Main Method
    public static void main(String[] args) {
        // Storage of input variable and random number generator
        Scanner input = new Scanner(System.in);
        Random generator = new Random();

        // Input
        System.out.print("Enter a length between 5 to 10: ");

        if (!input.hasNextInt()) {
            error("Non-integer input.");
        }
        
        int length = input.nextInt();
        input.nextLine();

        if (length < 5 || length > 10) {
            error("Invalid length.");
        }
        input.close();

        Animal[] animals = new Animal[length];

        // Calculation and Output Part 1
        System.out.println("*** Part 1 ***");
        for (int i = 0; i < animals.length; i++) {
            System.out.println("Animal at index " + i + ":");
            int number1 = generator.nextInt(4) + 1;
            int number2 = generator.nextInt(11) + 4;
            int number3 = generator.nextInt(21) + 15;
            int number4 = generator.nextInt(21) + 15;
            switch (number1) {
                case 1:
                    animals[i] = new Squirrel(number2, number3);
                    break;
                case 2:
                    animals[i] = new Pelican(number2, number3);
                    break;
                case 3:
                    animals[i] = new Crab(number2, number3);
                    break;
                case 4:
                    animals[i] = new Lobster(number2, number3, number4);
                    break;
                default:
                    error("Invalid animal choice: " + number1 + ".");
            }
            animals[i].showProfile();

            
        }
        
        // Calcualtion and Output Part 2
        System.out.println("*** Part 2 ***");
        for (int i = 0; i < animals.length; i++) {
            System.out.println("Animal at index " + i + ":");
            if (animals[i] instanceof Running) {
                Running runner = (Running)animals[i];
                runner.run();
            }
            if (animals[i] instanceof Flying) {
                Flying flyer = (Flying)animals[i];
                flyer.fly();
            }
            if (animals[i] instanceof Swimming) {
                Swimming swimmer = (Swimming)animals[i];
                swimmer.swim();
            }
        }

        // Calcualtion and Output Part 3
        System.out.println("*** Part 3 ***");
        for (int i =0; i < animals.length; i++) {
            System.out.println("Animal at index " + i + ":");
            if (animals[i] instanceof Squirrel) {
                Squirrel squirrel = (Squirrel)animals[i];
                squirrel.jump();
            } else if (animals[i] instanceof Pelican) {
                Pelican pelican = (Pelican)animals[i];
                pelican.dive();
            } else if (animals[i] instanceof Crab) {
                Crab crab = (Crab)animals[i];
                crab.pinch();
                if (crab instanceof Lobster) {
                    Lobster lobster = (Lobster)crab;
                    int number5 = generator.nextInt(10) + 1;
                    lobster.pinch(number5);
                    lobster.eat();
                }
            }
        }
    }

    // Ther Error Method
    public static void error(String message) {
        System.out.println("Error: " + message);
        System.exit(1);
    }
}
