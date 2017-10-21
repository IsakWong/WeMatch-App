package nullref.dlut.wematch.utils.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

/**
 * Created by IsakWong on 2017/7/9.
 */

public abstract class BaseDbHelper extends OrmLiteSqliteOpenHelper {


    public BaseDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
    }


}
