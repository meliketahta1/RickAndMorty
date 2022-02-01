package com.example.rickandmorty.presentation.characterlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharacterListBinding
import com.example.rickandmorty.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ViewWithFragmentComponent


@AndroidEntryPoint
class CharacterListFragment : BaseFragment() {

    private val viewModel : CharactersViewModel by viewModels()
    private lateinit var binding: FragmentCharacterListBinding

    private lateinit var characterListAdapter: CharacterListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentCharacterListBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchCharacters()
        initRecyclerViewAdapter()
        characterListAdapter.setOnClickListener {
            val bundle = Bundle().apply {
                it.id?.let { it1 -> putInt("itemCharacterId", it1) }
            }
            findNavController().navigate(
                R.id.action_characterListFragment_to_characterDetailFragment,
                bundle
            )
        }
        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.charactersSuccesState.observe(viewLifecycleOwner){
            binding.rvCharacterList.visibility=View.VISIBLE
            characterListAdapter.differ.submitList(it.results)


        }
        viewModel.characterLoadingState.observe(viewLifecycleOwner){loading->
           if(loading){
               binding.pbMainPage.visibility=View.VISIBLE
               binding.rvCharacterList.visibility=View.GONE
           }else{
               binding.pbMainPage.visibility=View.GONE
           }
        }
        viewModel.characterErrorState.observe(viewLifecycleOwner){error->
            when(error){
                true->{
                    binding.pbMainPage.visibility=View.GONE
                    Toast.makeText(requireContext(),"Error",Toast.LENGTH_LONG).show()
                }
            }

        }
    }
    private fun initRecyclerViewAdapter() {
        characterListAdapter = CharacterListAdapter()
        binding.rvCharacterList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = characterListAdapter
        }
    }


}