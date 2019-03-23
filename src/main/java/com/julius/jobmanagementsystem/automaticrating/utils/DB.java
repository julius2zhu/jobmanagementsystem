package com.julius.jobmanagementsystem.automaticrating.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {  
	//数据库的地址，本机一般为localhost。crawl为数据库的名称
    public static final String url = "jdbc:mysql://localhost:3306/crawl";  
    public static final String name = "com.mysql.jdbc.Driver";  
    //用户名
    public static final String user = "root";  
    //密码
    public static final String password = "root";  
    public static Connection conn = null;  
    //关闭连接
    public void close() {  
        try {  
            this.conn.close();   
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
   //建立连接
    public void open(String sql)
    {
        try {  
            Class.forName(name);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接  
            Statement st = conn.createStatement();
            System.out.println("成功连接数据库");
            //执行sql语句
            int rs = st.executeUpdate(sql);
            
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    } 
}  
