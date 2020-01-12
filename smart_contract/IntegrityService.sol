pragma solidity ^0.4.21;

    /**
    * Integrity service for IoT data.
    */

contract IntegrityService {

    address institution;
    address client;

    uint balance;
    uint maxDelay;//in seconds
    uint lastUpdate;
    uint penalty;
    uint deposit;

    bool registered;

    event MeasurementUpdate(
        bytes32 indexed _hash
    );

    modifier onlyBy(address _account)
    {
        require(msg.sender == _account);
        _;
    }

	
    function IntegrityService(uint _maxDelay, uint _deposit) public{
	    institution = msg.sender;
        maxDelay = _maxDelay;
        deposit += _deposit;
    }

    /**
    * Client registers and pays the required deposit.
    */
    function register() public payable {

        require(msg.value == deposit && registered == false);

        client = msg.sender;
        balance = msg.value;
        registered = true;
    }

	/**
    * Update function to submit new values.
    */
    function update(bytes32 hash) onlyBy(client) public {

        require(registered == true);

        if(block.timestamp > lastUpdate+maxDelay){
            penalty = penalty + 1;
        }

        lastUpdate = block.timestamp;
        emit MeasurementUpdate(hash);
    }
	
	/**
    * Transfers the penaly to the address which is registered as institution.
    */
    function withdraw() onlyBy(institution) public {
        require(balance >= penalty);
        msg.sender.transfer(penalty);
        balance = balance - penalty;
    }

	/**
    * Transfers the remaining to balance.
    */
    function withdrawDeposit() onlyBy(client) public {
        msg.sender.transfer(balance);
        balance = 0;
    }
	
	/**
    * Resets all parameters to start the contract again. Only for testing purposes!
    */
    function reset() onlyBy(institution) public{
        registered = false;
        penalty = 0;
        lastUpdate = 0;
    }


    //********** ONLY FOR TESTING **************//
    function getBalance() public view returns(uint) {
        return balance;
    }

    function getPenalty() public view returns(uint) {
        return penalty;
    }

    function isRegistered() public view returns(bool) {
        return registered;
    }

    function getLastUpdate() public view returns(uint) {
        return lastUpdate;
    }
}
