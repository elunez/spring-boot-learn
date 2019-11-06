package com.ydyno;

import com.ydyno.task.AsyncTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppTests {

    @Autowired
    private AsyncTask asyncTask;

    @Test
    void test(){
        for (int i = 0; i < 100; i++) {
            asyncTask.run(i);
        }
    }

    @Test
    void test1(){
        for (int i = 0; i < 100; i++) {
            asyncTask.run1(i);
        }
    }
}
