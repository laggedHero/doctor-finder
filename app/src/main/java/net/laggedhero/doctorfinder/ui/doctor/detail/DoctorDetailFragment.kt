package net.laggedhero.doctorfinder.ui.doctor.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import net.laggedhero.doctorfinder.R
import net.laggedhero.doctorfinder.databinding.FragmentDoctorDetailBinding
import net.laggedhero.doctorfinder.extensions.toDoctor

// TODO: this was rushed...
class DoctorDetailFragment : Fragment() {

    private val args: DoctorDetailFragmentArgs by navArgs()

    private var _binding: FragmentDoctorDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoctorDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val doctor = args.doctor.toDoctor()

        binding.fragmentDoctorDetailName.text = doctor.name.value
        binding.fragmentDoctorDetailAddress.text = doctor.address.value
        Glide.with(binding.root)
            .load(doctor.photo?.value)
            .placeholder(R.mipmap.ic_launcher_round)
            .circleCrop()
            .into(binding.fragmentDoctorDetailImage)
    }
}