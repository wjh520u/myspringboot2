package com.project.utils;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class jdk_PBE {
    public static final String src = "I Love You !";
    public static void main(String [] args){
        long l = System.currentTimeMillis();
        for (int i = 0; i < 3000000; i++) {
            jdkPBE();
        }
        System.out.println(System.currentTimeMillis() - l);
    }
    private static void jdkPBE(){
        try {
            //初始化盐
            SecureRandom random = new SecureRandom();
            byte [] salt = random.generateSeed(8);

            //口令和密钥
            String password = "imooc";
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
            //生成密钥转换对象
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
            Key key = factory.generateSecret(pbeKeySpec);

            //加密
            PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);//实例化PBE对象的一个输入的材料：参数分别为"盐和迭代次数"
            Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
            cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
            byte [] result = cipher.doFinal(src.getBytes());
            //System.out.println("PBE:"+result.toString());

            //解密
            cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
            result = cipher.doFinal(result);
            //System.out.println("PBE:"+new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
