package pt.jp.purchasesservice.exceptions;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(String message){
        super(message);
    }
}