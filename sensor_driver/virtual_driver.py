import json
import threading
import time
import sys, getopt

from coapthon import defines
from coapthon.server.coap import CoAP
from coapthon.resources.resource import Resource
from coapthon.client.helperclient import HelperClient

client = None
server = None

current_time_in_milli = lambda : int(round(time.time() * 1000))

class VirtualResource(Resource):

    def __init__(self, client_id, name="Virtual Resource", coap_server=None):
        super(VirtualResource, self).__init__(name, coap_server, visible=True,
                                              observable=True, allow_children=False)
        self.resource_type = "Virtual Temperatur Resource"
        self.content_type = "application/json"
        self.client_id = client_id;
        self.temperature = 1000
        self.period = 5
        self.sent_msg = 0
        self.read_sensor(0, True)
        self.value = {"value": self.temperature, "unit": "degree", "timestamp": current_time_in_milli()}

    def render_GET(self, request):
        self.value = {"value": self.temperature, "unit": "degree", "timestamp": current_time_in_milli()}
        self.payload = (defines.Content_types["application/json"], json.dumps(self.value))
        return self

    def read_sensor(self, sent_msg, first=False):
        self.temperature = 1000+sent_msg

        self.value = {"value": self.temperature, "unit": "degree", "timestamp": current_time_in_milli()}
        self.payload = (defines.Content_types["application/json"], json.dumps(self.value))

        if sent_msg > 60:
            print "############################# Client "+str(self.client_id)+" - Sent 60 Messages #############################"
        elif not self._coap_server.stopped.isSet() and sent_msg <= 60:
            sent_msg += 1
            timer = threading.Timer(self.period, self.read_sensor, (sent_msg, ))
            timer.setDaemon(True)
            timer.start()

            if not first and self._coap_server is not None:
                self._coap_server.notify(self)
                self.observe_count += 1


class CoAPServer(CoAP):
    def __init__(self, host, port):
        CoAP.__init__(self, (host, port))
        print("CoAP server start on "+host+":"+str(port))

def start_server(port, num_res):
    global server

    server = CoAPServer("0.0.0.0", port)

    for i in xrange(num_res):
        name = str(i)+'/'
        server.add_resource(name, VirtualResource(i,name,server))
        print "Added resource: "+name

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
        opts, args = getopt.getopt(sys.argv[1:], "a:r:s:p:c:");

        if (len(opts) != 5):
            print 'USAGE: virtual_driver.py -a <ip address with port> -r <port for own coap server> -s <server ip address> -p <port> -c <number of resources>';
            sys.exit(2);

        for opt, arg in opts:
            if opt == "-a":
                address = arg;
            elif opt == "-r":
                res_port = int(arg);
            elif opt == "-s":
                host = arg;
            elif opt == "-c":
                num_res = int(arg);
            else:
                port = int(arg);

        return address, res_port, host, port, num_res;

    except getopt.GetoptError:
        print 'USAGE: virtual_driver.py -a <ip address with port> -r <port for own coap server> -s <server ip address> -p <port> -c <number of resources>';
        sys.exit(2);

if __name__ == '__main__':
    try:
        address, res_port, host, port, num_res = parseCmdLine();

        print "Starting Registry Request"
        isRegistered = register(address, host, port);

        if isRegistered:
            print "Starting server"
            start_server(res_port, num_res)
    except getopt.GetoptError:
        print 'virtual_driver.py -a <ip address with port>';
        sys.exit(2);
    finally:
        if client is not None:
            print "Closing client"
            client.close()
        if server is not None:
            print "Closing server"
            server.close()
