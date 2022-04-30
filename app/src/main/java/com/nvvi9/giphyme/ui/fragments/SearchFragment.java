package com.nvvi9.giphyme.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.nvvi9.giphyme.R;
import com.nvvi9.giphyme.databinding.FragmentSearchBinding;
import com.nvvi9.giphyme.ui.adapters.SearchSuggestionAdapter;
import com.nvvi9.giphyme.ui.fragments.base.BaseFragment;
import com.nvvi9.giphyme.ui.listeners.SearchSuggestionsListener;
import com.nvvi9.giphyme.ui.listeners.TextChangedListener;
import com.nvvi9.giphyme.ui.viewmodels.SearchViewModel;
import com.nvvi9.giphyme.utils.ViewUtils;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class SearchFragment extends BaseFragment implements SearchSuggestionsListener {

    private static final String TAG = "SearchFragment";

    private SearchViewModel searchViewModel;

    private FragmentSearchBinding binding;

    private SearchSuggestionAdapter searchSuggestionAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater);

        binding.searchToolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(this).navigateUp());

        searchSuggestionAdapter = new SearchSuggestionAdapter(this);

        binding.recyclerView.setAdapter(searchSuggestionAdapter);
        binding.recyclerView.setItemAnimator(null);

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        EditText searchQuery = binding.searchQuery;

        searchQuery.addTextChangedListener(new TextChangedListener(s -> searchViewModel.updateQuery(s)));

        searchQuery.setOnClickListener(v -> {
            searchQuery.requestFocus();
            ViewUtils.showKeyboard(searchQuery);
        });

        searchQuery.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                CharSequence text = v.getText();
                if (text.length() > 0) {
                    navigateToSearchResults(text.toString());
                }
                return true;
            } else {
                return false;
            }
        });

        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        disposable.add(searchViewModel.getSuggestions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        suggestionItem -> searchSuggestionAdapter.submitList(suggestionItem.getSuggestions()),
                        throwable -> Log.e(TAG, "error while receiving suggestions", throwable),
                        () -> Log.d(TAG, "completed")
                ));
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.searchQuery.requestFocus();
        ViewUtils.showKeyboard(binding.searchQuery);
    }

    @Override
    public void onPause() {
        binding.searchQuery.clearFocus();
        ViewUtils.hideKeyboard(binding.searchQuery);
        super.onPause();
    }

    @Override
    public void onItemClicked(String text) {
        binding.searchQuery.setText(text);
        binding.searchQuery.setSelection(text.length());
        navigateToSearchResults(text);
    }

    @Override
    public void onArrowClicked(String text) {
        binding.searchQuery.setText(text);
        binding.searchQuery.setSelection(text.length());
    }

    private void navigateToSearchResults(String query) {
        Bundle b = new Bundle();
        b.putString(SearchResultsFragment.QUERY_KEY, query);
        NavHostFragment.findNavController(this).navigate(R.id.action_searchFragment_to_searchResultsFragment, b);
    }
}
