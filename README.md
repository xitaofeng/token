#使用
##注册servlet
为了能够编辑token授权用户信息，我们必须开启项目的web Servlet支持
也就是将此类com.lang.token.support.http.servlet.TokenViewServlet
注册到servlet。
##注册filter
此外还需要配置token的授权和验证中心com.lang.token.filter.LangFilter
。也就是注册filter并且拦截对应的api请求和token授权接口地址。
##创建token.properties
最后需要在src/main/resources 目录下创建token.properties
包括以下key：
url=必填(数据库连接地址)
driverClassName=必填(数据库连接驱动类)
username=必填(数据库账户)
password=必填(数据库密码)
token_path=必填(token授权接口地址)
aes_key=必填(AES算法加密密钥，16位以上)
expires=选填(token有效时间，单位s,默认7200s)
##创建token授权用户信息表
在你的数据库里创建以下表
DROP TABLE IF EXISTS `token_user`;
CREATE TABLE `token_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `signature` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

