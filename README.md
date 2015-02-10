# Toucan Tutoring - Android Client

Here is how the project structure looks:

<pre>
app
│   
├── java
│   └── me
│       └── toucantutor
│           └── toucan
│               ├── locationdata
│               ├── tasks
│               ├── util
│               └── views
│                   └── HomeScreenActivity.java
└── res
    ├── drawable
    ├── drawable-hdpi
    ├── drawable-mdpi
    ├── drawable-xhdpi
    ├── drawable-xxhdpi
    ├── layout
    │   └── activity_home_screen.xml
    ├── menu
    │   └── menu_home.xml
    ├── values
    │   ├── dimens.xml
    │   ├── strings.xml
    │   └── styles.xml
    └── values-w820dp
        └── dimens.xml
</pre>

The most important folders here are:

+ _res_
    + Place any image in _&lt;app_root&gt/imageScripts/img;_ and execute the following from _&lt;app_root&gt/imageScripts/_
  to create resized images and place in the appropriate folders: <br/> ```$ python script.py ```
    + Place constant string values in values/strings.xml
    + Place activity and fragment layouts in layout


+ _src/main_ __me.toucantutor.toucan__ package
    + __api__ package will include any documentation needed to work with API
  as well as examples of the responses received from using the API

    + __tasks__ will hold any AsyncTasks that may be needed throughout the app,
  such as tasks to fetch images from URLS, load data from the Network, etc

    + __locationdata__ is responsible for finding a user's current location,
  previous location, and determining which location strategy to use to get an
  accurate location to use to determine nearby tutors.

    + __views__ will hold Java classes that directly relate to what a user will
  see, such as a SplashScreen, a HomeScreen, LoginScreen, etc. Will hold Activities
  or also Fragments and any needed helpers.

    + __util__ will hold miscellaneous utilities we may need such as classes to
  to convert between XML and JSON.


#### To Do:

 + Use the Navigation Drawer properly on each view page
 + Get Location and update every 300m or so
 + Show that app is active vs closed (need to know when tutor is actively available for tutoring)
 + Create Login/Logout/Signup screens
 + Show list of tutors and sort by reviews, price, etc

