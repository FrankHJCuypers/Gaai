[![maintainer](https://img.shields.io/badge/maintainer-Frank%20HJ%20Cuypers-green?style=for-the-badge&logo=github)](https://github.com/frankhjcuypers)
[![GitHub Discussions](https://img.shields.io/github/discussions/FrankHJCuypers/Gaai?style=for-the-badge&logo=github)](https://github.com/FrankHJCuypers/Gaai/discussions)

[![android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://www.android.com/)
[![android studio](https://img.shields.io/badge/Android_Studio-3DDC84?style=for-the-badge&logo=android-studio&logoColor=white)](https://developer.android.com/studio)
[![kotlin](    https://img.shields.io/badge/Kotlin-B125EA?style=for-the-badge&logo=kotlin&logoColor=white)](https://developer.android.com/kotlin)
[![compose](https://img.shields.io/badge/Jetpack-Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/compose)

[![alpha](https://img.shields.io/badge/version-experimental-red)](https://kotlinlang.org/docs/components-stability.html#stability-levels-explained)

[![github release](https://img.shields.io/github/v/release/FrankHJCuypers/Gaai?logo=github)](https://github.com/FrankHJCuypers/Gaai/releases)
[![github release date](https://img.shields.io/github/release-date/FrankHJCuypers/Gaai)](https://github.com/FrankHJCuypers/Gaai/releases)
[![GitHub License](https://img.shields.io/github/license/FrankHJCuypers/Gaai)](LICENSE)
[![Gitea Last Commit](https://img.shields.io/gitea/last-commit/FrankHJCuypers/Gaai)](https://github.com/FrankHJCuypers/Gaai/commits)
[![github contributors](https://img.shields.io/github/contributors/FrankHJCuypers/Gaai)](https://github.com/FrankHJCuypers/Gaai/graphs/contributors)
[![github commit activity](https://img.shields.io/github/commit-activity/y/FrankHJCuypers/Gaai?logo=github)](https://github.com/FrankHJCuypers/Gaai/commits/main)

# Nexxtender Home android app

The goal of this app is to control the *Nexxtender Home* charger without requiring the
[Android Nexxtmove](https://play.google.com/store/apps/details?id=com.powerdale.nexxtender) app.
The Android Nexxtmove app is the official app for controlling the *Nexxtender Home* charger and for syncing
billing information to the [Nexxtmove.me](https://www.nexxtmove.me/) website for refunding.
Both the Nexxtmove app and the Nexxtmove.me website require a username and password to login.
Without it, it is impossible to use the Nexxtmove app and therefore impossible to operate the *Nexxtender* Home charger,
which has no UI or input device on its own.
If you no longer have a valid username and password for the Nexxtmove app, using Gaai is an option.
An other alternative is the
[ESPHome BLE Client for Powerdale Nexxtender EV Charger](https://github.com/geertmeersman/nexxtender).

## Gaai?

Choosing a short distinctive name for a project is always difficult.
A bird name was chosen.
[Gaai](https://nl.wikipedia.org/wiki/Gaai) is Dutch for the
[Eurasian jay](https://en.wikipedia.org/wiki/Eurasian_jay).

# Features

- Only requires to install *Gaai*.
- No additional hardware required.
- Connects over [Bluetooth Low Energy (BLE)](https://en.wikipedia.org/wiki/Bluetooth_Low_Energy) to the
  *Nexxtender Home*.
- Finds and connects to the device based on its PN and SN. No MAC or PIN required.
- Shows real time data from the *Nexxtender Home*: device information, charging status, current, power consumption, ...
- Allows to control the *Nexxtender Home*: start and stop the charger in ECO or MAX mode,
  sync the time with the mobile phone.
- Allows to configure the *Nexxtender Home*: default charging mode, max grid and max device current, off-peak hours.
- Remembers the PN and SN of multiple devices.

# Development

This app is developed in [Kotlin](https://developer.android.com/kotlin) with
[Android Studio](https://developer.android.com/studio) using

- [Jetpack Compose](https://developer.android.com/develop/ui/compose)
- [Kotlin coroutines](https://developer.android.com/kotlin/coroutines)
- [Kotlin BLE Library for Android](https://github.com/NordicSemiconductor/Kotlin-BLE-Library)

## Install Gaai using Android Studio on Windows

*Gaai* can be installed on your Android phone using Android Studio.
Follow these steps:

1. Install the 64-bit version of [Git](https://git-scm.com/downloads/win).
2. Install [Android Studio](https://developer.android.com/studio/install)
3. In Android Studio select the menu item *File* -> *Settings* -> *Version Control* -> *Git* and make sure that the
   *Path to Git executable* points to the git.exe that you just installed.
4. [Clone GitHub Gaai project in Android Studio](https://www.geeksforgeeks.org/how-to-clone-android-project-from-github-in-android-studio/).
   The required URL can be found by clicking on the ![Green Code>](docs/images/GreenCode.png)
   button at the top of this page.
   You might require a GitHub login to be able to clone a repo.
   If you plan on contributing to *Gaai*, you better first fork the repo in GitHub and use the URL of your fork in
   Android Studio.
   Under *Directory* choose a directory where you want the project to be stored.
   Press the *Clone* button.
5. In Android Studio [Connect to your device using Wi-Fi](https://developer.android.com/studio/run/device#wireless).
6. [Build and run Gaai](https://developer.android.com/studio/run)
   This might take some while the first time because Android Studio will also download all dependencies.

## Install Gaai using APK file

An APK file generated from Gaai can be installed as described in
[How to install third-party apps without the Google Play Store](https://www.androidauthority.com/how-to-install-apks-31494/)

## Install Gaai using Google Play Store

Once *Gaai* is sufficiently stable, distributing it via Google Play Store is an option to consider.
For the moment it is not available.

# Getting Started

## Bluetooth

*Gaai* uses BLE to connect to the *Nexxtender Home*,
so make sure that Bluetooth is enabled on your mobile device.

In addition, *Gaai* requires some Bluetooth related permissions in order to function.
When *Gaai* is ran for the first time, it will ask for the required permissions.
In that case, please press the "Request permissions" button, and then press "Allow".
If you press "Don't Allow", *Gaai* will insist that it needs the permissions to continue,
but recent Android versions only allow an app to ask only once for permissions.
The only way out in that case is to go to Android Settings -> Apps -> Gaai -> Permissions.
In the "Not Allowed" section you will find the permission "Nearby devices":
click on it and press "Allow". You now have granted the permission.

Depending on the Android version, the following permissions are required:

+ Up to and including Android 11 (SDK <= 30): `android.permission.ACCESS_FINE_LOCATION`
+ Starting from Android 12 (SDK >= 31): `android.permission.BLUETOOTH_SCAN` and `android.permission.BLUETOOTH_CONNECT`

Note that permission naming is confusing in Android!
Although the *Gaai* application code asks for the permissions `BLUETOOTH_SCAN` and `BLUETOOTH_CONNECT`
in Android 12 and up:

+ The Android pop-up where you have to allow the permissions, will ask
  "Allow *Gaai* to find, connect to, and determine the relative position of nearby devices."
+ In Android Settings -> Apps -> Gaai -> Permission the permission is named
  "Nearby Devices permission".

## Bluetooth pairing

The first time that*Gaai* connects to a *Nexxtender Home* device,
*Gaai* will show a pop-up asking to enter the 6-digit PIN.
After entering the PIN, pairing completes.
See [Add a Nexxtender Home device](#add-a-Nexxtender-Home-device) for the PIN value.

If the mobile phone user later removes the *Nexxtender Home* device from the "Bluetooth" menu in Android,
the pairing information is lost and *Gaai* will not automatically open the pairing dialog again.
The only options to restore from this situation are either of:

- pair the device from the Android "Bluetooth menu".
- delete the *Nexxtender Home* from *Gaai* and then add it again.
  *Gaai* will now again ask to pair.

## First start

When *Gaai* is started the first time, it has no *Nexxtender Home* devices in its database yet.
*Gaai* will display a home screen like the following:

![Empty DB](docs/images/EmptyDB.png)

At the bottom right is a button with a "+" that allows to add a *Nexxtender Home* device.

## Add a Nexxtender Home device

When tapping the "+" in the home screen, the following dialog box appears:

![Device Entry Empty](docs/images/DeviceEntryEmpty.png)

The *Scan device* button stays grayed out until a valid PN and SN are entered.
The PN and SN of your *Nexxtender Home* can be found at the bottom of your device,
as is shown in

![serial](https://github.com/geertmeersman/nexxtender/blob/main/images/serial.png).

When a valid PN and SN are entered, the *Scan Device* button become active.

![Device Entry Empty](docs/images/DeviceEntryCorrectSNPN.png)

Make sure that you are close to the *Nexxtender Home* and that no other device is connected with it over BLE.
Tap the *Scan Device* button. Scanning will start and the button will now show *Cancel Scanning* allowing you to
cancel the scan if it takes to long.

![Device Entry Empty](docs/images/DeviceEntryScanning.png)

If you entered the correct PN and SN,
*Gaai* should find the *Nexxtender Home* almost immediately and show the following screen.

![Device Entry Empty](docs/images/DeviceEntryFound.png)

Gaai shows a card with the details of the found device.

+ At the top left is the PN.
+ At the top right is the SN.
+ At the bottom left is the MAC.
+ At the bottom right is the Service Data used to find the BLE device based on its advertisement packet.
+ At the bottom center is the PIN to use when pairing with this device.

Press *Save* to save this device in *Gaai's* database.
This will bring you back to the previous screen, now showing one device.

If the device that you scanned was already in *Gaai's* database, you will see

![Device Entry Empty](docs/images/DeviceEntryDuplicate.png)

*Gaai* will not let you create duplicates.

## List of Devices

As soon as the database contains at least one device, *Gaai* shows a list of all registered devices at startup as
shown in the next picture.

![Device Entry Empty](docs/images/HomeScreenThreeDevices.png)

In this screen you can

+ Add another device by clicking on the "+" button at the bottom right.
+ Remove a device by swiping its card to the right.
+ Connect to the device by clicking on its card.
  This will open the [Device details](#device-details) screen.
  Make sure that you are close to the *Nexxtender Home* and that no other device is connected with it over BLE.

## Device details

When clicking on a device card in [List of Devices](#list-of-devices),
*Gaai* tries to connect to the device and shows the following screen.

![Device Detail](docs/images/DeviceDetailScreen.png)

The complete screen is larger then the phone's screen.
You must scroll up to see the other parts.
When the screen opens, only the labels are shown without data values.
The values are populated as soon as *Gaai* has established the connection and read all the data.
That can take 5 seconds.
If no data appears after a few seconds,
it might be that another BLE client is still connected to the device.
It could also be a problem in *Gaai*.
It is normally solved by going back to the previous screen using the left arrow at the top left of the screen,
and try again.

The card on the top is the same one as from the [List of Devices](#list-of-devices).

The next cards show the device name and the Device Information.
These contain general BLE information, not specific for *Nexxtender Home*.

The next cards contain specific *Nexxtender Home* information as reported by the device.

### Basic Data

The Basic Data card shows Basic *Nexxtender Home* charging data.

![Basic Data Card](docs/images/BasicDataCard.png)

### Grid Data

The Grid Data card shows Grid Data as measured by the *Nexxtender Home*.

![Grid Data Card](docs/images/GridDataCard.png)

### Car Data

The Car Data card shows Car Data as measured by the *Nexxtender Home*.

![Car Data Card](docs/images/CarDataCard.png)

### Advanced Data

The Advanced Data card shows Advanced Data as measured by the *Nexxtender Home*.

![Advanced Data Card](docs/images/AdvancedDataCard.png)

### Configuration

The Configuration card shows the data that can be configured in the *Nexxtender Home*.

![Configuration Card](docs/images/ConfigurationCard.png)

The fields "Min Device" and "iCapacity" are only shown when the Nexxtender Home firmware version is at least 3.50.
The fields "Max Device" and "Network Type" are only shown when the Nexxtender Home firmware version is at least 1.1.0.
In theory all these fields are configurable and can be changed.
*Gaai* only allows to change the fields marked with ![edit outline](docs/images/mdi--edit-outline.png).
For the fields "Safe" and "Network Type" *Gaai* does not implement the option to change the values,
as it probably not wise to change these values if you don't know what you are doing.
Changing the values of "Min Device" and "iCapacity" is also not supported by *Gaai*;
it is insufficiently clear what they are and if it is safe to change them.

Clicking on ![edit outline](docs/images/mdi--edit-outline.png) next to "Default mode" gives the following dialog screen:

![Default mode dialog](docs/images/DefaultModeDialog.png)

Select the required default charging mode and press OK to confirm.
OK is grayed out if the choice is still "Unknown".
Pressing Cancel does not change anything.

Clicking on ![edit outline](docs/images/mdi--edit-outline.png) next to "Max Grid" or "Max Device"
gives the following dialog screen:

![Ampere Slider Dialog](docs/images/AmpereSliderDialog.png)

Select the required current value in Amperes using the slider and press OK to confirm.
Pressing Cancel does not change anything.

Clicking on ![edit outline](docs/images/mdi--edit-outline.png) next to "Weekdays" or "Weekend"
gives the following dialog screen:

![Tou Period Dialog](docs/images/TouPeriodDialog.png)

Change both Start and End times by clicking ![edit outline](docs/images/mdi--edit-outline.png) next to it.
A time picker dialog opens to select a time:

![Tou Time Picker Dialog](docs/images/TouTimePickerDialog.png)

After changing the Start and End times, press OK to confirm.
Pressing Cancel does not change anything.

### Time

The Time card allows to sync the time of the *Nexxtender Home* with the time on the phone.

![Time Card](docs/images/TimeCard.png)

Clicking "Get Time" reads the current time from the *Nexxtender Home*.

![Time Card Get Time](docs/images/TimeCardGetTime.png)

Clicking "Sync Time" writes the current phone time to the *Nexxtender Home*.

### Loader

The Loader card allows to start or switch the *Nexxtender Home* to charge in a specific mode or to stop it.

![Loader Card](docs/images/LoaderCard.png)

Click on a button and the *Nexxtender Home* immediately switches to the corresponding state.

# Links

Useful information can be found at

- [Nexxtender Home - Information, Frank HJ Cuypers](https://drive.google.com/file/d/13jiP2eNxM1pQ_JHfogX7eo2q3q1o2YCM/view?usp=drive_link)
- [ESPHome BLE Client for Powerdale Nexxtender EV Charger](https://github.com/geertmeersman/nexxtender)
- [Nexxtmove for Home Assistant](https://github.com/geertmeersman/nexxtmove)
- [Nexxtender Home Bluetooth Controller](https://github.com/toSvenson/nexxtender-ble)
- [Discord chat](https://discord.gg/PTpExQJsWA) related to
  [ESPHome BLE Client for Powerdale Nexxtender EV Charger](https://github.com/geertmeersman/nexxtender)
  and [Nexxtmove for Home Assistant](https://github.com/geertmeersman/nexxtmove)

# License

This project is licensed under the GNU AGPLv3 License. See the [LICENSE](LICENSE) file for details.

# Acknowledgements

- [ESPHome BLE Client for Powerdale Nexxtender EV Charger](https://github.com/geertmeersman/nexxtender)
  showing how to address the Nexxtender Home.
- [NordicSemiconductor Kotlin-BLE-Library uiscanner](https://github.com/NordicSemiconductor/Kotlin-BLE-Library/tree/main/uiscanner)
  for the BLE library.

# Disclaimer

The developer

- has no link with Powerdale nor Diego.
- developed this app without prior knowledge of most of the components and tools used
  developing it: Android app development, Android Studio, Gradle, Kotlin, Jetpack compose, BLE, ...
- did not see any perturbing interactions with the *Nexxtmove* app.
  However, if you still need the *Nexxtmove* app to upload charging information (for reimbursement or others),
  be very careful as there is no guarantee that it will not interfere.

*Gaai*

- is only tested on a Google 6 Pro with API 34 (Android 14).
  Basic scenarios seems to work on it, but further testing is required for other scenarios.
  In theory it should work from API 26 (Android 8), but that was not tested.
  Let me know if it works or not on other APIs than 34.
- is only tested with a *Nexxtender Home* with Hardware version A2 and Firmware version 2.53.2.

At this point *Gaai* should be considered an experimental app, for research purposes only.
It is currently not suited for the layman, but only for those willing/knowledgeable to spend the necessary debugging
time.

If despite the preceding warnings, you decide to use *Gaai*, that is on your own responsibility.

# Support

For support, questions, or feedback, please open an issue on
the [GitHub repository](https://github.com/FrankHJCuypers/Gaai/issues/new).
