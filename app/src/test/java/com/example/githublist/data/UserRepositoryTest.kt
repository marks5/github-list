package com.example.githublist.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.githublist.MainCoroutineRule
import com.example.githublist.data.mapper.UserDaoToUserDomainMapper
import com.example.githublist.data.mapper.UserEntityToDaoMapper
import com.example.githublist.data.model.UserEntity
import com.example.githublist.domain.model.UserDomain
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    private val mockUserNetwork: GithubAPI = mockk()

    private val mockUserDao: UserDao = mockk()

    private val mockUserDataStore: UserDataStore = mockk()

    private val mockUserEntityToDaoMapper: UserEntityToDaoMapper = mockk()

    private val mockUserDaoToUserDomainMapper: UserDaoToUserDomainMapper = mockk()

    private lateinit var userRepository: UserRepositoryImpl

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun onSetup() {
        userRepository = UserRepositoryImpl(
            mockUserNetwork,
            mockUserDao,
            mockUserDataStore,
            mockUserEntityToDaoMapper,
            mockUserDaoToUserDomainMapper
        )
    }

    @Test
    fun `when users are fetched then users are filtered and inserted in the database`()
    = runTest {
        // given
        val userEntityList = givenUserEntityList()
        coEvery { mockUserNetwork.getUsers(1, 200) } coAnswers {userEntityList}
        val userDaoList = givenUserDaoList(userEntityList)

        val deletedUsers = setOf("email2", "email3")
        val deletedUsersFlow = flow { emit(deletedUsers) }
        coEvery { mockUserDataStore.deletedUsersFlow } coAnswers {deletedUsersFlow}
        coEvery { mockUserDao.insertUsers(userDaoList) } coAnswers { Unit }

        // when
        userRepository.fetchNewUsers()

        // then
        coVerify { mockUserNetwork.getUsers(1, 200) }
        coVerify { mockUserDao.insertUsers(userDaoList) }
    }

    @Test
    fun `when local users are queried then the database is queried`() = runTest {
        //given
        coEvery { mockUserDao.queryAllUsers() } coAnswers { listOf(givenUser()) }
        coEvery { mockUserDaoToUserDomainMapper.userDaoToUserDomain(givenUser()) } coAnswers { givenUserDomain() }

        // when
        userRepository.queryLocalUsers()

        // then
        coVerify { mockUserDao.queryAllUsers() }
    }

    @Test
    fun `when user is deleted then the database is updated`() = runTest {
        // given
        val login = "login"
        coEvery { mockUserDao.deleteUser(login) } coAnswers { Unit }
        coEvery { mockUserDataStore.updateDeletedUsers(login) } coAnswers { Unit }

        // when
        userRepository.deleteUser(login)

        // then
        coVerify { mockUserDao.deleteUser(login) }
        coVerify { mockUserDataStore.updateDeletedUsers(login) }
    }

    @Test
    fun `when users are filtered then the database is queried`() = runTest {
        // given
        val filter = "filter"
        coEvery { mockUserDao.queryUsersWithFilter(filter) } coAnswers { listOf(givenUser()) }
        coEvery { mockUserDaoToUserDomainMapper.userDaoToUserDomain(givenUser()) } coAnswers { givenUserDomain() }

        // when
        userRepository.queryUsersWithFilter(filter)

        // then
        coVerify { mockUserDao.queryUsersWithFilter(filter) }
    }

    private fun givenUserEntityList() : List<UserEntity> = listOf(
                givenUserEntity(),
                givenUserEntity(),
                givenUserEntity(),
                givenUserEntity(),
            )

    private fun givenUserEntity(): UserEntity {
        val login = "login"
        val url = "url"

        return UserEntity(login, url)
    }

    private fun givenUserDomain(): UserDomain {
        return UserDomain(login = "login", avatarUrl = "url")
    }

    private fun givenUser(): User {
        return User(
            login = "login",
            avatarUrl = "url"
        )
    }

    private fun givenUserDaoList(userEntityList: List<UserEntity>) : List<User> {
        val userDaoList = userEntityList.map { mapUserEntityToDao(it) }

        for ((index, user) in userEntityList.withIndex()) {
            coEvery { mockUserEntityToDaoMapper.userEntityToDao(user) } coAnswers {userDaoList[index]}
        }
        return userDaoList
    }

    private fun mapUserEntityToDao(userEntity: UserEntity) =
        User(userEntity.login, userEntity.avatarUrl)
}