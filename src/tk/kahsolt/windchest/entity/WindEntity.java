package tk.kahsolt.windchest.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by kahsolt on 17-6-13.
 */
@Entity
@Table(name = "wind", schema = "windchest", catalog = "")
public class WindEntity {
    private int wid;
    private int uid;
    private String chest;
    private String type;
    private String text;
    private String path;
    private Timestamp time;

    @Id
    @Column(name = "wid", nullable = false)
    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    @Basic
    @Column(name = "uid", nullable = false)
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "chest", nullable = false, length = 64)
    public String getChest() {
        return chest;
    }

    public void setChest(String chest) {
        this.chest = chest;
    }

    @Basic
    @Column(name = "type", nullable = true, length = 16)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "text", nullable = true, length = -1)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "path", nullable = true, length = 512)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "time", nullable = false)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WindEntity that = (WindEntity) o;

        if (wid != that.wid) return false;
        if (uid != that.uid) return false;
        if (chest != null ? !chest.equals(that.chest) : that.chest != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = wid;
        result = 31 * result + uid;
        result = 31 * result + (chest != null ? chest.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
