package com.sonu.loginmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sonu.loginmvvm.data.db.entities.USER_ID
import com.sonu.loginmvvm.data.db.entities.User

@Dao
interface UserDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upSert(user: User): Long

    @Query("SELECT * FROM USER WHERE id = $USER_ID")
    fun getUser(): LiveData<User>
}