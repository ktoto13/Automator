package ru.netology.testing.uiautomator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


const val SETTINGS_PACKAGE = "com.android.settings"
const val MODEL_PACKAGE = "ru.netology.testing.uiautomator"

const val TIMEOUT = 5000L

@RunWith(AndroidJUnit4::class)
class ChangeTextTest {

    private lateinit var device: UiDevice
    private val textToSet = "Netology"
    private val textToSetSpace = " "
    private val textToSetHello = "Hello UiAutomator!"
    private fun waitForPackage(packageName: String) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(MODEL_PACKAGE)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
    }
    @Before
    fun beforeEachTest() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
    }

    @Test
    fun testSpaceInText() {
        val packageName = MODEL_PACKAGE
        waitForPackage(packageName)

        device.findObject(By.res(packageName,"userInput")).text = textToSetSpace
        device.findObject(By.res(packageName,"buttonChange")).click()
        val result = device.findObject(By.res(packageName,"textToBeChanged")).text

        Thread.sleep(5000)
        assertEquals(textToSetHello, result)

    }

    @Test
    fun testOpenNewActivity() {
        val packageName = MODEL_PACKAGE
        waitForPackage(packageName)

        device.findObject(By.res(packageName,"userInput")).text = textToSet
        device.findObject(By.res(packageName,"buttonActivity")).click()
        device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
        val result = device.findObject(By.res(packageName,"text")).text

        Thread.sleep(5000)
        assertEquals(textToSet, result)
    }

}
