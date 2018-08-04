package com.lang.token.util;

import org.junit.Test;

public class DefaultTokenUtilsTest {

    @Test
    public void push() throws Exception{
//        Date nowTime = new Date();
//        Date overTime = DateTimeUtils.getDateInstance().getSomeDay(nowTime,1);
//        long expires = 7200;
//        System.out.println((overTime.getTime()+7200*1000-nowTime.getTime())/3600/1000);
//
//        TokenInfo tokenInfo = new TokenInfo();
//        String key = MD5Utils.getEncrypt().toMd5String("你的等我及 ");
//        tokenInfo.setUserName(MD5Utils.getEncrypt().toMd5String("a"));
//        tokenInfo.setValidTime(1530169129395L);
//        tokenInfo.setExpires(7200);
//        tokenInfo.setSalt(SnowFlake.getInstance().nextId());
//         System.out.println(tokenInfo.toString());
//        byte[] bytes = KryoUtil.writeObjectToByteArray(tokenInfo);
//          System.out.println(Base64Utils.getEncoder().encodeToString(AesUtils.encrypt(bytes,key)));
//        bytes =  Base64Utils.getDecoder().decode(Base64Utils.getEncoder().encodeToString(AesUtils.encrypt(bytes,key)));
//        bytes = AesUtils.decrypt(bytes,key);
//        System.out.println(KryoUtil.readObjectFromByteArray(bytes,TokenInfo.class).toString());
//        System.out.println(MD5Utils.getEncrypt().toMd5String(new Base64().encodeToString("ab534534c".getBytes())));
//        System.out.println(MD5Utils.getEncrypt().toMd5String(new Base64().encodeToString("ab534534c".getBytes())));
//        System.out.println(Base64Utils.getEncoder().encodeToString("ab534534c".getBytes()));
//        System.out.println(new Base64().encodeToString("ab534534c".getBytes()));
//        int MAXIMUM_CAPACITY = 1 << 30;
//        System.out.println(1<<4);
//        int number = 0;
//        number =  number >= MAXIMUM_CAPACITY ? MAXIMUM_CAPACITY: (number > 1) ? Integer.highestOneBit((number - 1) << 1) : 1;
//        System.out.println(number);
//        System.out.println(2<<128);System.out.println(Math.pow(2,128));
//        Token token = Token.getInstance((byte) 16);
//        Token token1 = Token.getInstance();
//        Token token2 = Token.getInstance((byte)32);
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