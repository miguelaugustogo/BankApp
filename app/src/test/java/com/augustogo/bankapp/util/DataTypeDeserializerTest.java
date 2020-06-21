package com.augustogo.bankapp.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DataTypeDeserializerTest {

    @Test
    public void formatDate(){
        String date = DateTypeDeserializer.formatDate("2020-06-21");
        assertThat(date, is(equalTo("21/06/2020")));
    }
}
