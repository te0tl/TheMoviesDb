
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.CapturingSlot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Rule

@ExperimentalCoroutinesApi
abstract class BaseViewModelTest<VM, VMS> where VMS : Any {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    protected val testDispatcher: TestCoroutineDispatcher = coroutinesTestRule.testDispatcher

    protected abstract val viewModel: VM
    protected abstract val viewModelStateSlot: CapturingSlot<VMS>
    protected abstract val viewModelStateObserver: Observer<VMS>

}
