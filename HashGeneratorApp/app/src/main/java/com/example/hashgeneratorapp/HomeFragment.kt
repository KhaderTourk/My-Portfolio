package com.example.hashgeneratorapp

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hashgeneratorapp.databinding.FragmentHomeBinding
import com.example.hashgeneratorapp.models.Model
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var homeViewModel:Model? =null

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        val hashAlgorithms = resources.getStringArray(R.array.hash_algorithms)
        val arrayAdapter = ArrayAdapter(requireContext() , R.layout.drop_down_item , hashAlgorithms)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeViewModel = Model()

        setHasOptionsMenu(true)

        binding.generateButton.setOnClickListener {
            onGenerateClick()
        }

        return binding.root
    }

    private fun onGenerateClick(){
        if(binding.plainText.text.isEmpty()){
            showSnackBar("Field Empty.")
        }else{
            lifecycleScope.launch {
                applyAnimation()

                navigateToSuccess(getHashData())
            }
        }
    }

    private suspend fun applyAnimation(){
        binding.generateButton.isClickable = false
        binding.titleTv.animate().alpha(0f).duration = 400L
        binding.generateButton.animate().alpha(0f).duration = 400L
        binding.textInputLayout.animate().alpha(0f).translationXBy(1200f).duration = 400L
        binding.plainText.animate().alpha(0f).translationXBy(-1200f).duration = 400L

        delay(300)

        binding.successBackground.animate().alpha(1f).duration = 600L
        binding.successBackground.animate().rotationBy(720f).duration = 600L
        binding.successBackground.animate().scaleXBy(900f).duration = 800L
        binding.successBackground.animate().scaleYBy(900f).duration = 800L

        delay(500)

        binding.successImageView.animate().alpha(1f).duration = 1000L

        delay(1500)
    }

    private fun navigateToSuccess(hash:String){
        val directions = HomeFragmentDirections.actionHomeFragmentToSuccessFragment(hash)
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getHashData():String{

        val algorithm = binding.autoCompleteTextView.text.toString()
        val plainText = binding.plainText.text.toString()
        Log.e("error","hashData")
        val hashData = homeViewModel!!.getHash(plainText,algorithm)
        Log.e("error",hashData)
        return hashData

    }

    private fun showSnackBar(message:String){
        val snackBar = Snackbar.make(binding.rootLayout,message,Snackbar.LENGTH_SHORT)
        snackBar.setAction("Okay"){}
        snackBar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
        snackBar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu , menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.clear_menu){
            binding.plainText.text.clear()
            showSnackBar("Cleared.")
        }
        return true
    }
}