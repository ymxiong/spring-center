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

    private static final int DB_PORT = #{dbPort}#;

    private static final String DB_SCHEMA = "#{dbSchema}#";

    private static final String STORAGE_LOCATION = "/tmp/maria/test";

    private static final String ARTIFACT_ID = "center";

    public static void main(String[] args) {
        startDatabase();
        HashSet<String> argSet = new HashSet<>(Arrays.asList(args));
        argSet.add("--spring.profiles.active=dev");
        AppStarter.main(argSet.toArray(new String[0]));
    }

    private static void startDatabase() {
        try {

            // DB Config
            DBConfigurationBuilder configBuilder = DBConfigurationBuilder.newBuilder();
            configBuilder.setPort(DB_PORT);
            configBuilder.setDataDir(STORAGE_LOCATION);

            // DB Start
            DB db = DB.newEmbeddedDB(configBuilder.build());
            db.start();
            new Thread(()-> {
                try {
                    db.createDB(DB_SCHEMA);
                } catch (Exception ignored) {
                    logger.info("Schema already exists");
                }
            }).start();
            new Thread(()-> {
                try {
                    db.source(ARTIFACT_ID + ".sql", DB_SCHEMA);
                    db.source(ARTIFACT_ID + "_test.sql", DB_SCHEMA);
                } catch (Exception ignored) {
                    logger.info("Sql not exists");
                }
            }).start();

        }catch (Exception e){
            logger.error("DB start error");
        }
    }

}
