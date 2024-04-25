package com.example.p4randomimagegenerator

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.fragment.findNavController
import com.example.p4randomimagegenerator.databinding.FragmentFirstBinding

import kotlin.ClassCastException


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null


    private lateinit var viewModel: MainViewModel


    private val binding get() = _binding!!
    private lateinit var listener: ImageFetchListener


    interface ImageFetchListener{
        fun onImageFetched(imageUrl: String)
    }

    override fun onAttach(context: Context){
        super.onAttach(context)
        try{
            listener = context as ImageFetchListener
        }
        catch (e: ClassCastException){
            throw ClassCastException("$context MUST IMPLEMENT INTERFACE")
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    // when button clicked a dog image is fetched from api
        binding.getRandom.setOnClickListener{
            viewModel.fetchRandomImage(requireContext())
        }
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        // check for changes in imageUrl a livedata object of our viewmodel, when imageUrl changes , onImageFetched is called
        viewModel.imageUrl.observe(viewLifecycleOwner, Observer{
            imageUrl->
            listener.onImageFetched(imageUrl)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}