# Console document

**help:**
```powershell
.\moac-windows-4.0-amd64.exe help
```

**Start a node on private chain**
```powershell
.\moac-windows-4.0-amd64.exe --dev --datadir ./dev --rpc --rpcaddr=0.0.0.0 --rpcapi="chain3, mc, admin, net, vnode, personal" --rpccorsdomain=*
```
Note: if you wanna create a node on testnet chain, you can use "--testnet".
    and the network id will be 101. The private node network id is 100.


**Connect to Moac node**
```powershell
.\moac-windows-4.0-amd64.exe attach
```
>connect to remote Moac nodes
```powershell
.\moac-windows-4.0-amd64.exe attach http://IP Address:port
```

**Common command**
```powershell
mc.accounts
personal.newAccount()
personal.unloackAccount("your accounts")
miner.start(1)
miner.stop()
```
**More details please view [here](https://github.com/MOACChain/moac-core/wiki/Commands).**

