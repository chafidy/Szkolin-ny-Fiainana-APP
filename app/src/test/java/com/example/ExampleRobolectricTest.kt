package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.BookData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Sekolin'ny Fiainana", appName)
  }

  @Test
  fun `verify book data integrity`() {
    assertEquals(8, BookData.cahiers.size)
    assertTrue("Should have planches populated", BookData.allPlanches.isNotEmpty())
    assertTrue("Should have workshops populated", BookData.workshops.isNotEmpty())
    assertTrue("Should have quizzes populated", BookData.quizzes.isNotEmpty())
  }
}

