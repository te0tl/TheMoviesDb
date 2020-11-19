package com.te0tl.common

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText

infix fun Int.perform(action: ViewAction) {
    onView(ViewMatchers.withId(this)).perform(action)
}

/*infix fun Int.checkThat(matcher: Matcher) {
    onView(ViewMatchers.withId(this)).check(ViewAssertions.matches(matcher))
}*/

infix fun Int.checkThatTextIs(text: String) {
    onView(ViewMatchers.withId(this)).check(ViewAssertions.matches(withText(text)))
}

infix fun Int.replaceTextWith(text: String?) {
    onView(ViewMatchers.withId(this)).perform(ViewActions.replaceText(text))
}