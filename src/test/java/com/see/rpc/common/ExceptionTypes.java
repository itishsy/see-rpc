package com.see.rpc.common;

/**
 * 异常类型
 */
public enum ExceptionTypes {

    BIZ_EXCEPTION("Business Exception"),

    DB_EXCEPTION("Database Exception"),

    DAO_EXCEPTION("Dao Exception"),

    FILE_EXCEPTION("File Exception"),

    IMPORT_EXCEPTION("Import Exception"),

    VALIDATE_EXCEPTION("Validate Exception");

    private String code;

    ExceptionTypes(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
