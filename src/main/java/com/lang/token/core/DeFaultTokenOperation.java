package com.lang.token.core;

import com.lang.token.model.TokenInfo;
import com.lang.token.model.TokenUser;
import com.lang.token.model.TokenUserInfo;
import com.lang.token.sql.dao.UserDao;
import com.lang.token.util.PropertiesUtils;
import com.lang.token.util.SnowFlake;
import com.lang.token.util.md5.MD5Utils;

import java.util.Date;

/**
 * @author liu_yeye
 * @date 2018-06-28 22:10
 */
public class DeFaultTokenOperation implements TokenOperation {
    private Encryption encryption = new AbstractEncryption();
    //7200s 默认有效时间
    private static final long DEFAULT_EXPIRES=7200;
    private static final String AES_KEY = MD5Utils.getEncrypt().toMd5String(PropertiesUtils.getValue("aes_key"));

    @Override
    public TokenInfo validateUser(String userName, String passWord, String signature) throws TokenException{
        UserDao userDao = new UserDao();
        //signature md5
        String md5Signature = encryption.md5Str(signature);
        //时间戳
        Date nowTime = new Date();
        //token信息盐值
        long salt = SnowFlake.getInstance().nextId();
        //验证token授权者
        TokenUser tokenUser = userDao.validUser(userName,passWord,md5Signature);
        if(tokenUser==null){
            throw new TokenException("token授权信息验证失败");
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
    public String encryptToken(TokenInfo tokenInfo) throws TokenException{
        try{
            //object to byte[]
            byte[] tokenBytes = encryption.objectToByteArray(tokenInfo);
            //aes 加密
            byte[] aesBytes = encryption.aesEncryptBytes(tokenBytes,AES_KEY);
            //base64 编码
            return encryption.base64EncoderStr(aesBytes);
        }catch (Exception e){
            throw  new TokenException("token加密失败",e);
        }
    }

    @Override
    public TokenInfo decryptToken(String token) throws TokenException{
        try{
            //base64 解码
            byte[] base64Bytes = encryption.base64DecoderStr(token);
            //aes 解密
            byte[] aesBytes = encryption.aesDecryptBytes(base64Bytes,AES_KEY);
            //转化token对象
            return (TokenInfo) encryption.byteArrayToObject(aesBytes,TokenInfo.class);
        }catch (Exception e){
            throw  new TokenException("无效的token",e);
        }
    }
    @Override
    public TokenUser encryptUser(TokenUserInfo tokenUserInfo,String signature) throws TokenException{
        try{
            long salt = SnowFlake.getInstance().nextId();
            tokenUserInfo.setSalt(salt);
            //object to byte[]
            byte[] userInfoBytes = encryption.objectToByteArray(tokenUserInfo);
            //aes 加密
            byte[] aesBytes = encryption.aesEncryptBytes(userInfoBytes,AES_KEY);
            //base64 编码 得到授权用户名
            String base64UserName = encryption.base64EncoderStr(aesBytes);

            //aes 加密
            byte[] pwdAesBytes = encryption.aesEncryptBytes((base64UserName+salt).getBytes("UTF-8"),AES_KEY);
            //md5 密码
            String md5Pwd = encryption.md5Str(pwdAesBytes);
            String md5Signature = encryption.md5Str(signature);
            TokenUser tokenUser = new TokenUser();
            tokenUser.setUserName(encryption.md5Str(base64UserName));
            tokenUser.setPassWord(md5Pwd);
            tokenUser.setSignature(md5Signature);
            return tokenUser;
        }catch (Exception e){
            throw  new TokenException("加密token用户失败",e);
        }
    }
    @Override
    public void validateToken(TokenInfo tokenInfo) throws TokenException{
        long nowTimestamp =System.currentTimeMillis();
        long tokenTimestamp = tokenInfo.getValidTime();
        long expires = tokenInfo.getExpires();
        if ((tokenTimestamp+expires*1000)<=nowTimestamp){
            throw new TokenException("token已过期");
        }
    }
}
