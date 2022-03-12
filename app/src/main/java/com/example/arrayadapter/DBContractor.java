package com.example.arrayadapter;

import android.provider.BaseColumns;

public final class DBContractor {

    private DBContractor(){}

    public static class Entry implements BaseColumns{
        public static final String TABLE_NAME = "orders";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PCSET = "pcset";
        public static final String COLUMN_PCIMAGE = "pcimage";
        public static final String COLUMN_PCAMOUNT = "pcamount";
        public static final String COLUMN_MOUSE = "mouse";
        public static final String COLUMN_MOUSEIMAGE = "mouseimage";
        public static final String COLUMN_KEYBOARD = "keyboard";
        public static final String COLUMN_KEYBOARDIMAGE = "keyboardimage";
        public static final String COLUMN_MONITOR = "monitor";
        public static final String COLUMN_MONITORIMAGE = "monitorimage";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_ORDERDATE = "orderdate";
    }
    public static class User implements BaseColumns{
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_LOGIN = "login";
        public static final String COLUMN_PASSWORD = "password";
    }
}
