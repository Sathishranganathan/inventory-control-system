# Entity-relationship model #

According to our last discussion, I designed 4 entities(Category, Goods, Batch, Staff) based on our inventory system.


# Entity #


  * ategory plays each category in this store.
  * oods plays each goods in this store.
  * atch plays each batch of goods into the store.
  * taff plays each staff in the store.

# Relationship #

  * taff is an independent entity.
  * here may be many Goods in one Category, and each Category has a unique `catid`.
  * ne kind of Goods can be stored by batch, and each batch has its own expired time, count and price.