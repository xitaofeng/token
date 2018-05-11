package com.lang.token.util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class DefaultTokenUtilsTest {

    @Test
    public void push() {
        Token token = Token.getInstance((byte) 16);
        Token token1 = Token.getInstance();
        Token token2 = Token.getInstance((byte)32);
        token.getTokenArray()[0]="a";
        token1.getTokenArray()[2]="33";
        token2.getTokenArray()[3]="b";
        DefaultTokenUtils tokenUtils = new DefaultTokenUtils(token);
        DefaultTokenUtils tokenUtils1 = new DefaultTokenUtils(token1);
        DefaultTokenUtils tokenUtils2 = new DefaultTokenUtils(token2);
        System.out.println(Arrays.toString(tokenUtils.getToken().getTokenArray()));
        System.out.println(Arrays.toString(tokenUtils1.getToken().getTokenArray()));
        System.out.println(Arrays.toString(tokenUtils2.getToken().getTokenArray()));
    }

    @Test
    public void contains() {
    }

    @Test
    public void pop() {
    }

    @Test
    public void size() {
    }
}