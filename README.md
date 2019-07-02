# MRT Route Find

MRT Route Find is an API which allow the users to query the path
between MRT Station. Some of the features are:

* Path between two stations ordered by the number of stations to travel
* Path between two stations ordered by the total time needed to travel.
* Incorporate the different TimePeriod (PEAK, NON_PEAK, NIGHT) to find
  the most efficient path dynamically

### Assumptions

* The list of unit test covered is not comprehensive, can add more unit
  test later.
* For NS12 station, Opening date does not contains the day, changed it
  to 01 December 2019
*  Current loading the list of stations from StationMap.csv file into
   memory at startup, later code can be modifies to read this data from
   database.
* We can cache query response to speed up the performance of API's

### Tech

MRT Route Find API is built by using  open source projects:

* Spring-Boot
* Java
* Maven

### Installation

MRT Route Find API requires [Java](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) 8 and [Maven](https://maven.apache.org/install.html) 3 to run.

Ensure Java and Maven is Installed and properly configured on the 
machine.

```sh
download the project on some path in the machine (e.g. /tmp/MRT-Route-Find)
$ cd MRT-Route-Find
$ mvn clean package
```

Above command will generate a ```target``` folder which contains the required ```jar``` to be used for stating the application.

To start the application:

* open application.properties file in the path /tmp/MRT-Route-Find/src/main/resources
* change the value of property station.data.file in application.properties file to point to an absolute path of StationMap.csv (e.g. /tmp/MRT-Route-Find/data/StationMap.csv)
* Go to /tmp/MRT-Route-Find/bin directory
* run start.sh (this will start the application on the post 8080) and will also create a file containg processId in /tmp/MRT-Route-Find/logs directory

To stop the application:
* Run the stop.sh file present in the /tmp/MRT-Route-Find/bin directory

## API Format

The application exposes the below API:

API: ```POST``` ```localhost:8080/v1/getRouteByTime```

This API returns path with shortest time, ordered by time.

Sample Request Body:
```
{
    "sourceStation":"Boon Lay",
    "destinationStation":"Little India",
    "startingTime":"2019-01-31T06:05"
}
```

Sample Response Data:
```
{
    "routeList": [
        {
            "sourceStation": "Boon Lay",
            "destinationStation": "Little India",
            "noOfStations": 15,
            "routePath": "'EW27', 'EW26', 'EW25', 'EW24', 'EW23', 'EW22', 'EW21', 'CC22', 'CC21', 'CC20', 'CC19', 'DT9', 'DT10', 'DT11', 'DT12'",
            "routeDescription": "Take EW Line from Boon Lay to Lakeside ----> Take EW Line from Lakeside to Chinese Garden ----> Take EW Line from Chinese Garden to Jurong East ----> Take EW Line from Jurong East to Clementi ----> Take EW Line from Clementi to Dover ----> Take EW Line from Dover to Buona Vista ----> Change from EW line to CC line ----> Take CC Line from Buona Vista to Holland Village ----> Take CC Line from Holland Village to Farrer Road ----> Take CC Line from Farrer Road to Botanic Gardens ----> Change from CC line to DT line ----> Take DT Line from Botanic Gardens to Stevens ----> Take DT Line from Stevens to Newton ----> Take DT Line from Newton to Little India ----> ",
            "routeTime": 150
        },
        {
            "sourceStation": "Boon Lay",
            "destinationStation": "Little India",
            "noOfStations": 20,
            "routePath": "'EW27', 'EW26', 'EW25', 'EW24', 'EW23', 'EW22', 'EW21', 'CC22', 'CC21', 'CC20', 'CC19', 'DT9', 'DT10', 'DT11', 'NS21', 'NS22', 'NS23', 'NS24', 'NE6', 'NE7'",
            "routeDescription": "Take EW Line from Boon Lay to Lakeside ----> Take EW Line from Lakeside to Chinese Garden ----> Take EW Line from Chinese Garden to Jurong East ----> Take EW Line from Jurong East to Clementi ----> Take EW Line from Clementi to Dover ----> Take EW Line from Dover to Buona Vista ----> Change from EW line to CC line ----> Take CC Line from Buona Vista to Holland Village ----> Take CC Line from Holland Village to Farrer Road ----> Take CC Line from Farrer Road to Botanic Gardens ----> Change from CC line to DT line ----> Take DT Line from Botanic Gardens to Stevens ----> Take DT Line from Stevens to Newton ----> Change from DT line to NS line ----> Take NS Line from Newton to Orchard ----> Take NS Line from Orchard to Somerset ----> Take NS Line from Somerset to Dhoby Ghaut ----> Change from NS line to NE line ----> Take NE Line from Dhoby Ghaut to Little India ----> ",
            "routeTime": 209
        },
        {
            "sourceStation": "Boon Lay",
            "destinationStation": "Little India",
            "noOfStations": 24,
            "routePath": "'EW27', 'EW26', 'EW25', 'EW24', 'EW23', 'EW22', 'EW21', 'CC22', 'CC21', 'CC20', 'CC19', 'DT9', 'DT10', 'DT11', 'NS21', 'NS22', 'NS23', 'NS24', 'NS25', 'EW13', 'EW12', 'DT14', 'DT13', 'DT12'",
            "routeDescription": "Take EW Line from Boon Lay to Lakeside ----> Take EW Line from Lakeside to Chinese Garden ----> Take EW Line from Chinese Garden to Jurong East ----> Take EW Line from Jurong East to Clementi ----> Take EW Line from Clementi to Dover ----> Take EW Line from Dover to Buona Vista ----> Change from EW line to CC line ----> Take CC Line from Buona Vista to Holland Village ----> Take CC Line from Holland Village to Farrer Road ----> Take CC Line from Farrer Road to Botanic Gardens ----> Change from CC line to DT line ----> Take DT Line from Botanic Gardens to Stevens ----> Take DT Line from Stevens to Newton ----> Change from DT line to NS line ----> Take NS Line from Newton to Orchard ----> Take NS Line from Orchard to Somerset ----> Take NS Line from Somerset to Dhoby Ghaut ----> Take NS Line from Dhoby Ghaut to City Hall ----> Change from NS line to EW line ----> Take EW Line from City Hall to Bugis ----> Change from EW line to DT line ----> Take DT Line from Bugis to Rochor ----> Take DT Line from Rochor to Little India ----> ",
            "routeTime": 245
        },
        {
            "sourceStation": "Boon Lay",
            "destinationStation": "Little India",
            "noOfStations": 28,
            "routePath": "'EW27', 'EW26', 'EW25', 'EW24', 'EW23', 'EW22', 'EW21', 'CC22', 'CC21', 'CC20', 'CC19', 'DT9', 'DT10', 'DT11', 'NS21', 'NS22', 'NS23', 'NS24', 'NS25', 'NS26', 'NS27', 'CE2', 'CE1', 'DT16', 'DT15', 'DT14', 'DT13', 'DT12'",
            "routeDescription": "Take EW Line from Boon Lay to Lakeside ----> Take EW Line from Lakeside to Chinese Garden ----> Take EW Line from Chinese Garden to Jurong East ----> Take EW Line from Jurong East to Clementi ----> Take EW Line from Clementi to Dover ----> Take EW Line from Dover to Buona Vista ----> Change from EW line to CC line ----> Take CC Line from Buona Vista to Holland Village ----> Take CC Line from Holland Village to Farrer Road ----> Take CC Line from Farrer Road to Botanic Gardens ----> Change from CC line to DT line ----> Take DT Line from Botanic Gardens to Stevens ----> Take DT Line from Stevens to Newton ----> Change from DT line to NS line ----> Take NS Line from Newton to Orchard ----> Take NS Line from Orchard to Somerset ----> Take NS Line from Somerset to Dhoby Ghaut ----> Take NS Line from Dhoby Ghaut to City Hall ----> Take NS Line from City Hall to Raffles Place ----> Take NS Line from Raffles Place to Marina Bay ----> Change from NS line to CE line ----> Take CE Line from Marina Bay to Bayfront ----> Change from CE line to DT line ----> Take DT Line from Bayfront to Promenade ----> Take DT Line from Promenade to Bugis ----> Take DT Line from Bugis to Rochor ----> Take DT Line from Rochor to Little India ----> ",
            "routeTime": 281
        },
        {
            "sourceStation": "Boon Lay",
            "destinationStation": "Little India",
            "noOfStations": 28,
            "routePath": "'EW27', 'EW26', 'EW25', 'EW24', 'EW23', 'EW22', 'EW21', 'CC22', 'CC21', 'CC20', 'CC19', 'DT9', 'DT10', 'DT11', 'NS21', 'NS20', 'NS19', 'NS18', 'NS17', 'CC15', 'CC14', 'CC13', 'NE12', 'NE11', 'NE10', 'NE9', 'NE8', 'NE7'",
            "routeDescription": "Take EW Line from Boon Lay to Lakeside ----> Take EW Line from Lakeside to Chinese Garden ----> Take EW Line from Chinese Garden to Jurong East ----> Take EW Line from Jurong East to Clementi ----> Take EW Line from Clementi to Dover ----> Take EW Line from Dover to Buona Vista ----> Change from EW line to CC line ----> Take CC Line from Buona Vista to Holland Village ----> Take CC Line from Holland Village to Farrer Road ----> Take CC Line from Farrer Road to Botanic Gardens ----> Change from CC line to DT line ----> Take DT Line from Botanic Gardens to Stevens ----> Take DT Line from Stevens to Newton ----> Change from DT line to NS line ----> Take NS Line from Newton to Novena ----> Take NS Line from Novena to Toa Payoh ----> Take NS Line from Toa Payoh to Braddell ----> Take NS Line from Braddell to Bishan ----> Change from NS line to CC line ----> Take CC Line from Bishan to Lorong Chuan ----> Take CC Line from Lorong Chuan to Serangoon ----> Change from CC line to NE line ----> Take NE Line from Serangoon to Woodleigh ----> Take NE Line from Woodleigh to Potong Pasir ----> Take NE Line from Potong Pasir to Boon Keng ----> Take NE Line from Boon Keng to Farrer Park ----> Take NE Line from Farrer Park to Little India ----> ",
            "routeTime": 289
        }
    ]
}
```


API: ```POST``` ```localhost:8080/v1/getRoute```

This API returns path with shortest path, ordered by path Length.

Sample Request Body:
```
{
    "sourceStation":"Holland Village",
    "destinationStation":"Bugis"
}
```

Sample Response Data:
```
{
    "routeList": [
        {
            "sourceStation": "Holland Village",
            "destinationStation": "Bugis",
            "noOfStations": 8,
            "routePath": "'CC21', 'CC20', 'CC19', 'DT9', 'DT10', 'DT11', 'DT12', 'DT13', 'DT14'",
            "routeDescription": "Take CC Line from Holland Village to Farrer Road ----> Take CC Line from Farrer Road to Botanic Gardens ----> Change from CC line to DT line ----> Take DT Line from Botanic Gardens to Stevens ----> Take DT Line from Stevens to Newton ----> Take DT Line from Newton to Little India ----> Take DT Line from Little India to Rochor ----> Take DT Line from Rochor to Bugis ----> ",
            "routeTime": "NA"
        },
        {
            "sourceStation": "Holland Village",
            "destinationStation": "Bugis",
            "noOfStations": 10,
            "routePath": "'CC21', 'CC20', 'CC19', 'CC17', 'TE9', 'TE10', 'TE11', 'DT10', 'DT11', 'DT12', 'DT13', 'DT14'",
            "routeDescription": "Take CC Line from Holland Village to Farrer Road ----> Take CC Line from Farrer Road to Botanic Gardens ----> Take CC Line from Botanic Gardens to Caldecott ----> Change from CC line to TE line ----> Take TE Line from Caldecott to Mount Pleasant ----> Take TE Line from Mount Pleasant to Stevens ----> Change from TE line to DT line ----> Take DT Line from Stevens to Newton ----> Take DT Line from Newton to Little India ----> Take DT Line from Little India to Rochor ----> Take DT Line from Rochor to Bugis ----> ",
            "routeTime": "NA"
        },
        {
            "sourceStation": "Holland Village",
            "destinationStation": "Bugis",
            "noOfStations": 12,
            "routePath": "'CC21', 'CC20', 'CC19', 'CC17', 'TE9', 'TE10', 'TE11', 'DT10', 'DT11', 'NS21', 'NS22', 'NS23', 'NS24', 'NS25', 'EW13', 'EW12'",
            "routeDescription": "Take CC Line from Holland Village to Farrer Road ----> Take CC Line from Farrer Road to Botanic Gardens ----> Take CC Line from Botanic Gardens to Caldecott ----> Change from CC line to TE line ----> Take TE Line from Caldecott to Mount Pleasant ----> Take TE Line from Mount Pleasant to Stevens ----> Change from TE line to DT line ----> Take DT Line from Stevens to Newton ----> Change from DT line to NS line ----> Take NS Line from Newton to Orchard ----> Take NS Line from Orchard to Somerset ----> Take NS Line from Somerset to Dhoby Ghaut ----> Take NS Line from Dhoby Ghaut to City Hall ----> Change from NS line to EW line ----> Take EW Line from City Hall to Bugis ----> ",
            "routeTime": "NA"
        },
        {
            "sourceStation": "Holland Village",
            "destinationStation": "Bugis",
            "noOfStations": 13,
            "routePath": "'CC21', 'CC20', 'CC19', 'CC17', 'CC16', 'CC15', 'NS17', 'NS18', 'NS19', 'NS20', 'NS21', 'DT11', 'DT12', 'DT13', 'DT14'",
            "routeDescription": "Take CC Line from Holland Village to Farrer Road ----> Take CC Line from Farrer Road to Botanic Gardens ----> Take CC Line from Botanic Gardens to Caldecott ----> Take CC Line from Caldecott to Marymount ----> Take CC Line from Marymount to Bishan ----> Change from CC line to NS line ----> Take NS Line from Bishan to Braddell ----> Take NS Line from Braddell to Toa Payoh ----> Take NS Line from Toa Payoh to Novena ----> Take NS Line from Novena to Newton ----> Change from NS line to DT line ----> Take DT Line from Newton to Little India ----> Take DT Line from Little India to Rochor ----> Take DT Line from Rochor to Bugis ----> ",
            "routeTime": "NA"
        },
        {
            "sourceStation": "Holland Village",
            "destinationStation": "Bugis",
            "noOfStations": 15,
            "routePath": "'CC21', 'CC20', 'CC19', 'CC17', 'CC16', 'CC15', 'CC14', 'CC13', 'NE12', 'NE11', 'NE10', 'NE9', 'NE8', 'NE7', 'DT12', 'DT13', 'DT14'",
            "routeDescription": "Take CC Line from Holland Village to Farrer Road ----> Take CC Line from Farrer Road to Botanic Gardens ----> Take CC Line from Botanic Gardens to Caldecott ----> Take CC Line from Caldecott to Marymount ----> Take CC Line from Marymount to Bishan ----> Take CC Line from Bishan to Lorong Chuan ----> Take CC Line from Lorong Chuan to Serangoon ----> Change from CC line to NE line ----> Take NE Line from Serangoon to Woodleigh ----> Take NE Line from Woodleigh to Potong Pasir ----> Take NE Line from Potong Pasir to Boon Keng ----> Take NE Line from Boon Keng to Farrer Park ----> Take NE Line from Farrer Park to Little India ----> Change from NE line to DT line ----> Take DT Line from Little India to Rochor ----> Take DT Line from Rochor to Bugis ----> ",
            "routeTime": "NA"
        }
    ]
}
```

