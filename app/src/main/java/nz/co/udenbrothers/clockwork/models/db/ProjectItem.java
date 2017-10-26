package nz.co.udenbrothers.clockwork.models.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;

/**
 *
 */

@Entity
public class ProjectItem {
    @Id
    private Long id;

    private String businessId;
    private String qrCodeIdentifier;
    private String projectName;
    private String companyName;


    @ToMany(joinProperties = {
            @JoinProperty(name = "qrCodeIdentifier", referencedName = "qrCodeIdentifier")
    })
    private List<ShiftItem> shifts;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 912681950)
    private transient ProjectItemDao myDao;


    public ProjectItem(String newName) {
        this.qrCodeIdentifier = newName;
        this.projectName = newName;
    }

    @Generated(hash = 915690222)
    public ProjectItem(Long id, String businessId, String qrCodeIdentifier,
            String projectName, String companyName) {
        this.id = id;
        this.businessId = businessId;
        this.qrCodeIdentifier = qrCodeIdentifier;
        this.projectName = projectName;
        this.companyName = companyName;
    }

    @Generated(hash = 554209198)
    public ProjectItem() {
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBusinessId() {
        return this.businessId;
    }
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
    public String getQrCodeIdentifier() {
        return this.qrCodeIdentifier;
    }
    public void setQrCodeIdentifier(String qrCodeIdentifier) {
        this.qrCodeIdentifier = qrCodeIdentifier;
    }
    public String getProjectName() {
        return this.projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getCompanyName() {
        return this.companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 80507627)
    public List<ShiftItem> getShifts() {
        if (shifts == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ShiftItemDao targetDao = daoSession.getShiftItemDao();
            List<ShiftItem> shiftsNew = targetDao._queryProjectItem_Shifts(qrCodeIdentifier);
            synchronized (this) {
                if (shifts == null) {
                    shifts = shiftsNew;
                }
            }
        }
        return shifts;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 2027901155)
    public synchronized void resetShifts() {
        shifts = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 304207351)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProjectItemDao() : null;
    }

}
