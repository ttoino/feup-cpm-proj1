# M.EIC 2023/2024 CPM - 1st Assignment

## Team
<table border="0" >

 <tr>
<td>

Anete Medina Pereira  

</td>
<td>

[202007145](https://sigarra.up.pt/feup/pt/fest_geral.cursos_list?pv_num_unico=202007145) 
    
</td>
</tr>
<tr>
<td>
    
João António Semedo Pereira 

</td>
<td>
    
[202007145](https://sigarra.up.pt/feup/pt/fest_geral.cursos_list?pv_num_unico=202007145)  

</td>
</tr>
<tr>
<td>
    
Mariana Solange Monteiro Rocha

</td>
<td>
    
 [202004656](https://sigarra.up.pt/feup/pt/fest_geral.cursos_list?pv_num_unico=202004656)

</td>
</tr>
</table>


## Index

1. [Overview](#CHANGE)
2. [App Architecture](#architecture)
3. [Server Database](#server-database)
4. [Navigation Map](#navigation-map)
5. [Implemented Features](#features)
   
- [ ] Performed Scenarios Tests
- [ ] Screenshot e tutorial como usar

## Architecture

A solid architecture is essential in mobile app development to make it less prone to errors, ensuring flexibility, scalability and maintainability. Well structured components promote modularity, simplyfing app development.

To achieve that, the architecture used in the development of this app follows principles suggested in the [Google Guide to App Architecture](https://developer.android.com/topic/architecture).

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

<div>

![architecture.png](images-report%2Farchitecture.png)
###### Figure 1 - TikTek Architecture

</div>

## Data Schemas

### Server Database

#### ☕ Cafeteria Item

Cafeteria Items are stored in the database with a string ID, which is the primary key, the item name (Name), price (Price) stored as an integer (because the price is stored in the database as cents of euro and converted for euros everytime it is shown to the user), and an image URL (Image Url) to represent the item visually.

| Name      | Type    |
| --------- | ------- |
| **ID\***  | String  |
| Name      | String  |
| Price     | Integer |
| Image Url | String  |

#### 📋 Order

The Order schema represents an order made by a customer. Each order has a unique identifier (ID) and a date (Date) indicating when the order was made.

| Attribute | Type   |
| --------- | ------ |
| **ID\***  | String |
| Date      | Date   |

#### 🏷️ Voucher

The Voucher schema represents a voucher that can be redeemed by a user for discounts or special offers. Each voucher has a unique identifier (ID), a discount amount (Discount), an optional associated item (Item), a user who owns the voucher (User), and an optional associated order (Order).

| Attribute | Type               |
| --------- | ------------------ |
| **ID\***  | String             |
| Discount  | Integer (Nullable) |
| Item      | Item (Nullable)    |
| User      | User               |
| Order     | Order (Nullable)   |

#### 🎭 Event

The Event schema represents an event avalaible in the app. It includes details such as the event's unique identifier (ID), name (Name), description (Description), date (Date), start time (Start Time), end time (End Time), location (Location), optional location details (Location Details), price (Price), and an image URL (ImageUrl).

| Attribute        | Type              |
| ---------------- | ----------------- |
| **ID\***         | String            |
| Name             | String            |
| Description      | String            |
| Date             | Date              |
| Start Time       | Time              |
| End Time         | Time              |
| Location         | String            |
| Location Details | String (Nullable) |
| Price            | Integer           |
| ImageUrl         | String            |

#### 🎟️ Ticket

The Ticket schema represents a ticket purchased by a user for a specific event. Each ticket has a unique identifier (ID), an associated event (Event), user who purchased the ticket (User), associated seat (Seat), purchase date (Purchase Date), and optional use date (Use Date).

| Attribute     | Type            |
| ------------- | --------------- |
| **ID\***      | String          |
| Event         | Event           |
| User          | User            |
| Seat          | Seat            |
| Purchase Date | Date            |
| Use Date      | Date (Nullable) |

#### 👥 User

The User schema represents a registered user in the system. It includes attributes such as a unique identifier (ID), name (Name), tax identification number (NIF), birthdate (Birthdate), email address (E-mail), name on credit card (Name CC), credit card number (Number CC), credit card expiration date (Expiration Date CC), and password (Password).

| Attribute          | Type   |
| ------------------ | ------ |
| **ID\***           | String |
| Name               | String |
| NIF                | String |
| Birthdate          | Date   |
| E-mail             | String |
| Name CC            | String |
| Number CC          | String |
| Expiration Date CC | String |
| Password           | String |

---

### Navigation Map

Pages highlighted in pink within the navigation map represent sections accessible via the bottom navigation bar. These sections offer easy access and, since the user is logged in, can be reached with just a single click.

<div align="center">
 
![navigation-map.png](images-report%2Fnavigation-map.png)
###### Figure 2 - TikTek Navigation Map
</div>

## Features
### Main Features

#### Authentication

When users open the app for the first time, they are directed to the "Initial Page" (first from left to right) featuring two primary options: Log In or Sign Up. Once logged in, regardless of whether the app is closed or not, the default landing page becomes the "Events Page".

The users can create an account if they don't have one or log in in a previously registered account as shown in the screenshots below.

##### Features:
- [x] Register account
- [x] Log into account

<div align="center">

![authentication.png](images-report%2Fauthentication.png)
###### Figure 3 - Authentication Pages
</div>


#### Events

When the users log in, as referred above, they are redirected to the events page. There they can check the upcoming events, buy tickets and collect information about specific events as shown in the pictures below. When an user attempts to finish a purchase they have to confirm that they are the owners of the phone by using biometric authentication.


##### Features:
- [x] Check the next events taking place
- [x] Check information about a specific event (date, time, price, description)
- [x] Buy tickets to upcoming events
- [x] Check my event tickets that are still available to be used
- [x] Check my event tickets history
- [x] Generate QR code to confirm purchase on the terminal


<div align="center">

![events-screenS.png](images-report%2Fevents-screens.png)


![events-screens2.png](images-report%2Fevents-screens-2.png)
###### Figure 5 - Events' Pages
</div>


#### Cafeteria 

Besides being able to buy and check information about upcoming event, users are also allowed to buy from the local theatre cafeteria using the app.  When an user attempts to finish a purchase, just like in events tickets, they have to confirm that they are the owners of the phone by using biometric authentication.
After a purchase is made, a QR code is generated to be validated on the terminal. It contains all the products bought and their quantity, vouchers used and the price before and after the use of the vouchers.

##### Features:
- [x] Check items available in the cafeteria menu
- [x] Check if cafeteria is open or closed
- [x] Add items to cart
- [x] Remove items from cart
- [x] Change items quantity in cart
- [x] Buy cafeteria order
- [x] Cancel cafeteria order
- [x] Generate QR code to confirm purchase on the terminal
- [x] Check my cafeteria orders that are still available to be used
- [x] Check my cafeteria orders history

<div align="center">

![events-screenS.png](images-report%2Fcafeteria-screens.png)

###### Figure 5 - Cafeteria's Pages
</div>


#### Ticket Terminal

The ticket terminal allows customers to validate their purchased tickets. Users simply scan the QR code generated by the main app on their device using the terminal's camera. The terminal then sends the ticket information to the server for validation and displays the validation status.

<div align="center">

![ticket-terminal.png](images-report%2Fticket-terminal.png)
###### Figure X - Authentication Pages
</div>

#### Cafeteria Terminal

The cafeteria terminal facilitates ordering and payment for cafeteria items. Customers scan the QR code generated by the main app on their device using the terminal's camera. The terminal sends the cart information to the server for validation and displays a confirmation screen with the purchased items and total price.

<div align="center">

![cafeteria-terminal.png](images-report%2Fcafeteria-terminal.png)
###### Figure X - Authentication Pages
</div>




## Performed Scenarios Test

?? TODO

## How to use

## References
1. [Google Guide to App Architecture](https://developer.android.com/topic/architecture)
2.