package nz.co.udenbrothers.clockwork.util.db.migration;

import android.database.sqlite.SQLiteDatabase;

import nz.co.udenbrothers.clockwork.util.db.DatabaseUpgradeHelper;


/**
 * Migration to scheme v2

 */

public class MigrationV2 implements DatabaseUpgradeHelper.Migration {
    @Override
    public Integer getVersion() {
        return 2;
    }

    @Override
    public void runMigration(SQLiteDatabase db) {
        //noinspection unchecked
        //MigrationHelper.migrate(db, SampleDao.class, SampleRecordDao.class);
    }
}
