db = db.getSiblingDB('item_list')

db.createUser({
  user: 'keeper',
  pwd: 'keeper',
  roles: [
    {
      role: 'readWrite',
      db: 'item_list',
    },
  ],
});