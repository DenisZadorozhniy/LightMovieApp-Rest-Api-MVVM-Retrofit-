package com.example.movieapp;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MovieAppExecutors {
    private static MovieAppExecutors instance;

    public static MovieAppExecutors getInstance(){
        if(instance == null){
            instance =  new MovieAppExecutors();
        } return instance;
    }

    private final ScheduledExecutorService mNetwork = Executors.newScheduledThreadPool(3);
    public ScheduledExecutorService networkIO(){
        return mNetwork;
    }
}
