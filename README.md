# Fire Watch
### A fire threat visualiser
#### The idea
Note: This is merely a prototype.
The generalised concept is as follows, the user with some data will create an input file with longatude (-180,180) and latitude (-90,90) with corresponding satelite data of
all kinds from wind speed to Chloryphyll-A density. We attempted to train a neural network on the data from JAXA and CSA but were unable to obtain the fire reference data so we
manually selected course polygons where fires were known to occur such as california however, the accuracy of the model was very poor and more time and data would be needed to
produce one with an acceptible accuracy. We decided as a proof of concept to use statistical methods on our data in order to find areas which have statistically abnormal levels
of carbon monoxide and high temperatures which produced acceptible results when using data from the MOPITT sensor. 
The application is run as a java program through a terminal. With specific argument requirements. 
The argument requirements are as follows: <inputfile> <true/false for CO data> <true/false for Temperature data>
Example: java FireWatch src/input/output.csv true true

Note that the supplied data must has temperature in Kelvin and CO should be dimensionless. 

#### Runtime
When run the user will be shown a world map with red pixels representing "hotspots" or high risk locations. 
![](screenshot.png)
