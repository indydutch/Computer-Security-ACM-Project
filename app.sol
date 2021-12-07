pragma solidity 0.5.1;

contract Counter {
    uint public id = 0;
    uint public blockNum = block.number;

    event Increment(uint value);
    event Decrement(uint value);

    function increment() public {  
        id += 1;
        emit Increment(id);
    }

    function decrement() public {
        id -= 1;
        emit Decrement(id);
    }

    function getCount() view public returns(uint){
        return id;
    }

    function getBlockNum() view public returns(uint){
        return blockNum;
    }
}