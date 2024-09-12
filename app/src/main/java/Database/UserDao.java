package Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    public void addUser(User user);
    @Update
    public void updateUser(User user);
    @Delete
    public void deleteUser(User user);
    @Query("select * from users")
    public List<User> getAllUser();
    @Query("select * from users where login == :login")
    public User getUser(String login);

    @Query("select user_id from users where login == :login")
    public int getUserId(String login);

}