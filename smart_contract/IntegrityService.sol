pragma solidity ^0.4.21;

/**
* Integrity service for IoT data.
*/

contract IntegrityService {

    address institution;
    address client;

    uint balance;
    uint maxDelay;//in seconds
    uint penalty;
    uint deposit;

    bool registered;
    bool penaltyPaid;

    mapping(bytes32 => uint) lastUpdates;

    event MeasurementUpdate(
        address indexed sender,
        uint8 function_code,
        uint8 digest_length,
        bytes32 digest
    );

    modifier onlyBy(address _account){
        require(msg.sender == _account);
        _;
    }


    function IntegrityService(uint _maxDelay, uint _deposit) public {
        institution = msg.sender;
        maxDelay = _maxDelay;
        deposit = _deposit;
    }

    /**
    * Here the client registers and pays the required deposit.
    */
    function register() public payable {

        require(msg.value == deposit && registered == false);

        client = msg.sender;
        balance = msg.value;
        registered = true;
    }

    function update(uint8 function_code, uint8 digest_length, bytes32 digest, bytes32 id_hash) onlyBy(client) public {

        require(registered == true && penaltyPaid == false);

        if (lastUpdates[id_hash] > 0 && block.timestamp > lastUpdates[id_hash] + maxDelay) {
            penalty += 1;
        }

        lastUpdates[id_hash] = block.timestamp;
        emit MeasurementUpdate(msg.sender, function_code, digest_length, digest);
    }

    function getLastUpdate(bytes32 id_hash) public returns (uint){

        if (lastUpdates[id_hash] > 0 && block.timestamp > lastUpdates[id_hash] + maxDelay) {
            penalty += 1;
        }

        return lastUpdates[id_hash];
    }

    function withdraw() onlyBy(institution) public {
        require(balance >= penalty);
        msg.sender.transfer(penalty);
        balance = balance - penalty;
        penalty = 0;
        penaltyPaid = true;
    }

    function withdrawDeposit() onlyBy(client) public {
        require(penaltyPaid = true);
        msg.sender.transfer(balance);
        balance = 0;
    }


    //********** ONLY FOR TESTING **************//
    function getBalance() public view returns (uint) {
        return balance;
    }

    function getPenalty() public view returns (uint) {
        return penalty;
    }

    function isRegistered() public view returns (bool) {
        return registered;
    }

    function getDeposit() public view returns (uint) {
        return deposit;
    }

    function getMaxDelay() public view returns (uint) {
        return maxDelay;
    }

    function reset() onlyBy(institution) public {
        registered = false;
        penalty = 0;
    }
}
