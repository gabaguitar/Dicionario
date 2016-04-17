package com.adroidtest.dicionario;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private final String URL = "http://192.168.25.134/dictionary/api/definition/";

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        final Button searchButton = (Button) view.findViewById(R.id.search_button);
        final EditText searchText = (EditText) view.findViewById(R.id.search_text);


        final TextView resultTitle = (TextView) view.findViewById(R.id.title);
        final TextView resultWord = (TextView) view.findViewById(R.id.word_result);
        final TextView resultDefinition = (TextView) view.findViewById(R.id.definition_result);
        final TextView resultSynonymous = (TextView) view.findViewById(R.id.synonymous_result);
        final TextView resultAntonyms = (TextView) view.findViewById(R.id.antonyms_result);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = searchText.getText().toString();
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(URL + word, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                        view.findViewById(R.id.result_view).setVisibility(View.VISIBLE);
                        setText("synonymous", jsonObject, resultSynonymous);
                        setText("antonym", jsonObject, resultAntonyms);
                        setText("definition", jsonObject, resultDefinition);
                        setText("word", jsonObject, resultWord);
                        setText("title", jsonObject, resultTitle);
                    }

                });
            }
        });


        return view;
    }

    private void setText(String jsonProperty, JSONObject jsonObject, TextView  textView){
        try {
            String value = jsonObject.getString(jsonProperty);
            textView.setText(value);
        } catch (JSONException e) {
            textView.setText("Não encontrado.");
        }
    }

}
