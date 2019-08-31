package com.example.sample.currencyapp

import androidx.lifecycle.ViewModelProviders
import com.example.sample.currencyapp.base.ViewModelFactory
import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File


@RunWith(MockitoJUnitRunner::class)
class MainViewTest {
    lateinit var mockServer: MockWebServer
    var viewModel: MainViewModel? = null
    lateinit var viewModelFactory: ViewModelFactory

    @Mock
    lateinit var view: MainActivity
    lateinit var testAppComponent: TestAppComponent


    @Before
    fun setUp() {
        this.testAppComponent = DaggerTestAppComponent.builder()
            .build()
        this.viewModel = ViewModelProviders.of(view, viewModelFactory)[MainViewModel::class.java]
        this.testAppComponent.inject(this)
        mockServer = MockWebServer()

        // Mock a response with status 200 and sample JSON output
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("json/popular.json"))
        // Enqueue request
        mockServer.enqueue(mockResponse)
        mockServer.start()

        viewModel = MainViewModel()
    }

    @Test
    fun getRate() {
        viewModel!!.getRate("EUR")
        val request = mockServer.takeRequest()
        Mockito.verify(view)?.addData()
        assertEquals(getJson("json/popular.json"), request.body)
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }


    fun getJson(path: String): String {
        // Load the JSON response
        val uri = this.javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }


}