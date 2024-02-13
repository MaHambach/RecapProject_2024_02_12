# Shop Service (Advanced)

## Clarify uncertainties

Which questions remained unanswered from the previous sessions?
Please clarify these questions as a group.
Use all available resources for clarification.

When you are ready, click on 'Next'

## Coding: ShopService

Today, everything revolves around the ShopService task. Add or write tests for all steps in the task, unless otherwise specified.

Clone the sample solution: [ShopService](https://github.com/Flooooooooooorian/Recap-Project-Objektorientierung-ShopService). You will be working in new group configurations today and should gain experience working with unfamiliar code. Therefore, please work based on the sample solution from the last module.

Now remove the link to Florian's GitHub repository by selecting "Git" in the menu, then "Manage Remotes," and removing the entry "origin."

Upload the project as a new repository on GitHub to one of your own GitHub accounts: In IntelliJ, select "Git" in the cloned project, then "GitHub," and choose "Share Project on GitHub."

The sample solution is now the main branch of your GitHub repository.

What is the link to your GitHub repository?
`https://github.com/MaHambach/RecapProject_2024_02_12.git`

## Coding: Order Status

Add an order status to the Order (PROCESSING, IN_DELIVERY, COMPLETED) to determine the status of the order.

To do this, create a new branch, create and push the commits, create a pull request, review the PR, and merge it into the main branch.

What is the link to your pull request?
`https://github.com/MaHambach/RecapProject_2024_02_12/pull/2`

## Coding: Order Status

Write a method in the ShopService that returns a list of all orders with a specific order status (parameter) using streams.

## Coding: Optional Product

Modify the 'getProductById' method in your ProductRepo so that it returns an Optional<Product> if the product exists, otherwise an empty Optional.

## Coding: Exceptions

Modify the 'addOrder' method in the ShopService so that an exception is thrown if the product does not exist.

## Coding: Lombok

Add an 'updateOrder' method in the ShopService that updates the Order based on an orderId and a new order status. Use the Lombok `@With` annotation for this.

## Coding: Order Date

Extend the Order object with a field that stores the order timestamp. In the 'addOrder' method, fill this field with the current timestamp.

This timestamp should be able to be used as evidence in court if customers claim that they did not place the order. Consider which data type is best suited for this, even if customers order from abroad.

## Bonus: Setting in Main Repo

Create a Main class with a main method. In this method, create an instance of the ShopService.

The concrete instances for OrderRepo and ShopRepo should also be created here in the main method. Pass them to the ShopService constructor. Use the `@RequiredArgsConstructor` annotation in the ShopService to generate a corresponding constructor.

Define three concrete orders and add them all to the ShopService.

## Bonus: ID Generation

Create an `IdService` for generating an ID, which returns a new UUID in the `generateId` method (using `java.util.UUID`). Create a concrete implementation of the `IdService` in the main method and pass it to the ShopService constructor.

## Bonus: Pending Orders

Write a method `getOldestOrderPerStatus` that returns a map with the oldest Order object per status.

## Bonus: Transaction File

Have the main method read a file `transactions.txt` in the following format:
```
addOrder A 1 2 3
addOrder B 4 1
setStatus A COMPLETED
printOrders
```

This file should contain a list of command lines that the ShopService should execute.

The following command lines should be supported:

---

`addOrder`

Adds a new order. The order should contain the given product IDs. The order should have the status `PROCESSING`.

`addOrder <alias for order within file> <productId> [<productId> ...]`

Save the OrderID returned by the ShopService in a data structure (with the specified, freely selectable alias) so that you can later change the status of the order.

---

`setStatus`

Sets the status of an order.

`setStatus <alias for order within file> <status>`

---

`printOrders`

Prints all orders.

## Bonus: Quantity and Stock Levels

Add a quantity to the products. When a product is ordered, the quantity of the product is decreased. If a product is out of stock, it cannot be ordered anymore. Allow decimal numbers as well. Also, extend the command processing with `transactions.txt` accordingly.