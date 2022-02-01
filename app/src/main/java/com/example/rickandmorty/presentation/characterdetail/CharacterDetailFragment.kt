package com.example.rickandmorty.presentation.characterdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.rickandmorty.databinding.FragmentCharacterDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_character.view.*

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private lateinit var args: CharacterDetailFragmentArgs
    private lateinit var binding:FragmentCharacterDetailBinding
    private val viewModel: CharacterDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCharacterDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments ?: return
        args =CharacterDetailFragmentArgs.fromBundle(bundle)
        var id =CharacterDetailFragmentArgs.fromBundle(bundle).itemCharacterId
        viewModel.fetchCharacter(id)
        observeLiveData()


    }

    fun observeLiveData(){
        viewModel.characterSuccess.observe(viewLifecycleOwner){
            it?.let {character->
                binding.tvName.visibility=View.VISIBLE
                binding.tvGender.visibility=View.VISIBLE
                binding.tvStatus.visibility=View.VISIBLE

                binding.tvName.text=character.name
                binding.tvGender.text=character.gender
                binding.tvStatus.text=character.status
                Glide.with(requireContext()).load(character.image)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .thumbnail(0.25F).into(binding.ivCharacterImage)

            }
        }
        viewModel.characterLoading.observe(viewLifecycleOwner){loading->
            if(loading){
                binding.pbDetailPage.visibility=View.VISIBLE
                binding.tvName.visibility=View.GONE
                binding.tvGender.visibility=View.GONE
                binding.tvStatus.visibility=View.GONE
            }else{
                binding.pbDetailPage.visibility=View.GONE
                binding.tvName.visibility=View.VISIBLE
                binding.tvGender.visibility=View.VISIBLE
                binding.tvStatus.visibility=View.VISIBLE
            }
        }
        viewModel.characterError.observe(viewLifecycleOwner){error->
            if(error){
                binding.pbDetailPage.visibility=View.GONE
                Toast.makeText(requireContext(),"Error",Toast.LENGTH_LONG).show()
            }
        }
    }
}