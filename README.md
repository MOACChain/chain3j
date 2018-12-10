# Chain3j - Demo
<p>Sample program of Java demo based on the Chain3j.</p>

## Development Environment
- IDE: Intellij IDEA or Visual Studio Code
- Moac node
- Java Version: 1.8
- chain3j: The latest version

## Demo
The demo in BaseDemo package shows some example how to use the Chain3j to connect to Moac.

The demo in ContractDemo includes a sol sample file and ContractEentSample.java, TokenERC20.java. Two Java classes demonstrate how to get smart contract and call the smart contract in Moac.

**[Please read the Demo description for more details.](http://www.baidu.com)**

## Document list
- [Chain3j document](https://github.com/DavidRicardoWilde/chain3j-Win-Demo/blob/master/Document/Chain3j.md)
- [Console document](https://github.com/DavidRicardoWilde/chain3j-Win-Demo/blob/master/Document/Console.md)
- [Demo description](https://github.com/DavidRicardoWilde/chain3j-Win-Demo/blob/master/Document/Demo.md)

# Geting start
## Moac Chain
Download [the last version](https://github.com/MOACChain/moac-core).
<p>Create a node and a wallet for testing</p>

>Start a private chain and a node:
```PowerShell
.\moac-windows-4.0-amd64.exe --dev --datadir ./dev --rpc --rpcaddr=0.0.0.0 --rpcapi="chain3, mc, admin, net, vnode, personal" --rpccorsdomain=*
```
>Enter the console:
```PowerShell
.\moac-windows-4.0-amd64.exe attach
```
> Create a wakket:
```PowerShell
personal.newAccount("Your password")
*** or ***
personal.newAccount()
```
**[Please refer to the Console document for more details.](http://www.baidu.com)**

## Chain3j
Download [the latest version](https://github.com/MOACChain/chain3j)
It is a library for you to develop a Java or Android application

**[Chain3j Document](http://www.baidu.com)**

## Wallet

## Contract

## Demo
- [BaseDemo](https://github.com/DavidRicardoWilde/chain3j-Win-Demo/blob/master/BaseDemo/src/main/java/Demo/BaseDemo/BaseDemoApplication.java)
- [BaseFunctionDemo](https://github.com/DavidRicardoWilde/chain3j-Win-Demo/blob/master/BaseDemo/src/main/java/Demo/BaseDemo/BaseFunctionDemo.java): Base api function example
- [Contract Demo](https://github.com/DavidRicardoWilde/chain3j-Win-Demo/tree/master/BaseDemo/src/main/java/Demo/ContractDemo/GreetContract): Demo of calling smart contract
- **Other demos will be uploaded soon.**