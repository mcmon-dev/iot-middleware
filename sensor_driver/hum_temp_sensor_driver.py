import json
import threading
import grovepi
import time
import sys, getopt

from coapthon import defines
from coapthon.server.coap import CoAP
from coapthon.resources.resource import Resource
from coapthon.client.helperclient import HelperClient

client = None
server = None

current_time_in_milli = lambda : int(round(time.time() * 1000))

class HumidityResource(Resource):
    def __init__(self, name="BasicResource", coap_server=None):
        super(HumidityResource, self).__init__(name, coap_server, visible=True,
                                               observable=True, allow_children=True)
        self.resource_type = "Humidity Resource"
        self.content_type = "application/json"
        self.humidity = 0
        self.period = 5
        self.read_sensor(True)

        self.value = {"value": self.humidity, "unit": "percent", "timestamp": current_time_in_milli()}

    def render_GET(self, request):
        self.value = {"value": self.humidity, "unit": "percent", "timestamp": current_time_in_milli()}
        self.payload = (defines.Content_types["application/json"], json.dumps(self.value))
        return self

    def read_sensor(self, first=False):
        hum = grovepi.dht(4,0)[1]
        self.humidity = hum

        self.value = {"value": self.humidity, "unit": "percent", "timestamp": current_time_in_milli()}
        self.payload = (defines.Content_types["application/json"], json.dumps(self.value))

        if not self._coap_server.stopped.isSet():
            timer = threading.Timer(self.period, self.read_sensor)
            timer.setDaemon(True)
            timer.start()

            if not first and self._coap_server is not None:
                self._coap_server.notify(self)
                self.observe_count += 1


class TemperatureResource(Resource):
    def __init__(self, name="TemperatureResource", coap_server=None):
        super(TemperatureResource, self).__init__(name, coap_server, visible=True,
                                                  observable=True, allow_children=False)
        self.resource_type = "Temperature Resource"
        self.content_type = "application/json"
        self.temperature = 0
        self.period = 5
        self.read_sensor(True)

        self.value = {"value": self.temperature, "unit": "degree", "timestamp": current_time_in_milli()}

    def render_GET(self, request):
        self.value = {"value": self.temperature, "unit": "degree", "timestamp": current_time_in_milli()}
        self.payload = (defines.Content_types["application/json"], json.dumps(self.value))
        return self

    def read_sensor(self, first=False):
        temp = grovepi.dht(4,0)[0]
        self.temperature = temp

        self.value = {"value": self.temperature, "unit": "degree", "timestamp": current_time_in_milli()}
        self.payload = (defines.Content_types["application/json"], json.dumps(self.value))

        if not self._coap_server.stopped.isSet():
            timer = threading.Timer(self.period, self.read_sensor)
            timer.setDaemon(True)
            timer.start()

            if not first and self._coap_server is not None:
                self._coap_server.notify(self)
                self.observe_count += 1

class CoAPServer(CoAP):
    def __init__(self, host, port):
        CoAP.__init__(self, (host, port))
        print("CoAP server start on "+host+":"+str(port))

def start_server(port):
    global server
    server = CoAPServer("0.0.0.0", port)
    server.add_resource('humidity/', HumidityResource(coap_server=server))
    server.add_resource('temperature/', TemperatureResource(coap_server=server))
    try:
        server.listen(10)
    except KeyboardInterrupt:
        print("Server Shutdown")
        server.close()
        print("Exiting...")

def register(address, host, port):
    global client
    client = HelperClient(server=(host, port))
    response = client.post("register", "coap://"+address)
    client.close()
    print "Client closed"

    if response.code == defines.Codes.CREATED.number:
        print "Registry at "+host+":"+str(port)+" was successful."
        return True
    else:
        print "Registry at " + host + ":" + str(port) + " was not successful."
        return False

def parseCmdLine():
    try:
        opts, args = getopt.getopt(sys.argv[1:], "a:r:s:p:");

        if (len(opts) != 4):
            print 'USAGE: hum_temp_sensor_driver.py -a <ip address with port> -r <port for own coap server> -s <server ip address> -p <port>';
            sys.exit(2);

        for opt, arg in opts:
            if opt == "-a":
                address = arg;
            elif opt == "-r":
                res_port = int(arg);
            elif opt == "-s":
                host = arg;
            else:
                port = int(arg);

        return address, res_port, host, port;

    except getopt.GetoptError:
        print 'USAGE: hum_temp_sensor_driver.py -a <ip address with port> -r <port for own coap server> -s <server ip address> -p <port>';
        sys.exit(2);

if __name__ == '__main__':

    try:
        address, res_port, host, port = parseCmdLine();

        print "Starting Registry Request"
        isRegistered = register(address, host, port);

        if isRegistered:
            print "Starting server"
            start_server(res_port)
    except getopt.GetoptError:
        print 'hum_temp_sensor_driver.py -a <ip address with port>';
        sys.exit(2);
    finally:
        if client is not None:
            print "Closing client"
            client.close()
        if server is not None:
            print "Closing server"
            server.close()
