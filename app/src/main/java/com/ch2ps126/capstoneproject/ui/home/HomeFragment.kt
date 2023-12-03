package com.ch2ps126.capstoneproject.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.ch2ps126.capstoneproject.R
import com.ch2ps126.capstoneproject.databinding.FragmentHomeBinding
import com.google.android.material.search.SearchBar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var searchBar: SearchBar

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        (activity as AppCompatActivity).supportActionBar?.hide()

        val searchView = binding.searchView
        searchBar = binding.searchBar

        with(binding) {
            searchBar.overflowIcon = ContextCompat.getDrawable(requireContext(), R.drawable.sort)
            searchView.setupWithSearchBar(this.searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    val searchText = searchView.text.toString()
                    searchBar.setText(searchText)
                    searchView.hide()
                    if (searchView.text?.isEmpty() == true) {
                        Toast.makeText(requireContext(), "Jangan Kosong", Toast.LENGTH_SHORT)
                            .show()

                    } else {

                    }
                    false
                }
        }

        searchBar.inflateMenu(R.menu.sort_menu)
        searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_az -> {
                    // Do stuff...
                    Log.d("Menu", "AZ")
                    true
                }
                R.id.menu_za -> {
                    // Do stuff...
                    Log.d("Menu", "ZA")
                    true
                }
                else -> false
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}