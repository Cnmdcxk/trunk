package netplus.utils.excel.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityWrap implements Serializable {

    private boolean isValid = true;
    private List<String> messages = new ArrayList<String>();


    public boolean getIsValid(){
        return this.isValid;
    }

    public void setIsValid(boolean isValid){
        this.isValid = isValid;
    }

    public List<String> getMessages(){
        return this.messages;
    }

    public void setMessage(String message){
        this.messages.add(message);
    }
}
