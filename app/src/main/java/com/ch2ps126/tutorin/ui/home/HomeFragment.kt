package com.ch2ps126.tutorin.ui.home

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ch2ps126.tutorin.R
import com.ch2ps126.tutorin.databinding.FragmentHomeBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.search.SearchBar
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var searchBar: SearchBar
    private lateinit var homeAdapter: HomeAdapter
    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory.getInstance(requireActivity())
    }

    private val selectedChipsLiveData = MutableLiveData<List<String>>()
    private val selectedChips: MutableList<String> = mutableListOf()
    private var muscleTypes: Array<String> = arrayOf()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.rvTool.layoutManager = GridLayoutManager(context, 2)

        homeAdapter = HomeAdapter()
        binding.rvTool.adapter = homeAdapter

        homeViewModel()
        getAllMuscles()

        val searchView = binding.searchView
        searchBar = binding.searchBar

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
                    homeViewModel.setSearchQuery(searchText)
                    false
                }
        }

        searchBar.inflateMenu(R.menu.sort_menu)
        searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_az -> {
                    homeViewModel.setSort("asc")
                    true
                }

                R.id.menu_za -> {
                    homeViewModel.setSort("desc")
                    true
                }

                else -> false
            }
        }

        selectedChipsLiveData.observe(viewLifecycleOwner) { selectedChips ->
            updateChipGroup(selectedChips)
        }

        binding.chipGroupFilter.apply {
            if (selectedChipsLiveData.value.isNullOrEmpty()) {
                addView(createChip("All", true))
            } else {
                selectedChipsLiveData.value?.forEach { chipText ->
                    addView(createChip(chipText, true))
                }
            }
        }

        return root
    }

    private fun homeViewModel() {
        lifecycleScope.launch {
            homeViewModel.getAllEquipment()
            homeViewModel.equipmentData.observe(viewLifecycleOwner) { equipmentResponse ->
                equipmentResponse?.let {
                    homeAdapter.submitList(it)
                    if (it.isEmpty()) {
                        binding.tvNotFound.visibility = View.VISIBLE
                    } else {
                        binding.tvNotFound.visibility = View.GONE
                    }
                }
            }
        }

        homeViewModel.searchQuery.observe(viewLifecycleOwner) {
            homeViewModel.filterEquipment()
        }

        homeViewModel.muscleTypes.observe(viewLifecycleOwner) {
            homeViewModel.filterEquipment()
        }

        homeViewModel.sort.observe(viewLifecycleOwner) {
            homeViewModel.filterEquipment()
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun getAllMuscles() {
        homeViewModel.getAllMuscles()
        homeViewModel.musclesData.observe(viewLifecycleOwner) { muscleResponse ->
            muscleResponse?.let {
                muscleTypes = it.map { muscle ->
                    muscle?.targetMuscleName ?: ""
                }.toTypedArray()
            }
        }
    }

    private fun createChip(chipText: String, isChecked: Boolean): Chip {
        val chip = Chip(requireContext())
        chip.text = chipText
        chip.isCheckable = true
        chip.isChecked = isChecked
        chip.setOnClickListener {
            showChipDialog(muscleTypes)
        }
        return chip
    }

    private fun updateChipGroup(chips: List<String>) {
        binding.chipGroupFilter.removeAllViews()
        chips.forEach { chipText ->
            binding.chipGroupFilter.addView(createChip(chipText, true))
        }
    }

    private fun showChipDialog(chipItems: Array<String>) {
        val dialog = Dialog(requireContext())

        val overlayView = View(requireContext())
        overlayView.setBackgroundColor(Color.parseColor("#80000000"))
        overlayView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.setOnDismissListener {
            (requireActivity().findViewById(android.R.id.content) as ViewGroup).removeView(
                overlayView
            )
        }
        (requireActivity().findViewById(android.R.id.content) as ViewGroup).addView(overlayView)

        dialog.setContentView(R.layout.dialog_menu)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val close = dialog.findViewById<View>(R.id.btn_close)
        val apply = dialog.findViewById<View>(R.id.btn_apply)

        close?.setOnClickListener {
            dialog.dismiss()
        }
        apply?.setOnClickListener {
            selectedChipsLiveData.value = selectedChips.toList()
            if (selectedChips.isEmpty()) {
                selectedChipsLiveData.value = listOf("All")
            }

            val muscleTypes = selectedChips.joinToString(",")
            homeViewModel.setMuscleTypes(muscleTypes)
            dialog.dismiss()
        }
        val chipGroup = dialog.findViewById<ChipGroup>(R.id.chipGroup)

        for (chipItem in chipItems) {
            val chip = Chip(requireActivity())
            chip.text = chipItem
            chip.isClickable = true
            chip.isCheckable = true
            chip.isChecked = selectedChips.contains(chipItem)

            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (!selectedChips.contains(chipItem)) {
                        selectedChips.add(chipItem)
                    }
                } else {
                    if (selectedChips.contains(chipItem)) {
                        selectedChips.remove(chipItem)
                    }
                }
            }

            chipGroup.addView(chip)
        }

        dialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}