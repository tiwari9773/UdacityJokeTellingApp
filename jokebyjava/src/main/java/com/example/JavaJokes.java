package com.example;

import java.util.Random;

public class JavaJokes {

    public String getJoke() {
        Random r = new Random();
        int joke = r.nextInt(4);
        switch (joke) {
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
