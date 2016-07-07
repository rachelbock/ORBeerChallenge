# Oregon Beer Challenge 

The Oregon Beer Challenge app provides the user with a list of all of Oregon's breweries. The user can mark off which ones they have visited with a goal of visiting them all. 

The application uses the Brewery DB API: <http://www.brewerydb.com/>
### Home Page
- The home page provides the user with the total number of breweries that they have visited and a list of the breweries.
- The list of breweries comes from a local database set up using SQLiteOpenHelper.
- The Floating Action Button brings the user to a list of all of the breweries in Oregon. 

![Home Page](https://raw.githubusercontent.com/rachelbock/ORBeerChallenge/master/screenshots/home_page.png)

### Brewery List
- The list of breweries contains an image of the Brewery and the Brewery Name. There is a checkbox in each row for the user to mark off which breweries they have visited.
- Each brewery row is clickable and loads a detail fragment.

![Brewery List](https://raw.githubusercontent.com/rachelbock/ORBeerChallenge/master/screenshots/brewery_list.png)

### Brewery Detail Page
- The detail page contains the image and name of the brewery as well as a list of all of the beers if the API contains the information for the brewery. 

![Brewery Details](https://raw.githubusercontent.com/rachelbock/ORBeerChallenge/master/screenshots/brewery_detail.png)