package nz.co.udenbrothers.clockwork.app;

import android.annotation.SuppressLint;
import android.app.Application;

import org.greenrobot.greendao.database.Database;

import nz.co.udenbrothers.clockwork.models.db.DaoMaster;
import nz.co.udenbrothers.clockwork.models.db.DaoSession;
import nz.co.udenbrothers.clockwork.util.db.DatabaseUpgradeHelper;

/**
 * Created by user on 28/08/2017.
 */

public class App extends Application {
    private DaoSession daoSession;
    private DaoMaster daoMaster;
    private Database db;

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onCreate() {
        super.onCreate();
        Preferences.initInstance(this);
        initDao();
    }
    /**
     * Init Database framework
     */
    private void initDao() {
        DatabaseUpgradeHelper helper = new DatabaseUpgradeHelper(this,AppConstants.Database.DB_NAME);
        db = helper.getWritableDb();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    /**
     * Get DAO session object
     * @return dao session object
     */
    public DaoSession getDaoSession() {
        return daoSession;
    }
    public DaoMaster getDaoMaster() {
        return daoMaster;
    }


    public void dropAllTables() {
        DaoMaster.dropAllTables(db,true);
        DaoMaster.createAllTables(db,true);
    }
}