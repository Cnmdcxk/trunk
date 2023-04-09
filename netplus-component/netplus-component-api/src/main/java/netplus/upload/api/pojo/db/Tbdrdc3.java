package netplus.upload.api.pojo.db;

public class Tbdrdc3 {
    private Long filehandle;

    private String info;

    private String filename;

    private String createtime;

    private String creatre;

    private String lock;

    private String managed;

    private String filedescription;

    private String filelocation;

    public Long getFilehandle() {
        return filehandle;
    }

    public void setFilehandle(Long filehandle) {
        this.filehandle = filehandle;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getCreatre() {
        return creatre;
    }

    public void setCreatre(String creatre) {
        this.creatre = creatre == null ? null : creatre.trim();
    }

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock == null ? null : lock.trim();
    }

    public String getManaged() {
        return managed;
    }

    public void setManaged(String managed) {
        this.managed = managed == null ? null : managed.trim();
    }

    public String getFiledescription() {
        return filedescription;
    }

    public void setFiledescription(String filedescription) {
        this.filedescription = filedescription == null ? null : filedescription.trim();
    }

    public String getFilelocation() {
        return filelocation;
    }

    public void setFilelocation(String filelocation) {
        this.filelocation = filelocation == null ? null : filelocation.trim();
    }
}