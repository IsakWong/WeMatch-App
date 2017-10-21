package nullref.dlut.wematch.utils.database;

/**
 * Created by IsakWong on 2017/7/9.
 */

import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;

import java.util.List;

import nullref.dlut.wematch.utils.LogToFile;
import nullref.dlut.wematch.utils.WeMatchApplication;

/**
 * App配置数据库
 */
public class ConfigDbHelper extends BaseDbHelper {
    private static final int VERSION = 4;

    public static String dbName = "config.db";
    public static String AvatarUrlPrefix = "avatar_prefix";
    public static String MainServerIp = "server_ip";
    public static String CommunityServerIp = "";
    public static String isAutoLogin = "auto_login";
    public static String DefaultUser = "default_user";
    public static String isFirstRun = "first_run";
    public static String LocalUserAvatarUrl = "avatar_url";
    public static String needUpdateInfo = "need_update";
    public static String imageUrlPrefix = "image_prefix";
    public static String WeMatchVersion = "1.0";
    protected static ConfigDbHelper instance;
    private Dao<Pair, Integer> configDao = null;
    public ConfigDbHelper() {
        super(WeMatchApplication.getInstance(), dbName, null, VERSION);
    }

    public static ConfigDbHelper getInstance() {
        if (null == instance) {
            instance = new ConfigDbHelper();
        }
        return instance;
    }

    public void update(String key, String value) {
        try {
            UpdateBuilder updateBuilder = getDBDao().updateBuilder();
            updateBuilder.updateColumnValue("value", value);
            updateBuilder.where().eq("name", key).prepare();
            updateBuilder.update();

        } catch (Exception excep) {
            LogToFile.e("type:database...", excep.getMessage());
        }
    }

    public Dao<Pair, Integer> getDBDao() {
        try {
            return getDao(Pair.class);
        } catch (Exception exp) {
            LogToFile.e("sql", exp.getMessage());

        }
        return null;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Pair.class);
            configDao = getDao(Pair.class);

            Pair firstRun = new Pair();
            firstRun.name = isFirstRun;
            firstRun.value = "true";
            configDao.create(firstRun);

            Pair serverIP = new Pair();
            serverIP.name = MainServerIp;
            serverIP.value = "http://60.205.218.75:8980/api";
            ;
            configDao.create(serverIP);

            Pair defaultUser = new Pair();
            defaultUser.name = DefaultUser;
            defaultUser.value = "";
            configDao.create(defaultUser);

            Pair autoRun = new Pair();
            autoRun.name = isAutoLogin;
            autoRun.value = "false";
            configDao.create(autoRun);

            Pair avatarPrefix = new Pair();
            avatarPrefix.name = AvatarUrlPrefix;
            avatarPrefix.value = "https://wematch.oss-cn-shanghai.aliyuncs.com/";
            configDao.create(avatarPrefix);

            Pair userURL = new Pair();
            userURL.name = LocalUserAvatarUrl;
            userURL.value = "";
            configDao.create(userURL);


            Pair needUpdate = new Pair();
            needUpdate.name = needUpdateInfo;
            needUpdate.value = "true";
            configDao.create(needUpdate);

            Pair imagePrefix = new Pair();
            imagePrefix.name = imageUrlPrefix;
            imagePrefix.value = "http://60.205.218.75:8980/image/";
            configDao.create(imagePrefix);

        } catch (Exception exception) {
            LogToFile.e(exception);
        }
    }

    public String query(String key) {
        try {
            configDao = getDao(Pair.class);
            List<Pair> result = configDao.queryBuilder().where().eq("name", key).query();
            return result.get(0).value;
        } catch (Exception exception) {
            LogToFile.e(exception);
        }
        return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            configDao = getDao(Pair.class);
            Pair imagePrefix = new Pair();
            imagePrefix.name = imageUrlPrefix;
            imagePrefix.value = "http://60.205.218.75:8980/image/";
            configDao.create(imagePrefix);


        } catch (Exception exception) {
            LogToFile.e(exception);
        }
    }

    /**
     * Pair键值对表
     */
    @DatabaseTable(tableName = "app_config")
    public static class Pair {

        @DatabaseField(generatedId = true)
        public int id;
        @DatabaseField(columnName = "name")
        public String name;
        @DatabaseField(columnName = "value")
        public String value;

        public Pair() {

        }

        public Pair(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
}

