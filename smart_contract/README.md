#Smart Contract 

## Prerequisites
- Geth 1.9.10-stable

## Deployment
In case you need some basic knowledge how to use the Geth client have a look at this [tutorial](https://github.com/senacor/SmartContract-WorkshopSetup/tree/master/during-workshop) section *"First Steps with Ethereum"*.

1. In case you have changed the smart contract you have to recompile it. There are several ways to compile it. An easy 
way to do it is online with the [Solidity Compiler](https://remix.ethereum.org). Add your file, compile it and you will 
receive the ABI and the byte code file for the contract. In case you want to deploy the same contract which is contained 
in this repository just take the data from the [ABI](./IntegrityService.abi) and the [byte code](IntegrityService.bin) files.
2. Go to your terminal and connect to a network. The option *--testnet* indicates to connect to 
    Ropsten. However, you can also choose other networks. The light syncmode (*--syncmode light*) prevents the client from 
    synchronizing the whole blockchain.
    ```shell
    geth --testnet --syncmode light --rpc --allow-insecure-unlock console
    ```
    WATCH OUT: In more recent versions of the geth client it became insecure to unlock clients when providing access to 
    the node via rpc (--rpc). Since the original version of the IoT middleware connected via rpc the warning is bypassed 
    with *--allow-insecure-unlock*. However, if you consider using it in a real-world setting DO NOT use this option, 
    connect via the geth.ipc file.
3. Check that you have created two accounts, that the accounts have enough balance and that they are unlocked. Ether for 
    testnets can be retrieved some certain websites i.e. for Ropsten go to this [website](https://faucet.ropsten.be/).
4. Execute the following commands, to deploy the smart contract. The parameters of the constructor command *contract.new(10000, 10, ...*)
can be changed of course.
    ```javascript
    var contract = eth.contract(ABI);
    var contractInstance = contract.new(10000,10,{from: eth.accounts[0], data: 'BYTE_CODE', gas: 5000000})
    ```
    In case you have used the Solidity Compiler copy the ABI file and replace it in the command. Additionally, copy the 
    BYTE_CODE also from the Solidity Compiler. Watch out the copy contains also other content. Thus, take only the byte code 
    and add a leading *0x* at the beginning if it is not already there. In case you haven't recompiled the code copy the 
    files from this folder.
5. The contract is now deployed to the network.  
    HINT: To load an already deployed contract, execute the following commands:
    ```javascript
    var contract = eth.contract(ABI);
    var contractInstance = contract.at('ADDRESS')
    ```

## Initialization
In order to use the smart contract for updating it with sensor data, it has to be initialized.

1. Register with a second account which you haven't used for the creation of the contract. This account has to be used by
the IoT middleware to update the sensor data.
    ```javascript
    contractInstance.register.sendTransaction({from: eth.accounts[1], value: 10})
    ```

