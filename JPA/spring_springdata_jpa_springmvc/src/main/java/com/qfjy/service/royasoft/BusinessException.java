package com.qfjy.service.royasoft;

import org.apache.commons.lang3.StringUtils;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 703092643576817202L;

    private String code;

    private String tips;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String code, String message, String tips) {
        super(StringUtils.isEmpty(message) ? tips : message);
        this.code = code;
        this.tips = tips;
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BusinessException(String code, String message, String tips, Throwable cause) {
        super(StringUtils.isEmpty(message) ? tips : message, cause);
        this.code = code;
        this.tips = tips;
    }

    public String getCode() {
        return code;
    }

    public String getTips() {
        return tips;
    }
}
