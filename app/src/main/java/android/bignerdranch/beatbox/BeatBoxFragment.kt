package android.bignerdranch.beatbox

import android.bignerdranch.beatbox.databinding.FragmentBeatBoxBinding
import android.bignerdranch.beatbox.databinding.ListItemSoundBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_beat_box.*

class BeatBoxFragment: Fragment() {

    private var beatBox: BeatBox? = null

    companion object
    {
        fun newInstance():BeatBoxFragment
        {
            return BeatBoxFragment()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        beatBox = BeatBox(activity!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentBeatBoxBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_beat_box, container, false)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = SoundAdapter(beatBox?.sounds!!)
        }


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        speed_seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener
            {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    beatBox?.speed = p0?.progress?.toFloat()!!
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                    beatBox?.speed = p0?.progress?.toFloat()!!
                }

            }
        )

    }

    inner class SoundHolder(val binding: ListItemSoundBinding): RecyclerView.ViewHolder(binding.root)
    {
        init {
            binding.viewModel = beatBox?.let { SoundViewModel(it) }
        }
        fun bind(sound: Sound)
        {
            binding?.viewModel?.sound = sound
            binding.executePendingBindings()
        }
    }

    inner class SoundAdapter(val sounds: MutableList<Sound>): RecyclerView.Adapter<SoundHolder>()
    {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
           val inflater = LayoutInflater.from(activity)
            val binding:ListItemSoundBinding = DataBindingUtil.inflate(inflater, R.layout.list_item_sound, parent, false)
            return SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sounds.get(position)
            holder.bind(sound)
        }

        override fun getItemCount(): Int {
            return sounds.size
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        beatBox?.release()
    }
}