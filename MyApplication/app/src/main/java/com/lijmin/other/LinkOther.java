package com.lijmin.other;

public class LinkOther {
    static{
        System.loadLibrary("other");
        System.loadLibrary("link-other");
    }
    public native int add(int a,int b);
}
