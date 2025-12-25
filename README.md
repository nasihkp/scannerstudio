# How to Run the Scanner App

Since this project was generated file-by-file, it does not yet have the Gradle Wrapper (`gradlew`) scripts pre-generated. The easiest way to run it is using **Android Studio**.

## Option 1: Android Studio (Recommended)

1.  **Open Android Studio**.
2.  Select **File > Open** (or "Open" from the welcome screen).
3.  Navigate to this directory: `C:\Users\nasih\scanner`.
4.  Select the `scanner` folder (or the `build.gradle` file inside it) and click **OK**.
5.  **Wait for Sync**: Android Studio will detect the Gradle configuration and start syncing. It might ask to download the required Gradle version or Android SDKs. Allow it to do so.
6.  **Connect a Device**:
    *   Connect your Android phone via USB (Enable USB Debugging).
    *   OR create an Emulator in Device Manager.
7.  **Run**: Click the green **Run** (Play) button in the toolbar (or press `Shift + F10`).

## Option 2: Command Line (If you have Gradle installed)

If you have a standalone `gradle` installed on your system:

1.  Open a terminal in this directory.
2.  Run:
    ```bash
    gradle installDebug
    ```
3.  This will build the APK and install it on a connected device.

## Troubleshooting

*   **SDK Location**: If the build fails saying "SDK location not found", create a file named `local.properties` in this root directory with the path to your SDK:
    ```properties
    sdk.dir=C:\\Users\\nasih\\AppData\\Local\\Android\\Sdk
    ```
    *(Note: Adjust the username if different)*

*   **Permissions**: On Android 6.0+, you will be asked for Camera permissions at runtime. Make sure to grant them.
