#Sensor/Virtual Driver

## Prerequisites
- Python 2.7.6
- For the humidity and temperature sensor a  [Grove Temp&HumiSensor](http://wiki.seeedstudio.com/Grove-TemperatureAndHumidity_Sensor/) and a Raspberry Pi with the [GrovePi+ bridge](https://www.seeedstudio.com/GrovePi-p-2241.html) is needed.
- [IoT-Middleware](../iotmiddleware) has to be up and running

## Installation
Install the required Python modules.

```shell
pip install -r requirements.txt
```
To setup the required modules for the humidity and temperature sensor, please have look at the producer's [documentation](https://github.com/initialstate/grovepi/wiki/Part-2.-GrovePi-Setup).

## Configuration
The drivers are configured via their command line arguments. The arguments are explained in this section and the usage in the section below.

**-a:** This is the IP address of the IoT middleware which you call to register e.g 192.168.0.10:50000   
**-r:** This is the port where the sensor/virtual driver server is started  
**-s:** This is the IP or hostname of the middleware e.g. 192.168.0.10  
**-p:** This is the port of the IoT middleware e.g. 50000  
**-c (Virtual Driver only):** This is the number of data sources which shall be simulated or rather launched.

##Usage
This is the driver for the humidity and temperature sensor: 
```shell
USAGE: python hum_temp_sensor_driver.py -a <ip address with port> -r <port for own coap server> -s <server ip address> -p <port>
```
This is the virtual driver which simulates sensor data:
```shell
USAGE: python virtual_driver.py -a <ip address with port> -r <port for own coap server> -s <server ip address> -p <port> -c <number of data sources>
```

