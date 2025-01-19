# Grabszan

Welcome to the Grabszan project! This project is developed using Java.

## Table of Contents

- [Introduction](#introduction)
- [Installation](#installation)
- [Authors](#Authors)

## Introduction

**Grabszan** is a project designed to provide an engaging and seamless experience for playing the traditional board game Trylma (also known as Chinese Checkers). This repository hosts two separate applications, each with a distinct purpose:

1. **Trylma**  
   The server-side application responsible for handling the game's logic. It manages game rules, player interactions, and ensures smooth gameplay. Trylma serves as the backbone of the project, enabling players to connect, create game rooms, and participate in matches.

2. **Client-App**  
   The client-side application designed for user interaction. It provides an intuitive interface for players to communicate with the server, create game rooms, and join ongoing matches. This application ensures a user-friendly way to interact with the Trylma server and enjoy the game.

Both components work together to create a fully functional Trylma gaming platform. This is the first version of the project, and it is still under active development.  


## Installation

### Prerequisites
- Make sure you have **Java 21** or a later version installed on your system. The project requires features available only in Java 21 or higher.
- JavaFX 21 or a later version is required as well. Ensure you have the corresponding JavaFX SDK installed to support the necessary JavaFX modules.

### Steps

1. To start using the **Grabszan** project, you need to download the necessary files from the [Releases](https://github.com/BartoszGrab/Grabszan.git) section on GitHub.:
    ### Required Files
    **trylma-2.0.jar**  
        The server application responsible for managing the game logic.
   
    **client-app-2.0.jar**  
        The client application used to interact with the server.

2. Run the server:
    ```sh
    java -jar .\trylma-2.0.jar
    ```

3. Run the client-app (Replace [PATH TO FILE] with the file path.):
    ```sh
    java --module-path "C:\[PATH TO FILE]\javafx-sdk-21.0.5\lib" --add-modules javafx.controls,javafx.fxml -jar .\client-app-2.0.jar
    ```

## Authors
- **Bartosz Grabiński**
- **Michał Szandarowski**