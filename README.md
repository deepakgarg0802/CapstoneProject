# CapstoneProject
Stay updated with trending news from national,regional newspapers and websites. The app gives you the option to bookmark any news so that you can retrieve it later. The app also has option to share the news.

## Intended User
From students to professionals this app is for everyone who wishes to grasp all the news at one place. Feeds of all quality will be catered. Aspirants for government jobs can use it as a source of current affairs.

## Features
* Thumbnails of different sources at the main activity
* Dig a little deeper by browsing particular source
* Details displayed in webview
* Bookmark any article
* Widget to show headline

## Key Considerations

* How will your app handle data persistence?
> A content provider will be used along with sqlite to store data
* Describe any corner cases in the UX.
> There may not be any favourites marked by user. In that case.message should be displayed. Also network access state should be displayed.
* Describe any libraries you’ll be using and share your reasoning for including them.
> Glide to handle the loading and caching of images.
> Volley to Loading data from API
* Describe how you will implement Google Play Services.
> Adwords : Interstitial ad between main and detail activity.
