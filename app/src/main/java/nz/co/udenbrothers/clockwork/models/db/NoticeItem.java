package nz.co.udenbrothers.clockwork.models.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 *
 */

@Entity
public class NoticeItem {
    @Id
    private Long id;
    private String title;
    private String message;

    public NoticeItem(String ti, String mss){
        title = ti;
        message = mss;
    }

    @Generated(hash = 85421668)
    public NoticeItem(Long id, String title, String message) {
        this.id = id;
        this.title = title;
        this.message = message;
    }

    @Generated(hash = 1816315188)
    public NoticeItem() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
