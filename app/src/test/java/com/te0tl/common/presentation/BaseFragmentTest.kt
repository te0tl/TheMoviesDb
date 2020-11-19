
import androidx.fragment.app.testing.FragmentScenario
import com.agoda.kakao.screen.Screen
import com.te0tl.common.presentation.fragment.BaseFragment
import com.te0tl.common.presentation.viewmodel.BaseViewModel
import com.te0tl.themoviesdb.EmptyApp
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = EmptyApp::class,
        manifest = Config.NONE,
        qualifiers = "en-rUS-w360dp-h640dp-xhdpi")
abstract class BaseFragmentTest<F> : AutoCloseKoinTest() where F: BaseFragment<*> {
    protected abstract val scenario: FragmentScenario<F>

    protected abstract val viewModel: BaseViewModel<*>

    protected abstract val screen: Screen<*>

}
