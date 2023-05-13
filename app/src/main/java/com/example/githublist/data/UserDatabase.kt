package com.example.githublist.data

import androidx.room.*
import androidx.room.RoomDatabase
import com.example.githublist.domain.model.UserDomain

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract val userDao: UserDao
}

@Entity(tableName = "User")
data class User constructor(
    @PrimaryKey val login: String,
    val avatarUrl: String)

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(item: List<User>)

    @Query("select * from User")
    suspend fun queryAllUsers(): List<User>?

    @Query("SELECT * FROM User " +
            "WHERE login LIKE :filter || '%' ")
    fun queryUsersWithFilter(filter: String): List<User>?

    @Query("delete from User where login = :login")
    suspend fun deleteUser(login: String)
}