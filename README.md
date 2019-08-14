<img src="https://i.imgur.com/UMPqWDV.gif" title="By Ali Kazi"/>

### Summary ###

* __What was my apporach?__
    - Architecture  
    I chose to go with the latest MVVM architecture for this project. There are a lot of advantages of using this architecture like `ViewModel` survives orientation changes out of the box, the architecture follows SOLID principles and splits reponsibilites to many smaller, maintainable components. I prefer this architecture to MVP. Also, this is the future of Android.
    - UI  
    I added a custom `SnapHelper` that will snap a full item view to the top when the user scrolls. This helps keep a neat look and experience. I also added some animations to make the UI more appealing. The API was quite simple so for the given data, I think I used a lot of it.
    - Android Jetpack  
    I wanted to add Room as a database, but later decided that it would be overkill for this project. The API is always getting updated, so it makes sense to fetch API frequently. In real project, I would use `WorkManager` to schedule API calls in the background to fetch new data and update my database.

* __What libraries did I use and why?__
    - Volley (https://github.com/google/volley)  
    Easy to set up, fast and recommended by Googl in their official documentation. I like the `addToQueue` functionality.

    - Glide (https://github.com/bumptech/glide)  
    One of the better performing image libraries. Supports lazy loading out of the box. Larger than Picasso, but faster.
    
    - gson-xml (https://github.com/stanfy/gson-xml)  
    I am a long time user of Gson library by Google for parsing JSON responses. But in this project, the API returned XML. So I had two options: 1) either parse the XML directly or 2) convert the XML to JSON then parse the JSON via Gson.  
    I did some research and found this handy library. It was a bit confusing how it worked, but I also found that the second option would be much more tedious and error prone task. So I went ahead with this library. Once I figured out how to setup my POJO for the XML parsing to work, the rest of the process was a breeze.
    
    - EqualizerView (https://github.com/gsotti/EqualizerView)  
    Since this project had some audio I could play, I looked for a way to add some visual effects. I found this easy to use library that places a fake animating equalizer which added sufficient visua effect.
    
* __Any known issues?__
    - If you start preview on one song then scroll the `RecyclerView` down, the animations will be lost when you come back to the song.  
    I tried fixing it and almost did, but I got stuck at the challenge where I would need to keep the animations working in background and restore them to the item when user scolls back to it.  
    I would reply on a `boolean` isPlaying on the song model (`PlayoutItem`), and restore the animnating state if the boolean is true. I didn't have enough time to implement this fix unfortunately.

* __What would I do if I had more time?__
    - Fix the known issues. Make the app robust even in edge cases.
    - Add some instrumented tests with Espresso.
    - Add `Room` and `WorkManager` to get scheduled updates.
    - Improve the UI more.

### Repo owner ###

Ali Kazi   
[LinkedIn](linkedin.com/in/mdalikazi)  
