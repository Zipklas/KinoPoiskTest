package Database;

import androidx.room.RoomDatabase;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
@Database(entities = {User.class },version = 1)
public abstract class Dabase extends RoomDatabase {
    public abstract UserDao getUserDao();
}