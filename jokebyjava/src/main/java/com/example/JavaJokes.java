package com.example;

public class JavaJokes {

    public String getJoke() {
        double i = Math.random();
        int j = (int) (i / 5);
        switch (j) {
            case 0:
                return "0";
            case 1:
                return "1";
            case 2:
                return "2";
            case 3:
                return "3";
            case 4:
                return "4";
        }
        return "default";
    }
}
