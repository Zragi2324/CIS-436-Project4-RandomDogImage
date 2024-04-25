package com.example.p4randomimagegenerator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.p4randomimagegenerator.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {


    private var _binding: FragmentSecondBinding? = null
    private var imageUrl: String? = null



    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var imageView: ImageView

    companion object {
        // Function to create a new instance of SecondFragment with imageUrl as an argument
        fun newInstance(imageUrl: String): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putString("imageUrl", imageUrl)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = binding.displayDog

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.imageUrl.observe(viewLifecycleOwner, Observer {
            imageUrl ->
            Glide.with(this)
                .load(imageUrl)
                .into(imageView)
        })
/*
        binding.button.setOnClickListener{
            viewModel.fetchRandomImage(requireContext())
        }
*/
    }

    fun updateImageUrl(imageUrl: String){
        this.imageUrl = imageUrl

        view?.let{
            imageView = it.findViewById(R.id.displayDog)
            Glide.with(requireContext())
                .load(imageUrl)
                .into(imageView)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}