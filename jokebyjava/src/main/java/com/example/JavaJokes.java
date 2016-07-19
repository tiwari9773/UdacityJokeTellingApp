package com.example;

import java.util.Random;

public class JavaJokes {

    public String getJoke() {
        Random r = new Random();
        int joke = r.nextInt(5);
        switch (joke) {
            case 0:
                return "Joke 0";
            case 1:
                return "Joke 1";
            case 2:
                return "Joke 2";
            case 3:
                return "Joke 3";
            case 4:
                return "Joke 4";
        }
        return "Joke default";
    }
}
