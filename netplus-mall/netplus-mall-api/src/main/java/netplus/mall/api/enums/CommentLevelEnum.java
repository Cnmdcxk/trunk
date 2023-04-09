package netplus.mall.api.enums;

public enum CommentLevelEnum {
    GOOD(0,"好评"),
    MIDDLE(1,"中评"),
    BAD(2,"差评");

    private int code;
    private String level;

    CommentLevelEnum(int code, String level) {
        this.code = code;
        this.level = level;
    }

    public int getCode() {
        return code;
    }

    public String getLevel() {
        return level;
    }

    public static CommentLevelEnum getLevelByScore(int score){
        if(score>3){
            return GOOD;
        }else if(score==3){
            return MIDDLE;
        }else{
            return BAD;
        }
    }
}
