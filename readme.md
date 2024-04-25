# M.EIC 2023/2024 CPM - 1st  Assignment

## Topics
- [ ] Architecture
- [ ] Data Schemas (server database, a ticket, an order, a voucher)
- [ ] Features
- [ ] Navigation Map
- [ ] Performed Scenarios Tests
- [ ] Screenshot e tutorial como usar

## Architecture
A solid architecture is essential in mobile app development to make it less prone to errors, ensuring flexibility, scalability and maintainability. Well structured components promote modularity, simplyfing app development.

To achieve that, the architecture used in the development of this app follows principles suggested in the Google Guide to App Architecture.

Our app is mostly divided into the following components:
### Screen
What the user sees amd interacts with on their device. Contains all the visual interface definition and structure and components such as buttons, text fields, etc.

### View Model
The view model acts as a middleman between the UI and the data. Contains the business logic required to transform raw data into a appropriate format to be presented in the UI.
By acting as a middleman, it provides a **separation of concerns** which inscreases app modularity.


### Repositories
Repositories provide a easy way for accessing data. They contain methods for fetching, updating and deleting data. Their service helps centralizing data access and serves as a mediator between the view models' business logic and the data own of storage.

### Data Sources
Data sources are the data providers for the app. They include databases and network REST APIs. Data sources are the data providers that repositories use to retrieve and store data. In the context of our app, since it needs to communicate with a server, but also needs to work offline two kinds of data sources are being used: network data sources and local data sources.

#### Network
Data retrieved from any other source that is not the user's device. Allows real-time updates and access of information that is not locally stored.

#### Local
Data stored on the user's device. This kind of data source enables offline access and faster retrieval.

### Work Manager
TODO

### Model
TODO

![architecture.png](images-report%2Farchitecture.png)

## Data Schemas
### Server Database
#### ☕ Cafeteria Item 
Cafeteria Items are stored in the database with a string ID, which is the primary key, the item name (Name), price (Price) stored as an integer (because the price is stored in the database as cents of euro and converted for euros everytime it is shown to the user), and an image URL (Image Url) to represent the item visually.

| Name      | Type    |
|-----------|---------|
| **ID***   | String  |
| Name      | String  |
| Price     | Integer |
| Image Url | String  |

#### 📋 Order 
The Order schema represents an order made by a customer. Each order has a unique identifier (ID) and a date (Date) indicating when the order was made.

| Attribute    | Type   |
|--------------|--------|
| **ID***      | String |
| Date         | Date   |


#### 🏷️ Voucher 
The Voucher schema represents a voucher that can be redeemed by a user for discounts or special offers. Each voucher has a unique identifier (ID), a discount amount (Discount), an optional associated item (Item), a user who owns the voucher (User), and an optional associated order (Order).

| Attribute  | Type            |
|------------|-----------------|
| **ID***    | String          |
| Discount   | Integer (Nullable) |
| Item       | Item (Nullable) |
| User       | User            |
| Order      | Order (Nullable) |
#### 🎭 Event 
The Event schema represents an event avalaible in the app. It includes details such as the event's unique identifier (ID), name (Name), description (Description), date (Date), start time (Start Time), end time (End Time), location (Location), optional location details (Location Details), price (Price), and an image URL (ImageUrl).

| Attribute         | Type          |
|-------------------|---------------|
| **ID***           | String        |
| Name              | String        |
| Description       | String        |
| Date              | Date          |
| Start Time        | Time          |
| End Time          | Time          |
| Location          | String        |
| Location Details  | String (Nullable) |
| Price             | Integer       |
| ImageUrl          | String        |

#### 🎟️ Ticket
The Ticket schema represents a ticket purchased by a user for a specific event. Each ticket has a unique identifier (ID), an associated event (Event), user who purchased the ticket (User), associated seat (Seat), purchase date (Purchase Date), and optional use date (Use Date).


| Attribute      | Type          |
|----------------|---------------|
| **ID***        | String        |
| Event          | Event         |
| User           | User        |
| Seat           | Seat        |
| Purchase Date  | Date          |
| Use Date       | Date (Nullable) |

#### 👥 User 
The User schema represents a registered user in the system. It includes attributes such as a unique identifier (ID), name (Name), tax identification number (NIF), birthdate (Birthdate), email address (E-mail), name on credit card (Name CC), credit card number (Number CC), credit card expiration date (Expiration Date CC), and password (Password).

| Attribute         | Type    |
|-------------------|---------|
| **ID***           | String  |
| Name              | String  |
| NIF               | String  |
| Birthdate         | Date    |
| E-mail            | String  |
| Name CC           | String  |
| Number CC         | String  |
| Expiration Date CC| String  |
| Password          | String  |

----------------------
### Navigation Map
When a user opens the app for the first time, they are directed to the "Initial Page," featuring two primary options: Log In or Sign Up. Once logged in, regardless of whether the app is closed or not, the default landing page becomes the "Events Page."

Pages highlighted in pink within the navigation map represent sections accessible via the bottom navigation bar. These sections offer easy access and, since the user is logged in, can be reached with just a single click.

<div align="center">
 
![navigation-map.png](images-report%2Fnavigation-map.png)
###### Figure 1 - TikTek Navigation Map
</div>


## Features
- [X] Login
- [X] Register account
- [X] Logout
- [X] Change personal details and payment information
- [X] Credit card validation  (MATEMATICO)
- [X] SSN validation  (MATEMATICO)
- [X] Check items available in the cafeteria menu 
- [X] Check if cafeteria is open or closed
- [X] Add items to cart
- [X] Remove items from cart
- [X] Change items quantity in cart
- [X] Buy cafeteria order
- [X] Cancel cafeteria order
- [X] Check my cafeteria orders that are still available to be used
- [X] Check my cafeteria orders history
- [X] Check next events
- [X] Check information about a specific event (date, time, price, description)
- [X] Buy tickets to upcoming events
- [X] Check my event tickets that are still available to be used
- [X] Check my event tickets history
- [X] Available in Portuguese and English
- [ ] SECURITY <-----------

----------------------

<div align="center">

![navigation-bar.png](images-report%2Fbottom-navigation-bar.png)
###### Figure 2 - Bottom Navigation Bar
</div>

**adicionar das 2 paginas iniciais**

### Ticket Terminal
The ticket terminal allows customers to validate their purchased tickets. Users simply scan the QR code generated by the main app on their device using the terminal's camera. The terminal then sends the ticket information to the server for validation and displays the validation status.

![ticket-terminal.png](images-report%2Fticket-terminal.png)


### Cafeteria Terminal
The cafeteria terminal facilitates ordering and payment for cafeteria items. Customers scan the QR code generated by the main app on their device using the terminal's camera. The terminal sends the cart information to the server for validation and displays a confirmation screen with the purchased items and total price.

![cafeteria-terminal.png](images-report%2Fcafeteria-terminal.png)


## Performed Scenarios Test
?? TODO

## How to use

## Team
- [Anete Medina Pereira](https://sigarra.up.pt/feup/pt/fest_geral.cursos_list?pv_num_unico=202008856)
- [João António Semedo Pereira](https://sigarra.up.pt/feup/pt/fest_geral.cursos_list?pv_num_unico=202007145)
- [Mariana Solange Monteiro Rocha](https://sigarra.up.pt/feup/pt/fest_geral.cursos_list?pv_num_unico=202004656)
