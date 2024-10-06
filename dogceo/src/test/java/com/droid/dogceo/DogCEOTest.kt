import android.app.Application
import com.droid.dogceo.core.DogCEO
import com.droid.dogceo.core.convertToDogImages
import com.droid.dogceo.data.DogCEORepository
import com.droid.dogceo.exceptions.InvalidCountException
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DogCEOTest {

    private lateinit var application: Application
    private lateinit var mockRepository: DogCEORepository
    private lateinit var mockDogCEO: DogCEO

    @Before
    fun setUp() {
        application = mock(Application::class.java)
        mockRepository = mock(DogCEORepository::class.java)
        mockDogCEO = DogCEO(mockRepository)
    }


    @Test(expected = IllegalStateException::class)
    fun `test getApplication throws exception when not initialized`() {
        mockDogCEO.getApplication()
    }

    @Test
    fun `test getApplication returns application when initialized`() {
        mockDogCEO.init(application)
        assertEquals(application, mockDogCEO.getApplication())
    }

    @Test
    fun `test getImages returns DogImages when count is valid`() = runTest {
        val dogImages =
            listOf("image1", "image2", "image3", "image4", "image5").convertToDogImages()
        `when`(mockRepository.getDogImages(5)).thenReturn(dogImages)

        val result = mockDogCEO.getImages(5)
        assertNotNull(result)
        assertEquals(dogImages, result)
    }

    @Test(expected = InvalidCountException::class)
    fun `test getImages throws InvalidCountException when count is less than 1`() {
        runBlocking {
            mockDogCEO.getImages(0)
        }
    }

    @Test(expected = InvalidCountException::class)
    fun `test getImages throws InvalidCountException when count is greater than MAX_IMAGE_COUNT`() {
        runBlocking {
            mockDogCEO.getImages(11)
        }
    }

    @Test
    fun `test getImage returns image from repository`() =
        runTest {
            val dogImage = "http://example.com/dog.jpg"
            `when`(mockRepository.getDogImage()).thenReturn(dogImage)
            val result = mockDogCEO.getImage()
            assertEquals(dogImage, result)
        }
    
}