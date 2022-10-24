import crypto.AESUtil;

public class AESUtilTest {

    public static void main(String[] args) {
        String key = "2bc73dw20ebf4d46";
        String data = "{username: 哈哈哈哈哈哈哈惺惺惜惺惺}";
        System.out.println("加密前: " + data);
        String encode = AESUtil.encryptIntoHexString(data, key);
        System.out.println("加密后: " + encode);
        String decode = AESUtil.decryptByHexString(encode, key);
        System.out.println("解密后: " + decode);


    }
}
