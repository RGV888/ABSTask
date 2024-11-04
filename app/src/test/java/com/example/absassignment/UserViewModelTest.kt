package com.example.absassignment


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.absassignment.data.repository.UserRepository
import com.example.absassignment.utils.UserUtils
import com.example.absassignment.viewmodel.UserViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var userViewModel: UserViewModel
    private val mockRepository: UserRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        userViewModel = UserViewModel(mockRepository)
    }

    @Test
    fun `fetchUsers should return success state when users are fetched successfully`() = runTest {
        // Given
        val userList = UserUtils.createSampleUserList()
        coEvery { mockRepository.getUsers(2) } returns userList

        // When
        userViewModel.fetchUsers(2)
        advanceUntilIdle()
        // Then
        userViewModel.uiState.observeForever { state ->
            assert(state is UserViewModel.UserUiState.Success)
            assert((state as UserViewModel.UserUiState.Success).users == userList)
        }
    }

    @Test
    fun `fetchUsers should return error state when fetching users fails`() = runTest {
        // Given
        coEvery { mockRepository.getUsers(2) } throws Exception("Network error")

        // When
        userViewModel.fetchUsers(2)
        advanceUntilIdle()
        // Then
        userViewModel.uiState.observeForever { state ->
            assert(state is UserViewModel.UserUiState.Error)
            assert((state as UserViewModel.UserUiState.Error).message == "Failed to fetch users: Network error")
        }
    }

    @Test
    fun `fetchUsers should show loading state before fetching users`() = runTest {
        // Given
        coEvery { mockRepository.getUsers(2) } returns emptyList() // Adjust as needed

        // When
        userViewModel.fetchUsers(2)


        // Then
        val state = userViewModel.uiState.value
        assert(state is UserViewModel.UserUiState.Loading)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain() // Reset Main dispatcher
    }
}
