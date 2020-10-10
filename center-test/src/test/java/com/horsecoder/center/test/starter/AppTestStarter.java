package com.horsecoder.center.test.starter;

import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import

import java.util.Arrays;
import java.util.HashSet;
import com.horsecoder.center.AppStarter;


public class AppTestStarter {

    private static final Logger logger = LoggerFactory.getLogger(AppTestStarter.class);

    public static void main(String[] args) {
        HashSet<String> argSet = new HashSet<>(Arrays.asList(args));
        argSet.add("--spring.profiles.active=dev");
        AppStarter.main(argSet.toArray(new String[0]));
    }

}
