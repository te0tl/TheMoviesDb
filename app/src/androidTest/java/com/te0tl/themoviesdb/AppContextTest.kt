package com.te0tl.themoviesdb

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test

class AppContextTest {

    @Test
    fun testCorrectPackage() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.te0tl.themoviesdb", appContext.packageName)
    }

}
