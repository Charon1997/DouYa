package nexuslink.charon.douya.ui.provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Charon on 2017/7/11.
 */

public class SearchProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY="nexuslink.charon.douya.ui.provider.SearchProvider";
    public final static int MODE=DATABASE_MODE_QUERIES;

    public SearchProvider(){
        super();
        setupSuggestions(AUTHORITY, MODE);
    }
}
