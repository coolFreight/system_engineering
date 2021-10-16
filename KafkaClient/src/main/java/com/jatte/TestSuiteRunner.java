package com.jatte;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestSuiteRunner {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(SuiteRunner.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
            failure.getException().printStackTrace();
        }


        System.out.println("Test successful? " + result.wasSuccessful());
    }
}
