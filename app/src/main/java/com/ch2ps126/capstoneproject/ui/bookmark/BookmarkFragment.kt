package com.ch2ps126.capstoneproject.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ch2ps126.capstoneproject.R
import com.ch2ps126.capstoneproject.databinding.FragmentBookmarkBinding
import com.google.android.material.search.SearchBar
import kotlinx.coroutines.launch

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private lateinit var searchBar: SearchBar
    private lateinit var bookmarkAdapter: BookmarkAdapter

    private val viewModel by viewModels<BookmarkViewModel> {
        BookmarkViewModelFactory.getInstance(requireActivity())
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.rvBookmarkTool.layoutManager = GridLayoutManager(requireContext(), 2)
        bookmarkAdapter = BookmarkAdapter()
        binding.rvBookmarkTool.adapter = bookmarkAdapter

        lifecycleScope.launch {
            viewModel.getAllBookmark()
            viewModel.bookmarkData.observe(viewLifecycleOwner) { equipmentResponse ->
                equipmentResponse?.let {
                    bookmarkAdapter.submitList(it)
                }
            }
        }

        with(binding) {
            searchBar.overflowIcon =
                ContextCompat.getDrawable(requireContext(), R.drawable.sort)
            searchView.setupWithSearchBar(this.searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    val searchText = searchView.text.toString()
                    searchBar.setText(searchText)
                    searchView.hide()
                    viewModel.searchBookmark(searchText)
                    false
                }
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllBookmark()
    }

}
