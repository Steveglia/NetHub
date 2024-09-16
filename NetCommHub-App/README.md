---

# NetCommHub - Group-Based Client-Server Communication System

## Overview

This coursework project aims to implement a networked distributed system for group-based client-server communication. The system is configured with a Graphical User Interface (GUI). It follows specific requirements outlined below:

- Members can join the group by providing parameters such as port, and IP address via GUI.
- The first member to join becomes the coordinator, responsible for maintaining the state of active group members.
- The coordinator informs new members about the details of the current coordinator.
- Members can request details of existing members from the coordinator.
- Any member can leave the group at any time; if the coordinator leaves, a new member becomes the coordinator.
- Communication among remaining members should not be interrupted if a member leaves.
- The system should print out messages sent to/by the members.

## Implementation

### Running the Program

The program can be executed in manual mode:
  
- **Manual Mode:** Requires user input to simulate the task. Users can type messages to send to other members.

### Parameters

- **ID:** Unique identifier assigned to each member.
  
- **Login:** Port and IP Address of the Server:** Provided during the member's connection.

### Exiting the Program

- Members can quit by using a Quit button (GUI).

## Technical Details

The project implementation demonstrates the following programming principles and practices contributing to the final grade:

- **Design Patterns:** Utilized design patterns to enhance code structure and maintainability.
  
- **JUnit Testing:** Implemented JUnit-based testing for application components.
  
- **Fault Tolerance:** Incorporated fault tolerance mechanisms to ensure system reliability.
  
- **Version Control System (VCS):** The project uses a Version Control System (Git) for source code management.

## Getting Started

To run the program, follow these steps:

1. Clone the repository:

   ```bash
   git clone https://nirperetz@dev.azure.com/nirperetz/NetCommHub/_git/NetCommHub-App
   ```
   
2. Run the Server class to activate the server.

3. Run the Client class to connect a client to the server.

---