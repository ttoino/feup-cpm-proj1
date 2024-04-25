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
#### Network
#### Local

## Data Schemas
### Server Database
#### ☕ Cafeteria Item 
Cafeteria Items are stored in the database with a string ID, which is the primary key, the item name, the price (stored as integer because the price is stored in the database as cents of euro and converted for euros everytime it is shown to the user) and a image url to represent the item.

| Name      | Type    |
|-----------|---------|
| **ID***   | String  |
| Name      | String  |
| Price     | Integer |
| Image Url | String  |

#### 📋 Order 

#### 🏷️ Voucher 

#### 🎭 Event 

#### 🎟️ Ticket 

#### 👥 User 

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



## Performed Scenarios Test
?? TODO

## How to use

## Team
- [Anete Medina Pereira](https://sigarra.up.pt/feup/pt/fest_geral.cursos_list?pv_num_unico=202008856)
- [João António Semedo Pereira](https://sigarra.up.pt/feup/pt/fest_geral.cursos_list?pv_num_unico=202007145)
- [Mariana Solange Monteiro Rocha](https://sigarra.up.pt/feup/pt/fest_geral.cursos_list?pv_num_unico=202004656)
