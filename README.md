# PARKING LOT
This code is developed only to support parking lot for cars but is extensible enough to support parking of any type and dimensions. 
It has all proper exception handling for various cases like `same registration number vehicle cannot be parked twice`.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Pre-requisites
What things you need to install the software and how to install them
1. It requires `JDK 1.8` for setup on local
2. Used maven shade plugin to create runnable jar


### Installing
1. Import the project as maven project in Intellij or Eclipse.
2. Go to root directory of the project i.e. `parkinglot` and run `mvn clean install` to generate runnable jar file.
3. The runnable jar that is generated supports both inputs either give a file in arguments as below

``
java -jar parking-lot-1.0.0-SNAPSHOT.jar <input_file_path>
``
 
   Or, can be run interactively using command below. **While running in interactive mode program can be exited at any point of time using `quit` as an input.**

``
java -jar parking-lot-1.0.0-SNAPSHOT.jar
``


### Sample input
```
create_parking_lot 6
park KA-01-HH-1234 White
park KA-01-HH-9999 White
park KA-01-BB-0001 Black
park KA-01-HH-7777 Red
park KA-01-HH-2701 Blue
park KA-01-HH-3141 Black
leave 4
status
park KA-01-P-333 White
park DL-12-AA-9999 White
registration_numbers_for_cars_with_colour White
slot_numbers_for_cars_with_colour White
slot_number_for_registration_number KA-01-HH-3141
slot_number_for_registration_number MH-04-AY-1111
```

### Expected Output

```
Created a parking lot with 6 slots
Allocated slot number: 1
Allocated slot number: 2
Allocated slot number: 3
Allocated slot number: 4
Allocated slot number: 5
Allocated slot number: 6
Slot number 4 is free
Slot No. Registration No Colour
1 KA-01-HH-1234 White
2 KA-01-HH-9999 White
3 KA-01-BB-0001 Black
5 KA-01-HH-2701 Blue
6 KA-01-HH-3141 Black
Allocated slot number: 4
Sorry, parking lot is full
KA-01-HH-1234, KA-01-HH-9999, KA-01-P-333
1, 2, 4
6
Not found
```