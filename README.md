
# EHTS Bias Detector Watch
An application that aims to detect and raise awareness of implicit bias within users by
collecting health data -Sp02 and heart rate-

# Tech Stack
- Source Code (`Kotlin`): SRC is composed of `Kotlin`, a language similar to java with more type safety
- Watch UI `(Jetpack Compose)`: `Compose` is a more modern practice for designing UI
- Data Flow `(Firebase)`: Data from the watch is sent to `Firebase`, a backend platform
- Build `(Gradle)`: `Gradle` is an automation tool that handles building your application

<img width="150" height="150" alt="Image" src="https://github.com/user-attachments/assets/f36cbc42-176c-4d7e-983e-978c283db977" />

<img width="150" height="150" alt="Image" src="https://github.com/user-attachments/assets/fa8ff80c-124d-4bbd-b90c-b5b75f536708" />

<img width="150" height="150" alt="Image" src="https://github.com/user-attachments/assets/0b9a0db6-f990-4fd6-aefa-6c0f5e6d5c54" />

# Launching the App

1) Clone the Repo

If you haven't already, download Android Studio, clone the repository, and allow a few moments
for Gradle to import all the necessary modules. You'll know everything's ready when the drop down
menu on the top-left changes from `Project` to `Android`

2) Rebuild and Sync with Gradle

Run "rebuild project" under the `Build` drop down menu. **This probably will result in errors**.
Go to Android -> Gradle Scripts -> build.gradle.kts (Module : Watch) and ensure the compile SDK is `35`

Also, switch back to the `Project` view, find "local.properties", and change the path name from
`sdk.dir=/Users/musashoeb/Library/Android/sdk` to whatever your local device is called

Rebuild again, Re-Sync gradle again, and keep troubleshooting. Getting started admittedly takes a while

3) Running the App on Android Studio

You should be able to click on the launch icon atop the screen and view the app running. If not,
you'll need to add a device to the simulator, as well as add a configuration

`Adding a device`: Go to the device manager on the right side of the screen and
add a new device under "Wear OS". The specifications don't really matter.

`Adding a configuration` : Click on the three dots near the debugging icon to view configurations.
If one doesn't exist, add a new one as an "Android App" and set the module to BiasDetectorProject.watch

Note: If you interact with UI that measures health data, it is not going to work as you're using a
simulator, you should get that popup message. To view  actual results, rely on the physical device.

# Using the App

1) You'll first be greeted by the `Landing Page`. Click on `Get Started` to continue to testing.

2) You are now on the `Select User Page`. Swipe horizontally to navigate to the desired user, and click `Start Test`

3) The next screen is the `Oxymeter Activity Page`. This measures the one-instance blood oxygen level of a user
    * It is normal to have an oxymeter reading of 95-100. Something lower might indicate a medical issue
    * To start measuring, click on `Measure`. Wait about 10 seconds for data to appear on the screen. 
    * NOTE: Oxymeter is VERY finnicky. Ensure you are staying completely still and the watch is secured to your wrist
    * NOTE: If no reading is happening, its better to just hit `Complete` and `Measure` again to restart the process
    * Once a satisfactory reading is given, click on `Continue`

4) The final screen is` Heart Rate Activity Page`. This measures heart rate continuously as long as you need (or until the battery dies)
    * This works similar to oximeter, but is easier to work with. Once you're finished testing, click on `Finish Test`

5) You'll be navigated back to the `Select User Page`. That's all there is to it :)

# Future Improvements
In full transparency, we realized about all the different directions this project
could have taken a little too late. This project is limited by the hardware and features
of the Samsung Galaxy Watch 4. Realistically, you could just use a fitbit to collect
health data over time, especially in situations without wifi, and then focus more of your
efforts on what constitutes bias. I highly encourage you to explore more options on an idea
you want to commit too, the client is open to your ideas!
