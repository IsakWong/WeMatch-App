package nullref.dlut.wematch.utils.database;

/**
 * Created by IsakWong on 2017/7/9.
 */


import android.database.sqlite.SQLiteDatabase;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;
import nullref.dlut.wematch.utils.LogToFile;
import nullref.dlut.wematch.utils.WeMatchApplication;


public class UserDbHelper extends BaseDbHelper {

    protected static UserDbHelper instance;

    private static final int VERSION = 1;
    private static String dbName = "user.db";
    public Dao<UserPwd, Integer> userDao;


    @DatabaseTable(tableName = "user_pwd")
    public static class UserPwd {

        @DatabaseField(columnName = "email",id = true)
        public String email;
        @DatabaseField(columnName = "md5pwd")
        public String md5pwd;
    }

    public UserDbHelper() {
        super(WeMatchApplication.getInstance(), dbName, null, VERSION);
    }

    public static UserDbHelper getInstance(){
        if(null==instance){
            instance = new UserDbHelper();
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, UserPwd.class);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }


    public Dao<UserPwd, Integer> getUserDao() {
        return userDao;
    }

    public UserPwd getUserPwd(String userName) {
        try {
            List<UserPwd> result = getDao(UserPwd.class).queryBuilder().where().eq("email", userName).query();
            if (!result.isEmpty()) {
                return result.get(0);
            }
        } catch (SQLException sqlException) {
            LogToFile.e("sql",sqlException.getMessage());
        }
        return null;
    }

    public void saveUserPwd(String username, String pwd) {

        UserPwd user = new UserPwd();
        user.email = username;
        user.md5pwd = pwd;
        try {
            getDao(UserPwd.class).createOrUpdate(user);
        } catch (SQLException sqlException) {
            LogToFile.e("sql",sqlException.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }
}

