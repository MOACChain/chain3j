pragma solidity ^0.4.2;

 contract Mortal {
     address owner;

     constructor() public { owner = msg.sender; }

     function kill() public { if (msg.sender == owner) selfdestruct(owner); }
 }

 contract Greeter is Mortal {
     string greeting;

     constructor(string _greeting) public {
         greeting = _greeting;
     }

     function newGreeting(string _greeting) public {
         greeting = _greeting;
     }

     /* main function */
     function greet() public view returns (string) {
         return greeting;
     }

 	function getGreeting() public view returns (string){
 		return greeting;
 	}

 	function getGreeting2() public view returns (string){
 		return greeting;
 	}
 }