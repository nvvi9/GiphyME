package com.nvvi9.giphyme.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.nvvi9.giphyme.R
import com.nvvi9.giphyme.databinding.FragmentSearchBinding
import com.nvvi9.giphyme.ui.adapters.SearchSuggestionAdapter
import com.nvvi9.giphyme.ui.fragments.base.BaseFragment
import com.nvvi9.giphyme.ui.listeners.SearchSuggestionsListener
import com.nvvi9.giphyme.ui.listeners.TextChangedListener
import com.nvvi9.giphyme.ui.viewmodels.SearchViewModel
import com.nvvi9.giphyme.utils.hideKeyboard
import com.nvvi9.giphyme.utils.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class SearchFragment : BaseFragment(), SearchSuggestionsListener {

    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchSuggestionAdapter: SearchSuggestionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        binding.searchToolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
        searchSuggestionAdapter = SearchSuggestionAdapter(this)
        binding.recyclerView.adapter = searchSuggestionAdapter
        binding.recyclerView.itemAnimator = null
        val searchQuery = binding.searchQuery
        searchQuery.addTextChangedListener(TextChangedListener {
            searchViewModel.updateQuery(it)
        })
        searchQuery.setOnClickListener {
            searchQuery.requestFocus()
            searchQuery.showKeyboard()
        }
        searchQuery.setOnEditorActionListener { v: TextView, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val text = v.text
                if (text.isNotEmpty()) {
                    navigateToSearchResults(text.toString())
                }
                true
            } else {
                false
            }
        }
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disposable.add(
            searchViewModel.suggestions
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { (suggestions) ->
                        searchSuggestionAdapter.submitList(suggestions)
                    },
                    { Log.e(TAG, "error while receiving suggestions", it) },
                    { Log.d(TAG, "completed") })
        )
    }

    override fun onResume() {
        super.onResume()
        binding.searchQuery.requestFocus()
        binding.searchQuery.showKeyboard()
    }

    override fun onPause() {
        binding.searchQuery.clearFocus()
        binding.searchQuery.hideKeyboard()
        super.onPause()
    }

    override fun onItemClicked(text: String) {
        binding.searchQuery.setText(text)
        binding.searchQuery.setSelection(text.length)
        navigateToSearchResults(text)
    }

    override fun onArrowClicked(text: String) {
        binding.searchQuery.setText(text)
        binding.searchQuery.setSelection(text.length)
    }

    private fun navigateToSearchResults(query: String) {
        val b = Bundle()
        b.putString(SearchResultsFragment.QUERY_KEY, query)
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_searchFragment_to_searchResultsFragment, b)
    }

    companion object {
        private const val TAG = "SearchFragment"
    }
}