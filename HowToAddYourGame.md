

# Integration of your game on `Bandit Games` platform

This document describes how a new game should be integrated with the platform.
Below you will find different topic and frequently asked questions regarding adding your `board game`.

**⚠️ Please read carefully! This is important.**


## Table of Contents

1. [General information](#general-information)
2. [Explore our infrastructure](#explore-our-infrastructure)
3. [Adding a game flow](#adding-a-game-flow)
4. [Backend configuration](#backend-configuration)
5. [Frontend configuration](#frontend-configuration)

## General information
Welcome to Bandit Games. Here you will find everything you need to know on how to add your board game onto our platform. 

In order to begin your journey, you have emailed us with your request and description of your game.
Email: [bandit-games@mail.com](bandit-games@mail.com)

Your application can be deployed anywhere in the world on any provider you like.
But there are some requirements to integrate with our platform:

* Messaging communication between our and yours platforms will be set up using **RabbitMQ**.
* The backend and frontend of your solution must be configured with specific APIs and message structures to capture correct analytics and playing experience.


## Explore our infrastructure

Our platform consists of several blocks:
1. Main platform:
   * A place where players of Bandit Games are matched in a lobby.
   * Players Achievements are displayed.
   * Handles Game registration process.
2. Statistics/Analytics:
   * Collects events from every Game session process:
     1. Game session started event.
     2. Player made a move event.
     3. Game session finished event.
   * Process those events and predict related metrics for the registered player such as:
     1. Churn: how likely a player to return to our platform.
     2. First move win probability: how likely a player to win next match if he is first player in the lobby.
     3. Player classification: a classification of a player on how he interacts with the platform (e.g. Beginner, Frequent player)
3. Chatbot:
   * Provide information about utilization of platform and how to play all games on our platform

Additionally, our platform utilizes `SSO` for main platform and your board game.

## Adding a game flow

Here is an example flow of how should you add your game to our platform.

1. Connect your Message consumer to our `RabbitMQ` instance.
2. Connect your backend applicaiton to our `Keycloak` instance.
3. Prepare a set of rules of how to play your board game.
4. Set-up all events schemas and test if they align with provided schemas in the section at the end of this document.
5. Configure that your Frontend and Backend are communicating properly

## Backend configuration
Content of the subheading 4

## Frontend configuration
Information


## JSON schema of objects:
