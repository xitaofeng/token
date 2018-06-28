package com.lang.token.sql.dao;

import com.lang.token.model.TokenUser;
import com.lang.token.sql.pool.DruidPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liu_yeye
 * @date 2018-06-27 23:18
 */
public class UserDao {
    private DruidPool druidPool = new DruidPool();
    public void addUser(String userName,String passWord,String signature) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = druidPool.druidDataSource();
            String sql = "insert into token_user values (?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,passWord);
            preparedStatement.setString(3,signature);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            release(connection,preparedStatement);
        }
    }
    public void delUser(String userName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = druidPool.druidDataSource();
            String sql = "delete from  token_user where username=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            release(connection,preparedStatement);
        }
    }
    public void updateUser(String userName,String signature) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = druidPool.druidDataSource();
            String sql = "update token_user set signature=? where username=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,signature);
            preparedStatement.setString(2,userName);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            release(connection,preparedStatement);
        }
    }
    public TokenUser validUser(String userName,String passWord,String signature){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        TokenUser tokenUser = null;
        try{
            connection = druidPool.druidDataSource();
            String sql = "select username from token_user where username=? and password=? and signature=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,passWord);
            preparedStatement.setString(3,signature);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                tokenUser = new TokenUser();
                tokenUser.setUserName(resultSet.getString("username"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            release(connection,preparedStatement,resultSet);
        }
        return tokenUser;
    }
    public void findUser() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<TokenUser> tokenUsers = null;
        try{
            connection = druidPool.druidDataSource();
            String sql = "select id,username,password,signature from token_user";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            tokenUsers = new ArrayList<>(8);
            while (resultSet.next()){
                TokenUser tokenUser = new TokenUser();
                tokenUser.setUserName(resultSet.getString("username"));
                tokenUser.setPassWord(resultSet.getString("password"));
                tokenUser.setSignature(resultSet.getString("signature"));
                tokenUsers.add(tokenUser);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            release(connection,preparedStatement,resultSet);
        }
    }
    private void release(Connection connection,PreparedStatement preparedStatement){
        try {
            if(connection !=null){
                connection.close();
            }
            if(preparedStatement != null){
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void release(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet){
        try {
            if(connection !=null){
                connection.close();
            }
            if(preparedStatement != null){
                preparedStatement.close();
            }
            if (resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
