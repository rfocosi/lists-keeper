# Lists Keeper
Small Ktor application to create and search lists

## Running

`docker-compose up --build`

## Testing

- Create a list:
```
curl -I -X POST \
    -H 'Content-Type: application/json' \
    http://localhost:8080/lists/mylist
```

- Add item:
```
curl -I -X POST \
    -H 'Content-Type: application/json' \
    http://localhost:8080/lists/mylist/myitem 
```

- Find a item:
```
curl -I -X GET \
    -H 'Content-Type: application/json' \
    http://localhost:8080/lists/mylist/myitem 
```

- Bulk insert:
```
curl -X POST \
    -H 'Content-Type: application/json' \
    http://localhost:8080/lists/mylist/bulk \
    -d '{
	"items": [ 
		"item1", "item2", "item3" , "item4", "item5"
	]
}'
```