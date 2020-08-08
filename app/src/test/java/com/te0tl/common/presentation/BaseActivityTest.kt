
import androidx.test.core.app.ActivityScenario
import com.agoda.kakao.screen.Screen
import com.te0tl.commons.presentation.activity.BaseActivity
import com.te0tl.commons.presentation.viewmodel.BaseViewModel
import com.te0tl.themoviesdb.EmptyApp
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = EmptyApp::class,
        manifest = Config.NONE)
abstract class BaseActivityTest<A> : AutoCloseKoinTest() where A: BaseActivity<*> {
    protected abstract val scenario: ActivityScenario<A>

    protected abstract val viewModel: BaseViewModel<*>

    protected abstract val screen: Screen<*>

}