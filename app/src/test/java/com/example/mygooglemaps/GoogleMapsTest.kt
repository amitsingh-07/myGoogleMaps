package com.example.mygooglemaps

import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

    @RunWith(AndroidJUnit4::class)
    class GoogleMapsTest {

        @Mock
        private lateinit var mockFusedLocationProviderClient: FusedLocationProviderClient
        private lateinit var mainActivity: MainActivity

        @Before
        fun setUp() {
            MockitoAnnotations.openMocks(this)
            mainActivity = MainActivity()
            mainActivity.mFusedLocationClient = mockFusedLocationProviderClient
        }

        @Test
        fun testGetLocation() {
            // Mock the required behavior
            val mockLocation = mock(Location::class.java)
            `when`(mockFusedLocationProviderClient.lastLocation).thenReturn(mockLocation)
            val mockGeocoder = mock(Geocoder::class.java)
            val mockAddress = mock(Address::class.java)
            `when`(mockGeocoder.getFromLocation(mockLocation.latitude, mockLocation.longitude, 1))
                .thenReturn(listOf(mockAddress))
            `when`(mockAddress.latitude).thenReturn(37.7749)
            `when`(mockAddress.longitude).thenReturn(-122.4194)
            `when`(mockAddress.countryName).thenReturn("United States")
            `when`(mockAddress.locality).thenReturn("San Francisco")
            `when`(mockAddress.getAddressLine(0)).thenReturn("San Francisco, CA, USA")

            // Call the method being tested
            mainActivity.getLocation()

            // Verify the expected behavior
            assertEquals("Latitude\n37.7749", mainActivity.mainBinding.tvLatitude.text)
            assertEquals("Longitude\n-122.4194", mainActivity.mainBinding.tvLongitude.text)
            assertEquals("Country Name\nUnited States", mainActivity.mainBinding.tvCountryName.text)
            assertEquals("Locality\nSan Francisco", mainActivity.mainBinding.tvLocality.text)
            assertEquals("Address\nSan Francisco, CA, USA", mainActivity.mainBinding.tvAddress.text)
        }
    }