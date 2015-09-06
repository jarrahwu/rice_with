package com.stkj.xtools;

public class MultipartParameter<T> {

    private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
	
    private String paramName;
	private String contentType;
	private T value;

	public MultipartParameter(String paramName, T value) {
	    this(paramName, value, DEFAULT_CONTENT_TYPE);
	}
	
	public MultipartParameter(String paramName, T value, String contentType) {
        this.paramName = paramName;
        this.value = value;
        this.contentType = contentType;
    }

	public String getParamName() {
		return paramName;
	}

	public T getValue() {
		return value;
	}

    protected void setValue(T value) {
        this.value = value;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
}