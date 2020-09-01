package android.bignerdranch.beatbox

import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.Before


import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SoundViewModelTest {

    private var beatBox: BeatBox? = null
    private var sound: Sound? = null
    private var subject: SoundViewModel? = null

    @Before
    fun setUp() {
        beatBox = mock(BeatBox::class.java)
        sound = Sound("assetPath")
        subject = SoundViewModel(beatBox!!)
        subject?.sound = sound
    }

    @Test
    fun exposesSoundNameAsTitle()
    {
        MatcherAssert.assertThat(subject?.getName(), Is.`is`(sound?.name))
    }


    @Test
    fun callsBeatBoxPlayOnButtonClicked()
    {
        subject?.onButtonClicked()
        verify(beatBox)?.play(sound!!)
    }
}