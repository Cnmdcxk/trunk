package netplus.component.authbase.interceptor;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthError extends Exception {

    private Integer status;

    public AuthError(Integer status, String message) {
        super(message);
        this.status = status;
    }
}
