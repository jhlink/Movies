### Introduction

## Instructions

1. In order to ensure functionality of the Movies (Pt. 1) app, a file called *ENV_KEYS* must be created in the root project directory with the following text. 

`MOVIE_KEY = [_apiKey_]` 

2. In effect, the `ENV_KEYS` file should be in the same directory as the `app` directory, as noted in the `build.gradle` file when reviewing the `getKey` function. 

## Notes

- There may be some lag or frames that drop when switching between the two sort criteria. While this isn't ideal, there are definitely optimizations around this, which I do plan ( hopefully ) for Pt. 2. 

- When executing lint within the Android Studio IDE, there are some warnings that were ignored, such as "FrameLayout" being replaced with the `<merge>` tag or `AllowBackup/FullBackupContent` issues. For the purposes of the project, these warnings have been ignored.  
