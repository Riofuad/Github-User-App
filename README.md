# Dicoding: GitHub User App 📱

<p align="center">
  <img src="https://cdn-icons-png.flaticon.com/512/25/25231.png" alt="GitHub Logo"/>
</p>

An application containing a list of GitHub users from [GitHub REST API](https://docs.github.com/en/rest/quickstart?apiVersion=2022-11-28). In this app, you can search for GitHub users, view their detail pages, view the followers, and the following list of that GitHub account. This app is made for final submission on the "Android Fundamentals" ([Belajar Fundamental Aplikasi Android](https://www.dicoding.com/academies/14)) Dicoding course. This application implements some of the material from the course, i.e:
- Fragment
- Navigation
- Background Thread and Networking (Web API & Retrofit)
- Android Architecture Components (LiveData)
- Include UI Testing using Espresso
- Local Data Persistent (Shared Preferences/Data Store, SQL, Room, Repository, and Injection)

This application is based on API base URL ```https://api.github.com/``` with several routes in use, i.e.:
- ```.../search/users``` for search users
- ```.../users/{login}``` for get the user detail
- ```.../users/{login}/followers``` for get the user followers list
- ```.../users/{login}/following``` for get the user following list</br>

The API required GitHub Token from your account. This application uses Kotlin as a programing language and Android Studio version Electric Eel as a tool for developing the app.

## ⚠ Disclaimer ⚠
This repository is created for sharing and educational purposes only. Plagiarism is unacceptable and is not my responsibility as the author.
