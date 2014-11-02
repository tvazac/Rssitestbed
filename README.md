### Motes deploy
There is a `mote_deploy.sh` script which contains list of connected motes. First most is in list has ID = 1, second ID = 2 and so on. To deploy application on all motes, simply type `./mote_deploy.sh`

## Java application
### Configuration
Folder `configs` contains two csv files. `motes.csv` is holding a list with all connected motes. `scenario.csv` has two columns, where first column is number of packest and second is power.

### Build and run
In `java/src` there is `buildAndRun.sh` script which compiles java source code and executes the app.



