package nz.co.udenbrothers.clockwork.models.db;

import android.text.format.DateUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

import nz.co.udenbrothers.clockwork.tools.MyDate;
import nz.co.udenbrothers.clockwork.util.MyDateUtils;
import org.greenrobot.greendao.annotation.Generated;

/**
 *
 */

@Entity
public class ShiftItem {
    @Id
    private Long id;

    private String userId;
    private String qrCodeIdentifier;
    private String shiftTimeStartOnUtc;
    private String shiftTimeEndOnUtc;
    private String comment = "";
    private int isManual = 0;
    private int uploaded = 0;
    private int stopped = 0;

    public ShiftItem(String name, String start, String end, String uid){
        qrCodeIdentifier = name;
        shiftTimeStartOnUtc = start;
        shiftTimeEndOnUtc = end;
        userId = uid;
    }
    @Generated(hash = 1402358763)
    public ShiftItem(Long id, String userId, String qrCodeIdentifier,
            String shiftTimeStartOnUtc, String shiftTimeEndOnUtc, String comment,
            int isManual, int uploaded, int stopped) {
        this.id = id;
        this.userId = userId;
        this.qrCodeIdentifier = qrCodeIdentifier;
        this.shiftTimeStartOnUtc = shiftTimeStartOnUtc;
        this.shiftTimeEndOnUtc = shiftTimeEndOnUtc;
        this.comment = comment;
        this.isManual = isManual;
        this.uploaded = uploaded;
        this.stopped = stopped;
    }
    @Generated(hash = 81559532)
    public ShiftItem() {
    }
    public Date getStartDate() {
        return shiftTimeStartOnUtc!=null? MyDate.strToDate(shiftTimeStartOnUtc):null;
    }
    public Date getEndDate() {
        return shiftTimeEndOnUtc!=null? MyDate.strToDate(shiftTimeEndOnUtc):null;
    }

    public boolean isManual() {
        return isManual==1;
    }

    public void setManual(boolean manual) {
        isManual = manual?1:0;
    }

    public boolean isToday() {
        Date ed = getEndDate();
        if (ed==null)
            return true;
        if (DateUtils.isToday(ed.getTime()))
            return true;
        Date sd = getStartDate();
        if (sd==null)
            return false;
        return DateUtils.isToday(sd.getTime());
    }

    public boolean isThisWeek() {
        Date ed = getEndDate();
        if (ed == null)
            return true;
        if (MyDateUtils.isThisWeek(ed.getTime()))
            return true;
        Date sd = getStartDate();
        if (sd == null)
            return false;
        return MyDateUtils.isThisWeek(sd.getTime());
    }

    public boolean isThisMonth() {
        Date ed = getEndDate();
        if (ed == null)
            return true;
        if (MyDateUtils.isThisMonth(ed.getTime()))
            return true;
        Date sd = getStartDate();
        if (sd == null)
            return false;
        return MyDateUtils.isThisMonth(sd.getTime());
    }

    public long getDuration() {
        Date ed = getEndDate();
        Date sd = getStartDate();
        if (ed==null)
            ed = new Date();
        if (sd==null)
            return 0;
        return ed.getTime()-sd.getTime();
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getQrCodeIdentifier() {
        return this.qrCodeIdentifier;
    }
    public void setQrCodeIdentifier(String qrCodeIdentifier) {
        this.qrCodeIdentifier = qrCodeIdentifier;
    }
    public String getShiftTimeStartOnUtc() {
        return this.shiftTimeStartOnUtc;
    }
    public void setShiftTimeStartOnUtc(String shiftTimeStartOnUtc) {
        this.shiftTimeStartOnUtc = shiftTimeStartOnUtc;
    }
    public String getShiftTimeEndOnUtc() {
        return this.shiftTimeEndOnUtc;
    }
    public void setShiftTimeEndOnUtc(String shiftTimeEndOnUtc) {
        this.shiftTimeEndOnUtc = shiftTimeEndOnUtc;
    }
    public String getComment() {
        return this.comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public int getIsManual() {
        return this.isManual;
    }
    public void setIsManual(int isManual) {
        this.isManual = isManual;
    }
    public int getUploaded() {
        return this.uploaded;
    }
    public void setUploaded(int uploaded) {
        this.uploaded = uploaded;
    }
    public int getStopped() {
        return this.stopped;
    }
    public void setStopped(int stopped) {
        this.stopped = stopped;
    }

}
