package com.lang.token.core;

import com.lang.token.model.TokenInfo;
import com.lang.token.model.TokenUser;
import com.lang.token.model.TokenUserInfo;
import com.lang.token.sql.dao.UserDao;
import com.lang.token.util.PropertiesUtils;
import com.lang.token.util.SnowFlake;
import com.lang.token.util.Token;
import com.lang.token.util.aes.AesUtils;
import com.lang.token.util.md5.MD5Utils;

import java.util.Date;

/**
 * @author liu_yeye
 * @date 2018-06-28 22:10
 */
public class DeFaultTokenOperation implements TokenOperation {
    //7200s 默认有效时间
    private static final long DEFAULT_EXPIRES=7200;
    private static final String AES_KEY = MD5Utils.getEncrypt().toMd5String(PropertiesUtils.getValue("aes_key"));
    @Override
    public TokenInfo validateUser(String userName, String passWord, String signature) {
        UserDao userDao = new UserDao();
        //signature md5
        String md5Signature = MD5Utils.getEncrypt().toMd5String(signature);
        //时间戳
        Date nowTime = new Date();
        //token信息盐值
        long salt = SnowFlake.getInstance().nextId();
        //验证token授权者
        TokenUser tokenUser = userDao.validUser(userName,passWord,md5Signature);
        if(tokenUser==null){
            throw new RuntimeException("token授权信息验证失败");
        }
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setUserName(tokenUser.getUserName());
        tokenInfo.setValidTime(nowTime.getTime());
        tokenInfo.setSalt(salt);
        String propertiesExpires = PropertiesUtils.getValue("expires");
        long expires;
        if(propertiesExpires ==null || propertiesExpires.equals("")){
            expires = DEFAULT_EXPIRES;
        }else {
            expires = Long.valueOf(propertiesExpires);
        }
        tokenInfo.setExpires(expires);
        return tokenInfo;
    }

    @Override
    public String encryptToken(TokenInfo tokenInfo) {
        try{
            //object to byte[]
            Encryption encryption = new AbstractEncryption();
            byte[] tokenBytes = encryption.objectToByteArray(tokenInfo);
            //aes 加密
            byte[] aesBytes = encryption.aesEncryptBytes(tokenBytes,AES_KEY);
            //base64 编码
            return encryption.base64EncoderStr(aesBytes);
        }catch (Exception e){
            throw  new RuntimeException("token加密失败",e);
        }
    }

    @Override
    public TokenInfo decryptToken(String token) {
        try{
            Encryption encryption = new AbstractEncryption();
            //base64 解码
            byte[] base64Bytes = encryption.base64DecoderStr(token);
            //aes 解密
            byte[] aesBytes = encryption.aesDecryptBytes(base64Bytes,AES_KEY);
            //转化token对象
            return (TokenInfo) encryption.byteArrayToObject(aesBytes,TokenInfo.class);
        }catch (Exception e){
            throw  new RuntimeException("token无效",e);
        }
    }
    @Override
    public TokenUser encryptUser(TokenUserInfo tokenUserInfo,String signature) {
        try{
            Encryption encryption = new AbstractEncryption();
            //object to byte[]
            byte[] userInfoBytes = encryption.objectToByteArray(tokenUserInfo);
            //aes 加密
            byte[] aesBytes = encryption.aesDecryptBytes(userInfoBytes,AES_KEY);
            //base64 编码 得到授权用户名
            String base64UserName = encryption.base64EncoderStr(aesBytes);
            long salt = SnowFlake.getInstance().nextId();
            tokenUserInfo.setSalt(salt);
            //aes 加密
            byte[] pwdAesBytes = encryption.aesDecryptBytes(userInfoBytes,AES_KEY);
            //md5 密码
            String md5Pwd = encryption.md5Str(pwdAesBytes);
            String md5Signature = encryption.md5Str(signature);
            TokenUser tokenUser = new TokenUser();
            tokenUser.setUserName(base64UserName);
            tokenUser.setPassWord(md5Pwd);
            tokenUser.setSignature(md5Signature);
            return tokenUser;
        }catch (Exception e){
            throw  new RuntimeException("token无效",e);
        }
    }
    @Override
    public void validateToken(TokenInfo tokenInfo) {
        long nowTimestamp =System.currentTimeMillis();
        long tokenTimestamp = tokenInfo.getValidTime();
        long expires = tokenInfo.getExpires();
        if ((tokenTimestamp+expires*1000)<=nowTimestamp){
            throw new RuntimeException("token已失效");
        }
    }
}
