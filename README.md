# EPIDEMIC-TRANSMISSION-SIMULATION
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=leohoc_epidemic-transmission-simulation&metric=coverage)](https://sonarcloud.io/dashboard?id=leohoc_epidemic-transmission-simulation)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=leohoc_epidemic-transmission-simulation&metric=alert_status)](https://sonarcloud.io/dashboard?id=leohoc_epidemic-transmission-simulation)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=leohoc_epidemic-transmission-simulation&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=leohoc_epidemic-transmission-simulation)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=leohoc_epidemic-transmission-simulation&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=leohoc_epidemic-transmission-simulation)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=leohoc_epidemic-transmission-simulation&metric=security_rating)](https://sonarcloud.io/dashboard?id=leohoc_epidemic-transmission-simulation)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=leohoc_epidemic-transmission-simulation&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=leohoc_epidemic-transmission-simulation)

A JAVA simulation of the dynamics of an epidemic disease spread.

### Introduction

Given an initial population moving within a delimited map and a set of parameters describing the disease spread 
and the population dynamics behavior, this application will perform a simulation and output the following results:

- Not exposed to the disease count;
- Infected count;
- Hospitalized count;
- Recovered count;
- Dead count;

### Install requirements

- Java >= 11
- Git

### Running the application

- Clone the repository:

```
    git clone https://github.com/leohoc/epidemic-transmission-simulation.git
```

- Navigate to the application root folder:

```
    cd epidemic-transmission-simulation/
```

- Run the tests and build a JAR file:

```
    ./gradlew clean test fatJar
```

- Run the application:

```
    java -jar build/libs/epidemic-transmission-simulation-*.jar
```

### Simulation Properties Configuration

All simulation properties can be configured in the [application.properties][1] file.

#### General Simulation Properties

| Property                                     | Type    | Description                                          |
|----------------------------------------------|---------|------------------------------------------------------|
| simulation.population.size                   | Integer | Number of individuals compounding the population.    |
| simulation.initialconditions.infectedpercent | Double  | Population percent starting the simulation infected. |
| simulation.graphics.enabled                  | Boolean | Toggle to show the simulation graphically.           |

#### Simulation Iterations Properties

Disease behavior properties are configured in day units, so the number of iterations per day is an important property
to describe how much an individual can move in each simulated day, impacting the disease spread.

| Property                               | Type    | Description                                          |
|----------------------------------------|---------|------------------------------------------------------|
| simulation.iterations.totaliterations  | Integer | Total number of iterations.                          |
| simulation.iterations.iterationsperday | Integer | Number of iterations that compounds a simulated day. |

#### Individual Properties

The size of an individual relates to the probability of infection, which is triggered when one infected individual 
overlaps another individual with no antibodies in the simulation map. 

| Property                     | Type    | Description        |
|------------------------------|---------|--------------------|
| simulation.individual.width  | Integer | Individual width.  |
| simulation.individual.height | Integer | Individual height. |

#### Movement Properties

The simulation takes place in a 2D map with an X and a Y axis. To confine the population in a finite map, the boundary
properties sets the limits of the individuals' movement.

To match the inverted Y axis of the Java AWT lib, the Y axis starts in the `simulation.movement.boundary.up` point and
**increases** downward until the `simulation.movement.boundary.down` point.

At each iteration, the individuals can choose to maintain its direction, change its direction or to stand still.

| Property                                   | Type    | Description                                                           |
|--------------------------------------------|---------|-----------------------------------------------------------------------|
| simulation.movement.boundary.up            | Integer | Upper limit of the map Y axis.                                        |
| simulation.movement.boundary.right         | Integer | Right limit of the map X axis.                                        |
| simulation.movement.boundary.down          | Integer | Lower limit of the map Y axis.                                        |
| simulation.movement.boundary.left          | Integer | Left limit of the map X axis.                                         |
| simulation.movement.changeprobability      | Double  | Probability of an individual change its direction at each iteration.  |
| simulation.movement.socialisolationpercent | Double  | Probability of an individual choose to stand still at each iteration. |

#### Epidemic Properties

The individuals start the simulation either with a *normal* health status, or a *infected* health status (defined by 
the initial infected population percent property). When an *infected* individual overlaps a *normal* individual in the
simulation map, the *normal* individual changes its health status to *infected*.

After a configured number of days, a configured percent of the *infected* individuals will need hospitalization, changing its
status to *hospitalized*. *Hospitalized* individuals can no longer move, but they can infect *normal* individuals that
happens to overlap them.  

After a configured number of days, *infected* and *hospitalized* individuals will change its health status to either 
*recovered* or *died* health status, respecting the configured death percentage. 

| Property                                      | Type    | Description                                                                                        |
|-----------------------------------------------|---------|----------------------------------------------------------------------------------------------------|
| simulation.epidemic.recoverydays              | Integer | Number of simulated days it takes to an individual recover from the disease.                       |
| simulation.epidemic.deathpercentage           | Double  | Probability for an infected individual die after the disease recovery time.                        |
| simulation.epidemic.hospitalizationpercentage | Double  | Probability of an infected individual need hospitalization after the disease hospitalization time. |
| simulation.epidemic.hospitalizationdays       | Integer | Number of simulated days it takes to decide if an infected individual will need hospitalization.   |

#### Health System Properties

When an individual needs hospitalization, it will fill one of the available ICUs beds, releasing it after it's recovery 
time. If there is no bed available when an individual needs, it will change its status to *died* instead of *hospitalized*.

| Property                                      | Type    | Description                                                                     |
|-----------------------------------------------|---------|---------------------------------------------------------------------------------|
| simulation.healthsystem.icus.availablebeds    | Integer | Number of Intensive Care Unit beds available to treat hospitalized individuals. |

#### General Graphics properties

If the graphic interface is enabled, the following properties will define how the simulation will be showed.

The map properties define the size of a window, starting at zero point in both X and Y axis, that shows the individuals
real time movements and health status. Note that the individuals can move outside this window, respecting the movement
boundaries properties.

| Property                                         | Type    | Description                                                                    |
|--------------------------------------------------|---------|--------------------------------------------------------------------------------|
| simulation.graphics.map.width                    | Integer | Width of the visible map.                                                      |
| simulation.graphics.map.height                   | Integer | Height of the visible map.                                                     |

#### Chart Panel properties

The chart panel properties defines a space to build a chart panel containing all the information about the ongoing simulation.

The info sub-properties defines the position each simulation information will be showed.   

| Property                                                    | Type    | Description                                                                    |
|-------------------------------------------------------------|---------|--------------------------------------------------------------------------------|
| simulation.graphics.chartpanel.x                            | Integer | Start point in the X axis of the chart panel.                                  |
| simulation.graphics.chartpanel.y                            | Integer | Start point in the Y axis of the chart panel.                                  |
| simulation.graphics.chartpanel.width                        | Integer | Width of the chart panel.                                                      |
| simulation.graphics.chartpanel.height                       | Integer | Height of the chart panel.                                                     |
| simulation.graphics.chartpanel.info.current.x               | Integer | Start point in the X axis of the current population health status information. |
| simulation.graphics.chartpanel.info.current.notexposed.y    | Integer | Y axis point to show the number of not exposed to the disease individuals.     |
| simulation.graphics.chartpanel.info.current.infected.y      | Integer | Y axis point to show the current number of infected individuals.               |
| simulation.graphics.chartpanel.info.current.hospitalized.y  | Integer | Y axis point to show the current number of hospitalized individuals.           |
| simulation.graphics.chartpanel.info.current.simulationday.y | Integer | Y axis point to show the current simulated day.                                |
| simulation.graphics.chartpanel.info.total.x                 | Integer | Start point in the X axis of the health status information totals.             |
| simulation.graphics.chartpanel.info.total.infected.y        | Integer | Y axis point to show the total number of infected individuals.                 |
| simulation.graphics.chartpanel.info.total.hospitalized.y    | Integer | Y axis point to show the total number of hospitalized individuals.             |
| simulation.graphics.chartpanel.info.total.recovered.y       | Integer | Y axis point to show the total number of recovered individuals.                |
| simulation.graphics.chartpanel.info.total.dead.y            | Integer | Y axis point to show the total number of dead individuals.                     |

#### Area Chart properties

The area chart properties defines a space to build an area chart, showing the evolution of the population health status.

| Property                                         | Type    | Description                                                                    |
|--------------------------------------------------|---------|--------------------------------------------------------------------------------|
| simulation.graphics.areachart.x                  | Integer | Start point in the X axis of the area chart.                                   |
| simulation.graphics.areachart.y                  | Integer | Start point in the Y axis of the area chart.                                   |
| simulation.graphics.areachart.width              | Integer | Width of the area chart.                                                       |
| simulation.graphics.areachart.height             | Integer | Height of the area chart.                                                      |

[1]: https://github.com/leohoc/epidemic-transmission-simulation/blob/master/src/main/resources/application.properties 

