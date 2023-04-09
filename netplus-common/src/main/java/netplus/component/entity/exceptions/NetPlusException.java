package netplus.component.entity.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NetPlusException extends RuntimeException {

    private String errorCode;

    public NetPlusException(String message){
        super(message);
    }

    public NetPlusException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

}
