package com.droid.dogceo

import com.droid.dogceo.core.DogImages
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DogImagesTest {

    private lateinit var dogImages: DogImages

    @Before
    fun setup() {
        // Initialize DogImages before each test
        dogImages = DogImages()
    }

    @Test
    fun testEmptyListHasNoNextOrPreviousImage() {
        assertFalse("Empty list should not have next image", dogImages.hasNextImage())
        assertFalse("Empty list should not have previous image", dogImages.hasPreviousImage())
    }


    @Test
    fun testMultipleImagesHasNextAndPreviousImage() {
        dogImages.add("image1")
        dogImages.add("image2")
        dogImages.add("image3")

        assertTrue("Multiple items list should have next image", dogImages.hasNextImage())
        assertFalse("Initial index should not have previous image", dogImages.hasPreviousImage())

        dogImages.getNextImage() // Move to index 1
        assertTrue(
            "After moving to index 1, should have previous image",
            dogImages.hasPreviousImage()
        )
    }


    @Test
    fun testGetPreviousImageInList() {
        dogImages.add("image1")
        dogImages.add("image2")
        dogImages.add("image3")

        dogImages.getNextImage() // Move to index 1
        dogImages.getNextImage() // Move to index 2

        assertEquals("image2", dogImages.getPreviousImage())
        assertEquals("image1", dogImages.getPreviousImage())

        // Ensure null is returned after reaching the start
        assertNull("Should return null if no previous image", dogImages.getPreviousImage())
    }

    @Test
    fun testIndexUpdateOnNavigation() {
        dogImages.add("image1")
        dogImages.add("image2")
        dogImages.add("image3")

        // Navigate forward
        dogImages.getNextImage()
        assertEquals("Current index should be 1", 1, dogImages.currentIndex)

        dogImages.getNextImage()
        assertEquals("Current index should be 2", 2, dogImages.currentIndex)

        // Navigate backward
        dogImages.getPreviousImage()
        assertEquals("Current index should be 1 after moving back", 1, dogImages.currentIndex)

        dogImages.getPreviousImage()
        assertEquals("Current index should be 0 after moving back", 0, dogImages.currentIndex)
    }

    @Test
    fun testEdgeCaseIndexOutOfBounds() {
        dogImages.add("image1")

        dogImages.getNextImage() // Move to end of list
        assertNull("Should return null at end of list", dogImages.getNextImage())

        dogImages.getPreviousImage() // Move to start
        assertNull("Should return null at start of list", dogImages.getPreviousImage())
    }
}
