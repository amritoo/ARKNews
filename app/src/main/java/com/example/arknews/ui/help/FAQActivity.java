package com.example.arknews.ui.help;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arknews.R;
import com.example.arknews.model.FAQ;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class FAQActivity extends AppCompatActivity {

    private ArrayList<FAQ> faqArrayList;

    RecyclerView recyclerView;
    FaqAdapter adapter;

    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        toolbar = findViewById(R.id.manual_toolbar);
        recyclerView = findViewById(R.id.faq_rv);
        toolbar.setNavigationOnClickListener(v -> finish());

        populateList();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FaqAdapter(faqArrayList);
        recyclerView.setAdapter(adapter);

    }

    void populateList() {
        faqArrayList = new ArrayList<>();
        faqArrayList.add(new FAQ("1. How is ArkNews different from other news application?",
                "ArkNews is a mini Project developed by the students that focus on giving a platform for Ad-free and faster news to the readers."));

        faqArrayList.add(new FAQ("2. What are the additional/special features in ArkNews?",
                "ArkNews provides  the following features:\n" +
                        "- Multiple Language features\n" +
                        "- Dark/Light mode\n" +
                        "- Favorite feature\n" +
                        "- Auto delete \n" +
                        "- News History\n" +
                        "- Rate us"));

        faqArrayList.add(new FAQ("3. The articles appear to be outdated - How can I retrieve the latest news?",
                "You can refresh/update the content by pulling down from the top of the page"));

        faqArrayList.add(new FAQ("4. How to share News articles and photos?",
                "To share the news you can simply click on the share icon."));

        faqArrayList.add(new FAQ("5. How to save the news to read later?",
                "You can use the pin news to save it and read later"));

        faqArrayList.add(new FAQ("6. How to turn on/off notifications on Arknews?",
                " Go to setting \n" +
                        "- click notification \n" +
                        "- select on/off"));

        faqArrayList.add(new FAQ("7. Can we read the news offline or in air-plane mode?",
                "you can read the previously loaded news but cannot read or load the latest news."));

        faqArrayList.add(new FAQ("8. Does the ArkNews cost anything?",
                "ArkNews News does not charge for the application at this time.\n" +
                        "Please note that in the absence of a Wi-Fi connection, the News application for Android uses your phone's cellular network connection to access and download content."));

        faqArrayList.add(new FAQ("9. Does ArkNews provide Dark/Light mode?",
                "Yes, it provides the light/dark mode. to access it:\n" +
                        "- Go to settings\n" +
                        "- Click on Appearance\n" +
                        "- select the desired mode"));

        faqArrayList.add(new FAQ("10. How can we add favourite channel?",
                "ArkNews provide readers to choose the type of news they would want to read.\n" +
                        " Go to the Favorited channels and select your favourite news channels,\n" +
                        " the news feed will be accordingly updated."));

        faqArrayList.add(new FAQ("11. Which devices are supported by the ArkNews?",
                "The ArkNews Android application is a mobile application built specifically for Android phones with software version 6.0 and up"));

        faqArrayList.add(new FAQ("12. How can i send feedback to ArkNews about the application?",
                "you can send the feedback by rating the news application\n" +
                        "- you can also use the 'contact us' to send the feedback"));
    }
}
