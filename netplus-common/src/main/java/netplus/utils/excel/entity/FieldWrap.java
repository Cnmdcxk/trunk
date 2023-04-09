package netplus.utils.excel.entity;

import java.io.Serializable;

public class FieldWrap implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = -594273774969036032L;

	private String value;

    // 一般是value的关联项，code拿name，name拿code
    private String valueExt;

    private boolean isValid;

    private String message;

    public FieldWrap(){

    }

    public FieldWrap(String value){
        this.value = value;
        this.isValid = true;
    }

    public FieldWrap(String value, String valueExt){
        this.value = value;
        this.valueExt = valueExt;
        this.isValid = true;
    }

    public String getValue(){
        return this.value;
    }

    public void setValue(String value){
    	this.value = value;
    }

    public boolean getIsValid(){
        return this.isValid;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message){
        this.message = message;
        this.isValid = false;
    }

    public String getValueExt(){
        return this.valueExt;
    }

    public void setValueExt(String valueExt){
        this.valueExt = valueExt;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

}
