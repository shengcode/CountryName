package com.mymodern.calc.sideProject.entity;

public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

    

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static <T> ApiResponse <T> success (T data){
        return new ApiResponse<>(200, "success", data);
    }

     public static <T> ApiResponse <T> failWithException (T data){
        return new ApiResponse<>(500, "fail with exception, internal error", null);
    }

     public static <T> ApiResponse <T> customize (int status, String message, T data){
        return new ApiResponse<>(status, message, data);
    }



    

}
