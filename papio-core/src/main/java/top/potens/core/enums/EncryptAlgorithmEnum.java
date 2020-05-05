package top.potens.core.enums;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className EncryptAlgorithmEnum
 * @projectName papio-framework
 * @date 2020/5/5 7:47
 */
public enum EncryptAlgorithmEnum {
    MD5("md5"),
    SHA1("sha1"),
    ;
    private String algorith;

    EncryptAlgorithmEnum(String algorith) {
        this.algorith = algorith ;
    }
    public String getAlgorithm () {
        return this.algorith;
    }
}
