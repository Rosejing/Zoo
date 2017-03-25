package com.example.rose.zoo;

import android.provider.BaseColumns;

/**
 * Created by Rose on 01/09/2017.
 */

public class TableData {
    //default constructor
    public TableData() {

    }
    public static abstract class TableInfo implements BaseColumns {
        public static final String USER_NAME = "user_name";
        public static final String USER_PASS = "user_pass";
        public static final String DATABASE_NAME = "user_info";
        public static final String TABLE_NAME ="reg_info";
    }
}
