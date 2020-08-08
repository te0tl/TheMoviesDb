
import androidx.test.espresso.intent.Intents
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class IntentsRule : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)

        Intents.init()

    }

    override fun finished(description: Description?) {
        super.finished(description)

        Intents.release()

    }
}