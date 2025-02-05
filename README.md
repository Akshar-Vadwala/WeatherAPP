# WeatherApp - Android

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Description

WeatherApp is an Android application that provides real-time weather data using AWS IoT Core. It retrieves sensor data, such as temperature, humidity, precipitation, and pressure, and displays it in an intuitive user interface. The app allows users to create and manage their profiles, ensuring personalized weather insights.

### Features:
- Real-time weather updates (Temperature, Humidity, Precipitation, Pressure)
- User authentication and profile management
- Data retrieval from AWS IoT Core using MQTT
- Interactive UI with CardView and ConstraintLayout
- Editable profile section

## Tech Stack

- **Language:** Java
- **UI Components:** XML (ConstraintLayout, CardView)
- **Database:** Android SQLite
- **Cloud Services:** AWS IoT Core
- **Messaging Protocol:** MQTT
- **Development Environment:** Android Studio

## Demo Video

![](/app_demo.gif)

## Usage

### 1. Clone this repository:

```bash
git clone https://github.com/Akshar-Vadwala/WeatherAPP.git
```

### 2. Open the project in Android Studio
- Import the project and sync Gradle dependencies.
- Ensure you have the required SDKs installed.

### 3. Configure AWS credentials
- Set up AWS IoT Core and obtain necessary credentials.
- Update your `AWSIoTManager` class with endpoint details.

### 4. Run the application
- Connect a physical device or use an emulator.
- Build and deploy the app from Android Studio.

## Contribution

Contributions are welcome! You can help by:
- Reporting bugs and suggesting feature improvements.
- Enhancing UI/UX for better user experience.
- Optimizing MQTT communication for real-time updates.
- Improving AWS integration and security.

Feel free to fork the repository, make your changes, and submit a pull request.

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT). See the [LICENSE](LICENSE) file for details.

