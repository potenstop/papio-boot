package top.potens.core.util;

import org.apache.commons.collections4.CollectionUtils;
import top.potens.core.enums.EncryptAlgorithmEnum;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className EncryptUtil
 * @projectName papio-framework
 * @date 2020/5/5 7:42
 */
public class EncryptUtil {
    public static String md5(String inputText) {
        return EncryptUtil.encrypt("", inputText, EncryptAlgorithmEnum.MD5);
    }

    public static String md5(String salt, String inputText) {
        return EncryptUtil.encrypt(salt, inputText, EncryptAlgorithmEnum.MD5);
    }

    public static String sha1(String inputText) {
        return EncryptUtil.encrypt("", inputText, EncryptAlgorithmEnum.SHA1);
    }

    public static String sha1(String salt, String inputText) {
        return EncryptUtil.encrypt(salt, inputText, EncryptAlgorithmEnum.SHA1);
    }

    public static String encrypt(String inputText, EncryptAlgorithmEnum algorithmEnum) {
        return EncryptUtil.encrypt("", inputText,  algorithmEnum);
    }

    /**
     *
     * @param salt  加密盐
     * @param inputText 要加密的文本
     * @param algorithmEnum 加密算法
     * @return
     */
    public static String encrypt(String salt, String inputText, EncryptAlgorithmEnum algorithmEnum) {
        if (StringUtil.isBlank(salt)) {
            salt = "";
        }
        if (inputText == null || "".equals(inputText.trim())) {
            throw new IllegalArgumentException("请输入要加密的内容");
        }
        try {
            MessageDigest md = MessageDigest.getInstance(algorithmEnum.getAlgorithm());
            md.update((salt + inputText).getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("加密算法未找到");
        }
    }

}
