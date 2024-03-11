package org.ac.cst8277.sun.guiquan.ums.utils;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Objects;

@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component(value = "jsonResult")
public class JSONResult<T> implements Serializable {
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public void setData(T data) {
        this.data = data;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof JSONResult other)) {
            return false;
        }
        if (!other.canEqual(this)) {
            return false;
        }
        if (isSuccess() != other.isSuccess()) {
            return false;
        }
        Object thisObject = getMessage();
        Object otherObject = other.getMessage();
        if (!Objects.equals(thisObject, otherObject)) {
            return false;
        }
        Object thisCode = getCode();
        Object otherCode = other.getCode();
        if (!Objects.equals(thisCode, otherCode)) {
            return false;
        }

        Object thisData = getData();
        Object otherData = other.getData();
        return Objects.equals(thisData, otherData);
    }

    protected boolean canEqual(Object other) {
        return other instanceof JSONResult;
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + (isSuccess() ? 79 : 97);
        Object messageForhash = getMessage();
        result = result * 59 + ((messageForhash == null) ? 43 : messageForhash.hashCode());
        Object codeForhash = getCode();
        result = result * 59 + ((codeForhash == null) ? 43 : codeForhash.hashCode());
        Object dataForHash = getData();
        return result * 59 + ((dataForHash == null) ? 43 : dataForHash.hashCode());
    }

    public String toString() {
        return "JSONResult(success=" + isSuccess() + ", " +
                "message=" + getMessage() + ", code=" + getCode() + ", ";
    }

    private boolean success = true;

    public boolean isSuccess() {
        return this.success;
    }

    private String message = "Operate successfully";

    public String getMessage() {
        return this.message;
    }

    private String code = "200";

    public String getCode() {
        return this.code;
    }


    @Transient
    private T data;

    public T getData() {
        return this.data;
    }


    public JSONResult<T> success(int code, T obj) {
        JSONResult<T> instance = new JSONResult<>();
        instance.setSuccess(true);
        instance.setCode(String.valueOf(code));
        instance.setData(obj);
        return instance;
    }

    public JSONResult<T> success(int code, String msg) {
        JSONResult<T> instance = new JSONResult<>();
        instance.setSuccess(true);
        instance.setCode(String.valueOf(code));
        instance.setMessage(msg);
        return instance;
    }

    public JSONResult<T> success(int code, String msg, T obj) {
        JSONResult<T> instance = new JSONResult<>();
        instance.setSuccess(true);
        instance.setCode(String.valueOf(code));
        instance.setMessage(msg);
        instance.setData(obj);
        return instance;
    }

    public JSONResult<T> error(int code, String msg) {
        JSONResult<T> instance = new JSONResult<>();
        instance.setSuccess(false);
        instance.setCode(String.valueOf(code));
        instance.setMessage(msg);
        return instance;
    }


    public boolean hasLength(String str) {
        return StringUtils.hasLength(str);
    }


}
