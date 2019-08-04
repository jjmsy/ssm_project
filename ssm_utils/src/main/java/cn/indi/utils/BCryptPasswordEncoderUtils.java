package cn.indi.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//用户密码加密工具类
public class BCryptPasswordEncoderUtils {
    public static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public static String encodPassword(String password){
        return encoder.encode(password);
    }

    public static void main(String[] args) {
        String password = "123";
        String password1 = encodPassword(password);//$2a$10$gfqs8ZCK4cd3ON6DatWWoOYYXM9uyOj3q7/RgL.XZE71Eehvo1iE6
        System.out.println(password1);
    }
}
