package com.pravisolutions.binterthreadcommunication.csyncronizedblock.asyncronizedblock;

public class LearnSyncronizedBlockExe {

    public static void main(String[] args) {

        // Demo of synchronized block, still using intrinsic lock .. hence not having gain.
        ThreadWorkOne twone = new ThreadWorkOne();
        twone.execute();

        ThreadWorkTwo ttwo = new ThreadWorkTwo();
        ttwo.execute();
    }
}
