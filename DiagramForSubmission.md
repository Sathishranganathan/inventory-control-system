# Introduction #

This is an inventory control system that suitable for the small grocery. Our system aims to manage the information about the input and output for a range of goods, which may appear in a grocery. Further services the system can provide includes track and warn of impending expiry of goods and divide the goods into categories. Three actors would be involved in this system, which are worker, manager and administer. And basically we use Qt Creator as our software development tool in this project.

# Development Platform #
|Development Language|C++|
|:-------------------|:--|
|Development Tool    |QT Creator|
|Database            |SQLite|
|GUI Framework       |Nokia QT|

# Team Member #
|Name|Responsibility|
|:---|:-------------|
|Wang Ding|Team Leader, System Architecture Design & Implementation(View Goods,View Category,Order Stock,Dynamically issue stock-up request)|
|Lu Mukai|Use Case Design & Implementation(Add Category, Statistics)|
|Zhao Longwen|Class Diagram Design & Implementation(Login, Add Goods)|
|Wang Yabin|Database Desgin,E-R Diagram Design& Implementation(Deliver Stock,Report Impending Expiry)|
|Luo Di|System Testing,Documentation(Maintain Project Wiki, Combine the documents from others into the one )|


# Use Case Diagram #
![http://inventory-control-system.googlecode.com/files/Use_Case_Diagram.png](http://inventory-control-system.googlecode.com/files/Use_Case_Diagram.png)


  * Outbound: The main function of this use cases to insert warehouse outbound records.And there are four parameters were involved in the use case, which include batch number, date, goods name, quantity and unit price.

  * Inbound: The function is to insert warehouse outbound records. The parameters include batch number, date, goods name, quantity, unit price and expired date.

  * Goods info: The function is in order to select all the records from database and display them to users.

  * Setting expiry time is the case to valid the expiry time inputted is not less than the current date.

  * category: In our system, we divided all the goods into variety of categories, in that case we can manage the goods more efficient. And each category has a unique ID.

  * Use cases of set warning line of depletion and set warning line of expiry should be operating when add a new goods, using these two warning lines we can prevent the goods in the grocery from out of data.

  * Database, backup database and restore database are the use case to provide some functions to process the issues on database, such as backup and restoring the database.

# Class Diagram #

![http://inventory-control-system.googlecode.com/files/Class.jpg](http://inventory-control-system.googlecode.com/files/Class.jpg)

Actually there are 6 classes designed in our inventory control system. Attributes in each class are private, and the functions to get these attributes are not shown in the class diagram.

  * Worker class is a parent class with two son classes.They represent two different roles: manager and administer.
  * Category ID in the goods class represent the category of goods which is associate with the category class.
  * There are some differences between addnewgoods function and updategoods function.Addnewgoods function means add goods of new category then updategoods function means update number of some goods.
  * Expiry and depletion are two son classes of warning. They overload the serWarningMessage function separately.

# E-R Diagram #

![http://inventory-control-system.googlecode.com/files/ER.png](http://inventory-control-system.googlecode.com/files/ER.png)