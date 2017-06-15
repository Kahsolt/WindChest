package tk.kahsolt.windchest.entity;

import javax.persistence.*;

/**
 * Created by kahsolt on 17-6-13.
 */
@Entity
@Table(name = "chest", schema = "windchest", catalog = "")
public class ChestEntity {
    private int fid;
    private int uid;
    private String name;

    @Id
    @Column(name = "fid", nullable = false)
    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
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
    @Column(name = "name", nullable = false, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChestEntity that = (ChestEntity) o;

        if (fid != that.fid) return false;
        if (uid != that.uid) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fid;
        result = 31 * result + uid;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
