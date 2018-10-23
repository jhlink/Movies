### Introduction

### Movies Pt. 2 ---

## Notes

- Room, Repository, LiveData, ViewModels were utilized to persist data. In regards to the Favorite's database, only the movie title, movie id, release data, poster path, vote average, and plot synopsis were stored. The videos and reviews are retrieved solely through an API call. As a result, when opening the MovieDetails activity without network access, both the video and review recycler views will not be populated with any data.  

- There is a bit of code repetition in that multiple recycler views were used. Incorporating Fragments should condense or eliminate this substantially.. 

### Movies Pt. 1 ---
## Instructions

1. In order to ensure functionality of the Movies (Pt. 1) app, a file called *ENV_KEYS* must be created in the root project directory with the following text. 

`MOVIE_KEY = [_apiKey_]` 

2. In effect, the `ENV_KEYS` file should be in the same directory as the `app` directory, as noted in the `build.gradle` file when reviewing the `getKey` function. 

## Notes

- There may be some lag or frames that drop when switching between the two sort criteria. While this isn't ideal, there are definitely optimizations around this, which I do plan ( hopefully ) for Pt. 2. 

- When executing lint within the Android Studio IDE, there are some warnings that were ignored, such as "FrameLayout" being replaced with the `<merge>` tag or `AllowBackup/FullBackupContent` issues. For the purposes of the project, these warnings have been ignored.  

- Instead of using AsyncTask and HttpUrlConnection, OkHttp was used to make an asynchronous GET request to obtain all the necessary data specified in the the project rubric. It should be noted that when an OkHttp network request completes, a Runnable is utilized to apply UI updates as needed.
